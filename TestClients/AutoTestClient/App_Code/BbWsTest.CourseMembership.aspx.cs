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
using bbIDLA.BBAddedService;
//!!using bbws;

public partial class BbWsTest : System.Web.UI.Page
{
    protected void RunCourseMembershipTest() {
            //!!testArgs.ClearAllTestData();
        
            testArgs.courseMembership.courseMembershipLoadRecordById.execute();
            testArgs.courseMembership.courseMembershipLoadListById.execute();
            testArgs.courseMembership.courseMembershipLoadRecordByCourseAndUserId.execute();
            testArgs.courseMembership.courseMembershipLoadListByUserId.execute();
            testArgs.courseMembership.courseMembershipLoadListByCourseId.execute();
            testArgs.courseMembership.courseMembershipLoadListByCourseIdWithUserInfo.execute();
            testArgs.courseMembership.courseMembershipLoadListByCourseIdAndRole.execute();
            testArgs.courseMembership.courseMembershipInsertRecordById_minimal.execute();
            testArgs.courseMembership.courseMembershipUpdateRecordById.execute();

            testArgs.courseMembership.courseMembershipUpdateRecordById_extended.execute();
        
            testArgs.courseMembership.courseMembershipPersistRecordById_insert.execute();
            testArgs.courseMembership.courseMembershipPersistRecordById_update.execute();
            testArgs.courseMembership.courseMembershipDeleteRecordById.execute();
            testArgs.courseMembership.courseMembershipInsertListById.execute();
            testArgs.courseMembership.courseMembershipUpdateListById.execute();
            testArgs.courseMembership.courseMembershipPersistListById.execute();
            testArgs.courseMembership.courseMembershipDeleteListById.execute();
            testArgs.courseMembership.courseMembershipLoadRecordByCourseAndUserBatchId.execute();
            testArgs.courseMembership.courseMembershipLoadListByTemplate.execute();
    }

    class _courseMembershipTestCase_RecordResult : TestCase_SuccessRecord<_courseMembershipTestArgs, courseMembershipDetails>, ITestAction { }
    class _courseMembershipTestCase_ListResult : TestCase_SuccessList<_courseMembershipTestArgs, courseMembershipDetails>, ITestAction { }

    class _courseMembershipTestArgs : TestArgs<courseMembershipDetails> {
        public override bbWsDataLogRecord[] getDataLogArray() {
            return wsResultRecord.bbWsDataLog;
        }
        public override String getBbWsBoolResult() {
            return wsResultRecord.bbWsBoolResult;
        }
        public override String getBbWsRecordResultId() {
            return wsResultRecord.bbId;
        }
        public override void SetFieldsMinimal() { }
        public override void SetFieldsExtended() {
            System.Reflection.PropertyInfo[] props = wsInputRecord.GetType().GetProperties();
            foreach (System.Reflection.PropertyInfo prop in props) {
                //wsResultRecord.
                Object value = prop.GetValue(wsInputRecord, null);
                String str_value = Convert.ToString(value);
                switch (prop.Name) {
                    case "bbWsDataLog":
                        continue;
                    case "bbId":
                        continue;
                    case "userBbId":
                        continue;
                    case "courseBbId":
                        continue;
                    case "role":
                        //INSTRUCTOR, TEACHING_ASSISTANT, COURSE_BUILDER, GRADER, STUDENT, GUEST, NONE, DEFAULT
                        str_value = "GRADER";
                        break;
                    case "available":
                        str_value = "false";
                        break;
                    case "cartridgeAccess":
                        str_value = "false";
                        break;
                    case "dataSourceBbId":
                        continue;
                    case "enrollmentDate":
                        str_value = wsResultRecord.enrollmentDate;
                        break;
                    case "modifiedDate":
                        continue;
                        //str_value = wsResultRecord.enrollmentDate;
                        //break;
                    case "lastAccessDate":
                        str_value = wsResultRecord.enrollmentDate;
                        break;
                    case "childCourseId":
                        str_value = wsResultRecord.courseBbId;
                        break;
                    default:
                        str_value = prop.Name + currentTestKeySuffix;
                        break;
                }
                prop.SetValue(wsInputRecord, str_value, null);
            }
        
        }
        public override void UpdateFieldsMinimal() { }



        public _courseMembershipLoadRecordById courseMembershipLoadRecordById;
        public _courseMembershipLoadListById courseMembershipLoadListById;
        public _courseMembershipLoadRecordByCourseAndUserId courseMembershipLoadRecordByCourseAndUserId;
        public _courseMembershipLoadListByUserId courseMembershipLoadListByUserId;
        public _courseMembershipLoadListByCourseId courseMembershipLoadListByCourseId;
        public _courseMembershipLoadListByCourseIdWithUserInfo courseMembershipLoadListByCourseIdWithUserInfo;
        public _courseMembershipLoadListByCourseIdAndRole courseMembershipLoadListByCourseIdAndRole;

        public _courseMembershipInsertRecordById_minimal courseMembershipInsertRecordById_minimal;

        public _courseMembershipUpdateRecordById courseMembershipUpdateRecordById;
        public _courseMembershipUpdateRecordById_extended courseMembershipUpdateRecordById_extended;
        

