package wsgen;

import java.util.List;

public class WsgApiRegulationStruct {

    public WsgApiRegulationStruct() {
        super();
    }

    WsgApiRegulationStruct(WsgApiRegulationStruct apiRegulation) {
        this();
        assignNullFields(apiRegulation);
        /*
        this.wsMethodName = apiRegulation.wsMethodName;
        this.bbMethodSignature = apiRegulation.bbMethodSignature;
        this.apiOperation = apiRegulation.apiOperation;
        this.apiBySuffix = apiRegulation.apiBySuffix;
        this.apiResultKind = apiRegulation.apiResultKind;
        this.apiResultKind = apiRegulation.apiResultKind;
        this.apiParamKindList.addAll(apiRegulation.apiParamKindList);
        this.apiParamKind = apiRegulation.apiParamKind;
        this.apiBySuffix = apiRegulation.apiBySuffix;
        this.comment = apiRegulation.comment;*/
    }

    public void assignNullFields(WsgApiRegulationStruct apiRegulation) {
        if (this.wsMethodName == null) {
            this.wsMethodName = apiRegulation.wsMethodName;
        }
        if (this.bbMethodSignature == null) {
            this.bbMethodSignature = apiRegulation.bbMethodSignature;
        }
        if (this.apiOperation == null) {
            this.apiOperation = apiRegulation.apiOperation;
        }
        if (this.apiBySuffix == null) {
            this.apiBySuffix = apiRegulation.apiBySuffix;
        }
        if (this.apiResultKind == null) {
            this.apiResultKind = apiRegulation.apiResultKind;
        }
        if (this.apiResultTypeName == null) {
            this.apiResultTypeName = apiRegulation.apiResultTypeName;
        }
        if (this.apiParamKindList == null) {
            this.apiParamKindList = apiRegulation.apiParamKindList;
        }
        if (this.apiParamKind == null) {
            this.apiParamKind = apiRegulation.apiParamKind;
        }
        if (this.apiParamTypeNameList == null) {
            this.apiParamTypeNameList = apiRegulation.apiParamTypeNameList;
        }
        /*if (this.apiArgsDirectList == null) {
            this.apiArgsDirectList = apiRegulation.apiArgsDirectList;
        }*/
        if (this.needsApiParam == null) {
            this.needsApiParam = apiRegulation.needsApiParam;
        }
        if (this.comment == null) {
            this.comment = apiRegulation.comment;
        }
    }
    public String wsMethodName;
    public String bbMethodSignature;
    public WsgApiOperationKind apiOperation;
    public WsgApiDataKind apiResultKind;
    public String apiResultTypeName;
    public List<WsgApiDataKind> apiParamKindList;
    public WsgApiDataKind apiParamKind;
    public List<String> apiParamTypeNameList;
    //public List<String> apiArgsDirectList;
    public String apiBySuffix;
    public Boolean needsApiParam;
    public String comment;


    public String getDataAccessPackNestedClassName() {
        String res_data_kind;
        if (WsgApiDataKind.ListById.equals(apiResultKind) || WsgApiDataKind.ListByTemplate.equals(apiResultKind)){
            res_data_kind = "List";
        } else res_data_kind = "Record";

        return WsgUtil.firstLetterUCase(apiOperation.name())
            + res_data_kind
            + apiBySuffix;
    }
}
