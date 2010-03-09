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
    protected void RunUserTest() {
        testArgs.ClearAllTestData();
        testArgs.user.userLoadRecordByBatchUid.execute();
        testArgs.user.userLoadListByTemplate.execute();
        testArgs.user.userInsertRecordByBatchUid_duplicate.execute();
        testArgs.user.userInsertRecordByBatchUid_minimal.execute();
        testArgs.user.userUpdateRecordByBatchUid.execute();
        testArgs.user.userPersistRecordByBatchUid_insert.execute();
        testArgs.user.userPersistRecordByBatchUid_update.execute();
        testArgs.user.userDeleteRecordByBatchUid.execute();
        testArgs.user.userPersistListByBatchUid.execute();
        testArgs.user.userDeleteListByBatchUid.execute();
        testArgs.user.userLoadRecordById.execute();
        testArgs.user.userLoadRecordByName.execute();
        testArgs.user.userInsertRecordById_minimal.execute();
        testArgs.user.userUpdateRecordById.execute();

        testArgs.user.userLoadRecordByName.execute();
        testArgs.user.userUpdateRecordById_extended.execute();

        testArgs.user.userPersistRecordById_insert.execute();
        testArgs.user.userPersistRecordById_update.execute();
        testArgs.user.userDeleteRecordById.execute();
        testArgs.user.userLoadListById.execute();
        testArgs.user.userInsertListById.execute();
        testArgs.user.userUpdateListById.execute();
        testArgs.user.userPersistListById.execute();
        testArgs.user.userDeleteListById.execute();
        testArgs.user.userLoadListByEmailAddressFamilyNameGivenName_email.execute();
        testArgs.user.userLoadListBySearchByUserName.execute();
        
        testArgs.user.userLoadListByCourseId.execute();
        testArgs.user.userLoadListObservedByObserverId.execute();
        testArgs.user.userLoadListByGroupId.execute();
        testArgs.user.userLoadListByPrimaryRoleId.execute();

        //!! assign customly created PortalRole to an user
    
    }






    //    class _userTestCase : BbWsTest.TestCase<_userTestArgs, bbws.userDetails>, ITestAction { }
    class _userTestCase_SuccessRecord : BbWsTest.TestCase_SuccessRecord<_userTestArgs, bbws.userDetails>, ITestAction { }
    class _userTestCase_SuccessList : BbWsTest.TestCase_SuccessList<_userTestArgs, bbws.userDetails>, ITestAction { }
    /*
    class _userTestCase_SuccessRecord : TestCase<_userTestArgs, bbws.userDetails>, ITestAction {
        override public void init(Object args) {
            base.init(args);
            checkResultsEvent += checkRecordSuccessResult;
        }
    }
    class _userTestCase_SuccessList : TestCase<_userTestArgs, bbws.userDetails>, ITestAction {
        override public void init(Object args) {
            base.init(args);
            checkResultsEvent += checkListSuccessResult;
        }
    }
    */
    class _userTestArgs : TestArgs<bbws.userDetails> {
        public override bbws.bbWsDataLogRecord[] getDataLogArray() {
            return wsResultRecord.bbWsDataLog;
        }
        public override String getBbWsBoolResult() {
            return wsResultRecord.bbWsBoolResult;
        }
        public override String getBbWsRecordResultId() {
            return wsResultRecord.bbId;
        }
        public override void SetFieldsMinimal() {
            wsInputRecord.batchUid = "batchUid" + currentTestKeySuffix;
            wsInputRecord.userName += "userName" + currentTestKeySuffix;
            wsInputRecord.familyName += "familyName" + currentTestKeySuffix;
            wsInputRecord.givenName += "givenName" + currentTestKeySuffix;
            wsInputRecord.emailAddress = "emailAddress" + "@" + currentTestKeySuffix;
        }
        public override void UpdateFieldsMinimal() {
            wsInputRecord.familyName = "familyName" + currentTestKeySuffix;
            wsInputRecord.givenName = "givenName" + currentTestKeySuffix;
            wsInputRecord.emailAddress = "emailAddress" + "@" + currentTestKeySuffix;
        }


        public override void SetFieldsExtended() {
            System.Reflection.PropertyInfo[] props = wsInputRecord.GetType().GetProperties();
            foreach (System.Reflection.PropertyInfo prop in props) {
                //wsResultRecord.
                Object value = prop.GetValue(wsInputRecord, null);
                String str_value = Convert.ToString(value);
                switch (prop.Name) {
                    case "bbId":
                        continue;
                    case "bbWsDataLog":
                        continue;
                    case "birthDate":
                        str_value = wsResultRecord.modifiedDate;
                        break;
                    case "cardNumber":
                        continue;
                    case "cdRomDrivePC":
                        str_value = "D";
                        break;
                    case "cdRomDriveMac":
                        str_value = "D";
                        break;
                    case "dataSourceBbId":
                        continue;
                    case "isAvailable":
                        str_value = "true";
                        break;
                    //    "K_8", "HIGH_SCHOOL", "FRESHMAN", "SOPHOMORE", "JUNIOR", "SENIOR", "GRADUATE_SCHOOL", "POST_GRADUATE_SCHOOL", "UNKNOWN"
                    case "educationLevel":
                        str_value = "HIGH_SCHOOL";
                        break;
                    //"FEMALE", "MALE", "UNKNOWN"
                    case "gender":
                        str_value = "MALE";
                        break;
                    case "lastLogin":
                        str_value = wsResultRecord.modifiedDate;
                        break;
                    case "locale":
                        str_value = "lang_COUNTRY";//_AnyAddInfo
                        break;

                    case "modifiedDate":
                        //str_value = wsResultRecord.modifiedDate;
                        continue;
                    case "showAddContactInfo":
                        str_value = "true";
                        break;
                    case "showAddressInfo":
                        str_value = "true";
                        break;
                    case "showEmailInfo":
                        str_value = "true";
                        break;
                    case "showWorkInfo":
                        str_value = "true";
                        break;
                    case "isInfoPublic":
                        str_value = "true";
                        break;


                    //SYSTEM_ADMIN, SYSTEM_SUPPORT, COURSE_CREATOR, COURSE_SUPPORT, ACCOUNT_ADMIN, GUEST, USER, OBSERVER, INTEGRATION, PORTAL, ECOMMERCE_ADMIN, CARD_OFFICE_ADMIN, STORE_ADMIN, LMS_INTEGRATION_ADMIN, NONE, DEFAULT;
                    case "systemRole":
                        str_value = "COURSE_CREATOR";
                        //str_value = wsResultRecord.systemRole;
                        break;
                    case "systemRoleId":
                        //str_value = wsResultRecord.systemRoleId;
                        str_value = "C";
                        break;
                        //continue;
                    case "portalRoleName":
                        //str_value = "STAFF";
                        //str_value = "FACULTY" + ".role_name"; 
                        str_value = "Staff";
                        break;
                    case "portalRoleId": //!! - assign custom (created role)
                        //str_value = "FACULTY";
                        continue;
                        break;
                    //case "batchUid": 
                    //case "userName": continue;
                    default:
                        str_value = prop.Name + currentTestKeySuffix;
                        break;
                }
                prop.SetValue(wsInputRecord, str_value, null);
            }
        }




        public _userLoadRecordByBatchUid userLoadRecordByBatchUid;
        public _userLoadRecordByBatchUid_inserted userLoadRecordByBatchUid_inserted;

        public _userLoadListByTemplate userLoadListByTemplate;
        public _userInsertRecordByBatchUid_duplicate userInsertRecordByBatchUid_duplicate;

        public _userInsertRecordByBatchUid_minimal userInsertRecordByBatchUid_minimal;

        public _userUpdateRecordByBatchUid userUpdateRecordByBatchUid;
        //public _userPersistRecordByBatchUid userPersistRecordByBatchUid;

        public _userPersistRecordByBatchUid_insert userPersistRecordByBatchUid_insert;
        public _userPersistRecordByBatchUid_update userPersistRecordByBatchUid_update;


        public _userDeleteRecordByBatchUid userDeleteRecordByBatchUid;
        public _userPersistListByBatchUid userPersistListByBatchUid;
        public _userDeleteListByBatchUid userDeleteListByBatchUid;
        public _userLoadRecordById userLoadRecordById;
        public _userLoadRecordByName userLoadRecordByName;

        public _userInsertRecordById_minimal userInsertRecordById_minimal;

        public _userUpdateRecordById userUpdateRecordById;
        public _userUpdateRecordById_extended userUpdateRecordById_extended;

        //public _userPersistRecordById userPersistRecordById;
        public _userPersistRecordById_insert userPersistRecordById_insert;
        public _userPersistRecordById_update userPersistRecordById_update;


        public _userDeleteRecordById userDeleteRecordById;
        public _userLoadListById userLoadListById;
        public _userInsertListById userInsertListById;
        public _userUpdateListById userUpdateListById;
        public _userPersistListById userPersistListById;
        public _userDeleteListById userDeleteListById;
        public _userLoadListByEmailAddressFamilyNameGivenName_email userLoadListByEmailAddressFamilyNameGivenName_email;
        public _userLoadListBySearchByUserName userLoadListBySearchByUserName;
        public _userLoadListObservedByObserverId userLoadListObservedByObserverId;
        public _userLoadListByCourseId userLoadListByCourseId;
        public _userLoadListByGroupId userLoadListByGroupId;
        public _userLoadListByPrimaryRoleId userLoadListByPrimaryRoleId;


        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);


            userLoadRecordByBatchUid = new _userLoadRecordByBatchUid();
            userLoadRecordByBatchUid.init(this.testArgs.user);

            userLoadRecordByBatchUid_inserted = new _userLoadRecordByBatchUid_inserted();
            userLoadRecordByBatchUid_inserted.init(this.testArgs.user);


            userLoadListByTemplate = new _userLoadListByTemplate();
            userLoadListByTemplate.init(this.testArgs.user);
            userInsertRecordByBatchUid_duplicate = new _userInsertRecordByBatchUid_duplicate();
            userInsertRecordByBatchUid_duplicate.init(this.testArgs.user);

            userInsertRecordByBatchUid_minimal = new _userInsertRecordByBatchUid_minimal();
            userInsertRecordByBatchUid_minimal.init(this.testArgs.user);

            userUpdateRecordByBatchUid = new _userUpdateRecordByBatchUid();
            userUpdateRecordByBatchUid.init(this.testArgs.user);
            //userPersistRecordByBatchUid = new _userPersistRecordByBatchUid();
            //userPersistRecordByBatchUid.init(this.testArgs.user);

            userPersistRecordByBatchUid_update = new _userPersistRecordByBatchUid_update();
            userPersistRecordByBatchUid_update.init(this.testArgs.user);
            userPersistRecordByBatchUid_insert = new _userPersistRecordByBatchUid_insert();
            userPersistRecordByBatchUid_insert.init(this.testArgs.user);

            userDeleteRecordByBatchUid = new _userDeleteRecordByBatchUid();
            userDeleteRecordByBatchUid.init(this.testArgs.user);
            userPersistListByBatchUid = new _userPersistListByBatchUid();
            userPersistListByBatchUid.init(this.testArgs.user);
            userDeleteListByBatchUid = new _userDeleteListByBatchUid();
            userDeleteListByBatchUid.init(this.testArgs.user);
            userLoadRecordById = new _userLoadRecordById();
            userLoadRecordById.init(this.testArgs.user);
            userLoadRecordByName = new _userLoadRecordByName();
            userLoadRecordByName.init(this.testArgs.user);
            userInsertRecordById_minimal = new _userInsertRecordById_minimal();
            userInsertRecordById_minimal.init(this.testArgs.user);
            userUpdateRecordById_extended = new _userUpdateRecordById_extended();
            userUpdateRecordById_extended.init(this.testArgs.user);

            userUpdateRecordById = new _userUpdateRecordById();
            userUpdateRecordById.init(this.testArgs.user);

            //userPersistRecordById = new _userPersistRecordById();
            //userPersistRecordById.init(this.testArgs.user);		                
            userPersistRecordById_insert = new _userPersistRecordById_insert();
            userPersistRecordById_insert.init(this.testArgs.user);
            userPersistRecordById_update = new _userPersistRecordById_update();
            userPersistRecordById_update.init(this.testArgs.user);

            userDeleteRecordById = new _userDeleteRecordById();
            userDeleteRecordById.init(this.testArgs.user);
            userLoadListById = new _userLoadListById();
            userLoadListById.init(this.testArgs.user);
            userInsertListById = new _userInsertListById();
            userInsertListById.init(this.testArgs.user);
            userUpdateListById = new _userUpdateListById();
            userUpdateListById.init(this.testArgs.user);
            userPersistListById = new _userPersistListById();
            userPersistListById.init(this.testArgs.user);
            userDeleteListById = new _userDeleteListById();
            userDeleteListById.init(this.testArgs.user);
            userLoadListByEmailAddressFamilyNameGivenName_email = new _userLoadListByEmailAddressFamilyNameGivenName_email();
            userLoadListByEmailAddressFamilyNameGivenName_email.init(this.testArgs.user);
            userLoadListBySearchByUserName = new _userLoadListBySearchByUserName();
            userLoadListBySearchByUserName.init(this.testArgs.user);

            userLoadListObservedByObserverId = new _userLoadListObservedByObserverId();
            userLoadListObservedByObserverId.init(this.testArgs.user);
            userLoadListByCourseId = new _userLoadListByCourseId();
            userLoadListByCourseId.init(this.testArgs.user);
            userLoadListByGroupId = new _userLoadListByGroupId();
            userLoadListByGroupId.init(this.testArgs.user);

            userLoadListByPrimaryRoleId = new _userLoadListByPrimaryRoleId();
            userLoadListByPrimaryRoleId.init(this.testArgs.user);


            loadBaseRecordAction = userLoadRecordByName;
            loadInsertedRecordAction = userLoadRecordByBatchUid_inserted;
            insertRecordAction = userInsertRecordByBatchUid_minimal;
            deleteRecordAction = userDeleteRecordByBatchUid;
        }
    }

    class _userLoadRecordByBatchUid : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            String batch_uid = args.wsResultRecord.batchUid;
            args.ClearInputsAndResults();
            args.wsInputRecord.batchUid = batch_uid;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }

    class _userLoadRecordByBatchUid_inserted : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.wsInputRecord.batchUid = "batchUid" + args.currentTestKeySuffix;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _userLoadListByTemplate : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.preAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListByTemplate(args.param, args.wsInputRecord);
        }
    }
    class _userInsertRecordByBatchUid_duplicate : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord = args.wsResultRecord;
            String batchUid = "batchUid" + args.currentTestKeySuffix;
            args.UpdateFieldsMinimal();
            args.wsInputRecord.batchUid = batchUid;
            args.wsInputRecord.userName = "userName" + args.currentTestKeySuffix;
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userInsertRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _userInsertRecordByBatchUid_minimal : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.SetFieldsMinimal();
        }
        override public void postAction() {
            args.wsInputRecord.batchUid = "batchUid" + args.currentTestKeySuffix;
            args.userDeleteRecordByBatchUid.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userInsertRecordByBatchUid(args.param, args.wsInputRecord);
        }

    }

    class _userUpdateRecordByBatchUid : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.currentTestKeySuffix = args.testKeySuffixes[0];
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.UpdateFieldsMinimal();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userUpdateRecordByBatchUid(args.param, args.wsInputRecord);
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
    }
    class _userPersistRecordByBatchUid : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            throw new Exception("Has to be overriden in successor.");
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userPersistRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }


    class _userPersistRecordByBatchUid_insert : _userPersistRecordByBatchUid {
        override public void preAction() {
            args.insertRecordAction.preAction();
        }
    }
    class _userPersistRecordByBatchUid_update : _userPersistRecordByBatchUid {
        override public void preAction() {
            args.userUpdateRecordByBatchUid.preAction();
        }
    }

    class _userDeleteRecordByBatchUid : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            String batch_uid = args.wsInputRecord.batchUid;
            args.ClearInputsAndResults();
            args.wsInputRecord.batchUid = batch_uid;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userDeleteRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }

    class _userPersistListByBatchUid : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            //inserts record
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
            //updates just inserted record
            args.userUpdateRecordByBatchUid.preAction();
            args.MoveInputRecordToList();
            args.ClearResults();
        }
        override public void postAction() {
            args.insertRecordAction.preAction();
            args.insertRecordAction.postAction();
            args.userUpdateRecordByBatchUid.preAction();
            args.userUpdateRecordByBatchUid.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userPersistListByBatchUid(args.param, args.wsInputList.ToArray());
        }
    }

    class _userDeleteListByBatchUid : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            List<String> buid_list = new List<String>();
            args.insertRecordAction.PreActionAndExecuteImp();
            buid_list.Add(args.wsInputRecord.batchUid);
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.PreActionAndExecuteImp();
            buid_list.Add(args.wsInputRecord.batchUid);
            args.ClearInputsAndResults();
            foreach (String str_buid in buid_list) {
                args.wsInputRecord.batchUid = str_buid;
                args.MoveInputRecordToList();
            }
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userDeleteListByBatchUid(args.param, args.wsInputList.ToArray());
        }
    }
    class _userLoadRecordById : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            String id = args.wsResultRecord.bbId;
            args.ClearInputsAndResults();
            args.wsInputRecord.bbId = id;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordById(args.param, args.wsInputRecord);
        }
    }
    class _userLoadRecordByName : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.wsInputRecord.userName = args.baseUserName;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordByName(args.param, args.wsInputRecord);
        }
    }
    class _userInsertRecordById_minimal : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.SetFieldsMinimal();
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userInsertRecordById(args.param, args.wsInputRecord);
        }
    }
    class _userUpdateRecordById : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.UpdateFieldsMinimal();
            args.wsInputRecord.batchUid = "batchUid" + args.currentTestKeySuffix;
            args.wsInputRecord.userName = "userName" + args.currentTestKeySuffix;
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userUpdateRecordById(args.param, args.wsInputRecord);
        }
    }
    class _userUpdateRecordById_extended : _userUpdateRecordById, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            ShowResultListTableAndDataLog();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.SetFieldsExtended();
            args.wsInputRecord.batchUid = "batchUid" + args.currentTestKeySuffix;
            args.wsInputRecord.userName = "userName" + args.currentTestKeySuffix;
        }
    }
    class _userPersistRecordById : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            throw new Exception("Has to be overriden in successor.");
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userPersistRecordById(args.param, args.wsInputRecord);
        }
    }

    class _userPersistRecordById_insert : _userPersistRecordById, ITestAction {
        override public void preAction() {
            args.insertRecordAction.preAction();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
    }
    class _userPersistRecordById_update : _userPersistRecordById, ITestAction {
        override public void preAction() {
            args.userUpdateRecordById.preAction();
        }
        override public void postAction() {
            args.userUpdateRecordById.postAction();
        }
    }

    class _userDeleteRecordById : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            String id = args.wsResultRecord.bbId;
            args.ClearInputsAndResults();
            args.wsInputRecord.bbId = id;
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userDeleteRecordById(args.param, args.wsInputRecord);
        }
    }
    class _userLoadListById : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            //List<bbws.userDetails> = new List<bbws.userDetails>();
            //List<String> id_list = new List<String>();
            args.userLoadRecordByName.PreActionAndExecuteImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            //id_list.Add(args.wsResultRecord.bbId);
            args.MoveInputRecordToList();
            //CreateInputRecord();
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            //id_list.Add(args.wsResultRecord.bbId);
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.MoveInputRecordToList();
            //ClearInputsAndResults();
            args.ClearResults();
            //foreach (String str_id in id_list) {
            //args.wsInputRecord.bbId = str_id;
            //MoveInputRecordToList();
            //}
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _userInsertListById : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            foreach (String key_suff in args.testKeySuffixes) {
                args.currentTestKeySuffix = key_suff;
                args.insertRecordAction.preAction();
                args.MoveInputRecordToList();
            }
            args.ClearResults();
        }
        override public void postAction() {
            foreach (String key_suff in args.testKeySuffixes) {
                args.currentTestKeySuffix = key_suff;
                args.insertRecordAction.postAction();
            }
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userInsertListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _userUpdateListById : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.currentTestKeySuffix = args.testKeySuffixes[0];
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord = args.wsResultRecord;
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.UpdateFieldsMinimal();
            args.MoveInputRecordToList();

            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord = args.wsResultRecord;
            args.currentTestKeySuffix = args.testKeySuffixes[2];
            args.UpdateFieldsMinimal();
            args.MoveInputRecordToList();
            args.ClearResults();
        }
        override public void postAction() {
            args.currentTestKeySuffix = args.testKeySuffixes[0];
            args.insertRecordAction.postAction();
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.postAction();
        }

        override public void executeImp() {
            args.wsResultList = args.bbWs.userUpdateListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _userPersistListById : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.userUpdateRecordById.PreActionAndExecuteImp();
            args.MoveInputRecordToList();
            args.currentTestKeySuffix = args.testKeySuffixes[2];
            args.insertRecordAction.preAction();
            args.MoveInputRecordToList();
        }
        override public void postAction() {
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.postAction();
            args.currentTestKeySuffix = args.testKeySuffixes[2];
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userPersistListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _userDeleteListById : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.userLoadRecordByName.executeImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.MoveInputRecordToList();
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.PreActionAndExecuteImp();
            args.userLoadRecordByName.executeImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.MoveInputRecordToList();
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userDeleteListById(args.param, args.wsInputList.ToArray());
        }
    }
    class _userLoadListByEmailAddressFamilyNameGivenName_email : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            String email = args.wsInputRecord.emailAddress;
            String fname = args.wsInputRecord.familyName;
            String gname = args.wsInputRecord.givenName;
            args.ClearInputsAndResults();
            args.wsInputRecord.emailAddress = email;
            args.wsInputRecord.familyName = fname;
            args.wsInputRecord.givenName = gname;
            //??!! - by names
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListByEmailAddressFamilyNameGivenName(args.param, args.wsInputRecord);
        }
    }
    class _userLoadListBySearchByUserName : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.wsInputRecord.userName = "%";
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListBySearchByUserName(args.param, args.wsInputRecord);
        }
    }
    class _userLoadListObservedByObserverId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.testArgs.observerAssociation.insertRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord.batchUid = args.testArgs.observerAssociation.wsInputRecord.observerBatchUid;
            args.userLoadRecordByBatchUid.executeImp();
            args.ClearInputs();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
        }
        override public void postAction() {
            args.testArgs.observerAssociation.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListObservedByObserverId(args.param, args.wsInputRecord);
        }
    }

    class _userLoadListByCourseId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.testArgs.courseMembership.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.course.wsInputRecord = new bbws.courseDetails();
            args.testArgs.course.wsInputRecord.bbId = args.testArgs.courseMembership.wsResultRecord.courseBbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListByCourseId(args.param, args.wsInputRecord, args.testArgs.course.wsInputRecord);
        }
    }

    class _userLoadListByGroupId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.courseMembership.wsInputRecord.userBbId = args.wsResultRecord.bbId;
            args.testArgs.courseMembership.courseMembershipLoadListByTemplate.PreActionAndExecuteImp();
            //args.userLoadListByCourseId.preAction();
            //args.testArgs.group.wsResultList = args.bbGrpWs.getGroupDetailsForGivenCourseIdWithNamedElements(null, args.testArgs.courseMembership.wsResultRecord.courseBbId);
            args.testArgs.group.wsResultList = args.bbGrpWs.getGroupDetailsForGivenCourseIdWithNamedElements(null, args.testArgs.courseMembership.wsResultList[0].courseBatchUid);
            args.testArgs.group.wsInputRecord.bbId = args.testArgs.group.wsResultList[0].bbId;
        }
        override public void postAction() {
        }
        override public void executeImp() {
            bbws.groupDetails grpd = new bbws.groupDetails();
            grpd.bbId = args.testArgs.group.wsInputRecord.bbId;
            args.wsResultList = args.bbWs.userLoadListByGroupId(args.param, args.wsInputRecord, grpd);
        }
    }

    class _userLoadListByPrimaryRoleId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.testArgs.portalRole.portalRoleLoadRecordPrimaryRoleByUserId.PreActionAndExecuteImp();
            args.testArgs.portalRole.ClearInputs();
            args.testArgs.portalRole.wsInputRecord.bbId = args.testArgs.portalRole.wsResultRecord.bbId;
        }
        override public void postAction() {
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListByPrimaryPortalRoleId(args.param, args.wsInputRecord, args.testArgs.portalRole.wsInputRecord);
        }
    }

}
