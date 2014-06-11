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
import blackboard.data.Identifiable;
import blackboard.persist.Id;
import blackboard.base.FormattedText;
import blackboard.base.BbEnum;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import blackboard.persist.Id;
import blackboard.persist.DataType;

public abstract class BbWsDataAccessPack<ArgumentsType extends BbWsArguments<WsResultType, WsInputType>,
        BbObjectType extends Identifiable, WsResultType extends BbWsDataDetails, WsInputType extends BbWsDataDetails> {
    private ArgumentsType args;

    protected Class<BbObjectType> bbObjectClass;
    protected BbObjectType bbObject;
    protected List<BbObjectType> bbObjectList;
    private DataAccessor firstAccessor;
    //public void initialize(ArgumentsType args, Class<BbObjectType> bbObjectClass, BbWsDataAccessor firstAccessor) {
    public void initialize(ArgumentsType args, Class<BbObjectType> bbObjectClass, DataAccessor firstAccessor) {

//                Class<BbWsInputProcessor> inputProcessorClass,
//                Class<BbWsDataAccessor> dataAccessorClass) {
        this.args = args;
        //http://blog.taragana.com/index.php/archive/fifo-list-in-java/
        //accessorsList = new LinkedList<BbWsDataAccesser>();
        //inputProcessor = BbWsUtil.newClassInstance(inputProcessorClass);
        this.firstAccessor = firstAccessor;
        //this.firstAccessor = BbWsUtil.newClassInstance(firstAccessorClass);
        //firstAccessor.initialize(null);
        this.bbObjectClass = bbObjectClass;
        //nextDataAccessor = BbWsUtil.newClassInstance(nextDataAccessorClass);
        //nextDataAccessor.initialize(args, nextDataAccesserClass);
        //accessorsList.add(nextDataAccesser);
        //nextDataAccesserClass =
    }
    public void startFirstAccessor() throws Exception {
        firstAccessor.access();
    }

        public void execute() throws Throwable {
            try {
                //args.getCurrentApiUsed();
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered execute(); ", this);
                //accessInternal();
                //firstAccessor.access();
                startFirstAccessor();
            } catch (Throwable e) {
                BbWsLog.logForward(LogService.Verbosity.ERROR, e, "execute() caught (Exception e): ", this);
                //blackboard.persist.PersistenceException e1;
                //!! use BbWsUtil.constructExceptionMessage()
                if (e instanceof blackboard.base.NestedException) {
                    if (((blackboard.base.NestedException)e).getNestedException() != null) {
                        e = ((blackboard.base.NestedException)e).getNestedException();
                    }
                }
                if (e.getCause() != null) e = e.getCause();
/*
 //This is the Basic Message.System.Web.Services.Protocols.SoapException: This is the Basic Message. at System.Web.Services.Protocols.SoapHttpClientProtocol.ReadResponse(SoapClientMessage message, WebResponse response, Stream responseStream, Boolean asyncCall) at System.Web.Services.Protocols.SoapHttpClientProtocol.Invoke(String methodName, Object[] parameters) at bbuws.BBUserWebService.insertRecordByBatchUid(bbWsParams params, userDetails inputRecord) in d:\W2KS\Microsoft.NET\Framework\v2.0.50727\Temporary ASP.NET Files\vs2005\8be1cfd5\d85564f6\App_WebReferences.l73_5ofw.2.cs:line 2545 at ASP.user_aspx.WsCall_insertRecordByBatchUid() in w:\BB-webservice\src\wservices_idla\TestClients\VS2005\User.aspx:line 59 at ASP.user_aspx.WsCall.Invoke() at ASP.user_aspx.__Render__control1(HtmlTextWriter __w, Control parameterContainer) in w:\BB-webservice\src\wservices_idla\TestClients\VS2005\User.aspx:line 279
 BbWsFaultException bbwsfe = new BbWsFaultException(e.toString() + ": " + e.getMessage());
                if (args.getResultList() != null) {
                    for (BbWsDataDetails result: args.getResultList()) {
                        bbwsfe.getDataLog().addAll(result.getDataLog());
                    }
                }
                if (args.getResultLHM() != null) {
                    for (BbWsDataDetails result: args.getResultLHM().values()) {
                        bbwsfe.getDataLog().addAll(result.getDataLog());
                    }
                }
                if (args.getResultRecord() != null) {
                    bbwsfe.getDataLog().addAll(args.getResultRecord().getDataLog());
                }
                if (args.getResultBool() != null) {
                    bbwsfe.getDataLog().addAll(args.getResultBool().getDataLog());
                }
                throw new BbWsFault("This is the Basic Message.", bbwsfe); */

                throw e;
            }
        }
        //abstract List<BbObjectType> loadList() throws Exception;
        protected void loadList() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".loadList() has to be overriden");
        }
        //abstract BbObjectType loadRecord() throws Exception;
        protected void loadRecord() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".loadRecord() has to be overriden");
        }
        protected void loadRecordByAltId() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".loadRecordByAltId() has to be overriden");
        }
        protected void setWsFields() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".getBbFields() has to be overriden");
        }
        protected void insertRecord() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".insertRecord() has to be overriden");
        }
        protected void updateRecord() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".updateRecord() has to be overriden");
        }
        protected void persistRecord() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".persistRecord() has to be overriden");
        }
        protected void setBbFields() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".setBbFields() has to be overriden");
        }
        protected void deleteRecord() throws Exception {
            throw new BbWsException (this.getClass().getName() + ".deleteRecord() has to be overriden");
        }

        protected Id checkAndgenerateId(DataType idType, String strId) throws Exception {
        //protected void checkNotNullId() throws Exception {
            if (strId == null 
                    || BbWsUtil.nullSafeStringComparator(strId, getArgs().getNullValueTag()) == 0
                    || BbWsUtil.nullSafeStringComparator(strId, getArgs().getMissFieldTag()) == 0) {
                throw new blackboard.persist.KeyNotFoundException ("Id for " + idType.getName() + " cannot be " + String.valueOf(strId) + " for " + getClass().getName() + ".");
            }
            return Id.generateId(idType, strId);
        }
        //public abstract void accessInternal() throws Exception;
        public static class BbWsDataLogException extends BbWsException {
            BbWsDataLogException(String message, Throwable cause) {
                super (message, cause);
            }
            BbWsDataLogException(String message) {
                super (message);
            }
        }

        //!! May be fields like OperationNum and OperationName
        //(i.e. something of kind OperationPass=1, OperationName="insert", OperationPass=1, OperationName="load")
        //So that DataLog be easier to read
        public void addDataLog(BbWsDataDetails dd, BbWsArguments.DataLogSeverity severity,
                String fieldName, String value, String bbValue, String wsValue, String message, Exception e) throws Exception {
            if (severity.compareTo(getArgs().getDataLogSeverity()) >= 0 ) {
                String sev = severity.toString();
                //dd.getDataLog().add(new BbWsDataLogRecord(fieldName, value, getArgs().getCurrentPassApi(), sev, message));
                dd.addDataLogRecord(fieldName, value, bbValue, wsValue, getArgs().getCurrentPassApi().api, getArgs().getApiPassedCount() + 1, sev, message);
            }
            if (getArgs().getDataFieldErrorThrowLevel().compareTo(severity) <= 0) {
                if (e != null) throw e;
                else throw new BbWsDataLogException(message);
            } else {
                if (e != null) BbWsLog.logForward(LogService.Verbosity.INFORMATION, e, "addDataLog() registered exception. dataFieldErrorThrowLevel level is set higher than " + severity.toString() + ", exception is not propagated. ", this);
            }                
            BbWsLog.logForward(LogService.Verbosity.DEBUG, 
                //"\n" + "recordId: " + recordId + "\t"
                "fieldName: " + fieldName + "\t"
                + "value: " + value + "\t"
                + "bbValue: " + bbValue + "\t"
                + "wsValue: " + wsValue + "\t"
                + "apiUsed: " + getArgs().getCurrentPassApi().api.toString() + "\t"
                + "apiPass: " + String.valueOf(getArgs().getApiPassedCount() + 1) + "\t"
                + "severityLevel: " + severity.toString() + "\t"
                + "message: " + message + "\t"
                , this);
            }

            

        public String extractDate(Calendar cal) {
            if (cal == null) return null;
            String pattern = getArgs().getDatePattern() + " " + getArgs().getTimePattern();
            java.text.SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(cal.getTime());
        }
        //http://www.velocityreviews.com/forums/t140831-convert-simpledateformat-to-gregoriancalendar.html
        public Calendar parseDate(String date) throws Exception {
            if (date == null) return null;
            String pattern = getArgs().getDatePattern() + " " + getArgs().getTimePattern();
            java.text.SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date d = formatter.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            return calendar;
        }

        public FormattedText parseFormattedText(String formattedText) throws Exception {
            final String FT_PREFIX = "FormattedText{type=";
            if (formattedText == null) return null;
            if (!formattedText.startsWith(FT_PREFIX))
                throw new BbWsException("FormattedText should start with \"FormattedText{type=\"");
            int comma_index = formattedText.indexOf(",");
            if (comma_index == -1)
                throw new BbWsException("Comma separating type from the text is not found");
            String ft_type_str = formattedText.substring(FT_PREFIX.length(), comma_index);
            //FormattedText.Type ft_type = (FormattedText.Type) BbEnum.fromFieldName(ft_type_str, FormattedText.Type.class);
            FormattedText.Type ft_type = BbWsUtil.bbEnumFromFieldName(ft_type_str, FormattedText.Type.class);
            int text_index = formattedText.indexOf("text=", comma_index + 1);
            if (text_index == -1)
                throw new BbWsException("\"text=\" is not found");
            String ft_text_str = formattedText.substring(text_index + "text=".length(),
                                    formattedText.length() - 1);
            FormattedText ft = new FormattedText(ft_text_str, ft_type);
            return ft;
        }


        /*
        protected void appendBoolResultDataLogFromInputRecord() {
            if (args.getResultBool() == null) {
                args.setResultBool(new BbWsBoolResult());
                args.getResultBool().initialize();
            }
            args.getResultBool().appendDataLog(args.getInputRecord());
        }*/


    /**
     * @return the args
     */
    public ArgumentsType getArgs() {
        return args;
    }

    protected void putMissingRecordIntoLHM() {
        LinkedHashMap<String, WsResultType> lhm = getArgs().getResultLHM();
        if (lhm != null) {
            if (getArgs().getResultRecord() != null) {
                if ( ! lhm.containsKey(getArgs().getResultRecord().getBbId())) {
                    if (BbWsUtil.nullSafeStringComparator(getArgs().getMissFieldTag(), getArgs().getResultRecord().getBbId()) == 0) {
                        getArgs().getResultRecord().setBbId(java.util.UUID.randomUUID().toString());
                    }
                    lhm.put(getArgs().getResultRecord().getBbId(), getArgs().getResultRecord());
                    getArgs().setResultRecord(null);
                }
            }
        }
    }


    public abstract class DataAccessor {
        DataAccessor nextDataAccessor;
        public void initialize(DataAccessor nextDataAccessor) {
            this.nextDataAccessor = nextDataAccessor;
        }
        public abstract void access() throws Exception;
    }

    public class InputListProcessor extends DataAccessor {
        List<LinkedHashMap<String, WsResultType>> lhmList;
        @Override public void access() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered access(); ", this);
            lhmList = new ArrayList<LinkedHashMap<String, WsResultType>>();
            LinkedHashMap<String, WsResultType> recordLHM = new LinkedHashMap<String, WsResultType>();
            for (WsInputType rec: getArgs().getInputList()) {
                try {
                    getArgs().setInputRecord(rec);
                    getArgs().setResultRecord(null);
                    getArgs().setResultLHM(null);
                    bbObject = null;
                    bbObjectList = null;

                    nextDataAccessor.access();

                } catch (Exception e) { //?? looks like duplicates catch in RecordAccessor - needs review
                    if (getArgs().getDataRecordErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0 ) {
                        //just rethrow it, it will be logged by DataAccessPack.execute()
                        throw e;
                    }
                    BbWsLog.logForward(LogService.Verbosity.ERROR, e, 
                            "Exception was caught in InputListProcessor.access() and dataRecordErrorThrowLevel parameter is set above ERROR - processing is continuied.", this);
                    putMissingRecordIntoLHM();
                }
                LinkedHashMap<String, WsResultType> lhm = getArgs().getResultLHM();
                if (lhm != null) {
                    lhmList.add(lhm);
                    if (getArgs().getResultRecord() != null) throw new BbWsException ("ASSERT: InputListProcessor() - nextDataAccessor.access(); both resultLHM and resultRecord are set - allowed only in case of esception in nextDataAccessor.access()");
                }
                else {
                    if (getArgs().getResultRecord() != null ) {
                        if (BbWsUtil.nullSafeStringComparator(getArgs().getMissFieldTag(), getArgs().getResultRecord().getBbId()) == 0) {
                            getArgs().getResultRecord().setBbId(java.util.UUID.randomUUID().toString());
                        }
                        recordLHM.put(getArgs().getResultRecord().getBbId(), getArgs().getResultRecord());
                    } else throw new BbWsException ("ASSERT: InputListProcessor() - nextDataAccessor.access(); did not set neither resultLHM nor resultRecord ");
                }

            }
            if (lhmList.size() != 0) {
                LinkedHashMap<String, WsResultType> res_lhm = new LinkedHashMap<String, WsResultType>();
                getArgs().setResultLHM(res_lhm);
                for (LinkedHashMap<String, WsResultType> lhm: lhmList) {
                    res_lhm.putAll(lhm);
                }
                if (recordLHM.size() != 0) throw new BbWsException ("ASSERT: InputListProcessor() - both recordLHM.size() and lhmList.size() sizes != 0");
            } else getArgs().setResultLHM(recordLHM);
            getArgs().setResultRecord(null);
        }
    }

    public abstract class RecordAccessor extends DataAccessor {
        protected abstract void accessRecord() throws Exception;
        @Override public void access() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered access(); ", this);
                try {
                    accessRecord();

                    //??!! in case of handled exception it gets re-set incorrectly - may happen if RecordAccessor calls another RecordAccessor.access
                    //initial code:
                    //getArgs().getResultRecord().setTrueResult();
                    //100824 - trying this, was not tested well 
                    if (BbWsUtil.nullSafeStringComparator(getArgs().getResultRecord().getBbWsBoolResult(), "false") != 0) {
                        getArgs().getResultRecord().setTrueResult();
                    }
                } catch (Exception e) {
                    if (getArgs().getDataRecordErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0 ) {
                        //just rethrow it, it will be logged by DataAccessPack.execute()
                        throw e;
                    }
                    //else -
                    BbWsLog.logForward(LogService.Verbosity.INFORMATION, e,
                            "Exception was caught in RecordAccessor.access() and dataRecordErrorThrowLevel parameter is set above ERROR - processing is continuied.", this);
                    if (getArgs().getResultRecord() != null) {
                        getArgs().getResultRecord().setFalseResult();
                        String msg = BbWsUtil.constructExceptionMessage(e);
                        getArgs().getResultRecord().setBbWsTextResult(msg);
                    } else throw new BbWsException ("ASSERT: RecordAccessor.access() - accessRecord() did not set resultRecord ", e);
                }
        }

    }

