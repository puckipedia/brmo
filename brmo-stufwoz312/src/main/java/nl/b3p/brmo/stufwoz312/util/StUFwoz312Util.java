/*
 * Copyright (C) 2021 B3Partners B.V.
 */
package nl.b3p.brmo.stufwoz312.util;

import nl.egem.stuf.stuf0301.*;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
    private static final String XMLNAMESPACE = "xmlns";
    private static JAXBContext jaxbContext;

    private StUFwoz312Util() {
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
        // stuur.setOntvanger();
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
                SimpleEntry s = new SimpleEntry(name, node.getNodeValue());
                prefixes.add(s);
            }
        }
    }

}
