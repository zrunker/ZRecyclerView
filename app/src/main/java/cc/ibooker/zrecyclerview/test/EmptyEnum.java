package cc.ibooker.zrecyclerview.test;

/**
 * 空数据状态枚举
 *
 * @author 邹峰立
 */
public enum EmptyEnum {
    STATUE_HIDDEN(0),// 隐藏状态
    STATUE_DEFAULT(1),// 默认状态
    STATUE_FAILED(2),// 错误状态
    STATUE_ERROR(3);// 异常状态

    private int statue;

    EmptyEnum(int statue) {
        this.statue = statue;
    }

    public int getStatue() {
        return statue;
    }
}
