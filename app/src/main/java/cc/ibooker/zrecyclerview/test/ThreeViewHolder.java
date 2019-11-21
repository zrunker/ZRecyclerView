package cc.ibooker.zrecyclerview.test;

import android.view.View;
import android.widget.TextView;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerviewlib.BaseViewHolder;

public class ThreeViewHolder extends BaseViewHolder<View, ThreeBean> {
    private TextView tipTv;

    public ThreeViewHolder(View itemView) {
        super(itemView);
        tipTv = itemView.findViewById(R.id.tv_title);
    }

    @Override
    public void onBind(ThreeBean data) {
        super.onBind(data);
        if (data != null)
            tipTv.setText(data.getTitle());
    }
}
