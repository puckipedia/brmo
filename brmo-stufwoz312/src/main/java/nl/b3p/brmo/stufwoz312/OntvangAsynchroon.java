package nl.b3p.brmo.stufwoz312;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.waarderingskamer.stuf._0312.NNPSa01SleutelSynchronisatieOptioneel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author mprins
 */
@WebService(
        serviceName = "StUFBGAsynchroon",
        portName = "StUFBGAsynchronePort",
        endpointInterface = "nl.waarderingskamer.stuf._0312.OntvangAsynchroon",
        targetNamespace = "http://www.waarderingskamer.nl/StUF/0312",
        wsdlLocation = "WEB-INF/wsdl/woz0312_ontvangAsynchroon_lv_mutatie_afnemer.wsdl"
)
@HandlerChain(file = "/handler-chain.xml")
public class OntvangAsynchroon {
    private static final Log LOG = LogFactory.getLog(OntvangAsynchroon.class);

    /**
     * Web service operation
     */
    public Bv03Bericht nnpSa01(NNPSa01SleutelSynchronisatieOptioneel message) {
        //TODO write your implementation code here:
        return null;
    }
    

}
