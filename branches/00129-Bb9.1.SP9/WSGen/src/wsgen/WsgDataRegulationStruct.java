package wsgen;

import bbwscommon.BbWsUtil;
//* import javassist.ClassPool;
//* import javassist.CtClass;
import org.apache.commons.lang3.StringUtils;

public class WsgDataRegulationStruct {

    WsgRegulation genRegulation;
    WsgDataRegulationStruct(WsgRegulation genRegulation, WsgDataAccessPackGenerator dapg, WsgBbTouchersLink dal) throws Exception {
        super();
        this.genRegulation = genRegulation;
        WsgBbGetterRegulationStruct dgr = genRegulation.bbGetter2WSFieldMap.get(dal.dataGetterSignature);
        WsgBbSetterRegulationStruct dsr = genRegulation.bbSetter2WSFieldMap.get(dal.dataSetterSignature);
        this.wsFieldName = dgr.getFieldNameLCase();
        this.boolPrefix = WsgUtil.firstLetterLCase(dgr.boolPrefix);
        this.dataGetterSignature = dal.dataGetterSignature;
        this.dataSetterSignature = dal.dataSetterSignature;
        this.bbGetMethodName = dgr.bbGetMethodName;
        this.bbSetMethodName = dsr.bbSetMethodName;
        this.returnType = dgr.returnType;
        this.paramType = dsr.paramType;

        /*if (!this.bbType.equals(dsr.paramType)) {
            throw new Exception("ASSERT: if (!this.bbType.equals(dsr.paramType) {");
        }*/
        try {
        returnTypeClass = WsgUtil.getClassForName(this.returnType);
        } catch (Throwable e) {
            throw new Exception(e);
        }
        //* paramTypeClass = ClassPool.getDefault().get(this.paramType);
        paramTypeClass = WsgUtil.getClassForName(this.paramType);

        String get_body_gen_class_name = null;
/*
        //boolean is_enum = bbTypeClass.getClass().isEnum();
        boolean is_enum = bbTypeClass.isEnum();
        String base_name = null;
        if (bbTypeClass.getSuperclass() != null) {
            base_name = bbTypeClass.getSuperclass().getName();
            is_enum = bbTypeClass.getSuperclass().getClass().isEnum();
        } else {
            base_name = bbTypeClass.getName();
        }
        if (bbTypeClass.getSuperclass() != null && bbTypeClass.getSuperclass().getName().equals("java.lang.Enum")) {
            body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_enum";
        } else {
            body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_" + bbType.replace(".", "_");
        }
 */
        if (returnTypeClass.isEnum()) {
            get_body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_enum";
        } else {
            get_body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_" + returnType.replace(".", "_");
        }

        Class get_body_gen_class = WsgUtil.getClassForName(get_body_gen_class_name);
       
        //Class<WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator> body_gen_class
                //= WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.forName(body_gen_class_name);
                //= (Class<WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator>)Class.forName(body_gen_class_name);
        //toucherMethodBodiesGenerator = BbWsUtil.<WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator>newClassInstanceWithThrow(body_gen_class);
        //toucherMethodBodiesGenerator = WsgUtil.<WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator>newClassInstanceWithThrow(body_gen_class);
        //toucherMethodBodiesGenerator = WsgUtil.<WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator>
        getterMethodBodiesGenerator = //WsgUtil.newClassInstanceWithThrow(body_gen_class, toucherMethodBodiesGenerator.getClass());
                            WsgUtil.newClassInstanceWithThrow(get_body_gen_class, WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class, dapg);
                            //newClassInstanceWithThrow(body_gen_class, toucherMethodBodiesGenerator.getClass());

        String set_body_gen_class_name = null;
        if (paramTypeClass.isEnum()) {
            set_body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_enum";
        } else {
            set_body_gen_class_name = WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class.getName() + "_" + paramType.replace(".", "_");
        }
        Class set_body_gen_class = WsgUtil.getClassForName(set_body_gen_class_name);
        setterMethodBodiesGenerator = //WsgUtil.newClassInstanceWithThrow(body_gen_class, toucherMethodBodiesGenerator.getClass());
                            WsgUtil.newClassInstanceWithThrow(set_body_gen_class, WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator.class, dapg);

        
    }
    public String wsFieldName;
    public String boolPrefix;
    public String dataGetterSignature;
    public String dataSetterSignature;
    public String bbGetMethodName;
    public String bbSetMethodName;
    public String returnType;
    public String paramType;
    //* public CtClass returnTypeClass;
    public Class returnTypeClass;
    //* public CtClass paramTypeClass;
    public Class paramTypeClass;
    public WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator getterMethodBodiesGenerator;
    public WsgDataAccessPackGenerator.FieldToucherMethodBodiesGenerator setterMethodBodiesGenerator;

    private String getBaseMethodName() {
        if (StringUtils.equals("", boolPrefix)) {
            return WsgUtil.firstLetterUCase(wsFieldName);
        } else {
            return WsgUtil.firstLetterUCase(boolPrefix) + WsgUtil.firstLetterUCase(wsFieldName);
        }
    }

    public String getWsGetMethodName() {
        return "get" + getBaseMethodName();
    }

    public String getWsSetMethodName() {
        return "set" + getBaseMethodName();
    }

    public String getWsFieldName() {
        if (StringUtils.equals("", boolPrefix)) {
            return WsgUtil.firstLetterLCase(wsFieldName);
        } else {
            return WsgUtil.firstLetterLCase(boolPrefix) + WsgUtil.firstLetterUCase(wsFieldName);
        }
    }
}
