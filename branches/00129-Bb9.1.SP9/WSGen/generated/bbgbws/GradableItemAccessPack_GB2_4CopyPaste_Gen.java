
package bbgbws;

import blackboard.platform.gradebook2.GradableItem;

public class GradableItemAccessPack_GB2_4CopyPaste_Gen
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

    public static class LoadListPack
        extends GradableItemAccessPack_GB2_Gen
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.RecordListLoader da = new bbwscommon.BbWsDataAccessPack.RecordListLoader();
            da.initialize(null);
            super.initialize(args, GradableItem.class, da);
        }

        protected void loadListByCourseId()
            throws Exception
        {
        }

        protected void loadListByGradingPeriodId()
            throws Exception
        {
        }

        protected void loadListByGradingPeriodIdAndBookVersion()
            throws Exception
        {
        }

    }

    public static class LoadRecordByAssessmentId
        extends GradableItemAccessPack_GB2_Gen.LoadRecordByAssessmentId
    {


        protected void loadRecord()
            throws Exception
        {
        }

    }

    public static class LoadRecordByCourseContentId
        extends GradableItemAccessPack_GB2_Gen.LoadRecordByCourseContentId
    {


        protected void loadRecord()
            throws Exception
        {
        }

    }

}
