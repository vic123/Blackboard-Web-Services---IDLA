using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

using System.Collections.Generic;

public partial class BbWsTest : System.Web.UI.Page
{
    protected virtual void RunTest() {
        try {

            testArgs = new TestArgsStruct();
            /*RunUserTest();
            RunCourseMembershipTest();
            RunPortalRoleTest();
            RunObserverAssociationTest();*/
            RunCourseTest();

        } catch (Exception e1) {
            Response.Output.Write(e1.ToString());
        }
    }

    class TestArgsStruct {
        public _userTestArgs user = null;
        public _courseMembershipTestArgs courseMembership = null;
        public _courseTestArgs course = null;
        public _portalRoleTestArgs portalRole = null;
        public _observerAssociationTestArgs observerAssociation = null;
        public _groupTestArgs group = null;
        
        public TestArgsStruct() {
            user = new _userTestArgs();
            user.init(this);
            courseMembership = new _courseMembershipTestArgs();
            courseMembership.init(this);
            course = new _courseTestArgs();
            course.init(this);
            portalRole = new _portalRoleTestArgs();
            portalRole.init(this);
            observerAssociation = new _observerAssociationTestArgs();
            observerAssociation.init(this);
            group = new _groupTestArgs();
            group.init(this);
        }
        public void ClearAllTestData() {
            user.ClearTestData();
            courseMembership.ClearTestData();
            //course.ClearTestData();
            portalRole.ClearTestData();
            observerAssociation.ClearTestData();
            //group.ClearTestData();
        }
        public void ClearAllInputsAndResults() {
            user.ClearInputsAndResults();
            user.currentTestKeySuffix = user.testKeySuffixes[0];

            courseMembership.ClearInputsAndResults();
            courseMembership.currentTestKeySuffix = courseMembership.testKeySuffixes[0];

            course.ClearInputsAndResults();
            course.currentTestKeySuffix = course.testKeySuffixes[0];
             
            portalRole.ClearInputsAndResults();
            portalRole.currentTestKeySuffix = portalRole.testKeySuffixes[0];

            observerAssociation.ClearInputsAndResults();
            observerAssociation.currentTestKeySuffix = observerAssociation.testKeySuffixes[0];

            group.ClearInputsAndResults();
            group.currentTestKeySuffix = group.testKeySuffixes[0];

        }
    }
    TestArgsStruct testArgs;


    public delegate void TACall();

    interface ITestAction {
        void preAction();
        void postAction();
        //void errorHandleAction();
        void SetException(Exception e);
        //void executeMain();
        void execute();
        void executeImp();
        void SafePostAction();
        void PreActionAndExecuteImp();
        void init(Object args);
    }

    //class TestArgs<TestActionType> where TestActionType : TestAction<TestArgs<TestActionType>> {
    abstract class TestArgs<WsDataType> where WsDataType : new() {
        public String baseUserName = "student_001";
        public String currentTestKeySuffix;
        public String[] testKeySuffixes;

        //protected abstract void LoadRecordInitial();
        /*
        protected abstract void LoadRecord_1();
        protected abstract void LoadRecord_2();
        protected abstract void LoadRecord_3();
        protected abstract void LoadRecord_12();
        protected abstract void LoadRecord_123();
        */


