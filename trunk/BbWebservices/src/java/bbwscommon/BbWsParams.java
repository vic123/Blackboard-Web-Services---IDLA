/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package bbwscommon;

/**
 *
 * @author vic
 */
import blackboard.platform.BbServiceManager;
import java.util.List;

//public abstract class BbWsCommonParams<DataDetailsType extends BbWsDataDetails> {
//public class BbWsParams<DataDetailsType extends BbWsDataDetails> {
//public class BbWsParams<InputType extends BbWsInput> {
public class BbWsParams {
    //?? transferred (should not)
    /**
     * @param apiToUseList the apiToUseList to set
     */
    public void setApiToUseList(java.util.List<ApiToUse> apiToUseList) {
        //throw new BbWsException("BbWsParams.setApiToUseList() is not allowed, method is added just for automated web interface generation.");
        this.apiToUseList = apiToUseList;
    }

    public void setNullValueTag(String nullValueTag) {
        //throw new BbWsException("BbWsParams.setNullValueTag() is not allowed, method is added just for automated web interface generation.");
        this.nullValueTag = nullValueTag;
    }

    public void setErrorValueTag(String errorValueTag) {
        //throw new BbWsException("BbWsParams.setErrorValueTag() is not allowed, method is added just for automated web interface generation.");
        this.errorValueTag = errorValueTag;
    }

    public void setMissFieldTag(String missFieldTag) {
        //throw new BbWsException("BbWsParams.setMissFieldTag() is not allowed, method is added just for automated web interface generation.");
        this.missFieldTag = missFieldTag;
    }

    public void setDataFieldErrorThrowSeverity(String dataFieldErrorThrowLevel) {
        //throw new BbWsException("BbWsParams.setDataFieldErrorThrowLevel() is not allowed, method is added just for automated web interface generation.");
        this.dataFieldErrorThrowSeverity = dataFieldErrorThrowLevel;
    }

    public void setDataRecordErrorThrowSeverity(String dataRecordErrorThrowLevel) {
        //throw new BbWsException("BbWsParams.setDataRecordErrorThrowLevel() is not allowed, method is added just for automated web interface generation.");
        this.dataRecordErrorThrowSeverity = dataRecordErrorThrowLevel;
    }

    public void setDataVerbosity(String dataVerbosity) {
        //throw new BbWsException("BbWsParams.setDataVerbosity() is not allowed, method is added just for automated web interface generation.");
        this.dataVerbosity = dataVerbosity;
    }

    /**
     * @return the datePattern
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * @param datePattern the datePattern to set
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * @return the timePattern
     */
    public String getTimePattern() {
        return timePattern;
    }

    /**
     * @param timePattern the timePattern to set
     */
    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    /**
     * @return the warnValueTag
     */
    public String getWarnValueTag() {
        return warnValueTag;
    }

    /**
     * @param warnValueTag the warnValueTag to set
     */
    public void setWarnValueTag(String warnValueTag) {
        this.warnValueTag = warnValueTag;
    }
    public static class ApiToUse {
        public String apiTag;
        public String innerClassOverride;
    }
/*    static String[] apiToUseTags = {
              "DATA_GB"
            , "PLATFORM_GB2"
            , "WS_GB"
        };
*/
/*
    private DataDetailsType data;
    public DataDetailsType getDataDetails() {
        return getData();
    }
*/
    
    private java.util.List<ApiToUse> apiToUseList;
    private String password;
    //?? protected String verbosity;
    private String nullValueTag;
    private String errorValueTag;
    private String warnValueTag;
    private String missFieldTag;
    private String dataFieldErrorThrowSeverity;
    private String dataRecordErrorThrowSeverity;
//    private String insertOrUpdate;
//    private String throwInsertUpdateOnly;
//    private String checkPersistedDataWithLoad;
//    private String libraryVersion;
    private String dataLogSeverity;
    private String dataVerbosity;
    private String datePattern;
    private String timePattern;
   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<ApiToUse> getApiToUseList() {
        return apiToUseList;
    }

    public void setApiToUse(List<ApiToUse> apiToUseList) {
        this.setApiToUseList(apiToUseList);
    }

    public String getDataLogSeverity() {
        return dataLogSeverity;
    }

    public void setDataLogSeverity(String dataLogVerbosity) {
        this.dataLogSeverity = dataLogVerbosity;
    }

    public String getNullValueTag() {
        return nullValueTag;
    }
    public String getErrorValueTag() {
        return errorValueTag;
    }

    public String getMissFieldTag() {
        return missFieldTag;
    }

    public String getDataFieldErrorThrowSeverity() {
        return dataFieldErrorThrowSeverity;
    }

    public String getDataVerbosity() {
        return dataVerbosity;
    }

    public String getDataRecordErrorThrowSeverity() {
        return dataRecordErrorThrowSeverity;
    }
    
}


