/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;
//* import javassist.ClassPool;
//* import javassist.CtClass;
//* import javassist.CtMethod;
//* import javassist.CtField;
//* import javassist.bytecode.AccessFlag;


import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JArray;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JVar;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JCatchBlock;
import com.sun.codemodel.JType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;


import blackboard.platform.gradebook2.GradableItem;
//import bbwscommon.*;
import bbwscommon.BbWsUtil;
import bbwscommon.BbWsDataDetails;
import bbwscommon.BbWsDataAccessPack;
import bbwscommon.BbWsException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * @author Administrator
 */
public class WsgApiRegulationGenerator {
    WsgRegulation genRegulation;
    public WsgApiRegulationGenerator(WsgRegulation genRegulation) {
        this.genRegulation = genRegulation;
    }

    private List<Map.Entry<String, String>> excludedApiMethodNamesMapEntryList = new ArrayList<Map.Entry<String, String>>();
    private List<Map.Entry<String, String>> includedApiMethodNamesMapEntryList = new ArrayList<Map.Entry<String, String>>();


    public void generate() throws Exception {
        initApiRegulationLists();
        generateApiRegulation();
    }

    public void initApiRegulationLists() throws Exception {
        //* ClassPool pool = ClassPool.getDefault();
        for (Map.Entry<String,
                Map<WsgApiOperationKind,
                    List<String>>> apicn2opek_entry
                        : genRegulation.apiClassName2OperationKindMap.entrySet()) {
            Map<WsgApiOperationKind, List<String>> apimpplm
                    = apicn2opek_entry.getValue();
            initApiRegulationListsRecursive(apicn2opek_entry.getKey(), apimpplm);
        }
        List<WsgApiRegulationStruct> api_reg_list_lists = new ArrayList<WsgApiRegulationStruct>();
        for (WsgApiRegulationStruct api_reg: genRegulation.apiRegulationList) {
            //if (api_reg.apiResultKind.equals(WsgApiDataKind.Record))
            if (("ById".equals(api_reg.apiBySuffix) || "ByBatchUid".equals(api_reg.apiBySuffix))
                    && (api_reg.apiParamKind == WsgApiDataKind.Record)
                ) {
                WsgApiRegulationStruct api_reg1 = new WsgApiRegulationStruct(api_reg);
                api_reg1.apiResultKind = WsgApiDataKind.ListById;
                api_reg1.apiParamKind = WsgApiDataKind.ListById;
                api_reg1.wsMethodName = WsgUtil.firstLetterLCase(genRegulation.dataClassSimpleName);
                api_reg1.wsMethodName += api_reg1.getDataAccessPackNestedClassName();

                api_reg_list_lists.add(api_reg1);

            }
        }
        genRegulation.apiRegulationList.addAll(api_reg_list_lists);

        Collections.sort(genRegulation.apiRegulationList, new Comparator<WsgApiRegulationStruct>() {
                public int compare(WsgApiRegulationStruct a, WsgApiRegulationStruct b){
                //return a.bbMethodSignature.compareTo(b.bbMethodSignature);
                    return a.wsMethodName.compareTo(b.wsMethodName);
            }
        });
        excludedApiMethodNamesMapEntryList =
                WsgUtil.sortMapEntryListByKey(
                    new ArrayList<Map.Entry<String, String>>(genRegulation.excludedApiMethodNamesMap.entrySet())
                );
        /*
        excludedApiMethodNamesMapEntryList = new ArrayList<Map.Entry<String, String>>(genRegulation.excludedApiMethodNamesMap.entrySet());
        Collections.sort(excludedApiMethodNamesMapEntryList, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> a, Map.Entry<String, String> b){
                //return a.bbMethodSignature.compareTo(b.bbMethodSignature);
                    return a.getKey().compareTo(b.getKey());
            }
        });*/
        includedApiMethodNamesMapEntryList =
                WsgUtil.sortMapEntryListByKey(
                    new ArrayList<Map.Entry<String, String>>(genRegulation.includedApiMethodNamesMap.entrySet())
                );
        /*
        includedApiMethodNamesMapEntryList = new ArrayList<Map.Entry<String, String>>(genRegulation.includedApiMethodNamesMap.entrySet());
        Collections.sort(includedApiMethodNamesMapEntryList, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> a, Map.Entry<String, String> b){
                    return a.getKey().compareTo(b.getKey());
            }
        });*/
    }
    public void initApiRegulationListsRecursive(String className,
                    Map<WsgApiOperationKind, List<String>> apiMethodPrefixPatternListMap) throws Exception {
        //* ClassPool pool = ClassPool.getDefault();
        //* CtClass cclazz = pool.get(className);
        Class cclazz = WsgUtil.getClassForName(className);
        //* CtClass[] api_interfaces = cclazz.getInterfaces();
        Class[] api_interfaces = cclazz.getInterfaces();
        //* List<CtClass> class_list = new ArrayList<CtClass>(Arrays.asList(api_interfaces));
        List<Class> class_list = new ArrayList<Class>(Arrays.asList(api_interfaces));
        class_list.add(cclazz);
        //* CtClass cc_obj = pool.get("java.lang.Object");
        Class cc_obj = WsgUtil.getClassForName("java.lang.Object");
        //* for (CtClass api_cclass: class_list) {
        for (Class api_cclass: class_list) {
            if (api_cclass.equals(cc_obj)) continue;
            //* CtMethod[] meth_arr = api_cclass.getDeclaredMethods();
            Method[] meth_arr = api_cclass.getDeclaredMethods();
            //* for (CtMethod method: meth_arr){
            for (Method method: meth_arr){
                //?? WsgApiRegulationStruct api_reg = null;
                //** if (!AccessFlag.isPublic(method.getMethodInfo().getAccessFlags())) continue;
                if (!Modifier.isPublic(method.getModifiers())) continue;
                String m_name = method.getName();
                //String m_signature = api_cclass.getName() + " " + WsgUtil.getMethodSignature(method);//method.getSignature();
                String m_signature = WsgUtil.getMethodSignature(method);//method.getSignature();
                if (genRegulation.excludedApiMethodNamesMap.containsKey(m_signature)) continue;

                //CtClass data_cc = pool.get(genRegulation.dataClassName);
                //String data_cname = data_cc.getSimpleName();
                if (!genRegulation.includedApiMethodNamesMap.containsKey(m_signature)) {
                    boolean found_dcn_pattern = false;
                    for (String data_cname: genRegulation.apiMethodDataClassPatternList) {
                        if (StringUtils.containsIgnoreCase(m_name, data_cname)) found_dcn_pattern = true;
                    }

                    //??if (!found_dcn_pattern && api_reg == null) {
                    if (!found_dcn_pattern) {
                        String comment = "Missing data class name ("
                                + Arrays.toString(genRegulation.apiMethodDataClassPatternList.toArray())
                                + ") in method name; ";
                        genRegulation.excludedApiMethodNamesMap.put(m_signature, comment);
                        continue;
                    }
                }
                boolean found_p_pattern = false;
                for (Map.Entry<WsgApiOperationKind, List<String>> mpp_list_entry: apiMethodPrefixPatternListMap.entrySet()) {
                    WsgApiOperationKind api_op = mpp_list_entry.getKey();
                    for (String p_pattern: mpp_list_entry.getValue()) {
                        if (StringUtils.startsWithIgnoreCase(m_name, p_pattern)) {
                            found_p_pattern = true;
                            //?? if (api_reg == null) api_reg = new WsgApiRegulationStruct();
                            WsgApiRegulationStruct api_reg = new WsgApiRegulationStruct();
                            if (api_reg.bbMethodSignature == null) api_reg.bbMethodSignature = m_signature;
                            if (api_reg.apiOperation == null)
                                api_reg.apiOperation = mpp_list_entry.getKey();
                            if (api_reg.apiBySuffix == null) {
                                api_reg.apiBySuffix = "By" + StringUtils.substringAfter(m_name, "By");
                                if ("By".equals(api_reg.apiBySuffix)) api_reg.apiBySuffix += "Id";
                            }
                            //* CtClass id_cclass = pool.get(blackboard.persist.Id.class.getName());
                            Class id_cclass = blackboard.persist.Id.class;
                            //* CtClass ret_type = method.getReturnType();
                            Class ret_type = method.getReturnType();
                            if (api_reg.apiResultKind == null) {
                                //* if (ret_type.subtypeOf(pool.get("java.util.List"))) {
                                if (WsgUtil.isSubclassOf(ret_type, java.util.List.class)) {
                                    api_reg.apiResultKind = WsgApiDataKind.ListByTemplate;
                                } else api_reg.apiResultKind = WsgApiDataKind.Record;
                                    /*if (ret_type.subclassOf(id_cclass)) {
                                    api_reg.apiResultKind = WsgApiDataKind.Record;
                                } else if (ret_type.isPrimitive()) {
                                    api_reg.apiResultKind = WsgApiDataKind.Primitive;
                                } else api_reg.apiResultKind = WsgApiDataKind.Custom;*/
                            }
                            if (api_reg.needsApiParam == null) {
                                if (api_reg.apiResultKind.equals(WsgApiDataKind.ListByTemplate)) api_reg.needsApiParam = Boolean.TRUE;
                                else  api_reg.needsApiParam = Boolean.FALSE;
                            }
                            if (api_reg.apiResultTypeName == null) api_reg.apiResultTypeName = ret_type.getName();
                            if (api_reg.apiParamKindList == null) {
                                api_reg.apiParamKindList = new ArrayList<WsgApiDataKind>();
                                //* for (CtClass param_type: method.getParameterTypes()) {
                                for (Class param_type: method.getParameterTypes()) {
                                    //* if (param_type.subclassOf(id_cclass)) {
                                    if (WsgUtil.isSubclassOf(param_type, id_cclass)) {
                                        api_reg.apiParamKindList.add(WsgApiDataKind.Record);
                                    } else api_reg.apiParamKindList.add(WsgApiDataKind.Unknown);
                                        /*if (param_type.isPrimitive()) {
                                        api_reg.apiParamKindList.add(WsgApiDataKind.Primitive);
                                    } else api_reg.apiParamKindList.add(WsgApiDataKind.Custom);*/
                                }
                            }
                            if (api_reg.apiParamKind == null) {
                                api_reg.apiParamKind = WsgApiDataKind.Record;
                            }
                            if (api_reg.apiParamTypeNameList == null) {
                                api_reg.apiParamTypeNameList = new ArrayList<String>();
                                //* for (CtClass param_type: method.getParameterTypes()) {
                                for (Class param_type: method.getParameterTypes()) {
                                    api_reg.apiParamTypeNameList.add(param_type.getName());
                                }
                            }
                            if (api_reg.comment == null) api_reg.comment = "";
                            if (api_reg.wsMethodName == null) {
                                //CtClass data_cclass = pool.get(genRegulation.dataClassName);
                                //api_reg.wsMethodName = WsgUtil.firstLetterLCase(data_cclass.getSimpleName());
                                api_reg.wsMethodName = WsgUtil.firstLetterLCase(genRegulation.dataClassSimpleName);
                                api_reg.wsMethodName += api_reg.getDataAccessPackNestedClassName();
                            }

                            /*if (api_reg.apiArgsDirectList == null) {
                                api_reg.apiArgsDirectList = new ArrayList<String>();
                                String field_name = WsgUtil.firstLetterLCase(StringUtils.substringAfter(api_reg.apiBySuffix, "By"));
                                if (field_name.equals("id")) field_name = "bbId";


                            }*/
                                /*if (api_reg.apiParamKindList.size() == 1) {
                                    if (genRegulation.wsField2BbTouchersLinkMap.containsKey(field_name)) {
                                        api_reg.apiArgsDirectList.add("getArgs().getInputRecord().get"
                                                + WsgUtil.firstLetterUCase(field_name) + "()");
                                    }
                                }*/

                            final WsgApiOperationKind api_oper = api_reg.apiOperation;
                            final String bb_m_sig = api_reg.bbMethodSignature;
                            final String wsmn = api_reg.wsMethodName;
                            Collection<WsgApiRegulationStruct> existing_ar_col
                                = CollectionUtils.select(genRegulation.apiRegulationList,
                                    new Predicate<WsgApiRegulationStruct>() {
                                        public boolean evaluate(WsgApiRegulationStruct object) {
                                            //WsgApiRegulation ar = (WsgApiRegulation)object;
                                            return (api_oper.equals(object.apiOperation)
                                                    || wsmn.equals(object.wsMethodName))
                                                    && bb_m_sig.equals(object.bbMethodSignature);
                                        }
                                    });
                            for (WsgApiRegulationStruct existing_ar: existing_ar_col) {
                                existing_ar.assignNullFields(api_reg);
                            }
                            if (existing_ar_col.size() == 0)
                                genRegulation.apiRegulationList.add(api_reg);

                            //add "List" extension
                            /*
                            if (!api_reg.apiResultKind.equals(WsgApiDataKind.List)) {
                                WsgApiRegulation list_api_reg = new WsgApiRegulation(api_reg);
                                if (api_reg.apiResultKind.equals(WsgApiDataKind.Record)) {
                                    api_reg.apiResultKind = WsgApiDataKind.List;
                                }
                                List<WsgApiDataKind> api_pkind_list = new List<WsgApiDataKind>();
                                for (WsgApiDataKind api_pkind: api_reg.apiParamKindList) {

                                }

                            }*/
                        }

                    }
                }

                //?? if (!found_p_pattern && api_reg == null) {
                if (!found_p_pattern) {
                    String prefix_patterns = "";
                    for (Map.Entry<WsgApiOperationKind, List<String>> mpp_list_entry: apiMethodPrefixPatternListMap.entrySet()) {
                        prefix_patterns += mpp_list_entry.getKey().name()
                                + ":" + Arrays.toString(mpp_list_entry.getValue().toArray());
                    }
                    String comment = "Missing action prefix partern ("
                            + prefix_patterns
                            + ") in method name; ";
                    genRegulation.excludedApiMethodNamesMap.put(m_signature, comment);
                    continue;
                }
            }
            //api_cclass = api_cclass.getSuperclass();
            System.out.println(api_cclass.getName());
            if (!api_cclass.isInterface()) {
                initApiRegulationListsRecursive(api_cclass.getSuperclass().getName(),
                        apiMethodPrefixPatternListMap);
            } /*!! else {
                for (Class cl: api_cclass.getInterfaces()) {
                    initApiRegulationListsRecursive(cl.getName(),
                            apiMethodPrefixPatternListMap);
                }
            } */
      //        for (Map.Entry<String, Map<WsgRegulation.WsgApiOperationKind,
                //List<String>>> apicn2opek_entry: genRegulation.apiClassName2OperationKindMap.entrySet()) {
        }
    }


