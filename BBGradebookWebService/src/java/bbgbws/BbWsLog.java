/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;
import blackboard.platform.log.*;

/**
 *
 * @author vic
 */
public class BbWsLog {
	static Log log = LogServiceFactory.getInstance().getDefaultLog();
	static void logForward(String message) {
                logForward(LogService.Verbosity.WARNING, message);
	}
	static void logForward(java.lang.Throwable error, String message) {
                logForward(LogService.Verbosity.WARNING, error, message);
	}
        
	//log forwarding functions - intoroduced for easier production of log messages
	//without necessity of modifying of server log settings
	//assumes that active server log level is at least WARNING 
	static void logForward(LogService.Verbosity verbosity, String message) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "	" + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
		if (verbosity.getLevelAsInt() > LogService.Verbosity.WARNING.getLevelAsInt()) {
			log.logWarning(message);
		}
		log.log(message, verbosity);
		//log.log("strLogMessages: " + strLogMessages, verbosity);    
	}
	static void logForward(LogService.Verbosity verbosity, java.lang.Throwable error, String message) {
		message = "BBGradebookWebService	" + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
		if (verbosity.getLevelAsInt() > LogService.Verbosity.WARNING.getLevelAsInt()) {
			log.logWarning(message, error);
		}
		log.log(message, error, verbosity);
	}
}
