package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.view.View;

/**
 * 底部View基类
 *
 * @author 邹峰立
 */
public abstract class BaseRvFooterView<T> {
    private View footerView;
    private T footerData;
    private Context context;

    public BaseRvFooterView(Context context, T data) {
        this.context = context;
        this.footerView = createFooterView(context);
        this.footerData = data;
    }

    /**
     * 创建底部View
     *
     * @param context 上下文对象
     */
    public abstract View createFooterView(Context context);

    /**
     * 刷新底部数据
     *
     * @param data 待刷新数据
     */
    public abstract void refreshFooterView(T data);

    public T getFooterData() {
        return footerData;
    }

    public View getFooterView() {
        return footerView;
    }

    public Context getContext() {
        return context;
    }
}
