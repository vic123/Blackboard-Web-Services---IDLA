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
//!!
using bbIDLA.BBAddedService;
//using bbws;

public partial class BbWsTest : System.Web.UI.Page
{
    protected void RunPortalRoleTest() {
        //testArgs.ClearAllTestData();
        testArgs.portalRole.portalRoleLoadRecordById.execute();
        testArgs.portalRole.portalRoleLoadRecordByRoleId.execute();
        testArgs.portalRole.portalRoleLoadRecordByRoleName.execute();

        testArgs.portalRole.portalRoleLoadRecordDefault.execute();
        testArgs.portalRole.portalRoleLoadListRemovable.execute();
        testArgs.portalRole.portalRoleLoadListAll.execute();
        testArgs.portalRole.portalRoleLoadListAvailable.execute();
        
        testArgs.portalRole.portalRoleLoadRecordPrimaryRoleByUserId.execute();
        testArgs.portalRole.portalRoleLoadListAllByUserId.execute();
        testArgs.portalRole.portalRoleLoadListSecondaryRolesByUserId.execute();
        
        testArgs.portalRole.portalRoleInsertRecordById.execute();

        testArgs.portalRole.portalRoleUpdateRecordById.execute();

        testArgs.portalRole.portalRoleUpdateRecordById_extended.execute();

        testArgs.portalRole.portalRolePersistRecordById_insert.execute();
        testArgs.portalRole.portalRolePersistRecordById_update.execute();
        testArgs.portalRole.portalRoleDeleteRecordById.execute();
    }

    class _portalRoleTestCase_RecordResult : BbWsTest.TestCase_SuccessRecord<_portalRoleTestArgs, portalRoleDetails>, ITestAction { }
    class _portalRoleTestCase_ListResult : BbWsTest.TestCase_SuccessList<_portalRoleTestArgs, portalRoleDetails>, ITestAction { }

