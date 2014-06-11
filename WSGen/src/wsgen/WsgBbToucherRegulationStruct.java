package wsgen;

public class WsgBbToucherRegulationStruct {

    private String wsFieldName;

    public WsgBbToucherRegulationStruct(String wsFieldName) {
        super();
        this.wsFieldName = WsgUtil.firstLetterLCase(wsFieldName);
    }

    public String getFieldNameLCase() {
        String res = WsgUtil.firstLetterLCase(wsFieldName);
        return res;
    }
}
