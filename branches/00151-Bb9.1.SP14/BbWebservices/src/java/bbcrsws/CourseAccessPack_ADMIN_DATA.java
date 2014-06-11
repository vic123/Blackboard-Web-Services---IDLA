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
import blackboard.util.StringUtil;
import java.util.Arrays;


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
    
    protected static abstract class BaseCloneRecord
            extends CourseAccessPack_ADMIN_DATA<CourseArgumentsWithTargetCourseInputAndCopyParams> {

        public void initialize(CourseArgumentsWithTargetCourseInputAndCopyParams args, BaseRecordCloner da) {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecord.initialize() ", this);
            super.initialize(args, CourseSite.class, da);
            if (getArgs().getInputSourceCourse() == null) {
                    throw new BbWsException("inputSourceCourseRecord cannot be null.");
            }
            if (getArgs().getInputTargetCourse() == null) {
                    throw new BbWsException("inputTargetCourseRecord cannot be null.");
            }
        }

        protected String getSourceCourseBatchUid() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecord.getSourceCourseBatchUid() ", this);
            throw new BbWsException("BaseCloneRecord.getSourceCourseBatchUid() must be overriden.");
        }
        //protected abstract String getTargetCourseBatchUid() throws Exception;

        protected abstract class BaseRecordCloner extends BbWsDataAccessPack.RecordAccessor  {
            protected String sourceCourseBatchUid;
            protected String targetCourseBatchUid;
            protected CloneConfig buildCloneConfig() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseRecordCloner.buildCloneConfig() ", this);
                CloneConfig cfg = new CloneConfig();
                cfg.setArchiveCSItems(true);
                cfg.setReconcileFlag(true);
                cfg.setCloneForDecrossList(false);
                cfg.setUseCourseCopyLog(true);
                CourseCopyParams clone_params = getArgs().getCourseCopyParams();
                if (clone_params == null) clone_params = new CourseCopyParams();
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "CourseCloneParams.includeAreaList: "
                            + clone_params.getIncludeAreaList(), this);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "CourseCloneParams.excludeAreaList: "
                            + clone_params.getExcludeAreaList(), this);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "CloneConfig.Area.getValues(): "
                            + Arrays.toString(CloneConfig.Area.getValues()), this);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "CloneConfig.Area.getEnumConstants(): "
                            + CloneConfig.Area.class.getEnumConstants(), this);
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "BbWsUtil.getBbEnumFieldNames(): "
                            + BbWsUtil.getBbEnumFieldNames(CloneConfig.Area.class), this);
                if (clone_params.getIncludeAreaList().size() == 0) clone_params.getIncludeAreaList().add("ALL");
                try {
                    for (String inc_area : clone_params.getIncludeAreaList()) {
                        CloneConfig.Area area = (CloneConfig.Area) CloneConfig.Area.fromFieldName(inc_area, CloneConfig.Area.class);
                        cfg.includeArea(area);
                    }
                    for (String exc_area : clone_params.getExcludeAreaList()) {
                        CloneConfig.Area area = (CloneConfig.Area) CloneConfig.Area.fromFieldName(exc_area, CloneConfig.Area.class);
                        cfg.excludeArea(area);
                    }
                } catch (IllegalArgumentException ie) {
                    throw new BbWsException("CourseCloneParams include/exclude area lists may contain following values: " + BbWsUtil.getBbEnumFieldNames(CloneConfig.Area.class), ie);
                }
                return cfg;
            }
            protected void setContextUser() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseRecordCloner.setContextUser() ", this);
                User bbUser =
                    UserDbLoader.Default.getInstance().loadByUserName("Administrator");
                blackboard.platform.context.ContextManager cm
                        = blackboard.platform.context.ContextManagerFactory.getInstance();
                blackboard.platform.context.impl.ContextImpl bbCtx
                        = (blackboard.platform.context.impl.ContextImpl) cm.getContext();
                bbCtx.setUser(bbUser);
            }
            protected void verifyFields() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseRecordCloner.verifyFields() ", this);
                getArgs().getResultRecord().incApiPassedCount();//!! this does not work as expected, needs to be checked
                //reloading cloned cource and checking that all fields passed in target course are set as expected (that cloning did not modify them)
                if (getArgs().getDataVerbosity().compareTo(
                        BbWsArguments.DataVerbosity.NONE) > 0) {
                    //??getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag());
                    //- if it was copy, then it should have been set to MissFieldTag
                    // - if it was merge then it should be either the same or set to MissFieldTag on client side
                    loadRecordByAltId(); //??getArgs().getInputRecord().getBatchUid() - may not be set when ById
                                        //bbObject = CourseSiteDbLoader.Default.getInstance().load(targetCourseBatchUid);
                    setWsFields();
                }
            }

            protected void doClone() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseRecordCloner.doClone() ", this);
                setContextUser();
                CloneConfig cfg = buildCloneConfig();
                CourseSitePersister site_persister = CourseSiteDbPersister.Default.getInstance();
                BbWsLog.logForward(LogService.Verbosity.DEBUG,
                        "sourceBatchUid: "+ sourceCourseBatchUid
                        + "; targetCourseBatchUid: "+ targetCourseBatchUid, this);
                site_persister.clone(sourceCourseBatchUid, targetCourseBatchUid, cfg);
            }
            protected void updateTargetCourse() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordMerger.updateTargetCourse() ", this);
                getArgs().setInputRecord(getArgs().getInputTargetCourse());
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "bbObject before setBbFields(): " + bbObject, this);
                setBbFields();
                updateRecord();
                getArgs().getResultRecord().incApiPassedCount();
            }
            protected void accessRecord() throws Exception {
                //!! this probably has to be re-coded and go below into class hierarchy
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseRecordCloner.accessRecord(); ", this);
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputTargetCourse());
                } else setOrCreateWsResultObjectIfNull(null);
            }

        }
        protected class RecordCopier extends BaseRecordCloner {
            @Override protected CloneConfig buildCloneConfig() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordCopier.buildCloneConfig() ", this);
                CloneConfig cfg = super.buildCloneConfig();
                //if (cfg.isAreaIncluded(CloneConfig.Area.ALL)) cfg.setCopyType(Enum.valueOf(CloneConfig.CopyType.class, "COPY_EXACT_COURSE"));
                //else cfg.setCopyType(Enum.valueOf(CloneConfig.CopyType.class, "COPY_NEW_COURSE"));
                if (cfg.isAreaIncluded(CloneConfig.Area.ALL)) cfg.setCopyType(CloneConfig.CopyType.COPY_EXACT_COURSE);
                else cfg.setCopyType(CloneConfig.CopyType.COPY_NEW_COURSE);
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "cfg.getCopyType(): " + cfg.getCopyType(), this);
                cfg.setReconcileFlag(true);
                return cfg;
            }
            protected void insertTargetCourse() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordCopier.insertTargetCourse() ", this);

                getArgs().setInputRecord(getArgs().getInputTargetCourse());
                /*??
                //this is necessary because setBbFields() is logging into resultRecord - it will become our target course record
                if (getArgs().getDataVerbosity().compareTo(BbWsArguments.DataVerbosity.ONLY_ID) > 0) {
                    setOrCreateWsResultObjectIfNull(getArgs().getInputRecord());
                } else setOrCreateWsResultObjectIfNull(null);
                 */
                //these 2 fields cannon be set anyhow and will result in errors upon reload-compare
                //it is assumed that targetCourse should not have these fields set as well, otherwise regular error upom reload will occur
                //??getArgs().getResultRecord().setModifiedDate(getArgs().getMissFieldTag());
                //??getArgs().getResultRecord().setCreationDate(getArgs().getMissFieldTag());

                getArgs().getResultRecord().setBbId(getArgs().getMissFieldTag()); //!! see RecordInserter.accessRecord() for comments
                //replacing bbObject with target course data, it can "override" source course struct data
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "bbObject before setBbFields(): " + bbObject, this);
                setBbFields();
                //resetting Id of resulted bbObject (merged from loaded source and provided target course struct data)
                bbObject.setId(Id.UNSET_ID);
                insertRecord();
            }
            protected void deleteTargetCourse() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordCopier.deleteTargetCourse() ", this);
                getArgs().setInputRecord(getArgs().getInputTargetCourse());
                getArgs().getInputRecord().setBatchUid(targetCourseBatchUid);
                deleteRecord();
            }
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordCopier.accessRecord(); ", this);
                super.accessRecord();
                //loadTargetCourseOriginIntoBbObject();
                getArgs().setInputRecord(getArgs().getInputSourceCourse());
                loadRecord();
                sourceCourseBatchUid = bbObject.getBatchUid();
                insertTargetCourse();
                //insertTargetCourse uses source course as origin... 
                //but InputTargetCourse cannot have missing batch uid - key violation would happen
                //getArgs().getInputTargetCourse().setBatchUid(bbObject.getBatchUid());
                targetCourseBatchUid = bbObject.getBatchUid();
                try {
                    //insertTargetCourse() (CourseSiteDbPersister.insert)
                    //does not set classificationId,
                    //insertById does, but we'll need to update anyway after clone()
                    //verifyFields();
                    doClone();
                    //Following fields are detected to be overwriten by clone():
                    //absoluteLimit, bannerImageFile, buttonStyleId, (??classificationId may be bad just from the insert),
                    //institution, isLocaleEnforced, isNavigationCollapsible, navigationBackgroundColor,
                    //navigationForegroundColor, navigationStyle, softLimit, uploadLimit
                    //verifyFields();
                    updateTargetCourse();
                    verifyFields();
                } catch (Exception e) {
                    BbWsLog.logForward(LogService.Verbosity.ERROR, e, "",  this);
                    try {
                        deleteTargetCourse();
                    } catch (Exception e1) {
                        BbWsLog.logForward(LogService.Verbosity.ERROR, e1, "",  this);
                    }
                    throw e;
                }
            }
        }
        protected class RecordMerger extends BaseRecordCloner {
            @Override protected CloneConfig buildCloneConfig() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordMerger.buildCloneConfig() ", this);
                CloneConfig cfg = super.buildCloneConfig();
                //cfg.setCopyType(Enum.valueOf(CloneConfig.CopyType.class, "COPY_EXISTING_COURSE"));
                cfg.setCopyType(CloneConfig.CopyType.COPY_EXISTING_COURSE);
                cfg.setReconcileFlag(false);
                return cfg;
            }
            @Override protected void accessRecord() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered RecordMerger.accessRecord(); ", this);
                super.accessRecord();
                //loadTargetCourseOriginIntoBbObject();
                getArgs().setInputRecord(getArgs().getInputTargetCourse());
                loadRecord();
                sourceCourseBatchUid = getSourceCourseBatchUid();
                //it may be unset when doing merge ById
                getArgs().getInputTargetCourse().setBatchUid(bbObject.getBatchUid());
                targetCourseBatchUid = bbObject.getBatchUid();
                doClone();
                updateTargetCourse();
                verifyFields();
            }
        }
    }
    protected static class BaseCloneRecordById extends BaseCloneRecord {
        @Override protected String getSourceCourseBatchUid() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordById.getSourceCourseBatchUid() ", this);
            Id id = checkAndgenerateId(CourseSite.DATA_TYPE, getArgs().getInputSourceCourse().getBbId());
            Course crs = CourseDbLoader.Default.getInstance().loadById(id);
            return crs.getBatchUid();
        }
        /*
        @Override protected String getTargetCourseBatchUid() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordById.getTargetCourseBatchUid() ", this);
            getArgs().setInputRecord(getArgs().getInputTargetCourse());
            //load source Course struct data into bbObject
            loadRecord();
            return  bbObject.getBatchUid();
        }*/
        @Override protected void loadRecord() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordById.loadRecord() ", this);
            loadRecordById();
        }
    }
    protected static class BaseCloneRecordByBatchUid extends BaseCloneRecord {
            @Override protected String getSourceCourseBatchUid() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordByBatchUid.getSourceCourseBatchUid() ", this);
                return getArgs().getInputSourceCourse().getBatchUid();
            }
            /*
            @Override protected String getTargetCourseBatchUid() throws Exception {
                BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordByBatchUid.getTargetCourseBatchUid() ", this);
                return getArgs().getInputTargetCourse().getBatchUid();
            }*/
        @Override protected void loadRecord() throws Exception {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered BaseCloneRecordByBatchUid.loadRecord() ", this);
            loadRecordByBatchUid();
            //?? Bb9.1 SP5 CopyRecordByBatchUid causes Failed to create Wiki - com.xythos.storageServer.api.DuplicateEntryException
            //  it seem to be caused by deleting of record with courseDeleteRecordById -see comments in _courseCopyRecordById of AutoTestClient
        }
    }
    public static class CopyRecordWithParamsById extends BaseCloneRecordById {
        public void initialize(CourseArgumentsWithTargetCourseInputAndCopyParams args) {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered CopyRecordById.initialize() ", this);
            RecordCopier da = new RecordCopier();
            da.initialize(null);
            super.initialize(args, da);
        }
    }
    public static class CopyRecordWithParamsByBatchUid extends BaseCloneRecordByBatchUid {
        public void initialize(CourseArgumentsWithTargetCourseInputAndCopyParams args) {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered CopyRecordByBatchUid.initialize() ", this);
            RecordCopier da = new RecordCopier();
            da.initialize(null);
            super.initialize(args, da);
        }
    }
    public static class MergeRecordById extends BaseCloneRecordById  {
        public void initialize(CourseArgumentsWithTargetCourseInputAndCopyParams args) {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered MergeRecordById.initialize() ", this);
            RecordMerger da = new RecordMerger();
            da.initialize(null);
            super.initialize(args, da);

        }
    }
    public static class MergeRecordByBatchUid extends BaseCloneRecordByBatchUid {
        public void initialize(CourseArgumentsWithTargetCourseInputAndCopyParams args) {
            BbWsLog.logForward(LogService.Verbosity.INFORMATION, "Entered MergeRecordByBatchUid.initialize() ", this);
            RecordMerger da = new RecordMerger();
            da.initialize(null);
            super.initialize(args, da);
        }
    }
}





 







