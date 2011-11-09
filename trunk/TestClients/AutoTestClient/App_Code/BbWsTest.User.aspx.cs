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
//using bbIDLA.BBAddedService;
using bbws;

public partial class BbWsTest : System.Web.UI.Page
{
    protected void RunUserTest() {
        //testArgs.ClearAllTestData();//!!
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

        testArgs.user.userLoadListAvailableObserversByCourseId.execute();
        testArgs.user.userLoadListObservedByObserverId.execute();
        testArgs.user.userLoadListByGroupId.execute();
        testArgs.user.userLoadListByPrimaryRoleId.execute();
    }

    class _userTestCase_SuccessRecord : BbWsTest.TestCase_SuccessRecord<_userTestArgs, userDetails>, ITestAction { }
    class _userTestCase_SuccessList : BbWsTest.TestCase_SuccessList<_userTestArgs, userDetails>, ITestAction { }

    
    class _userTestArgs : TestArgs<userDetails> {
    
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
            wsInputRecord.batchUid = "batchUid" + currentTestKeySuffix;
            wsInputRecord.userName = "username" + currentTestKeySuffix;
            wsInputRecord.familyName = "familyName" + currentTestKeySuffix;
            wsInputRecord.givenName = "givenName" + currentTestKeySuffix;
            wsInputRecord.middleName = "middleName" + currentTestKeySuffix;
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
                        str_value = "3902854230";
                        break;
                        //continue;
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
                        str_value = param.missFieldTag;
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
                        //Tested creation of role with the same name (but different Role Id) as built in one – it was created and its name got ".role_name" suffix too. Analyzed other DB tables - built-in course_roles and system_roles have ".name" suffix, it looks to come from column name. 
                        //More comments are in BbWsTest.PortalRole.aspx.cs
                        //!! Does not work in 9.1 SP5
                        //str_value = "ALUmni";
                        //str_value = "Alumni";
                        //str_value = "ALUmni" + ".role_name";
                        //str_value = "FACULTY" + ".role_name";
                        //str_value = "Staff";
                        str_value = param.missFieldTag;
                        break;
                    case "portalRoleId":
                        //either portalRoleName or portalRoleId has to be set, portalRoleName overrides portalRoleId, 
                        // but if values do not point to same portal role it will generate BbWsError upon field compare
                        str_value = "_2_1";
                        //str_value = "_27_1";
                        //str_value = param.missFieldTag;
                        break;
                    //case "batchUid": 
                    //case "userName": continue;
                    case "password":
                        str_value = "bla-bla";
                        //md5 hash of "bla-bla":
                        //str_value = "636F03926A5A3EB24DAF67461D8A075B";
                        
                        //byte[] data_buffer = System.Text.Encoding.UTF8.GetBytes(str_value);
                        byte[] data_buffer = System.Text.Encoding.Unicode.GetBytes(str_value);
                        System.Security.Cryptography.MD5 md = new System.Security.Cryptography.MD5CryptoServiceProvider();
                        byte[] md5_hash = md.ComputeHash(data_buffer);
                        str_value = System.Convert.ToString(md5_hash);
                        str_value = System.Convert.ToBase64String(md5_hash);
                        str_value = BitConverter.ToString(md5_hash).Replace("-", string.Empty);

                        //continue;
                        break;
                    case "middleName": //test that "" is not transferred as null (otherwise may get interpreted as missFieldTag which is null by default 
                        str_value = "";
                        break;
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
        public _userLoadListAvailableObserversByCourseId userLoadListAvailableObserversByCourseId;
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
            userLoadListAvailableObserversByCourseId = new _userLoadListAvailableObserversByCourseId();
            userLoadListAvailableObserversByCourseId.init(this.testArgs.user);
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
            String data_verb = args.param.dataVerbosity;
            args.param.dataVerbosity = "EXTENDED";
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.param.dataVerbosity = data_verb;
            String batch_uid = args.wsResultRecord.batchUid;
            args.ClearInputsAndResults();
            args.wsInputRecord.batchUid = batch_uid;
            args.wsInputRecord.businessFax = args.param.missFieldTag;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }

