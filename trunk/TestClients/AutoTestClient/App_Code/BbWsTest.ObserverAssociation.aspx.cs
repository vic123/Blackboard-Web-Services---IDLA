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
    protected void RunObserverAssociationTest() {
        //testArgs.ClearAllTestData();
        testArgs.observerAssociation.observerAssociationLoadListByTemplate.execute();
        testArgs.observerAssociation.observerAssociationLoadRecordByObserverAndUsersBatchUid.execute();

        testArgs.observerAssociation.observerAssociationInsertRecordByObserverAndUsersBatchUid.execute();

        testArgs.observerAssociation.observerAssociationDeleteRecordByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationLoadListByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationInsertListByObserverAndUsersBatchUid.execute();
        testArgs.observerAssociation.observerAssociationDeleteListByObserverAndUsersBatchUid.execute();
    }

    class _observerAssociationTestCase_RecordResult : BbWsTest.TestCase_SuccessRecord<_observerAssociationTestArgs, bbws.observerAssociationDetails>, ITestAction { }
    class _observerAssociationTestCase_ListResult : BbWsTest.TestCase_SuccessList<_observerAssociationTestArgs, bbws.observerAssociationDetails>, ITestAction { }

    class _observerAssociationTestArgs : TestArgs<bbws.observerAssociationDetails> {
        public override bbws.bbWsDataLogRecord[] getDataLogArray() {
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
        public _observerAssociationDeleteRecordByObserverAndUsersBatchUid observerAssociationDeleteRecordByObserverAndUsersBatchUid;
        public _observerAssociationLoadListByObserverAndUsersBatchUid observerAssociationLoadListByObserverAndUsersBatchUid;
        public _observerAssociationInsertListByObserverAndUsersBatchUid observerAssociationInsertListByObserverAndUsersBatchUid;
        public _observerAssociationDeleteListByObserverAndUsersBatchUid observerAssociationDeleteListByObserverAndUsersBatchUid;




        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            observerAssociationLoadListByTemplate = new _observerAssociationLoadListByTemplate();
            observerAssociationLoadListByTemplate.init(this.testArgs.observerAssociation);
            observerAssociationLoadRecordByObserverAndUsersBatchUid = new _observerAssociationLoadRecordByObserverAndUsersBatchUid();
            observerAssociationLoadRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationInsertRecordByObserverAndUsersBatchUid = new _observerAssociationInsertRecordByObserverAndUsersBatchUid();
            observerAssociationInsertRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationDeleteRecordByObserverAndUsersBatchUid = new _observerAssociationDeleteRecordByObserverAndUsersBatchUid();
            observerAssociationDeleteRecordByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationLoadListByObserverAndUsersBatchUid = new _observerAssociationLoadListByObserverAndUsersBatchUid();
            observerAssociationLoadListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
            observerAssociationInsertListByObserverAndUsersBatchUid = new _observerAssociationInsertListByObserverAndUsersBatchUid();
            observerAssociationInsertListByObserverAndUsersBatchUid.init(this.testArgs.observerAssociation);
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
    class _observerAssociationInsertListByObserverAndUsersBatchUid : _observerAssociationTestCase_ListResult, ITestAction {
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
