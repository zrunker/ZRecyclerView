package cc.ibooker.zrecyclerview.bean;

/**
 * 底部数据
 * Created by 邹峰立 on 2017/10/25.
 */
public class FooterData {
    private boolean isShowFooter;// 是否显示底部布局
    private boolean isShowProgressBar;// 是否显示进度条
    private String title;// 显示的文字

    public FooterData() {
        super();
    }

    public FooterData(boolean isShowFooter, boolean isShowProgressBar, String title) {
        this.isShowFooter = isShowFooter;
        this.isShowProgressBar = isShowProgressBar;
        this.title = title;
    }

    public boolean isShowFooter() {
        return isShowFooter;
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
    }

    public boolean isShowProgressBar() {
        return isShowProgressBar;
    }

    public void setShowProgressBar(boolean showProgressBar) {
        isShowProgressBar = showProgressBar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
