package cc.ibooker.zrecyclerviewlib;

import android.view.View;

/**
 * RecycleView单项长按事件
 *
 * @author 邹峰立
 */
public interface RvItemLongClickListener {
    /**
     * 长按事件
     *
     * @param position 被点击项
     */
    void onRvItemLongClick(View view, int position);
}
