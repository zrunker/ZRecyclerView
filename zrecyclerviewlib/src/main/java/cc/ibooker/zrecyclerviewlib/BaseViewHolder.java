package cc.ibooker.zrecyclerviewlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ViewHolder基类
 *
 * @author 邹峰立
 */
public class BaseViewHolder<V extends View, T> extends RecyclerView.ViewHolder {
    private V mItemView;
    private T mData;
    protected RvItemDiyCViewClickListener rvItemDiyCViewClickListener;
    protected RvItemDiyLongCViewClickListener rvItemDiyLongCViewClickListener;

    public BaseViewHolder setRvItemDiyCViewClickListener(RvItemDiyCViewClickListener rvItemDiyCViewClickListener) {
        this.rvItemDiyCViewClickListener = rvItemDiyCViewClickListener;
        return this;
    }

    public BaseViewHolder setRvItemDiyLongCViewClickListener(RvItemDiyLongCViewClickListener rvItemDiyLongCViewClickListener) {
        this.rvItemDiyLongCViewClickListener = rvItemDiyLongCViewClickListener;
        return this;
    }

    public BaseViewHolder(V itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public V getItemView() {
        return mItemView;
    }

    public void onBind(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }
}
