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
    BbWsException(String message, Throwable cause) {
        super(message, cause);
    }
    BbWsException(String message) {
        super(message);
    }
}
