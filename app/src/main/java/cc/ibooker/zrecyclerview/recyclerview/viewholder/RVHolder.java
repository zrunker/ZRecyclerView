package cc.ibooker.zrecyclerview.recyclerview.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerview.bean.RvData;

/**
 * RecyclerView的ViewHolder
 * Created by 邹峰立 on 2017/10/25.
 */
public class RVHolder extends RecyclerView.ViewHolder {
    private Context context;
    private LinearLayout layout;
    private TextView titleTv, descTv;

    public RVHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.layout = itemView.findViewById(R.id.layout);
        this.titleTv = itemView.findViewById(R.id.tv_title);
        this.descTv = itemView.findViewById(R.id.tv_desc);
    }

    public void bindHolder(RvData rvData, final int position) {
        if (rvData != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击" + position, Toast.LENGTH_SHORT).show();
                }
            });
            titleTv.setText(rvData.getTitle());
            descTv.setText(rvData.getDesc());
        }
    }
}