        //public _courseMembershipPersistRecordById courseMembershipPersistRecordById;
        public _courseMembershipPersistRecordById_insert courseMembershipPersistRecordById_insert;
        public _courseMembershipPersistRecordById_update courseMembershipPersistRecordById_update;

        public _courseMembershipDeleteRecordById courseMembershipDeleteRecordById;
        public _courseMembershipInsertListById courseMembershipInsertListById;
        public _courseMembershipUpdateListById courseMembershipUpdateListById;
        public _courseMembershipPersistListById courseMembershipPersistListById;
        public _courseMembershipDeleteListById courseMembershipDeleteListById;
        public _courseMembershipLoadRecordByCourseAndUserBatchId courseMembershipLoadRecordByCourseAndUserBatchId;
        public _courseMembershipLoadListByTemplate courseMembershipLoadListByTemplate;

        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            courseMembershipLoadRecordById = new _courseMembershipLoadRecordById();
            courseMembershipLoadRecordById.init(this.testArgs.courseMembership);
            courseMembershipLoadListById = new _courseMembershipLoadListById();
            courseMembershipLoadListById.init(this.testArgs.courseMembership);
            courseMembershipLoadRecordByCourseAndUserId = new _courseMembershipLoadRecordByCourseAndUserId();
            courseMembershipLoadRecordByCourseAndUserId.init(this.testArgs.courseMembership);
            courseMembershipLoadListByUserId = new _courseMembershipLoadListByUserId();
            courseMembershipLoadListByUserId.init(this.testArgs.courseMembership);
            courseMembershipLoadListByCourseId = new _courseMembershipLoadListByCourseId();
            courseMembershipLoadListByCourseId.init(this.testArgs.courseMembership);
            courseMembershipLoadListByCourseIdWithUserInfo = new _courseMembershipLoadListByCourseIdWithUserInfo();
            courseMembershipLoadListByCourseIdWithUserInfo.init(this.testArgs.courseMembership);
            courseMembershipLoadListByCourseIdAndRole = new _courseMembershipLoadListByCourseIdAndRole();
            courseMembershipLoadListByCourseIdAndRole.init(this.testArgs.courseMembership);
            courseMembershipInsertRecordById_minimal = new _courseMembershipInsertRecordById_minimal();
            courseMembershipInsertRecordById_minimal.init(this.testArgs.courseMembership);
            courseMembershipUpdateRecordById = new _courseMembershipUpdateRecordById();
            courseMembershipUpdateRecordById.init(this.testArgs.courseMembership);

            courseMembershipUpdateRecordById_extended = new _courseMembershipUpdateRecordById_extended();
            courseMembershipUpdateRecordById_extended.init(this.testArgs.courseMembership);

            courseMembershipPersistRecordById_insert = new _courseMembershipPersistRecordById_insert();
            courseMembershipPersistRecordById_insert.init(this.testArgs.courseMembership);
            courseMembershipPersistRecordById_update = new _courseMembershipPersistRecordById_update();
            courseMembershipPersistRecordById_update.init(this.testArgs.courseMembership);

            courseMembershipDeleteRecordById = new _courseMembershipDeleteRecordById();
            courseMembershipDeleteRecordById.init(this.testArgs.courseMembership);
            courseMembershipInsertListById = new _courseMembershipInsertListById();
            courseMembershipInsertListById.init(this.testArgs.courseMembership);
            courseMembershipUpdateListById = new _courseMembershipUpdateListById();
            courseMembershipUpdateListById.init(this.testArgs.courseMembership);
            courseMembershipPersistListById = new _courseMembershipPersistListById();
            courseMembershipPersistListById.init(this.testArgs.courseMembership);
            courseMembershipDeleteListById = new _courseMembershipDeleteListById();
            courseMembershipDeleteListById.init(this.testArgs.courseMembership);
            courseMembershipLoadRecordByCourseAndUserBatchId = new _courseMembershipLoadRecordByCourseAndUserBatchId();
            courseMembershipLoadRecordByCourseAndUserBatchId.init(this.testArgs.courseMembership);
            courseMembershipLoadListByTemplate = new _courseMembershipLoadListByTemplate();
            courseMembershipLoadListByTemplate.init(this.testArgs.courseMembership);

