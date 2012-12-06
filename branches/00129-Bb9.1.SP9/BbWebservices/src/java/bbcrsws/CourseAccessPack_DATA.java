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
import blackboard.persist.course.CourseDbLoader;
import blackboard.data.user.User;
import blackboard.persist.user.UserDbLoader;
import blackboard.data.course.Course;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseDbPersister;
import blackboard.platform.log.LogService;
import blackboard.admin.data.course.CourseSite;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.admin.persist.course.impl.CourseSiteDbPersister;
import blackboard.admin.persist.course.impl.CourseSiteDbLoader;

import bbwscommon.*;

public class CourseAccessPack_DATA <ArgumentsType extends CourseAccessPack.CourseArguments_DATA>
        extends CourseAccessPack<Course, ArgumentsType> {

    protected Id generateInputCourseId() throws Exception {
        return checkAndgenerateId(Course.DATA_TYPE, getArgs().getInputRecord().getBbId());
    }

    protected void loadRecordById()  throws Exception {
        Id id = generateInputCourseId();
        bbObject = CourseDbLoader.Default.getInstance().loadById(id);
    }
    protected void loadRecordByCourseId()  throws Exception {
        bbObject = CourseDbLoader.Default.getInstance().loadByCourseId(getArgs().getInputRecord().getCourseId());
    }
    protected void loadRecordByBatchUid()  throws Exception {
        bbObject = CourseDbLoader.Default.getInstance().loadByBatchUid(getArgs().getInputRecord().getBatchUid());
    }
    
    @Override protected void loadRecord() throws Exception {
        loadRecordById();
    }
    @Override protected void loadRecordByAltId() throws Exception {
        loadRecordByCourseId();
    }

    @Override protected void insertRecord() throws Exception {
        CourseDbPersister dbper = CourseDbPersister.Default.getInstance();
        dbper.persist(bbObject);
    }

    @Override protected void updateRecord() throws Exception {
        CourseDbPersister dbper = CourseDbPersister.Default.getInstance();
        dbper.persist(bbObject);
    }

    @Override protected void deleteRecord() throws Exception {
        Id id = generateInputCourseId();
        CourseDbPersister dbper = CourseDbPersister.Default.getInstance();
        dbper.deleteById(id);
    }
    

    public static class LoadRecordPack extends CourseAccessPack_DATA <CourseArguments_DATA> {
            //extends CourseMembershipAccessPack_DATA<CourseMembership, CourseMembershipArguments_DATA> {
        public void initialize(CourseArguments_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, Course.class, da);
        }
    }
    public static class LoadRecordById extends LoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
    }
    public static class LoadRecordByCourseId extends LoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByCourseId();
        }
    }
    public static class LoadRecordByBatchUid extends LoadRecordPack {
        @Override protected void loadRecord() throws Exception {
            loadRecordByBatchUid();
        }
    }
    
    public static class LoadListByTemplate extends CourseAccessPack<CourseSite, CourseArguments_DATA> {
        public void initialize(CourseArguments_DATA args) {
            RecordListLoader da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
        @Override protected void loadList () throws Exception {
            if (bbObject == null) bbObject = new CourseSite();
            setBbFields();
            bbObjectList = CourseSiteDbLoader.Default.getInstance().load(bbObject);
        }
    }
    

    public static class CourseAccessByIdPack extends CourseAccessPack_DATA <CourseArguments_DATA> {
    }

    public static class InsertRecordById extends CourseAccessByIdPack {
        public void initialize(CourseArguments_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, Course.class, da);
        }
    }

    public static class UpdateRecordById extends CourseAccessByIdPack {
        public void initialize(CourseArguments_DATA args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            super.initialize(args, Course.class, da);
        }
    }

    public static class PersistRecordById extends CourseAccessByIdPack {
        public void initialize(CourseArguments_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, Course.class, da);
        }
    }
    public static class DeleteRecordById extends CourseAccessByIdPack {
        public void initialize(CourseArguments_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, Course.class, da);
        }
    }
    
}





 






