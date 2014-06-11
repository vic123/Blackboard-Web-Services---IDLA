package wsgen;

import bbwscommon.BbWsDataAccessPack;
import bbwscommon.BbWsException;
import bbwscommon.BbWsArguments;
import bbwscommon.BbWsParams;

import blackboard.platform.gradebook2.GradableItem;

import com.sun.codemodel.JCatchBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JVar;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JFieldVar;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class WsgDataAccessPackGenerator {

    WsgRegulation genRegulation;
    public WsgDataAccessPackGenerator(WsgRegulation genRegulation) {
        this.genRegulation = genRegulation;
    }

    JMethod setBbFieldsMethod = null;
    JMethod setWsFieldsMethod = null;

    JMethod genSetFieldsMethod(String method_name) {
        JMethod method = genRegulation.dataAccessPackGenJDC.method(JMod.PROTECTED, genRegulation.codeModel.VOID, method_name);
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

    JDefinedClass genBbFieldSetterClass(WsgDataRegulationStruct dataRegulation) throws Exception {
        JDefinedClass d_class = genRegulation.codeModel.anonymousClass(BbWsDataAccessPack.BbFieldSetter.class);
        JMethod getBb_m = genFieldToucherGetMethod(d_class, "getBbFieldValue");
        JMethod getWs_m = genFieldToucherGetMethod(d_class, "getWsFieldValue");
        JMethod setBb_m = genFieldToucherSetMethod(d_class, "setBbFieldImp");
        if (dataRegulation.bbGetMethodName != null) {
            dataRegulation.getterMethodBodiesGenerator.genGetBbFieldValue(dataRegulation, getBb_m);
        } else {
            dataRegulation.getterMethodBodiesGenerator.genGetBbFieldValueWarn(dataRegulation, getBb_m);
        }
        dataRegulation.getterMethodBodiesGenerator.genGetWsFieldValue(dataRegulation, getWs_m);
        if (dataRegulation.bbSetMethodName != null) {
            dataRegulation.setterMethodBodiesGenerator.genSetBbFieldImp(dataRegulation, setBb_m);
        } else {
            dataRegulation.setterMethodBodiesGenerator.genSetBbFieldValueWarn(dataRegulation, getBb_m);
        }
        return d_class;
    }

    JDefinedClass genWsFieldSetterClass(WsgDataRegulationStruct dataRegulation) throws Exception {
        JDefinedClass d_class = genRegulation.codeModel.anonymousClass(BbWsDataAccessPack.WsFieldSetter.class);
        JMethod getBb_m = genFieldToucherGetMethod(d_class, "getBbFieldValue");
        JMethod getWs_m = genFieldToucherGetMethod(d_class, "getWsFieldValue");
        JMethod setBb_m = genFieldToucherSetMethod(d_class, "setWsFieldImp");
        if (dataRegulation.bbGetMethodName != null) {
            dataRegulation.getterMethodBodiesGenerator.genGetBbFieldValue(dataRegulation, getBb_m);
        } else {
            dataRegulation.getterMethodBodiesGenerator.genGetBbFieldValueWarn(dataRegulation, getBb_m);
        }
        dataRegulation.getterMethodBodiesGenerator.genGetWsFieldValue(dataRegulation, getWs_m);
        dataRegulation.getterMethodBodiesGenerator.genSetWsFieldImp(dataRegulation, setBb_m);
        return d_class;
    }

    void generate() throws Exception {
        genRegulation.dataAccessPackGenJDC = genRegulation.codeModel._class(genRegulation.getDataAccessPackClassName() + "_Gen");
        //genRegulation.dataAccessPackGenJDC.generify("BbGradableItemType", GradableItem.class);
        genRegulation.dataAccessPackArgumentsJDC
                = genRegulation.dataAccessPackGenJDC._class(JMod.PUBLIC | JMod.STATIC,
                            genRegulation.dataClassSimpleName
                            + "Arguments_"
                            + genRegulation.apiSuffix);
        JClass args_narrowed_jc = genRegulation.codeModel.ref(BbWsArguments.class);
        JClass datadetails_jc = genRegulation.codeModel.ref(genRegulation.getDataDetailsClassName());
        args_narrowed_jc = args_narrowed_jc.narrow(datadetails_jc).narrow(datadetails_jc);
        genRegulation.dataAccessPackArgumentsJDC._extends(args_narrowed_jc);

        JClass data_access_pack_narrowed
                = genRegulation.codeModel.ref(BbWsDataAccessPack.BbWsDataAccessPackWithSameInputResultType.class)
                .narrow(genRegulation.dataAccessPackArgumentsJDC)
                .narrow(GradableItem.class)
                .narrow(datadetails_jc);
        genRegulation.dataAccessPackGenJDC = genRegulation.dataAccessPackGenJDC._extends(data_access_pack_narrowed);

        JFieldVar data_params_jfv = genRegulation.dataAccessPackArgumentsJDC.field(JMod.PUBLIC,
                genRegulation.codeModel.ref(genRegulation.getDataParamsClassName()),
                genRegulation.getDataParamsFieldName());

        //record method initialize
        JMethod initialize_jm = genRegulation.dataAccessPackArgumentsJDC
                .method(JMod.PUBLIC, genRegulation.codeModel.VOID, "initialize");
        JVar res_class_jv = initialize_jm.param(genRegulation.codeModel.ref(Class.class).narrow(datadetails_jc), "wsResultClass");
        JVar params_jv = initialize_jm.param(genRegulation.codeModel.ref(BbWsParams.class), "params");
        JVar data_details_jv = initialize_jm.param(datadetails_jc, "recordInput");
        JVar data_ap_class_name_jv = initialize_jm.param(genRegulation.codeModel.ref(String.class), "dataAccessPackClassName");
        JVar inner_dap_def_class_name_jv = initialize_jm.param(genRegulation.codeModel.ref(String.class), "innerDAPDefaultClassName");
        JVar data_params_jv = initialize_jm.param(data_params_jfv.type(), data_params_jfv.name());
        JInvocation initialize_ji = initialize_jm.body().invoke(JExpr._super(), initialize_jm.name());
        initialize_ji.arg(res_class_jv)
                .arg(params_jv)
                .arg(data_details_jv)
                .arg(data_ap_class_name_jv)
                .arg(inner_dap_def_class_name_jv);
        initialize_jm.body().assign(JExpr.ref(JExpr._this(), data_params_jfv), data_params_jv);
        JConditional data_params_jc = initialize_jm.body()._if(data_params_jfv.eq(JExpr._null()));
        data_params_jc._then().assign(
                    JExpr.ref(JExpr._this(), data_params_jfv),
                    JExpr._new(genRegulation.codeModel.ref(genRegulation.getDataParamsClassName()))
                    );


        //record method initialize
        initialize_jm = genRegulation.dataAccessPackArgumentsJDC
                .method(JMod.PUBLIC, genRegulation.codeModel.VOID, "initialize");
        res_class_jv = initialize_jm.param(genRegulation.codeModel.ref(Class.class).narrow(datadetails_jc), "wsResultClass");
        params_jv = initialize_jm.param(genRegulation.codeModel.ref(BbWsParams.class), "params");
        JClass list_jc = genRegulation.codeModel.ref(List.class).narrow(datadetails_jc);
        data_details_jv = initialize_jm.param(list_jc, "listInput");
        data_ap_class_name_jv = initialize_jm.param(genRegulation.codeModel.ref(String.class), "dataAccessPackClassName");
        inner_dap_def_class_name_jv = initialize_jm.param(genRegulation.codeModel.ref(String.class), "innerDAPDefaultClassName");
        data_params_jv = initialize_jm.param(data_params_jfv.type(), data_params_jfv.name());
        initialize_ji = initialize_jm.body().invoke(JExpr._super(), initialize_jm.name());
        initialize_ji.arg(res_class_jv)
                .arg(params_jv)
                .arg(data_details_jv)
                .arg(data_ap_class_name_jv)
                .arg(inner_dap_def_class_name_jv);
        initialize_jm.body().assign(JExpr.ref(JExpr._this(), data_params_jfv), data_params_jv);
        data_params_jc = initialize_jm.body()._if(data_params_jfv.eq(JExpr._null()));
        data_params_jc._then().assign(
                    JExpr.ref(JExpr._this(), data_params_jfv),
                    JExpr._new(genRegulation.codeModel.ref(genRegulation.getDataParamsClassName()))
                    );

        /*
        private CourseCopyParams courseCopyParams;
        public void initialize(Class<CourseDetails> wsResultClass, BbWsParams params, CourseDetails recordInput,
            String dataAccessPackClassName, String innerDAPDefaultClassName, CourseDetails inputTargetCourseRecord,
            CourseCopyParams courseCopyParams) {
                super.initialize(wsResultClass, params, recordInput, dataAccessPackClassName, innerDAPDefaultClassName,
                        inputTargetCourseRecord);
                this.courseCopyParams  = courseCopyParams;
        }
*/

        setBbFieldsMethod = genSetFieldsMethod("setBbFields");
        setWsFieldsMethod = genSetFieldsMethod("setWsFields");
        for (WsgDataRegulationStruct dr : genRegulation.dataFieldRegulationList) {
            JDefinedClass bbSetter_dclass = genBbFieldSetterClass(dr);
            JDefinedClass wsSetter_dclass = genWsFieldSetterClass(dr);
            JInvocation invoc_new = JExpr._new(bbSetter_dclass);
            JInvocation anc_inv = setBbFieldsMethod.body().invoke(invoc_new, "setBbField");
            anc_inv.arg(dr.wsFieldName);
            invoc_new = JExpr._new(wsSetter_dclass);
            anc_inv = setWsFieldsMethod.body().invoke(invoc_new, "setWsField");
            anc_inv.arg(dr.wsFieldName);
        }


        
        //GradableItemAccessPack_GB2_Gen
        genRegulation.codeModel.build(new File("./generated"));
    }

    abstract class FieldToucherMethodBodiesGenerator {
        public void genGetWsFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("getArgs().getInputRecord()." + dr.getWsGetMethodName() + "()");
            method.body()._return(ret_expr);
        }

        public void genSetWsFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            //JExpression ret_expr = JExpr.direct("getArgs().getResultRecord()." + dr.getWsSetMethodName() + "(newValue)");
            //method.body()._return(ret_expr);
            method.body().invoke(JExpr.direct("getArgs().getResultRecord()"), dr.getWsSetMethodName())
                    .arg(JExpr.ref("newValue"));
            
        }

        public void genGetBbFieldValueWarn(WsgDataRegulationStruct dr, JMethod method) {
            JInvocation invoc = JExpr.invoke("addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN, " + "\"" + dr.wsFieldName + "\", null, null, null, \"getBbField() Bb GET method is not available for this field.\")");
            JExpression ret_expr = JExpr._null();
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldValueWarn(WsgDataRegulationStruct dr, JMethod method) {
            JInvocation invoc = JExpr.invoke("addDataLog(getArgs().getResultRecord(), BbWsArguments.DataLogSeverity.WARN, " + "\"" + dr.wsFieldName + "\", null, null, null, \"setBbField() Bb SET method is not available for this field.\")");
        }

        public abstract void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method);

        public abstract void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method);
    }

    public class FieldToucherMethodBodiesGenerator_java_lang_String extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "()");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.direct("newValue"));
            method.body().add(invoc);
        }
    }

    abstract class FieldToucherMethodBodiesGenerator_primitive extends FieldToucherMethodBodiesGenerator {

        protected abstract String convertToString(String expression);

        protected abstract String parse(String expression);

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct(convertToString("bbObject." + dr.bbGetMethodName + "()"));
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            //String bbType = genRegulation.wsIdField2BbTypeMap.get(dr.getWsFieldName());
            JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.direct(parse("newValue")));
            method.body().add(invoc);
        }
    }

    public class FieldToucherMethodBodiesGenerator_int extends FieldToucherMethodBodiesGenerator_primitive {

        protected String convertToString(String expression) {
            return "Integer.toString(" + expression + ")";
        }

        protected String parse(String expression) {
            return "Integer.parseInt(" + expression + ")";
        }
    }
    public class FieldToucherMethodBodiesGenerator_long extends FieldToucherMethodBodiesGenerator_primitive {

        protected String convertToString(String expression) {
            return "Long.toString(" + expression + ")";
        }

        protected String parse(String expression) {
            return "Long.parseInt(" + expression + ")";
        }
    }

    public class FieldToucherMethodBodiesGenerator_float extends FieldToucherMethodBodiesGenerator_primitive {

        protected String convertToString(String expression) {
            return "Float.toString(" + expression + ")";
        }

        protected String parse(String expression) {
            return "Float.parseFloat(" + expression + ")";
        }
    }

    public class FieldToucherMethodBodiesGenerator_double extends FieldToucherMethodBodiesGenerator_primitive {

        protected String convertToString(String expression) {
            return "Double.toString(" + expression + ")";
        }

        protected String parse(String expression) {
            return "Double.parseDouble(" + expression + ")";
        }
    }

    public class FieldToucherMethodBodiesGenerator_boolean extends FieldToucherMethodBodiesGenerator_primitive {

        protected String convertToString(String expression) {
            return "Boolean.toString(" + expression + ")";
        }

        protected String parse(String expression) {
            return "Boolean.parseBoolean(" + expression + ")";
        }
    }

    public class FieldToucherMethodBodiesGenerator_java_util_Calendar extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("extractDate(bbObject." + dr.bbGetMethodName + "())");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            String bbType = genRegulation.wsIdField2BbTypeMap.get(dr.getWsFieldName());
            JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.direct("parseDate(newValue)"));
            method.body().add(invoc);
        }
    }

    public class FieldToucherMethodBodiesGenerator_blackboard_persist_Id extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().toExternalString()");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            String s = dr.getWsFieldName();
            String bbType = genRegulation.wsIdField2BbTypeMap.get(dr.getWsFieldName());
            JInvocation invoc = method.body().invoke(JExpr.ref("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.invoke("checkAndgenerateId")
                    .arg(genRegulation.codeModel.ref(bbType).staticRef("DATA_TYPE"))
                    .arg(JExpr.ref("newValue"))
            );
        }
    }

    public class FieldToucherMethodBodiesGenerator_blackboard_base_FormattedText extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().toString()");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            String bbType = genRegulation.wsIdField2BbTypeMap.get(dr.getWsFieldName());
            JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.direct("parseFormattedText(newValue)"));
            method.body().add(invoc);
        }
    }

    public class FieldToucherMethodBodiesGenerator_blackboard_persist_RowVersion extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().getValue().toString()");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            String bbType = genRegulation.wsIdField2BbTypeMap.get(dr.getWsFieldName());
            JInvocation invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            invoc.arg(JExpr.direct("new blackboard.persist.RowVersion(Long.parseLong(newValue))"));
            method.body().add(invoc);
        }
    }

    public class FieldToucherMethodBodiesGenerator_enum extends FieldToucherMethodBodiesGenerator {

        public void genGetBbFieldValue(WsgDataRegulationStruct dr, JMethod method) {
            JExpression ret_expr = JExpr.direct("bbObject." + dr.bbGetMethodName + "().name()");
            method.body()._return(ret_expr);
        }

        public void genSetBbFieldImp(WsgDataRegulationStruct dr, JMethod method) {
            JTryBlock try_block = method.body()._try();
            JCatchBlock catch_block = try_block._catch(genRegulation.codeModel.ref(IllegalArgumentException.class));
            JVar exc_var = catch_block.param("iae");
            JInvocation new_exc_invoc = JExpr._new(genRegulation.codeModel.ref(BbWsException.class));
            JExpression new_exc_arg1_expr = JExpr.lit(genRegulation.dataClassSimpleName + "Details " + dr.wsFieldName + " may contain one of the following values: ");
            JClass arrays_jc = genRegulation.codeModel.ref(Arrays.class);
            JInvocation arr_2string = arrays_jc.staticInvoke("toString");
            JClass bbtype_jc = genRegulation.codeModel.ref(dr.paramType);
            arr_2string.arg(bbtype_jc.staticInvoke("values"));
            //new_exc_arg1_expr = new_exc_arg1_expr.plus(JExpr.direct("Arrays.toString(" + dr.bbType + ".values())"));
            new_exc_invoc.arg(new_exc_arg1_expr);
            new_exc_invoc.arg(exc_var);
            catch_block.body()._throw(new_exc_invoc);
            //try_block.body().add(JExpr.invoke("bbObject." + dr.bbSetMethodName + "("
            JInvocation set_method_invoc = JExpr.invoke(JExpr.direct("bbObject"), dr.bbSetMethodName);
            JClass enum_jc = genRegulation.codeModel.ref(Enum.class);
            JClass bbenum_jc = genRegulation.codeModel.ref(dr.paramType);
            JInvocation enum_valof_ji = enum_jc.staticInvoke("valueOf")
                    .arg(bbenum_jc.dotclass())
                    .arg(JExpr.ref("newValue"));
            set_method_invoc.arg(enum_valof_ji);
            try_block.body().add(set_method_invoc);
        }
    }

}
