package cc.ibooker.zrecyclerviewlib;

import android.view.View;

/**
 * 列表item点击事件监听
 *
 * @author 邹峰立
 */
public interface MyRvItemClickListener<T> extends RvItemDiyCViewClickListener {

    /**
     * 点击事件处理
     *
     * @param view     待处理view
     * @param data     待处理数据
     * @param position 待处理项
     */
    void onViewClick(View view, T data, int position);

}
