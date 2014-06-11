using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    public abstract class WsgRegulation {
        public String dataClassSimpleName;
        //Dictionary<String, String> excludedMethodNames = new Dictionary<String, String>();
        public Dictionary<String, WsgBbGetterRegulationStruct> bbGetter2WSFieldMap = new Dictionary<String, WsgBbGetterRegulationStruct>();
        public Dictionary<String, WsgBbSetterRegulationStruct> bbSetter2WSFieldMap = new Dictionary<String, WsgBbSetterRegulationStruct>();
        public Dictionary<String, List<String>> dataType2ValuesMap = new Dictionary<String, List<String>>();
        
        //public static Dictionary<String, String> excludedMethodNamesMap = new Dictionary<String, String>();
        //public static Dictionary<String, String> includedMethodNamesMap = new Dictionary<String, String>();
        public List<WsgApiRegulationStruct> apiRegulationList = new List<WsgApiRegulationStruct>();
        protected abstract void fillBBToucher2wsFieldMaps();
        public abstract void fillDataType2ValuesMap();
        protected abstract void fillApiRegulationList();
        public void fillRegulationStructs() {
            fillBBToucher2wsFieldMaps();
            fillDataType2ValuesMap();
            fillApiRegulationList();
        }
    }
}
