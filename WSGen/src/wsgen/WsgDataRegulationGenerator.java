/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;
//* import javassist.ClassPool;
//*import javassist.CtClass;
//*import javassist.CtMethod;
//*import javassist.CtField;
//*import javassist.bytecode.AccessFlag;


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
//import java.util.A;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
/**
 *
 * @author Administrator
 */
public class WsgDataRegulationGenerator {

    /**
     * @param args the command line arguments
     */
/*
 * http://stackoverflow.com/questions/12588121/initializing-data-structures-to-constant-values-with-mixed-data-types
static final xyz[] example1 = new xyz[] { new xyz() {{ a = "value a"; b = 42; }},
                                          new xyz() {{ a = "value b"; b = 43; }}
                                        };

 */


    /*
    static {
        genRegulation.packageName = "bbgbws";
        genRegulation.dataClassName = "GradableItem";
    }*/
    WsgRegulation genRegulation;
    WsgDataAccessPackGenerator dataAccessPackGenerator;
    public WsgDataRegulationGenerator(WsgRegulation genRegulation) {
        this.genRegulation = genRegulation;
        dataAccessPackGenerator = new WsgDataAccessPackGenerator(genRegulation);
    }

    public void generate() throws Exception {
            initDataRegulationList();
            generateRegulationsInitialization();
            generateDataDetails();
            generateDataAccessPack();
    }

