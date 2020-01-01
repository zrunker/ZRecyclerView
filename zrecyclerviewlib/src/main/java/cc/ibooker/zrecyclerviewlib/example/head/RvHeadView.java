package cc.ibooker.zrecyclerviewlib.example.head;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.ibooker.zrecyclerviewlib.BaseRvHeadView;
import cc.ibooker.zrecyclerviewlib.R;

/**
 * RecycleView顶部View
 *
 * @author 邹峰立
 */
public class RvHeadView extends BaseRvHeadView<HeadData> {
    private TextView titleTv;

    public RvHeadView(Context context, HeadData data) {
        super(context, data);
    }

    @Override
    public View createHeadView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.zrecyclerview_layout_head, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        titleTv = view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshHeadView(HeadData data) {
        if (data != null)
            titleTv.setText(data.getTitle());
    }
}
