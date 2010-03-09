/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbwscommon;
import blackboard.platform.log.*;
import java.util.*;
import javax.xml.ws.WebServiceException;
/**
 *
 * @author vic
 */
public class BbWsApiProcessor<ArgumentsType extends BbWsArguments> {
    private ArgumentsType args;
    private WebServiceProperties wsp = new WebServiceProperties("IDLA","BbWebservices");
    //private Class<ArgumentsType> argumentsClass;
    //private ProcessingKind processingKind;
    //public static enum ProcessingKind {LOAD, PERSIST, DELETE};

    //public void initialize(Class<ArgumentsType> ArgumentsClass, ArgumentsType args, ProcessingKind processingKind) {
    //public void initialize(Class<ArgumentsType> argumentsClass, ArgumentsType args) {
    static public void start(BbWsArguments args) throws BbWsFault{
        BbWsApiProcessor api_proc = new BbWsApiProcessor();
        api_proc.initialize(args);
        api_proc.run();
    }

    public void initialize(ArgumentsType args) {
        this.args = args;
        //this.argumentsClass = argumentsClass;
        //this.processingKind = processingKind;
        //??initializeResult();
    }
    //??protected abstract void initializeResult();

    //public void run() throws Exception {//WebServiceException {
    public void run() throws WebServiceException, BbWsFault {
        try {
            //!!authoriseMethodUse(args.getPassword(), args.getWebMethodName());
            if(wsp.usingPassword() && !wsp.passwordMatches(args.getPassword())) {
                throw new WebServiceException("Access Denied");
            }

            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BbWsDataAccessProcessor.run(); ", this);
            BbWsLog.logForward(LogService.Verbosity.DEBUG, "for (int i = 0; i < CommonParams.apiToUseTags.length; i++) { args.getDataLogSeverity(): " + args.getDataLogSeverity() , this);

            //??may be an array of predefined APIs to search through is more security ensured,
            //but in my current point of view does not see it to be possible to load class from some other place than
            //project's package....
            //for (int i = 0; i < args.apiToUseTags.length; i++) {
            //    if (params.apiToUse.indexOf(CommonParams.apiToUseTags[i]) != -1) {
            //!!and here is more approach
            //google:java dynamic class load parameter security
            for (int i = 0; i < args.getApiToUseList().size(); i++ ) {
                    /*
                    List<String> l = args.getApiToUseList();
                    String api = l.get(i);
                    //!!?? - incompatible types - getApiToUseList() is List<String>
                    //String api1 = args.getApiToUseList().get(i);
                    */
                    //tried to add <ApiToUseType extends BbWsArguments.ApiToUse> to BbWsArguments, but could not access ApiToUseType.DATA_GB

                    BbWsArguments.ApiToUse api = (BbWsArguments.ApiToUse) args.getApiToUseList().get(i);

                    BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Loading class for API "
                            + api
                            + "; this.getClass().getSimpleName(): " + this.getClass().getSimpleName()
                            + "; args.getProcessingKind(): " + args.getProcessingKind()
                            + "; args.getDataAccesserStartMethodName(): " + args.getInnerDAPDefaultClassName(), this);
                    //String c_name = args.getDataAccessPackClassName() + "_" + api;
                    String api_class2load = args.getInnerDAPDefaultClassName();
                    if (api.innerDAPClassNameOverride != null) api_class2load = api.innerDAPClassNameOverride;
                    String c_name = args.getDataAccessPackClassName() + "_" + api.api + "$" + api_class2load;
                    args.setCurrentPassApi(api);
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "Class data_loader_class = Class.forName(c_name); c_name: " + c_name, this);
                    Class data_loader_class = Class.forName(c_name);
                    
                    //BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method = data_loader_class.getDeclaredMethod...; args.getDataAccesserStartMethodName(): " + args.getDataAccesserStartMethodName(), this);
                    //java.lang.reflect.Method data_loader_method = data_loader_class.getDeclaredMethod(args.getDataAccesserStartMethodName(), args.getClass());
                    Object dap = BbWsUtil.newClassInstanceWithThrow(data_loader_class);
                    //java.lang.reflect.Constructor data_loader_constructor = data_loader_class.getConstructor();

                    //java.lang.reflect.Method data_loader_method = data_loader_class.getDeclaredMethod("initialize", args.getClass());
                    java.lang.reflect.Method data_loader_method = data_loader_class.getMethod("initialize", args.getClass());
                    //result = (ResultType) data_loader_method.invoke(null, params);
//                    Class<ResultType> c_result = data_loader_method.getReturnType();
//                    return c_result.cast(data_loader_method.invoke(null, params));
//                    Object paramObjs[] = new Object[2];
//                    paramObjs[0] = params;
//                    paramObjs[1] = internalResult;
                    //BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method.invoke(null, args);", this);
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method.invoke(dap, args);", this);
                    //data_loader_method.invoke(null, args);
                    data_loader_method.invoke(dap, args);
                    java.lang.reflect.Method exec_method = data_loader_class.getMethod("execute");
                    exec_method.invoke(dap);

                    args.incApiPassedCount();
                    //BbWsLog.logForward(LogService.Verbosity.DEBUG, "data_loader_method.invoke(null, params, internalResult);", this);
                    //data_loader_method.invoke(null, params, internalResult);  //- works too
            }
            //result = (ResultType)internalResult;
        } catch (Throwable t) {
            //rethrowing exception class and message only for common security principles
            //it looks like only java.lang.reflect.InvocationTargetException with null message is generated here
            //it is logged anyway for any case
            BbWsLog.logForward(LogService.Verbosity.ERROR, t, "", this);
            if (t.getCause() != null) t = t.getCause();
            if (t instanceof BbWsFault) throw (BbWsFault)t;
            throw new WebServiceException(//!!??args.errorPrefix +
//??                    "Exception Message: " + t.getMessage() + "; Exception.toString(): " + t.toString() );
                t.toString() );
        }
    }

/*
    private class LineitemDetailsForGivenLineitemBbIdGetter
            extends DataGetter<LineitemDetails,
                               LineitemDetails, LineitemParams> {
        LineitemDetailsForGivenLineitemBbIdGetter (LineitemParams params) {
            super(params);
            internalResult = new LineitemDetails();
            //result = new java.util.LinkedHashMap<String, LineitemDetails>();
            errorPrefix = "Error: Could not retrieve line item, caused by: ";
            methodName = "getLineItemForGivenLineitemBbIdWithNamedElements";
            dataLoaderClassNamePrefix = "bbgbws.LineitemDetails$RecordLoader";
            dataLoaderStaticMethodName = "loadByGivenLineitemBbIdStarter";
            paramTypes = new Class[2];
            paramTypes[0] = LineitemParams.class;
            paramTypes[1] = LineitemDetails.class;
        }
    }
 *
 */

    static public abstract class DataLoader<DataDetailsType extends BbWsDataDetails, ParamsType extends BbWsParams> {
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
    }
}
