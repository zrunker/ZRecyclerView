package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * 自定义监听RecycleView单项长按事件
 *
 * @author 邹峰立
 */
public interface RvItemDiyLongCViewClickListener {
    /**
     * 长按事件
     */
    void onRvItemCViewLongClick(@NonNull View view);
}
