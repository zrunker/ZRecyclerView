package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.view.View;

/**
 * 头部View基类
 *
 * @author 邹峰立
 */
public abstract class BaseRvHeadView<T> {
    private View headView;
    private T headData;
    private Context context;

    public BaseRvHeadView(Context context, T data) {
        this.context = context;
        this.headView = createHeadView(context);
        this.headData = data;
    }

    /**
     * 创建头部View
     *
     * @param context 上下文对象
     */
    public abstract View createHeadView(Context context);

    /**
     * 刷新头部数据
     *
     * @param data 待刷新数据
     */
    public abstract void refreshHeadView(T data);

    public T getHeadData() {
        return headData;
    }

    public View getHeadView() {
        return headView;
    }

    public Context getContext() {
        return context;
    }
}
