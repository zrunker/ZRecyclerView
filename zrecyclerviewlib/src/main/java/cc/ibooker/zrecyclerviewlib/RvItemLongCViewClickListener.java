package cc.ibooker.zrecyclerviewlib;

import android.view.View;

/**
 * RecycleView单项长按事件
 *
 * @author 邹峰立
 */
public interface RvItemLongCViewClickListener {
    /**
     * 长按事件
     *
     * @param position     被点击项
     * @param realPosition 数据列表位置
     */
    void onRvItemCViewLongClick(View view, int position, int realPosition);
}
