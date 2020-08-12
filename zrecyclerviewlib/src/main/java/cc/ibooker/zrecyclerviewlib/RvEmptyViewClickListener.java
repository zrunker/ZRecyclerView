package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * RecyclerView空页面点击监听
 *
 * @author 邹峰立
 */
public interface RvEmptyViewClickListener {

    /**
     * 设置点击空页面监听
     *
     * @param view 被点击的View
     */
    void onRvEmptyViewClick(@NonNull View view);
}
