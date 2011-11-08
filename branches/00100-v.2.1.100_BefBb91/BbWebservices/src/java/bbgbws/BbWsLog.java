/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;
import blackboard.platform.log.*;
import blackboard.platform.BbServiceManager;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.servlet.ServletContext;
import javax.xml.ws.handler.MessageContext; 



/**
 *
 * @author vic
 */
public class BbWsLog {
//@Resource
//private static WebServiceContext wsContext;

        

	static Log log = LogServiceFactory.getInstance().getDefaultLog();
        //static int debuggingLogLevelOvercome = LogService.Verbosity.WARNING.getLevelAsInt();
        static int logSeverityOverride = -1;
//        static int debuggingLogLevelOvercome = LogService.Verbosity.DEBUG.getLevelAsInt();
        //??static Log log = BbServiceManager.getLogService().getDefaultLog();
        
        public static LogService.Verbosity getOverridenSeverity (LogService.Verbosity verbosity) {
            if (logSeverityOverride == -1) {
                log.log("BBWS - logging initialization. (ServletContext sc = (ServletContext)wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);)", log.getVerbosityLevel());
                //ServletContext sc = (ServletContext)wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
                ServletContext sc = (ServletContext)BBGradebookWebService.wsContextStatic.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
                String value = sc.getInitParameter("logSeverityOverride");
                log.log("getOverridenSeverity - String value = sc.getInitParameter value: " + value, log.getVerbosityLevel());
                if (value == null) value = "0";
                logSeverityOverride = Integer.decode(value);
            }
//            return LogService.Verbosity.WARNING;
            
            if (log.getVerbosityLevel().getLevelAsInt() < verbosity.getLevelAsInt()
                    && verbosity.getLevelAsInt() <= logSeverityOverride) {
                return log.getVerbosityLevel();
            } else {
                return verbosity;
            }
        }
        
	public static void logForward(String message) {
                logForward(LogService.Verbosity.DEBUG, message);
	}
	public static void logForward(java.lang.Throwable error, String message) {
                logForward(LogService.Verbosity.DEBUG, error, message);
	}
        
	//log forwarding functions - intoroduced for easier production of log messages
	//without necessity of modifying of server log settings
	//assumes that active server log level is at least WARNING 
	public static void logForward(LogService.Verbosity verbosity, String message) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "	" + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
//		if (verbosity.getLevelAsInt() > debuggingLogLevelOvercome) {
//			log.logWarning(message);
//		}
                verbosity = getOverridenSeverity(verbosity);
		log.log(message, verbosity);
		//log.log("strLogMessages: " + strLogMessages, verbosity);    
	}
	public static void logForward(LogService.Verbosity verbosity, String message, Object obj) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "	" + String.valueOf(obj) + "   " + message + "; obj.toString(): ";
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
//		if (verbosity.getLevelAsInt() > debuggingLogLevelOvercome) {
//			log.logWarning(message);
//		}
		log.log(message, getOverridenSeverity(verbosity));
		//log.log("strLogMessages: " + strLogMessages, verbosity);    
	}
        
        //this.getClass().getName() 
	public static void logForward(LogService.Verbosity verbosity, java.lang.Throwable error, String message) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "    " + message;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
//		if (verbosity.getLevelAsInt() > debuggingLogLevelOvercome) {
//			log.logWarning(message, error);
//		}
		log.log(message, error, getOverridenSeverity(verbosity));
	}
	public static void logForward(LogService.Verbosity verbosity, java.lang.Throwable error, String message, Object obj) {
		message = "BBGradebookWebService	" + verbosity.toExternalString() + "	" + String.valueOf(obj) + " " + message + "; obj.toString(): " ;
		//using higher severity log level for easier development testing, log is overfilled when all messages are of debug level
		//actual log.logWarning has to be commented out in production release, but may be uncommented for collecting of log messages  
//		if (verbosity.getLevelAsInt() > debuggingLogLevelOvercome) {
//			log.logWarning(message, error);
//		}
		log.log(message, error, getOverridenSeverity(verbosity));
		//log.log("strLogMessages: " + strLogMessages, verbosity);    
	}
        
}
