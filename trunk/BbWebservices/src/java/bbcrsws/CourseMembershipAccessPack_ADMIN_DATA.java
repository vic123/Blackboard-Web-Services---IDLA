/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbcrsws;

/**
 *
 * @author vic
 */

import blackboard.admin.data.course.Enrollment;
import blackboard.admin.persist.course.impl.EnrollmentDbLoader;

import blackboard.data.course.CourseMembership;

import bbwscommon.*;

public class CourseMembershipAccessPack_ADMIN_DATA <BbCourseMembershipType extends Enrollment,
        ArgumentsType extends CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA>
        extends CourseMembershipAccessPack<BbCourseMembershipType, ArgumentsType> {



    public static class LoadRecordByCourseAndUserBatchId extends
            CourseMembershipAccessPack_ADMIN_DATA<Enrollment, CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA> {
        public void initialize(CourseMembershipArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, Enrollment.class, da);
        }

        @Override protected void loadRecord() throws Exception {
            String c_bid = getArgs().getInputRecord().getCourseBatchUid();
            String u_bid = getArgs().getInputRecord().getUserBatchUid();
            bbObject = EnrollmentDbLoader.Default.getInstance().load(c_bid, u_bid);
        }
    }

    public static class LoadListByTemplate extends
            CourseMembershipAccessPack_ADMIN_DATA<Enrollment, CourseMembershipAccessPack.CourseMembershipArguments_ADMIN_DATA> {
        public void initialize(CourseMembershipArguments_ADMIN_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, Enrollment.class, da);
        }
        @Override protected void loadList() throws Exception {
            bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            bbObjectList = EnrollmentDbLoader.Default.getInstance().load(bbObject);
        }
    }


    @Override protected void setBbCourseBatchUidField() throws Exception {
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getCourseSiteBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getCourseBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setCourseSiteBatchUid(newValue);
                }
            }.setBbField("courseBatchUid");
    }

    @Override protected void setWsCourseBatchUidField() throws Exception {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getCourseSiteBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getCourseBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setCourseBatchUid(newValue);
                }
            }.setWsField("courseBatchUid");
    }

    @Override protected void setBbUserBatchUidField() throws Exception {
            new BbFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                    return bbObject.getPersonBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getInputRecord().getUserBatchUid();
                }
                @Override public void setBbFieldImp(String newValue) throws Exception {
                    bbObject.setPersonBatchUid(newValue);
                }
            }.setBbField("userBatchUid");
    }

    @Override protected void setWsUserBatchUidField() throws Exception {
            new WsFieldSetter() {
                @Override public String getBbFieldValue() throws Exception {
                return bbObject.getPersonBatchUid();
                }
                @Override public String getWsFieldValue() throws Exception {
                    return getArgs().getResultRecord().getUserBatchUid();
                }
                @Override public void setWsFieldImp(String newValue) throws Exception {
                    getArgs().getResultRecord().setUserBatchUid(newValue);
                }
            }.setWsField("userBatchUid");
    }



}
