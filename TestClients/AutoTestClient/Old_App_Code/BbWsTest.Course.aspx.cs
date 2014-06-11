//#define BB_ADDED//!!

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

#if (BB_ADDED)
using bbIDLA.BBAddedService;//--this is for work through DotNetProxy
#else
using IDLA.WS.AutoTestClient.bbws;//--this is for direct web services access
#endif


namespace IDLA.WS.AutoTestClient {
    public partial class BbWsTest : System.Web.UI.Page {
        protected void RunCourseTest() {
            //testArgs.ClearAllTestData();
                    
                    testArgs.course.courseLoadRecordById.execute();
                    testArgs.course.courseLoadRecordByCourseId.execute();
                    testArgs.course.courseLoadRecordByBatchUid.execute();
                    testArgs.course.courseLoadListByTemplate.execute();
                    testArgs.course.courseInsertRecordById.execute();
                    testArgs.course.courseUpdateRecordById.execute();
                    testArgs.course.coursePersistRecordById.execute();
                    testArgs.course.courseDeleteRecordById.execute();
                    testArgs.course.courseCopyRecordById.execute();
                    testArgs.course.courseCopyRecordByBatchUid.execute();
                    testArgs.course.courseInsertRecordByBatchUid.execute();
                    testArgs.course.courseUpdateRecordByBatchUid.execute();
                    testArgs.course.coursePersistRecordByBatchUid.execute();
            
                    testArgs.course.courseDeleteRecordByBatchUid.execute();
             
                    testArgs.course.courseCopyRecordWithParamsById.execute();
             
                testArgs.course.courseCopyRecordWithParamsByBatchUid.execute();
                testArgs.course.courseMergeRecordWithParamsById.execute();
                testArgs.course.courseMergeRecordWithParamsByBatchUid.execute();
         
          


            //testArgs.course.courseMergeRecordWithParamsById.execute();
            //testArgs.course.courseMergeRecordWithParamsByBatchUid.execute();
            /*
            testArgs.course.courseMergeRecordWithParamsById.execute();
            testArgs.course.courseMergeRecordWithParamsByBatchUid.execute();
            */
        }

        class _courseTestArgs : TestArgs<courseDetails> {
            public override bbWsDataLogRecord[] getDataLogArray() {
                //Error	1	Cannot implicitly convert type 'bbcrsws.bbWsDataLogRecord[]' to 'bbws.bbWsDataLogRecord[]'	W:\BB-webservice\src\wservices_idla\TestClients\VS2005\BbWebservices.aspx	380	20	W:\...\VS2005\
                return wsResultRecord.bbWsDataLog;
                //return null;
            }

            public override String getBbWsBoolResult() {
                return wsResultRecord.bbWsBoolResult;
            }
            public override String getBbWsRecordResultId() {
                return wsResultRecord.bbId;
            }

            public override void SetFieldsMinimal() {
                wsInputRecord.batchUid = "batchUid" + currentTestKeySuffix;
                wsInputRecord.title = wsInputRecord.title + currentTestKeySuffix;
            }
            public override void UpdateFieldsMinimal() { }