    public void initDataRegulationList() throws Exception {
        //* ClassPool pool = ClassPool.getDefault();
            //* CtClass cc = pool.get("blackboard.platform.gradebook2.GradableItem");
            Class cc = blackboard.platform.gradebook2.GradableItem.class;
            //* CtClass cc_obj = pool.get("java.lang.Object");
            Class cc_obj = java.lang.Object.class;
            while (!cc.equals(cc_obj)) {
                //* CtMethod[] meth_arr = cc.getDeclaredMethods();
                Method[] meth_arr = cc.getDeclaredMethods();
                //* for (CtMethod method: meth_arr){
                for (Method method: meth_arr){
                    //* if (!AccessFlag.isPublic(method.getMethodInfo().getAccessFlags())) continue;
                    if (!Modifier.isPublic(method.getModifiers())) continue;
                    String m_name = method.getName();

                    String m_signature = WsgUtil.getMethodSignature(method);//method.getSignature();
                    //* String m_signature_noret = method.getSignature();

                    if (genRegulation.bbSetter2WSFieldMap.containsKey(m_signature)
                            || genRegulation.bbGetter2WSFieldMap.containsKey(m_signature)
                            || genRegulation.excludedBbToucherMethodNamesMap.containsKey(m_signature)
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
                            && (genRegulation.includedBbGetterMethodNames.contains(m_signature)
                                || genRegulation.includedBbSetterMethodNames.contains(m_signature))
                    ) f_name = m_name;
                    if (f_name == null) {
                        genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                                "Unrecognized method naming convention");
                        continue;
                    }
                    if (m_name.startsWith("get")
                            || m_name.startsWith("is")
                            || m_name.startsWith("has")
                            || genRegulation.includedBbGetterMethodNames.contains(m_signature)) {
                        String r_type = method.getReturnType().getName();
                        if ((r_type.compareToIgnoreCase("void") == 0
                            || method.getParameterTypes().length != 0)
                            && !genRegulation.includedBbGetterMethodNames.contains(m_signature)) {
                            genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                                "Inappropriate GET return type or parameters");
                            continue;
                        }
                        WsgBbGetterRegulationStruct dg_reg
                                = new WsgBbGetterRegulationStruct(m_name, bool_prefix, f_name, r_type);
                        genRegulation.bbGetter2WSFieldMap.put(m_signature, dg_reg);
                        addDataType2ValuesMap(method.getReturnType());
                    }
                    if (m_name.startsWith("set")
                            || genRegulation.includedBbSetterMethodNames.contains(m_signature)) {
                        String r_type = method.getReturnType().getName();
                        if ((r_type.compareToIgnoreCase("void") != 0
                            || method.getParameterTypes().length != 1)
                            && !genRegulation.includedBbSetterMethodNames.contains(m_signature)) {
                            genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                                "Inappropriate SET return type or parameters");
                            continue;
                        }
                        String p_type = method.getParameterTypes()[0].getName();
                        WsgBbSetterRegulationStruct ds_reg =
                                new WsgBbSetterRegulationStruct(m_name, f_name, p_type);
                        genRegulation.bbSetter2WSFieldMap.put(m_signature, ds_reg);
                        addDataType2ValuesMap(method.getParameterTypes()[0]);
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
            } //while (!cc.equals(cc_obj)) {
/*
            for (WSGDataRegulationHashMap data_reghm:
                    new WSGDataRegulationHashMap[]
                    {genRegulation.bbGetter2WSFieldMap, genRegulation.bbSetter2WSFieldMap}) {
                WSGDataRegulationHashMap data_reghm_opposite;
                switch (data_reghm) {
                    case genRegulation.bbGetter2WSFieldMap:
                        data_reghm_opposite = genRegulation.bbSetter2WSFieldMap;
                }
            }
*/
        for (Map.Entry<String, WsgBbGetterRegulationStruct> dr_entry:
                genRegulation.bbGetter2WSFieldMap.entrySet()) {
            String m_signature = dr_entry.getKey();
            WsgBbGetterRegulationStruct dr = dr_entry.getValue();
            if (genRegulation.wsField2BbTouchersLinkMap.containsKey(dr.getFieldNameLCase())) {
                WsgBbTouchersLink dal = genRegulation.wsField2BbTouchersLinkMap.get(dr.getFieldNameLCase());
                if (dal.dataGetterSignature != null) {
                    genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                        "Another GET Method for the same field is already detected");
                    genRegulation.bbGetter2WSFieldMap.remove(m_signature);
                } else dal.dataGetterSignature = m_signature;
            } else {
                WsgBbTouchersLink dal = new WsgBbTouchersLink();
                dal.dataGetterSignature = m_signature;
                genRegulation.wsField2BbTouchersLinkMap.put(dr.getFieldNameLCase(), dal);
            }
        }
        for (Map.Entry<String, WsgBbSetterRegulationStruct> dr_entry:
                genRegulation.bbSetter2WSFieldMap.entrySet()) {
            String m_signature = dr_entry.getKey();
            WsgBbSetterRegulationStruct dr = dr_entry.getValue();
            if (genRegulation.wsField2BbTouchersLinkMap.containsKey(dr.getFieldNameLCase())) {
                WsgBbTouchersLink dal = genRegulation.wsField2BbTouchersLinkMap.get(dr.getFieldNameLCase());
                if (dal.dataSetterSignature != null) {
                    genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                        "Another SET Method for the same field is already detected");
                    genRegulation.bbSetter2WSFieldMap.remove(m_signature);
                } else dal.dataSetterSignature = m_signature;
            } else {
                WsgBbTouchersLink dal = new WsgBbTouchersLink();
                dal.dataSetterSignature = m_signature;
                genRegulation.wsField2BbTouchersLinkMap.put(dr.getFieldNameLCase(), dal);
            }
        }
        Iterator<Map.Entry<String, WsgBbTouchersLink>> df2al_iter
                = genRegulation.wsField2BbTouchersLinkMap.entrySet().iterator();
        while (df2al_iter.hasNext()) {
                Map.Entry<String, WsgBbTouchersLink> dal_entry = df2al_iter.next();
                WsgBbTouchersLink dal = dal_entry.getValue();
                String action_name = "GET";
                if (dal.dataGetterSignature == null) {
                    if (dal.dataSetterSignature == null) {
                        throw new Exception("ASSERT: dataSetterSignature should be set when dataGetterSignature == null");
                    }
                    String m_signature = dal.dataSetterSignature;
                    if (!genRegulation.includedBbSetterMethodNames.contains(m_signature)) {
                        genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                            "Missing corresponding " + action_name + " method");
                        genRegulation.bbSetter2WSFieldMap.remove(m_signature);
                        df2al_iter.remove();
                        continue;
                    }
                }
                action_name = "SET";
                if (dal.dataSetterSignature == null) {
                    if (dal.dataGetterSignature == null) {
                        throw new Exception("ASSERT: dataGetterSignature should be set when dataSetterSignature == null");
                    }
                    String m_signature = dal.dataGetterSignature;
                    if (!genRegulation.includedBbGetterMethodNames.contains(m_signature)) {
                        genRegulation.excludedBbToucherMethodNamesMap.put(m_signature,
                            "Missing corresponding " + action_name + " method");
                        genRegulation.bbGetter2WSFieldMap.remove(m_signature);
                        df2al_iter.remove();
                        continue;
                    }
                }
                WsgBbGetterRegulationStruct dgr
                        = genRegulation.bbGetter2WSFieldMap.get(dal.dataGetterSignature);
                WsgBbSetterRegulationStruct dsr
                        = genRegulation.bbSetter2WSFieldMap.get(dal.dataSetterSignature);
                if (!StringUtils.equals(dgr.returnType, dsr.paramType)) {
                    if (!genRegulation.includedBbGetterMethodNames.contains(dal.dataGetterSignature)) {
                        genRegulation.excludedBbToucherMethodNamesMap.put(dal.dataGetterSignature,
                            "Incompatible return/parameter types of GET/SET methods");
                        genRegulation.bbGetter2WSFieldMap.remove(dal.dataGetterSignature);
                        dal.dataGetterSignature = null;
                    }
                    if (!genRegulation.includedBbSetterMethodNames.contains(dal.dataSetterSignature)) {
                        genRegulation.excludedBbToucherMethodNamesMap.put(dal.dataSetterSignature,
                            "Incompatible return/parameter types of GET/SET methods");
                        genRegulation.bbSetter2WSFieldMap.remove(dal.dataSetterSignature);
                        dal.dataSetterSignature = null;
                    }
                    if (dal.dataGetterSignature == null
                            && dal.dataSetterSignature == null) df2al_iter.remove();
                }
            }
            for (Map.Entry<String, WsgBbTouchersLink> dal_entry: genRegulation.wsField2BbTouchersLinkMap.entrySet()) {
                WsgDataRegulationStruct dr = new WsgDataRegulationStruct(genRegulation, dataAccessPackGenerator, dal_entry.getValue());
                genRegulation.dataFieldRegulationList.add(dr);
            }
            Collections.sort(genRegulation.dataFieldRegulationList, new Comparator<WsgDataRegulationStruct>() {
                    public int compare(WsgDataRegulationStruct a, WsgDataRegulationStruct b){
                    return a.getWsFieldName().compareTo(b.getWsFieldName());
                }
            });

             WsgUtil.sortMapByComparableKey(genRegulation.excludedBbToucherMethodNamesMap);
    }


