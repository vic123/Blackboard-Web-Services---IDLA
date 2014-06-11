
package bbgbws;

import java.util.List;
import bbwscommon.BbWsArguments;
import bbwscommon.BbWsException;
import bbwscommon.BbWsParams;
import blackboard.data.content.Content;
import blackboard.data.course.Course;
import blackboard.data.qti.asi.AsiObject;
import blackboard.persist.Id;
import blackboard.platform.gradebook2.GradebookType;
import blackboard.platform.gradebook2.GradingPeriod;
import blackboard.platform.gradebook2.GradingSchema;

public class GradableItemAccessPack_GB2_Gen
    extends bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType<GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2, blackboard.platform.gradebook2.GradableItem, GradableItemDetails>
{


    @Override
    protected void setBbFields()
        throws Exception
    {
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getAggregationModel().name());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getAggregationModel());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                try {
                    (bbObject).setAggregationModel(Enum.valueOf(blackboard.platform.gradebook2.GradableItem.AttemptAggregationModel.class, newValue));
                } catch (IllegalArgumentException iae) {
                    throw new BbWsException("GradableItemDetails aggregationModel may contain one of the following values: ", iae);
                }
            }

        }
        .setBbField("aggregationModel");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getAssessmentId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getAssessmentId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setAssessmentId(checkAndgenerateId(AsiObject.DATA_TYPE, newValue));
            }

        }
        .setBbField("assessmentId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getBbId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setId(checkAndgenerateId(blackboard.platform.gradebook2.GradableItem.DATA_TYPE, newValue));
            }

        }
        .setBbField("bbId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Long.toString(bbObject.getVersion()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getBookVersion());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setVersion((new blackboard.persist.RowVersion(Long.parseLong(newValue))));
            }

        }
        .setBbField("bookVersion");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCalculatedInd().name());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCalculatedInd());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                try {
                    (bbObject).setCalculatedInd(Enum.valueOf(blackboard.platform.gradebook2.GradableItem.CalculationType.class, newValue));
                } catch (IllegalArgumentException iae) {
                    throw new BbWsException("GradableItemDetails calculatedInd may contain one of the following values: ", iae);
                }
            }

        }
        .setBbField("calculatedInd");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCategory());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCategory());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setCategory((newValue));
            }

        }
        .setBbField("category");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCategoryId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCategoryId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setCategoryId(checkAndgenerateId(GradebookType.DATA_TYPE, newValue));
            }

        }
        .setBbField("categoryId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCourseContentId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCourseContentId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setCourseContentId(checkAndgenerateId(Content.DATA_TYPE, newValue));
            }

        }
        .setBbField("courseContentId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCourseId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCourseId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setCourseId(checkAndgenerateId(Course.DATA_TYPE, newValue));
            }

        }
        .setBbField("courseId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDateAdded()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDateAdded());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDateAdded((parseDate(newValue)));
            }

        }
        .setBbField("dateAdded");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDateModified()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDateModified());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDateModified((parseDate(newValue)));
            }

        }
        .setBbField("dateModified");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getDescription().toString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDescription());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDescription((parseFormattedText(newValue)));
            }

        }
        .setBbField("description");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getDisplayTitle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDisplayTitle());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDisplayTitle((newValue));
            }

        }
        .setBbField("displayTitle");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDueDate()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDueDate());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDueDate((parseDate(newValue)));
            }

        }
        .setBbField("dueDate");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getExternalAnalysisUrl());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getExternalAnalysisUrl());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setExternalAnalysisUrl((newValue));
            }

        }
        .setBbField("externalAnalysisUrl");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getExternalAttemptHandlerUrl());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getExternalAttemptHandlerUrl());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setExternalAttemptHandlerUrl((newValue));
            }

        }
        .setBbField("externalAttemptHandlerUrl");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getGradingPeriodId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getGradingPeriodId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setGradingPeriodId(checkAndgenerateId(GradingPeriod.DATA_TYPE, newValue));
            }

        }
        .setBbField("gradingPeriodId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getGradingSchemaId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getGradingSchemaId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setGradingSchemaId(checkAndgenerateId(GradingSchema.DATA_TYPE, newValue));
            }

        }
        .setBbField("gradingSchemaId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isDeleted()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsDeleted());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setDeleted((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("deleted");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isHideAttempt()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsHideAttempt());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setHideAttempt((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("hideAttempt");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isLimitedAttendance()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsLimitedAttendance());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setLimitedAttendance((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("limitedAttendance");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isScorable()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsScorable());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setScorable((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("scorable");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isShowStatsToStudent()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsShowStatsToStudent());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setShowStatsToStudent((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("showStatsToStudent");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isSingleAttempt()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsSingleAttempt());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setSingleAttempt((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("singleAttempt");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isUserCreatedColumn()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsUserCreatedColumn());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setUserCreatedColumn((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("userCreatedColumn");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleInAllTerms()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleInAllTerms());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setVisibleInAllTerms((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("visibleInAllTerms");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleInBook()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleInBook());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setVisibleInBook((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("visibleInBook");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleToStudents()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleToStudents());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setVisibleToStudents((Boolean.parseBoolean(newValue)));
            }

        }
        .setBbField("visibleToStudents");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getLinkId());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getLinkId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setLinkId((newValue));
            }

        }
        .setBbField("linkId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Integer.toString(bbObject.getMaxAttempts()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getMaxAttempts());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setMaxAttempts((Integer.parseInt(newValue)));
            }

        }
        .setBbField("maxAttempts");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Double.toString(bbObject.getPoints()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getPoints());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setPoints((Double.parseDouble(newValue)));
            }

        }
        .setBbField("points");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Integer.toString(bbObject.getPosition()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getPosition());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setPosition((Integer.parseInt(newValue)));
            }

        }
        .setBbField("position");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getScoreProviderHandle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getScoreProviderHandle());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setScoreProviderHandle((newValue));
            }

        }
        .setBbField("scoreProviderHandle");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getSecondaryGradingSchemaId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getSecondaryGradingSchemaId());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                bbObject.setSecondaryGradingSchemaId(checkAndgenerateId(GradingSchema.DATA_TYPE, newValue));
            }

        }
        .setBbField("secondaryGradingSchemaId");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getTitle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getTitle());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setTitle((newValue));
            }

        }
        .setBbField("title");
        new bbwscommon.BbWsDataAccessPack.BbFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Float.toString(bbObject.getWeight()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getWeight());
            }

            @Override
            public void setBbFieldImp(String newValue)
                throws Exception
            {
                (bbObject).setWeight((Float.parseFloat(newValue)));
            }

        }
        .setBbField("weight");
    }

    @Override
    protected void setWsFields()
        throws Exception
    {
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getAggregationModel().name());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getAggregationModel());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setAggregationModel(newValue);
            }

        }
        .setWsField("aggregationModel");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getAssessmentId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getAssessmentId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setAssessmentId(newValue);
            }

        }
        .setWsField("assessmentId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getBbId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setBbId(newValue);
            }

        }
        .setWsField("bbId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Long.toString(bbObject.getVersion()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getBookVersion());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setBookVersion(newValue);
            }

        }
        .setWsField("bookVersion");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCalculatedInd().name());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCalculatedInd());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setCalculatedInd(newValue);
            }

        }
        .setWsField("calculatedInd");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCategory());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCategory());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setCategory(newValue);
            }

        }
        .setWsField("category");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCategoryId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCategoryId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setCategoryId(newValue);
            }

        }
        .setWsField("categoryId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCourseContentId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCourseContentId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setCourseContentId(newValue);
            }

        }
        .setWsField("courseContentId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getCourseId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getCourseId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setCourseId(newValue);
            }

        }
        .setWsField("courseId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDateAdded()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDateAdded());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setDateAdded(newValue);
            }

        }
        .setWsField("dateAdded");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDateModified()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDateModified());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setDateModified(newValue);
            }

        }
        .setWsField("dateModified");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getDescription().toString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDescription());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setDescription(newValue);
            }

        }
        .setWsField("description");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getDisplayTitle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDisplayTitle());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setDisplayTitle(newValue);
            }

        }
        .setWsField("displayTitle");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (extractDate(bbObject.getDueDate()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getDueDate());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setDueDate(newValue);
            }

        }
        .setWsField("dueDate");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getExternalAnalysisUrl());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getExternalAnalysisUrl());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setExternalAnalysisUrl(newValue);
            }

        }
        .setWsField("externalAnalysisUrl");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getExternalAttemptHandlerUrl());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getExternalAttemptHandlerUrl());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setExternalAttemptHandlerUrl(newValue);
            }

        }
        .setWsField("externalAttemptHandlerUrl");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getGradingPeriodId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getGradingPeriodId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setGradingPeriodId(newValue);
            }

        }
        .setWsField("gradingPeriodId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getGradingSchemaId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getGradingSchemaId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setGradingSchemaId(newValue);
            }

        }
        .setWsField("gradingSchemaId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isDeleted()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsDeleted());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsDeleted(newValue);
            }

        }
        .setWsField("deleted");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isHideAttempt()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsHideAttempt());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsHideAttempt(newValue);
            }

        }
        .setWsField("hideAttempt");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isLimitedAttendance()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsLimitedAttendance());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsLimitedAttendance(newValue);
            }

        }
        .setWsField("limitedAttendance");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isScorable()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsScorable());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsScorable(newValue);
            }

        }
        .setWsField("scorable");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isShowStatsToStudent()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsShowStatsToStudent());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsShowStatsToStudent(newValue);
            }

        }
        .setWsField("showStatsToStudent");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isSingleAttempt()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsSingleAttempt());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsSingleAttempt(newValue);
            }

        }
        .setWsField("singleAttempt");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isUserCreatedColumn()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsUserCreatedColumn());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsUserCreatedColumn(newValue);
            }

        }
        .setWsField("userCreatedColumn");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleInAllTerms()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleInAllTerms());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsVisibleInAllTerms(newValue);
            }

        }
        .setWsField("visibleInAllTerms");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleInBook()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleInBook());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsVisibleInBook(newValue);
            }

        }
        .setWsField("visibleInBook");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Boolean.toString(bbObject.isVisibleToStudents()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getIsVisibleToStudents());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setIsVisibleToStudents(newValue);
            }

        }
        .setWsField("visibleToStudents");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getLinkId());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getLinkId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setLinkId(newValue);
            }

        }
        .setWsField("linkId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Integer.toString(bbObject.getMaxAttempts()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getMaxAttempts());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setMaxAttempts(newValue);
            }

        }
        .setWsField("maxAttempts");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Double.toString(bbObject.getPoints()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getPoints());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setPoints(newValue);
            }

        }
        .setWsField("points");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Integer.toString(bbObject.getPosition()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getPosition());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setPosition(newValue);
            }

        }
        .setWsField("position");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getScoreProviderHandle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getScoreProviderHandle());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setScoreProviderHandle(newValue);
            }

        }
        .setWsField("scoreProviderHandle");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getSecondaryGradingSchemaId().toExternalString());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getSecondaryGradingSchemaId());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setSecondaryGradingSchemaId(newValue);
            }

        }
        .setWsField("secondaryGradingSchemaId");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (bbObject.getTitle());
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getTitle());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setTitle(newValue);
            }

        }
        .setWsField("title");
        new bbwscommon.BbWsDataAccessPack.WsFieldSetter() {


            @Override
            public String getBbFieldValue()
                throws Exception
            {
                return (Float.toString(bbObject.getWeight()));
            }

            @Override
            public String getWsFieldValue()
                throws Exception
            {
                return (getArgs().getInputRecord().getWeight());
            }

            @Override
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                (getArgs().getResultRecord()).setWeight(newValue);
            }

        }
        .setWsField("weight");
    }

    protected Id generateInputId()
        throws Exception
    {
        return checkAndgenerateId(blackboard.platform.gradebook2.GradableItem.DATA_TYPE, getArgs().getInputRecord().getBbId());
    }

    public static class DeleteListById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordDeleter da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordDeleter();
            da.initialize(null);
            bbwscommon.BbWsDataAccessPack.InputListProcessor ilp = new bbwscommon.BbWsDataAccessPack.InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, ilp);
        }

    }

    public static class DeleteRecordById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordDeleter da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordDeleter();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class GradableItemArguments_GB2
        extends BbWsArguments<GradableItemDetails, GradableItemDetails>
    {

        public GradableItemParams gradableItemParams;

        public void initialize(Class<GradableItemDetails> wsResultClass, BbWsParams params, GradableItemDetails recordInput, String dataAccessPackClassName, String innerDAPDefaultClassName, GradableItemParams gradableItemParams) {
            super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, innerDAPDefaultClassName);
            this.gradableItemParams = gradableItemParams;
            if (gradableItemParams == null) {
                this.gradableItemParams = new GradableItemParams();
            }
        }

        public void initialize(Class<GradableItemDetails> wsResultClass, BbWsParams params, List<GradableItemDetails> listInput, String dataAccessPackClassName, String innerDAPDefaultClassName, GradableItemParams gradableItemParams) {
            super.initialize(wsResultClass, params, listInput, dataAccessPackClassName, innerDAPDefaultClassName);
            this.gradableItemParams = gradableItemParams;
            if (gradableItemParams == null) {
                this.gradableItemParams = new GradableItemParams();
            }
        }

    }

    public static class InsertListById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordInserter da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordInserter();
            da.initialize(null);
            bbwscommon.BbWsDataAccessPack.InputListProcessor ilp = new bbwscommon.BbWsDataAccessPack.InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, ilp);
        }

    }

    public static class InsertRecordById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordInserter da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordInserter();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class LoadListByCourseId
        extends bbgbws.GradableItemAccessPack_GB2.LoadListPack
    {


        @Override
        protected void loadList()
            throws Exception
        {
            loadListByCourseId();
        }

    }

    public static class LoadListByGradingPeriodId
        extends bbgbws.GradableItemAccessPack_GB2.LoadListPack
    {


        @Override
        protected void loadList()
            throws Exception
        {
            loadListByGradingPeriodId();
        }

    }

    public static class LoadListByGradingPeriodIdAndBookVersion
        extends bbgbws.GradableItemAccessPack_GB2.LoadListPack
    {


        @Override
        protected void loadList()
            throws Exception
        {
            loadListByGradingPeriodIdAndBookVersion();
        }

    }

    public static class LoadListById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader();
            da.initialize(null);
            bbwscommon.BbWsDataAccessPack.InputListProcessor ilp = new bbwscommon.BbWsDataAccessPack.InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, ilp);
        }

    }

    public static class LoadRecordByAssessmentId
        extends GradableItemAccessPack_GB2_Gen
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class LoadRecordByCourseContentId
        extends GradableItemAccessPack_GB2_Gen
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class LoadRecordById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class PersistListById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordPersister da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordPersister();
            da.initialize(null);
            bbwscommon.BbWsDataAccessPack.InputListProcessor ilp = new bbwscommon.BbWsDataAccessPack.InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, ilp);
        }

    }

    public static class PersistRecordById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordPersister da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordPersister();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

    public static class UpdateListById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordUpdater da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordUpdater();
            da.initialize(null);
            bbwscommon.BbWsDataAccessPack.InputListProcessor ilp = new bbwscommon.BbWsDataAccessPack.InputListProcessor();
            ilp.initialize(da);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, ilp);
        }

    }

    public static class UpdateRecordById
        extends bbgbws.GradableItemAccessPack_GB2.AccessByIdPack
    {


        public void initialize(GradableItemAccessPack_GB2_Gen.GradableItemArguments_GB2 args) {
            bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordUpdater da = new bbwscommon.BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordUpdater();
            da.initialize(null);
            super.initialize(args, blackboard.platform.gradebook2.GradableItem.class, da);
        }

    }

}