            public override void SetFieldsExtended() {
                System.Reflection.PropertyInfo[] props = wsInputRecord.GetType().GetProperties();
                foreach (System.Reflection.PropertyInfo prop in props) {
                    //wsResultRecord.
                    Object value = prop.GetValue(wsInputRecord, null);
                    String str_value = Convert.ToString(value);
                    switch (prop.Name) {
                        case "bbId":
                            continue;
                        case "batchUid":
                            continue;
                        case "courseId":
                            continue;
                        case "bbWsDataLog":
                            continue;
                        case "dataSourceBbId":
                            continue;
                        case "creationDate":
                            str_value = param.missFieldTag;
                            break;
                        case "modifiedDate":
                            str_value = param.missFieldTag;
                            break;
                        case "navigationBackgroundColor":
                            //continue;
                            str_value = "#FFFFFE";
                            break;
                        case "navigationForegroundColor":
                            //continue;
                            str_value = "#0033CB";
                            break;
                        case "cartridgeId":
                            continue;
                        case "absoluteLimit":
                            //continue;
                            str_value = "123";
                            break;
                        case "durationType": //CONTINUOUS (DEFAULT), DATE_RANGE, FIXED_NUM_DAYS
                            //continue;
                            str_value = "FIXED_NUM_DAYS";
                            break;
                        case "endDate":
                            //continue;
                            str_value = "2010-11-20 14:29:53";
                            break;
                        case "enrollmentType": //INSTRUCTOR_LED (DEFAULT), SELF_ENROLLMENT, EMAIL_ENROLLMENT
                            //continue;
                            str_value = "EMAIL_ENROLLMENT";
                            break;
                        case "enrollmentStartDate":
                            //continue;
                            str_value = "2010-05-20 13:29:53";
                            break;
                        case "enrollmentEndDate":
                            //continue;
                            str_value = "2010-10-20 12:29:53";
                            break;
                        case "navigationStyle": //TEXT (DEFAULT), BUTTON 
                            //continue;
                            str_value = "BUTTON";
                            break;
                        case "numberOfDaysOfUse":
                            //continue;
                            str_value = "145";
                            break;
                        case "paceType": //INSTRUCTOR_LED (DEFAULT), SELF_PACED
                            //continue;
                            str_value = "SELF_PACED";
                            break;
                        case "serviceLevelType": //FULL (DEFAULT), COMMUNITY, REGISTERED, TEST_DRIVE, SYSTEM
                            //continue;
                            //this hides record from Administrative GUI
                            //str_value = "TEST_DRIVE";
                            //this too
                            //str_value = "COMMUNITY";
                            //this too
                            //str_value = "REGISTERED";
                            //this too
                            //str_value = "SYSTEM"; 
                            str_value = "FULL";
                            break;
                        case "softLimit":
                            //continue;
                            str_value = "170";
                            break;
                        case "startDate":
                            //continue;
                            str_value = "2010-09-20 11:29:53";
                            break;
                        case "uploadLimit":
                            //continue;
                            str_value = "220";
                            break;
                        case "isAvailable":
                            //continue;
                            str_value = "false";
                            break;
                        case "allowGuests":
                            //continue;
                            str_value = "false";
                            break;
                        case "allowObservers":
                            //continue;
                            str_value = "true";
                            break;
                        case "bannerImageFile":
                            //continue;
                            str_value = "C:\\" + prop.Name + currentTestKeySuffix;
                            break;
                        case "buttonStyleId": //bb_bb60.dbo.buttonstyles
                            //continue;
                            str_value = "_106_1";
                            break;
                        case "classificationId": //bb_bb60.dbo.classifications
                            //continue;
                            str_value = "_115_1";
                            break;
                        case "isLocaleEnforced":
                            //continue;
                            str_value = "true";
                            break;
                        case "isLockedOut":
                            //continue;
                            str_value = "true";
                            break;
                        case "isNavigationCollapsible":
                            //continue;
                            str_value = "false";
                            break;
                        case "locale":
                            //incorrect locale value causes error with ID 2f7fa692-4b53-4343-89c0-2c1ed7078c7b in GUI upon class edit attempt
                            continue;
                        default:
                            str_value = prop.Name + currentTestKeySuffix;
                            break;
                    }
                    prop.SetValue(wsInputRecord, str_value, null);
                }
            }


            //..        public _courseCopyCourseExact courseCopyCourseExact;

            public _courseLoadRecordById courseLoadRecordById;
            public _courseLoadRecordByCourseId courseLoadRecordByCourseId;
            public _courseLoadRecordByBatchUid courseLoadRecordByBatchUid;
            public _courseLoadListByTemplate courseLoadListByTemplate;
            public _courseInsertRecordById courseInsertRecordById;
            public _courseUpdateRecordById courseUpdateRecordById;
            public _coursePersistRecordById coursePersistRecordById;
            public _courseDeleteRecordById courseDeleteRecordById;
            public _courseCopyRecordById courseCopyRecordById;
            public _courseCopyRecordByBatchUid courseCopyRecordByBatchUid;
            public _courseInsertRecordByBatchUid courseInsertRecordByBatchUid;
            public _courseUpdateRecordByBatchUid courseUpdateRecordByBatchUid;
            public _coursePersistRecordByBatchUid coursePersistRecordByBatchUid;
            public _courseDeleteRecordByBatchUid courseDeleteRecordByBatchUid;
            public _courseCopyRecordWithParamsById courseCopyRecordWithParamsById;
            public _courseCopyRecordWithParamsByBatchUid courseCopyRecordWithParamsByBatchUid;
            public _courseMergeRecordWithParamsById courseMergeRecordWithParamsById;
            public _courseMergeRecordWithParamsByBatchUid courseMergeRecordWithParamsByBatchUid;



