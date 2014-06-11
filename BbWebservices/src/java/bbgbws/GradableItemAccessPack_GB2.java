
package bbgbws;

import bbwscommon.BbWsUtil;

import blackboard.platform.gradebook2.GradableItem;
import blackboard.persist.Id;
import blackboard.platform.gradebook2.GradebookManagerFactory;
import blackboard.platform.gradebook2.GradebookManager;
import blackboard.platform.log.LogService;


import java.util.List;

public class GradableItemAccessPack_GB2
    extends GradableItemAccessPack_GB2_Gen
{
    public static class AccessByBatchUidPack
        extends GradableItemAccessPack_GB2_Gen
    {


        @Override
        protected void deleteRecord()
            throws Exception
        {
        }

        @Override
        protected void insertRecord()
            throws Exception
        {
        }

        @Override
        protected void loadRecord()
            throws Exception
        {
        }

        @Override
        protected void loadRecordByAltId()
            throws Exception
        {
        }

        @Override
        protected void persistRecord()
            throws Exception
        {
        }

        @Override
        protected void updateRecord()
            throws Exception
        {
        }

    }

    protected static class AccessByIdPack
        extends GradableItemAccessPack_GB2_Gen
    {
        @Override
        protected void deleteRecord()
            throws Exception
        {
            Id id = generateInputId();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            gradebookManager.deleteGradebookItem(id);
        }

        @Override
        protected void insertRecord()
            throws Exception
        {
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            gradebookManager.persistGradebookItem(bbObject, true);//primarySchemaChanged = true
        }

        @Override
        protected void loadRecord()
            throws Exception
        {
            Id id = generateInputId();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            bbObject = gradebookManager.getGradebookItem(id);
        }

        @Override
        protected void loadRecordByAltId()
            throws Exception
        {
            Id course_id = bbObject.getCourseId();
            int pos = bbObject.getPosition();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            List<GradableItem> course_gi_list = gradebookManager.getGradebookItems(course_id);
            for (GradableItem gi: course_gi_list) {
                if (gi.getPosition() == pos) {
                    bbObject = gi;
                    break;
                }
            }
            /*
            persistGradebookItem():
            if ((g.getId() == null) || (g.getId().equals(Id.UNSET_ID)))
            {
              g.setPosition(this._gradableItemDAO.getMaxPosition(g.getCourseId()) + 1);
            }*/
        }

        @Override
        protected void persistRecord()
            throws Exception
        {
            boolean primarySchemaChanged = true;
            GradableItem input_gi = bbObject;
            try {
                    loadRecord();
                    String old_sch_id = bbObject.getGradingSchemaId().toExternalString();
                    if (BbWsUtil.nullSafeStringComparator(old_sch_id,
                        getArgs().getInputRecord().getGradingSchemaId()) == 0)
                            primarySchemaChanged = false;
            } catch (blackboard.persist.KeyNotFoundException kfe) {
                BbWsLog.logForward(LogService.Verbosity.DEBUG, "persistRecord() caught blackboard.persist.KeyNotFoundException thrown by loadRecord() => record does not exist, primarySchemaChanged gets set to true. ", this);
            }
            bbObject = input_gi;
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            gradebookManager.persistGradebookItem(bbObject, primarySchemaChanged);

            /*
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();*
            String strId = getArgs().getInputRecord().getBbId();
            if (strId != null
                    && BbWsUtil.nullSafeStringComparator(strId, getArgs().getNullValueTag()) != 0
                    && BbWsUtil.nullSafeStringComparator(strId, getArgs().getMissFieldTag()) != 0) {
                Id id = generateInputId();
                GradableItem gi = gradebookManager.getGradebookItem(id);
                String old_sch_id = gi.getGradingSchemaId().toExternalString();
                if (BbWsUtil.nullSafeStringComparator(old_sch_id,
                        getArgs().getInputRecord().getGradingSchemaId()) == 0)
                            primarySchemaChanged = false;
            }
            gradebookManager.persistGradebookItem(bbObject, primarySchemaChanged);
            */
        }

        @Override
        protected void updateRecord()
            throws Exception
        {
            boolean primarySchemaChanged = true;
            GradableItem input_gi = bbObject;
            loadRecord();
            String old_sch_id = bbObject.getGradingSchemaId().toExternalString();
            if (BbWsUtil.nullSafeStringComparator(old_sch_id,
                getArgs().getInputRecord().getGradingSchemaId()) == 0)
                    primarySchemaChanged = false;
            bbObject = input_gi;
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            gradebookManager.persistGradebookItem(bbObject, primarySchemaChanged);
            /*
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id id = generateInputId();
            GradableItem gi = gradebookManager.getGradebookItem(id);
            if (BbWsUtil.nullSafeStringComparator(old_sch_id,
                    getArgs().getInputRecord().getGradingSchemaId()) == 0)
                        primarySchemaChanged = false;
            gradebookManager.persistGradebookItem(bbObject, primarySchemaChanged);//primarySchemaChanged = true
             */
        }

    }

    public static class LoadListPack
        extends GradableItemAccessPack_GB2_Gen
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.RecordListLoader da = new bbwscommon.BbWsDataAccessPack.RecordListLoader();
            da.initialize(null);
            super.initialize(args, GradableItem.class, da);
        }

        protected void complementGradableItemParams() {
            if (getArgs().gradableItemParams.getCourseId() == null) {
                getArgs().gradableItemParams.setCourseId(getArgs().getInputRecord().getCourseId());
            }
            if (getArgs().gradableItemParams.getGradingPeriodId() == null) {
                getArgs().gradableItemParams.setGradingPeriodId(getArgs().getInputRecord().getGradingPeriodId());
            }
            if (getArgs().gradableItemParams.getBookVersion() == null) {
                getArgs().gradableItemParams.setBookVersion(getArgs().getInputRecord().getBookVersion());
            }
            /*
            if (getArgs().gradableItemParams.getAssessmentId() == null) {
                getArgs().gradableItemParams.setAssessmentId(getArgs().getInputRecord().getAssessmentId());
            }
            if (getArgs().gradableItemParams.getContentId() == null) {
                getArgs().gradableItemParams.setContentId(getArgs().getInputRecord().getCourseContentId());
            }*/
        }

        protected void loadListByCourseId()
            throws Exception
        {
            complementGradableItemParams();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id course_id = Id.generateId(blackboard.data.course.Course.DATA_TYPE
                        , getArgs().gradableItemParams.getCourseId());
            bbObjectList = gradebookManager.getGradebookItems(course_id);
        }

        protected void loadListByGradingPeriodId()
            throws Exception
        {
            complementGradableItemParams();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id gp_id = Id.generateId(blackboard.platform.gradebook2.GradingPeriod.DATA_TYPE
                        , getArgs().gradableItemParams.getGradingPeriodId());
            bbObjectList = gradebookManager.getGradebookItemsByGradingPeriod(gp_id, 0L);
        /*
        java.util.List getGradebookItemsByGradingPeriod(blackboard.persist.Id, long);
         *
         if (bookVersionNumber > 0L)
            {
              crit.add(activityBuilder.greaterThan("bookVersionNumber", new RowVersion(bookVersionNumber)));
            }
            else
            {
              crit.add(activityBuilder.equal("deleted", Boolean.FALSE));
            }
            */

        }

        protected void loadListByGradingPeriodIdAndBookVersion()
            throws Exception
        {
            complementGradableItemParams();
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id gp_id = Id.generateId(blackboard.platform.gradebook2.GradingPeriod.DATA_TYPE
                        , getArgs().gradableItemParams.getGradingPeriodId());
            bbObjectList = gradebookManager.getGradebookItemsByGradingPeriod(gp_id
                    , Long.parseLong(getArgs().gradableItemParams.getBookVersion()));
        }

    }

    public static class LoadRecordByAssessmentId
        extends GradableItemAccessPack_GB2_Gen.LoadRecordByAssessmentId
    {


        protected void loadRecord()
            throws Exception
        {
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id ass_id = Id.generateId(blackboard.data.qti.asi.assessment.Assessment.DATA_TYPE
                        , getArgs().gradableItemParams.getAssessmentId());
            bbObject = gradebookManager.getGradebookItemByAssessmentId(ass_id);
        }

    }

    public static class LoadRecordByContentId
        extends GradableItemAccessPack_GB2_Gen.LoadRecordByCourseContentId
    {
        protected void loadRecord()
            throws Exception
        {
            GradebookManager gradebookManager = GradebookManagerFactory.getInstanceWithoutSecurityCheck();
            Id con_id = Id.generateId(blackboard.data.content.Content.DATA_TYPE
                        , getArgs().gradableItemParams.getAssessmentId());
            bbObject = gradebookManager.getGradebookItemByContentId(con_id);
        }

    }

}
