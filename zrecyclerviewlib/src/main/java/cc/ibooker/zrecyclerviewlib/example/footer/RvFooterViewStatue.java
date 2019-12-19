package cc.ibooker.zrecyclerviewlib.example.footer;

/**
 * 底部View状态枚举类
 *
 * @author 邹峰立
 */
public enum RvFooterViewStatue {
    STATUE_HIDDEN(0),// 隐藏状态
    STATUE_DEFAULT(1),// 默认状态
    STATUE_LOADING(2),// 正在加载状态
    STATUE_LOADED(3);// 加载完成状态

    private int statue;

    RvFooterViewStatue(int statue) {
        this.statue = statue;
    }

    public int getStatue() {
        return statue;
    }
}