        public bbws.BbWebservices bbWs = new bbws.BbWebservices();
        public bbcrsws.BBCourseWebService bbCrsWs = new bbcrsws.BBCourseWebService();
        public bbgrpws.BBGroupWebServiceService bbGrpWs = new bbgrpws.BBGroupWebServiceService();
        
        
        public bbws.bbWsParams param = new bbws.bbWsParams();
        public WsDataType wsInputRecord = new WsDataType();
        public List<WsDataType> wsInputList = new List<WsDataType>();
        public WsDataType wsResultRecord = new WsDataType();//default(WsDataType);
        public WsDataType[] wsResultList = null; //new List<WsDataType>();
        public HttpResponse response;
        public String resultString = "";
        public TestArgs() {
            HttpContext context = HttpContext.Current;
            this.response = context.Response;
            //param.dataLogSeverity = "DEBUG";
            //param.dataLogSeverity = "INFO";
            param.dataLogSeverity = "WARN";
            //param.dataVerbosity = "NONE";
            //param.dataVerbosity = "STANDARD";
            param.dataVerbosity = "EXTENDED";

            param.dataRecordErrorThrowSeverity = "FATAL";
            param.dataFieldErrorThrowSeverity = "FATAL";
            //param.dataFieldErrorThrowSeverity = "ERROR";

            testKeySuffixes = new String[3];
            testKeySuffixes[0] = "_bbws_01";
            testKeySuffixes[1] = "_bbws_02";
            testKeySuffixes[2] = "_bbws_03";
            currentTestKeySuffix = testKeySuffixes[0];

        }
        public TestArgsStruct testArgs;
        public virtual void init(TestArgsStruct testArgs) {
            this.testArgs = testArgs;
        }
        public abstract bbws.bbWsDataLogRecord[] getDataLogArray();
        public abstract String getBbWsBoolResult();
        public abstract String getBbWsRecordResultId();
        public abstract void SetFieldsMinimal();
        public abstract void SetFieldsExtended();
        public abstract void UpdateFieldsMinimal();

        public virtual void ClearTestData() {
            foreach (String key_suff in testKeySuffixes) {
                currentTestKeySuffix = key_suff;
                insertRecordAction.preAction();
                insertRecordAction.SafePostAction();
            }
        }




        /*public TestAction<TestArgs<WsDataType>, WsDataType> loadBaseRecordAction;
        public TestAction<TestArgs<WsDataType>, WsDataType> loadInsertedRecordAction;
        public TestAction<TestArgs<WsDataType>, WsDataType> insertRecordAction;
        public TestAction<TestArgs<WsDataType>, WsDataType> deleteRecordAction;*/

        public ITestAction loadBaseRecordAction;
        public ITestAction loadInsertedRecordAction;
        public ITestAction insertRecordAction;
        public ITestAction deleteRecordAction;

        public virtual void ClearResults() {
            wsResultList = (WsDataType[])null;
            wsResultRecord = default(WsDataType);
        }
        public virtual void CreateInputRecord() {
            wsInputRecord = new WsDataType();
        }

        public virtual void MoveInputRecordToList() {
            wsInputList.Add(wsInputRecord);
            CreateInputRecord();
        }
        public virtual void ClearInputs() {
            wsInputList.Clear();
            CreateInputRecord();
        }
        public virtual void ClearInputsAndResults() {
            ClearInputs();
            ClearResults();
        }


    }

