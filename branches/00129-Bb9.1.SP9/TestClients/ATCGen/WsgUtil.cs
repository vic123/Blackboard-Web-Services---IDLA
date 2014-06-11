using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ATCGen {
    class WsgUtil {
        public static String firstLetterLCase(String str) {
            String res = str.Substring(0, 1).ToLower()
                    + str.Substring(1, str.Length-1);
            return res;
        }
        public static String firstLetterUCase(String str) {
            String res = str.Substring(0, 1).ToUpper()
                    + str.Substring(1, str.Length-1);
            return res;
        }
    }
}
