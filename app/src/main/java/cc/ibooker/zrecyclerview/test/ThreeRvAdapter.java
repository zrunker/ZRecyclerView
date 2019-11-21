package cc.ibooker.zrecyclerview.test;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerviewlib.BaseRvAdapter;
import cc.ibooker.zrecyclerviewlib.BaseViewHolder;

public class ThreeRvAdapter extends BaseRvAdapter<ThreeBean> {
    @Override
    public BaseViewHolder onCreateItemViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ThreeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_item, viewGroup, false));
    }

    @Override
    public void onBindItemViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        ((ThreeViewHolder) baseViewHolder).onBind(getData().get(position));
    }
}