    //***************************************************************************************    
    class TestAction<TestArgsType, WsDataType> : ITestAction
        where TestArgsType : TestArgs<WsDataType>
        where WsDataType : new() {
        protected TestArgsType args;
        protected Exception exception;
        //C# events vs. delegates
        //http://blog.monstuff.com/archives/000040.html
        public event TACall executeEvent;// = null;
        public event TACall preActionEvent = null;
        public event TACall postActionEvent = null;
        public event TACall errorHandleEvent = null;
        public event TACall checkResultsEvent = null;

        public virtual void ShowClassName() {
            args.response.Output.Write("<b>" + this.GetType().ToString() + "</b><br>");
        }
        public virtual void ShowException() {
            args.response.Output.Write(exception.ToString());
        }
        public virtual void SafePostAction() {
            try {
                postAction();
            } catch (Exception e) {
                args.response.Output.Write(GetType().ToString() + " SafePostAction warning: " + e.ToString());
            }
        }
        public virtual void ShowResultListTableAndDataLog() {
            ShowResultListTableAndDataLogAction<WsDataType> show_act
                    = new ShowResultListTableAndDataLogAction<WsDataType>();
            show_act.init(args);
            show_act.execute();
        }

        public void InsertListInitAndExecute() {
            foreach (String key_suff in args.testKeySuffixes) {
                args.currentTestKeySuffix = key_suff;
                args.insertRecordAction.PreActionAndExecuteImp();
                args.insertRecordAction.executeImp();
            }
        }
        public void insertListInit() {
            foreach (String key_suff in args.testKeySuffixes) {
                args.currentTestKeySuffix = key_suff;
                args.insertRecordAction.preAction();
                args.MoveInputRecordToList();
            }
        }


        public virtual void preAction() { }
        public virtual void postAction() { }
        public virtual void executeImp() { }
        public virtual void checkResultsImp() { }
        public void PreActionAndExecuteImp() {
            preAction();
            executeImp();
        }
        public virtual void CheckResults() {
            try {
                checkResultsEvent();
                checkResultsImp();
            } catch (Exception e) {
                args.response.Output.Write("<b>Test Case FAILED: </b> " + e.ToString() + "");
                return;
            }
            args.response.Output.Write("<b>Test Case SUCCESS</b>");
        }
        public virtual void checkRecordSuccessResult() {
            if (args.getBbWsBoolResult().CompareTo("true") != 0)
                throw new Exception("args.getBbWsBoolResult().CompareTo(\"true\") != 0");
        }

        public virtual void checkListSuccessResult() {
            if (args.wsResultList == null) {
                throw new Exception("args.wsResultList == null" );
            }
            foreach (WsDataType res in args.wsResultList) {
                args.wsResultRecord = res;
                if (args.getBbWsBoolResult().CompareTo("true") != 0)
                    throw new Exception("res.bbWsBoolResult.CompareTo(\"true\") != 0; res.ge" + args.getBbWsRecordResultId());
            }
        }


        //public virtual void errorHandleAction() { }

        public virtual void SetException(Exception e) {
            exception = e;
        }

        virtual public void execute() {
            try {
                if (preActionEvent != null) preActionEvent();
                preAction();
                if (executeEvent != null) executeEvent();
                executeImp();
                
                if (postActionEvent != null) postActionEvent();
                SafePostAction();
            } catch (Exception e) {
                this.exception = e;
                if (errorHandleEvent != null) errorHandleEvent();
                else throw;
            }

        }
        virtual public void init(Object args) {
            this.args = (TestArgsType)args;
        }
    }
    class TestOperation<WsDataType> : TestAction<TestArgs<WsDataType>, WsDataType>
        where WsDataType : new() {
        override public void init(Object args) {
            base.init(args);
            preActionEvent += ShowClassName;
        }
    }
    class TestCase<TestArgsType, WsDataType> : TestAction<TestArgsType, WsDataType>
        where WsDataType : new()
        where TestArgsType : TestArgs<WsDataType> {
        override public void init(Object args) {
            base.init(args);
            preActionEvent += ShowClassName;
            //preActionEvent += this.args.ClearInputsAndResults;
            preActionEvent += this.args.testArgs.ClearAllInputsAndResults;
            postActionEvent += CheckResults;
            postActionEvent += ShowResultListTableAndDataLog;
            errorHandleEvent += CheckResults;
            errorHandleEvent += ShowException;
            errorHandleEvent += SafePostAction;
        }
    }

    class TestCase_SuccessRecord<TestArgsType, WsDataType> : TestCase<TestArgsType, WsDataType> 
        where WsDataType : new()
        where TestArgsType : TestArgs<WsDataType> {
        override public void init(Object args) {
            base.init(args);
            checkResultsEvent += checkRecordSuccessResult;
        }
    }
    class TestCase_SuccessList<TestArgsType, WsDataType> : TestCase<TestArgsType, WsDataType> 
        where WsDataType : new()
        where TestArgsType : TestArgs<WsDataType> {
        override public void init(Object args) {
            base.init(args);
            checkResultsEvent += checkListSuccessResult;
        }
    }


    //************************************************************************************************************


