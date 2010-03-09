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
    protected void RunCourseTest() {
        testArgs.ClearAllTestData();
        testArgs.course.courseCopyCourseExact.execute();
    }

    class _courseTestArgs : TestArgs<bbws.courseDetails> {
        public override bbws.bbWsDataLogRecord[] getDataLogArray() {
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

        public override void SetFieldsMinimal() { }
        public override void SetFieldsExtended() { }
        public override void UpdateFieldsMinimal() { }

        public _courseCopyCourseExact courseCopyCourseExact;


        override public void init(TestArgsStruct testArgs) {
            base.init(testArgs);
            courseCopyCourseExact = new _courseCopyCourseExact();
            courseCopyCourseExact.init(this);
        }
    }

    class _courseTestCase : TestCase_SuccessRecord<_courseTestArgs, bbws.courseDetails>, ITestAction {
        //Boolean ResultBool;
    }

    class _courseCopyCourseExact : _courseTestCase {
        override public void preAction() {
            args.testArgs.courseMembership.courseMembershipLoadListByTemplate.PreActionAndExecuteImp();
            args.wsInputRecord.batchUid = args.testArgs.courseMembership.wsResultList[0].courseBatchUid;
            args.ClearResults();
        }
        override public void postAction() {
            Boolean b_spec = true;
            Boolean b;
            //String course_batch_uid = args.testArgs.courseMembership.wsInputRecord.courseBatchUid;
            String course_batch_uid = args.wsInputRecord.batchUid;
            String new_course_batch_uid = course_batch_uid + args.currentTestKeySuffix;
            args.bbCrsWs.deleteCourseByCourseId(null, new_course_batch_uid, out b, out b_spec);
        }
        override public void executeImp() {
            Boolean b_spec = true;
            Boolean b;
            //String course_batch_uid = args.testArgs.courseMembership.wsInputRecord.courseBatchUid;
            String course_batch_uid = args.wsInputRecord.batchUid;
            String new_course_batch_uid = course_batch_uid + args.currentTestKeySuffix;
            args.bbCrsWs.copyCourseExact(null, course_batch_uid, new_course_batch_uid, out b, out b_spec);
            args.wsResultRecord = args.wsInputRecord;
            args.wsResultRecord.bbWsBoolResult = b.ToString().ToLower();
        }
    }
//!!updateCourse
}
