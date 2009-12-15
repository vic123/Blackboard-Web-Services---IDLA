/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;

/**
 *
 * @author vic
 */
import blackboard.platform.log.LogService;
import blackboard.data.Identifiable;
import java.util.List;

public abstract class DataDetails {
    static public class DATA_LOG_SEVERITY {
        public static final String ERROR = "ERROR";
        public static final String WARN = "WARN";
        public static final String INFO = "INFO";
        public static final String DEBUG = "DEBUG";
    }
    
    private int apiPassedCount;
    private java.util.List<DataLogRecord> dataLog;    

    protected DataDetails() {
        dataLog = new java.util.ArrayList<DataLogRecord>();
        apiPassedCount = 0;
    }
    public int getApiPassedCount() {
        return apiPassedCount;
    }
    public void incApiPassedCount() {
        apiPassedCount++;
    }
    
    public abstract String getId();
    public java.util.List<DataLogRecord> getDataLog() {
        return dataLog;
    }
    public void setDataLog(java.util.List<DataLogRecord> dataLog) throws Exception {
        throw new Exception("DataDetails.setDataLog() is not allowed, method is added just for automated web interface generation.");
    }
    
    static public abstract class DataLoader<ParamsType extends CommonParams> {
        protected blackboard.platform.log.LogService.Verbosity logVerbosityInternal;
        protected ParamsType params;
        protected String apiUsed;
        public void load() throws Exception {
            try {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered DataLoader.load(); ", this);
                loadImp();
            } catch (Exception e) {
                BbWsLog.logForward(LogService.Verbosity.ERROR, e, "Dataloader.load() catch (Exception e): ", this);
                throw e;
            }
        }
        public abstract void loadImp() throws Exception;

        DataLoader (ParamsType params, String apiUsed) {
            this.apiUsed = apiUsed;
            this.params = params;
            logVerbosityInternal = blackboard.platform.log.LogService.Verbosity.WARNING;
            if (params.getLogVerbosity().compareTo(DATA_LOG_SEVERITY.ERROR) == 0) {
                logVerbosityInternal = blackboard.platform.log.LogService.Verbosity.ERROR;
            }
            if (params.getLogVerbosity().compareTo(DATA_LOG_SEVERITY.WARN) == 0) {
                logVerbosityInternal = blackboard.platform.log.LogService.Verbosity.WARNING;
            }
            if (params.getLogVerbosity().compareTo(DATA_LOG_SEVERITY.INFO) == 0) {
                logVerbosityInternal = blackboard.platform.log.LogService.Verbosity.INFORMATION;
            }
            if (params.getLogVerbosity().compareTo(DATA_LOG_SEVERITY.DEBUG) == 0) {
                logVerbosityInternal = blackboard.platform.log.LogService.Verbosity.DEBUG;
            }
        }
        
        public void addDataLog(DataDetails dd,
                blackboard.platform.log.LogService.Verbosity verbosity, 
                String fieldName, String value, String apiUsed, String message) {
            if (verbosity.getLevelAsInt() <=  logVerbosityInternal.getLevelAsInt()) {
                String str_verb = "null";
                //!!convert to hashmap
                if (verbosity ==  blackboard.platform.log.LogService.Verbosity.DEBUG) {
                    str_verb = DATA_LOG_SEVERITY.DEBUG;
                }
                if (verbosity ==  blackboard.platform.log.LogService.Verbosity.INFORMATION) {
                    str_verb = DATA_LOG_SEVERITY.INFO;
                }
                if (verbosity ==  blackboard.platform.log.LogService.Verbosity.WARNING) {
                    str_verb = DATA_LOG_SEVERITY.WARN;
                }
                if (verbosity ==  blackboard.platform.log.LogService.Verbosity.ERROR) {
                    str_verb = DATA_LOG_SEVERITY.ERROR;
                }
                dd.getDataLog().add(new DataLogRecord(fieldName, value, apiUsed, str_verb, message));                
            }
        }
    }
    
