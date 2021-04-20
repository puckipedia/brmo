package nl.b3p.brmo.stufwoz312;

import nl.b3p.brmo.loader.BrmoFramework;
import nl.b3p.brmo.loader.util.BrmoException;
import nl.b3p.brmo.loader.util.BrmoLeegBestandException;
import nl.b3p.brmo.service.util.ConfigUtil;
import nl.b3p.brmo.stufwoz312.util.StUFwoz312Util;
import nl.egem.stuf.stuf0301.*;
import nl.waarderingskamer.stuf._0312.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.Holder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
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

    private Bv03Bericht.Stuurgegevens saveBericht(Object jaxbElement, String tijdstipBericht) throws Fo03 {
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
    private InputStream getXml(Object jaxbElement) throws JAXBException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Marshaller jaxbMarshaller = StUFwoz312Util.getStufJaxbContext().createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.marshal(jaxbElement, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    @Override
    public Bv03Bericht.Stuurgegevens nnpSa01(NNPStuurgegevensSa01 stuurgegevens, NNPInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens npsSa01(NPSStuurgegevensSa01 stuurgegevens, NPSInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens swoSa01Lvwoz(SWOStuurgegevensSa01 stuurgegevens, SWOSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens swoSh01Lvwoz(SWOStuurgegevensSh01 stuurgegevens, SWOSa01Lv actueel, SwoSh01Lvwoz.Historie historie) throws Fo03 {
        return null;
    }

    @Override
    public Bv03Bericht.Stuurgegevens vesSa01(VESStuurgegevensSa01 stuurgegevens, VESInSa01SleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wozSa01Lvwoz(WOZStuurgegevensSa01 stuurgegevens, WOZSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wozSh01Lvwoz(WOZStuurgegevensSh01 stuurgegevens, WOZSa01Lv actueel, WozSh01Lvwoz.Historie historie) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wrdSa01Lvwoz(WRDStuurgegevensSa01 stuurgegevens, WRDSx01TLvSleutelSynchronisatieOptioneel actueel) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wrdSh01Lvwoz(WRDStuurgegevensSh01 stuurgegevens, WRDSa01Lv actueel, WrdSh01Lvwoz.Historie historie) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens bskmbDi01(StuurgegevensBSKMB stuurgegevens, WOZLvRoutering routering, NPSUpdateVoegToeMetDatumEindeSynchronisatie voegToeNPS, NNPUpdateVoegToeMetDatumEindeSynchronisatie voegToeNNP, VESUpdateVoegToeMetDatumEindeSynchronisatie voegToeVES, WRDUpdateVoegBeschikkingMBToe voegBeschikkingMBtoeWRD, WOZUpdateWijzigMedeBelanghebbende voegMedeBelanghebbendeToeWOZ) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens bswoDi01(StuurgegevensBSWO stuurgegevens, WOZLvRoutering routering, SWOUpdateBeeindig beeindigSWO) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens bwozDi01(StuurgegevensBWOZ stuurgegevens, WOZLvRoutering routering, WOZUpdateBeeindig beeindigWOZ, List<NNPUpdateBeeindig> beeindigNNP, List<NPSUpdateBeeindig> beeindigNPS, List<VESUpdateBeeindig> beeindigVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens ibbDi01(StuurgegevensIBB stuurgegevens, WOZLvRoutering routering, WRDUpdateIndienenBezwaarBeroep indienenBezwaarBeroepWRD) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }


    @Override
    public Bv03Bericht.Stuurgegevens mbeDi01(StuurgegevensMBE stuurgegevens, WOZLvRoutering routering, NPSUpdateVoegToe voegToeNPS, NNPUpdateVoegToe voegToeNNP, VESUpdateVoegToe voegToeVES, WOZUpdateWijzigStatusBelang wijzigStatusBelang, MBEDi01.WijzigEigenaarWOZ wijzigEigenaarWOZ, NNPUpdateBeeindig beeindigNNP, NPSUpdateBeeindig beeindigNPS, VESUpdateBeeindig beeindigVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens mbgDi01(StuurgegevensMBG stuurgegevens, WOZLvRoutering routering, NPSUpdateVoegToe voegToeNPS, NNPUpdateVoegToe voegToeNNP, VESUpdateVoegToe voegToeVES, WOZUpdateWijzigStatusBelang wijzigStatusBelang, WOZUpdateWijzigGebruiker wijzigGebruikerWOZ, NNPUpdateBeeindig beeindigNNP, NPSUpdateBeeindig beeindigNPS, VESUpdateBeeindig beeindigVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens melDi01(StuurgegevensMEL stuurgegevens, WOZLvRoutering routering, SWOUpdateMelding meldingSWO, WOZUpdateMelding meldingWOZ, WRDUpdateMelding meldingWRD) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens mhefDi01(StuurgegevensMHEF stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigOZBVrijstelling wijzigWOZ, WRDUpdateWijzigHeffingskenmerken wijzigWRD) throws Fo03 {
        LOG.warn("unsupported bericht voor:  " + wijzigWOZ + wijzigWRD);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }


    @Override
    public Bv03Bericht mbagDi01(MBAGDi01 body) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Fo03Bericht fo01(Fo01Bericht body) throws Fo03 {
        return maakFout(body);
    }

    @Override
    public void fo03(Holder<Fo03Bericht> body) throws Fo03 {
        LOG.warn("unsupported functie aanroep voor:  " + body);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens mkenDi01(StuurgegevensMKEN stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigKenmerken wijzigWOZ) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: "
                + wijzigWOZ.getEntiteittype()
                + ", functie: " + wijzigWOZ.getFunctie().value()
        );
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen routering: "
                + routering.getEntiteittype()
                + ", functie: " + routering.getFunctie().value()
        );
        // herstel het hele geparsed object
        MKENDi01 mkenDi01 = new MKENDi01();
        mkenDi01.setStuurgegevens(stuurgegevens);
        mkenDi01.setRoutering(routering);
        mkenDi01.setWijzigWOZ(wijzigWOZ);
        return saveBericht(mkenDi01, stuurgegevens.getTijdstipBericht());
    }

    @Override
    public Bv03Bericht.Stuurgegevens mkozDi01(StuurgegevensMKOZ stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigKOZ wijzigKadObjectWOZ, SWOUpdateWijzigKOZ wijzigKadObjectSWO) throws Fo03 {
        LOG.warn("unsupported bericht voor:  " + wijzigKadObjectWOZ);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens msubDi01(StuurgegevensMSUB stuurgegevens, WOZLvRoutering routering, NNPUpdateWijzig wijzigNNP, NPSUpdateWijzig wijzigNPS, VESUpdateWijzig wijzigVES) throws Fo03 {
        LOG.warn("unsupported bericht voor:  " + wijzigNNP);
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens nbskDi01(StuurgegevensNBSK stuurgegevens, WOZLvRoutering routering, WRDUpdateVoegWaardeToe voegToeWRD, WRDUpdateVoegBeschikkingToe voegBeschikkingtoe) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens oswoDi01(StuurgegevensOSWO stuurgegevens, WOZLvRoutering routering, SWOUpdateVoegToe voegToeSWO) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: " + voegToeSWO);
        saveBericht(voegToeSWO, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }

    @Override
    public Bv03Bericht.Stuurgegevens owozDi01(StuurgegevensOWOZ stuurgegevens, WOZLvRoutering routering, List<NNPUpdateVoegToe> voegToeNNP, List<NPSUpdateVoegToe> voegToeNPS, List<VESUpdateVoegToe> voegToeVES, WOZUpdateVoegToe voegToeWOZ) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
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
        saveBericht(wijzigAfbakeningSWO, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }

    @Override
    public Bv03Bericht.Stuurgegevens wawozDi01(StuurgegevensWAWOZ stuurgegevens, WOZLvRoutering routering, WOZUpdateWijzigAfbakening wijzigAfbakeningWOZ) throws Fo03 {
        LOG.debug("WOZUpdateWijzigKenmerken bericht ontvangen: " + wijzigAfbakeningWOZ);
        saveBericht(wijzigAfbakeningWOZ, stuurgegevens.getTijdstipBericht());
        return maakStuurgegevensBv03();
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemHangObjectOmDi01(StuurgegevensWGEMHangObjectOm stuurgegevens, WOZLvRoutering routering, SWOUpdateWijzigGemeente wijzigGemeenteSWO, WOZUpdateWijzigGemeente wijzigGemeenteWOZ) throws Fo03 {

        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemHangSubjectOmDi01(StuurgegevensWGEMHangSubjectOm stuurgegevens, WOZLvRoutering routering, WGEMHangSubjectOmDi01.OudeGemeenteNNP oudeGemeenteNNP, WGEMHangSubjectOmDi01.NieuweGemeenteNNP nieuweGemeenteNNP, WGEMHangSubjectOmDi01.OudeGemeenteNPS oudeGemeenteNPS, WGEMHangSubjectOmDi01.NieuweGemeenteNPS nieuweGemeenteNPS, WGEMHangSubjectOmDi01.OudeGemeenteVES oudeGemeenteVES, WGEMHangSubjectOmDi01.NieuweGemeenteVES nieuweGemeenteVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemMeldSubjectAfDi01(StuurgegevensWGEMMeldSubjectAf stuurgegevens, WOZLvRoutering routering, NNPUpdateBeeindig meldAfNNP, NPSUpdateBeeindig meldAfNPS, VESUpdateBeeindig meldAfVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wgemVoegSubjectToeDi01(StuurgegevensWGEMVoegSubjectToe stuurgegevens, WOZLvRoutering routering, NNPUpdateVoegToe voegToeNNP, NPSUpdateVoegToe voegToeNPS, VESUpdateVoegToe voegToeVES) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

    @Override
    public Bv03Bericht.Stuurgegevens wwspDi01(StuurgegevensWWSP stuurgegevens, WOZLvRoutering routering, SWOUpdateWijzigWaterschap wijzigWaterschapSWO, WOZUpdateWijzigWaterschap wijzigWaterschapWOZ) throws Fo03 {
        throw new Fo03("unsupported", maakFout("unsupported"));
    }

}
