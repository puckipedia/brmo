/*
 * Copyright (C) 2021 B3Partners B.V.
 */
package nl.b3p.brmo.stufwoz312.util;

import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.loader.util.BrmoException;
import nl.b3p.brmo.loader.util.BrmoLeegBestandException;
import nl.b3p.brmo.service.util.ConfigUtil;
import nl.egem.stuf.stuf0301.*;
import nl.waarderingskamer.stuf._0312.Fo03;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;

/**
 * Utility methodes voor StUF BG 204.
 *
 * @author mprins
 */
public final class StUFwoz312Util {
    public static final SimpleDateFormat STUFWOZDATEFORMAT = new SimpleDateFormat("yyyyMMddkkmmssSSS");
    private static final Log LOG = LogFactory.getLog(StUFwoz312Util.class);
    private static final String XMLNAMESPACE = "xmlns";
    private static JAXBContext jaxbContext;

    private StUFwoz312Util() {
    }

    public static Bv03Bericht.Stuurgegevens saveBericht(Object jaxbElement, String tijdstipBericht) throws Fo03 {
        try {
            Date d = STUFWOZDATEFORMAT.parse(tijdstipBericht);
            String bestand_naam = "StUF-WOZ upload op " + STUFWOZDATEFORMAT.format(new Date());

            DataSource ds = ConfigUtil.getDataSourceStaging();
            BrmoFramework brmo = new BrmoFramework(ds, null);

            InputStream in = getXml(jaxbElement);
            brmo.loadFromStream(BrmoFramework.BR_WOZ, in, bestand_naam, d, null);
            brmo.closeBrmoFramework();
        } catch (BrmoLeegBestandException ex) {
            LOG.error(ex.getLocalizedMessage());
            throw new Fo03(ex.getLocalizedMessage(), maakFout(ex.getLocalizedMessage(), ex));
        } catch (BrmoException ex) {
            LOG.error("Fout tijdens laden van StUF-WOZ bericht", ex);
            throw new Fo03("Fout tijdens laden van StUF-WOZ bericht", maakFout("Fout tijdens laden van StUF-WOZ bericht", ex));
        } catch (JAXBException | ParseException ex) {
            LOG.error("Fout tijdens parsen van bericht", ex);
            LOG.debug("Fout tijdens parsen van bericht: " + jaxbElement);
            throw new Fo03("Fout tijdens parsen van bericht", maakFout("Fout tijdens parsen van bericht", ex));
        }
        return maakStuurgegevensBv03();
    }

    /**
     * Maakt van POJO een inputstream.
     *
     * @param jaxbElement POJO (bericht body)
     * @return inputstream van bericht body/POJO
     * @throws JAXBException als mashalling mislukt
     */
    private static InputStream getXml(Object jaxbElement) throws JAXBException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Marshaller jaxbMarshaller = StUFwoz312Util.getStufJaxbContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.marshal(jaxbElement, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static Fo03Bericht.Stuurgegevens maakStuurgegevensFo03() {
        Fo03Bericht.Stuurgegevens stuurgegevens = new Fo03Bericht.Stuurgegevens();
        stuurgegevens.setTijdstipBericht(STUFWOZDATEFORMAT.format(new Date()));
        Systeem sys = new Systeem();
        sys.setApplicatie("BRMO StUF woz 3.12");
        sys.setOrganisatie("B3Partners B.V.");
        sys.setAdministratie("P");
        stuurgegevens.setZender(sys);
        stuurgegevens.setBerichtcode(Berichtcode.FO_03);
        return stuurgegevens;
    }

    public static Fo03Bericht maakFout(String errorcode) {
        return maakFout(errorcode, null);
    }

    public static Fo03Bericht maakFout(String errorcode, Exception e) {
        final Fo03Bericht fout = new Fo03Bericht();
        Foutbericht f = new Foutbericht();
        f.setCode(errorcode);
        f.setPlek(Foutplek.SERVER);
        if (e != null) {
            f.setOmschrijving(e.getLocalizedMessage());
            f.setDetails(e.toString());
        }
        fout.setBody(f);
        fout.setStuurgegevens(maakStuurgegevensFo03());

        return fout;
    }

    public static Fo03Bericht maakFout(Fo01Bericht body) {
        final Fo03Bericht fout = new Fo03Bericht();
        fout.setStuurgegevens(maakStuurgegevensFo03());
        fout.setBody(body.getBody());

        return fout;
    }

    public static Bv03Bericht.Stuurgegevens maakStuurgegevensBv03() {
        Bv03Bericht.Stuurgegevens stuurgegevens = new Bv03Bericht.Stuurgegevens();
        stuurgegevens.setTijdstipBericht(STUFWOZDATEFORMAT.format(new Date()));
        Systeem sys = new Systeem();
        sys.setApplicatie("BRMO StUF woz 3.12");
        sys.setOrganisatie("B3Partners B.V.");
        sys.setAdministratie("P");
        stuurgegevens.setZender(sys);
        stuurgegevens.setBerichtcode(Berichtcode.BV_03);
        // TODO mogelijk niet correct
        stuurgegevens.setOntvanger(sys);
        // stuur.setCrossRefnummer();
        // stuur.setReferentienummer();

        return stuurgegevens;
    }

    public static Bv03Bericht maakBevestiging() {
        Bv03Bericht bericht = new Bv03Bericht();
        bericht.setStuurgegevens(maakStuurgegevensBv03());

        return bericht;
    }

    public static JAXBContext getStufJaxbContext() throws JAXBException {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance("nl.egem.stuf.stuf0301:nl.egem.stuf.sector.bg._0310:nl.waarderingskamer.stuf._0312:net.opengis.gml");
        }
        return jaxbContext;
    }

    public static void getPrefixesRecursive(Element element, List<SimpleEntry<String, String>> prefixes) {
        getPrefixes(element, prefixes);
        Node parent = element.getParentNode();
        if (parent instanceof Element) {
            getPrefixesRecursive((Element) parent, prefixes);
        }
    }

    /**
     * Get all prefixes defined on this element for the specified namespace.
     *
     * @param element  dom element
     * @param prefixes lijst van namespace prefixes
     */
    public static void getPrefixes(Element element, List<SimpleEntry<String, String>> prefixes) {
        NamedNodeMap atts = element.getAttributes();
        for (int i = 0; i < atts.getLength(); i++) {
            Node node = atts.item(i);
            String name = node.getNodeName();
            if (name != null && (XMLNAMESPACE.equals(name) || name.startsWith(XMLNAMESPACE + ":"))) {
                SimpleEntry<String, String> s = new SimpleEntry<>(name, node.getNodeValue());
                prefixes.add(s);
            }
        }
    }

}
