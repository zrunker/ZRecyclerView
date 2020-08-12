package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * RecyclerView头部点击监听
 *
 * @author 邹峰立
 */
public interface RvHeadViewClickListener {

    /**
     * 设置点击头部监听
     *
     * @param view 被点击的View
     */
    void onRvHeadViewClick(@NonNull View view);
}
