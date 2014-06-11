package wsgen;

public class WsgBbSetterRegulationStruct extends WsgBbToucherRegulationStruct {

    public WsgBbSetterRegulationStruct(String bbSetMethodName, String wsFieldName, String paramType) {
        super(wsFieldName);
        this.bbSetMethodName = bbSetMethodName;
        this.paramType = paramType;
    }
    public String bbSetMethodName;
    public String paramType;
}
