package nl.b3p.brmo.loader.xml;

import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.loader.StagingProxy;
import nl.b3p.brmo.loader.entity.Bericht;
import nl.b3p.brmo.loader.entity.WozBericht;
import nl.b3p.brmo.loader.util.RsgbTransformer;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WozXMLReader extends BrmoXMLReader {
    public static final String PRS_PREFIX = "WOZ.NPS.";
    private static final Log LOG = LogFactory.getLog(WozXMLReader.class);
    private final String pathToXsl = "/xsl/woz-brxml-preprocessor.xsl";
    private final StagingProxy staging;
    private InputStream in;
    private Templates template;
    private NodeList nodes = null;
    private int index;
    private String brOrigXML = null;

    private final XPathFactory xPathfactory = XPathFactory.newInstance();

    public WozXMLReader(InputStream in, Date d, StagingProxy staging) throws Exception {
        this.in = in;
        this.staging = staging;
        setBestandsDatum(d);
        init();
    }

    @Override
    public void init() throws Exception {
        soort = BrmoFramework.BR_WOZ;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        in = new TeeInputStream(in, bos, true);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(in);

        brOrigXML = bos.toString(StandardCharsets.UTF_8.name());
        LOG.debug("Originele WOZ xml is: \n" + brOrigXML);

        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setURIResolver(new URIResolver() {
            @Override
            public Source resolve(String href, String base) {
                return new StreamSource(RsgbTransformer.class.getResourceAsStream("/xsl/" + href));
            }
        });

        Source xsl = new StreamSource(this.getClass().getResourceAsStream(pathToXsl));
        this.template = tf.newTemplates(xsl);

        XPath xpath = xPathfactory.newXPath();

        if (this.getBestandsDatum() == null) {
            // probeer nog uit doc te halen
            XPathExpression tijdstipBericht = xpath.compile("//*[local-name()='tijdstipBericht']");
            Node datum = (Node) tijdstipBericht.evaluate(doc, XPathConstants.NODE);
            setDatumAsString(datum.getTextContent(), "yyyyMMddHHmmssSSS");
        }

        XPathExpression objectNode = xpath.compile("//*[local-name()='object']");
        nodes = (NodeList) objectNode.evaluate(doc, XPathConstants.NODESET);
        index = 0;
    }

    @Override
    public boolean hasNext() throws Exception {
        return index < nodes.getLength();
    }

    @Override
    public WozBericht next() throws Exception {
        Node n = nodes.item(index);
        index++;
        String object_ref = getObjectRef(n);
        StringWriter sw = new StringWriter();

        // kijk hier of dit bericht een voorganger heeft: zo niet, dan moet niet de preprocessor template gebruikt worden, maar de gewone.
        Bericht old = staging.getPreviousBericht(object_ref, getBestandsDatum(), -1L, new StringBuilder());
        Transformer t;
        if (old != null) {
            LOG.debug("gebruik preprocessor xsl");
            t = this.template.newTransformer();
        } else {
            LOG.debug("gebruik extractie xsl");
            t = TransformerFactory.newInstance().newTransformer();
        }

        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(new DOMSource(n), new StreamResult(sw));

        Map<String, String> bsns = extractBSN(n);
        String el = getXML(bsns);
        String origXML = sw.toString();
        String brXML = "<root>" + origXML;
        brXML += el + "</root>";

        WozBericht b = new WozBericht(brXML);
        if (index == 1) {
            // alleen op 1e brmo bericht van mogelijk meer uit originele soap bericht
            b.setBrOrgineelXml(brOrigXML);
        }
        b.setVolgordeNummer(index);
        b.setObjectRef(object_ref);
        b.setDatum(getBestandsDatum());
        LOG.trace("bericht: " + b);
        return b;
    }


    private String getObjectRef(Node n) throws XPathExpressionException {
        // WOZ:object StUF:entiteittype="WOZ"/WOZ:wozObjectNummer
        // WOZ:object StUF:entiteittype="NPS"/WOZ:isEen/WOZ:gerelateerde/BG:inp.bsn
        // WOZ:object StUF:entiteittype="NNP"/WOZ:isEen/WOZ:gerelateerde/BG:inn.nnpId
        XPath xpath = xPathfactory.newXPath();
        XPathExpression wozObjectNummer = xpath.compile("//*[local-name()='wozObjectNummer']");
        XPathExpression bsn = xpath.compile("//*[local-name()='inp.bsn']");
        XPathExpression nnpId = xpath.compile("//*[local-name()='nnpId']");

        String objRef = null;
//        NodeList childs = n.getChildNodes();
//
//        for (int i = 0; i < childs.getLength(); i++) {
//            Node child = childs.item(i);
//            if (child != null && null != child.getLocalName()) {
//                switch (child.getLocalName()) {
//                    case "wozObjectNummer":
//                        objRef = "WOZ.WOZ." + child.getTextContent();
//                        break;
//                    case "inp.bsn":
//                        objRef = "WOZ.NPS." + getHash(child.getTextContent());
//                        break;
//                    case "inp.nnpId":
//                        objRef = "WOZ.NNP." + child.getTextContent();
//                        break;
//                }
//            }
//        }
        NodeList obRefs = (NodeList) wozObjectNummer.evaluate(n, XPathConstants.NODESET);
        if (obRefs.getLength() > 0) {
            objRef = "WOZ.WOZ." + obRefs.item(0).getTextContent();
        }
        obRefs = (NodeList) bsn.evaluate(n, XPathConstants.NODESET);
        if (obRefs.getLength() > 0) {
            objRef = "WOZ.NPS." + getHash(obRefs.item(0).getTextContent());
        }
        obRefs = (NodeList) nnpId.evaluate(n, XPathConstants.NODESET);
        if (obRefs.getLength() > 0) {
            objRef = "WOZ.NPS." + obRefs.item(0).getTextContent();
        }
        return objRef;
    }

    /**
     * maakt een map met bsn,bsnhash.
     *
     * @param n document node met bsn-nummer
     * @return hashmap met bsn,bsnhash
     * @throws XPathExpressionException if any
     */
    public Map<String, String> extractBSN(Node n) throws XPathExpressionException {
        Map<String, String> hashes = new HashMap<>();

        //XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//*[local-name() = 'inp.bsn']");
        NodeList nodelist = (NodeList) expr.evaluate(n, XPathConstants.NODESET);
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node bsn = nodelist.item(i);
            String bsnString = bsn.getTextContent();
            String hash = getHash(bsnString);
            hashes.put(bsnString, hash);

        }
        return hashes;
    }

    public String getXML(Map<String, String> map) throws ParserConfigurationException {
        if (map.isEmpty()) {
            // als in bericht geen personen zitten
            return "";
        }
        String root = "<bsnhashes>";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                String hash = entry.getValue();
                String el = "<" + PRS_PREFIX + entry.getKey() + ">" + hash + "</" + PRS_PREFIX + entry.getKey() + ">";
                root += el;
            }
        }
        root += "</bsnhashes>";
        return root;
    }
}
