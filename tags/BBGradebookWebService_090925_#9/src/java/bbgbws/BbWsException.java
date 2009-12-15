/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author vic
 */
public class BbWsException extends RuntimeException {
    
    private static final String ERROR_PREFIX = "BB webservices-idla custom error: ";    
    
    BbWsException(String message, Throwable cause) {
        super (ERROR_PREFIX + message, cause);
    }
    BbWsException(String message) {
        super (ERROR_PREFIX + message);
    }
}
