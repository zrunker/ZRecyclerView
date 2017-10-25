package cc.ibooker.zrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerview.bean.FooterData;
import cc.ibooker.zrecyclerview.bean.RvData;
import cc.ibooker.zrecyclerview.recyclerview.viewholder.FooterHolder;
import cc.ibooker.zrecyclerview.recyclerview.viewholder.RVHolder;

/**
 * RecyclerView适配器
 * Created by 邹峰立 on 2017/10/25.
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ONE = 0, TYPE_FOOT = 1;
    private ArrayList<RvData> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    private FooterData footerData;

    public RvAdapter(Context context, ArrayList<RvData> list, FooterData footerData) {
        this.mDatas = list;
        this.footerData = footerData;
        this.mInflater = LayoutInflater.from(context);
    }

    // 刷新全部数据
    public void reflushData(ArrayList<RvData> list, FooterData footerData) {
        this.mDatas = list;
        this.footerData = footerData;
        this.notifyDataSetChanged();
    }

    // 刷新列表数据
    public void reflushList(ArrayList<RvData> list) {
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    // 刷新底部
    public void reflushFooterData(FooterData footerData) {
        this.footerData = footerData;
        this.notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                holder = new RVHolder(mInflater.inflate(R.layout.activity_main_item, parent, false));
                break;
            case TYPE_FOOT:// 底部
                holder = new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ONE:
                ((RVHolder) holder).bindHolder(mDatas.get(position), position);
                break;
            case TYPE_FOOT:// 底部
                ((FooterHolder) holder).bindHolder(footerData);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + (footerData == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDatas.size())
            return mDatas.get(position).getType();
        else
            return TYPE_FOOT;
    }
}