//*    void addDataType2ValuesMap(CtClass ctClass) throws Exception {
    void addDataType2ValuesMap(Class ctClass) throws Exception {
        if (genRegulation.dataType2ValuesMap.containsKey(ctClass.getName())) return;
        List<String> list = new ArrayList<String>();
        if (ctClass.isPrimitive()) {
            //* if (CtClass.booleanType.equals(ctClass)) {
            //* if (java.lang.Boolean.class.equals(ctClass)) {
            if (boolean.class.equals(ctClass)) {
                list.add("true");
                list.add("false");
            }
            genRegulation.dataType2ValuesMap.put(ctClass.getName(), list);
            return;
        }
        Class<?> clazz = WsgUtil.getClassForName(ctClass.getName());
        if (ctClass.isEnum()) {
            Object ec_arr[] = clazz.getEnumConstants();
            for (Object ec: ec_arr) {
                list.add(ec.toString());
            }
            genRegulation.dataType2ValuesMap.put(ctClass.getName(), list);
            return;
        }
        try {
            Class<? extends blackboard.base.BbEnum> bbe_c = clazz.asSubclass(blackboard.base.BbEnum.class);
            list = BbWsUtil.getBbEnumFieldNames(bbe_c);
            genRegulation.dataType2ValuesMap.put(ctClass.getName(), list);
            return;
        } catch (ClassCastException cce) {} //ignore it. it just means that asSubclass failed and it is not BbEnum
        genRegulation.dataType2ValuesMap.put(ctClass.getName(), list);
    }


    void generateFillExcludedBbToucherMethodNamesMap() throws Exception {
//        JFieldVar excmn_jfv = definedClass.field(JMod.STATIC,
//                linhm_jc.narrow(String.class, String.class), "excludedMethodNames");
//        excmn_jfv.init(JExpr._new(linhm_jc.narrow(String.class, String.class)));
        JFieldRef excmn_jfr = JExpr.refthis("excludedBbToucherMethodNamesMap");
        JMethod fill_etmnm_jm = genRegulation.regulationJDC.method(JMod.PROTECTED
                , genRegulation.codeModel.VOID
                , "fillExcludedBbToucherMethodNamesMap");
        fill_etmnm_jm.annotate(Override.class);

        JVar comment_var = fill_etmnm_jm.body().decl(genRegulation.codeModel.ref(String.class), "comment");
        for (Map.Entry<String, String> exc_m_entry:
                genRegulation.excludedBbToucherMethodNamesMap.entrySet()) {
            String m_signature = exc_m_entry.getKey();
            String m_comment = exc_m_entry.getValue();
            fill_etmnm_jm.body().assign(comment_var, JExpr.lit(m_comment));
            JInvocation invoc_put = fill_etmnm_jm.body().invoke(excmn_jfr, "put");
            invoc_put.arg(m_signature);
            invoc_put.arg(comment_var);
        }
    }

    void generateFillBBToucher2wsFieldMaps() {
        JClass linhm_jc = genRegulation.codeModel.ref(LinkedHashMap.class);
        //JFieldVar get2fm_jfr = genRegulation.regulationJDC.field(JMod.STATIC,
          //      linhm_jc.narrow(String.class, WsgBbGetterRegulationStruct.class), "bbGetter2WSFieldMap");
        //get2fm_jfr.init(JExpr._new(linhm_jc.narrow(String.class, WsgBbGetterRegulationStruct.class)));
        JFieldRef get2fm_jfr = JExpr.refthis("bbGetter2WSFieldMap");
        //JFieldVar set2fm_jfr = genRegulation.regulationJDC.field(JMod.STATIC,
          //      linhm_jc.narrow(String.class, WsgBbSetterRegulationStruct.class), "bbSetter2WSFieldMap");
        //set2fm_jfr.init(JExpr._new(linhm_jc.narrow(String.class, WsgBbSetterRegulationStruct.class)));
        JFieldRef set2fm_jfr = JExpr.refthis("bbSetter2WSFieldMap");

        JMethod fill_t2fm_jm = genRegulation.regulationJDC.method(JMod.PROTECTED
                , genRegulation.codeModel.VOID
                , "fillBBToucher2wsFieldMaps");
        fill_t2fm_jm.annotate(Override.class);
        JVar key = fill_t2fm_jm.body().decl(genRegulation.codeModel.ref(String.class), "key");

        //* for (Map.Entry<String, WsgBbTouchersLink> dal_entry:
        //*        genRegulation.wsField2BbTouchersLinkMap.entrySet()) {
        for (WsgDataRegulationStruct dr : genRegulation.dataFieldRegulationList) {
            //* String field_name = dal_entry.getKey();
            String field_name = dr.wsFieldName;
            //* WsgBbTouchersLink dal = dal_entry.getValue();
            //* if (dal.dataGetterSignature != null) {
                WsgBbGetterRegulationStruct dgr =
            //*            genRegulation.bbGetter2WSFieldMap.get(dal.dataGetterSignature);
                        genRegulation.bbGetter2WSFieldMap.get(dr.dataGetterSignature);
                //* fill_t2fm_jm.body().assign(key, JExpr.lit(dal.dataGetterSignature));
                fill_t2fm_jm.body().assign(key, JExpr.lit(dr.dataGetterSignature));
                JInvocation invoc_new_val = JExpr._new(genRegulation.codeModel.ref(WsgBbGetterRegulationStruct.class));
                invoc_new_val.arg(dgr.bbGetMethodName);
                invoc_new_val.arg(dgr.boolPrefix);
                invoc_new_val.arg(dgr.getFieldNameLCase());
                invoc_new_val.arg(dgr.returnType);
                JInvocation invoc_put = fill_t2fm_jm.body().invoke(get2fm_jfr, "put");
                invoc_put.arg(key);
                invoc_put.arg(invoc_new_val);
            //* }
            //* if (dal.dataSetterSignature != null) {
                WsgBbSetterRegulationStruct dsr =
                        //* genRegulation.bbSetter2WSFieldMap.get(dal.dataSetterSignature);
                        genRegulation.bbSetter2WSFieldMap.get(dr.dataSetterSignature);
                //* fill_t2fm_jm.body().assign(key, JExpr.lit(dal.dataSetterSignature));
                fill_t2fm_jm.body().assign(key, JExpr.lit(dr.dataSetterSignature));
                //* JInvocation invoc_new_val = JExpr._new(genRegulation.codeModel.ref(WsgBbSetterRegulationStruct.class));
                invoc_new_val = JExpr._new(genRegulation.codeModel.ref(WsgBbSetterRegulationStruct.class));
                invoc_new_val.arg(dsr.bbSetMethodName);
                invoc_new_val.arg(dsr.getFieldNameLCase());
                invoc_new_val.arg(dsr.paramType);
                //* JInvocation invoc_put = fill_t2fm_jm.body().invoke(set2fm_jfr, "put");
                invoc_put = fill_t2fm_jm.body().invoke(set2fm_jfr, "put");
                invoc_put.arg(key);
                invoc_put.arg(invoc_new_val);
            //* }
        }
    }

    void generateFillDataType2ValuesMap() {
        WsgUtil.sortMapByComparableKey(genRegulation.dataType2ValuesMap);

        JMethod fill_datt2val_jm = genRegulation.regulationJDC.method(JMod.PUBLIC, genRegulation.codeModel.VOID, "fillDataType2ValuesMap");
        fill_datt2val_jm.annotate(Override.class);
        JClass list_jc = genRegulation.codeModel.ref(List.class).narrow(String.class);
        JClass arr_list_jc = genRegulation.codeModel.ref(ArrayList.class).narrow(String.class);

        JVar key_jv = fill_datt2val_jm.body().decl(genRegulation.codeModel.ref(String.class), "key");
        JVar list_jv = fill_datt2val_jm.body().decl(list_jc, "list");

        JFieldRef map_jfr = JExpr.refthis("dataType2ValuesMap");

        for (Map.Entry<String, List<String>> datt2val_entry:
                genRegulation.dataType2ValuesMap.entrySet()) {
            fill_datt2val_jm.body().assign(key_jv, JExpr.lit(datt2val_entry.getKey()));
            fill_datt2val_jm.body().assign(list_jv, JExpr._new(arr_list_jc));
            List<String> list = datt2val_entry.getValue();
            WsgUtil.sortComparableList(list);
            for (String val: list) {
                fill_datt2val_jm.body().invoke(list_jv, "add").arg(val);
            }
            fill_datt2val_jm.body().invoke(map_jfr, "put").arg(key_jv).arg(list_jv);
        }
    }

    void generateRegulationsInitialization() throws Exception {
        generateFillExcludedBbToucherMethodNamesMap();
        generateFillBBToucher2wsFieldMaps();
        generateFillDataType2ValuesMap();
    }



    void generateDataDetails() throws Exception {
        JCodeModel codeModel = new JCodeModel();
        JDefinedClass definedClass = genRegulation.codeModel._class(
            genRegulation.getDataDetailsClassName() + "_Gen"
        );
        definedClass._extends(bbwscommon.BbWsDataDetails.class);

        for (Map.Entry<String, WsgBbTouchersLink> dal_entry:
                genRegulation.wsField2BbTouchersLinkMap.entrySet()) {
            String field_name = dal_entry.getKey();
            WsgBbTouchersLink dal = dal_entry.getValue();
            //String ft_name = null;
            WsgBbGetterRegulationStruct dgr = null;
            WsgBbSetterRegulationStruct dsr = null;
            if (dal.dataGetterSignature != null)
                dgr = genRegulation.bbGetter2WSFieldMap.get(dal.dataGetterSignature);
            if (dal.dataSetterSignature != null)
                dsr = genRegulation.bbSetter2WSFieldMap.get(dal.dataSetterSignature);
            String bool_prefix = "";
            if (dgr != null) {
                //ft_name = dgr.returnType;
                bool_prefix = dgr.getBoolPrefixUCase();
                if (!StringUtils.equals(bool_prefix, ""))
                    field_name = dgr.getBoolPrefixLCase() + WsgUtil.firstLetterUCase(dgr.getFieldNameLCase());
            } //else ft_name = dsr.paramType;
            //JClass f_class = genRegulation.codeModel.ref(String.class);
            JFieldVar field = definedClass.field(JMod.PRIVATE, String.class, field_name);
            JFieldRef f_ref = JExpr.ref(JExpr._this(), field);
            if (dgr != null) {
                JMethod method = definedClass.method(JMod.PUBLIC, String.class,
                        "get" + bool_prefix + WsgUtil.firstLetterUCase(dgr.getFieldNameLCase()));
                 //method.type(f_type);
                 method.body()._return(f_ref);
            }
            if (dsr != null) {
                JMethod method = definedClass.method(JMod.PUBLIC, void.class,
                        "set" + bool_prefix + WsgUtil.firstLetterUCase(dsr.getFieldNameLCase()));
                JVar param = method.param(String.class, field_name);
                method.body().assign(f_ref, param);
            }

        }
        genRegulation.codeModel.build(new File("./generated"));
    }

    void generateDataAccessPack () throws Exception {
        dataAccessPackGenerator.generate();
    }

}
