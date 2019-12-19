package cc.ibooker.zrecyclerviewlib.example.footer;

/**
 * 底部数据
 *
 * @author 邹峰立
 */
public class FooterData {
    private boolean isShowFooter;// 是否显示底部布局
    private boolean isShowProgressBar;// 是否显示进度条
    private String title;// 显示的文字
    private RvFooterViewStatue rvFooterViewStatue;

    public FooterData() {
        super();
    }

    public FooterData(RvFooterViewStatue rvFooterViewStatue) {
        this.rvFooterViewStatue = rvFooterViewStatue;
    }

    public FooterData(boolean isShowFooter, boolean isShowProgressBar, String title, RvFooterViewStatue rvFooterViewStatue) {
        this.isShowFooter = isShowFooter;
        this.isShowProgressBar = isShowProgressBar;
        this.title = title;
        this.rvFooterViewStatue = rvFooterViewStatue;
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

    public RvFooterViewStatue getRvFooterViewStatue() {
        return rvFooterViewStatue;
    }

    public void setRvFooterViewStatue(RvFooterViewStatue rvFooterViewStatue) {
        this.rvFooterViewStatue = rvFooterViewStatue;
    }

    @Override
    public String toString() {
        return "FooterData{" +
                "isShowFooter=" + isShowFooter +
                ", isShowProgressBar=" + isShowProgressBar +
                ", title='" + title + '\'' +
                ", rvFooterViewStatue=" + rvFooterViewStatue +
                '}';
    }
}