    static public abstract class RecordLoader<BBObjectType extends Identifiable, 
            ParamsType extends CommonParams, 
            DataDetailsType extends DataDetails> extends DataLoader<ParamsType> {
        protected BBObjectType bbObject;
        protected DataDetailsType record;
        /*
        public RecordLoader(BBObjectType bbObject, ParamsType params, DataDetailsType record) {
            this.bbObject = bbObject;
            this.params = params;
            this.record = record;
        }*/
        
        RecordLoader(ParamsType params, String apiUsed) {
            super(params, apiUsed);
        }

        public String loadValue(String oldValue, String fieldName, ValueLoader loaderImp) {
                return loaderImp.load(fieldName, oldValue);
        }
        
        protected abstract void createWsObject();
        protected abstract BBObjectType loadObj() throws Exception;
        
        public abstract class ValueLoader<BBObjectType>  {
            public abstract String loadImp(); 
            public String load(String fieldName, String oldValue) {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered ValueLoader.load(); ", this);
                String curValue = null;
                String value = "";
                try {
                    curValue = loadImp();
                    value = curValue;
                    if (oldValue != null) {
                        if (oldValue.compareTo(value) != 0) {
                            addDataLog(record, blackboard.platform.log.LogService.Verbosity.ERROR, 
                                fieldName, value, apiUsed, 
                                "APIs returned different values");
                            value = params.getCommonErrorValue();
                        }
                    }
                } catch (Exception e) {
                    addDataLog(record, blackboard.platform.log.LogService.Verbosity.ERROR, 
                        fieldName, value, apiUsed, 
                        "Exception: " + e.toString());
                   value = params.getCommonErrorValue();
                }
                addDataLog(record, blackboard.platform.log.LogService.Verbosity.DEBUG, 
                    fieldName, value, apiUsed, 
                    "curValue: " + curValue + "    oldValue: " +  oldValue);
                return value;                
            } 
        }
    }
    

    static public abstract class RecordListLoader<BBObjectType extends Identifiable, 
            ParamsType extends CommonParams, 
            DataDetailsType extends DataDetails, 
            RecordLoaderType extends RecordLoader<BBObjectType, ParamsType, DataDetailsType>> extends DataLoader<ParamsType> {
        java.util.LinkedHashMap<String, DataDetailsType> resultLHash;
        boolean firstApiPass;
        RecordListLoader(ParamsType params, String apiUsed, java.util.LinkedHashMap<String, DataDetailsType> resultLHash) {
            super(params, apiUsed);
            this.resultLHash = resultLHash;
            this.firstApiPass = true;
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (resultLHash.size() != 0) this.firstApiPass = false; resultLHash.size(): " + resultLHash.size(), this);
            if (resultLHash.size() != 0) this.firstApiPass = false;
        }
        
        protected abstract RecordLoaderType createRecordLoader ();
        protected abstract List<BBObjectType> loadList () throws Exception;
                    

        public void loadRecord(BBObjectType bbli) throws Exception {
            DataDetailsType li = null;
            //RecordLoaderType recLoader = new RecordLoaderType(params, apiUsed);
            RecordLoaderType recLoader = createRecordLoader();
            recLoader.bbObject = bbli;
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (!firstApiPass) { firstApiPass: " + firstApiPass, this);
            if (!firstApiPass) {
                li = resultLHash.get(bbli.getId().toExternalString());
                if (li == null) {
                    //li = new LineitemDetails();
                    recLoader.createWsObject();
                    addDataLog(li, LogService.Verbosity.WARNING, 
                            "", "", apiUsed, 
                            "Record was missing in previously passed APIs");
                } else recLoader.record = li;
            } else recLoader.createWsObject();
            recLoader.load();
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "resultLHash.put(recLoader.record.getId(), recLoader.record); record.getId(): " + recLoader.record.getId(), this);
            //resultLHash.put(li.getId(), li);
            resultLHash.put(recLoader.record.getId(), recLoader.record);
        }
        
        public void loadImp () throws Exception { //RecordList
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered loadImp(); ", this);
            List<BBObjectType> l = loadList();
            if(l.size()<1)
            {
                throw new BbWsException("No items found.");
            }
            java.util.Iterator<BBObjectType> i = l.iterator();
            while (i.hasNext())
            {
                loadRecord(i.next());
            }
            
            java.util.Iterator<DataDetailsType> lii = resultLHash.values().iterator();
            while (lii.hasNext())
            {
                DataDetailsType li = lii.next();
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (li.getApiPassedCount() < params.getApiPassedCount()+1) {; li.getId(): " + li.getId() + "; li.getApiPassedCount(): " + li.getApiPassedCount() 
                        + "; params.getApiPassedCount(): " + params.getApiPassedCount(), this);
                if (li.getApiPassedCount() < params.getApiPassedCount()) {
                    addDataLog(li, LogService.Verbosity.WARNING, 
                            "", "", apiUsed, 
                            "Record is missing in currently passed API");
                    li.incApiPassedCount();
                }
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "li.getApiPassedCount(): " + li.getApiPassedCount() 
                        + "; params.getApiPassedCount(): " + params.getApiPassedCount(), this);
                if (li.getApiPassedCount() != params.getApiPassedCount()+1) {
                    throw new Exception ("ASSERT: li.getApiPassedCount() != params.getApiPassedCount(+1");
                }
            }
            //or you will get an out of memory error...
            l.clear();
            l = null;
        }

    }
}
