/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbcrsws;

/**
 *
 * @author vic
 */
import blackboard.persist.Id;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;

import bbwscommon.*;

//public class CourseMembershipAccessPack_DATA <BbCourseMembershipType extends CourseMembership,
//        ArgumentsType extends CourseMembershipAccessPack.CourseMembershipArguments_DATA>
//        extends CourseMembershipAccessPack<BbCourseMembershipType, ArgumentsType> {
public class CourseMembershipAccessPack_DATA
        extends CourseMembershipAccessPack<CourseMembership,
        CourseMembershipAccessPack.CourseMembershipArguments_DATA> {

    @Override protected void setBbCourseBatchUidField() throws Exception {
        if (BbWsUtil.nullSafeStringComparator(getArgs().getInputRecord().getCourseBatchUid(), getArgs().getMissFieldTag()) != 0) {
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.ERROR,
                "courseBatchUid", null, "Field was provided, but is not accessible from DATA API (blackboard.data.CourseMebership)", null);
        }
        getArgs().getResultRecord().setCourseBatchUid(getArgs().getErrorValueTag());
    }

    @Override protected void setWsCourseBatchUidField() throws Exception {
        if (BbWsUtil.nullSafeStringComparator(getArgs().getResultRecord().getCourseBatchUid(), getArgs().getMissFieldTag()) != 0
            && getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.CUSTOM) == 0) {
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN,
                "courseBatchUid", null, "Field was requested, but is not available in DATA API (blackboard.data.CourseMebership)", null);
        }
        getArgs().getResultRecord().setCourseBatchUid(getArgs().getWarnValueTag());
    }

    @Override protected void setBbUserBatchUidField() throws Exception {
        if (BbWsUtil.nullSafeStringComparator(getArgs().getInputRecord().getCourseBatchUid(), getArgs().getMissFieldTag()) != 0) {
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.ERROR,
                "userBatchUid", null, "Field was provided, but is not accessible from DATA API (blackboard.data.CourseMebership)", null);
        }
        getArgs().getResultRecord().setUserBatchUid(getArgs().getErrorValueTag());
    }

    @Override protected void setWsUserBatchUidField() throws Exception {
        if (BbWsUtil.nullSafeStringComparator(getArgs().getResultRecord().getCourseBatchUid(), getArgs().getMissFieldTag()) != 0
            && getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.CUSTOM) == 0) {
            addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN,
                "userBatchUid", null, "Field was requested, but is not available in DATA API (blackboard.data.CourseMebership)", null);
        }
        getArgs().getResultRecord().setUserBatchUid(getArgs().getWarnValueTag());
    }


    protected Id generateInputCourseId() throws Exception {
        return PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(Course.DATA_TYPE, getArgs().getInputRecord().getCourseBbId());
    }
    protected Id generateInputUserId() throws Exception {
        return PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(User.DATA_TYPE, getArgs().getInputRecord().getUserBbId());
    }
    protected Id generateInputCourseMembershipId() throws Exception {
        return PersistenceServiceFactory.getInstance().getDbPersistenceManager().generateId(CourseMembership.DATA_TYPE,getArgs().getInputRecord().getBbId());
    }

    protected void loadRecordById()  throws Exception {
        checkNotNullId();
        Id id = generateInputCourseMembershipId();
        bbObject = CourseMembershipDbLoader.Default.getInstance().loadById(id);
    }
    protected void loadRecordByCourseAndUserId()  throws Exception {
        Id c_id = generateInputCourseId();
        Id u_id = generateInputUserId();
        bbObject = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId(c_id, u_id);
    }

    public static class LoadRecordPack extends CourseMembershipAccessPack_DATA {
            //extends CourseMembershipAccessPack_DATA<CourseMembership, CourseMembershipArguments_DATA> {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
    }
    public static class LoadRecordById extends LoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
    }
    public static class LoadRecordByCourseAndUserId extends LoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByCourseAndUserId();
        }
    }

    public static class LoadListPack extends CourseMembershipAccessPack_DATA {
            //extends CourseMembershipAccessPack_DATA<CourseMembership, CourseMembershipArguments_DATA> {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordListLoader  da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
        protected void loadListByUserId()  throws Exception {
            Id u_id = generateInputUserId();
            bbObjectList = CourseMembershipDbLoader.Default.getInstance().loadByUserId(u_id);
        }
        protected void loadListByCourseId()  throws Exception {
            Id c_id = generateInputCourseId();
            bbObjectList = CourseMembershipDbLoader.Default.getInstance().loadByCourseId(c_id);
        }
        protected void loadListByCourseIdWithUserInfo()  throws Exception {
            Id c_id = generateInputCourseId();
            bbObjectList = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdWithUserInfo(c_id);
        }
        protected void loadListByCourseIdAndRole()  throws Exception {
            Id c_id = generateInputCourseId();
            Role r = Role.fromFieldName(getArgs().getInputRecord().getRole().toUpperCase());
            bbObjectList = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(c_id, r);
        }
    }

    public static class LoadListByUserId extends LoadListPack {
        @Override protected void loadList() throws Exception {
            loadListByUserId();
        }
    }
    public static class LoadListByCourseId extends LoadListPack {
        @Override protected void loadList() throws Exception {
            loadListByCourseId();
        }
    }
    public static class LoadListByCourseIdWithUserInfo extends LoadListPack {
        @Override protected void loadList() throws Exception {
            loadListByCourseIdWithUserInfo();
        }
    }
    public static class LoadListByCourseIdAndRole extends LoadListPack {
        @Override protected void loadList() throws Exception {
            loadListByCourseIdAndRole();
        }
    }


    public static class CourseMembershipAccessByIdPack extends CourseMembershipAccessPack_DATA {
            //extends CourseMembershipAccessPack_DATA<CourseMembership, CourseMembershipArguments_DATA> {
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
        @Override protected void loadRecordByAltId() throws Exception {
            loadRecordByCourseAndUserId();
        }

        @Override protected void insertRecord() throws Exception {
            CourseMembershipDbPersister dbper = CourseMembershipDbPersister.Default.getInstance();
            dbper.persist(bbObject);
        }

        @Override protected void updateRecord() throws Exception {
            CourseMembershipDbPersister dbper = CourseMembershipDbPersister.Default.getInstance();
            dbper.persist(bbObject);
        }

        @Override protected void deleteRecord() throws Exception {
            Id id = generateInputCourseMembershipId();
            CourseMembershipDbPersister dbper = CourseMembershipDbPersister.Default.getInstance();
            dbper.deleteById(id);
        }
    }



    public static class InsertRecordById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
    }

    public static class UpdateRecordById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
    }

    public static class PersistRecordById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
    }
    public static class DeleteRecordById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, CourseMembership.class, da);
        }
    }

    public static class LoadListById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, CourseMembership.class, ilp);
        }
    }

    public static class InsertListById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, CourseMembership.class, ilp);
        }
    }

    public static class UpdateListById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, CourseMembership.class, ilp);
        }
    }

    public static class PersistListById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, CourseMembership.class, ilp);
        }
    }

    public static class DeleteListById extends CourseMembershipAccessByIdPack {
        public void initialize(CourseMembershipArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            InputListProcessor ilp = new InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, CourseMembership.class, ilp);
        }
    }


    //!! loadInstructorsByUser
}





 






