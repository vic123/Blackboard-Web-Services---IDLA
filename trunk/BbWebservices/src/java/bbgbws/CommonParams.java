/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author vic
 */
import blackboard.platform.BbServiceManager;

public class CommonParams {
    static String[] apiToUseTags = {
              "DATA_GB"
            , "PLATFORM_GB2"
            , "WS_GB"
        };

    static String[] logSeverityTags = {
              "DEBUG"
            , "INFO"
            , "WARN"
            , "ERROR"
        };

    protected String password;
    protected String commonEmptyValue;
    private String commonErrorValue;
    protected String apiToUse; //DATA_GB, PLATFORM_GB2, WS_GB
    protected String libraryVersion;
    protected int apiPassedCount;

    private String logVerbosity; //DEBUG, INFO, WARN, ERROR
    //?? protected String verbosity;
    private String currentPassApi;
    
    
    
    public CommonParams () {
        super();
        password = "";
        commonEmptyValue = "";
        commonErrorValue = "!!ERROR!!";
        apiToUse = "DATA_GB, PLATFORM_GB2";        
        logVerbosity = "WARN";
        apiPassedCount = 0;
        libraryVersion = BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.LIBRARY_VERSION);    
    }
   

    public CommonParams (CommonParams params) {
        this();
        if (params != null) {
            this.password = params.password;
            this.commonEmptyValue = params.commonEmptyValue;
            this.commonErrorValue = params.commonErrorValue;
            this.apiToUse = params.apiToUse;
            this.logVerbosity = params.logVerbosity;
        }
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCommonEmptyValue() {
        return commonEmptyValue;
    }

    public void setCommonEmptyValue(String commonEmptyValue) {
        this.commonEmptyValue = commonEmptyValue;
    }

    public String getApiToUse() {
        return apiToUse;
    }

    public void setApiToUse(String apiToUse) {
        this.apiToUse = apiToUse;
    }

    public String getLogVerbosity() {
        return logVerbosity;
    }

    public void setLogVerbosity(String logVerbosity) {
        this.logVerbosity = logVerbosity;
    }

    public String getCommonErrorValue() {
        return commonErrorValue;
    }

    public void setCommonErrorValue(String commonErrorValue) {
        this.commonErrorValue = commonErrorValue;
    }
    
    public int getApiPassedCount() {
        return apiPassedCount;
    }
    public void incApiPassedCount() {
        apiPassedCount++;
    }

    public String wsPrivate_getCurrentApiUsed() {
        return currentPassApi;
    }

    public void wsPrivate_setCurrentApiUsed(String currentPassApi) {
        this.currentPassApi = currentPassApi;
    }
    

    
}
