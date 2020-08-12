package cc.ibooker.zrecyclerviewlib.example;

import android.support.annotation.NonNull;
import android.view.View;

import cc.ibooker.zrecyclerviewlib.RvItemDiyLongCViewClickListener;

/**
 * 自定义长按事件
 *
 * @author 邹峰立
 */
public interface MRvItemDiyLongCViewClickListener extends RvItemDiyLongCViewClickListener {
    /**
     * 长按事件
     *
     * @param position     被点击项
     * @param realPosition 数据列表位置
     */
    void onRvItemCViewLongClick(@NonNull View view, int position, int realPosition);
}
