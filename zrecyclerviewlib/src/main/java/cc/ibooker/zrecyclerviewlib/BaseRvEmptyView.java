package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.view.View;

/**
 * 空页面基类
 *
 * @author 邹峰立
 */
public abstract class BaseRvEmptyView<T> {
    private View emptyView;
    private T emptyData;

    public BaseRvEmptyView(Context context, T data) {
        this.emptyView = createEmptyView(context);
        this.emptyData = data;
    }

    // 创建空界面
    public abstract View createEmptyView(Context context);

    public abstract void refreshEmptyView(T data);

    public T getEmptyData() {
        return emptyData;
    }

    public View getEmptyView() {
        return emptyView;
    }
}
