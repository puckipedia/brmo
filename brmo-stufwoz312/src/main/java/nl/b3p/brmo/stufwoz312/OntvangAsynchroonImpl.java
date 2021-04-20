package nl.b3p.brmo.stufwoz312;

import nl.egem.stuf.stuf0301.*;
import nl.waarderingskamer.stuf._0312.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.Holder;
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
// check of we dit nodig hebben... geeft een warning:
// WARNING [RMI TCP Connection(2)-127.0.0.1] com.sun.xml.ws.model.RuntimeModeler.buildRuntimeModel
// Invalid annotation: @SOAPBinding on endpoint implementation class "nl.b3p.brmo.stufwoz312.OntvangAsynchroonImpl"
// - will be ignored. "nl.b3p.brmo.stufwoz312.OntvangAsynchroonImpl" is annotated with
// @WebService(endpointInterface="nl.waarderingskamer.stuf._0312.OntvangAsynchroonPortType"},
// it must not be annotated with @SOAPBinding, to fix it -
// put this annotation on the SEI nl.waarderingskamer.stuf._0312.OntvangAsynchroonPortType.
// @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
// logging
@HandlerChain(file = "/handler-chain.xml")
public class OntvangAsynchroonImpl implements OntvangAsynchroonPortType {
    private static final Log LOG = LogFactory.getLog(OntvangAsynchroonImpl.class);

