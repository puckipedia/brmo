package nl.b3p.brmo.stufwoz312;

import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.loader.util.BrmoException;
import nl.b3p.brmo.service.util.ConfigUtil;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.egem.stuf.stuf0301.Fo03Bericht;
import nl.egem.stuf.stuf0301.StuurgegevensMKEN;
import nl.waarderingskamer.stuf._0312.Fo03;
import nl.waarderingskamer.stuf._0312.OntvangAsynchroonPortType;
import nl.waarderingskamer.stuf._0312.WOZLvRoutering;
import nl.waarderingskamer.stuf._0312.WOZUpdateWijzigKenmerken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nl.b3p.brmo.stufwoz312.util.StUFwoz312Util.*;

/**
 * @author mprins
 */
@WebService(
        serviceName = "OntvangAsynchroon",
        portName = "OntvangAsynchronePort",
        endpointInterface = "nl.waarderingskamer.stuf._0312.OntvangAsynchroonPortType",
        targetNamespace = "http://www.waarderingskamer.nl/StUF/0312",
        wsdlLocation = "WEB-INF/wsdl/woz0312_ontvangAsynchroon_lv_mutatie_afnemer.wsdl"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file = "/handler-chain.xml")
public class OntvangAsynchroonImpl implements OntvangAsynchroonPortType {
    private static final Log LOG = LogFactory.getLog(OntvangAsynchroonImpl.class);

    private void saveBericht(Object body, String datum) {
        try {
            DataSource ds = ConfigUtil.getDataSourceStaging();
            BrmoFramework brmo = new BrmoFramework(ds, null);
            InputStream in = getXml(body);

            Date d = STUFWOZDATEFORMAT.parse(datum);
            String bestand_naam = "StUF-WOZ upload op " + STUFWOZDATEFORMAT.format(new Date());

            brmo.loadFromStream(BrmoFramework.BR_BRP, in, bestand_naam, d, null);
            brmo.closeBrmoFramework();
        } catch (BrmoException ex) {
            LOG.error("Fout tijdens laden van StUF-BG bericht", ex);
        } catch (JAXBException | ParseException ex) {
            LOG.error("Fout tijdens parsen van bericht", ex);
        }
    }

    private InputStream getXml(Object o) throws JAXBException {
        try {
            // maak van POJO een inputstream
            Marshaller jaxbMarshaller = getStufJaxbContext().createMarshaller();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(o, baos);
            InputStream in = new ByteArrayInputStream(baos.toByteArray());

            // maak er een document van
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(in);
            List<AbstractMap.SimpleEntry<String, String>> prefixes = new ArrayList<>();
            getPrefixesRecursive(doc.getDocumentElement(), prefixes);
            //haal de body eruit met xpath
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//*[local-name() = 'body']/*");
            NodeList nodelist = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            Document newDoc = builder.newDocument();
            Node root = newDoc.createElement("root");

            for (int i = 0; i < nodelist.getLength(); i++) {
                Node n = nodelist.item(i);
                Node newNode = newDoc.importNode(n, true);
                root.appendChild(newNode);
            }

            // Vertaal xml naar inputstream voor verwerking in brmo framework
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new DOMSource(root), new StreamResult(outputStream));
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());

            return is;
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException ex) {
            LOG.error("Cannot parse body", ex);
        }
        return null;
    }

//    @Override
//    public Bv03Bericht.Stuurgegevens nnpSa01(NNPStuurgegevensSa01 stuurgegevens, NNPInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
//        throw new Fo03("unsupported", maakFout("unsupported"));
//    }

    /**
     * @param body
     * @return returns nl.egem.stuf.stuf0301.Fo03Bericht
     * @throws Fo03
     */
    @Override
    public Fo03Bericht fo01(Fo01Bericht body) throws Fo03 {
        return maakFout(body);
    }

    /**
     * @param body
     * @throws Fo03
     */
    @Override
    public void fo03(Holder<Fo03Bericht> body) throws Fo03 {

    }


    /**
     * @param stuurgegevens
     * @param routering
     * @param wijzigWOZ
     * @return returns nl.egem.stuf.stuf0301.Bv03Bericht.Stuurgegevens
     * @throws Fo03
     */
    @Override
    public Bv03Bericht.Stuurgegevens mkenDi01(StuurgegevensMKEN stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigKenmerken wijzigWOZ) throws Fo03 {
        saveBericht(wijzigWOZ, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }


}
