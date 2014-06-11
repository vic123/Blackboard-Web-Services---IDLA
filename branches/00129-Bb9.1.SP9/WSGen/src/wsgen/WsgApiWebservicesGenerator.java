/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsgen;

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
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.CodeWriter;


import bbwscommon.BbWsParams;
import bbwscommon.BbWsDataAccessPack;

import com.sun.codemodel.CodeWriter;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class WsgApiWebservicesGenerator {
    WsgRegulation genRegulation;
    JDefinedClass dataAccessPackJDC;
    public WsgApiWebservicesGenerator(WsgRegulation genRegulation) {
        this.genRegulation = genRegulation;
        this.dataAccessPackJDC = genRegulation.dataAccessPackGenJDC;
    }
    public void generate() throws Exception {
        //JDefinedClass webservices_jdc = genRegulation.codeModel._class("bbwscommon.BbWebservices_Gen");
        JDefinedClass webservices_jdc = genRegulation.webservicesJDC;
        JDefinedClass iwebservices_jdc = genRegulation.iwebservicesJDC;


        //JDefinedClass args_jdc = genRegulation.dataAccessPackArgumentsJDC;
                //= dataAccessPackGenJDC._class(genRegulation.dataClassSimpleName
                  //          + "Arguments_"
                    ///        + genRegulation.apiSuffix);
        for (WsgApiRegulationStruct api_reg: genRegulation.apiRegulationList) {
            //JClass bbdata_jclass = genRegulation.codeModel.directClass(genRegulation.dataClassName);
            JClass wsdata_jc = genRegulation.codeModel.ref(genRegulation.getDataDetailsClassName());
            JClass result_jtype;
            if (WsgApiDataKind.ListById.equals(api_reg.apiResultKind) || WsgApiDataKind.ListByTemplate.equals(api_reg.apiResultKind)) {
                result_jtype = genRegulation.codeModel.ref(List.class).narrow(wsdata_jc);
                //result_jtype
            } else result_jtype = wsdata_jc;
            JMethod method = webservices_jdc.method(JMod.PUBLIC, result_jtype, api_reg.wsMethodName);
            JMethod imethod = iwebservices_jdc.method(JMod.PUBLIC, result_jtype, api_reg.wsMethodName);
            imethod.annotate(javax.jws.WebMethod.class);
            JVar params_jvar = method.param(BbWsParams.class, "params");
            JVar iparams_jvar = imethod.param(BbWsParams.class, "params");
            JAnnotationUse iparam_jau = iparams_jvar.annotate(javax.jws.WebParam.class);
            iparam_jau.param("name", "params");
            //JDefinedClass wsdata_jdc = genRegulation.codeModel._getClass(genRegulation.getDataDetailsClassName());

            JVar inprec_jvar = null;
            JVar iinprec_jvar = null;
            JAnnotationUse iinprec_jau = null;
            if (WsgApiDataKind.ListById.equals(api_reg.apiResultKind) || WsgApiDataKind.ListByTemplate.equals(api_reg.apiResultKind)) {
                JClass list_jc = genRegulation.codeModel.ref(List.class).narrow(wsdata_jc);
                inprec_jvar = method.param(list_jc, "inputList");
                iinprec_jvar = imethod.param(list_jc, "inputList");
                iinprec_jau = inprec_jvar.annotate(javax.jws.WebParam.class);
                iinprec_jau.param("name", "inputList");
            } else {
                inprec_jvar = method.param(wsdata_jc, "inputRecord");
                iinprec_jvar = imethod.param(wsdata_jc, "inputRecord");
                iinprec_jau = inprec_jvar.annotate(javax.jws.WebParam.class);
                iinprec_jau.param("name", "inputRecord");
            }
            method._throws(javax.xml.ws.WebServiceException.class);
            method._throws(bbwscommon.BbWsFault.class);
            imethod._throws(javax.xml.ws.WebServiceException.class);
            imethod._throws(bbwscommon.BbWsFault.class);

            JClass log_jclass = genRegulation.codeModel.ref(bbwscommon.BbWsLog.class);
            JInvocation log_jinv = method.body().staticInvoke(log_jclass, "logForward");
            JClass logverbosity_jc = genRegulation.codeModel.ref(blackboard.platform.log.LogService.Verbosity.class);
            JFieldRef logverbosity_jfr = logverbosity_jc.staticRef("INFORMATION");
            log_jinv.arg(logverbosity_jfr);
            //??doesn't add import blackboard.platform.log.LogService
            //with code below - adds
            //JClass logservice_jc = genRegulation.codeModel.ref(blackboard.platform.log.LogService.class);
            //JFieldRef logverbosity_jfr1 = logservice_jc.staticRef("Verbosity");
            //log_jinv.arg(logverbosity_jfr1);
            log_jinv.arg("Entered " + api_reg.wsMethodName + "()");
            log_jinv.arg(JExpr._this());

            JClass args_jc
                    = genRegulation.codeModel.ref(genRegulation.getDataAccessPackClassName()
                        + "." + genRegulation.dataClassSimpleName
                        + "Arguments_"
                        + genRegulation.apiSuffix);

            genRegulation.codeModel._getClass(genRegulation.getDataDetailsClassName());
            JInvocation args_new_ji = JExpr._new(args_jc);
            //JExpression args_je = args_jv.assign(args_new_ji);
            JVar args_jv = method.body().decl(args_jc, "args").init(args_new_ji);
            JInvocation initialize_ji = method.body().invoke(args_jv, "initialize")
                    .arg(wsdata_jc.staticRef("class"))
                    .arg(params_jvar)
                    .arg(inprec_jvar)
                    .arg(dataAccessPackJDC.staticRef("class").invoke("getName"))
                    .arg(api_reg.getDataAccessPackNestedClassName())
            ;
            if (api_reg.needsApiParam) {
                JClass apiparam_jc = genRegulation.codeModel.ref(genRegulation.getDataParamsClassName());
                String api_params_name = genRegulation.getDataParamsFieldName();
                JVar apiparam_jv = method.param(apiparam_jc, api_params_name);
                JVar iapiparam_jv = imethod.param(apiparam_jc, api_params_name);
                JAnnotationUse apiparam_jau = apiparam_jv.annotate(javax.jws.WebParam.class);
                JAnnotationUse iapiparam_jau = iapiparam_jv.annotate(javax.jws.WebParam.class);
                apiparam_jau.param("name", api_params_name);
                iapiparam_jau.param("name", api_params_name);
                initialize_ji.arg(apiparam_jv);
            } else initialize_ji.arg(JExpr._null());

            method.body().staticInvoke(genRegulation.codeModel.ref(bbwscommon.BbWsApiProcessor.class), "start")
                    .arg(args_jv);
            if (WsgApiDataKind.ListById.equals(api_reg.apiResultKind) || WsgApiDataKind.ListByTemplate.equals(api_reg.apiResultKind)) {
                method.body()._return(JExpr.invoke(args_jv, "getResultList"));
            } else method.body()._return(JExpr.invoke(args_jv, "getResultRecord"));
            
            //dataAccessPackGenJDC.staticRef(null).
            //args_jv.invoke("initialize").arg(wsdata_jdc.staticRef("class"));

            //args_jv.init(args_new_ji);
            //method.body().assign(args_jv, args_new_ji);
            //method.body().assign(args_jv, args_new_ji);
            //method.body().assign(args_jv, args_new_ji);
            ///+++
        }

        //DataAccessPack nested classes template
        JDefinedClass dapgen_jdc = genRegulation.dataAccessPackGenJDC;

        JMethod gen_id_jm = dapgen_jdc
                .method(JMod.PROTECTED, blackboard.persist.Id.class, "generateInputId")
                ._throws(Exception.class);
        //JInvocation gen_id_ji = gen_id_jm.body().invoke("checkAndgenerateId");
        JInvocation gen_id_ji = JExpr.invoke("checkAndgenerateId");
        gen_id_ji.arg(genRegulation.codeModel.ref(genRegulation.dataClassName).staticRef("DATA_TYPE"));
        gen_id_ji.arg(JExpr.invoke("getArgs").invoke("getInputRecord").invoke("getBbId"));
        gen_id_jm.body()._return(gen_id_ji);


        JDefinedClass dap4cp_jdc = genRegulation.codeModel._class(genRegulation.getDataAccessPackClassName() + "_4CopyPaste_Gen");
        dap4cp_jdc._extends(genRegulation.dataAccessPackGenJDC);
        JDefinedClass dapgen4cp_accessbyid_jdc
                = dap4cp_jdc._class(JMod.PROTECTED | JMod.STATIC, "AccessByIdPack")
                ._extends(dapgen_jdc);
        JDefinedClass dapgen4cp_accessbybatchuid_jdc
                = dap4cp_jdc._class(JMod.PUBLIC | JMod.STATIC, "AccessByBatchUidPack")
                ._extends(dapgen_jdc);
        JDefinedClass dapgen4cp_loadlistpack_jdc
                = dap4cp_jdc._class(JMod.PUBLIC | JMod.STATIC, "LoadListPack")
                ._extends(dapgen_jdc);

        //dap_jdc = dap_jdc._extends(genRegulation.dataAccessPackGenJDC);
        //JClass dap_jdc = genRegulation.codeModel.directClass(genRegulation.getDataAccessPackClassName());
        String s = genRegulation.getDataAccessPackClassName();
        JDefinedClass dap_jdc = genRegulation.codeModel._class(genRegulation.getDataAccessPackClassName());
        dap_jdc._extends(genRegulation.dataAccessPackGenJDC);
        JDefinedClass dap_accessbyid_jdc
                = dap_jdc._class(JMod.PUBLIC | JMod.STATIC, "AccessByIdPack")._extends(dapgen_jdc);
        JDefinedClass dap_accessbybatchuid_jdc
                = dap_jdc._class(JMod.PUBLIC | JMod.STATIC, "AccessByBatchUidPack");
        JDefinedClass dap_loadlistpack_jdc
                = dap_jdc._class(JMod.PUBLIC | JMod.STATIC, "LoadListPack");

        Map<WsgApiOperationKind, Class> op_kind2recda_map
                = new LinkedHashMap<WsgApiOperationKind, Class>();
        op_kind2recda_map.put(WsgApiOperationKind.Delete, BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordDeleter.class);
        op_kind2recda_map.put(WsgApiOperationKind.Insert, BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordInserter.class);
        op_kind2recda_map.put(WsgApiOperationKind.Load, BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordLoader.class);
        op_kind2recda_map.put(WsgApiOperationKind.Persist, BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordPersister.class);
        op_kind2recda_map.put(WsgApiOperationKind.Update, BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordUpdater.class);

        String accm_names[] = {"deleteRecord",
                            "insertRecord",
                            "loadRecord",
                            "loadRecordByAltId",
                            "persistRecord",
                            "updateRecord"};
        for (String accm_name: accm_names) {
            JMethod access_jm = dapgen4cp_accessbyid_jdc.method(JMod.PROTECTED,
                    genRegulation.codeModel.VOID, accm_name)
                    ._throws(Exception.class);
            access_jm.annotate(Override.class);
            access_jm = dapgen4cp_accessbybatchuid_jdc.method(JMod.PROTECTED,
                    genRegulation.codeModel.VOID, accm_name)
                    ._throws(Exception.class);
            access_jm.annotate(Override.class);
        }

        JMethod list_init_jm = dapgen4cp_loadlistpack_jdc.method(JMod.PUBLIC,
                        genRegulation.codeModel.VOID,
                        "initialize");
        JVar list_args_jvar = list_init_jm.param(genRegulation.dataAccessPackArgumentsJDC, "args");
        JClass list_bbdata_jclass = genRegulation.codeModel.directClass(genRegulation.dataClassName);
        JClass list_da_jc = genRegulation.codeModel.ref(
                    BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordListLoader.class
            );
        JVar list_da_jv = list_init_jm.body().decl(list_da_jc, "da", JExpr._new(list_da_jc));
        list_init_jm.body().invoke(list_da_jv, "initialize").arg(JExpr._null());
        list_init_jm.body().invoke(JExpr._super(), "initialize")
                            .arg(list_args_jvar)
                            .arg(JExpr.dotclass(list_bbdata_jclass))
                            .arg(list_da_jv);

        for (WsgApiRegulationStruct api_reg: genRegulation.apiRegulationList) {
            String nested_cname = api_reg.getDataAccessPackNestedClassName();
            try {
                //inheriting from nested class which enclosing one ends on the digit
                //adds an extra space before the dot, i.e.
                //extends bbgbws.GradableItemAccessPack_GB2 .AccessByIdPack
                //An issue may be started debugged from JFormatter.t(JClass type)
                //Currently it is fixed with perl post processing of generated code.
                //https://java.net/jira/browse/CODEMODEL-22?page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel&focusedCommentId=372455
                JDefinedClass nested_jdc = dapgen_jdc._class(JMod.PUBLIC | JMod.STATIC, nested_cname);

                //JDefinedClass nested4cp_jdc = dap4cp_jdc._class(JMod.PUBLIC | JMod.STATIC, nested_cname);
                //nested4cp_jdc._extends(nested_jdc);

                if ("ById".equals(api_reg.apiBySuffix)) nested_jdc._extends(dap_accessbyid_jdc);
                else if ("ByBatchUid".equals(api_reg.apiBySuffix)) nested_jdc._extends(dap_accessbybatchuid_jdc);
                else if (api_reg.apiOperation.equals(WsgApiOperationKind.Load)
                        && api_reg.apiResultKind.equals(WsgApiDataKind.ListByTemplate)) {
                    nested_jdc._extends(dap_loadlistpack_jdc);
                    String loadlist_mname = "loadList" + api_reg.apiBySuffix;
                    JMethod loadlist_jm = dapgen4cp_loadlistpack_jdc
                            .method(JMod.PROTECTED, void.class, loadlist_mname)
                            ._throws(Exception.class);
                    JMethod loadlist_nested_jm =  nested_jdc.method(JMod.PROTECTED, void.class, "loadList")
                            ._throws(Exception.class);
                    loadlist_nested_jm.annotate(Override.class);
                    loadlist_nested_jm.body().invoke(loadlist_jm);
                } else if (api_reg.apiOperation.equals(WsgApiOperationKind.Load)
                        && api_reg.apiResultKind.equals(WsgApiDataKind.Record)) {
                    nested_jdc._extends(dapgen_jdc);
                    JDefinedClass nested4cp_jdc = dap4cp_jdc._class(JMod.PUBLIC | JMod.STATIC, nested_cname);
                    nested4cp_jdc._extends(nested_jdc);
                    nested4cp_jdc.method(JMod.PROTECTED, void.class, "loadRecord")
                            ._throws(Exception.class);
                }

                //else nested_jdc._extends(dapgen4cp_accessbybatchuid_jdc);


                //nested_jdc._extends(dap_jc);


                /*
                if (WsgApiDataKind.ListByTemplate.equals(api_reg.apiResultKind)) {
                    JClass da_jc = genRegulation.codeModel.ref(
                                BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.RecordListLoader.class
                       );
                    JVar da_jv = init_jm.body().decl(da_jc, "da", JExpr._new(da_jc));
                    init_jm.body().invoke(da_jv, "initialize").arg(JExpr._null());
                    init_jm.body().invoke(JExpr._super(), "initialize")
                            .arg(args_jvar)
                            .arg(JExpr.dotclass(bbdata_jclass))
                            .arg(da_jv);
                } else*/
                if (!WsgApiDataKind.ListByTemplate.equals(api_reg.apiResultKind)) {
                    JMethod init_jm = nested_jdc.method(JMod.PUBLIC,
                            genRegulation.codeModel.VOID,
                            "initialize");
                    JVar args_jvar = init_jm.param(genRegulation.dataAccessPackArgumentsJDC, "args");
                    JClass bbdata_jclass = genRegulation.codeModel.directClass(genRegulation.dataClassName);

                    Class da_clazz = op_kind2recda_map.get(api_reg.apiOperation);
                    JClass da_jc = genRegulation.codeModel.ref(da_clazz);
                    JVar da_jv = init_jm.body().decl(da_jc, "da", JExpr._new(da_jc));
                    init_jm.body().invoke(da_jv, "initialize").arg(JExpr._null());
                    JVar ilp_jv = null;
                    if (WsgApiDataKind.ListById.equals(api_reg.apiResultKind)) {
                        JClass ilp_jc = genRegulation.codeModel.ref(
                                BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.InputListProcessor.class
                        );
                        ilp_jv = init_jm.body().decl(ilp_jc, "ilp", JExpr._new(ilp_jc));
                        init_jm.body().invoke(ilp_jv, "initialize").arg(da_jv);
                    }
                    JInvocation super_init_ji = init_jm.body().invoke(JExpr._super(), "initialize")
                            .arg(args_jvar)
                            .arg(JExpr.dotclass(bbdata_jclass));
                    if (WsgApiDataKind.ListById.equals(api_reg.apiResultKind)) {
                        super_init_ji.arg(ilp_jv);
                    } else super_init_ji.arg(da_jv);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        genRegulation.codeModel.build(new File("./generated"));
    }


}
