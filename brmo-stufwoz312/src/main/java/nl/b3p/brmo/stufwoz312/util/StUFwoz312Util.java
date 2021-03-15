/*
 * Copyright (C) 2017 B3Partners B.V.
 */
package nl.b3p.brmo.stufwoz312.util;

import nl.egem.stuf.stuf0301.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility methodes voor StUF BG 204.
 *
 * @author mprins
 */
public final class StUFwoz312Util {

  public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmssSSS");
  private static JAXBContext jaxbContext;

  private StUFwoz312Util() {}

  public static Fo03Bericht Fo03Bericht(String errorcode) {
    return maakFout(errorcode, null);
  }

  public static Fo03Bericht maakFout(String errorcode, Exception e) {
    final Fo03Bericht fout = new Fo03Bericht();

    Fo03Bericht.Stuurgegevens stuurgegevens = new Fo03Bericht.Stuurgegevens();
    stuurgegevens.setTijdstipBericht(sdf.format(new Date()));
    Systeem sys = new Systeem();
    sys.setApplicatie("BRMO StUF woz 3.12");
    sys.setOrganisatie("B3Partners B.V.");
    stuurgegevens.setZender(sys);
    //        s.setOntvanger();
    //        s.setCrossRefnummer();
    //        s.setReferentienummer();

    stuurgegevens.setBerichtcode(Berichtcode.FO_03);
    fout.setStuurgegevens(stuurgegevens);

    Foutbericht f = new Foutbericht();
    f.setCode(errorcode);
    f.setPlek(Foutplek.SERVER);
    if (e != null) {
      f.setOmschrijving(e.getLocalizedMessage());
    }

    fout.setBody(f);
    return fout;
  }

  public static Bv03Bericht maakBevestiging() {
    Bv03Bericht bericht = new Bv03Bericht();
    Bv03Bericht.Stuurgegevens stuur = new Bv03Bericht.Stuurgegevens();
    stuur.setTijdstipBericht(sdf.format(new Date()));
    Systeem sys = new Systeem();
    sys.setApplicatie("BRMO StUF woz 3.12");
    sys.setOrganisatie("B3Partners B.V.");
    stuur.setZender(sys);
    stuur.setBerichtcode(Berichtcode.BV_03);
    // stuur.setOntvanger();
    // stuur.setCrossRefnummer();
    // stuur.setReferentienummer();

    bericht.setStuurgegevens(stuur);

    return bericht;
  }

  public static JAXBContext getStufJaxbContext() throws JAXBException {
    if (jaxbContext == null) {
      jaxbContext = JAXBContext.newInstance("nl.egem.stuf.stuf0301:nl.waarderingskamer.stuf._0312");
    }
    return jaxbContext;
  }
}
