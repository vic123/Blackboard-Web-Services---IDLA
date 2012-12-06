/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 */
import javax.xml.ws.WebServiceException;

@javax.xml.ws.WebFault (name = "BbWsFaultException", targetNamespace = "http://www.ncl.ac.uk/BbWsFaultException")
public class BbWsFault extends Exception { //??WebServiceException
//public class BbWsFault extends WebServiceException {

 private BbWsFaultException faultInfo;


    public BbWsFault(String message, BbWsFaultException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public BbWsFault(String message, BbWsFaultException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public BbWsFaultException getFaultInfo() {
        return faultInfo;
    }

}
