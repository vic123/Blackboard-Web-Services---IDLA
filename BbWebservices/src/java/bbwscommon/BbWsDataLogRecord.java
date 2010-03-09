/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 */
import blackboard.platform.log.LogService;

public class BbWsDataLogRecord {
    private String recordId;
    private String fieldName;
    private String value;
    private String apiUsed;
    private String apiPass;
    private String severityLevel;
    private String message;
    private String dateTime;
    
    protected BbWsDataLogRecord () {
    }
    
    BbWsDataLogRecord (String recordId, String fieldName, String value, BbWsArguments.ApiToUseEnum apiUsed, int apiPass, String severityLevel, String message) {
        this.recordId = recordId;
        this.fieldName = fieldName;
        this.apiUsed = apiUsed.toString();
        this.apiPass = String.valueOf(apiPass);
        this.severityLevel = severityLevel;
        this.message = message;
        this.value = value;
        this.dateTime = getCurrentDateTime();
        /*BbWsLog.logForward(LogService.Verbosity.DEBUG,
                "\n" + "recordId: " + recordId + "\t"
                + "fieldName: " + fieldName + "\t"
                + "value: " + value + "\t"
                + "apiUsed: " + apiUsed + "\t"
                + "apiPass: " + apiPass + "\t"
                + "severityLevel: " + severityLevel + "\t"
                + "message: " + message + "\t"
                , this);*/ //-moved to BbWsDataAccessPack.addDataLog()
    }

    private String getCurrentDateTime() {
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.ms");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        //java.util.Date date = new java.util.Date();
        return dateFormat.format(cal.getTime());
    }
    
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getApiUsed() {
        return apiUsed;
    }

    public void setApiUsed(String apiUsed) {
        this.apiUsed = apiUsed;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the apiPass
     */
    public String getApiPass() {
        return apiPass;
    }

    /**
     * @param apiPass the apiPass to set
     */
    public void setApiPass(String apiPass) {
        this.apiPass = apiPass;
    }

}
