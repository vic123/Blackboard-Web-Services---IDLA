using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    public class WsgApiRegulationStruct {
        public WsgApiRegulationStruct()
            : base() {
        }

        WsgApiRegulationStruct(WsgApiRegulationStruct apiRegulation):this() {
            this.wsMethodName = apiRegulation.wsMethodName;
            this.bbMethodSignature = apiRegulation.bbMethodSignature;
            this.apiOperation = apiRegulation.apiOperation;
            this.apiResultKind = apiRegulation.apiResultKind;
            this.apiParamKindList.AddRange(apiRegulation.apiParamKindList);
            this.apiBySuffix = apiRegulation.apiBySuffix;
            this.comment = apiRegulation.comment;
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
            if (this.apiParamTypeNameList == null) {
                this.apiParamTypeNameList = apiRegulation.apiParamTypeNameList;
            }
            if (this.apiArgsDirectList == null) {
                this.apiArgsDirectList = apiRegulation.apiArgsDirectList;
            }
            if (this.comment == null) {
                this.comment = apiRegulation.comment;
            }
        }
        public String wsMethodName;
        public String bbMethodSignature;
        public WsgApiOperationKind? apiOperation;
        public WsgApiDataKind? apiResultKind;
        public bool needsApiParam;
        public String apiResultTypeName;
        public List<WsgApiDataKind> apiParamKindList;
        public WsgApiDataKind apiParamKind;
        public List<String> apiParamTypeNameList;
        public List<String> apiArgsDirectList;
        public String apiBySuffix;
        public String comment;

        public String getDataAccessPackNestedClassName() {
            String res_data_kind;
            if (apiResultKind == WsgApiDataKind.ListByTemplate || apiResultKind == WsgApiDataKind.ListById) {
                res_data_kind = "List";
            } else res_data_kind = WsgUtil.firstLetterUCase(WsgApiDataKind.Record.ToString());

            return WsgUtil.firstLetterUCase(apiOperation.ToString())
                + res_data_kind
                + apiBySuffix;
        }
    }
}
