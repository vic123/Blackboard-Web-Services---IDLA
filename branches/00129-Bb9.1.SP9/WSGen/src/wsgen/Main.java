/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;


import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JArray;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JVar;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JCatchBlock;
import com.sun.codemodel.JType;

import org.apache.commons.lang3.StringUtils;

import blackboard.platform.gradebook2.GradableItem;
//import bbwscommon.*;
import bbwscommon.BbWsUtil;
import bbwscommon.BbWsDataDetails;
import bbwscommon.BbWsDataAccessPack;
import bbwscommon.BbWsException;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.io.File;
/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
/*
 * http://stackoverflow.com/questions/12588121/initializing-data-structures-to-constant-values-with-mixed-data-types
static final xyz[] example1 = new xyz[] { new xyz() {{ a = "value a"; b = 42; }},
                                          new xyz() {{ a = "value b"; b = 43; }}
                                        };

 */
        public static String firstLetterUCase(String str) {
            String res = StringUtils.substring(str, 0, 1).toUpperCase()
                    + StringUtils.substring(str, 1, str.length());
                    //str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
            return res;
        }
        public static String firstLetterLCase(String str) {
            String res = StringUtils.substring(str, 0, 1).toLowerCase()
                    + StringUtils.substring(str, 1, str.length());
                    //str.substring(0, 1).toLowerCase()+ str.substring(1, str.length());
            return res;
        }

    public static class WSGDataRegulationHashMap<V extends WSGDataFieldRegulation>
            extends LinkedHashMap<String, V> {
    }
    public static class WSGDataGetterRegulationHashMap
            extends WSGDataRegulationHashMap<WSGDataGetterRegulation> {
        }
    public static class WSGDataSetterRegulationHashMap
            extends WSGDataRegulationHashMap<WSGDataSetterRegulation> {
    }


    public static class WSGDataFieldRegulation {
        private String wsFieldName;
        public WSGDataFieldRegulation(String wsFieldName) {
            this.wsFieldName = firstLetterLCase(wsFieldName);
        }
        public String getFieldNameLCase() {
            String res = firstLetterLCase(wsFieldName);
            return res;
        }
    }
    public static class WSGDataGetterRegulation extends WSGDataFieldRegulation {
        public WSGDataGetterRegulation(String bbGetMethodName, String boolPrefix, 
                    String wsFieldName, String returnType) {
            super(wsFieldName);
            this.boolPrefix = boolPrefix;
            this.bbGetMethodName = bbGetMethodName;
            this.returnType = returnType;
        }
        String getBoolPrefixLCase() {
            return firstLetterLCase(boolPrefix);
        }
        String getBoolPrefixUCase() {
            return firstLetterUCase(boolPrefix);
        }
        public String bbGetMethodName;
        public String returnType;
        public String boolPrefix;
    }

    public static class WSGDataSetterRegulation extends WSGDataFieldRegulation {
        public WSGDataSetterRegulation(String bbSetMethodName, String wsFieldName, String paramType) {
            super(wsFieldName);
            this.bbSetMethodName = bbSetMethodName;
            this.paramType = paramType;
        }
        public String bbSetMethodName;
        public String paramType;
    }
    public static class WSGDataActionsLink  {
        public String dataGetterSignature;
        public String dataSetterSignature;
    }
    public static class WSGDataRegulation  {
        WSGDataRegulation(WSGDataActionsLink dal) throws Exception {
            WSGDataGetterRegulation dgr
                    = dataStructRegulation.bbGetter2wsFieldMap.get(dal.dataGetterSignature);
            WSGDataSetterRegulation dsr
                    = dataStructRegulation.bbSetter2wsFieldMap.get(dal.dataSetterSignature);
            this.wsFieldName = dgr.getFieldNameLCase();
            this.boolPrefix = firstLetterLCase(dgr.boolPrefix);
            this.dataGetterSignature = dal.dataGetterSignature;
            this.dataSetterSignature = dal.dataSetterSignature;
            this.bbGetMethodName = dgr.bbGetMethodName;
            this.bbSetMethodName = dsr.bbSetMethodName;
            this.bbType = dgr.returnType;
            if (!this.bbType.equals(dsr.paramType)) {
                throw new Exception ("ASSERT: if (!this.bbType.equals(dsr.paramType) {");
            }
            bbTypeClass = ClassPool.getDefault().get(this.bbType);
            String body_gen_class_name = null;
            boolean is_enum = bbTypeClass.getClass().isEnum();
            String base_name = null;
            if (bbTypeClass.getSuperclass() != null) {
                base_name = bbTypeClass.getSuperclass().getName();
                is_enum = bbTypeClass.getSuperclass().getClass().isEnum();
            } else base_name = bbTypeClass.getName();
            if (bbTypeClass.getSuperclass() != null && bbTypeClass.getSuperclass().getName().equals("java.lang.Enum")) body_gen_class_name
            //if (bbTypeClass.getClass().isEnum()) body_gen_class_name
                                    = DataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_enum";
            else body_gen_class_name = DataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_"
                + bbType.replace(".", "_");
            Class body_gen_class = Class.forName(body_gen_class_name);
            toucherMethodBodiesGenerator =
                BbWsUtil.<DataAccessPackGenerator.FieldToucherMethodBodiesGenerator>
                    newClassInstanceWithThrow(body_gen_class);
        }
        public String wsFieldName;
        public String boolPrefix;
        public String dataGetterSignature;
        public String dataSetterSignature;
        public String bbGetMethodName;
        public String bbSetMethodName;
        public String bbType;
        public CtClass bbTypeClass;
        public DataAccessPackGenerator.FieldToucherMethodBodiesGenerator toucherMethodBodiesGenerator;
        private String getBaseMethodName() {
            if (StringUtils.equals("", boolPrefix)) return firstLetterUCase(wsFieldName);
            else return firstLetterUCase(boolPrefix) + firstLetterUCase(wsFieldName);
        }
        public String getWsGetMethodName() {
            return "get" + getBaseMethodName();
        }
        public String getWsSetMethodName() {
            return "set" + getBaseMethodName();
        }
        public String getWsFieldName() {
            if (StringUtils.equals("", boolPrefix)) return firstLetterLCase(wsFieldName);
            else return firstLetterLCase(boolPrefix) + firstLetterUCase(wsFieldName);
        }
    }

    public static class WSGDataField2ActionRegulationActionsLinkHashMap
            extends LinkedHashMap<String, WSGDataActionsLink> {
    }



