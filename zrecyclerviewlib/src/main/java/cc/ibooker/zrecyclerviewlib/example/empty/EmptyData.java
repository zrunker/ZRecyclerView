package cc.ibooker.zrecyclerviewlib.example.empty;

/**
 * 空数据
 *
 * @author 邹峰立
 */
public class EmptyData {
    private int res;
    private String title;
    private String desc;
    private EmptyEnum emptyEnum;

    public EmptyData() {
        super();
    }

    public EmptyData(int res, String title, String desc, EmptyEnum emptyEnum) {
        this.res = res;
        this.title = title;
        this.desc = desc;
        this.emptyEnum = emptyEnum;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EmptyEnum getEmptyEnum() {
        return emptyEnum;
    }

    public void setEmptyEnum(EmptyEnum emptyEnum) {
        this.emptyEnum = emptyEnum;
    }

    @Override
    public String toString() {
        return "EmptyData{" +
                "res=" + res +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", emptyEnum=" + emptyEnum +
                '}';
    }
}