    @Override
    public Bv03Bericht.Stuurgegevens nnpSa01(NNPStuurgegevensSa01 stuurgegevens, NNPInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens npsSa01(NPSStuurgegevensSa01 stuurgegevens, NPSInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens swoSa01Lvwoz(SWOStuurgegevensSa01 stuurgegevens, SWOSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens swoSh01Lvwoz(SWOStuurgegevensSh01 stuurgegevens, SWOSa01Lv actueel, SwoSh01Lvwoz.Historie historie) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens vesSa01(VESStuurgegevensSa01 stuurgegevens, VESInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wozSa01Lvwoz(WOZStuurgegevensSa01 stuurgegevens, WOZSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wozSh01Lvwoz(WOZStuurgegevensSh01 stuurgegevens, WOZSa01Lv actueel, WozSh01Lvwoz.Historie historie) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wrdSa01Lvwoz(WRDStuurgegevensSa01 stuurgegevens, WRDSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wrdSh01Lvwoz(WRDStuurgegevensSh01 stuurgegevens, WRDSa01Lv actueel, WrdSh01Lvwoz.Historie historie) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens bskmbDi01(StuurgegevensBSKMB stuurgegevens,
                                               WOZLvRoutering routering,
                                               NPSUpdateVoegToeMetDatumEindeSynchronisatie voegToeNPS,
                                               NNPUpdateVoegToeMetDatumEindeSynchronisatie voegToeNNP,
                                               VESUpdateVoegToeMetDatumEindeSynchronisatie voegToeVES,
                                               WRDUpdateVoegBeschikkingMBToe voegBeschikkingMBtoeWRD,
                                               WOZUpdateWijzigMedeBelanghebbende voegMedeBelanghebbendeToeWOZ) throws Fo03 {
        BSKMBDi01 bskmbDi01 = new BSKMBDi01();
        bskmbDi01.setStuurgegevens(stuurgegevens);
        bskmbDi01.setRoutering(routering);
        bskmbDi01.setVoegToeNPS(voegToeNPS);
        bskmbDi01.setVoegToeNNP(voegToeNNP);
        bskmbDi01.setVoegToeVES(voegToeVES);
        bskmbDi01.setVoegBeschikkingMBtoeWRD(voegBeschikkingMBtoeWRD);
        bskmbDi01.setVoegMedeBelanghebbendeToeWOZ(voegMedeBelanghebbendeToeWOZ);
        return saveBericht(bskmbDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens bswoDi01(StuurgegevensBSWO stuurgegevens, WOZLvRoutering routering, SWOUpdateBeeindig beeindigSWO) throws Fo03 {
        BSWODi01 bswoDi01 = new BSWODi01();
        bswoDi01.setStuurgegevens(stuurgegevens);
        bswoDi01.setRoutering(routering);
        bswoDi01.setBeeindigSWO(beeindigSWO);
        return saveBericht(bswoDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens bwozDi01(StuurgegevensBWOZ stuurgegevens,
                                              WOZLvRoutering routering,
                                              WOZUpdateBeeindig beeindigWOZ,
                                              List<NNPUpdateBeeindig> beeindigNNP,
                                              List<NPSUpdateBeeindig> beeindigNPS,
                                              List<VESUpdateBeeindig> beeindigVES) throws Fo03 {

        BWOZDi01 bwozDi01 = new BWOZDi01();
        bwozDi01.setStuurgegevens(stuurgegevens);
        bwozDi01.setRoutering(routering);
        bwozDi01.setBeeindigWOZ(beeindigWOZ);
        bwozDi01.getBeeindigNNP().addAll(beeindigNNP);
        bwozDi01.getBeeindigNPS().addAll(beeindigNPS);
        bwozDi01.getBeeindigVES().addAll(beeindigVES);

        return saveBericht(bwozDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens ibbDi01(StuurgegevensIBB stuurgegevens,
                                             WOZLvRoutering routering,
                                             WRDUpdateIndienenBezwaarBeroep indienenBezwaarBeroepWRD) throws Fo03 {
        IBBDi01 ibbDi01 = new IBBDi01();
        ibbDi01.setStuurgegevens(stuurgegevens);
        ibbDi01.setRoutering(routering);
        ibbDi01.setIndienenBezwaarBeroepWRD(indienenBezwaarBeroepWRD);
        return saveBericht(ibbDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mbeDi01(StuurgegevensMBE stuurgegevens,
                                             WOZLvRoutering routering,
                                             NPSUpdateVoegToe voegToeNPS,
                                             NNPUpdateVoegToe voegToeNNP,
                                             VESUpdateVoegToe voegToeVES,
                                             WOZUpdateWijzigStatusBelang wijzigStatusBelang,
                                             MBEDi01.WijzigEigenaarWOZ wijzigEigenaarWOZ,
                                             NNPUpdateBeeindig beeindigNNP,
                                             NPSUpdateBeeindig beeindigNPS,
                                             VESUpdateBeeindig beeindigVES) throws Fo03 {
        MBEDi01 mbeDi01 = new MBEDi01();
        mbeDi01.setStuurgegevens(stuurgegevens);
        mbeDi01.setRoutering(routering);
        mbeDi01.setVoegToeNPS(voegToeNPS);
        mbeDi01.setVoegToeNNP(voegToeNNP);
        mbeDi01.setVoegToeVES(voegToeVES);
        mbeDi01.setWijzigStatusBelang(wijzigStatusBelang);
        mbeDi01.setWijzigEigenaarWOZ(wijzigEigenaarWOZ);
        mbeDi01.setBeeindigNNP(beeindigNNP);
        mbeDi01.setBeeindigNPS(beeindigNPS);
        mbeDi01.setBeeindigVES(beeindigVES);
        return saveBericht(mbeDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mbgDi01(StuurgegevensMBG stuurgegevens,
                                             WOZLvRoutering routering,
                                             NPSUpdateVoegToe voegToeNPS,
                                             NNPUpdateVoegToe voegToeNNP,
                                             VESUpdateVoegToe voegToeVES,
                                             WOZUpdateWijzigStatusBelang wijzigStatusBelang,
                                             WOZUpdateWijzigGebruiker wijzigGebruikerWOZ,
                                             NNPUpdateBeeindig beeindigNNP,
                                             NPSUpdateBeeindig beeindigNPS,
                                             VESUpdateBeeindig beeindigVES) throws Fo03 {

        MBGDi01 mbgDi01 = new MBGDi01();
        mbgDi01.setStuurgegevens(stuurgegevens);
        mbgDi01.setRoutering(routering);
        mbgDi01.setVoegToeNPS(voegToeNPS);
        mbgDi01.setVoegToeNNP(voegToeNNP);
        mbgDi01.setVoegToeVES(voegToeVES);
        mbgDi01.setWijzigStatusBelang(wijzigStatusBelang);
        mbgDi01.setWijzigGebruikerWOZ(wijzigGebruikerWOZ);
        mbgDi01.setBeeindigNNP(beeindigNNP);
        mbgDi01.setBeeindigNPS(beeindigNPS);
        mbgDi01.setBeeindigVES(beeindigVES);
        return saveBericht(mbgDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens melDi01(StuurgegevensMEL stuurgegevens,
                                             WOZLvRoutering routering,
                                             SWOUpdateMelding meldingSWO,
                                             WOZUpdateMelding meldingWOZ,
                                             WRDUpdateMelding meldingWRD) throws Fo03 {
        MELDi01 melDi01 = new MELDi01();
        melDi01.setStuurgegevens(stuurgegevens);
        melDi01.setRoutering(routering);
        melDi01.setMeldingSWO(meldingSWO);
        melDi01.setMeldingWOZ(meldingWOZ);
        melDi01.setMeldingWRD(meldingWRD);
        return saveBericht(melDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mhefDi01(StuurgegevensMHEF stuurgegevens,
                                              WOZLvRoutering routering,
                                              WOZUpdateWijzigOZBVrijstelling wijzigWOZ,
                                              WRDUpdateWijzigHeffingskenmerken wijzigWRD) throws Fo03 {
        MHEFDi01 mhefDi01 = new MHEFDi01();
        mhefDi01.setStuurgegevens(stuurgegevens);
        mhefDi01.setRoutering(routering);
        mhefDi01.setWijzigWOZ(wijzigWOZ);
        mhefDi01.setWijzigWRD(wijzigWRD);
        return saveBericht(mhefDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mkenDi01(StuurgegevensMKEN stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigKenmerken wijzigWOZ) throws Fo03 {
        MKENDi01 mkenDi01 = new MKENDi01();
        mkenDi01.setStuurgegevens(stuurgegevens);
        mkenDi01.setRoutering(routering);
        mkenDi01.setWijzigWOZ(wijzigWOZ);
        return saveBericht(mkenDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mkozDi01(StuurgegevensMKOZ stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigKOZ wijzigKadObjectWOZ, SWOUpdateWijzigKOZ wijzigKadObjectSWO) throws Fo03 {
        MKOZDi01 mkozDi01 = new MKOZDi01();
        mkozDi01.setStuurgegevens(stuurgegevens);
        mkozDi01.setRoutering(routering);
        mkozDi01.setWijzigKadObjectWOZ(wijzigKadObjectWOZ);
        mkozDi01.setWijzigKadObjectSWO(wijzigKadObjectSWO);
        return saveBericht(mkozDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens msubDi01(StuurgegevensMSUB stuurgegevens,
                                              WOZLvRoutering routering,
                                              NNPUpdateWijzig wijzigNNP,
                                              NPSUpdateWijzig wijzigNPS,
                                              VESUpdateWijzig wijzigVES) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens nbskDi01(StuurgegevensNBSK stuurgegevens,
                                              WOZLvRoutering routering,
                                              WRDUpdateVoegWaardeToe voegToeWRD,
                                              WRDUpdateVoegBeschikkingToe voegBeschikkingtoe) throws Fo03 {
        // TODO implement
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens oswoDi01(StuurgegevensOSWO stuurgegevens, WOZLvRoutering routering, SWOUpdateVoegToe voegToeSWO) throws Fo03 {
        OSWODi01 oswoDi01 = new OSWODi01();
        oswoDi01.setStuurgegevens(stuurgegevens);
        oswoDi01.setRoutering(routering);
        oswoDi01.setVoegToeSWO(voegToeSWO);
        return saveBericht(oswoDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens owozDi01(StuurgegevensOWOZ stuurgegevens,
                                              WOZLvRoutering routering,
                                              List<NNPUpdateVoegToe> voegToeNNP,
                                              List<NPSUpdateVoegToe> voegToeNPS,
                                              List<VESUpdateVoegToe> voegToeVES,
                                              WOZUpdateVoegToe voegToeWOZ) throws Fo03 {
        OWOZDi01 owozDi01 = new OWOZDi01();
        owozDi01.setStuurgegevens(stuurgegevens);
        owozDi01.setRoutering(routering);
        owozDi01.getVoegToeNNP().addAll(voegToeNNP);
        owozDi01.getVoegToeNPS().addAll(voegToeNPS);
        owozDi01.getVoegToeVES().addAll(voegToeVES);
        owozDi01.setVoegToeWOZ(voegToeWOZ);
        return saveBericht(owozDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens ubbDi01(StuurgegevensUBB stuurgegevens, WOZLvRoutering routering, WRDUpdateUitspraakBezwaarBeroep uitspraakBezwaarBeroepWRD) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: " + uitspraakBezwaarBeroepWRD);
        saveBericht(uitspraakBezwaarBeroepWRD, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }

    @Override
    public Bv03Bericht.Stuurgegevens vbskDi01(StuurgegevensVBSK stuurgegevens, WOZLvRoutering routering, WRDUpdateVernietigBeschikking vernietigBeschikkingWRD) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: " + vernietigBeschikkingWRD);
        saveBericht(vernietigBeschikkingWRD, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }

    @Override
    public Bv03Bericht.Stuurgegevens waswoDi01(StuurgegevensWASWO stuurgegevens, WOZLvRoutering routering, SWOUpdateWijzigAfbakening wijzigAfbakeningSWO) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: " + wijzigAfbakeningSWO);
        WASWODi01 waswoDi01 = new WASWODi01();
        waswoDi01.setStuurgegevens(stuurgegevens);
        waswoDi01.setRoutering(routering);
        waswoDi01.setWijzigAfbakeningSWO(wijzigAfbakeningSWO);
        return saveBericht(wijzigAfbakeningSWO, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wawozDi01(StuurgegevensWAWOZ stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigAfbakening wijzigAfbakeningWOZ) throws Fo03 {
        WAWOZDi01 wawozDi01 = new WAWOZDi01();
        wawozDi01.setStuurgegevens(stuurgegevens);
        wawozDi01.setRoutering(routering);
        wawozDi01.setWijzigAfbakeningWOZ(wijzigAfbakeningWOZ);
        return saveBericht(wijzigAfbakeningWOZ, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemHangObjectOmDi01(StuurgegevensWGEMHangObjectOm stuurgegevens,
                                                          WOZLvRoutering routering,
                                                          SWOUpdateWijzigGemeente wijzigGemeenteSWO,
                                                          WOZUpdateWijzigGemeente wijzigGemeenteWOZ) throws Fo03 {
        WGEMHangObjectOmDi01 wgemHangObjectOmDi01 = new WGEMHangObjectOmDi01();
        wgemHangObjectOmDi01.setStuurgegevens(stuurgegevens);
        wgemHangObjectOmDi01.setRoutering(routering);
        wgemHangObjectOmDi01.setWijzigGemeenteSWO(wijzigGemeenteSWO);
        wgemHangObjectOmDi01.setWijzigGemeenteWOZ(wijzigGemeenteWOZ);
        return saveBericht(wgemHangObjectOmDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemHangSubjectOmDi01(StuurgegevensWGEMHangSubjectOm stuurgegevens,
                                                           WOZLvRoutering routering,
                                                           WGEMHangSubjectOmDi01.OudeGemeenteNNP oudeGemeenteNNP,
                                                           WGEMHangSubjectOmDi01.NieuweGemeenteNNP nieuweGemeenteNNP,
                                                           WGEMHangSubjectOmDi01.OudeGemeenteNPS oudeGemeenteNPS,
                                                           WGEMHangSubjectOmDi01.NieuweGemeenteNPS nieuweGemeenteNPS,
                                                           WGEMHangSubjectOmDi01.OudeGemeenteVES oudeGemeenteVES,
                                                           WGEMHangSubjectOmDi01.NieuweGemeenteVES nieuweGemeenteVES) throws Fo03 {
        WGEMHangSubjectOmDi01 wgemHangSubjectOmDi01 = new WGEMHangSubjectOmDi01();
        wgemHangSubjectOmDi01.setStuurgegevens(stuurgegevens);
        wgemHangSubjectOmDi01.setRoutering(routering);
        wgemHangSubjectOmDi01.setOudeGemeenteNNP(oudeGemeenteNNP);
        wgemHangSubjectOmDi01.setNieuweGemeenteNNP(nieuweGemeenteNNP);
        wgemHangSubjectOmDi01.setOudeGemeenteNPS(oudeGemeenteNPS);
        wgemHangSubjectOmDi01.setNieuweGemeenteNPS(nieuweGemeenteNPS);
        wgemHangSubjectOmDi01.setOudeGemeenteVES(oudeGemeenteVES);
        wgemHangSubjectOmDi01.setNieuweGemeenteVES(nieuweGemeenteVES);
        return saveBericht(wgemHangSubjectOmDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemMeldSubjectAfDi01(StuurgegevensWGEMMeldSubjectAf stuurgegevens,
                                                           WOZLvRoutering routering,
                                                           NNPUpdateBeeindig meldAfNNP,
                                                           NPSUpdateBeeindig meldAfNPS,
                                                           VESUpdateBeeindig meldAfVES) throws Fo03 {
        WGEMMeldSubjectAfDi01 wgemMeldSubjectAfDi01 = new WGEMMeldSubjectAfDi01();
        wgemMeldSubjectAfDi01.setStuurgegevens(stuurgegevens);
        wgemMeldSubjectAfDi01.setRoutering(routering);
        wgemMeldSubjectAfDi01.setMeldAfNNP(meldAfNNP);
        wgemMeldSubjectAfDi01.setMeldAfNPS(meldAfNPS);
        wgemMeldSubjectAfDi01.setMeldAfVES(meldAfVES);
        return saveBericht(wgemMeldSubjectAfDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemVoegSubjectToeDi01(StuurgegevensWGEMVoegSubjectToe stuurgegevens,
                                                            WOZLvRoutering routering,
                                                            NNPUpdateVoegToe voegToeNNP,
                                                            NPSUpdateVoegToe voegToeNPS,
                                                            VESUpdateVoegToe voegToeVES) throws Fo03 {
        WGEMVoegSubjectToeDi01 wgemVoegSubjectToeDi01 = new WGEMVoegSubjectToeDi01();
        wgemVoegSubjectToeDi01.setStuurgegevens(stuurgegevens);
        wgemVoegSubjectToeDi01.setRoutering(routering);
        wgemVoegSubjectToeDi01.setVoegToeNNP(voegToeNNP);
        wgemVoegSubjectToeDi01.setVoegToeNPS(voegToeNPS);
        wgemVoegSubjectToeDi01.setVoegToeVES(voegToeVES);
        return saveBericht(wgemVoegSubjectToeDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens wwspDi01(StuurgegevensWWSP stuurgegevens,
                                              WOZLvRoutering routering,
                                              SWOUpdateWijzigWaterschap wijzigWaterschapSWO,
                                              WOZUpdateWijzigWaterschap wijzigWaterschapWOZ) throws Fo03 {
        WWSPDi01 wwspDi01 = new WWSPDi01();
        wwspDi01.setStuurgegevens(stuurgegevens);
        wwspDi01.setRoutering(routering);
        wwspDi01.setWijzigWaterschapSWO(wijzigWaterschapSWO);
        wwspDi01.setWijzigWaterschapWOZ(wijzigWaterschapWOZ);
        return saveBericht(wwspDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Fo03Bericht fo01(Fo01Bericht body) throws Fo03 {
        return maakFout(body);
    }

    @Override
    public Bv03Bericht mbagDi01(MBAGDi01 body) throws Fo03 {
        // TODO implement
        LOG.warn("unsupported functie aanroep voor:  " + body);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }


    @Override
    public void fo03(Holder<Fo03Bericht> body) throws Fo03 {
        // TODO implement
        LOG.warn("unsupported functie aanroep voor:  " + body);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

}
