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
    protected void RunPortalRoleMembershipTest() {
        //!!testArgs.ClearAllTestData();

        testArgs.portalRoleMembership.portalRoleMembershipLoadRecordById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipLoadRecordByUserIdAndPortalRoleId.execute();
        testArgs.portalRoleMembership.portalRoleMembershipInsertRecordById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipPersistRecordById_insert.execute();
        testArgs.portalRoleMembership.portalRoleMembershipPersistRecordById_update.execute();

        testArgs.portalRoleMembership.portalRoleMembershipDeleteRecordById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId.execute();
        testArgs.portalRoleMembership.portalRoleMembershipLoadListById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipInsertListById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipPersistListById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipDeleteListById.execute();
        testArgs.portalRoleMembership.portalRoleMembershipDeleteListByUserId.execute();
        testArgs.portalRoleMembership.portalRoleMembershipLoadListByUserId.execute();
        testArgs.portalRoleMembership.portalRoleMembershipLoadListByPortalRoleId.execute();

        /*
         * load by template method of Bb 9.0 PortalRoleMembership API does not generate valid queries to database
         * [TEST\SQLEXPRESS]Incorrect syntax near the keyword 'AND'. - when searched by userId
         * com.inet.tds.be: Column 'p_person_batch_uid' not found. - when searched by userBatchUid
         * All other methods of administrative Bb API rely on BatchUid, 
         * which was planned to be available through portalRoleMembershipLoadListByTemplate.
         * Therefore are commented out and were not tested.
         */
        //testArgs.portalRoleMembership.portalRoleMembershipLoadListByTemplate.execute();
        /*
        testArgs.portalRoleMembership.portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid.execute();
        testArgs.portalRoleMembership.portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid.execute();
        testArgs.portalRoleMembership.portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid.execute();
        testArgs.portalRoleMembership.portalRoleMembershipLoadListByUserAndPortalRoleBatchUid.execute();
        testArgs.portalRoleMembership.portalRoleMembershipInsertListByUserAndPortalRoleBatchUid.execute();
        testArgs.portalRoleMembership.portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid.execute();
         * */
    }

    class _portalRoleMembershipTestCase_RecordResult : BbWsTest.TestCase_SuccessRecord<_portalRoleMembershipTestArgs, portalRoleMembershipDetails>, ITestAction { }
    class _portalRoleMembershipTestCase_ListResult : BbWsTest.TestCase_SuccessList<_portalRoleMembershipTestArgs, portalRoleMembershipDetails>, ITestAction { 
    }

    class _portalRoleMembershipTestArgs : TestArgs<portalRoleMembershipDetails> {
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
        public override void SetFieldsExtended() { }
        public override void UpdateFieldsMinimal() {
        }

        public _portalRoleMembershipLoadRecordById portalRoleMembershipLoadRecordById;
        public _portalRoleMembershipLoadRecordByUserIdAndPortalRoleId portalRoleMembershipLoadRecordByUserIdAndPortalRoleId;
        public _portalRoleMembershipInsertRecordById portalRoleMembershipInsertRecordById;
        public _portalRoleMembershipPersistRecordById_insert portalRoleMembershipPersistRecordById_insert;
        public _portalRoleMembershipPersistRecordById_update portalRoleMembershipPersistRecordById_update;
        public _portalRoleMembershipDeleteRecordById portalRoleMembershipDeleteRecordById;
        public _portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId;
        public _portalRoleMembershipLoadListById portalRoleMembershipLoadListById;
        public _portalRoleMembershipInsertListById portalRoleMembershipInsertListById;
        public _portalRoleMembershipPersistListById portalRoleMembershipPersistListById;
        public _portalRoleMembershipDeleteListById portalRoleMembershipDeleteListById;
        public _portalRoleMembershipDeleteListByUserId portalRoleMembershipDeleteListByUserId;
        public _portalRoleMembershipLoadListByUserId portalRoleMembershipLoadListByUserId;
        public _portalRoleMembershipLoadListByPortalRoleId portalRoleMembershipLoadListByPortalRoleId;

        /*
        public _portalRoleMembershipLoadListByTemplate portalRoleMembershipLoadListByTemplate;
        public _portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid;
        public _portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid;
        public _portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid;
        public _portalRoleMembershipLoadListByUserAndPortalRoleBatchUid portalRoleMembershipLoadListByUserAndPortalRoleBatchUid;
        public _portalRoleMembershipInsertListByUserAndPortalRoleBatchUid portalRoleMembershipInsertListByUserAndPortalRoleBatchUid;
        public _portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid;
        */
        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            portalRoleMembershipLoadRecordById = new _portalRoleMembershipLoadRecordById();
            portalRoleMembershipLoadRecordById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadRecordByUserIdAndPortalRoleId = new _portalRoleMembershipLoadRecordByUserIdAndPortalRoleId();
            portalRoleMembershipLoadRecordByUserIdAndPortalRoleId.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipInsertRecordById = new _portalRoleMembershipInsertRecordById();
            portalRoleMembershipInsertRecordById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipPersistRecordById_insert = new _portalRoleMembershipPersistRecordById_insert();
            portalRoleMembershipPersistRecordById_insert.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipPersistRecordById_update = new _portalRoleMembershipPersistRecordById_update();
            portalRoleMembershipPersistRecordById_update.init(this.testArgs.portalRoleMembership);


            portalRoleMembershipDeleteRecordById = new _portalRoleMembershipDeleteRecordById();
            portalRoleMembershipDeleteRecordById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId = new _portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId();
            portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadListById = new _portalRoleMembershipLoadListById();
            portalRoleMembershipLoadListById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipInsertListById = new _portalRoleMembershipInsertListById();
            portalRoleMembershipInsertListById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipPersistListById = new _portalRoleMembershipPersistListById();
            portalRoleMembershipPersistListById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipDeleteListById = new _portalRoleMembershipDeleteListById();
            portalRoleMembershipDeleteListById.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipDeleteListByUserId = new _portalRoleMembershipDeleteListByUserId();
            portalRoleMembershipDeleteListByUserId.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadListByUserId = new _portalRoleMembershipLoadListByUserId();
            portalRoleMembershipLoadListByUserId.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadListByPortalRoleId = new _portalRoleMembershipLoadListByPortalRoleId();
            portalRoleMembershipLoadListByPortalRoleId.init(this.testArgs.portalRoleMembership);

            /*
            portalRoleMembershipLoadListByTemplate = new _portalRoleMembershipLoadListByTemplate();
            portalRoleMembershipLoadListByTemplate.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid = new _portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid();
            portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid = new _portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid();
            portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid = new _portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid();
            portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipLoadListByUserAndPortalRoleBatchUid = new _portalRoleMembershipLoadListByUserAndPortalRoleBatchUid();
            portalRoleMembershipLoadListByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipInsertListByUserAndPortalRoleBatchUid = new _portalRoleMembershipInsertListByUserAndPortalRoleBatchUid();
            portalRoleMembershipInsertListByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid = new _portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid();
            portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid.init(this.testArgs.portalRoleMembership);
            */
            loadBaseRecordAction = portalRoleMembershipLoadRecordById;
            //loadInsertedRecordAction = portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid;
            insertRecordAction = portalRoleMembershipInsertRecordById;
            //deleteRecordAction = portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid;
        }
    }

    //************************************************************************************************************
    //******************************************************************************************************


    class _portalRoleMembershipLoadRecordById : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultList[0].bbId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipLoadRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadRecordByUserIdAndPortalRoleId : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.userId = args.wsResultList[0].userId;
            args.wsInputRecord.portalRoleId = args.wsResultList[0].portalRoleId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipLoadRecordByUserIdAndPortalRoleId(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipInsertRecordById : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.ClearInputsAndResults();
            args.portalRoleMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.testArgs.user.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.ClearInputs();
            args.wsInputRecord.userId = args.testArgs.user.wsResultRecord.bbId;
            args.wsInputRecord.portalRoleId = args.wsResultList[0].portalRoleId;
            args.ClearResults();
        }
        override public void postAction() {
            args.testArgs.user.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipInsertRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipPersistRecordById_insert : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertRecordById.preAction();
            args.ClearResults();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipPersistRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipPersistRecordById_update : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertRecordById.PreActionAndExecuteImp();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.ClearResults();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipPersistRecordById(args.param, args.wsInputRecord);
        }
    }

    class _portalRoleMembershipDeleteRecordById : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertRecordById.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipDeleteRecordById(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertRecordById.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.userId = args.wsResultRecord.userId;
            args.wsInputRecord.portalRoleId = args.wsResultRecord.portalRoleId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipDeleteRecordByUserIdAndPortalRoleId(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadListById : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.ClearInputs();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultList[0].bbId;
            args.wsInputList.Add(args.wsInputRecord);
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultList[1].bbId;
            args.wsInputList.Add(args.wsInputRecord);
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipLoadListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipInsertListById : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.insertRecordAction.PreActionAndExecuteImp();
            args.portalRoleMembershipLoadListByUserId.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.ClearInputs();

            args.CreateInputRecord();
            args.wsInputRecord.userId = args.testArgs.user.wsResultRecord.bbId;
            args.wsInputRecord.portalRoleId = args.wsResultList[0].portalRoleId;
            args.wsInputList.Add(args.wsInputRecord);
            args.CreateInputRecord();
            args.wsInputRecord.userId = args.testArgs.user.wsResultRecord.bbId;
            args.wsInputRecord.portalRoleId = args.wsResultList[1].portalRoleId;
            args.wsInputList.Add(args.wsInputRecord);
            args.ClearResults();
        }
        override public void postAction() {
            args.testArgs.user.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipInsertListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipPersistListById : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertListById.preAction();
        }
        override public void postAction() {
            args.portalRoleMembershipInsertListById.postAction();
        }

        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipPersistListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipDeleteListById : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertListById.PreActionAndExecuteImp();
            args.ClearInputs();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultList[0].bbId;
            args.wsInputList.Add(args.wsInputRecord);
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultList[1].bbId;
            args.wsInputList.Add(args.wsInputRecord);
            args.ClearResults();
        }
        override public void postAction() {
            args.portalRoleMembershipInsertListById.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipDeleteListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipDeleteListByUserId : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.portalRoleMembershipInsertListById.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.userId = args.wsResultList[0].userId;
            args.ClearResults();
        }
        override public void postAction() {
            args.portalRoleMembershipInsertListById.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipDeleteListByUserId(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadListByUserId : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.userId = args.testArgs.user.wsResultRecord.bbId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipLoadListByUserId(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadListByPortalRoleId : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.ClearInputs();
            args.wsInputRecord.portalRoleId = args.wsResultRecord.portalRoleId;
            args.ClearResults();
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipLoadListByPortalRoleId(args.param, args.wsInputRecord);
        }
    }



    //******************************************************************************************************

/*
    class _portalRoleMembershipLoadListByTemplate : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            //args.insertRecordAction.PreActionAndExecuteImp();
            //args.wsInputRecord.userBatchUid = args.wsResultRecord.userBatchUid;
            
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            //args.wsInputRecord.userBatchUid = args.testArgs.user.wsResultRecord.batchUid;
            //args.wsInputRecord.userBatchUid = "%";
            //args.wsInputRecord.userId = args.testArgs.user.wsResultRecord.bbId;
            args.wsInputRecord.portalRoleId = "ALUMNI";
            
        }
        override public void postAction()
        {
            args.insertRecordAction.postAction();
        }
        override public void executeImp()
        {
            args.wsResultList = args.bbWs.portalRoleMembershipLoadListByTemplate(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();



        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipLoadRecordByUserAndPortalRoleBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.wsInputRecord.userBatchUid = args.testArgs.user.wsResultRecord.batchUid;

            args.testArgs.portalRole.loadBaseRecordAction.PreActionAndExecuteImp();
            //args.wsInputRecord.portalRoleBatchUid = args.testArgs.portalRole.wsResultRecord.
            args.wsInputRecord.portalRoleId = args.testArgs.portalRole.wsResultRecord.bbId;
            args.ClearResults();
        }
        override public void postAction() {
            args.testArgs.user.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipInsertRecordByUserAndPortalRoleBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            //args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.wsInputRecord.userBatchUid = args.wsResultRecord.userBatchUid;
            args.wsInputRecord.portalRoleBatchUid = args.wsResultRecord.portalRoleBatchUid;
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.portalRoleMembershipDeleteRecordByUserAndPortalRoleBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _portalRoleMembershipLoadListByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipLoadListByUserAndPortalRoleBatchUid(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipInsertListByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipInsertListByUserAndPortalRoleBatchUid(args.param, args.wsInputList.ToArray());
        }
    }
    class _portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid : _portalRoleMembershipTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.portalRoleMembershipDeleteListByUserAndPortalRoleBatchUid(args.param, args.wsInputList.ToArray());
        }
    }

*/



}
