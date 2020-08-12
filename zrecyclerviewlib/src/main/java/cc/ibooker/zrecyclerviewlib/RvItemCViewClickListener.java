package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * RecycleView单项点击事件
 *
 * @author 邹峰立
 */
public interface RvItemCViewClickListener {

    /**
     * 点击事件
     *
     * @param position     被点击项
     * @param realPosition 数据列表位置
     */
    void onRvItemCViewClick(@NonNull View view, int position, int realPosition);
}