            override public void init(TestArgsStruct testArgs) {
                base.init(testArgs);
                //..courseCopyCourseExact = new _courseCopyCourseExact();
                //..courseCopyCourseExact.init(this);
                courseLoadRecordById = new _courseLoadRecordById();
                courseLoadRecordById.init(this.testArgs.course);
                courseLoadRecordByCourseId = new _courseLoadRecordByCourseId();
                courseLoadRecordByCourseId.init(this.testArgs.course);
                courseLoadRecordByBatchUid = new _courseLoadRecordByBatchUid();
                courseLoadRecordByBatchUid.init(this.testArgs.course);
                courseLoadListByTemplate = new _courseLoadListByTemplate();
                courseLoadListByTemplate.init(this.testArgs.course);
                courseInsertRecordById = new _courseInsertRecordById();
                courseInsertRecordById.init(this.testArgs.course);
                courseUpdateRecordById = new _courseUpdateRecordById();
                courseUpdateRecordById.init(this.testArgs.course);
                coursePersistRecordById = new _coursePersistRecordById();
                coursePersistRecordById.init(this.testArgs.course);
                courseDeleteRecordById = new _courseDeleteRecordById();
                courseDeleteRecordById.init(this.testArgs.course);
                courseCopyRecordById = new _courseCopyRecordById();
                courseCopyRecordById.init(this.testArgs.course);
                courseCopyRecordByBatchUid = new _courseCopyRecordByBatchUid();
                courseCopyRecordByBatchUid.init(this.testArgs.course);
                courseInsertRecordByBatchUid = new _courseInsertRecordByBatchUid();
                courseInsertRecordByBatchUid.init(this.testArgs.course);
                courseUpdateRecordByBatchUid = new _courseUpdateRecordByBatchUid();
                courseUpdateRecordByBatchUid.init(this.testArgs.course);
                coursePersistRecordByBatchUid = new _coursePersistRecordByBatchUid();
                coursePersistRecordByBatchUid.init(this.testArgs.course);
                courseDeleteRecordByBatchUid = new _courseDeleteRecordByBatchUid();
                courseDeleteRecordByBatchUid.init(this.testArgs.course);
                courseCopyRecordWithParamsById = new _courseCopyRecordWithParamsById();
                courseCopyRecordWithParamsById.init(this.testArgs.course);
                courseCopyRecordWithParamsByBatchUid = new _courseCopyRecordWithParamsByBatchUid();
                courseCopyRecordWithParamsByBatchUid.init(this.testArgs.course);
                courseMergeRecordWithParamsById = new _courseMergeRecordWithParamsById();
                courseMergeRecordWithParamsById.init(this.testArgs.course);
                courseMergeRecordWithParamsByBatchUid = new _courseMergeRecordWithParamsByBatchUid();
                courseMergeRecordWithParamsByBatchUid.init(this.testArgs.course);

            }
        }

        class _courseTestCase : TestCase_SuccessRecord<_courseTestArgs, courseDetails>, ITestAction {
            //Boolean ResultBool;
        }
        class _courseTestCase_SuccessList : BbWsTest.TestCase_SuccessList<_courseTestArgs, courseDetails>, ITestAction { }