/*
    public static class WSGDataLinkedHashMap<String, WSGDataFieldRegulation>
            extends LinkedHashMap<String, WSGDataFieldRegulation>
            implements IWSGDataLinkedHashMap{
        //@Override
        public WSGDataFieldRegulation get(String key) {
            return super.get(key);
        }

    }
*/

    public static class WSGDataStructRegulation {
        public String packageName;
        public String className;
        public LinkedHashMap<String, String> excludedMethodNames = new LinkedHashMap<String, String>();
        public ArrayList<String> forcedIncludeGetMethodNames = new ArrayList<String>();
        public ArrayList<String> forcedIncludeSetMethodNames = new ArrayList<String>();

        //public LinkedHashMap<String, WSGDataGetterRegulation> bbGetter2wsFieldMap = new LinkedHashMap<String, WSGDataGetterRegulation>();
        //public LinkedHashMap<String, WSGDataSetterRegulation> bbSetter2wsFieldMap = new LinkedHashMap<String, WSGDataSetterRegulation>();
        //public WSGDataRegulationHashMap<WSGDataGetterRegulation> bbGetter2wsFieldMap = new WSGDataRegulationHashMap<WSGDataGetterRegulation>();
        //public WSGDataRegulationHashMap<WSGDataSetterRegulation> bbSetter2wsFieldMap = new WSGDataRegulationHashMap<WSGDataSetterRegulation>();
        public WSGDataGetterRegulationHashMap bbGetter2wsFieldMap = new WSGDataGetterRegulationHashMap();
        public WSGDataSetterRegulationHashMap bbSetter2wsFieldMap = new WSGDataSetterRegulationHashMap();
        public WSGDataField2ActionRegulationActionsLinkHashMap dataField2ActionsLinkMap = new WSGDataField2ActionRegulationActionsLinkHashMap();
        public List<WSGDataRegulation> dataRegulationList = new ArrayList<WSGDataRegulation>();
    }
    static WSGDataStructRegulation dataStructRegulation = new WSGDataStructRegulation();
    static {
        dataStructRegulation.packageName = "bbgbws";
        dataStructRegulation.className = "GradableItem";
    }
    public static void fillExcludedMethodNames() {
        String comment;
        comment = "!! needs individual CRUD interface";
        dataStructRegulation.excludedMethodNames.put("blackboard.platform.gradebook2.GradingSchema getGradingSchema();", comment);
        dataStructRegulation.excludedMethodNames.put("void setGradingSchema(blackboard.platform.gradebook2.GradingSchema);", comment);
    }
    public static void fillBBAction2wsFieldMaps() {
        String key;
        key = "blackboard.persist.Id getId();";
        dataStructRegulation.bbGetter2wsFieldMap.put(key, new wsgen.Main.WSGDataGetterRegulation("getId", "", "bbId", "blackboard.persist.Id"));
        key = "void setId(blackboard.persist.Id);";
        dataStructRegulation.bbSetter2wsFieldMap.put(key, new wsgen.Main.WSGDataSetterRegulation("setId", "bbId", "blackboard.persist.Id"));
        key = "String getDescription().getFormattedText()";
        dataStructRegulation.bbGetter2wsFieldMap.put(key, new wsgen.Main.WSGDataGetterRegulation("getDescription().getFormattedText", "", "formattedDescription", "String"));
    }
    public static void initDataRegulationList() throws Exception {
        fillExcludedMethodNames();
        fillBBAction2wsFieldMaps();
        ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("blackboard.platform.gradebook2.GradableItem");
            CtClass cc_obj = pool.get("java.lang.Object");
            while (!cc.equals(cc_obj)) {
                CtMethod[] meth_arr = cc.getDeclaredMethods();
                for (CtMethod method: meth_arr){
                    if (!AccessFlag.isPublic(method.getMethodInfo().getAccessFlags())) continue;
                    String m_name = method.getName();

                    String m_signature = //method.getSignature();
                            method.getReturnType().getName() + " " + m_name + "(";
                    for (CtClass p_type: method.getParameterTypes()) {
                        m_signature += p_type.getName() + ", ";
                    }
                    if (method.getParameterTypes().length > 0) {
                        m_signature = m_signature.substring(0, m_signature.length()-2);
                    }
                    m_signature += ");";

                    if (dataStructRegulation.bbSetter2wsFieldMap.containsKey(m_signature)
                            || dataStructRegulation.bbGetter2wsFieldMap.containsKey(m_signature)
                            || dataStructRegulation.excludedMethodNames.containsKey(m_signature)
                       ) continue;
                    String f_name = null;
                    for (String prefix: new String[]{"get", "set"}) {
                        if (m_name.startsWith(prefix)) {
                            f_name = m_name.substring(prefix.length());
                        }
                    }
                    String bool_prefix = "";
                    for (String prefix: new String[]{"is", "has"}) {
                        if (m_name.startsWith(prefix)) {
                            f_name = m_name.substring(prefix.length());
                            bool_prefix = prefix;
                        }
                    }
                    if (f_name == null
                            && (dataStructRegulation.forcedIncludeGetMethodNames.contains(m_signature)
                                || dataStructRegulation.forcedIncludeSetMethodNames.contains(m_signature))
                    ) f_name = m_name;
                    if (f_name == null) {
                        dataStructRegulation.excludedMethodNames.put(m_signature,
                                "Unrecognized method naming convention");
                        continue;
                    }
                    if (m_name.startsWith("get")
                            || m_name.startsWith("is")
                            || m_name.startsWith("has")
                            || dataStructRegulation.forcedIncludeSetMethodNames.contains(m_signature)) {
                        String r_type = method.getReturnType().getName();
                        if ((r_type.compareToIgnoreCase("void") == 0
                            || method.getParameterTypes().length != 0)
                            && !dataStructRegulation.forcedIncludeGetMethodNames.contains(m_signature)) {
                            dataStructRegulation.excludedMethodNames.put(m_signature,
                                "Inappropriate GET return type or parameters");
                            continue;
                        }
                        WSGDataGetterRegulation dg_reg
                                = new WSGDataGetterRegulation(m_name, bool_prefix, f_name, r_type);
                        dataStructRegulation.bbGetter2wsFieldMap.put(m_signature, dg_reg);
                    }
                    if (m_name.startsWith("set")
                            || dataStructRegulation.forcedIncludeSetMethodNames.contains(m_signature)) {
                        String r_type = method.getReturnType().getName();
                        if ((r_type.compareToIgnoreCase("void") != 0
                            || method.getParameterTypes().length != 1)
                            && !dataStructRegulation.forcedIncludeSetMethodNames.contains(m_signature)) {
                            dataStructRegulation.excludedMethodNames.put(m_signature,
                                "Inappropriate SET return type or parameters");
                            continue;
                        }
                        String p_type = method.getParameterTypes()[0].getName();
                        WSGDataSetterRegulation ds_reg =
                                new WSGDataSetterRegulation(m_name, f_name, p_type);
                        dataStructRegulation.bbSetter2wsFieldMap.put(m_signature, ds_reg);
                    }
                    //System.out.println(method.getLongName());
                    //System.out.println(method.getName());
                    //System.out.println(method.getReturnType().getName());
                }
                cc = cc.getSuperclass();
                String cc_name = cc.getName();
                //Class<?> c = cc.toClass();
                //cc_name = c.getName();
                String tmo = cc_name;
            }
/*
            for (WSGDataRegulationHashMap data_reghm:
                    new WSGDataRegulationHashMap[]
                    {dataStructRegulation.bbGetter2wsFieldMap, dataStructRegulation.bbSetter2wsFieldMap}) {
                WSGDataRegulationHashMap data_reghm_opposite;
                switch (data_reghm) {
                    case dataStructRegulation.bbGetter2wsFieldMap:
                        data_reghm_opposite = dataStructRegulation.bbSetter2wsFieldMap;
                }
            }
*/
        for (Map.Entry<String, WSGDataGetterRegulation> dr_entry:
                dataStructRegulation.bbGetter2wsFieldMap.entrySet()) {
            String m_signature = dr_entry.getKey();
            WSGDataGetterRegulation dr = dr_entry.getValue();
            if (dataStructRegulation.dataField2ActionsLinkMap.containsKey(dr.getFieldNameLCase())) {
                WSGDataActionsLink dal = dataStructRegulation.dataField2ActionsLinkMap.get(dr.getFieldNameLCase());
                if (dal.dataGetterSignature != null) {
                    dataStructRegulation.excludedMethodNames.put(m_signature,
                        "Another GET Method for the same field is already detected");
                    dataStructRegulation.bbGetter2wsFieldMap.remove(m_signature);
                } else dal.dataGetterSignature = m_signature;
            } else {
                WSGDataActionsLink dal = new WSGDataActionsLink();
                dal.dataGetterSignature = m_signature;
                dataStructRegulation.dataField2ActionsLinkMap.put(dr.getFieldNameLCase(), dal);
            }
        }
        for (Map.Entry<String, WSGDataSetterRegulation> dr_entry:
                dataStructRegulation.bbSetter2wsFieldMap.entrySet()) {
            String m_signature = dr_entry.getKey();
            WSGDataSetterRegulation dr = dr_entry.getValue();
            if (dataStructRegulation.dataField2ActionsLinkMap.containsKey(dr.getFieldNameLCase())) {
                WSGDataActionsLink dal = dataStructRegulation.dataField2ActionsLinkMap.get(dr.getFieldNameLCase());
                if (dal.dataSetterSignature != null) {
                    dataStructRegulation.excludedMethodNames.put(m_signature,
                        "Another SET Method for the same field is already detected");
                    dataStructRegulation.bbSetter2wsFieldMap.remove(m_signature);
                } else dal.dataSetterSignature = m_signature;
            } else {
                WSGDataActionsLink dal = new WSGDataActionsLink();
                dal.dataSetterSignature = m_signature;
                dataStructRegulation.dataField2ActionsLinkMap.put(dr.getFieldNameLCase(), dal);
            }
        }
        Iterator<Map.Entry<String, WSGDataActionsLink>> df2al_iter
                = dataStructRegulation.dataField2ActionsLinkMap.entrySet().iterator();
        while (df2al_iter.hasNext()) {
                Map.Entry<String, WSGDataActionsLink> dal_entry = df2al_iter.next();
                WSGDataActionsLink dal = dal_entry.getValue();
                String action_name = "GET";
                if (dal.dataGetterSignature == null) {
                    if (dal.dataSetterSignature == null) {
                        throw new Exception("ASSERT: dataSetterSignature should be set when dataGetterSignature == null");
                    }
                    String m_signature = dal.dataSetterSignature;
                    //if (!dataStructRegulation.forcedIncludeSetMethodNames.contains(m_signature)) {
                        dataStructRegulation.excludedMethodNames.put(m_signature,
                            "Missing corresponding " + action_name + " method");
                        dataStructRegulation.bbSetter2wsFieldMap.remove(m_signature);
                        df2al_iter.remove();
                        continue;
                    //}
                }
                action_name = "SET";
                if (dal.dataSetterSignature == null) {
                    if (dal.dataGetterSignature == null) {
                        throw new Exception("ASSERT: dataGetterSignature should be set when dataSetterSignature == null");
                    }
                    String m_signature = dal.dataGetterSignature;
                    //if (!dataStructRegulation.forcedIncludeGetMethodNames.contains(m_signature)) {
                        dataStructRegulation.excludedMethodNames.put(m_signature,
                            "Missing corresponding " + action_name + " method");
                        dataStructRegulation.bbGetter2wsFieldMap.remove(m_signature);
                        df2al_iter.remove();
                        continue;
                    //}
                }
                WSGDataGetterRegulation dgr
                        = dataStructRegulation.bbGetter2wsFieldMap.get(dal.dataGetterSignature);
                WSGDataSetterRegulation dsr
                        = dataStructRegulation.bbSetter2wsFieldMap.get(dal.dataSetterSignature);
                if (!StringUtils.equals(dgr.returnType, dsr.paramType)) {
                    //if (!dataStructRegulation.forcedIncludeGetMethodNames.contains(dal.dataGetterSignature)) {
                        dataStructRegulation.excludedMethodNames.put(dal.dataGetterSignature,
                            "Incompatible return/parameter types of GET/SET methods");
                        dataStructRegulation.bbGetter2wsFieldMap.remove(dal.dataGetterSignature);
                        dal.dataGetterSignature = null;
                    //}
                    //if (!dataStructRegulation.forcedIncludeSetMethodNames.contains(dal.dataSetterSignature)) {
                        dataStructRegulation.excludedMethodNames.put(dal.dataSetterSignature,
                            "Incompatible return/parameter types of GET/SET methods");
                        dataStructRegulation.bbSetter2wsFieldMap.remove(dal.dataSetterSignature);
                        dal.dataSetterSignature = null;
                    //}
                    if (dal.dataGetterSignature == null
                            && dal.dataSetterSignature == null) df2al_iter.remove();
                }
            }
            for (Map.Entry<String, WSGDataActionsLink> dal: dataStructRegulation.dataField2ActionsLinkMap.entrySet()) {
                WSGDataRegulation dr = new WSGDataRegulation(dal.getValue());
                dataStructRegulation.dataRegulationList.add(dr);
            }
            Collections.sort(dataStructRegulation.dataRegulationList, new Comparator<WSGDataRegulation>() {
                    public int compare(WSGDataRegulation a, WSGDataRegulation b){
                    return a.getWsFieldName().compareTo(b.getWsFieldName());
                }
            });
    }
    public static void main(String[] args) {
        try {
            initDataRegulationList();
            generateRegulationsInitialization();
            generateDataDetails();
            generateDataAccessPack();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //System.out.println(e.getStackTrace());
            System.out.println(constructExceptionMessage(e));
            e.printStackTrace(System.out);
        }

    }
    //static void linkDRHMFields(WSGDataRegulationHashMap main_drhm,
    //static void linkDRHMFields(LinkedHashMap<String, WSGDataFieldRegulation> main_drhm,
            //WSGDataRegulationHashMap opposite_drhm) {


    static void generateRegulationsInitialization() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        JDefinedClass definedClass = codeModel._class(
            dataStructRegulation.packageName + "."
//            + dataStructRegulation.className
            + "WsGenDataRegulation"
            );
