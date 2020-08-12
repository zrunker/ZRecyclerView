package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * RecyclerView底部点击监听
 *
 * @author 邹峰立
 */
public interface RvFooterViewClickListener {

    /**
     * 设置点击底部监听
     *
     * @param view 被点击的View
     */
    void onRvFooterViewClick(@NonNull View view);
}
