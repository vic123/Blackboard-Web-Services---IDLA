using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    public class WsgBbSetterRegulationStruct: WsgBbToucherRegulationStruct {
        public WsgBbSetterRegulationStruct(String bbSetMethodName, 
                                        String wsFieldName, 
                                        String paramType):base(wsFieldName) {
            
            this.bbSetMethodName = bbSetMethodName;
            this.paramType = paramType;
        }
        public String bbSetMethodName;
        public String paramType;
    }
}
