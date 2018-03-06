# ZRecyclerView
Android中RecyclerView的使用，同时实现下拉刷新和加载更多的功能。

>作者：邹峰立，微博：zrunker，邮箱：zrunker@yahoo.com，微信公众号：书客创作，个人平台：[www.ibooker.cc](http://www.ibooker.cc)。

>本文选自[书客创作](http://www.ibooker.cc)平台第70篇文章。[阅读原文](http://www.ibooker.cc/article/70/detail) 。

![书客创作](http://upload-images.jianshu.io/upload_images/3480018-c2e916291af18731..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

RecyclerView是Android L版本中新添加的控件，它的灵活性、可替代性、回收机制等比listview更好。可能对于一些使用过RecyclerView的开发者会说，RecyclerView是用来替代ListView的。说RecyclerView是用来代替ListView就太严重了，不过确实是一个不错的控件。

要想引用recyclerview，需要在build.gradle中引入相应的依赖：
```
compile 'com.android.support:recyclerview-v7:26.1.0'
```

RecyclerView这是用来显示列表数据，但是不具备下拉刷新和加载更多功能。那么如何去实现这些功能呢？

### 下拉刷新分析

下拉刷新是指当布局下拉到顶部的时，要对该界面或者数据进行刷新。一般在下拉刷新时，会有相应的进度条等效果显示。RecyclerView不像ListView那样可以直接添加Header，通过操作Header来实现下拉刷新的效果。但是可以通过RecyclerView的父控件，在父控件中添加相应的下拉刷新的效果。

基于Android Material Design，可以使用SwipeRefreshLayout实现RecyclerView的下拉刷新。

SwipeRefreshLayout使用起来有一个小缺陷，就是无法直接实现下拉刷新效果，只能手动下拉之后才能显示下拉刷新的效果，为了解决这个问题需要对SwipeRefreshLayout进行一些小的修改。这里通过继承SwipeRefreshLayout，添加自动刷新方法autoRefresh，动态修改和调用SwipeRefreshLayout相关属性或方法。

```
/**
 * 自定义自动下拉刷新SwipeRefreshLayout
 * Created by 邹峰立 on 2017/5/10.
 */
public class AutoSwipeRefreshLayout extends SwipeRefreshLayout {
    public AutoSwipeRefreshLayout(Context context) {
        super(context);
    }

    public AutoSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 加载更多分析

对于RecyclerView而言，要实现加载更多功能，需要利用RecyclerView.OnScrollListener事件。在OnScrollListener事件提供的onScrollStateChanged进行判断并执行加载更多。

判断条件：
1、可见Item的数量大于0。
2、当前处于滚动停止状态。
3、最后一个可见项大于或等于Item的总数（滚动到最底部）。

另外还需要在OnScrollListener事件提供的onScrolled方法中记录滚动的距离和最后一个可见项的位置。这是提供onScrollStateChanged进行判断的依据。

综上分析，可以将RecyclerView.OnScrollListener事件进行封装，并对外提供接口（执行加载更多接口）。
```
/**
 * RecyclerView加载更多滚动事件
 * Created by 邹峰立 on 2017/4/30 0030.
 */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * layoutManager的类型（枚举）
     */
    private LAYOUT_MANAGER_TYPE layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 是否正在加载
     */
    private boolean isLoadingMore = false;

    /**
     * 记录y轴滚动距离（>0代表向下滚动，<0代表向上滚动）
     */
    private int dy;

    private int totalDy = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 记录y轴滚动距离（>0代表向下滚动）
        this.dy = dy;
        this.totalDy += dy;
        // 获取RecyclerView布局管理器
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        // 最后一个可见的item的位置
        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

        /**
         * 设置滚动距离事件
         */
        if (onScrollDistanceListener != null) {
            onScrollDistanceListener.onScrollDistance(totalDy);
        }

    }

    // 滚动状态改变
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        // 获取RecyclerView布局管理器
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        // 获取RecyclerView可见Item的数量
        int visibleItemCount = layoutManager.getChildCount();
        // 获取RecyclerView总Item的数量
        int totalItemCount = layoutManager.getItemCount();
        // 判断是否需要加载更多。1、可见性大于0。2、当前处于滚动停止状态。3、最后一个可见项大于或等于Item的总数（滚动到最底部）
        if (dy > 0 && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition >= totalItemCount - 1)) {
            /**
             * 加载更多 事件
             */
            if (!isLoadingMore && onLoadListener != null) {
                onLoadListener.onLoad();
                isLoadingMore = true;
            }
        }

        /**
         * 滚动状态改变事件
         */
        if (onScrollStateChangedListener != null) {
            onScrollStateChangedListener.onScrollStateChanged(newState);
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    // 加载更多接口
    public interface OnLoadListener {
        void onLoad();
    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    // 滚动距离接口
    public interface OnScrollDistanceListener {
        void onScrollDistance(int dy);
    }

    private OnScrollDistanceListener onScrollDistanceListener;

    public void setOnScrollDistanceListener(OnScrollDistanceListener onScrollDistanceListener) {
        this.onScrollDistanceListener = onScrollDistanceListener;
    }

    // 滚动状态改变接口
    public interface OnScrollStateChangedListener {
        void onScrollStateChanged(int newState);
    }

    private OnScrollStateChangedListener onScrollStateChangedListener;

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener onScrollStateChangedListener) {
        this.onScrollStateChangedListener = onScrollStateChangedListener;
    }
}
```
最后对RecyclerView进行再次封装，并实现加载更多相关功能。
```
/**
 * 下拉加载更多RecyclerView
 * Created by 邹峰立 on 2017/10/25.
 */
public class LoadRecyclerView extends RecyclerView {
    private RecyclerViewScrollListener rvScrollListener;

    public LoadRecyclerView(Context context) {
        this(context, null);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 初始化方法
    private void init() {
        rvScrollListener = new RecyclerViewScrollListener();
        this.addOnScrollListener(rvScrollListener);
    }

    // 设置是否加载更多
    public void setLoading(boolean bool) {
        rvScrollListener.setLoadingMore(bool);
    }

    // 加载更多
    public void setOnLoadListener(RecyclerViewScrollListener.OnLoadListener onLoadListener) {
        if (rvScrollListener != null)
            rvScrollListener.setOnLoadListener(onLoadListener);
    }
}
```
### 实现

首先看一下最终实现的效果图：

![RecyclerView下拉刷新与加载更多效果图](http://upload-images.jianshu.io/upload_images/3480018-42982f5eb588f16d..gif?imageMogr2/auto-orient/strip)

当应用程序打开的时候，进行数据加载，并显示下拉刷新的效果，加载完成之后显示数据。当下拉到底部的时候，显示加载更多效果，并加载数据，数据加载完成之后显示数据。

**一、布局**

根据上面的分析和封装的类进行页面布局。
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <cc.ibooker.zrecyclerview.view.AutoSwipeRefreshLayout
        android:id="@+id/swipe_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <cc.ibooker.zrecyclerview.view.LoadRecyclerView
            android:id="@+id/recyclerview"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </cc.ibooker.zrecyclerview.view.AutoSwipeRefreshLayout>

</RelativeLayout>
```
AutoSwipeRefreshLayout是AutoSwipeRefreshLayout的父控件，用来实现自动刷新和下拉刷新功能。LoadRecyclerView是用来实现加载更多以及呈现列表。

**二、下拉刷新实现**

实现下拉刷新效果，需要在实现类中继承SwipeRefreshLayout.OnRefreshListener，并实现下拉刷新onRefresh方法。最后让AutoSwipeRefreshLayout设置下拉刷新监听即可。

```
swipeLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_container);
swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
swipeLayout.setOnRefreshListener(this);
```
```
// 下拉刷新-一般为加载网络数据
@Override
public void onRefresh() {
    // 获取最新数据
    isAbleLoading = true;
    // 模拟网络加载
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if (mDatas == null)
                mDatas = new ArrayList<>();
            mDatas.clear();
            for (int i = 0; i < ConstantUtil.PAGE_SIZE; i++) {
                RvData rvData = new RvData();
                rvData.setTitle("标题=" + i);
                rvData.setDesc("描述=" + i);
                rvData.setType(RvAdapter.TYPE_ONE);
                mDatas.add(rvData);
            }
            setAdapter();
            // 取消加载
            swipeLayout.setRefreshing(false);
        }
    }, 3000);

    reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
}
```
为了避免下拉刷新和加载更多同时进行，所以添加一个boolean值isAbleLoading来控制是否可以加载更多。mDatas是用来保存数据的集合。当数据加载完成之后利用自定义setAdapter()方法来刷新列表，利用swipeLayout.setRefreshing(false)方法取消加载状态。每一次加载完数据都要重置底部加载更多布局，这里是利用reflashFooterView()方法来实现。

```
// 自定义setAdapter
private void setAdapter() {
    if (adapter == null) {
        adapter = new RvAdapter(this, mDatas, footerData);
        recyclerView.setAdapter(adapter);
    } else {
        adapter.reflushList(mDatas);
    }

    // 判断是否已经加载完成
    if (mDatas == null || mDatas.size() <= 0) {
        reflashFooterView(ConstantUtil.LOAD_MORE_HIDDEN);
    } else if (mDatas.size() < ConstantUtil.PAGE_SIZE || !isAbleLoading) {
        reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
    } else {
        reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
    }
}
```

**三、加载更多分析**

加载更多有自己的布局，这个布局始终显示在RecyclerView的底部。要想实现这样的效果，可以利用RecyclerView适配器中ItemViewType，设置多种类型显示。当页面加载完mDatas中的数据之后，就进行底部布局显示。

这里要实现两种样式的布局，需要在RecyclerView适配器当中进行设置。
```
/**
 * RecyclerView适配器
 * Created by 邹峰立 on 2017/10/25.
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ONE = 0, TYPE_FOOT = 1;
    private ArrayList<RvData> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    private FooterData footerData;

    public RvAdapter(Context context, ArrayList<RvData> list, FooterData footerData) {
        this.mDatas = list;
        this.footerData = footerData;
        this.mInflater = LayoutInflater.from(context);
    }

    // 刷新全部数据
    public void reflushData(ArrayList<RvData> list, FooterData footerData) {
        this.mDatas = list;
        this.footerData = footerData;
        this.notifyDataSetChanged();
    }

    // 刷新列表数据
    public void reflushList(ArrayList<RvData> list) {
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    // 刷新底部
    public void reflushFooterData(FooterData footerData) {
        this.footerData = footerData;
        this.notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                holder = new RVHolder(mInflater.inflate(R.layout.activity_main_item, parent, false));
                break;
            case TYPE_FOOT:// 底部
                holder = new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ONE:
                ((RVHolder) holder).bindHolder(mDatas.get(position), position);
                break;
            case TYPE_FOOT:// 底部
                ((FooterHolder) holder).bindHolder(footerData);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + (footerData == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDatas.size())
            return mDatas.get(position).getType();
        else
            return TYPE_FOOT;
    }
}
```
**四、加载更多实现**

首先需要在实现类中继承RecyclerViewScrollListener.OnLoadListener，并实现onLoad加载更多方法。
```
// 设置recyclerView的布局管理器
recyclerView = (LoadRecyclerView) findViewById(R.id.recyclerview);
recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
recyclerView.setOnLoadListener(this);
```
```
// 加载更多
@Override
public void onLoad() {
    if (mDatas.size() >= ConstantUtil.PAGE_SIZE && isAbleLoading) {
        // 获取更多数据
        // 模拟网络加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 加载数据
                if (mDatas == null)
                    mDatas = new ArrayList<>();
                int num = mDatas.size();
                for (int i = num; i < num + ConstantUtil.PAGE_SIZE; i++) {
                    RvData rvData = new RvData();
                    rvData.setTitle("标题=" + i);
                    rvData.setDesc("描述=" + i);
                    rvData.setType(RvAdapter.TYPE_ONE);
                    mDatas.add(rvData);
                }
                setAdapter();
            }
        }, 3000);

        reflashFooterView(ConstantUtil.LOAD_MORE);
        swipeLayout.setRefreshing(false);
    } else {
        reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
    }

}
```
最后给出刷新底部布局方法：
```
// 刷新底部
private void reflashFooterView(int index) {
    // 重构底部数据
    switch (index) {
        case ConstantUtil.LOAD_MORE_BEFORE:// 加载前/后
            recyclerView.setLoading(false);
            footerData.setShowProgressBar(false);
            footerData.setShowFooter(true);
            footerData.setTitle(getResources().getString(R.string.load_more_before));
            break;
        case ConstantUtil.LOAD_MORE:// 加载中
            recyclerView.setLoading(false);
            footerData.setShowProgressBar(true);
            footerData.setShowFooter(true);
            footerData.setTitle(getResources().getString(R.string.load_more));
            break;
        case ConstantUtil.LOAD_MORE_COMPLETE:// 不允许加载
            recyclerView.setLoading(false);
            footerData.setShowProgressBar(false);
            footerData.setShowFooter(true);
            footerData.setTitle(getResources().getString(R.string.load_more_complete));
            break;
        case ConstantUtil.LOAD_MORE_HIDDEN:// 隐藏
            recyclerView.setLoading(false);
            footerData.setShowProgressBar(false);
            footerData.setShowFooter(false);
            footerData.setTitle(getResources().getString(R.string.load_more_complete));
            break;
    }
    // 刷新底部
    if (adapter != null)
        adapter.reflushFooterData(footerData);
}
```
[Github地址](https://github.com/zrunker/ZRecyclerView)
[阅读原文](http://www.ibooker.cc/article/70/detail)

----------
![微信公众号：书客创作](http://upload-images.jianshu.io/upload_images/3480018-8d6449faf7daf69e..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 