    class _userLoadRecordByBatchUid_inserted : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.wsInputRecord.batchUid = "batchUid" + args.currentTestKeySuffix;
            args.ClearResults();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userLoadRecordByBatchUid(args.param, args.wsInputRecord);
        }
    }
    class _userLoadListByTemplate : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.preAction();
            args.ClearResults();
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
            //cdRomDrivePC with empty string value causes BbWsError - setWsField(): APIs returned different values
            args.wsInputRecord.cdRomDrivePC = args.param.missFieldTag;
            args.wsInputRecord.batchUid = batchUid;
            args.wsInputRecord.userName = "userName" + args.currentTestKeySuffix;
            args.SetStandardUnsetabbleFieldsToMissFieldTag();
            args.ClearResults();
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
            args.ClearResults();
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
            args.currentTestKeySuffix = args.testKeySuffixes[0];
            args.ClearResults();
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
            args.ClearResults();
        }
    }
    class _userPersistRecordByBatchUid_update : _userPersistRecordByBatchUid {
        override public void preAction() {
            args.userUpdateRecordByBatchUid.preAction();
            args.ClearResults();
        }
    }

    class _userDeleteRecordByBatchUid : _userTestCase_SuccessRecord, ITestAction {
        override public void preAction() {
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            String batch_uid = args.wsInputRecord.batchUid;
            args.ClearInputsAndResults();
            args.wsInputRecord.batchUid = batch_uid;
            args.ClearResults();
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
            args.ClearResults();
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
            args.ClearResults();
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
            args.ClearResults();
        }
        override public void postAction() {
            args.deleteRecordAction.executeImp();
        }
        override public void executeImp() {
            args.wsResultRecord = args.bbWs.userUpdateRecordById(args.param, args.wsInputRecord);
        }
    }
    //!! method name and test result are not printed correctly, probably because of something in inheritance
    //?? seem to print now ok
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
            args.ClearResults();
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
            args.ClearResults();
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
            args.ClearResults();
        }
    }
    class _userPersistRecordById_update : _userPersistRecordById, ITestAction {
        override public void preAction() {
            args.userUpdateRecordById.preAction();
            args.ClearResults();
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
            args.userLoadRecordByName.PreActionAndExecuteImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            //??cdRomDrivePC with empty string value causes BbWsError - setWsField(): APIs returned different values
            args.wsInputRecord.cdRomDrivePC = args.param.missFieldTag;
            args.MoveInputRecordToList();
            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.executeImp();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            //cdRomDrivePC with empty string value causes BbWsError - setWsField(): APIs returned different values
            args.wsInputRecord.cdRomDrivePC = args.param.missFieldTag;
            args.MoveInputRecordToList();
            //ClearInputsAndResults();
            args.ClearResults();
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
            //??cdRomDrivePC with empty string value causes BbWsError - setWsField(): APIs returned different values
            args.wsInputRecord.cdRomDrivePC = args.param.missFieldTag;
            args.SetStandardUnsetabbleFieldsToMissFieldTag();
            args.MoveInputRecordToList();

            args.insertRecordAction.PreActionAndExecuteImp();
            args.loadInsertedRecordAction.PreActionAndExecuteImp();
            args.wsInputRecord = args.wsResultRecord;
            args.currentTestKeySuffix = args.testKeySuffixes[2];
            args.UpdateFieldsMinimal();
            //??cdRomDrivePC with empty string value causes BbWsError - setWsField(): APIs returned different values
            args.wsInputRecord.cdRomDrivePC = args.param.missFieldTag;
            args.SetStandardUnsetabbleFieldsToMissFieldTag();
            args.MoveInputRecordToList();
            args.ClearResults();
        }
        override public void postAction() {
            args.currentTestKeySuffix = args.testKeySuffixes[0];
            args.insertRecordAction.postAction();
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.postAction();
            args.currentTestKeySuffix = args.testKeySuffixes[0];
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
            //args.userLoadRecordByName.executeImp(); //!! in 9.1 this loads previously created and already deleted record with batchUid_bbws_02 and even  username_bbws_02... smells on some problem with caches like the one happened in courseCopy... but how Bb manages to load record by name with different name... may be both deleting AND previously made update of  username_bbws_02 (if it was initially  username_bbws_01) did not changed some search index... calling of userLoadRecordByName here is not necessary, but the problem has to be studied
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.MoveInputRecordToList();
            args.currentTestKeySuffix = args.testKeySuffixes[1];
            args.insertRecordAction.PreActionAndExecuteImp();
            args.userLoadRecordByName.executeImp();
            args.CreateInputRecord();
            args.wsInputRecord.bbId = args.wsResultRecord.bbId;
            args.MoveInputRecordToList();
            args.currentTestKeySuffix = args.testKeySuffixes[0];
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
        }
        override public void postAction() {
            args.insertRecordAction.postAction();
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
            args.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListObservedByObserverId(args.param, args.wsInputRecord);
        }
    }

    class _userLoadListByCourseId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.testArgs.courseMembership.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.course.wsInputRecord = new courseDetails();
            args.testArgs.course.wsInputRecord.bbId = args.testArgs.courseMembership.wsResultRecord.courseBbId;
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListByCourseId(args.param, args.wsInputRecord, args.testArgs.course.wsInputRecord);
        }
    }

    class _userLoadListAvailableObserversByCourseId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.testArgs.courseMembership.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.course.wsInputRecord = new courseDetails();
            args.testArgs.course.wsInputRecord.bbId = args.testArgs.courseMembership.wsResultRecord.courseBbId;
            args.testArgs.observerAssociation.insertRecordAction.PreActionAndExecuteImp();
        }
        override public void postAction() {
            args.testArgs.observerAssociation.insertRecordAction.postAction();
        }
        override public void executeImp() {
            args.wsResultList = args.bbWs.userLoadListAvailableObserversByCourseId(args.param, args.wsInputRecord, args.testArgs.course.wsInputRecord);
        }
    }

    class _userLoadListByGroupId : _userTestCase_SuccessList, ITestAction {
        override public void preAction() {
            args.loadBaseRecordAction.PreActionAndExecuteImp();
            args.testArgs.courseMembership.wsInputRecord.userBbId = args.wsResultRecord.bbId;
            args.testArgs.courseMembership.courseMembershipLoadListByTemplate.PreActionAndExecuteImp();
            //don't forget to allow access to getGroupDetailsForGivenCourseIdWithNamedElements from building block settings
            //System.Net.WebException: The request failed with HTTP status 404: Not Found. - update App_WebReferences
            args.testArgs.group.wsResultList = args.bbGrpWs.getGroupDetailsForGivenCourseIdWithNamedElements(null, args.testArgs.courseMembership.wsResultList[0].courseBatchUid);
            args.testArgs.group.wsInputRecord.bbId = args.testArgs.group.wsResultList[0].bbId;
        }
        override public void postAction() {
        }
        override public void executeImp() {
            groupDetails grpd = new groupDetails();
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