    class ShowResultListTableAction<WsDataType> : TestAction<TestArgs<WsDataType>, WsDataType>, ITestAction
        where WsDataType : new() {
        override public void execute() {
            //if (!args.wsResultArray.Contains(args.wsResultRecord)) args.wsResultArray.Insert(0, args.wsResultRecord); 
            //either wsResultRecord or args.wsResultList has to be set
            List<WsDataType> res_list = new List<WsDataType>();
            if (args.wsResultList != null) res_list = new List<WsDataType>(args.wsResultList);
            if (args.wsResultRecord != null) {
                if (!res_list.Contains(args.wsResultRecord)) res_list.Insert(0, args.wsResultRecord);
            }
            if (args.wsResultRecord == null) args.wsResultRecord = args.wsResultList[0];
            Type wsDataType = args.wsResultRecord.GetType();
            System.Reflection.PropertyInfo[] props = wsDataType.GetProperties();
            args.resultString += "<table border=\"1\" cellpadding=\"1\" cellspacing=\"0\">";
            args.resultString += "<tr>";
            foreach (System.Reflection.PropertyInfo prop in props) {
                args.resultString += "<td>" + prop.Name + "</td>";
            }
            args.response.Write("</tr>");

            foreach (WsDataType ws_data in res_list) {
                args.wsResultRecord = ws_data;
                args.resultString += "<tr>";
                foreach (System.Reflection.PropertyInfo prop in props) {
                    //http://stackoverflow.com/questions/937224/propertyinfo-getvalue-how-do-you-index-into-a-generic-parameter-using-reflect
                    Object value = prop.GetValue(ws_data, null);
                    args.resultString += "<td>" + value + "</td>";
                }
                args.resultString += "</tr>";
            }
            args.resultString += "</table>";
            args.response.Write(args.resultString);
            args.resultString = "";
        }
    }

    class ShowResultListTableAndDataLogAction<WsDataType> : ShowResultListTableAction<WsDataType>, ITestAction
        where WsDataType : new() {
        override public void execute() {
            base.execute();
            ResultListDatalogTableShow<WsDataType> dl_ts = new ResultListDatalogTableShow<WsDataType>();
            dl_ts.init(args);
            dl_ts.execute();
        }
    }

    class ResultListIterationAction<WsDataType> : TestAction<TestArgs<WsDataType>, WsDataType>, ITestAction
        where WsDataType : new() {
        override public void execute() {
            List<WsDataType> res_list = new List<WsDataType>();
            if (args.wsResultList != null) res_list = new List<WsDataType>(args.wsResultList);
            if (!res_list.Contains(args.wsResultRecord)) res_list.Insert(0, args.wsResultRecord);
            foreach (WsDataType ws_data in res_list) {
                args.wsResultRecord = ws_data;
                base.execute();
            }
        }
    }
    class ResultListDatalogTableShow<WsDataType> : ShowResultListTableAction<WsDataType>, ITestAction
        where WsDataType : new() {
        override public void execute() {
            ResultListIterationAction<WsDataType> rl_iter = new ResultListIterationAction<WsDataType>();
            rl_iter.init(args);
            rl_iter.executeEvent += datalogTableShow;
            rl_iter.execute();
        }
        public void datalogTableShow() {
            _datalogTestArgs dl_args = new _datalogTestArgs();
            dl_args.wsResultList = args.getDataLogArray();
            if (dl_args.wsResultList == null || dl_args.wsResultList.GetLength(0) == 0) return;
            //dl_args.wsResultArray = args.wsResultRecord.bbWsDataLog;
            ShowResultListTableAction<bbws.bbWsDataLogRecord> datalogTableShow
                    = new ShowResultListTableAction<bbws.bbWsDataLogRecord>();
            datalogTableShow.init(dl_args);
            datalogTableShow.execute();
        }
    }

    class _datalogTestArgs : TestArgs<bbws.bbWsDataLogRecord> {
        public override bbws.bbWsDataLogRecord[] getDataLogArray() {
            throw new Exception("Unsupposed to be used");
        }

        public override String getBbWsBoolResult() {
            throw new Exception("Unsupposed to be used");
        }
        public override String getBbWsRecordResultId() {
            throw new Exception("Unsupposed to be used");
        }
        public override void SetFieldsMinimal() { }
        public override void SetFieldsExtended() { }
        public override void UpdateFieldsMinimal() {
        }

    }

}
