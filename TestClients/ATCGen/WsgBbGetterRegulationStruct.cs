using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    public class WsgBbGetterRegulationStruct : WsgBbToucherRegulationStruct {
        public WsgBbGetterRegulationStruct(String bbGetMethodName, 
                                    String boolPrefix, 
                                    String wsFieldName, 
                                    String returnType):base(wsFieldName) {
            this.boolPrefix = boolPrefix;
            this.bbGetMethodName = bbGetMethodName;
            this.returnType = returnType;
        }

        String getBoolPrefixLCase() {
            return WsgUtil.firstLetterLCase(boolPrefix);
        }

        String getBoolPrefixUCase() {
            return (boolPrefix);
        }
        public String bbGetMethodName;
        public String returnType;
        public String boolPrefix;
    }
}
