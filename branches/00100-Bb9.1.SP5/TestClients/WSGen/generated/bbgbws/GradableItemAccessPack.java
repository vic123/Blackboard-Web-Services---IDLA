
package bbgbws;

import bbwscommon.BbWsException;

public class GradableItemAccessPack {


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
                    (bbObject).setAggregationModel((Enum.valueOf(blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel.class, newValue))));
                } catch (IllegalArgumentException iae) {
                    throw new BbWsException(("GradableItemDetails aggregationModel may contain one of the following values: "+(Arrays.toString(blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel.values()))), iae);
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
                (bbObject).setAssessmentId((checkAndgenerateId(blackboard.data.qti.asi.AsiObject.DATA_TYPE, newValue)));
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
                (bbObject).setId((checkAndgenerateId(null.DATA_TYPE, newValue)));
            }

        }
        .setBbField("bbId");
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
                    (bbObject).setCalculatedInd((Enum.valueOf(blackboard.platform.gradebook2.GradableItem$CalculationType.class, newValue))));
                } catch (IllegalArgumentException iae) {
                    throw new BbWsException(("GradableItemDetails calculatedInd may contain one of the following values: "+(Arrays.toString(blackboard.platform.gradebook2.GradableItem$CalculationType.values()))), iae);
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
                (bbObject).setCategoryId((checkAndgenerateId(blackboard.platform.gradebook2.GradebookType.DATA_TYPE, newValue)));
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
                (bbObject).setCourseContentId((checkAndgenerateId(blackboard.data.content.Content.DATA_TYPE, newValue)));
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
                (bbObject).setCourseId((checkAndgenerateId(blackboard.data.course.Course.DATA_TYPE, newValue)));
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
                (bbObject).setGradingPeriodId((checkAndgenerateId(blackboard.platform.gradebook2.GradingPeriod.DATA_TYPE, newValue)));
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
                (bbObject).setGradingSchemaId((checkAndgenerateId(blackboard.platform.gradebook2.GradingSchema.DATA_TYPE, newValue)));
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
                (bbObject).setSecondaryGradingSchemaId((checkAndgenerateId(blackboard.platform.gradebook2.GradingSchema.DATA_TYPE, newValue)));
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getAggregationModel(newValue));
            }

        }
        .setWsField("aggregationModel");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getAssessmentId(newValue));
            }

        }
        .setWsField("assessmentId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getBbId(newValue));
            }

        }
        .setWsField("bbId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getCalculatedInd(newValue));
            }

        }
        .setWsField("calculatedInd");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getCategory(newValue));
            }

        }
        .setWsField("category");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getCategoryId(newValue));
            }

        }
        .setWsField("categoryId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getCourseContentId(newValue));
            }

        }
        .setWsField("courseContentId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getCourseId(newValue));
            }

        }
        .setWsField("courseId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getDateAdded(newValue));
            }

        }
        .setWsField("dateAdded");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getDateModified(newValue));
            }

        }
        .setWsField("dateModified");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getDescription(newValue));
            }

        }
        .setWsField("description");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getDisplayTitle(newValue));
            }

        }
        .setWsField("displayTitle");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getDueDate(newValue));
            }

        }
        .setWsField("dueDate");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getExternalAnalysisUrl(newValue));
            }

        }
        .setWsField("externalAnalysisUrl");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getExternalAttemptHandlerUrl(newValue));
            }

        }
        .setWsField("externalAttemptHandlerUrl");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getGradingPeriodId(newValue));
            }

        }
        .setWsField("gradingPeriodId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getGradingSchemaId(newValue));
            }

        }
        .setWsField("gradingSchemaId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsDeleted(newValue));
            }

        }
        .setWsField("deleted");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsHideAttempt(newValue));
            }

        }
        .setWsField("hideAttempt");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsLimitedAttendance(newValue));
            }

        }
        .setWsField("limitedAttendance");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsScorable(newValue));
            }

        }
        .setWsField("scorable");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsShowStatsToStudent(newValue));
            }

        }
        .setWsField("showStatsToStudent");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsSingleAttempt(newValue));
            }

        }
        .setWsField("singleAttempt");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsUserCreatedColumn(newValue));
            }

        }
        .setWsField("userCreatedColumn");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsVisibleInAllTerms(newValue));
            }

        }
        .setWsField("visibleInAllTerms");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsVisibleInBook(newValue));
            }

        }
        .setWsField("visibleInBook");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getIsVisibleToStudents(newValue));
            }

        }
        .setWsField("visibleToStudents");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getLinkId(newValue));
            }

        }
        .setWsField("linkId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getMaxAttempts(newValue));
            }

        }
        .setWsField("maxAttempts");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getPoints(newValue));
            }

        }
        .setWsField("points");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getPosition(newValue));
            }

        }
        .setWsField("position");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getScoreProviderHandle(newValue));
            }

        }
        .setWsField("scoreProviderHandle");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getSecondaryGradingSchemaId(newValue));
            }

        }
        .setWsField("secondaryGradingSchemaId");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getTitle(newValue));
            }

        }
        .setWsField("title");
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
            public void setWsFieldImp(String newValue)
                throws Exception
            {
                return (getArgs().getResultRecord().getWeight(newValue));
            }

        }
        .setWsField("weight");
    }

}