    JMethod fillApiRegulationList_JM;
    JVar apiRegulationStruct_JV;
    JVar apiParamKindList_JV;
    JVar apiParamTypeNameList_JV;
    //JVar apiArgsDirectList_JV;
    JFieldRef apiRegulationList_JFR;

    void generateFillExcludedApiMethodNamesMap() {
        JFieldRef excmnm_jfr = JExpr.refthis("excludedApiMethodNamesMap");
        JMethod filemnm_jm = genRegulation.regulationJDC.method(JMod.PROTECTED, genRegulation.codeModel.VOID, "fillExcludedApiMethodNamesMap");
        filemnm_jm.annotate(Override.class);

        JVar comment_var = filemnm_jm.body().decl(genRegulation.codeModel.ref(String.class), "comment");
        for (Map.Entry<String, String> exc_m_entry:
                excludedApiMethodNamesMapEntryList) {
            String m_signature = exc_m_entry.getKey();
            String m_comment = exc_m_entry.getValue();
            filemnm_jm.body().assign(comment_var, JExpr.lit(m_comment));
            JInvocation invoc_put = filemnm_jm.body().invoke(excmnm_jfr, "put");
            invoc_put.arg(m_signature);
            invoc_put.arg(comment_var);
        }
    }

    void generateFillIncludedApiMethodNamesMap() {
        JFieldRef incmnm_jfr = JExpr.refthis("includedApiMethodNamesMap");
        JMethod filimnm_jm = genRegulation.regulationJDC.method(JMod.PROTECTED, genRegulation.codeModel.VOID, "fillIncludedApiMethodNamesMap");
        JVar incmnm_comment_var = filimnm_jm.body().decl(genRegulation.codeModel.ref(String.class), "comment");
        for (Map.Entry<String, String> exc_m_entry:
                includedApiMethodNamesMapEntryList) {
            String m_signature = exc_m_entry.getKey();
            String m_comment = exc_m_entry.getValue();
            filimnm_jm.body().assign(incmnm_comment_var, JExpr.lit(m_comment));
            JInvocation invoc_put = filimnm_jm.body().invoke(incmnm_jfr, "put");
            invoc_put.arg(m_signature);
            invoc_put.arg(incmnm_comment_var);
        }
    }

