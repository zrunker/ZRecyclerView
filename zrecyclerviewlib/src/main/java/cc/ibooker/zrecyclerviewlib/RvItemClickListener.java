package cc.ibooker.zrecyclerviewlib;

import android.view.View;

/**
 * RecycleView单项点击事件
 *
 * @author 邹峰立
 */
public interface RvItemClickListener {

    /**
     * 点击事件
     *
     * @param position 被点击项
     */
    void onRvItemClick(View view, int position);
}
