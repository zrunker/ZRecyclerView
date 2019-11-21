package cc.ibooker.zrecyclerview.test;

/**
 * 空数据
 *
 * @author 邹峰立
 */
public class EmptyData {
    private String tip;
    private EmptyEnum emptyEnum;

    public EmptyData(String tip, EmptyEnum emptyEnum) {
        this.tip = tip;
        this.emptyEnum = emptyEnum;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public EmptyEnum getEmptyEnum() {
        return emptyEnum;
    }

    public void setEmptyEnum(EmptyEnum emptyEnum) {
        this.emptyEnum = emptyEnum;
    }
}