    void generateApiRegulation() throws Exception {
        JClass linhm_jc = genRegulation.codeModel.ref(LinkedHashMap.class);
        linhm_jc = linhm_jc.narrow(String.class, String.class);
        JClass arrl_jc = genRegulation.codeModel.ref(ArrayList.class);
        arrl_jc = arrl_jc.narrow(WsgApiRegulationStruct.class);

        //JFieldVar excmnm_jfr = genRegulation.regulationJDC.field(JMod.PUBLIC|JMod.STATIC,
          //      linhm_jc, "excludedMethodNamesMap");
        //excmnm_jfr.init(JExpr._new(linhm_jc));

        

        //JFieldVar incmnm_jfr = genRegulation.regulationJDC.field(JMod.PUBLIC|JMod.STATIC,
          //      linhm_jc, "includedMethodNamesMap");
        //incmnm_jfr.init(JExpr._new(linhm_jc));
        

        //apiRegulationList_JFR = genRegulation.regulationJDC.field(JMod.PUBLIC|JMod.STATIC,
          //      arrl_jc, "apiRegulationList");
        //apiRegulationList_JFR.init(JExpr._new(arrl_jc));

        generateFillExcludedApiMethodNamesMap();
        generateFillIncludedApiMethodNamesMap();



        fillApiRegulationList_JM = genRegulation.regulationJDC.method(JMod.PROTECTED, genRegulation.codeModel.VOID, "fillApiRegulationList");
        fillApiRegulationList_JM.annotate(Override.class);
        apiRegulationStruct_JV = fillApiRegulationList_JM.body().decl(genRegulation.codeModel.ref(WsgApiRegulationStruct.class), "apiRegulationStruct");
        apiParamKindList_JV = fillApiRegulationList_JM.body().decl(genRegulation.codeModel.ref(java.util.List.class).narrow(WsgApiDataKind.class),
                "apiParamKindList");
        apiParamTypeNameList_JV = fillApiRegulationList_JM.body().decl(genRegulation.codeModel.ref(java.util.List.class).narrow(java.lang.String.class),
                "apiParamTypeNameList");
        //apiArgsDirectList_JV = fillApiRegulationList_JM.body().decl(genRegulation.codeModel.ref(java.util.List.class).narrow(java.lang.String.class),
        //        "apiArgsDirectList");

        apiRegulationList_JFR = JExpr.refthis("apiRegulationList");
        for (WsgApiRegulationStruct api_reg: genRegulation.apiRegulationList) {
            generateApiRegulationStructRecord(api_reg);

        }

        genRegulation.codeModel.build(new File("./generated"));
    }

