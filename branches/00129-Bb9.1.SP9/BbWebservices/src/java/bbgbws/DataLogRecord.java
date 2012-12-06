/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author vic
 */
public class DataLogRecord {
    private String fieldName;
    private String value;
    private String apiUsed;
    private String severityLevel;
    private String message;
    private String dateTime;
    
    protected DataLogRecord () {
    }
    
    DataLogRecord (String fieldName, String value, String apiUsed, String severityLevel, String message) {
        this.fieldName = fieldName;
        this.apiUsed = apiUsed;
        this.severityLevel = severityLevel;
        this.message = message;
        this.value = value;
        this.dateTime = getCurrentDateTime();
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

}