    class _portalRoleTestArgs : TestArgs<portalRoleDetails> {
        public override bbWsDataLogRecord[] getDataLogArray() {
            return wsResultRecord.bbWsDataLog;
        }
        public override String getBbWsBoolResult() {
            return wsResultRecord.bbWsBoolResult;
        }
        public override String getBbWsRecordResultId() {
            return wsResultRecord.bbId;
        }
        public override void SetFieldsMinimal() {
            wsInputRecord.roleName = "roleName" + currentTestKeySuffix;
            wsInputRecord.roleId = "roleId" + currentTestKeySuffix;
        }
        public override void UpdateFieldsMinimal() {
            wsInputRecord.roleName = "roleName" + currentTestKeySuffix;
        }
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
                    case "isGuest":
                        str_value = "true";
                        break;
                    case "isRemovable":
                        str_value = "false";
                        break;
                    case "isSelfSelectable":
                        str_value = "true";
                        break;
                    case "dataSourceBbId":
                        continue;
                    default:
                        str_value = prop.Name + currentTestKeySuffix;
                        break;
                }
                prop.SetValue(wsInputRecord, str_value, null);
            }
        }

        public _portalRoleLoadRecordById portalRoleLoadRecordById;
        public _portalRoleLoadRecordByRoleId portalRoleLoadRecordByRoleId;
        public _portalRoleLoadRecordByRoleName portalRoleLoadRecordByRoleName;
        public _portalRoleLoadRecordDefault portalRoleLoadRecordDefault;
        public _portalRoleLoadListRemovable portalRoleLoadListRemovable;
        public _portalRoleLoadListAll portalRoleLoadListAll;
        public _portalRoleLoadListAvailable portalRoleLoadListAvailable;
        public _portalRoleLoadRecordPrimaryRoleByUserId portalRoleLoadRecordPrimaryRoleByUserId;
        public _portalRoleLoadListAllByUserId portalRoleLoadListAllByUserId;
        public _portalRoleLoadListSecondaryRolesByUserId portalRoleLoadListSecondaryRolesByUserId;
        public _portalRoleInsertRecordById portalRoleInsertRecordById;
        public _portalRoleUpdateRecordById portalRoleUpdateRecordById;

        public _portalRoleUpdateRecordById_extended portalRoleUpdateRecordById_extended;

        public _portalRolePersistRecordById_insert portalRolePersistRecordById_insert;
        public _portalRolePersistRecordById_update portalRolePersistRecordById_update;
        public _portalRoleDeleteRecordById portalRoleDeleteRecordById;

        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            portalRoleLoadRecordById = new _portalRoleLoadRecordById();
            portalRoleLoadRecordById.init(this.testArgs.portalRole);
            portalRoleLoadRecordByRoleId = new _portalRoleLoadRecordByRoleId();
            portalRoleLoadRecordByRoleId.init(this.testArgs.portalRole);
            portalRoleLoadRecordByRoleName = new _portalRoleLoadRecordByRoleName();
            portalRoleLoadRecordByRoleName.init(this.testArgs.portalRole);
            portalRoleLoadRecordDefault = new _portalRoleLoadRecordDefault();
            portalRoleLoadRecordDefault.init(this.testArgs.portalRole);
            portalRoleLoadListRemovable = new _portalRoleLoadListRemovable();
            portalRoleLoadListRemovable.init(this.testArgs.portalRole);
            portalRoleLoadListAll = new _portalRoleLoadListAll();
            portalRoleLoadListAll.init(this.testArgs.portalRole);
            portalRoleLoadListAvailable = new _portalRoleLoadListAvailable();
            portalRoleLoadListAvailable.init(this.testArgs.portalRole);
            portalRoleLoadRecordPrimaryRoleByUserId = new _portalRoleLoadRecordPrimaryRoleByUserId();
            portalRoleLoadRecordPrimaryRoleByUserId.init(this.testArgs.portalRole);
            portalRoleLoadListAllByUserId = new _portalRoleLoadListAllByUserId();
            portalRoleLoadListAllByUserId.init(this.testArgs.portalRole);
            portalRoleLoadListSecondaryRolesByUserId = new _portalRoleLoadListSecondaryRolesByUserId();
            portalRoleLoadListSecondaryRolesByUserId.init(this.testArgs.portalRole);
            portalRoleInsertRecordById = new _portalRoleInsertRecordById();
            portalRoleInsertRecordById.init(this.testArgs.portalRole);
            portalRoleUpdateRecordById = new _portalRoleUpdateRecordById();
            portalRoleUpdateRecordById.init(this.testArgs.portalRole);

            portalRoleUpdateRecordById_extended = new _portalRoleUpdateRecordById_extended();
            portalRoleUpdateRecordById_extended.init(this.testArgs.portalRole);

            

            portalRolePersistRecordById_insert = new _portalRolePersistRecordById_insert();
            portalRolePersistRecordById_insert.init(this.testArgs.portalRole);
            portalRolePersistRecordById_update = new _portalRolePersistRecordById_update();
            portalRolePersistRecordById_update.init(this.testArgs.portalRole);

            portalRoleDeleteRecordById = new _portalRoleDeleteRecordById();
            portalRoleDeleteRecordById.init(this.testArgs.portalRole);


            loadBaseRecordAction = portalRoleLoadRecordByRoleId;
            loadInsertedRecordAction = portalRoleLoadRecordByRoleId;
            insertRecordAction = portalRoleInsertRecordById;
            deleteRecordAction = portalRoleDeleteRecordById;

        }
    }

    //************************************************************************************************************
    //******************************************************************************************************

    class _portalRoleLoadRecordById : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.portalRoleLoadListAll.executeImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultList[0].bbId;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleLoadRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadRecordByRoleId : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.portalRoleLoadListAll.executeImp();
            args.ClearInputs();
            args.wsInputRecord.roleId = args.wsResultList[args.wsResultList.Length-1].roleId;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleLoadRecordByRoleId(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadRecordByRoleName : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.portalRoleLoadListAll.executeImp();
            args.ClearInputs();
            args.wsInputRecord.roleName = args.wsResultList[0].roleName;
            args.wsInputRecord.roleName = args.wsInputRecord.roleName.ToUpper() + ".role_name";
            //args.wsInputRecord.roleName = "ALUMNI.role_name"; 
            args.wsInputRecord.roleName = "Alumni"; 
            //args.wsInputRecord.roleName = "Alumni.role_name"; 
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleLoadRecordByRoleName(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadRecordDefault : _portalRoleTestCase_RecordResult, ITestAction {
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleLoadRecordDefault(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadListRemovable : _portalRoleTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.portalRoleInsertRecordById.PreActionAndExecuteImp();
        }
        override public void postAction() {
            args.portalRoleInsertRecordById.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleLoadListRemovable(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadListAll : _portalRoleTestCase_ListResult, ITestAction {
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleLoadListAll(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadListAvailable : _portalRoleTestCase_ListResult, ITestAction {
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleLoadListAvailable(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleLoadRecordPrimaryRoleByUserId : _portalRoleTestCase_RecordResult, ITestAction
    {
        override public void preAction() {
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.ClearInputs();
            args.testArgs.user.wsInputRecord.bbId = args.testArgs.user.wsResultRecord.bbId;
        }
        override public void executeImp()
        {
            args.wsResultRecord = args.bbWs.portalRoleLoadRecordPrimaryRoleByUserId(args.param, args.wsInputRecord, args.testArgs.user.wsInputRecord);
        }
    }

    class _portalRoleLoadListAllByUserId : _portalRoleTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.portalRoleLoadRecordPrimaryRoleByUserId.preAction();
        }
        override public void executeImp()
        {
            args.wsResultList = args.bbWs.portalRoleLoadListAllByUserId(args.param, args.wsInputRecord, args.testArgs.user.wsInputRecord);
        }
    }
    class _portalRoleLoadListSecondaryRolesByUserId : _portalRoleTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.portalRoleLoadRecordPrimaryRoleByUserId.preAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleLoadListSecondaryRolesByUserId(args.param, args.wsInputRecord, args.testArgs.user.wsInputRecord);
        }
    }

    class _portalRoleInsertRecordById : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.SetFieldsMinimal();
        }
        override public void postAction() {
            args.loadInsertedRecordAction.executeImp();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleInsertRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleUpdateRecordById : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            args.wsInputRecord = args.wsResultRecord;
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.UpdateFieldsMinimal();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleUpdateRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleUpdateRecordById_extended : _portalRoleUpdateRecordById, ITestAction {
        override public void preAction() {
            base.preAction();
            ShowResultListTableAndDataLog();
            args.SetFieldsExtended();
        }
    }
    
    class _portalRolePersistRecordById : _portalRoleTestCase_RecordResult, ITestAction {
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRolePersistRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRolePersistRecordById_insert : _portalRolePersistRecordById, ITestAction {
        override public void preAction() {
            args.SetFieldsMinimal();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
    }
    class _portalRolePersistRecordById_update : _portalRolePersistRecordById, ITestAction {
        override public void preAction() {
            args.portalRoleUpdateRecordById.preAction();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
    }
    class _portalRoleDeleteRecordById : _portalRoleTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleDeleteRecordById(args.param, args.wsInputRecord);
        }
    }
}
