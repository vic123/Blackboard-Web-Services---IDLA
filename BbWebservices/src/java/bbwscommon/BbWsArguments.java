/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bbwscommon;

import blackboard.platform.BbServiceManager;

import java.util.*;
import javax.xml.ws.WebServiceException;
/**
 *
 * @author vic
 */
public abstract class BbWsArguments <WsResultType extends BbWsDataDetails, WsInputType extends BbWsDataDetails> {
    /**
     * @return the currentPassApi
     */
    public BbWsArguments.ApiToUse getCurrentPassApi() {
        return currentPassApi;
    }

    /**
     * @param currentPassApi the currentPassApi to set
     */
    public void setCurrentPassApi(BbWsArguments.ApiToUse currentPassApi) {
        this.currentPassApi = currentPassApi;
    }

    /**
     * @return the libraryVersion
     */
    public String getLibraryVersion() {
        return libraryVersion;
    }

    /**
     * @return the input
     */
    public WsInputType getInputRecord() {
        return inputRecord;
    }
    public void setInputRecord(WsInputType recordInput) {
        this.inputRecord = recordInput;
    }


/*    private java.util.List convertLinkedHashMap2List(LinkedHashMap<String, WsResultType> lhm) {
        return Arrays.asList(lhm.values().toArray());
    }*/

    public List<WsResultType> getResultList() {
        return BbWsUtil.convertLinkedHashMap2List(resultLHM);
    }
    
    /*public void setResultList(List<WsResultType> listResult) {
        //this.resultList = listResult;
    }*/

    /**
     * @return the listInput
     */
    public List<WsInputType> getInputList() {
        return inputList;
    }

