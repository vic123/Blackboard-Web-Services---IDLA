package wsgen;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WsgRegulation {

    //public static WsgRegulation p;

    public String packageName;
    public String dataClassName;
    public String dataClassSimpleName;
    public String apiSuffix;
    public Map<String, String> excludedBbToucherMethodNamesMap = new LinkedHashMap<String, String>();
    public LinkedHashMap<String, WsgBbGetterRegulationStruct> bbGetter2WSFieldMap
                = new LinkedHashMap<String, WsgBbGetterRegulationStruct>();
    public LinkedHashMap<String, WsgBbSetterRegulationStruct> bbSetter2WSFieldMap
                = new LinkedHashMap<String, WsgBbSetterRegulationStruct>();

    public ArrayList<String> includedBbGetterMethodNames = new ArrayList<String>();
    public ArrayList<String> includedBbSetterMethodNames = new ArrayList<String>();
    //--
    public LinkedHashMap<String, WsgBbTouchersLink> wsField2BbTouchersLinkMap
            = new LinkedHashMap<String, WsgBbTouchersLink>();



    public Map<String, String> wsIdField2BbTypeMap = new LinkedHashMap<String, String>();
    //--
    public List<WsgDataRegulationStruct> dataFieldRegulationList = new ArrayList<WsgDataRegulationStruct>();

    public LinkedHashMap<String, String> excludedApiMethodNamesMap = new LinkedHashMap<String, String>();
    public LinkedHashMap<String, String> includedApiMethodNamesMap = new LinkedHashMap<String, String>();
    public List<WsgApiRegulationStruct> apiRegulationList = new ArrayList<WsgApiRegulationStruct>();
    public List<String> apiMethodDataClassPatternList = new ArrayList<String>();
    //public Map<WsgApiOperation, List<String>> apiMethodPrefixPatternListMap;
    public Map<WsgApiOperationKind, List<String>> apiOperationKind2MethodPrefixListMap 
            = new LinkedHashMap<WsgApiOperationKind, List<String>>();
    public Map<String, Map<WsgApiOperationKind, List<String>>> apiClassName2OperationKindMap
            = new LinkedHashMap<String, Map<WsgApiOperationKind, List<String>>>();
    public Map<String, List<String>> dataType2ValuesMap
            = new LinkedHashMap<String, List<String>>();


    public JCodeModel codeModel;
    public JDefinedClass webservicesJDC;
    public JDefinedClass iwebservicesJDC;
    public JDefinedClass dataAccessPackGenJDC;
    public JDefinedClass dataAccessPackArgumentsJDC;
    public JDefinedClass regulationJDC;

    public WsgRegulation() {}
    
    public WsgRegulation(JCodeModel codeModel, JDefinedClass webservicesJDC, JDefinedClass iwebservicesJDC) {
        this.codeModel = codeModel;
        this.webservicesJDC = webservicesJDC;
        this.iwebservicesJDC = iwebservicesJDC;
        overrideGeneratedRegulation();
    }

    public String getDataAccessPackClassName() {
        return packageName + "." + dataClassSimpleName + "AccessPack_" + apiSuffix;
    }
    public String getDataDetailsClassName() {
        return packageName + "." + dataClassSimpleName + "Details";
    }
    public String getDataParamsClassName() {
        return packageName + "." + dataClassSimpleName + "Params";
    }
    public String getDataParamsFieldName() {
        return WsgUtil.firstLetterLCase(dataClassSimpleName) + "Params";
    }

    public String getRegulationClassName() {
        return "wsgen.reggenerated.WsgReg" + dataClassSimpleName + "_Gen";
    }

    protected void overrideGeneratedRegulation() {
        setMainRegulationParams();
        fillExcludedBbToucherMethodNamesMap();
        fillIncludedBbToucherLists();
        fillBBToucher2wsFieldMaps();
        fillDataType2ValuesMap();

        fillWSIdField2BbTypeMap();
        fillApiOperationKind2MethodPrefixListMap();
        fillExcludedApiMethodNamesMap();
        fillIncludedApiMethodNamesMap();
        fillApiRegulationList();
    }

    protected void setMainRegulationParams() {}
    protected void fillExcludedBbToucherMethodNamesMap() {}
    protected void fillIncludedBbToucherLists() {}
    protected void fillBBToucher2wsFieldMaps(){}
    protected void fillDataType2ValuesMap() {}
    protected void fillExcludedApiMethodNamesMap() {}
    protected void fillIncludedApiMethodNamesMap() {}
    protected void fillApiRegulationList() {}


    protected void fillWSIdField2BbTypeMap () {
        wsIdField2BbTypeMap.put("assessmentId", "blackboard.data.qti.asi.AsiObject");
        wsIdField2BbTypeMap.put("categoryId", "blackboard.platform.gradebook2.GradebookType");
        wsIdField2BbTypeMap.put("courseContentId", "blackboard.data.content.Content");
        wsIdField2BbTypeMap.put("courseId", "blackboard.data.course.Course");
        wsIdField2BbTypeMap.put("gradingPeriodId", "blackboard.platform.gradebook2.GradingPeriod");
        wsIdField2BbTypeMap.put("gradingSchemaId", "blackboard.platform.gradebook2.GradingSchema");
        wsIdField2BbTypeMap.put("secondaryGradingSchemaId", "blackboard.platform.gradebook2.GradingSchema");
    }
    protected void fillApiOperationKind2MethodPrefixListMap() {
        List<String> load_pattern_list = new ArrayList<String>();
        load_pattern_list.add("load");
        load_pattern_list.add("get");
        apiOperationKind2MethodPrefixListMap.put(WsgApiOperationKind.Load, load_pattern_list);

        List<String> insert_pattern_list = new ArrayList<String>();
        insert_pattern_list.add("create");
        insert_pattern_list.add("persist");
        apiOperationKind2MethodPrefixListMap.put(WsgApiOperationKind.Insert, insert_pattern_list);

        List<String> update_pattern_list = new ArrayList<String>();
        update_pattern_list.add("persist");
        apiOperationKind2MethodPrefixListMap.put(WsgApiOperationKind.Update, update_pattern_list);

        List<String> persist_pattern_list = new ArrayList<String>();
        persist_pattern_list.add("persist");
        apiOperationKind2MethodPrefixListMap.put(WsgApiOperationKind.Persist, persist_pattern_list);

        List<String> delete_pattern_list = new ArrayList<String>();
        delete_pattern_list.add("delete");
        apiOperationKind2MethodPrefixListMap.put(WsgApiOperationKind.Delete, delete_pattern_list);
    }


}
