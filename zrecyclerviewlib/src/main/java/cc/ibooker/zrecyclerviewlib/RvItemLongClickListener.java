package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
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
     * @param position     被点击项
     * @param realPosition 数据列表位置
     */
    void onRvItemLongClick(@NonNull View view, int position, int realPosition);
}