    public void generateApiRegulationStructRecord(WsgApiRegulationStruct api_reg) {

            JBlock sub_block = new JBlock();

            fillApiRegulationList_JM.body().add(sub_block);
            sub_block.assign(apiRegulationStruct_JV, JExpr._new(genRegulation.codeModel.ref(WsgApiRegulationStruct.class)));

            JFieldRef apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "wsMethodName");
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.wsMethodName));

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "bbMethodSignature");
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.bbMethodSignature));

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiOperation");

            sub_block.assign(apir_field_expr, genRegulation.codeModel.ref(WsgApiOperationKind.class).staticRef(api_reg.apiOperation.name()));

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiResultKind");
            //sub_block.assign(apir_field_expr, JExpr.direct("WsgApiDataKind." + api_reg.apiResultKind.name()));
            sub_block.assign(apir_field_expr, genRegulation.codeModel.ref(WsgApiDataKind.class).staticRef(api_reg.apiResultKind.name()));

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "needsApiParam");
            //sub_block.assign(apir_field_expr, JExpr.direct("WsgApiDataKind." + api_reg.apiResultKind.name()));
            //sub_block.assign(apir_field_expr, genRegulation.codeModel.ref(Boolean.class).staticInvoke("valueOf").arg(api_reg.needsApiParam.toString()));
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.needsApiParam));


            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiResultTypeName");
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.apiResultTypeName));

            sub_block.assign(apiParamKindList_JV, JExpr._new(genRegulation.codeModel.ref(java.util.ArrayList.class).narrow(WsgApiDataKind.class)));
            for (WsgApiDataKind apipk: api_reg.apiParamKindList) {
                sub_block.add(
                    apiParamKindList_JV.invoke("add").arg(JExpr.direct("WsgApiDataKind." + apipk.name()))
                );
            }
            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiParamKindList");
            sub_block.assign(apir_field_expr, apiParamKindList_JV);

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiParamKind");
            //sub_block.assign(apir_field_expr, JExpr.direct("WsgApiDataKind." + api_reg.apiResultKind.name()));
            sub_block.assign(apir_field_expr, genRegulation.codeModel.ref(WsgApiDataKind.class).staticRef(api_reg.apiParamKind.name()));

            sub_block.assign(apiParamTypeNameList_JV, JExpr._new(genRegulation.codeModel.ref(java.util.ArrayList.class).narrow(java.lang.String.class)));
            for (String apiptn: api_reg.apiParamTypeNameList) {
                sub_block.add(
                    apiParamTypeNameList_JV.invoke("add").arg(JExpr.lit(apiptn))
                );
            }
            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiParamTypeNameList");
            sub_block.assign(apir_field_expr, apiParamTypeNameList_JV);

            /*sub_block.assign(apiArgsDirectList_JV, JExpr._new(genRegulation.codeModel.ref(java.util.ArrayList.class).narrow(java.lang.String.class)));
            for (String apiad: api_reg.apiArgsDirectList) {
                sub_block.add(
                    apiArgsDirectList_JV.invoke("add").arg(JExpr.lit(apiad))
                );
            }*/
            //apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiArgsDirectList");
            //sub_block.assign(apir_field_expr, apiArgsDirectList_JV);

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "apiBySuffix");
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.apiBySuffix));

            apir_field_expr = JExpr.ref(apiRegulationStruct_JV, "comment");
            sub_block.assign(apir_field_expr, JExpr.lit(api_reg.comment));

            sub_block.add(apiRegulationList_JFR.invoke("add").arg(apiRegulationStruct_JV));
    }
}
