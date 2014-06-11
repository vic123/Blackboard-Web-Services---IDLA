/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbcrsws;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author vic
 */
    public class CourseCopyParams {
        private List<String> includeAreaList;
        private List<String> excludeAreaList;

        public CourseCopyParams() {
            includeAreaList = new ArrayList<String>();
            excludeAreaList = new ArrayList<String>();
        }
        /**
         * @return the includeAreaList
         */
        public List<String> getIncludeAreaList() {
            return includeAreaList;
        }

        /**
         * @param includeAreaList the includeAreaList to set
         */
        public void setIncludeAreaList(List<String> includeAreaList) {
            this.includeAreaList = includeAreaList;
        }

        /**
         * @return the excludeAreaList
         */
        public List<String> getExcludeAreaList() {
            return excludeAreaList;
        }

        /**
         * @param excludeAreaList the excludeAreaList to set
         */
        public void setExcludeAreaList(List<String> excludeAreaList) {
            this.excludeAreaList = excludeAreaList;
        }
    }