            loadBaseRecordAction = courseMembershipLoadRecordById;
            loadInsertedRecordAction = courseMembershipLoadRecordByCourseAndUserId;
            insertRecordAction = courseMembershipInsertRecordById_minimal;
            deleteRecordAction = courseMembershipDeleteRecordById;
        }
    }

    //************************************************************************************************************
    //******************************************************************************************************

    class _courseMembershipLoadRecordById : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.courseMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultList[0].bbId;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipLoadRecordById(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipLoadListById : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.preAction();
            args.MoveInputRecordToList();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _courseMembershipLoadRecordByCourseAndUserId : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
            args.wsInputRecord.userBbId = args.wsResultRecord.userBbId;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipLoadRecordByCourseAndUserId(args.param, args.wsInputRecord);
        }
    }

    class _courseMembershipLoadListByUserId : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.userBbId = args.testArgs.user.wsResultRecord.bbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListByUserId(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipLoadListByCourseId : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListByCourseId(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipLoadListByCourseIdWithUserInfo : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListByCourseIdWithUserInfo(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipLoadListByCourseIdAndRole : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
            //this is course role - CourseMembership.Role, not PortalRole or SystemRole
            args.wsInputRecord.role = args.wsResultRecord.role;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListByCourseIdAndRole(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipInsertRecordById_minimal : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.wsInputRecord.userBbId = args.testArgs.user.wsResultRecord.bbId;
            //causes error Field was provided, but is not accessible from DATA API (blackboard.data.CourseMebership)	
            args.wsInputRecord.userBatchUid = args.param.missFieldTag;
            args.wsInputRecord.courseBatchUid = args.param.missFieldTag;
            args.ClearResults();
        }
        override public void postAction() {
            args.testArgs.user.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipInsertRecordById(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipUpdateRecordById : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.currentTestKeySuffix = args.testArgs.user.testKeySuffixes[1];
            args.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.courseMembershipLoadRecordByCourseAndUserId.executeImp();
            args.wsInputRecord = args.wsResultRecord;
            args.wsInputRecord.userBbId = args.testArgs.user.wsResultRecord.bbId;
            //causes error Field was provided, but is not accessible from DATA API (blackboard.data.CourseMebership)	
            args.wsInputRecord.userBatchUid = args.param.missFieldTag;
            args.wsInputRecord.courseBatchUid = args.param.missFieldTag;
            args.SetStandardUnsetabbleFieldsToMissFieldTag();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipUpdateRecordById(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipUpdateRecordById_extended : _courseMembershipUpdateRecordById, ITestAction {
        override public void preAction() {
            base.preAction();
            ShowResultListTableAndDataLog();
            args.SetFieldsExtended();
            //causes error Field was provided, but is not accessible from DATA API (blackboard.data.CourseMebership)	
            args.wsInputRecord.userBatchUid = args.param.missFieldTag;
            args.wsInputRecord.courseBatchUid = args.param.missFieldTag;
            args.ClearResults();
        }
    }



    class _courseMembershipPersistRecordById : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipPersistRecordById(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipPersistRecordById_insert : _courseMembershipPersistRecordById, ITestAction {
        override public void preAction() {
            args.insertRecordAction.preAction();
        }
        override public void postAction() {
            args.courseMembershipInsertRecordById_minimal.postAction();
        }
    }
    class _courseMembershipPersistRecordById_update : _courseMembershipPersistRecordById, ITestAction {
        override public void preAction() {
            args.courseMembershipUpdateRecordById.preAction();
        }
        override public void postAction() {
            args.courseMembershipUpdateRecordById.postAction();
        }
    }

    class _courseMembershipDeleteRecordById : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.courseBbId = args.wsResultRecord.courseBbId;
            args.wsInputRecord.userBbId = args.wsResultRecord.userBbId;
            args.loadInsertedRecordAction.executeImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipDeleteRecordById(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipInsertListById : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipInsertListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _courseMembershipUpdateListById : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.courseMembershipUpdateRecordById.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.courseMembershipUpdateRecordById.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipUpdateListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _courseMembershipPersistListById : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
            args.courseMembershipUpdateRecordById.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.testArgs.user.currentTestKeySuffix = args.testArgs.user.testKeySuffixes[0];
            args.testArgs.user.insertRecordAction.postAction();
            args.testArgs.user.currentTestKeySuffix = args.testArgs.user.testKeySuffixes[1];
            args.testArgs.user.insertRecordAction.postAction();
            args.testArgs.user.currentTestKeySuffix = args.testArgs.user.testKeySuffixes[0];
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipPersistListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _courseMembershipDeleteListById : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            List<courseMembershipDetails> cmd_list = new List<courseMembershipDetails>();
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            courseMembershipDetails cmd = new courseMembershipDetails();
            cmd.bbId = args.wsResultRecord.bbId;
            cmd_list.Add(cmd);

            args.courseMembershipUpdateRecordById.PreActionAndExecuteImp();
            cmd = new courseMembershipDetails();
            cmd.bbId = args.wsInputRecord.bbId;
            cmd_list.Add(cmd);
            args.ClearInputsAndResults();
            args.wsInputList = cmd_list;
        }
        override public void postAction() {
            args.courseMembershipPersistListById.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipDeleteListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _courseMembershipLoadRecordByCourseAndUserBatchId : _courseMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.courseMembershipLoadListByTemplate.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.courseBatchUid = args.wsResultList[0].courseBatchUid;
            args.wsInputRecord.userBatchUid = args.wsResultList[0].userBatchUid;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.courseMembershipLoadRecordByCourseAndUserBatchId(args.param, args.wsInputRecord);
        }
    }
    class _courseMembershipLoadListByTemplate : _courseMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.userBbId = args.testArgs.user.wsResultRecord.bbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.courseMembershipLoadListByTemplate(args.param, args.wsInputRecord);
        }
    }
}