/*
    public abstract class BbWsRecordAccessor<BbObjectType extends Identifiable, wsObjectType extends BbWsDataDetails>
        extends BbWsDataAccessor {
        //protected wsObjectType recordInput;
        //public void initialize(ArgumentsType args, ) {
        //   super.initialize(args);

          //  this.recordInput = recordInput;
        //}
    }

 *
 */
    protected final void setResultRecordIds() {
        if (getArgs().getInputRecord() != null) {
            //getArgs().getResultRecord().setBbId(getArgs().getInputRecord().getBbId());
            setSafeResultRecordIds();
        }
    }
    protected void setSafeResultRecordIds() {
        getArgs().getResultRecord().setBbId(getArgs().getInputRecord().getBbId());
    }



    //Currently expected resultRecord argument can be only wsInputRecord
    //?? function and/or its use needs revising
    //?? probably move it out to DataAccessPack and call during initialization
    protected void setOrCreateWsResultObjectIfNull (WsResultType resultRecord) throws Exception {
        //giving possibility for operations over resultRecord preparation to be handled in successor
        if (args.getResultRecord() == null) {
            if (resultRecord == null) {
                resultRecord = BbWsUtil.newClassInstanceWithThrow(args.getWsResultClass());
                resultRecord.initialize(args);
                args.setResultRecord(resultRecord);
                setResultRecordIds();
            } else args.setResultRecord(resultRecord);
            resultRecord.setBbWsBoolResult(null);
            resultRecord.setBbWsTextResult(null);
            resultRecord.setBbWsDataLog(new java.util.ArrayList<BbWsDataLogRecord>());
        }
    }

    public static class BbWsDataAccessPackWithSameInputResultType
            <ArgumentsType extends BbWsArguments<WsObjectType, WsObjectType>,
            BbObjectType extends Identifiable,
            WsObjectType extends BbWsDataDetails>
            extends BbWsDataAccessPack<ArgumentsType, BbObjectType, WsObjectType, WsObjectType>   {

        public class RecordLoader extends RecordAccessor {
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                //BbObjectType bbo
                //bbObject = loadRecord();
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.CUSTOM) == 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);
                loadRecord();
                setWsFields();
                getArgs().getResultRecord().incApiPassedCount();
                //??getArgs().getResultRecord().setTrueResult();
            }
        }

        public class RecordInserter extends RecordAccessor {
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                //BbWsBoolResult br = args.getRecordResult();
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);
                //reset BbId of result record. If another record was used by client as a template, 
                //and BbId remained set to this old record (client side flexibility - BbId value is ignored by insert),
                //then returned result with set BbId is confusing
                //!!Thing if in any other methods this should be considered
                getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag());
                if (bbObject == null) bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
                setBbFields();
                //may be check on supplied Id == missFieldTag should be performed... 
                //or may be this restriction is not necessary and will only complicate client side
                bbObject.setId(Id.UNSET_ID); //otherwise INSERT may behave as UPDATE if Bb API method used in insertRecord() is something like persist()
                insertRecord();
                getArgs().getResultRecord().incApiPassedCount();
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.NONE) > 0) {
                    getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag()); //?? it is not necessary here now (was added above)
                    loadRecordByAltId();
                    setWsFields();
                };
                //??getArgs().getResultRecord().setTrueResult();
                //appendBoolResultDataLogFromInputRecord();
            }
        }

        public class RecordUpdater extends RecordAccessor {
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);

                if (bbObject == null) {
                    loadRecord();
                }

                setBbFields();
                updateRecord();
                getArgs().getResultRecord().incApiPassedCount();
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.NONE) > 0) {
                    loadRecord();
                    setWsFields();
                }
                //??getArgs().getResultRecord().setTrueResult();
            }
        }

        public class RecordPersister extends RecordAccessor {
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);

                bbObject = null;
                try {
                    loadRecord();
                } catch (blackboard.persist.KeyNotFoundException kfe) {
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "access() caught blackboard.persist.KeyNotFoundException thrown by loadRecord() => record does not exist, choosing insert. ", this);
                }
                if (bbObject == null) {
                    RecordInserter ri = new RecordInserter();
                    ri.initialize(null);
                    ri.accessRecord();
                } else {
                    RecordUpdater ru = new RecordUpdater();
                    ru.initialize(null);
                    ru.accessRecord();
                }
            }
        }
        public class RecordDeleter extends RecordAccessor {
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);
                
                deleteRecord();
                //??getArgs().getResultRecord().setTrueResult();
            }
        }

    }


 
    public class RecordListLoader extends DataAccessor  {

        public class RecordListLoader_EHandler extends DataAccessor  {
            public void access() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered access(); ", this);
                try {
                    nextDataAccessor.access();

                } catch(Exception e) {  //?? may be excessive when DataAccessor is RecordAccessor which catches too - needs review 
                    if (getArgs().getDataRecordErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0 ) {
                        throw e;
                    }
                    BbWsLog.logForward(LogService.Verbosity.INFORMATION, e, "Handling exception in access(). ", this);
                    if (getArgs().getResultRecord() == null) {
                        BbWsLog.logForward(LogService.Verbosity.WARNING, "access():  getArgs().getResultRecord() == null", this);
                        setOrCreateWsResultObjectIfNull(null);
                    }
                    getArgs().getResultRecord().setFalseResult();
                    String msg = BbWsUtil.constructExceptionMessage(e);
                    getArgs().getResultRecord().setBbWsTextResult(msg);
                    putMissingRecordIntoLHM();
                }
            }
        }

        private class RecordListLoader_MainBody extends DataAccessor  {
            public void access() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered access(); ", this);
                setOrCreateWsResultObjectIfNull(null);
                if (getArgs().getResultLHM() == null) {
                    LinkedHashMap<String, WsResultType> lhm = new LinkedHashMap<String, WsResultType>();
                    getArgs().setResultLHM(lhm);
                }
                loadList();
                if(bbObjectList.size() < 1) {
                    throw new BbWsException("No items found.");
                }


                RecordListLoader_BbListIterator bbli = new RecordListLoader_BbListIterator();
                bbli.initialize(null);
                RecordListLoader_EHandler eh = new RecordListLoader_EHandler();
                eh.initialize(bbli);
                java.util.Iterator<BbObjectType> i = bbObjectList.iterator();
                while (i.hasNext()) {
                    bbObject = i.next();
                    eh.access();
                }
                
                RecordListLoader_WsLHMIterator wslhmi = new RecordListLoader_WsLHMIterator();
                wslhmi.initialize(null);
                eh.initialize(wslhmi);
                java.util.Iterator<WsResultType> lii = getArgs().getResultLHM().values().iterator();
                while (lii.hasNext()) {
                    WsResultType wso = lii.next();
                    getArgs().setResultRecord(wso);
                    eh.access();
                }
            }
        }
        private class RecordListLoader_BbListIterator extends DataAccessor  {
            public void access() throws Exception {
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered access(); ", this);
                        //IDs will be allways set by load, no problem that setOrCreateWsResultObjectIfNull sets it to something unknown
                        setOrCreateWsResultObjectIfNull(null);
                        //setWsFields(i.next());
                        //WsResultType wso;
                        //BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (!firstApiPass) { firstApiPass: " + firstApiPass, this);
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (getArgs().getApiPassedCount() > 0) {...; getArgs().getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                        //if (!firstApiPass) {
                        if (getArgs().getApiPassedCount() > 0) {
                            WsResultType wso = getArgs().getResultLHM().get(bbObject.getId().toExternalString());
                            if (wso == null) {
                                wso = getArgs().getResultRecord();
                                addDataLog(wso, BbWsArguments.DataLogSeverity.WARN,
                                        null, null, null, null,
                                        "Record was missing in previously passed APIs", null);
                            } else {
                                if (getArgs().getResultRecord() != null) wso.getBbWsDataLog().addAll(
                                            getArgs().getResultRecord().getBbWsDataLog());
                                getArgs().setResultRecord(wso);
                            }
                        }
                        setWsFields();
                        getArgs().getResultRecord().incApiPassedCount();
                        getArgs().getResultRecord().setTrueResult();
                        BbWsLog.logForward(LogService.Verbosity.DEBUG, "args.getResultLHM().put(args.getResultRecord().getId(), args.getResultRecord()); record.getBbId(): " + getArgs().getResultRecord().getBbId(), this);
                        getArgs().getResultLHM().put(getArgs().getResultRecord().getBbId(), getArgs().getResultRecord());
                        getArgs().setResultRecord(null);
                }
        }
        private class RecordListLoader_WsLHMIterator extends DataAccessor  {
            public void access() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered access(); ", this);
                WsResultType wso = getArgs().getResultRecord();
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (li.getApiPassedCount() < params.getApiPassedCount()+1) {; li.getBbId(): " + wso.getBbId() + "; li.getApiPassedCount(): " + wso.getApiPassedCount()
                        + "; params.getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                if (wso.getApiPassedCount() <= getArgs().getApiPassedCount()) {
                    wso.incApiPassedCount();
                    addDataLog(wso, BbWsArguments.DataLogSeverity.WARN,
                            null, null, null, null,
                            "Record is missing in currently passed API", null);
                }
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "li.getApiPassedCount(): " + wso.getApiPassedCount()
                        + "; params.getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                if (wso.getApiPassedCount() != getArgs().getApiPassedCount()+1) {
                    throw new BbWsException ("ASSERT: wso.getApiPassedCount() != params.getApiPassedCount+1");
                }
            }
        }
        public void access() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered access(); ", this);
            RecordListLoader_MainBody bbll = new RecordListLoader_MainBody();
            bbll.initialize(null);
            RecordListLoader_EHandler eh = new RecordListLoader_EHandler();
            eh.initialize(bbll);
            eh.access();


            /*WsResultType wso = null;
            setOrCreateWsResultObjectIfNull(null);
            if (getArgs().getResultLHM() == null) {
                LinkedHashMap<String, WsResultType> lhm = new LinkedHashMap<String, WsResultType>();
                getArgs().setResultLHM(lhm);
            }

            loadList();
            //bbListLoader.access();
            if(bbObjectList.size()<1)
            {
                throw new BbWsException("No items found.");
            }
            java.util.Iterator<BbObjectType> i = bbObjectList.iterator();
            while (i.hasNext())
            {
                try {
                    //IDs will be allways set by load, no problem that setOrCreateWsResultObjectIfNull sets it to something unknown
                    setOrCreateWsResultObjectIfNull(null);
                    bbObject = i.next();
                    //setWsFields(i.next());
                    //WsResultType wso;
                    //BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (!firstApiPass) { firstApiPass: " + firstApiPass, this);
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (getArgs().getApiPassedCount() > 0) {...; getArgs().getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                    //if (!firstApiPass) {
                    if (getArgs().getApiPassedCount() > 0) {
                        wso = getArgs().getResultLHM().get(bbObject.getId().toExternalString());
                        if (wso == null) {
                            wso = getArgs().getResultRecord();
                            addDataLog(wso, BbWsArguments.DataLogSeverity.WARN,
                                    "", "",
                                    "Record was missing in previously passed APIs", null);
                        } else {
                            if (getArgs().getResultRecord() != null) wso.getBbWsDataLog().addAll(
                                        getArgs().getResultRecord().getBbWsDataLog());
                            getArgs().setResultRecord(wso);
                        }
                    }
                    setWsFields();
                    getArgs().getResultRecord().incApiPassedCount();
                    getArgs().getResultRecord().setTrueResult();
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "args.getResultLHM().put(args.getResultRecord().getId(), args.getResultRecord()); record.getId(): " + getArgs().getResultRecord().getId(), this);
                } catch (Exception e) {
                    if (getArgs().getDataRecordErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0 ) {
                        //just rethrow it, it will be logged by DataAccessPack.execute()
                        throw e;
                    }
                    getArgs().getResultRecord().setFalseResult();
                    String msg = BbWsUtil.constructExceptionMessage(e);
                    getArgs().getResultRecord().setBbWsTextResult(msg);
                }
                getArgs().getResultLHM().put(getArgs().getResultRecord().getId(), getArgs().getResultRecord());
                getArgs().setResultRecord(null);
            }
            //or you will get an out of memory error...
            bbObjectList.clear();
            bbObjectList = null;

            java.util.Iterator<WsResultType> lii = getArgs().getResultLHM().values().iterator();
            while (lii.hasNext())
            {
                wso = lii.next();
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "if (li.getApiPassedCount() < params.getApiPassedCount()+1) {; li.getId(): " + wso.getId() + "; li.getApiPassedCount(): " + wso.getApiPassedCount()
                        + "; params.getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                if (wso.getApiPassedCount() < getArgs().getApiPassedCount()) {
                    wso.incApiPassedCount();
                    addDataLog(wso, BbWsArguments.DataLogSeverity.WARN,
                            "", "", "Record is missing in currently passed API", null);
                }
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "li.getApiPassedCount(): " + wso.getApiPassedCount()
                        + "; params.getApiPassedCount(): " + getArgs().getApiPassedCount(), this);
                if (wso.getApiPassedCount() != getArgs().getApiPassedCount()+1) {
                    throw new BbWsException ("ASSERT: wso.getApiPassedCount() != params.getApiPassedCount+1");
                }
            }*/
        }
    }

    public abstract class WsFieldSetter  {
        protected abstract void setWsFieldImp(String newValue) throws Exception;
        protected abstract String getWsFieldValue() throws Exception;;
        protected abstract String getBbFieldValue() throws Exception;
        protected boolean checkByNewValueCompareWithOld(String oldValue, String newValue) throws Exception {
            return BbWsUtil.nullSafeStringComparator(oldValue, newValue) == 0;
        }
        public void setWsField(String fieldName) throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered setWsField(); fieldName: " + fieldName, this);
            String bb_value = null;
            String ws_value = null;
            String value = null;
            try {
                ws_value = getWsFieldValue();
                //setWsField works a bit different from setBbField when it comes to MissFieldTag
                //as long as MissFieldTag defaults to null, which is an easiest value to be set by default
                //in WS input parameters it is considered only for DataVerbosity.CUSTOM
                //Otherwise client would need more work to do with initializing of input parameters.
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.CUSTOM) == 0
                        && BbWsUtil.nullSafeStringComparator(ws_value, getArgs().getMissFieldTag()) == 0
                        //!! all possible IDs have to be handled here - those that are set with setResultRecordIds()
                        && fieldName.compareTo("bbId") != 0) {
                    addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.INFO,
                            fieldName, value, bb_value, ws_value, "setWsField(): Field is omitted - dataVerbosity is set to CUSTOM and ws_value is set to missFieldTag.", null);
                    return;
                }
                //curValue = setWsFieldImp();
                bb_value = getBbFieldValue();
                if (bb_value == null) value = getArgs().getNullValueTag();
                else value = bb_value;
                if (getArgs().getResultRecord().getApiPassedCount() > 0 
                        && BbWsUtil.nullSafeStringComparator(ws_value, getArgs().getMissFieldTag()) != 0) {  //if we are comparing data reloaded after saving and dataVerbosity != CUSTOM - !! add this as check in code
                    if (!checkByNewValueCompareWithOld(ws_value, value)) {
                        value = getArgs().getErrorValueTag(); //!! quick fix - when error is thrown from here, but not thrown on record level, returned value is not set to BbWsError 
                        setWsFieldImp(value); //!!
                        String message = "setWsField(): APIs returned different values for " + fieldName + "; bb_value: " + bb_value + ";ws_value: " + ws_value;
                        addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.ERROR,
                            fieldName, value, bb_value, ws_value, message, null);
                        value = getArgs().getErrorValueTag();
                        /*(if (getArgs().getDataFieldErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0) {
                            throw new BbWsException(message);
                        }*/
                    }
                }
                setWsFieldImp(value);
            } catch (Exception e) {
                if (e instanceof BbWsDataLogException) throw e;
                String message = "setWsField(): " + e.toString();
                value = getArgs().getErrorValueTag();
                addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.ERROR,
                    fieldName, value, bb_value, ws_value, message, e);
                setWsFieldImp(value);
                /*if (getArgs().getDataFieldErrorThrowLevel().compareTo(BbWsArguments.DataLogSeverity.ERROR) <= 0) {
                    throw new BbWsException(message, e);
                }*/
            }
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.DEBUG,
                fieldName, value, bb_value, ws_value, 
                "setWsField(): Processed.", null);
        }
    }


    public abstract class BbFieldSetter  {
        protected abstract void setBbFieldImp(String value) throws Exception;
        protected abstract String getBbFieldValue() throws Exception;
        protected abstract String getWsFieldValue() throws Exception;
        
        protected void checkInputValue(String value) {};
        public void setBbField(String fieldName) throws Exception {
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "Entered setBbField(); fieldName: " + fieldName, this);
            String bb_value = null;            
            String ws_value = null;
            String value = null;            
            try {
                ws_value = getWsFieldValue();
                if (BbWsUtil.nullSafeStringComparator(ws_value, getArgs().getMissFieldTag()) == 0) {
                    addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.INFO,
                        fieldName, value, bb_value, ws_value, "setBbField(): Field is omitted - ws_value is set to missFieldTag.", null);
                    return;
                }
                bb_value = getBbFieldValue();
                ws_value = ws_value.trim();
                value = ws_value;
                if (BbWsUtil.nullSafeStringComparator(ws_value, getArgs().getNullValueTag()) == 0) value = null;
                checkInputValue(value);
                //try {
                    setBbFieldImp(value);
                //} catch (java.lang.IllegalArgumentException iae) {
                //!!add custom error message with possible field values.

            } catch (Exception e) {
                addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.ERROR,
                    fieldName, value, bb_value, ws_value, "setBbField(): " + e.toString(), e);
            }
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.DEBUG,
                fieldName, value, bb_value, ws_value, "setBbField(): Processed.", null);
        }
    }
}




