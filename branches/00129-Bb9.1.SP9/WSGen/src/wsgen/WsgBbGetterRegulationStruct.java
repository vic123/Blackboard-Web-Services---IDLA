package wsgen;

public class WsgBbGetterRegulationStruct extends WsgBbToucherRegulationStruct {

    public WsgBbGetterRegulationStruct(String bbGetMethodName, String boolPrefix, String wsFieldName, String returnType) {
        super(wsFieldName);
        this.boolPrefix = boolPrefix;
        this.bbGetMethodName = bbGetMethodName;
        this.returnType = returnType;
    }

    String getBoolPrefixLCase() {
        return WsgUtil.firstLetterLCase(boolPrefix);
    }

    String getBoolPrefixUCase() {
        return WsgUtil.firstLetterUCase(boolPrefix);
    }
    public String bbGetMethodName;
    public String returnType;
    public String boolPrefix;
}
