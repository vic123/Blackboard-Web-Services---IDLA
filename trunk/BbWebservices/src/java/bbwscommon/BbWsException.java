/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 */



public class BbWsException extends RuntimeException {
//public class BbWsException extends Exception {
    
    private static final String ERROR_PREFIX = "BbWsIdla custom error: ";
    
    public BbWsException(String message, Throwable cause) {
        super (ERROR_PREFIX + message, cause);
    }
    public BbWsException(String message) {
        super (ERROR_PREFIX + message);
    }


//java "extends WebServiceException" SOAP

//http://archive.netbsd.se/?ml=netbeans-j2ee&a=2007-06&t=4493871
//In order to have the runtime recognize the exception (I forget the exact error \
//now), but I had to add the @WebFault (faultBean="...") annotation to \
//the exception class.


//    Exceptions in RESTful HTTP with Restlet and JAXB
//http://sleeplessinslc.blogspot.com/2009_10_01_archive.html

//http://forums.java.net/jive/message.jspa?messageID=225716
//You need to annotate your exception class with @WebFault.
//hank you very much for your answer. Using JAX-WS 2.1.1 really solved the problem :-)
//I was using the JAX-WS version which is supplied by the standard installation of Java EE 5 SDK on the client side which obviously has the problem that I had encountered.

//JAX-WS WebServiceException SOAP @WebFault

//Using SOAP Faults and Exceptions in Java JAX-WS Web Services    
//http://io.typepad.com/eben_hewitt_on_java/2009/07/using-soap-faults-and-exceptions-in-java-jaxws-web-services.html
}
