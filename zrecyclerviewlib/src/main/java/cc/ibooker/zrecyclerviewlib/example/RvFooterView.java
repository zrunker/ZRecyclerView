package cc.ibooker.zrecyclerviewlib.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cc.ibooker.zrecyclerviewlib.BaseRvFooterView;
import cc.ibooker.zrecyclerviewlib.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * RecycleView底部View
 *
 * @author 邹峰立
 */
public class RvFooterView extends BaseRvFooterView<FooterData> {
    private ProgressBar progressBar;
    private TextView textView;
    private FooterData footerData;

    public RvFooterView(Context context, FooterData data) {
        super(context, data);
    }

    // 刷新View状态
    private RvFooterView refreshStatue(RvFooterViewStatue rvFooterViewStatue) {
        if (footerData == null)
            footerData = new FooterData();
        switch (rvFooterViewStatue) {
            case STATUE_HIDDEN:// 隐藏状态
                footerData.setShowFooter(false);
                refreshView();
                break;
            case STATUE_DEFAULT:// 默认状态
                footerData.setShowFooter(true);
                footerData.setShowProgressBar(false);
                footerData.setTitle(getContext().getResources().getString(R.string.load_more_before));
                refreshView();
                break;
            case STATUE_LOADING:// 加载中
                footerData.setShowFooter(true);
                footerData.setShowProgressBar(true);
                footerData.setTitle(getContext().getResources().getString(R.string.load_more));
                refreshView();
                break;
            case STATUE_LOADED:// 加载完成
                footerData.setShowFooter(true);
                footerData.setShowProgressBar(true);
                footerData.setTitle(getContext().getResources().getString(R.string.load_more_complete));
                refreshView();
                break;
        }
        return this;
    }

    // 刷新View
    private RvFooterView refreshView() {
        if (footerData != null && footerData.isShowFooter()) {
            getFooterView().setVisibility(VISIBLE);
            progressBar.setVisibility(footerData.isShowProgressBar() ? VISIBLE : GONE);
            textView.setText(footerData.getTitle());
        } else
            getFooterView().setVisibility(GONE);
        return this;
    }

    @Override
    public View createFooterView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.zrecyclerview_layout_footer, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar = view.findViewById(R.id.footer_progressbar);
        textView = view.findViewById(R.id.footer_tip);
        return view;
    }

    @Override
    public void refreshFooterView(FooterData data) {
        if (data != null)
            refreshStatue(data.getRvFooterViewStatue());
    }
}
