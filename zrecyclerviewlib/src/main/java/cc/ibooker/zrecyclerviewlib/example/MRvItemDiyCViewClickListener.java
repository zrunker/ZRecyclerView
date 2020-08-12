package cc.ibooker.zrecyclerviewlib.example;

import android.support.annotation.NonNull;
import android.view.View;

import cc.ibooker.zrecyclerviewlib.RvItemDiyCViewClickListener;

/**
 * 自定义点击事件
 *
 * @author 邹峰立
 */
public interface MRvItemDiyCViewClickListener extends RvItemDiyCViewClickListener {

    /**
     * 点击事件
     *
     * @param position     被点击项
     * @param realPosition 数据列表位置
     */
    void onRvItemCViewClick(@NonNull View view, int position, int realPosition);
}
