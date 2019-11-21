package cc.ibooker.zrecyclerview.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerviewlib.BaseRvEmptyView;

/**
 * 自定义空界面
 *
 * @author 邹峰立
 */
public class EmptyView extends BaseRvEmptyView<EmptyData> {
    private TextView tipTv;

    public EmptyView(Context context, EmptyData data) {
        super(context, data);
    }

    @Override
    public View createEmptyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_empty, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tipTv = view.findViewById(R.id.tv_tip);
        return view;
    }

    @Override
    public void refreshEmptyView(EmptyData data) {
        if (data != null) {
            EmptyEnum emptyEnum = data.getEmptyEnum();
            switch (emptyEnum) {
                case STATUE_DEFAULT:
                    tipTv.setText("默认状态");
                    break;
                case STATUE_HIDDEN:
                    tipTv.setText("隐藏状态");
                    break;
                case STATUE_FAILED:
                    tipTv.setText("失败状态");
                    break;
                case STATUE_ERROR:
                    tipTv.setText("错误状态");
                    break;
            }
        }
    }
}
