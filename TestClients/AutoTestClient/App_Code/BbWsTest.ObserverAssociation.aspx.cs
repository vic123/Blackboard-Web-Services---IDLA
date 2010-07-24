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
    protected void RunObserverAssociationTest() {
        //testArgs.ClearAllTestData();
        
        testArgs.observerAssociation.observerAssociationLoadListByTemplate.execute();
        testArgs.observerAssociation.observerAssociationLoadRecordByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationInsertRecordByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus.execute();

        testArgs.observerAssociation.observerAssociationDeleteRecordByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationLoadListByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationInsertListByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationPersistListByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationDeleteListByObserverAndUsersBatchUid.execute();
    }

    class _observerAssociationTestCase_RecordResult : BbWsTest.TestCase_SuccessRecord<_observerAssociationTestArgs, observerAssociationDetails>, ITestAction { }
    class _observerAssociationTestCase_ListResult : BbWsTest.TestCase_SuccessList<_observerAssociationTestArgs, observerAssociationDetails>, ITestAction { }

    class _observerAssociationTestArgs : TestArgs<observerAssociationDetails> {
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
            //wsInputRecord.
            //wsInputRecord.familyName = "familyName" + currentTestKeySuffix;
        }

        public _observerAssociationLoadListByTemplate observerAssociationLoadListByTemplate;
        public _observerAssociationLoadRecordByObserverAndUsersBatchUid observerAssociationLoadRecordByObserverAndUsersBatchUid;
        public _observerAssociationInsertRecordByObserverAndUsersBatchUid observerAssociationInsertRecordByObserverAndUsersBatchUid;
        public _observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus;
        public _observerAssociationDeleteRecordByObserverAndUsersBatchUid observerAssociationDeleteRecordByObserverAndUsersBatchUid;
        public _observerAssociationLoadListByObserverAndUsersBatchUid observerAssociationLoadListByObserverAndUsersBatchUid;
        public _observerAssociationInsertListByObserverAndUsersBatchUid observerAssociationInsertListByObserverAndUsersBatchUid;
        public _observerAssociationPersistListByObserverAndUsersBatchUid observerAssociationPersistListByObserverAndUsersBatchUid;
        public _observerAssociationDeleteListByObserverAndUsersBatchUid observerAssociationDeleteListByObserverAndUsersBatchUid;




        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            observerAssociationLoadListByTemplate = new _observerAssociationLoadListByTemplate();
            observerAssociationLoadListByTemplate.init(this.testArgs.observerAssociation);
            observerAssociationLoadRecordByObserverAndUsersBatchUid = new _observerAssociationLoadRecordByObserverAndUsersBatchUid();
            observerAssociationLoadRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationInsertRecordByObserverAndUsersBatchUid = new _observerAssociationInsertRecordByObserverAndUsersBatchUid();
            observerAssociationInsertRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus = new _observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus();
            observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus.init(this.testArgs.observerAssociation);

            observerAssociationDeleteRecordByObserverAndUsersBatchUid = new _observerAssociationDeleteRecordByObserverAndUsersBatchUid();
            observerAssociationDeleteRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationLoadListByObserverAndUsersBatchUid = new _observerAssociationLoadListByObserverAndUsersBatchUid();
            observerAssociationLoadListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationInsertListByObserverAndUsersBatchUid = new _observerAssociationInsertListByObserverAndUsersBatchUid();
            observerAssociationInsertListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationPersistListByObserverAndUsersBatchUid = new _observerAssociationPersistListByObserverAndUsersBatchUid();
            observerAssociationPersistListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);

            observerAssociationDeleteListByObserverAndUsersBatchUid = new _observerAssociationDeleteListByObserverAndUsersBatchUid();
            observerAssociationDeleteListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);



            loadBaseRecordAction = observerAssociationLoadRecordByObserverAndUsersBatchUid;
            loadInsertedRecordAction = observerAssociationLoadRecordByObserverAndUsersBatchUid;
            insertRecordAction = observerAssociationInsertRecordByObserverAndUsersBatchUid;
            deleteRecordAction = observerAssociationDeleteRecordByObserverAndUsersBatchUid;

        }
    }

    //************************************************************************************************************
    //******************************************************************************************************

    class _observerAssociationLoadListByTemplate : _observerAssociationTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.usersBatchUid = "%";
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.observerAssociationLoadListByTemplate(args.param, args.wsInputRecord);
        }
    }
    class _observerAssociationLoadRecordByObserverAndUsersBatchUid : _observerAssociationTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.observerAssociationLoadRecordByObserverAndUsersBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _observerAssociationInsertRecordByObserverAndUsersBatchUid : _observerAssociationTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.testArgs.user.ClearInputsAndResults();
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.usersBatchUid = args.testArgs.user.wsResultRecord.batchUid;

            args.testArgs.user.insertRecordAction.PreActionAndExecuteImp();
            args.testArgs.user.loadInsertedRecordAction.executeImp();
            args.wsInputRecord.observerBatchUid = args.testArgs.user.wsResultRecord.batchUid;
            args.ClearResults();


            //test preassigned with non-Parent primary and secondary system roles
            /*
            args.testArgs.user.loadBaseRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.usersBatchUid = args.testArgs.user.wsResultRecord.batchUid;
            args.testArgs.user.insertRecordAction.preAction();
            args.wsInputRecord.observerBatchUid = args.testArgs.user.wsInputRecord.batchUid;
            args.ClearResults(); */
        }
        override public void postAction() {
            args.testArgs.user.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.observerAssociationInsertRecordByObserverAndUsersBatchUid(args.param, args.wsInputRecord);
        }
    }

    class _observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus : _observerAssociationTestCase_RecordResult, ITestAction
    {
        override public void preAction() {
            args.ClearInputsAndResults();
            args.observerAssociationInsertRecordByObserverAndUsersBatchUid.PreActionAndExecuteImp();
            ShowResultListTableAndDataLog();
            args.ClearInputs();
            args.wsInputRecord.observerBatchUid = args.wsResultRecord.observerBatchUid;
            args.wsInputRecord.usersBatchUid = args.wsResultRecord.usersBatchUid;
        }
        override public void executeImp() {
            //Row Status values: ENABLED, SOFT_DELETE, DISABLED, DELETE_PENDING, COPY_PENDING, DEFAULT. 
            //DEFAULT = (DEFAULT)defineDefault(ENABLED); 
            args.response.Write("setRowStatus(\"DISABLED\"): </br>");
            setRowStatus("DISABLED");

            args.response.Write("Try to create new record of same user-role - should fail: </br>");
            observerAssociationDetails ws_i_rec = args.wsInputRecord;
            args.ClearInputs();
            args.observerAssociationInsertRecordByObserverAndUsersBatchUid.PreActionAndExecuteImp();
            ShowResultListTableAndDataLog();
            args.wsInputRecord = ws_i_rec;

            args.response.Write("setRowStatus(\"ENABLED\"): </br>");
            setRowStatus("ENABLED");
            args.response.Write("setRowStatus(\"SOFT_DELETE\"): </br>");
            setRowStatus("SOFT_DELETE");
            args.response.Write("setRowStatus(\"DELETE_PENDING\"): </br>");
            setRowStatus("DELETE_PENDING");
            args.response.Write("setRowStatus(\"COPY_PENDING\"): </br>");
            setRowStatus("COPY_PENDING");
            args.response.Write("setRowStatus(\"BAD_VALUE\"): </br>");
            setRowStatus("BAD_VALUE");
            args.ClearResults();
            args.loadBaseRecordAction.executeImp();
        }

        private void setRowStatus(String rowStatus) {
            args.wsInputRecord.rowStatus = rowStatus;
            args.ClearResults();
            args.wsResultRecord = args.bbWs.observerAssociationPersistRecordByObserverAndUsersBatchUid_update_rowStatus(args.param, args.wsInputRecord);
            ShowResultListTableAndDataLog();

            args.ClearResults();
            args.response.Write("Try to load record with observerAssociationLoadRecordByObserverAndUsersBatchUid</br>");
            args.observerAssociationLoadRecordByObserverAndUsersBatchUid.executeImp();
            ShowResultListTableAndDataLog();

            args.ClearResults();
            args.response.Write("Try to load list of records with observerAssociationLoadListByTemplate</br>");
            observerAssociationDetails ws_i_rec = args.wsInputRecord;
            args.ClearInputs();
            args.wsInputRecord.observerBatchUid = "%";
            args.wsInputRecord.usersBatchUid = "%";
            args.observerAssociationLoadListByTemplate.executeImp();
            ShowResultListTableAndDataLog();
            args.wsInputRecord = ws_i_rec;
        }
        override public void postAction() {
            args.observerAssociationInsertRecordByObserverAndUsersBatchUid.postAction();
        }
    }

    class _observerAssociationDeleteRecordByObserverAndUsersBatchUid : _observerAssociationTestCase_RecordResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            //args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.observerAssociationDeleteRecordByObserverAndUsersBatchUid(args.param, args.wsInputRecord);
        }
    }

    class _observerAssociationLoadListByObserverAndUsersBatchUid : _observerAssociationTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.observerAssociationLoadListByObserverAndUsersBatchUid(args.param, args.wsInputList.ToArray());
        }
    }
    class _observerAssociationInsertListByObserverAndUsersBatchUid : _observerAssociationTestCase_ListResult, ITestAction
    {
        override public void preAction() {
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.observerAssociationInsertListByObserverAndUsersBatchUid(args.param, args.wsInputList.ToArray());
        }
    }
    class _observerAssociationPersistListByObserverAndUsersBatchUid : _observerAssociationTestCase_ListResult, ITestAction
    {
        override public void preAction() {
            args.observerAssociationInsertListByObserverAndUsersBatchUid.PreActionAndExecuteImp();
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.observerAssociationPersistListByObserverAndUsersBatchUid(args.param, args.wsInputList.ToArray());
        }
        
    }

    class _observerAssociationDeleteListByObserverAndUsersBatchUid : _observerAssociationTestCase_ListResult, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.observerAssociationDeleteListByObserverAndUsersBatchUid(args.param, args.wsInputList.ToArray());
        }
    }



}