//definedClass.field(JMod.PRIVATE, int.class, "intVar");
        /*
        JArray exc_arr = JExpr.newArray(codeModel.ref(String.class));
        for (String method: dataStructRegulation.excludedMethodNames){
            exc_arr.add(JExpr.lit(method));
        }
        JFieldVar field = definedClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL,
                String[].class, "excMethodNamesArr", exc_arr);
         */
//public ArrayList<String> excludedMethodNames = new ArrayList<String>();
        JFieldVar field = definedClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL,
                LinkedHashMap.class, "excludedMethodNames");
        JMethod method = definedClass.method(JMod.PUBLIC|JMod.STATIC, codeModel.VOID, "fillExcludedMethodNames");

        JVar comment_var = method.body().decl(codeModel.ref(String.class), "comment");
        for (Map.Entry<String, String> exc_m_entry:
                dataStructRegulation.excludedMethodNames.entrySet()) {
            String m_signature = exc_m_entry.getKey();
            String m_comment = exc_m_entry.getValue();
            method.body().assign(comment_var, JExpr.lit(m_comment));
            JInvocation invoc_put = method.body().invoke(field, "put");
            invoc_put.arg(m_signature);
            invoc_put.arg(comment_var);
        }
        
        //method.body().assign(JExpr.ref(JExpr._this(), field), JExpr.lit("dsfads"));
        //method.body().assign(JExpr.refthis(field.name()), JExpr.lit("dsfads"));

        JFieldVar getter_map = definedClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL,
                LinkedHashMap.class, "bbGetter2wsFieldMap");
        JFieldVar setter_map = definedClass.field(JMod.PUBLIC|JMod.STATIC|JMod.FINAL,
                LinkedHashMap.class, "bbSetter2wsFieldMap");
        method = definedClass.method(JMod.PUBLIC|JMod.STATIC, codeModel.VOID, "fillBBAction2wsFieldMaps");
        JVar key = method.body().decl(codeModel.ref(String.class), "key");
        for (Map.Entry<String, WSGDataActionsLink> dal_entry:
                dataStructRegulation.dataField2ActionsLinkMap.entrySet()) {
            String field_name = dal_entry.getKey();
            WSGDataActionsLink dal = dal_entry.getValue();
            if (dal.dataGetterSignature != null) {
                WSGDataGetterRegulation dgr =
                        dataStructRegulation.bbGetter2wsFieldMap.get(dal.dataGetterSignature);
                method.body().assign(key, JExpr.lit(dal.dataGetterSignature));
                JInvocation invoc_new_val = JExpr._new(codeModel.ref(WSGDataGetterRegulation.class));
                invoc_new_val.arg(dgr.bbGetMethodName);
                invoc_new_val.arg(dgr.boolPrefix);
                invoc_new_val.arg(dgr.getFieldNameLCase());
                invoc_new_val.arg(dgr.returnType);
                JInvocation invoc_put = method.body().invoke(getter_map, "put");
                invoc_put.arg(key);
                invoc_put.arg(invoc_new_val);
            }
            if (dal.dataSetterSignature != null) {
                WSGDataSetterRegulation dsr =
                        dataStructRegulation.bbSetter2wsFieldMap.get(dal.dataSetterSignature);
                method.body().assign(key, JExpr.lit(dal.dataSetterSignature));
                JInvocation invoc_new_val = JExpr._new(codeModel.ref(WSGDataSetterRegulation.class));
                invoc_new_val.arg(dsr.bbSetMethodName);
                invoc_new_val.arg(dsr.getFieldNameLCase());
                invoc_new_val.arg(dsr.paramType);
                JInvocation invoc_put = method.body().invoke(setter_map, "put");
                invoc_put.arg(key);
                //invoc_put.arg(exc_m.getKey());
                invoc_put.arg(invoc_new_val);
            }
        }
        //JExpr.newArray(codeModel.ref(String.class)).add(ID).add(CODE).add(NAME);
    //will generate:
    //new String[]{ID, CODE, NAME}
        codeModel.build(new File("./generated"));
    }

    private static String constructExceptionMessage(Throwable e, String res, int recDepth) {
        if (recDepth > 15) res = res + " \r\nMORE THAN 15 CAUSES - Possible dead loop in exceptions' \"cause chain\"";
        else {
            res = res + e.toString();
            if (e.getCause() != null) res = constructExceptionMessage(e.getCause(), res + " \r\nCAUSED BY: ", recDepth + 1);
        }
        return res;
    }

    public static String constructExceptionMessage(Exception e) {
        return constructExceptionMessage(e, "", 0);
    }

    //LinkedHashMap<String, ?>
    public static void sortLinkedHashMapByStringKey (LinkedHashMap lhm) {
        List<Map.Entry<String, ?>> entry_list =
            new ArrayList<Map.Entry<String, ?>>(lhm.entrySet());
            Collections.sort(entry_list, new Comparator<Map.Entry<String, ?>>() {
                    public int compare(Map.Entry<String, ?> a, Map.Entry<String, ?> b){
                    return a.getKey().compareTo(b.getKey());
                }
            });
        lhm.clear();
        for (Map.Entry<String, ?> entry : entry_list) {
            lhm.put(entry.getKey(), entry.getValue());
        }
    }

    static void generateDataDetails() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        JDefinedClass definedClass = codeModel._class(
            dataStructRegulation.packageName + "."
                + dataStructRegulation.className + "Details");
        definedClass._extends(bbwscommon.BbWsDataDetails.class);

        for (Map.Entry<String, WSGDataActionsLink> dal_entry:
                dataStructRegulation.dataField2ActionsLinkMap.entrySet()) {
            String field_name = dal_entry.getKey();
            WSGDataActionsLink dal = dal_entry.getValue();
            //String ft_name = null;
            WSGDataGetterRegulation dgr = null;
            WSGDataSetterRegulation dsr = null;
            if (dal.dataGetterSignature != null)
                dgr = dataStructRegulation.bbGetter2wsFieldMap.get(dal.dataGetterSignature);
            if (dal.dataSetterSignature != null)
                dsr = dataStructRegulation.bbSetter2wsFieldMap.get(dal.dataSetterSignature);
            String bool_prefix = "";
            if (dgr != null) {
                //ft_name = dgr.returnType;
                bool_prefix = dgr.getBoolPrefixUCase();
                if (!StringUtils.equals(bool_prefix, ""))
                    field_name = dgr.getBoolPrefixLCase() + firstLetterUCase(dgr.getFieldNameLCase());
            } //else ft_name = dsr.paramType;
            JClass f_class = codeModel.ref(String.class);
            JFieldVar field = definedClass.field(JMod.PRIVATE, f_class, field_name);
            JFieldRef f_ref = JExpr.ref(JExpr._this(), field);
            if (dgr != null) {
                JMethod method = definedClass.method(JMod.PUBLIC, f_class,
                        "get" + bool_prefix + firstLetterUCase(dgr.getFieldNameLCase()));
                 //method.type(f_type);
                 method.body()._return(f_ref);
            }
            if (dsr != null) {
                JMethod method = definedClass.method(JMod.PUBLIC, f_class,
                        "set" + bool_prefix + firstLetterUCase(dsr.getFieldNameLCase()));
                JVar param = method.param(f_class, field_name);
                method.body().assign(f_ref, param);
            }

        }
        codeModel.build(new File("./generated"));
    }

    static void generateDataAccessPack () throws Exception {
        DataAccessPackGenerator dapg = new DataAccessPackGenerator();
        dapg.generateDataAccessPackClass();

    }
    public static class DataAccessPackGenerator {
        static JCodeModel codeModel = new JCodeModel();
        JDefinedClass dataAccessPackDClass  = null;
        JMethod setBbFieldsMethod = null;
        JMethod setWsFieldsMethod = null;

        JMethod genSetFieldsMethod (String method_name) {
            JMethod method = dataAccessPackDClass.method(JMod.PROTECTED, 
                    codeModel.VOID, method_name);
            method.annotate(Override.class);
            method._throws(Exception.class);
            return method;
        }
        JMethod genFieldToucherGetMethod(JDefinedClass toucherClass, String m_name) {
            JMethod method = toucherClass.method(JMod.PUBLIC, String.class, m_name);
            method.annotate(Override.class);
            method._throws(Exception.class);
            return method;
        }
        JMethod genFieldToucherSetMethod(JDefinedClass toucherClass, String m_name) {
            JMethod method = toucherClass.method(JMod.PUBLIC, void.class, m_name);
            method.annotate(Override.class);
            method._throws(Exception.class);
            method.param(String.class, "newValue");
            return method;
        }
        JDefinedClass genBbFieldSetterClass(WSGDataRegulation dataRegulation) throws Exception {
            JDefinedClass d_class = codeModel.anonymousClass(BbWsDataAccessPack.BbFieldSetter.class);
            JMethod getBb_m = genFieldToucherGetMethod(d_class, "getBbFieldValue");
            JMethod getWs_m = genFieldToucherGetMethod(d_class, "getWsFieldValue");
            JMethod setBb_m = genFieldToucherSetMethod(d_class, "setBbFieldImp");
            if (dataRegulation.bbGetMethodName != null)
                dataRegulation.toucherMethodBodiesGenerator.genGetBbFieldValue(dataRegulation, getBb_m);
            else dataRegulation.toucherMethodBodiesGenerator.genGetBbFieldValueWarn(dataRegulation, getBb_m);
            dataRegulation.toucherMethodBodiesGenerator.genGetWsFieldValue(dataRegulation, getWs_m);
            if (dataRegulation.bbSetMethodName != null)
                dataRegulation.toucherMethodBodiesGenerator.genSetBbFieldImp(dataRegulation, setBb_m);
            else dataRegulation.toucherMethodBodiesGenerator.genSetBbFieldValueWarn(dataRegulation, getBb_m);
            return d_class;
        }
        JDefinedClass genWsFieldSetterClass(WSGDataRegulation dataRegulation) throws Exception {
            JDefinedClass d_class = codeModel.anonymousClass(BbWsDataAccessPack.BbFieldSetter.class);
            JMethod getBb_m = genFieldToucherGetMethod(d_class, "getBbFieldValue");
            JMethod getWs_m = genFieldToucherGetMethod(d_class, "getWsFieldValue");
            JMethod setBb_m = genFieldToucherSetMethod(d_class, "setWsFieldImp");
            if (dataRegulation.bbGetMethodName != null)
                dataRegulation.toucherMethodBodiesGenerator.genGetBbFieldValue(dataRegulation, getBb_m);
            else dataRegulation.toucherMethodBodiesGenerator.genGetBbFieldValueWarn(dataRegulation, getBb_m);
            dataRegulation.toucherMethodBodiesGenerator.genGetWsFieldValue(dataRegulation, getWs_m);
            dataRegulation.toucherMethodBodiesGenerator.genSetWsFieldImp(dataRegulation, setBb_m);
            return d_class;
        }


        void generateDataAccessPackClass() throws Exception {
            dataAccessPackDClass  = codeModel._class(
                dataStructRegulation.packageName + "."
                    + dataStructRegulation.className + "AccessPack");
            setBbFieldsMethod = genSetFieldsMethod("setBbFields");
            setWsFieldsMethod = genSetFieldsMethod("setWsFields");
            for (WSGDataRegulation dr: dataStructRegulation.dataRegulationList) {
                JDefinedClass bbSetter_dclass = genBbFieldSetterClass(dr);
                JDefinedClass wsSetter_dclass = genWsFieldSetterClass(dr);
                JInvocation invoc_new = JExpr._new(bbSetter_dclass);
                JInvocation anc_inv = setBbFieldsMethod.body().invoke(invoc_new, "setBbField");
                anc_inv.arg(dr.wsFieldName);
                invoc_new = JExpr._new(wsSetter_dclass);
                anc_inv = setWsFieldsMethod.body().invoke(invoc_new, "setWsField");
                anc_inv.arg(dr.wsFieldName);
            }
            codeModel.build(new File("./generated"));
        }

        static abstract class FieldToucherMethodBodiesGenerator {
            static Map<String, String> bbType2AcronimMap = new LinkedHashMap<String, String>();
            static Map<String, String> wsField2BbType = new LinkedHashMap<String, String>();
            static {
                //bbType2AcronimMap.put("blackboard.persist.Id", "BbPersistId");
                //bbType2AcronimMap.put("java.util.Calendar", "Calendar");
                //bbType2AcronimMap.put("blackboard.base.FormattedText", "FormattedText");


                wsField2BbType.put("assessmentId", "blackboard.data.qti.asi.AsiObject");
                wsField2BbType.put("categoryId", "blackboard.platform.gradebook2.GradebookType");
                wsField2BbType.put("courseContentId", "blackboard.data.content.Content");
                wsField2BbType.put("courseId", "blackboard.data.course.Course");
                wsField2BbType.put("gradingPeriodId", "blackboard.platform.gradebook2.GradingPeriod");
                wsField2BbType.put("gradingSchemaId", "blackboard.platform.gradebook2.GradingSchema");
                wsField2BbType.put("secondaryGradingSchemaId", "blackboard.platform.gradebook2.GradingSchema");
            }
            
            
            public void genGetWsFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("getArgs().getInputRecord()." + dr.getWsGetMethodName() + "()");
                method.body()._return(ret_expr);
            }
            public void genSetWsFieldImp(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("getArgs().getResultRecord()." + dr.getWsGetMethodName() + "(newValue)");
                method.body()._return(ret_expr);
            }
            public void genGetBbFieldValueWarn(WSGDataRegulation dr, JMethod method) {
                JInvocation invoc = JExpr.invoke("addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN, "
                        + "\"" + dr.wsFieldName
                        + "\", null, null, null, \"getBbField() Bb GET method is not available for this field.\")");
                JExpression ret_expr = JExpr._null();
                method.body()._return(ret_expr);
            }
            public void genSetBbFieldValueWarn(WSGDataRegulation dr, JMethod method) {
                JInvocation invoc = JExpr.invoke("addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN, "
                        + "\"" + dr.wsFieldName
                        + "\", null, null, null, \"setBbField() Bb SET method is not available for this field.\")");
            }

            public abstract void genGetBbFieldValue(WSGDataRegulation dr, JMethod method);
            public abstract void genSetBbFieldImp(WSGDataRegulation dr, JMethod method);
        }

        static public class FieldToucherMethodBodiesGenerator_java_lang_String extends FieldToucherMethodBodiesGenerator {
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "()");
                method.body()._return(ret_expr);
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                invoc.arg(JExpr.direct("newValue"));
                method.body().add(invoc);
            }
        }

        static abstract class FieldToucherMethodBodiesGenerator_primitive extends FieldToucherMethodBodiesGenerator {
            protected abstract String convertToString(String expression);
            protected abstract String parse(String expression);
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct(convertToString("bbObject." + dr.bbGetMethodName + "()"));
                method.body()._return(ret_expr);
                //return Integer.toString(bbObject.getNumDaysOfUse());
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                String bbType = wsField2BbType.get(dr.getWsFieldName());
                JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                invoc.arg(JExpr.direct(parse("newValue")));
                method.body().add(invoc);
                //bbObject.setNumDaysOfUse(Integer.parseInt(newValue));
            }

        }
        static public class FieldToucherMethodBodiesGenerator_int extends FieldToucherMethodBodiesGenerator_primitive {
            protected String convertToString(String expression) {
                return "Integer.toString(" + expression + ")";
            }
            protected String parse(String expression) {
                return "Integer.parseInt(" + expression + ")";
            }
        }

        static public class FieldToucherMethodBodiesGenerator_float extends FieldToucherMethodBodiesGenerator_primitive {
            protected String convertToString(String expression) {
                return "Float.toString(" + expression + ")";
            }
            protected String parse(String expression) {
                return "Float.parseFloat(" + expression + ")";
            }
        }

        static public class FieldToucherMethodBodiesGenerator_double extends FieldToucherMethodBodiesGenerator_primitive {
            protected String convertToString(String expression) {
                return "Double.toString(" + expression + ")";
            }
            protected String parse(String expression) {
                return "Double.parseDouble(" + expression + ")";
            }
        }

        static public class FieldToucherMethodBodiesGenerator_boolean extends FieldToucherMethodBodiesGenerator_primitive {
            protected String convertToString(String expression) {
                return "Boolean.toString(" + expression + ")";
            }
            protected String parse(String expression) {
                return "Boolean.parseBoolean(" + expression + ")";
            }
        }


        static public class FieldToucherMethodBodiesGenerator_java_util_Calendar extends FieldToucherMethodBodiesGenerator {
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("extractDate(bbObject." + dr.bbGetMethodName + "())");
                method.body()._return(ret_expr);
                //return extractDate(bbObject.getCreatedDate());
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                String bbType = wsField2BbType.get(dr.getWsFieldName());
                JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                invoc.arg(JExpr.direct("parseDate(newValue)"));
                method.body().add(invoc);
                //bbObject.setCreatedDate(parseDate(newValue));
            }
        }



        static public class FieldToucherMethodBodiesGenerator_blackboard_persist_Id extends FieldToucherMethodBodiesGenerator {
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().toExternalString()");
                method.body()._return(ret_expr);
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                String bbType = wsField2BbType.get(dr.getWsFieldName());
                JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                invoc.arg(JExpr.direct("checkAndgenerateId(" + bbType + ".DATA_TYPE, newValue)"));
                method.body().add(invoc);
            }
        }

        static public class FieldToucherMethodBodiesGenerator_blackboard_base_FormattedText extends FieldToucherMethodBodiesGenerator {
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().toString()");
                method.body()._return(ret_expr);
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                String bbType = wsField2BbType.get(dr.getWsFieldName());
                JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                invoc.arg(JExpr.direct("parseFormattedText(newValue)"));
                method.body().add(invoc);
            }
        }


        static public class FieldToucherMethodBodiesGenerator_enum extends FieldToucherMethodBodiesGenerator {
            public void genGetBbFieldValue(WSGDataRegulation dr, JMethod method) {
                JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().name()");
                method.body()._return(ret_expr);
            }
            public void genSetBbFieldImp(WSGDataRegulation dr, JMethod method) {
                JTryBlock try_block = method.body()._try();
                JCatchBlock catch_block = try_block._catch(codeModel.ref(IllegalArgumentException.class));
                JVar exc_var = catch_block.param("iae");
                JInvocation new_exc_invoc = JExpr._new(codeModel.ref(BbWsException.class));
                JExpression new_exc_arg1_expr = JExpr.lit(dataStructRegulation.className + "Details "
                        + dr.wsFieldName + " may contain one of the following values: ");
                new_exc_arg1_expr = new_exc_arg1_expr.plus(JExpr.direct("Arrays.toString("
                                + dr.bbType + ".values())"));
                new_exc_invoc.arg(new_exc_arg1_expr);
                new_exc_invoc.arg(exc_var);
                catch_block.body()._throw(new_exc_invoc);
                //try_block.body().add(JExpr.invoke("bbObject." + dr.bbSetMethodName + "("
                JInvocation set_method_invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
                set_method_invoc.arg(JExpr.direct("Enum.valueOf(" + dr.bbType + ".class, newValue))"));
                try_block.body().add(set_method_invoc);
                /*
                try {
                    cfg.setCopyType(Enum.valueOf(CloneConfig.CopyType.class, clone_params.getCopyType()));
                } catch (IllegalArgumentException ie) {
                    throw new BbWsException("CourseCloneParams copyType may contain one of the following values: "
                                            + Arrays.toString(CloneConfig.CopyType.values()), ie);
                }
                */
            }
        }



        void generateDataAccessPackClass_() throws Exception {
            dataAccessPackDClass  = codeModel._class(
                dataStructRegulation.packageName + "."
                    + dataStructRegulation.className + "AccessPack");
            setBbFieldsMethod = genSetFieldsMethod("setBbFields");
            setWsFieldsMethod = genSetFieldsMethod("setWsFields");
            for (WSGDataRegulation dr: dataStructRegulation.dataRegulationList) {
                JDefinedClass setter_dclass = codeModel.anonymousClass(BbWsDataAccessPack.BbFieldSetter.class);
                JInvocation invoc_new = JExpr._new(setter_dclass);
                JMethod getBb_m = setter_dclass.method(JMod.PUBLIC, String.class, "getBbFieldValue");
                getBb_m.annotate(Override.class);
                getBb_m._throws(Exception.class);
                JExpression ret_expr = JExpr.direct("bbObject.getId().toExternalString()");
                getBb_m.body()._return(ret_expr);
                //JType[] ancestor_params = new JType[] {codeModel._ref(String.class)};
                //String ancestor_setter_class_name = BbWsDataAccessPack.BbFieldSetter.class.getName();
                //JDefinedClass ancestor_setter_dclass = codeModel._getClass(ancestor_setter_class_name);
                //JMethod ancestor_m = ancestor_setter_dclass.getMethod("setBbField", new JType[] {codeModel._ref(String.class)});
//                JInvocation anc_inv = bbset_method.body().invoke(invoc_new, "setBbField");
//                anc_inv.arg(dal_entry.getKey());
                //bbset_method.body().invoke(invoc_new, ancestor_m);

                //setter_dclass.method(mods, definedClass, null)
            }

    /*
                if (dgr != null) {
                    JMethod method = definedClass.method(JMod.PUBLIC, f_type,
                            "get" + bool_prefix + dgr.getFieldNameUCase());
                     //method.type(f_type);
                     method.body()._return(field);
                }
                if (dsr != null) {
                    JMethod method = definedClass.method(JMod.PUBLIC, f_type,
                            "set" + bool_prefix + dsr.getFieldNameUCase());
                    JVar param = method.param(f_type, field_name);
                    method.body().assign(field, param);
                }
    */

            for (Map.Entry<String, WSGDataActionsLink> dal_entry:
                    dataStructRegulation.dataField2ActionsLinkMap.entrySet()) {
                    //method.body().assign(key, JExpr.lit(dal.dataGetterSignature));
                    JDefinedClass setter_dclass = codeModel.anonymousClass(BbWsDataAccessPack.BbFieldSetter.class);
                    JInvocation invoc_new = JExpr._new(setter_dclass);
                    JMethod getBb_m = setter_dclass.method(JMod.PUBLIC, String.class, "getBbFieldValue");
                    getBb_m.annotate(Override.class);
                    getBb_m._throws(Exception.class);
                    JExpression ret_expr = JExpr.direct("bbObject.getId().toExternalString()");
                    getBb_m.body()._return(ret_expr);
                    //JType[] ancestor_params = new JType[] {codeModel._ref(String.class)};
                    //String ancestor_setter_class_name = BbWsDataAccessPack.BbFieldSetter.class.getName();
                    //JDefinedClass ancestor_setter_dclass = codeModel._getClass(ancestor_setter_class_name);
                    //JMethod ancestor_m = ancestor_setter_dclass.getMethod("setBbField", new JType[] {codeModel._ref(String.class)});
//                    JInvocation anc_inv = bbset_method.body().invoke(invoc_new, "setBbField");
//                    anc_inv.arg(dal_entry.getKey());
                    //bbset_method.body().invoke(invoc_new, ancestor_m);

                    //setter_dclass.method(mods, definedClass, null)
            }
    /*
                WSGDataActionsLink dal = dal_entry.getValue();
                String ft_name = null;
                WSGDataGetterRegulation dgr = null;
                WSGDataSetterRegulation dsr = null;
                if (dal.dataGetterSignature != null)
                    dgr = dataStructRegulation.bbGetter2wsFieldMap.get(dal.dataGetterSignature);
                if (dal.dataSetterSignature != null)
                    dsr = dataStructRegulation.bbSetter2wsFieldMap.get(dal.dataSetterSignature);
                String bool_prefix = "";
                if (dgr != null) {
                    ft_name = dgr.returnType;
                    bool_prefix = dgr.getBoolPrefixUCase();
                    field_name = dgr.getBoolPrefixLCase() + dgr.getFieldNameUCase();
                }
                else ft_name = dsr.paramType;
                JClass f_class = codeModel.ref(ft_name);
                JType f_type = codeModel.parseType(ft_name);
                JFieldVar field = definedClass.field(JMod.PRIVATE, f_class, field_name);
                //same output
                //field = definedClass.field(JMod.PRIVATE, f_type, field_name+"_1");
                if (dgr != null) {
                    JMethod method = definedClass.method(JMod.PUBLIC, f_type,
                            "get" + bool_prefix + dgr.getFieldNameUCase());
                     //method.type(f_type);
                     method.body()._return(field);
                }
                if (dsr != null) {
                    JMethod method = definedClass.method(JMod.PUBLIC, f_type,
                            "set" + bool_prefix + dsr.getFieldNameUCase());
                    JVar param = method.param(f_type, field_name);
                    method.body().assign(field, param);
                }

            }
     */
            codeModel.build(new File("./generated"));
         }

    }

}