    /**
     * @return the apiToUseList
     */
    public java.util.List<ApiToUse> getApiToUseList() {
        return apiToUseList;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the nullValueTag
     */
    public String getNullValueTag() {
        return nullValueTag;
    }

    /**
     * @return the errorValueTag
     */
    public String getErrorValueTag() {
        return errorValueTag;
    }

    /**
     * @return the missFieldTag
     */
    public String getMissFieldTag() {
        return missFieldTag;
    }

    /**
     * @return the dataLogThrowErrorLevel
     */
    public DataLogSeverity getDataFieldErrorThrowLevel() {
        return dataFieldErrorThrowLevel;
    }

    public DataLogSeverity getDataLogSeverity() {
        return dataLogSeverity;
    }

    public DataVerbosity getDataVerbosity() {
        return dataVerbosity;
    }

    public String getDataAccessPackClassName() {
        return dataAccessPackClassName;
    }

    public String getProcessingKind() {
        return processingKind;
    }

    public String getInnerDAPDefaultClassName() {
        return innerDAPDefaultClassName;
    }

    public LinkedHashMap<String, WsResultType> getResultLHM() {
        return resultLHM;
    }

    public void setResultLHM(LinkedHashMap<String, WsResultType> resultLHM) {
        this.resultLHM = resultLHM;
    }

    public WsResultType getResultRecord() {
        return resultRecord;
    }

    public void setResultRecord(WsResultType resultRecord) {
        this.resultRecord = resultRecord;
    }

    public Class<WsResultType> getWsResultClass() {
        return wsResultClass;
    }

    public DataLogSeverity getDataRecordErrorThrowLevel() {
        return dataRecordErrorThrowLevel;
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

    public static enum DataVerbosity{NONE, ONLY_ID, MINIMAL, STANDARD, EXTENDED, CUSTOM};
    //??static final Hashtable<String, DataVerbosity> dataVerbosity = new Hashtable<String, DataVerbosity>();
    static final Hashtable<String, DataVerbosity> dataVerbosityTags = new Hashtable();
    static {
        dataVerbosityTags.put("NONE", DataVerbosity.NONE);
        dataVerbosityTags.put("EXTENDED", DataVerbosity.ONLY_ID);
        dataVerbosityTags.put("MINIMAL", DataVerbosity.MINIMAL);
        dataVerbosityTags.put("STANDARD", DataVerbosity.STANDARD);
        dataVerbosityTags.put("EXTENDED", DataVerbosity.EXTENDED);
        dataVerbosityTags.put("CUSTOM", DataVerbosity.CUSTOM);
    }
    
    public static enum DataLogSeverity{DEBUG, INFO, WARN, ERROR, FATAL};
    static final Hashtable<String, DataLogSeverity> dataLogSeverityTags = new Hashtable();
    static {
        dataLogSeverityTags.put("DEBUG", DataLogSeverity.DEBUG);
        dataLogSeverityTags.put("INFO", DataLogSeverity.INFO);
        dataLogSeverityTags.put("WARN", DataLogSeverity.WARN);
        dataLogSeverityTags.put("ERROR", DataLogSeverity.ERROR);
        dataLogSeverityTags.put("FATAL", DataLogSeverity.FATAL);
//        dataLogSeverityTags.put("NONE", DataLogSeverity.NONE);
    }
    /*
    public static enum InsertOrUpdate{INSERT, UPDATE};
    static final Hashtable<String, EnumSet<InsertOrUpdate>> insertOrUpdateTags = new Hashtable();
    static {
        insertOrUpdateTags.put("INSERT", EnumSet.of(InsertOrUpdate.INSERT));
        insertOrUpdateTags.put("UPDATE", EnumSet.of(InsertOrUpdate.UPDATE));
        insertOrUpdateTags.put("BOTH", EnumSet.of(InsertOrUpdate.INSERT, InsertOrUpdate.UPDATE));
    }*/

    public static enum ApiToUseEnum{DATA, GRADEBOOK2, WS, ADMIN_DATA};
    static final Hashtable<String, ApiToUseEnum> apiToUseTags = new Hashtable();
    static {
        apiToUseTags.put("DATA", ApiToUseEnum.DATA);
        apiToUseTags.put("GRADEBOOK2", ApiToUseEnum.GRADEBOOK2);
        apiToUseTags.put("WS", ApiToUseEnum.WS);
        apiToUseTags.put("ADMIN_DATA", ApiToUseEnum.ADMIN_DATA);
    }
    public static class ApiToUse {
        public ApiToUse(ApiToUseEnum api, String innerDAPClassNameOverride) {
            this.api = api;
            this.innerDAPClassNameOverride = innerDAPClassNameOverride;
        }
        public ApiToUseEnum api;
        public String innerDAPClassNameOverride;
    }

    public static final String ERROR_VALUE_TAG_DEF = "BbWsError";
    public static final String WARN_VALUE_TAG_DEF = "BbWsWarn";
    public static final String NULL_VALUE_TAG_DEF = "BbWsNull";
    public static final String MISS_FIELD_TAG_DEF = null;

    //completely internal fields
//    private List<WsResultType> resultList;
    private LinkedHashMap<String, WsResultType> resultLHM;
    private WsResultType resultRecord;
    private Class<WsResultType> wsResultClass;
    private BbWsArguments.ApiToUse currentPassApi;
    private int apiPassedCount;
    private String libraryVersion;

    private String dataAccessPackClassName;
    private String innerDAPDefaultClassName;
    private String processingKind;



    //migrated from Params fields
    private List<WsInputType> inputList;
    private WsInputType inputRecord;

    private java.util.List<ApiToUse> apiToUseList;
    private String password;
    private String nullValueTag;
    private String errorValueTag;
    private String warnValueTag;
    private String missFieldTag;
    private DataLogSeverity dataFieldErrorThrowLevel;
    private DataLogSeverity dataRecordErrorThrowLevel;
//    private EnumSet<InsertOrUpdate> insertOrUpdate;
//    private Boolean throwInsertUpdateOnly;
//    private Boolean checkPersistedDataWithLoad;
    private DataLogSeverity dataLogSeverity;
    private DataVerbosity dataVerbosity;
    private String datePattern;
    private String timePattern;




    public void initialize(Class<WsResultType> wsResultClass, BbWsParams params, WsInputType recordInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName) {
        //initializeInternal(wsObjectClass, paramsClass, resultClass, params, dataParams);
        if (recordInput != null) recordInput.initialize();
        initializeInternal(wsResultClass, params, recordInput, null, dataAccessPackClassName, innerDAPDefaultClassName);
    }

    public void initialize(Class<WsResultType> wsResultClass, BbWsParams params, List<WsInputType> listInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName) {
        if (listInput != null) {
            for (WsInputType wso: listInput) wso.initialize();
        }
        initializeInternal(wsResultClass, params, null, listInput, dataAccessPackClassName, innerDAPDefaultClassName);

    }

    protected void initializeInternal(Class<WsResultType> wsResultClass,
            BbWsParams params, WsInputType recordInput, List<WsInputType> listInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName) {

        this.wsResultClass = wsResultClass;
        this.dataAccessPackClassName = dataAccessPackClassName;
//        this.processingKind = processingKind;
        this.innerDAPDefaultClassName = innerDAPDefaultClassName;
        libraryVersion
                = BbServiceManager.getConfigurationService().getBbProperty(blackboard.platform.config.BbConfig.LIBRARY_VERSION);
        this.inputRecord = recordInput;
        this.inputList = listInput;

        initializeDefaults();

        if (params != null) {
            if (params.getPassword() != null) this.password = params.getPassword();
            if (params.getNullValueTag() != null) this.nullValueTag = params.getNullValueTag();
            if (params.getErrorValueTag() != null) this.errorValueTag = params.getErrorValueTag();
            if (params.getWarnValueTag() != null) this.warnValueTag = params.getWarnValueTag();
            if (params.getMissFieldTag() != null) this.missFieldTag = params.getMissFieldTag();


            if (params.getDataFieldErrorThrowSeverity() != null) {
                this.dataFieldErrorThrowLevel
                    = convertTag2Enum(dataLogSeverityTags, "dataFieldErrorThrowLevel", params.getDataFieldErrorThrowSeverity());
            }
            if (params.getDataRecordErrorThrowSeverity() != null) {
                this.dataRecordErrorThrowLevel
                    = convertTag2Enum(dataLogSeverityTags, "dataRecordErrorThrowLevel", params.getDataRecordErrorThrowSeverity());
            }


            /*if (params.getInsertOrUpdate() != null) {
                this.insertOrUpdate 
                    = convertTag2Enum(insertOrUpdateTags, "insertOrUpdate", params.getInsertOrUpdate());
            }*/
/*
            if (params.getApiToUseList() != null)   this.apiToUseList = params.getApiToUseList();
            //?? - in different class second assignement causes type conversion error
            String api = this.apiToUseList.get(0);
            String api1 = this.getApiToUseList().get(0);
*/
            if (params.getApiToUseList() != null) {
                this.apiToUseList.clear();
                for (BbWsParams.ApiToUse atu_tag: params.getApiToUseList()) {
                    ApiToUseEnum atu = convertTag2Enum(apiToUseTags, "apiToUseTag", atu_tag.apiTag);
                    ApiToUse api2use = new ApiToUse(atu, atu_tag.innerClassOverride);
                    this.apiToUseList.add(api2use);
                }
            }

            /*if (params.getThrowInsertUpdateOnly() != null) {
                this.throwInsertUpdateOnly = Boolean.parseBoolean(params.getThrowInsertUpdateOnly());
            }
            if (params.getCheckPersistedDataWithLoad() != null) {
                this.checkPersistedDataWithLoad = Boolean.parseBoolean(params.getCheckPersistedDataWithLoad());
            }*/
            if (params.getDataLogSeverity() != null) {
                this.dataLogSeverity = convertTag2Enum(dataLogSeverityTags, "dataLogSeverity", params.getDataLogSeverity());
            }
            if (params.getDataVerbosity() != null) {
                this.dataVerbosity = convertTag2Enum(dataVerbosityTags, "dataVerbosity", params.getDataVerbosity());
            }
        }
    }
    protected <EnumType> EnumType convertTag2Enum(Hashtable<String, EnumType> tags, String tagName, String tagValue) {
        EnumType et = tags.get(tagValue);
                if (et == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Error: Invalid value of " + tagName + " tag. Can be either of: ");
                    //sb.append(tagName);
                    //sb.append(" tag. Can be either of: ");
                    for (String s : tags.keySet() ) {
                        sb.append(s + ", ");
                    }
                    sb.append(", passed value: " + tagValue);
                    throw new WebServiceException(sb.toString());
                }
        return et;
    }

    protected void initializeDefaults() {
        this.apiToUseList = new java.util.ArrayList<ApiToUse>();
        this.getApiToUseList().add(new ApiToUse(ApiToUseEnum.DATA, null));
        //if (getLibraryVersion().compareTo ("8.0") >= 0) this.getApiToUseList().add("PLATFORM_GB2");

        this.password = "";
        this.nullValueTag = BbWsArguments.NULL_VALUE_TAG_DEF;
        this.errorValueTag = BbWsArguments.ERROR_VALUE_TAG_DEF;
        this.warnValueTag = BbWsArguments.WARN_VALUE_TAG_DEF;
        this.missFieldTag = BbWsArguments.MISS_FIELD_TAG_DEF;
        this.dataFieldErrorThrowLevel = DataLogSeverity.ERROR;
        this.dataRecordErrorThrowLevel = DataLogSeverity.ERROR;
        //http://www.jakobhoman.com/2008/08/javas-enumset-fun-for-whole-family.html
        //this.insertOrUpdate = EnumSet.of(InsertOrUpdate.INSERT, InsertOrUpdate.UPDATE);
        //this.throwInsertUpdateOnly = true;
        //this.checkPersistedDataWithLoad = true;
        this.dataLogSeverity = DataLogSeverity.WARN;
        this.dataVerbosity = DataVerbosity.STANDARD;
        this.datePattern = "yyyy-MM-dd";
        this.timePattern = "HH:mm:ss";
    }
    
    public int getApiPassedCount() {
        return apiPassedCount;
    }
    public void incApiPassedCount() {
        apiPassedCount++;
    }


}

