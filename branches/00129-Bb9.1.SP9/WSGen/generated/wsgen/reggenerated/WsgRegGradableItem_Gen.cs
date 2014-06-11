
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {










public class WsgRegGradableItem_Gen: WsgRegulation {
    override protected void fillBBToucher2wsFieldMaps() {
        String key;
        key = "blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel getAggregationModel();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getAggregationModel", "", "aggregationModel", "blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel"));
        key = "void setAggregationModel(blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setAggregationModel", "aggregationModel", "blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel"));
        key = "blackboard.persist.Id getAssessmentId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getAssessmentId", "", "assessmentId", "blackboard.persist.Id"));
        key = "void setAssessmentId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setAssessmentId", "assessmentId", "blackboard.persist.Id"));
        key = "blackboard.persist.Id getId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getId", "", "bbId", "blackboard.persist.Id"));
        key = "void setId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setId", "bbId", "blackboard.persist.Id"));
        key = "long getVersion();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getVersion", "", "bookVersion", "long"));
        key = "void setVersion(blackboard.persist.RowVersion);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setVersion", "bookVersion", "blackboard.persist.RowVersion"));
        key = "blackboard.platform.gradebook2.GradableItem$CalculationType getCalculatedInd();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getCalculatedInd", "", "calculatedInd", "blackboard.platform.gradebook2.GradableItem$CalculationType"));
        key = "void setCalculatedInd(blackboard.platform.gradebook2.GradableItem$CalculationType);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setCalculatedInd", "calculatedInd", "blackboard.platform.gradebook2.GradableItem$CalculationType"));
        key = "java.lang.String getCategory();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getCategory", "", "category", "java.lang.String"));
        key = "void setCategory(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setCategory", "category", "java.lang.String"));
        key = "blackboard.persist.Id getCategoryId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getCategoryId", "", "categoryId", "blackboard.persist.Id"));
        key = "void setCategoryId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setCategoryId", "categoryId", "blackboard.persist.Id"));
        key = "blackboard.persist.Id getCourseContentId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getCourseContentId", "", "courseContentId", "blackboard.persist.Id"));
        key = "void setCourseContentId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setCourseContentId", "courseContentId", "blackboard.persist.Id"));
        key = "blackboard.persist.Id getCourseId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getCourseId", "", "courseId", "blackboard.persist.Id"));
        key = "void setCourseId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setCourseId", "courseId", "blackboard.persist.Id"));
        key = "java.util.Calendar getDateAdded();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getDateAdded", "", "dateAdded", "java.util.Calendar"));
        key = "void setDateAdded(java.util.Calendar);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDateAdded", "dateAdded", "java.util.Calendar"));
        key = "java.util.Calendar getDateModified();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getDateModified", "", "dateModified", "java.util.Calendar"));
        key = "void setDateModified(java.util.Calendar);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDateModified", "dateModified", "java.util.Calendar"));
        key = "blackboard.base.FormattedText getDescription();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getDescription", "", "description", "blackboard.base.FormattedText"));
        key = "void setDescription(blackboard.base.FormattedText);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDescription", "description", "blackboard.base.FormattedText"));
        key = "java.lang.String getDisplayTitle();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getDisplayTitle", "", "displayTitle", "java.lang.String"));
        key = "void setDisplayTitle(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDisplayTitle", "displayTitle", "java.lang.String"));
        key = "java.util.Calendar getDueDate();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getDueDate", "", "dueDate", "java.util.Calendar"));
        key = "void setDueDate(java.util.Calendar);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDueDate", "dueDate", "java.util.Calendar"));
        key = "java.lang.String getExternalAnalysisUrl();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getExternalAnalysisUrl", "", "externalAnalysisUrl", "java.lang.String"));
        key = "void setExternalAnalysisUrl(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setExternalAnalysisUrl", "externalAnalysisUrl", "java.lang.String"));
        key = "java.lang.String getExternalAttemptHandlerUrl();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getExternalAttemptHandlerUrl", "", "externalAttemptHandlerUrl", "java.lang.String"));
        key = "void setExternalAttemptHandlerUrl(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setExternalAttemptHandlerUrl", "externalAttemptHandlerUrl", "java.lang.String"));
        key = "blackboard.persist.Id getGradingPeriodId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getGradingPeriodId", "", "gradingPeriodId", "blackboard.persist.Id"));
        key = "void setGradingPeriodId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setGradingPeriodId", "gradingPeriodId", "blackboard.persist.Id"));
        key = "blackboard.persist.Id getGradingSchemaId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getGradingSchemaId", "", "gradingSchemaId", "blackboard.persist.Id"));
        key = "void setGradingSchemaId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setGradingSchemaId", "gradingSchemaId", "blackboard.persist.Id"));
        key = "boolean isDeleted();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isDeleted", "is", "deleted", "boolean"));
        key = "void setDeleted(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setDeleted", "deleted", "boolean"));
        key = "boolean isHideAttempt();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isHideAttempt", "is", "hideAttempt", "boolean"));
        key = "void setHideAttempt(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setHideAttempt", "hideAttempt", "boolean"));
        key = "boolean isLimitedAttendance();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isLimitedAttendance", "is", "limitedAttendance", "boolean"));
        key = "void setLimitedAttendance(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setLimitedAttendance", "limitedAttendance", "boolean"));
        key = "boolean isScorable();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isScorable", "is", "scorable", "boolean"));
        key = "void setScorable(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setScorable", "scorable", "boolean"));
        key = "boolean isShowStatsToStudent();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isShowStatsToStudent", "is", "showStatsToStudent", "boolean"));
        key = "void setShowStatsToStudent(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setShowStatsToStudent", "showStatsToStudent", "boolean"));
        key = "boolean isSingleAttempt();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isSingleAttempt", "is", "singleAttempt", "boolean"));
        key = "void setSingleAttempt(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setSingleAttempt", "singleAttempt", "boolean"));
        key = "boolean isUserCreatedColumn();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isUserCreatedColumn", "is", "userCreatedColumn", "boolean"));
        key = "void setUserCreatedColumn(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setUserCreatedColumn", "userCreatedColumn", "boolean"));
        key = "boolean isVisibleInAllTerms();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isVisibleInAllTerms", "is", "visibleInAllTerms", "boolean"));
        key = "void setVisibleInAllTerms(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setVisibleInAllTerms", "visibleInAllTerms", "boolean"));
        key = "boolean isVisibleInBook();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isVisibleInBook", "is", "visibleInBook", "boolean"));
        key = "void setVisibleInBook(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setVisibleInBook", "visibleInBook", "boolean"));
        key = "boolean isVisibleToStudents();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("isVisibleToStudents", "is", "visibleToStudents", "boolean"));
        key = "void setVisibleToStudents(boolean);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setVisibleToStudents", "visibleToStudents", "boolean"));
        key = "java.lang.String getLinkId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getLinkId", "", "linkId", "java.lang.String"));
        key = "void setLinkId(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setLinkId", "linkId", "java.lang.String"));
        key = "int getMaxAttempts();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getMaxAttempts", "", "maxAttempts", "int"));
        key = "void setMaxAttempts(int);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setMaxAttempts", "maxAttempts", "int"));
        key = "double getPoints();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getPoints", "", "points", "double"));
        key = "void setPoints(double);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setPoints", "points", "double"));
        key = "int getPosition();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getPosition", "", "position", "int"));
        key = "void setPosition(int);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setPosition", "position", "int"));
        key = "java.lang.String getScoreProviderHandle();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getScoreProviderHandle", "", "scoreProviderHandle", "java.lang.String"));
        key = "void setScoreProviderHandle(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setScoreProviderHandle", "scoreProviderHandle", "java.lang.String"));
        key = "blackboard.persist.Id getSecondaryGradingSchemaId();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getSecondaryGradingSchemaId", "", "secondaryGradingSchemaId", "blackboard.persist.Id"));
        key = "void setSecondaryGradingSchemaId(blackboard.persist.Id);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setSecondaryGradingSchemaId", "secondaryGradingSchemaId", "blackboard.persist.Id"));
        key = "java.lang.String getTitle();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getTitle", "", "title", "java.lang.String"));
        key = "void setTitle(java.lang.String);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setTitle", "title", "java.lang.String"));
        key = "float getWeight();";
        this.bbGetter2WSFieldMap.Add(key, new WsgBbGetterRegulationStruct("getWeight", "", "weight", "float"));
        key = "void setWeight(float);";
        this.bbSetter2WSFieldMap.Add(key, new WsgBbSetterRegulationStruct("setWeight", "weight", "float"));
    }

    override public void fillDataType2ValuesMap() {
        String key;
        List<String> list;
        key = "blackboard.base.FormattedText";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "blackboard.persist.DataType";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "blackboard.persist.Id";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "blackboard.platform.gradebook2.GradableItem$AttemptAggregationModel";
        list = new List<String>();
        list.Add("Average");
        list.Add("First");
        list.Add("Highest");
        list.Add("Last");
        list.Add("Lowest");
        this.dataType2ValuesMap.Add(key, list);
        key = "blackboard.platform.gradebook2.GradableItem$CalculationType";
        list = new List<String>();
        list.Add("AVERAGE");
        list.Add("MINMAX");
        list.Add("NON_CALCULATED");
        list.Add("TOTAL");
        list.Add("WEIGHTED_TOTAL");
        this.dataType2ValuesMap.Add(key, list);
        key = "boolean";
        list = new List<String>();
        list.Add("false");
        list.Add("true");
        this.dataType2ValuesMap.Add(key, list);
        key = "double";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "float";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "int";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "java.lang.String";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
        key = "java.util.Calendar";
        list = new List<String>();
        this.dataType2ValuesMap.Add(key, list);
    }
    override protected void fillApiRegulationList() {
        WsgApiRegulationStruct apiRegulationStruct;
        List<WsgApiDataKind> apiParamKindList;
        List<String> apiParamTypeNameList;
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemDeleteListById";
            apiRegulationStruct.bbMethodSignature = "void deleteGradebookItem(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Delete;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListById;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.ListById;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemDeleteRecordById";
            apiRegulationStruct.bbMethodSignature = "void deleteGradebookItem(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Delete;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemInsertListById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Insert;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListById;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.ListById;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemInsertRecordById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Insert;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByCourseId";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItems(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListByTemplate;
            apiRegulationStruct.needsApiParam = true;
            apiRegulationStruct.apiResultTypeName = "java.util.List";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ByCourseId";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByGradingPeriodId";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItemsByGradingPeriod(blackboard.persist.Id, long);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListByTemplate;
            apiRegulationStruct.needsApiParam = true;
            apiRegulationStruct.apiResultTypeName = "java.util.List";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiParamTypeNameList.Add("long");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ByGradingPeriodId";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByGradingPeriodIdAndBookVersion";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItemsByGradingPeriod(blackboard.persist.Id, long);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListByTemplate;
            apiRegulationStruct.needsApiParam = true;
            apiRegulationStruct.apiResultTypeName = "java.util.List";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiParamTypeNameList.Add("long");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ByGradingPeriodIdAndBookVersion";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListById";
            apiRegulationStruct.bbMethodSignature = "blackboard.platform.gradebook2.GradableItem getGradebookItem(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListById;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "blackboard.platform.gradebook2.GradableItem";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.ListById;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadRecordByAssessmentId";
            apiRegulationStruct.bbMethodSignature = "blackboard.platform.gradebook2.GradableItem getGradebookItemByAssessmentId(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "blackboard.platform.gradebook2.GradableItem";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ByAssessmentId";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadRecordByCourseContentId";
            apiRegulationStruct.bbMethodSignature = "blackboard.platform.gradebook2.GradableItem getGradebookItemByContentId(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "blackboard.platform.gradebook2.GradableItem";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ByCourseContentId";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadRecordById";
            apiRegulationStruct.bbMethodSignature = "blackboard.platform.gradebook2.GradableItem getGradebookItem(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "blackboard.platform.gradebook2.GradableItem";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Record));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.persist.Id");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemPersistListById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Persist;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListById;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.ListById;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemPersistRecordById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Persist;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemUpdateListById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Update;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.ListById;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.ListById;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
        {
            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemUpdateRecordById";
            apiRegulationStruct.bbMethodSignature = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Update;
            apiRegulationStruct.apiResultKind = WsgApiDataKind.Record;
            apiRegulationStruct.needsApiParam = false;
            apiRegulationStruct.apiResultTypeName = "void";
            apiParamKindList = new List<WsgApiDataKind>();
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiParamKindList.Add((WsgApiDataKind.Unknown));
            apiRegulationStruct.apiParamKindList = apiParamKindList;
            apiRegulationStruct.apiParamKind = WsgApiDataKind.Record;
            apiParamTypeNameList = new List<String>();
            apiParamTypeNameList.Add("blackboard.platform.gradebook2.GradableItem");
            apiParamTypeNameList.Add("boolean");
            apiRegulationStruct.apiParamTypeNameList = apiParamTypeNameList;
            apiRegulationStruct.apiBySuffix = "ById";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.Add(apiRegulationStruct);
        }
    }

}

}