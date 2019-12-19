package cc.ibooker.zrecyclerviewlib.example.empty;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cc.ibooker.zrecyclerviewlib.BaseRvEmptyView;
import cc.ibooker.zrecyclerviewlib.R;

/**
 * 自定义空界面
 *
 * @author 邹峰立
 */
public class EmptyView extends BaseRvEmptyView<EmptyData> {
    private ImageView statueIv;
    private TextView titleTv, descTv;

    public EmptyView(Context context, EmptyData data) {
        super(context, data);
    }

    @Override
    public View createEmptyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.zrecyclerview_layout_empty, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        statueIv = view.findViewById(R.id.iv_statue);
        titleTv = view.findViewById(R.id.tv_title);
        descTv = view.findViewById(R.id.tv_desc);
        return view;
    }

    @Override
    public void refreshEmptyView(EmptyData data) {
        if (data != null) {
            EmptyEnum emptyEnum = data.getEmptyEnum();
            statueIv.setImageResource(data.getRes());
            descTv.setText(data.getDesc());
            String title = data.getTitle();
            if (TextUtils.isEmpty(title))
                switch (emptyEnum) {
                    case STATUE_DEFAULT:// 默认状态
                        titleTv.setText("默认状态");
                        break;
                    case STATUE_HIDDEN:// 隐藏状态
                        titleTv.setText("隐藏状态");
                        break;
                    case STATUE_FAILED:// 失败状态
                        titleTv.setText("失败状态");
                        break;
                    case STATUE_ERROR:// 错误状态
                        titleTv.setText("错误状态");
                        break;
                }
            else
                titleTv.setText(data.getTitle());
        }
    }
}
