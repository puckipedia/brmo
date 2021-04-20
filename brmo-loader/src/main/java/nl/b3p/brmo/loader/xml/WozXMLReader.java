package nl.b3p.brmo.loader.xml;

import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.loader.StagingProxy;
import nl.b3p.brmo.loader.entity.Bericht;
import nl.b3p.brmo.loader.entity.WozBericht;
import nl.b3p.brmo.loader.util.RsgbTransformer;
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
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WozXMLReader extends BrmoXMLReader {
    public static final String PRS_PREFIX = "WOZ.NPS.";
    private final String pathToXsl = "/xsl/woz-brxml-preprocessor.xsl";
    private StagingProxy staging;
    private InputStream in;
    private Templates template;
    private NodeList nodes = null;
    private int index;

    public WozXMLReader(InputStream in, Date d, StagingProxy staging) throws Exception {
        this.in = in;
        this.staging = staging;

        if (d!=null) {
            setBestandsDatum(d);
        } else {
            //TODO  WOZ:stuurgegevens/StUF:tijdstipBericht 20200712072147894
             setDatumAsString("20200712072147894","yyyyMMddHHmmssSSS");
        }
        init();
    }

    @Override
    public void init() throws Exception {
        soort = BrmoFramework.BR_WOZ;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(in);

        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setURIResolver(new URIResolver() {
            @Override
            public Source resolve(String href, String base) {
                return new StreamSource(RsgbTransformer.class.getResourceAsStream("/xsl/" + href));
            }
        });

        Source xsl = new StreamSource(this.getClass().getResourceAsStream(pathToXsl));
        this.template = tf.newTemplates(xsl);

        nodes = doc.getDocumentElement().getChildNodes();
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
            t = this.template.newTransformer();
        } else {
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
        b.setBrOrgineelXml(origXML);
        b.setVolgordeNummer(index);
        b.setObjectRef(object_ref);
        b.setDatum(getBestandsDatum());
        return b;
    }


    private String getObjectRef(Node n) {
        // WOZ:object StUF:entiteittype="WOZ"/WOZ:wozObjectNummer
        // WOZ.WOZ.<nummer>
        // WOZ:object StUF:entiteittype="NPS"/WOZ:isEen/WOZ:gerelateerde/BG:inp.bsn
        // WOZ.NPS.<bsnhash>
        // WOZ:object StUF:entiteittype="NNP"/WOZ:isEen/WOZ:gerelateerde/BG:inn.nnpId
        // WOZ.NNP.nummer

        NodeList childs = n.getChildNodes();
        String objRef = null;
        for (int i = 0; i < childs.getLength(); i++) {
            Node child = childs.item(i);
            String name = child.getNodeName();
            if (name.contains("wozObjectNummer")) {
                objRef = child.getTextContent();
                break;
            }
        }
        return objRef;
    }

    /**
     * maakt een map met bsn,bsnhash.
     * @param n document node met bsn-nummer
     * @return hashmap met bsn,bsnhash
     * @throws XPathExpressionException if any
     */
    public Map<String, String> extractBSN(Node n) throws XPathExpressionException {
        Map<String, String> hashes = new HashMap<>();

        XPathFactory xPathfactory = XPathFactory.newInstance();
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
