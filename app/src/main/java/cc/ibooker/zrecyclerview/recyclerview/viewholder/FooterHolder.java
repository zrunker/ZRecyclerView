package cc.ibooker.zrecyclerview.recyclerview.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerview.bean.FooterData;
import cc.ibooker.zrecyclerview.utils.ClickUtil;

/**
 * RecyclerView底部ViewHolder
 * Created by 邹峰立 on 2017/4/30 0030.
 */
public class FooterHolder extends RecyclerView.ViewHolder {
    private Context context;
    private LinearLayout footerLayout;
    private ProgressBar progressBar;
    private TextView textView;

    public FooterHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.footerLayout = itemView.findViewById(R.id.load_layout);
        this.progressBar = itemView.findViewById(R.id.footer_progressbar);
        this.textView = itemView.findViewById(R.id.footer_tip);
    }

    public void bindHolder(FooterData footerData) {
        if (footerData != null) {
            if (footerData.isShowFooter()) {
                footerLayout.setVisibility(View.VISIBLE);
                if (footerData.isShowProgressBar())
                    progressBar.setVisibility(View.VISIBLE);
                else
                    progressBar.setVisibility(View.GONE);
                textView.setText(footerData.getTitle());
                footerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 防止连续点击
                        if (ClickUtil.isFastClick()) {
                            return;
                        }
                        Toast.makeText(context, "发送通讯", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                footerLayout.setVisibility(View.GONE);
            }

        }
    }
}
