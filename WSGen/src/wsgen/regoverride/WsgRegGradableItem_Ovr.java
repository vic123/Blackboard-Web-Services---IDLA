/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen.regoverride;

import wsgen.WsgBbGetterRegulationStruct;
import wsgen.WsgBbSetterRegulationStruct;
import wsgen.WsgApiRegulationStruct;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

import wsgen.WsgRegulation;
import wsgen.WsgApiOperationKind;
import wsgen.WsgApiDataKind;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class WsgRegGradableItem_Ovr extends WsgRegulation {
    public WsgRegGradableItem_Ovr(JCodeModel codeModel, JDefinedClass webservicesJDC, JDefinedClass iwebservicesJDC) {
        super(codeModel, webservicesJDC, iwebservicesJDC);
    }
    @Override
    protected void setMainRegulationParams() {
        packageName = "bbgbws";
        dataClassName = blackboard.platform.gradebook2.GradableItem.class.getName();
        dataClassSimpleName = blackboard.platform.gradebook2.GradableItem.class.getSimpleName();//"GradableItem";
        apiSuffix = "GB2";
        apiMethodDataClassPatternList.add(dataClassSimpleName);
        apiMethodDataClassPatternList.add("GradebookItem");
        //String apiClassName = blackboard.platform.gradebook2.impl.GradebookManagerFacade.class.getName();
        String apiClassName = blackboard.platform.gradebook2.GradebookManager.class.getName();
        apiClassName2OperationKindMap.put(apiClassName, apiOperationKind2MethodPrefixListMap);

    }
    @Override
    protected void fillExcludedBbToucherMethodNamesMap() {
        String comment;
        comment = "!! Functionality is implemeted though GradingSchemaId, GradingSchema by itself needs individual WS API interface.";
        //excludedBbToucherMethodNamesMap.put("blackboard.platform.gradebook2.GradingSchema blackboard.platform.gradebook2.GradableItem.getGradingSchema();", comment);
        //excludedBbToucherMethodNamesMap.put("void blackboard.platform.gradebook2.GradableItem.setGradingSchema(blackboard.platform.gradebook2.GradingSchema);", comment);
        excludedBbToucherMethodNamesMap.put("blackboard.platform.gradebook2.GradingSchema getGradingSchema();", comment);
        excludedBbToucherMethodNamesMap.put("void setGradingSchema(blackboard.platform.gradebook2.GradingSchema);", comment);
        comment = "!! GET has a parameter. SchemaValue is calculated by BaseGradingSchema.Type."
                + " May be an appropriate way to access it would be through GradingSchema WS API, not as the data field";
        excludedBbToucherMethodNamesMap.put("java.lang.String getSchemaValue(java.lang.Double);", comment);
        comment = "!! GET has a parameter. Score is similar to SchemaValue and calculated by BaseGradingSchema.Type."
                + " Score and SchemaValue are different visual representation of grade value";
        excludedBbToucherMethodNamesMap.put("java.lang.Double getScore(java.lang.String);", comment);
        comment = "!! GET has a parameter. It is calculated field:"
                + " return (this._scorable) && (this._calculatedInd == CalculationType.NON_CALCULATED) && ((this._points > 0.0D) || (includeExtraCredits));";
        excludedBbToucherMethodNamesMap.put("boolean isGradeItem(boolean);", comment);
        comment = "!! GET has parameters. It is calculated from other fields.";
        excludedBbToucherMethodNamesMap.put("java.lang.String getExtendedTitle(blackboard.util.GradeFormat, blackboard.platform.intl.BbResourceBundle, java.util.HashMap);", comment);
        comment = "!! Missing corresponding SET method. ScoreProvider is accessed through ScoreProviderHandle."
                + " ScoreProvider may need indivudual API.";
        excludedBbToucherMethodNamesMap.put("blackboard.platform.gradebook2.ScoreProvider getScoreProvider();", comment);
        comment = "!! Missing corresponding SET method. Covered through get/setCourseContentId. CourseContent needs separate WS API.";
        this.excludedBbToucherMethodNamesMap.put("blackboard.data.content.Content getCourseContent();", comment);

    }

    @Override
    protected void fillIncludedBbToucherLists() {
        includedBbGetterMethodNames.add("long getVersion();");
        includedBbSetterMethodNames.add("void setVersion(blackboard.persist.RowVersion);");
    }

    @Override
    protected void fillBBToucher2wsFieldMaps(){
        String key;
        key = "blackboard.persist.Id getId();";
        bbGetter2WSFieldMap.put(key, new wsgen.WsgBbGetterRegulationStruct("getId", "", "bbId", "blackboard.persist.Id"));
        key = "void setId(blackboard.persist.Id);";
        bbSetter2WSFieldMap.put(key, new wsgen.WsgBbSetterRegulationStruct("setId", "bbId", "blackboard.persist.Id"));
        key = "long getVersion();";
        this.bbGetter2WSFieldMap.put(key, new WsgBbGetterRegulationStruct("getVersion", "", "bookVersion", "long"));
        key = "void setVersion(blackboard.persist.RowVersion);";
        this.bbSetter2WSFieldMap.put(key, new WsgBbSetterRegulationStruct("setVersion", "bookVersion", "blackboard.persist.RowVersion"));


    }

    @Override
    protected void fillExcludedApiMethodNamesMap() {
        String comment;
        comment = "returns list of GradableItemForDisplay, which has just a subset of GradableItem data";
        excludedApiMethodNamesMap.put("java.util.List getGradableItemsForDisplay(blackboard.persist.Id);", comment);
        comment = "public void persistGradebookItem(GradableItem g, boolean primarySchemaChanged) should be used, "
                + "gradebook\\gradebook2\\instructor\\addModifyItemDefinition.jsp"
                + "form.elements.primarySchemaChanged.value = (originalSchema == '${textSchemaID}') && (currentSchema != '${textSchemaID}') ? 'true' : 'false';";
        excludedApiMethodNamesMap.put("void persistGradebookItem(blackboard.platform.gradebook2.GradableItem);", comment);
        comment = "void persistGradebookItem(blackboard.platform.gradebook2.GradableItem, boolean);"
                + "is a a better choice";
        excludedApiMethodNamesMap.put("blackboard.platform.gradebook2.GradableItem createGradableItem(java.lang.String, blackboard.persist.Id, double, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean);", comment);
        
    }
    @Override
    protected void fillApiRegulationList() {
        wsgen.WsgApiRegulationStruct apiRegulationStruct;
        List<WsgApiDataKind> appk_list;
        List<String> atnm_list;
        {
            /*
            api_reg = new wsgen.WsgApiRegulationGenerator.WsgApiRegulation();
            api_reg.bbMethodSignature = "void deleteGradebookItem(blackboard.persist.Id);";
            api_reg.apiBySuffix = "ById";
            genRegulation.apiRegulationList.add(api_reg);*/
            apiRegulationStruct = new wsgen.WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByGradingPeriodId";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItemsByGradingPeriod(blackboard.persist.Id, long);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiBySuffix = "ByGradingPeriodId";
            apiRegulationList.add(apiRegulationStruct);

            apiRegulationStruct = new wsgen.WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByGradingPeriodIdAndBookVersion";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItemsByGradingPeriod(blackboard.persist.Id, long);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiBySuffix = "ByGradingPeriodIdAndBookVersion";
            apiRegulationList.add(apiRegulationStruct);

            apiRegulationStruct = new wsgen.WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadListByCourseId";
            apiRegulationStruct.bbMethodSignature = "java.util.List getGradebookItems(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiBySuffix = "ByCourseId";
            apiRegulationList.add(apiRegulationStruct);

            apiRegulationStruct = new WsgApiRegulationStruct();
            apiRegulationStruct.wsMethodName = "gradableItemLoadRecordByCourseContentId";
            apiRegulationStruct.bbMethodSignature = "blackboard.platform.gradebook2.GradableItem getGradebookItemByContentId(blackboard.persist.Id);";
            apiRegulationStruct.apiOperation = WsgApiOperationKind.Load;
            apiRegulationStruct.apiBySuffix = "ByCourseContentId";
            apiRegulationStruct.comment = "";
            this.apiRegulationList.add(apiRegulationStruct);
        }
    }

    @Override
    protected void fillWSIdField2BbTypeMap() {
        super.fillWSIdField2BbTypeMap();
        wsIdField2BbTypeMap.put("bbId", "blackboard.platform.gradebook2.GradableItem");
    }
            
}
