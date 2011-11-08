/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;

/**
 *
 * @author vic
 */
public class BbWsFaultException {

    private java.util.List<BbWsDataLogRecord> dataLog;
    protected String message;

    BbWsFaultException () {
        dataLog = new java.util.ArrayList<BbWsDataLogRecord>();
    }

    BbWsFaultException (String message) {
        this();
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }
    
    /**
     * @return the dataLog
     */
    public java.util.List<BbWsDataLogRecord> getDataLog() {
        return dataLog;
    }

    /**
     * @param dataLog the dataLog to set
     */
    public void setDataLog(java.util.List<BbWsDataLogRecord> dataLog) {
        this.dataLog = dataLog;
    }

}
