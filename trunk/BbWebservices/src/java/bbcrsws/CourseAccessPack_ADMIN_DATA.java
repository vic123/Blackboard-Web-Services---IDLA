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
import blackboard.admin.data.course.CourseSite;
//import blackboard.data.course.Course;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseDbPersister;
import blackboard.platform.log.LogService;
import blackboard.admin.data.course.CourseSite;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.admin.persist.course.impl.CourseSiteDbPersister;
import blackboard.admin.persist.course.impl.CourseSiteDbLoader;

import bbwscommon.*;

public class CourseAccessPack_ADMIN_DATA <ArgumentsType extends CourseAccessPack.CourseArguments_ADMIN_DATA>
        extends CourseAccessPack<CourseSite, ArgumentsType> {

    protected Id generateInputCourseId() throws Exception {
        return checkAndgenerateId(CourseSite.DATA_TYPE, getArgs().getInputRecord().getBbId());
    }

    protected void loadRecordById()  throws Exception {
        Id id = generateInputCourseId();
        Course crs = CourseDbLoader.Default.getInstance().loadById(id);
        bbObject = CourseSiteDbLoader.Default.getInstance().load(crs.getBatchUid());
    }
    /*
    protected void loadRecordByCourseId()  throws Exception {
        Course crs = CourseDbLoader.Default.getInstance().loadByCourseId(
                getArgs().getInputRecord().getCourseId());
        bbObject = CourseSiteDbLoader.Default.getInstance().load(crs.getBatchUid());
    } */
    protected void loadRecordByBatchUid()  throws Exception {
        bbObject = CourseSiteDbLoader.Default.getInstance().load(getArgs().getInputRecord().getBatchUid());
    }
    /*
    @Override protected void loadRecord() throws Exception {
        loadRecordById();
    }*/
    @Override protected void loadRecord() throws Exception {
        loadRecordByBatchUid();
    }

    @Override protected void loadRecordByAltId() throws Exception {
        loadRecordByBatchUid();
    }

    @Override protected void insertRecord() throws Exception {
        CourseSitePersister dbper = CourseSiteDbPersister.Default.getInstance();
        dbper.insert(bbObject);
    }

    @Override protected void updateRecord() throws Exception {
        CourseSitePersister dbper = CourseSiteDbPersister.Default.getInstance();
        dbper.update(bbObject);
    }

    @Override protected void deleteRecord() throws Exception {
        CourseSitePersister dbper = CourseSiteDbPersister.Default.getInstance();
        if (bbObject == null) bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
        bbObject.setBatchUid(getArgs().getInputRecord().getBatchUid());
        dbper.remove(bbObject);
    }
    

    public static class LoadRecordPack extends CourseAccessPack_ADMIN_DATA <CourseArguments_ADMIN_DATA> {
            //extends CourseMembershipAccessPack_DATA<CourseMembership, CourseMembershipArguments_DATA> {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordLoader da = new RecordLoader();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
    }
    /*
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
    }*/
    
    public static class LoadListByTemplate extends CourseAccessPack<CourseSite, 
            CourseArguments_ADMIN_DATA> {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordListLoader da = new RecordListLoader();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
        @Override protected void loadList () throws Exception {
            if (bbObject == null) bbObject = BbWsUtil.newClassInstanceWithThrow(bbObjectClass);
            setBbFields();
            bbObjectList = CourseSiteDbLoader.Default.getInstance().load(bbObject);
        }
    }
    

    public static class CourseAccessByIdPack 
            extends CourseAccessPack_ADMIN_DATA <CourseArguments_ADMIN_DATA> {
    }

    public static class InsertRecordByBatchUid extends CourseAccessByIdPack {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordInserter da = new RecordInserter();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
    }

    public static class UpdateRecordByBatchUid extends CourseAccessByIdPack {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordUpdater da = new RecordUpdater();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
    }

    public static class PersistRecordByBatchUid extends CourseAccessByIdPack {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordPersister da = new RecordPersister();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
    }
    public static class DeleteRecordByBatchUid extends CourseAccessByIdPack {
        public void initialize(CourseArguments_ADMIN_DATA args) {
            RecordDeleter da = new RecordDeleter();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
    }
    
    
    
    public static class CopyRecordById 
            extends CourseAccessPack_ADMIN_DATA<CourseArgumentsWithTargetCourseInput> {
        public void initialize(CourseArgumentsWithTargetCourseInput args) {
            RecordCopier da = new RecordCopier();
            da.initialize(null);
            super.initialize(args, CourseSite.class, da);
        }
        @Override protected void loadRecord() throws Exception {
            loadRecordById();
        }
        public class RecordCopier extends BbWsDataAccessPack.RecordAccessor {
                @Override protected void accessRecord() throws Exception {
                    BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered accessRecord(); ", this);
                    if (getArgs().getInputTargetCourse() == null) {
                        throw new BbWsException("inputTargetCourseRecord cannot be null.");
                    }
                    getArgs().setInputRecord(getArgs().getInputSourceCourse());
                    //load source Course struct data into bbObject 
                    loadRecord();
                            
                    //bbObject now contains loaded source course
                    //later on we will need source course batchUid for clone call
                    String source_batch_uid = bbObject.getBatchUid();
                   
                    //start modifying bbObject with data passed in target course struct
                    getArgs().setInputRecord(getArgs().getInputTargetCourse());
                    //this is necessary because setBbFields() is logging into resultRecord - it will become our target course record
                    if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                        setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                    } else setOrCreateWsResultObjectIfNull(null);
                    //these 2 fields cannon be set anyhow and will result in errors upon reload-compare
                    //it is assumed that targetCourse should not have these fields set as well, otherwise regular error upom reload will occur
                    getArgs().getResultRecord().setModifiedDate(getArgs().getMissFieldTag());
                    getArgs().getResultRecord().setCreationDate(getArgs().getMissFieldTag());
                    getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag()); //!! see RecordInserter.accessRecord() for comments

                    //replacing bbObject with target course data, it can "override" source course struct data
                    //principaly, only required fields may be set here as long as (see below) 
                    //other fields get re-updated.... but just leaving it to be part of processing of 
                    //statdard setBbFields() function for now
                    setBbFields();
                    //resetting Id of resulted bbObject (merged from loaded source and provided target course struct data)
                    bbObject.setId(Id.UNSET_ID);
                    insertRecord();
                   
                    //performing course copy
                    CloneConfig cfg = new CloneConfig();
                    cfg.includeArea(CloneConfig.Area.ALL);
                    CourseSitePersister site_persister = CourseSiteDbPersister.Default.getInstance();
                    BbWsLog.logForward(LogService.Verbosity.DEBUG, "source_batch_uid: "+ source_batch_uid + "; getArgs().getInputTargetCourse().getBatchUid(): "+ getArgs().getInputTargetCourse().getBatchUid(), this);
                    site_persister.clone(source_batch_uid, 
                            getArgs().getInputTargetCourse().getBatchUid(), cfg);
                    //Following fields are detected to be overwriten by clone():
                    //  bannerImageFile, buttonStyleId, classificationId, isLocaleEnforced, 
                    //  isNavigationCollapsible, locale, navigationBackgroundColor, 
                    //  navigationForegroundColor, navigationStyle
                    //So, updating record with inputTargetCourse fields again 
                    setBbFields();
                    updateRecord();
                    
                    
                    getArgs().getResultRecord().incApiPassedCount();
                    //reloading cloned cource and checking that all fields passed in target course are set as expected (that cloning did not modify them)
                    if (getArgs().getDataVerbosity().compareTo(
                            BbWsArguments.DataVerbosity.NONE) > 0) {
                        getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag());
                        loadRecordByAltId();
                        setWsFields();
                    }
                }
        }
        
    }
    
    public static class CopyRecordByBatchUid extends CopyRecordById {
        @Override protected void loadRecord() throws Exception {
            loadRecordByBatchUid();
        }
    }
    
}





 






