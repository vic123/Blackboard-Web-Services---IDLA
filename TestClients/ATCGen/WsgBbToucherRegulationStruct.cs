using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    public class WsgBbToucherRegulationStruct {
        private String wsFieldName;

        public WsgBbToucherRegulationStruct(String wsFieldName)
            : base() {
            
            this.wsFieldName = WsgUtil.firstLetterLCase(wsFieldName);
        }

        public String getFieldNameLCase() {
            String res = WsgUtil.firstLetterLCase(wsFieldName);
            return res;
        }
    }


}
