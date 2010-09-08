/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 * 
 * Row Status values: 
 *  ENABLED = 0
 *  SOFT_DELETE = 1
 *  DISABLED = 2
 *  DELETE_PENDING = 3
 *  COPY_PENDING = 4
 *  DEFAULT = (DEFAULT)defineDefault(ENABLED); 

 */
import blackboard.platform.log.LogService;
import blackboard.data.Identifiable;
import java.util.List;

/*
//public abstract class BbWsDataDetails //<ArgumentsType extends BbWsCommonArguments>
public abstract class BbWsDataDetails<ParamsType extends BbWsCommonParams<? extends BbWsDataDetails>> extends BbWsDataDetails1//<ArgumentsType extends BbWsCommonArguments>
{
        public void initialize(ParamsType params) {

        }
}
 *
 */
//public abstract class BbWsDataDetails<ParamsType extends BbWsParams<? extends BbWsDataDetails>> //<ArgumentsType extends BbWsCommonArguments>
public abstract class BbWsDataDetails<ArgumentsType extends BbWsArguments>
{

    /**
     * @return the bbWsTextResult
     */
    public String getBbWsTextResult() {
        //throw new BbWsException("BbWsDataDetails.getBbWsTextResult() is not allowed, method is added just for automated web interface generation.");
        return bbWsTextResult;
    }
    static public class DATA_LOG_SEVERITY {
        public static final String ERROR = "ERROR";
        public static final String WARN = "WARN";
        public static final String INFO = "INFO";
        public static final String DEBUG = "DEBUG";
    }

    private int apiPassedCount;
    protected java.util.List<BbWsDataLogRecord> bbWsDataLog;
    private String bbWsTextResult;
    private String bbWsBoolResult;

    //protected BbWsDataDetails() {
    //}
    private void initialize() {
        bbWsDataLog = new java.util.ArrayList<BbWsDataLogRecord>();
        apiPassedCount = 0;
        bbWsBoolResult = "false";
    }
    public void initialize(BbWsArguments args) {
        initialize();
        initializeFields(args);
    }
    protected void initializeFields(BbWsArguments args) {
        String miss_tag = args.getMissFieldTag();
        if (miss_tag != null) {
            java.lang.Class<?> cls = this.getClass();
            BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,"initializeFields() - cls.getName(): " + cls.getName(), this);
            java.lang.reflect.Field fieldlist[] = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                java.lang.reflect.Field fld = fieldlist[i];
                if (fld.getName().compareTo("verbosity") == 0) continue;//!! - old field in UserDetails and other data structures
                BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.DEBUG,"initializeFields() - fld.getName(): " + fld.getName(), this);
                //!!
                try {
                    fld.setAccessible(true);
                    /*
                    Caused by: java.lang.IllegalArgumentException
                        at sun.reflect.UnsafeObjectFieldAccessorImpl.set(UnsafeObjectFieldAccessorImpl.java:63)
                        at java.lang.reflect.Field.set(Field.java:656)
                    at bbwscommon.BbWsDataDetails.initializeFields(BbWsDataDetails.java:80)
                    */
                    fld.set(this, miss_tag);
                    fld.setAccessible(false);//??
                } catch (IllegalAccessException e) {
                    BbWsLog.logForward(blackboard.platform.log.LogService.Verbosity.FATAL, e, "", this);
                }
            }
        }
    }
    /*
    public void appendDataLog(BbWsDataDetails dd) {
        //if (this.getId().length() == 0) this.setId(id + dd.getId());
        //else setId(id + ", " + dd.getId());
        this.setId(dd.getId());
        getBbWsDataLog().addAll(dd.getBbWsDataLog());
    }
    public void appendDataLog(BbWsDataDetails dd) {
        if (id.length() == 0) setId(id + dd.getId());
        else setId(id + ", " + dd.getId());
        getBbWsDataLog().addAll(dd.getBbWsDataLog());
    }
    */
    public void setFalseResult() {
        //appendDataLog(dd);
        //setBbWsBoolResult("false");
        //!!- change "false"/"true" at least to constants. Or internal independent boolean value
        bbWsBoolResult = "false";
    }
    public void setTrueResult() {
        //appendDataLog(dd);
        bbWsBoolResult = "true";
        setBbWsTextResult("Success");
    }
    
//works(1)    public void initialize(ParamsType params) {
//    public <ParamsType1 super ParamsType> void initialize(ParamsType1 params) {
//    public <ParamsType1 super BbWsCommonParams> void initialize(ParamsType1 params) {
//    public void initialize(BbWsCommonParams<? super ParamsType> params) {
//    public void initialize(BbWsDataDetails<? super ParamsType> params) {
//    public void initialize(bbwscommon.BbWsParams params) {
//        initialize();
//    }


    public void setBbWsTextResult(String bbWsTextResult) {
        this.bbWsTextResult = bbWsTextResult;
    }

    public String getBbWsBoolResult() {
        //throw new BbWsException("BbWsDataDetails.getBbWsBoolResult() is not allowed, method is added just for automated web interface generation.");
        return bbWsBoolResult;
    }

    public void setBbWsBoolResult(String bbWsBoolResult) {
        this.bbWsBoolResult = bbWsBoolResult;
    }

    public int getApiPassedCount() {
        return apiPassedCount;
    }
    public void incApiPassedCount() {
        apiPassedCount++;
    }
    
    public abstract String getBbId();
    public abstract void setBbId(String value);
    //public abstract String getBatchUid();
    //public abstract void setBatchUid(String value);
    
    public java.util.List<BbWsDataLogRecord> getBbWsDataLog() {
        return bbWsDataLog;
    }
    public void setBbWsDataLog(java.util.List<BbWsDataLogRecord> dataLog) throws Exception {
        bbWsDataLog = dataLog;
        //throw new BbWsException("BbWsDataDetails.setDataLog() is not allowed, method is added just for automated web interface generation.");
    }
    public void addDataLogRecord(String fieldName, String value, String bbValue, String wsValue, BbWsArguments.ApiToUseEnum apiUsed, int apiPass, String severityLevel, String message) {
        if (bbWsDataLog == null) bbWsDataLog = new java.util.ArrayList<BbWsDataLogRecord>();
        getBbWsDataLog().add(new BbWsDataLogRecord(getBbId(), fieldName, value, bbValue, wsValue, apiUsed, apiPass, severityLevel, message));
    }

}
