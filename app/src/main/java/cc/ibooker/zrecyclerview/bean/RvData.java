package cc.ibooker.zrecyclerview.bean;

/**
 * 模拟RecyclerView数据
 * Created by 邹峰立 on 2017/10/25.
 */
public class RvData {
    private String title;
    private String desc;
    // 根据业务需求添加
    private int type;

    public RvData() {
        super();
    }

    public RvData(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public RvData(String title, String desc, int type) {
        this.title = title;
        this.desc = desc;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RvData{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }
}