        class _courseLoadRecordById : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseLoadRecordById(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                String id = args.wsResultRecord.bbId;
                args.ClearInputsAndResults();
                args.wsInputRecord.bbId = id;
            }
        }
        class _courseLoadRecordByCourseId : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseLoadRecordByCourseId(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.wsInputRecord.courseId = args.baseCourseId;
            }
        }
        class _courseLoadRecordByBatchUid : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseLoadRecordByBatchUid(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                String batch_uid = args.wsResultRecord.batchUid;
                args.ClearInputsAndResults();
                args.wsInputRecord.batchUid = batch_uid;
                //args.wsInputRecord.batchUid = "TestClass_001_ID"; //!!
                //args.wsInputRecord.batchUid = "TestClass_001_ID_bbws_01"; //!!
                //args.wsInputRecord.batchUid = "TestClass_001_ID_bbws_01upd"; //!!
            }
        }
        class _courseLoadListByTemplate : _courseTestCase_SuccessList, ITestAction {
            override public void executeImp() {
                args.wsResultList = args.bbWs.courseLoadListByTemplate(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.ClearInputsAndResults();
                args.wsInputRecord.title = "%";
            }
        }
        class _courseInsertRecordById : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseInsertRecordById(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;
                //args.currentTestKeySuffix = args.testKeySuffixes[1];  //!!
                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + args.currentTestKeySuffix;
                args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;
                args.SetFieldsExtended();//!!
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.wsInputRecord.bannerImageFile = args.param.missFieldTag;
                args.ClearResults();
            }
            override public void postAction() {
                args.wsInputRecord.bbId = args.wsResultRecord.bbId;
                args.courseDeleteRecordById.executeImp();
            }
        }
        class _courseUpdateRecordById : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseUpdateRecordById(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseInsertRecordById.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;

                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + "upd";
                //courseId is not modifiable
                //args.wsInputRecord.courseId = args.wsInputRecord.courseId + "upd";
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.wsInputRecord.bannerImageFile = args.param.missFieldTag;
                args.ClearResults();
            }
            override public void postAction() {
                args.courseInsertRecordById.postAction();
            }
        }
        class _coursePersistRecordById : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.coursePersistRecordById(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;
                args.wsInputRecord.bbId = args.param.missFieldTag;
                args.SetFieldsExtended();
                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + args.currentTestKeySuffix;
                args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.ClearResults();
            }
            override public void postAction() {
                args.courseInsertRecordById.postAction();
            }
        }
        class _courseDeleteRecordById : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseDeleteRecordById(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseInsertRecordById.PreActionAndExecuteImp();
                args.ClearInputs();
                args.wsInputRecord.bbId = args.wsResultRecord.bbId;
                args.ClearResults();
            }
            override public void postAction() {
            }
        }
        class _courseCopyRecordById : _courseTestCase, ITestAction {
            protected courseDetails target_course = new courseDetails();
            String id;
            protected String batch_uid;
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseCopyRecordById(args.param, args.wsInputRecord, target_course);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                id = args.wsResultRecord.bbId;
                batch_uid = args.wsResultRecord.batchUid;
                args.wsInputRecord = args.wsResultRecord;

                args.SetFieldsExtended();
                //args.SetFieldsMinimal();
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.wsInputRecord.bbId = args.param.missFieldTag;
                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + args.currentTestKeySuffix;
                args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;
                args.wsInputRecord.isAvailable = "false";//!!
                //??Sometimes, could reproduce only with args.wsInputRecord.isAvailable = "false", occurs (Bb rel. 9.0.440.0):
                //blackboard.persist.PersistenceException: blackboard.persist.PersistenceException: Process failure. NESTED EXCEPTION: blackboard.persist.PersistenceException: Process failure.
                //Root Cause: 
                //com.inet.tds.be: Msg 547, Level 16, State 0, Procedure course_users_exact_cp, Line 37, Sqlstate 23000
                //[W2KS\SQL2K5]The INSERT statement conflicted with the FOREIGN KEY constraint "course_users_fk2". The conflict occurred in database "bb_bb60", table "dbo.course_main", column 'pk1'.

                //args.wsInputRecord.isAvailable = "true"; //!!
                target_course = args.wsInputRecord;
                args.ClearInputs();
                args.ClearResults();
                args.wsInputRecord.bbId = id;
            }
            override public void postAction() {
                //args.wsInputRecord.bbId = args.wsResultRecord.bbId;
                //??args.courseDeleteRecordById.executeImp();
                //On Bb 9.1 SP5 some mystic happens when copied course record is deleted with courseDeleteRecordById
                //1. Upon first run, course is copied
                //2. After it was deleted with courseDeleteRecordById, second copy attempt fails with 
                //      blackboard.persist.PersistenceException: Process failure.
                //          Caused by blackboard.plugin.bbwiki.exception.BbWikiException: Failed to create Wiki
                //          Caused by: com.xythos.storageServer.api.DuplicateEntryException
                //  BUT, course actually gets copied and shows up in Bb GUI, 
                //  AND, values of at least isAvailable, allowGuests and navigationStyle fields in 
                //      this course are set different than passed, probably default ones (i.e. something is happening on field level too)
                //3. After record is deleted from GUI, copy starts working again

                args.wsInputRecord.batchUid = args.wsResultRecord.batchUid;
                args.courseDeleteRecordByBatchUid.executeImp();
            }
        }
        class _courseCopyRecordByBatchUid : _courseCopyRecordById, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseCopyRecordByBatchUid(args.param, args.wsInputRecord, target_course);
            }
            override public void preAction() {
                base.preAction();
                args.wsInputRecord.batchUid = batch_uid;
                args.wsInputRecord.bbId = args.param.missFieldTag;
            }
        }

        class _courseCopyRecordWithParamsById : _courseTestCase, ITestAction {
            protected courseDetails target_course;// = new courseDetails();
            protected courseCopyParams copy_params;// = new courseCopyParams();
            String id;
            protected String batch_uid;
            override public void executeImp() {
                //System.Threading.Thread.Sleep(5000);
                args.wsResultRecord = args.bbWs.courseCopyRecordWithParamsById(args.param, args.wsInputRecord, target_course, copy_params);
            }
            override public void preAction() {
                target_course = new courseDetails();
                copy_params = new courseCopyParams();

                args.ClearInputs();
                args.ClearResults();

                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                id = args.wsResultRecord.bbId;
                batch_uid = args.wsResultRecord.batchUid;
                args.wsInputRecord = args.wsResultRecord;
                args.SetFieldsExtended();
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                target_course = args.wsInputRecord;
                args.ClearInputs();
                target_course.bbId = args.param.missFieldTag;
                target_course.batchUid = target_course.batchUid + args.currentTestKeySuffix;
                target_course.courseId = target_course.courseId + args.currentTestKeySuffix;
                target_course.courseId = target_course.courseId + "_exc_MEMBERSHIP_GROUP_GRADEBOOK";
                //args.wsInputRecord.isAvailable = "false";//!!
                List<String> area_list = new List<String>();
                //bbwscommon.BbWsException: BbWsIdla custom error: CourseCloneParams include/exclude area lists may contain following values: 
                //[ASSESSMENT, CATEGORY_MEMBERSHIP, ANNOUNCEMENT, GLOSSARY, CALENDAR, RUBRIC, TASK, BLOG, JOURNAL, SETTING, STD_ALIGNMENT, CONTENT, COURSE_TOC, CHAT_ARCHIVE, CHAT_SESSION, DROP_BOX, COURSE_STATISTICS, STAFF_INFORMATION, EARLY_WARNING_SYSTEM, DISCUSSION_BOARD, DISCUSSION_BOARD_ARCHIVE, GROUP, GRADEBOOK, GRADES, GRADEBOOK_CLEAR, MEMBERSHIP, MEMBERSHIP_EXACT, COURSE_CARTRIDGE, DISCUSSION_CARTRIDGE, ASSESSMENT_CARTRIDGE, AVAILABILITY_RULE, ALL, DEFAULT] 
                //CAUSED BY: java.lang.IllegalArgumentException: Element not found in enumeration: COURSE_TOC1 of class: blackboard.admin.persist.course.CloneConfig$A
                area_list.Add("MEMBERSHIP");
                area_list.Add("GROUP");
                area_list.Add("GRADEBOOK");

                copy_params.excludeAreaList = area_list.ToArray();
                //??Sometimes, could reproduce only with args.wsInputRecord.isAvailable = "false", occurs (Bb rel. 9.0.440.0):
                //blackboard.persist.PersistenceException: blackboard.persist.PersistenceException: Process failure. NESTED EXCEPTION: blackboard.persist.PersistenceException: Process failure.
                //Root Cause: 
                //com.inet.tds.be: Msg 547, Level 16, State 0, Procedure course_users_exact_cp, Line 37, Sqlstate 23000
                //[W2KS\SQL2K5]The INSERT statement conflicted with the FOREIGN KEY constraint "course_users_fk2". The conflict occurred in database "bb_bb60", table "dbo.course_main", column 'pk1'.
                //args.wsInputRecord.isAvailable = "false"; //!!
                args.ClearResults();
                args.wsInputRecord.bbId = id;
            }
            override public void postAction() {
                //args.wsInputRecord.bbId = args.wsResultRecord.bbId;
                //??args.courseDeleteRecordById.executeImp();
                //On Bb 9.1 SP5 some mystic happens when copied course record is deleted with courseDeleteRecordById
                //1. Upon first run, course is copied
                //2. After it was deleted with courseDeleteRecordById, second copy attempt fails with 
                //      blackboard.persist.PersistenceException: Process failure.
                //          Caused by blackboard.plugin.bbwiki.exception.BbWikiException: Failed to create Wiki
                //          Caused by: com.xythos.storageServer.api.DuplicateEntryException
                //  BUT, course actually gets copied and shows up in Bb GUI, 
                //  AND, values of at least isAvailable, allowGuests and navigationStyle fields in 
                //      this course are set different than passed, probably default ones (i.e. something is happening on field level too)
                //3. After record is deleted from GUI, copy starts working again

                args.wsInputRecord.batchUid = args.wsResultRecord.batchUid;
                args.courseDeleteRecordByBatchUid.executeImp();
            }
        }

        class _courseMergeRecordWithParamsById : _courseCopyRecordWithParamsById, ITestAction {
            override public void executeImp() {
                //System.Threading.Thread.Sleep(155000);
                args.wsResultRecord = args.bbWs.courseMergeRecordWithParamsById(args.param, args.wsInputRecord, target_course, copy_params);
            }
            override public void preAction() {
                base.preAction();
                target_course.courseId = target_course.courseId + "_merged_inc_MEMBERSHIP_GROUP";
                base.executeImp();
                target_course = args.wsResultRecord;
                //args.wsInputRecord.isAvailable = "false";//!!
                List<String> area_list = new List<String>();
                area_list.Add("MEMBERSHIP");
                area_list.Add("GROUP");
                copy_params.excludeAreaList = null;
                copy_params.includeAreaList = area_list.ToArray();
                target_course.isAvailable = "true";
                //args.wsInputRecord.bannerImageFile = "C:\\bannerImageFile_bbws03";
                //args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.wsInputRecord.isAvailable = "false"; //!!
                String bbId = args.wsInputRecord.bbId;
                args.ClearInputs();
                args.ClearResults();
                args.wsInputRecord.bbId = bbId;
                //            args.wsResultRecord = args.bbWs.courseCopyRecordWithParamsById(args.param, args.wsInputRecord, target_course, copy_params);
            }
            override public void postAction() {
                args.wsInputRecord.batchUid = args.wsResultRecord.batchUid;
                args.courseDeleteRecordByBatchUid.executeImp();
            }
        }
        class _courseCopyRecordWithParamsByBatchUid : _courseCopyRecordWithParamsById, ITestAction {
            override public void executeImp() {
                //System.Threading.Thread.Sleep(5000);
                args.wsResultRecord = args.bbWs.courseCopyRecordWithParamsByBatchUid(args.param, args.wsInputRecord, target_course, copy_params);
            }
            override public void preAction() {
                base.preAction();
                args.ClearInputs();
                args.wsInputRecord.batchUid = batch_uid;
                args.wsInputRecord.bbId = args.param.missFieldTag;
            }
        }
        class _courseMergeRecordWithParamsByBatchUid : _courseMergeRecordWithParamsById, ITestAction {
            override public void executeImp() {
                //System.Threading.Thread.Sleep(155000);
                args.wsResultRecord = args.bbWs.courseMergeRecordWithParamsByBatchUid(args.param, args.wsInputRecord, target_course, copy_params);
            }
            override public void preAction() {
                base.preAction();
                args.wsInputRecord.batchUid = batch_uid;
                args.wsInputRecord.bbId = args.param.missFieldTag;
            }
        }

        class _courseInsertRecordByBatchUid : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseInsertRecordByBatchUid(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;
                //args.currentTestKeySuffix = args.testKeySuffixes[1];  //!!
                args.SetFieldsExtended();
                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + args.currentTestKeySuffix;
                args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;
                //args.wsInputRecord.isAvailable = "true"; //!!
                //args.wsInputRecord.isLockedOut = "false"; //!!
                //classificationId is not settable via InsertRecordByBatchUid (CourseSiteDbPersister.insert()) method
                args.wsInputRecord.classificationId = args.param.missFieldTag;

                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.ClearResults();
            }
            override public void postAction() {
                if (args.getBbWsBoolResult().CompareTo("true") == 0) {
                    args.wsInputRecord.bbId = args.wsResultRecord.bbId;
                    args.courseDeleteRecordById.executeImp();
                }
            }
        }
        class _courseUpdateRecordByBatchUid : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseUpdateRecordByBatchUid(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseInsertRecordById.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;

                //blackboard.data.ImmutableException: An immutable object can not be modified [Course id cannot be modified.].
                //args.wsInputRecord.courseId = args.wsInputRecord.courseId + "upd";
                args.wsInputRecord.title = args.wsInputRecord.title + "updated";
                args.wsInputRecord.bbId = args.param.missFieldTag;
                args.SetFieldsExtended();
                args.wsInputRecord.isAvailable = "true"; //!!
                //args.wsInputRecord.isLockedOut = "false"; //!!
                //Updating of courseId with courseUpdateRecordByBatchUid() causes
                //blackboard.data.ImmutableException: An immutable object can not be modified [Course id cannot be modified.]
                //args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;

                //classificationId is not settable via InsertRecordByBatchUid (CourseSiteDbPersister.insert()) method

                //args.wsInputRecord.classificationId = args.param.missFieldTag;
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.ClearResults();
            }
            override public void postAction() {
                args.courseInsertRecordById.postAction();
            }
        }
        class _coursePersistRecordByBatchUid : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.coursePersistRecordByBatchUid(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseLoadRecordByCourseId.PreActionAndExecuteImp();
                args.wsInputRecord = args.wsResultRecord;
                args.wsInputRecord.bbId = args.param.missFieldTag;
                args.SetFieldsExtended();
                args.wsInputRecord.batchUid = args.wsInputRecord.batchUid + args.currentTestKeySuffix;
                args.wsInputRecord.courseId = args.wsInputRecord.courseId + args.currentTestKeySuffix;
                //classificationId is not settable via ....ByBatchUid methods
                args.wsInputRecord.classificationId = args.param.missFieldTag;
                args.SetStandardUnsetabbleFieldsToMissFieldTag();
                args.ClearResults();
            }
            override public void postAction() {
                args.courseInsertRecordById.postAction();
            }
        }
        class _courseDeleteRecordByBatchUid : _courseTestCase, ITestAction {
            override public void executeImp() {
                args.wsResultRecord = args.bbWs.courseDeleteRecordByBatchUid(args.param, args.wsInputRecord);
            }
            override public void preAction() {
                args.courseInsertRecordById.PreActionAndExecuteImp();
                args.ClearInputs();
                args.wsInputRecord.batchUid = args.wsResultRecord.batchUid;
                args.ClearResults();
                //args.wsInputRecord.batchUid = "TestClass_001_ID_bbws_01"; //!!
            }
            override public void postAction() {
            }
        }

        /* //..
        class _courseCopyCourseExact : _courseTestCase {
            override public void preAction() {
                args.testArgs.courseMembership.courseMembershipLoadListByTemplate.PreActionAndExecuteImp();
                args.wsInputRecord.batchUid = args.testArgs.courseMembership.wsResultList[0].courseBatchUid;
                //args.wsInputRecord.batchUid = "TestClass_001_ID";
                args.ClearResults();
            }
            override public void postAction() {
                Boolean b_spec = true;
                Boolean b;
                String course_batch_uid = args.wsInputRecord.batchUid;
                String new_course_batch_uid = course_batch_uid + args.currentTestKeySuffix;
                args.bbCrsWs.deleteCourseByCourseId(args.param.password, new_course_batch_uid, out b, out b_spec);
            }
            override public void executeImp() {
                Boolean b_spec = true;
                Boolean b;
                String course_batch_uid = args.wsInputRecord.batchUid;
                String new_course_batch_uid = course_batch_uid + args.currentTestKeySuffix;
                args.bbCrsWs.Timeout = 900000;
                args.bbCrsWs.copyCourseExact(args.param.password, course_batch_uid, new_course_batch_uid, out b, out b_spec);
                args.wsResultRecord = args.wsInputRecord;
                args.wsResultRecord.bbWsBoolResult = b.ToString().ToLower();
            }
        }*/
        //!!updateCourse


    }
    
}