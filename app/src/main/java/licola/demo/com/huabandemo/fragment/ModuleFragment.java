package licola.demo.com.huabandemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.activity.ImageDetailActivity;
import licola.demo.com.huabandemo.adapter.RecyclerHeadCardAdapter;
import licola.demo.com.huabandemo.bean.ListPinsBean;
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.httpUtils.RetrofitPinsRx;
import licola.demo.com.huabandemo.view.LoadingFooter;
import licola.demo.com.huabandemo.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import licola.demo.com.huabandemo.view.recyclerview.RecyclerViewUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2015/11/28  18:00
 * 展示各个模块的Fragment 在Main和Module Activity负责展示UI
 */
public class ModuleFragment extends BaseFragment {
    private final float percentageScroll = 0.8f;//滑动距离的百分比
    private int mMaxId = 0;

    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";
    protected String type;
    protected String title;
    private static int limit = Constant.LIMIT;


    @Bind(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.progressBar_recycler)
    ProgressBar mProgressBar;

    //    private MainRecyclerViewAdapter mAdapter;
//    private RecyclerCardAdapter mAdapter;
    private RecyclerHeadCardAdapter mAdapter;

    private Handler mHandler = new Handler();

    private final Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
//            mAdapter.getmList().clear();
//            getHttpMaxId(type, max, 20);
            startHttps();
            mSwipeRefresh.setRefreshing(false);
        }
    };

    private void startHttps() {
        getHttpFirstAndRefresh(type, limit);
    }


    public static ModuleFragment newInstance(String type, String title) {
        ModuleFragment fragment = new ModuleFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, type);
        args.putString(TYPE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getString(TYPE_KEY);
        title = args.getString(TYPE_TITLE);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefresh.setColorSchemeResources(R.color.pink_300, R.color.pink_500, R.color.pink_700, R.color.pink_900);
        initRecyclerView();
        initListener();
        getHttpFirstAndRefresh(type, limit);//默认的联网，区分于滑动的联网加载
    }


    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //// TODO: 2016/3/17 0017 预留选项 应该在设置中 添加一条单条垂直滚动选项
//        LinearLayoutManager layoutManager=new LinearLayoutManager(HuaBanApplication.getInstance());

//        mAdapter = new MainRecyclerViewAdapter(HuaBanApplication.getInstance());

        mAdapter = new RecyclerHeadCardAdapter(mRecyclerView);//正常adapter的初始化
        //转换成headAdapter
        HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(headAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        LoadingFooter loadingFooter = new LoadingFooter(getContext());
        loadingFooter.setState(LoadingFooter.State.Loading);
        RecyclerViewUtils.addFootView(mRecyclerView, loadingFooter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置默认动画

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    //滑动停止
//                    Logger.d("滑动停止 position=" + mAdapter.getAdapterPosition());
                    int size = (int) (mAdapter.getItemCount() * percentageScroll);
                    if (mAdapter.getAdapterPosition() >= --size) {
                        getHttpMaxId(type, mMaxId, limit);
                    }
                } else if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    //用户正在滑动
//                    Logger.d("用户正在滑动 position=" + mAdapter.getAdapterPosition());
                } else {
                    //惯性滑动
//                    Logger.d("惯性滑动 position=" + mAdapter.getAdapterPosition());
                }
            }
        });

    }

    /**
     * 根据max值联网 在滑动时调用 继续加载后续内容
     *
     * @param type
     * @param max
     * @param limit
     */
    private void getHttpMaxId(String type, int max, final int limit) {

        Observable<ListPinsBean> observable = RetrofitPinsRx.service.httpTypeMaxLimitRx(type, max, limit);
        observable
                .map(new Func1<ListPinsBean, List<PinsEntity>>() {
                    @Override
                    public List<PinsEntity> call(ListPinsBean listPinsBean) {
                        //取出list对象
                        return listPinsBean.getPins();
                    }
                })
                .filter(new Func1<List<PinsEntity>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsEntity> pinsEntities) {
                        //检查非空
                        return pinsEntities.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsEntity>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d();
                        checkException(e);//检查错误 弹出提示
                    }

                    @Override
                    public void onNext(List<PinsEntity> pinsEntities) {
                        Logger.d();
                        mMaxId = getMaxId(pinsEntities);
                        mAdapter.addList(pinsEntities);
                    }
                });
    }

    /**
     * 联网得到内容 每次都会清空之前内容
     *
     * @param type
     * @param limit
     */
    private void getHttpFirstAndRefresh(final String type, int limit) {
        Logger.d("getHttpFirstAndRefresh Start ");

        Observable<ListPinsBean> cardBigBeanObservable = RetrofitPinsRx.service.httpTypeLimitRx(type, limit);
        cardBigBeanObservable
                .filter(new Func1<ListPinsBean, Boolean>() {
                    @Override
                    public Boolean call(ListPinsBean Bean) {
                        //过滤掉数组为0的next
                        return Bean.getPins().size() != 0;
                    }
                })
                .map(new Func1<ListPinsBean, List<PinsEntity>>() {
                    @Override
                    public List<PinsEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread())//订阅者的运行线程 在main线程中才能修改UI
                .subscribe(new Subscriber<List<PinsEntity>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        setRecyclerProgressVisibility(false);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        setRecyclerProgressVisibility(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        setRecyclerProgressVisibility(true);
                        checkException(e);//检查错误 弹出提示
                    }

                    @Override
                    public void onNext(List<PinsEntity> result) {
                        Logger.d();
                        //保存maxId值 后续加载需要
                        mMaxId = getMaxId(result);
                        mAdapter.setList(result);
                    }
                });

    }

    /**
     * true 显示recycler 隐藏progress
     *
     * @param isShowRecycler
     */
    private void setRecyclerProgressVisibility(boolean isShowRecycler) {
        if (mRecyclerView != null) {
            mRecyclerView.setVisibility(isShowRecycler ? View.VISIBLE : View.GONE);
        }
        if (mProgressBar != null) {
            mProgressBar.setVisibility(isShowRecycler ? View.GONE : View.VISIBLE);

        }
    }


    /**
     * 从返回联网结果中保存max值 用于下次联网的关键
     *
     * @param result
     * @return
     */
    private int getMaxId(List<PinsEntity> result) {
        return result.get(result.size() - 1).getPin_id();
    }


    private void initListener() {

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.removeCallbacks(mRefresh);
                mHandler.postDelayed(mRefresh, 800);
            }
        });


        mAdapter.setOnClickItemListener(new RecyclerHeadCardAdapter.onAdapterListener() {
            @Override
            public void onClickImage(PinsEntity bean, View view) {
                Logger.d();
                ImageDetailActivity.launch(getActivity());
                EventBus.getDefault().postSticky(bean);
            }

            @Override
            public void onClickTitleInfo(PinsEntity bean, View view) {
                Logger.d();
                ImageDetailActivity.launch(getActivity());
                EventBus.getDefault().postSticky(bean);
            }

            @Override
            public void onClickInfoGather(PinsEntity bean, View view) {
                Logger.d();
            }

            @Override
            public void onClickInfoLike(PinsEntity bean, View view) {
                Logger.d();
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter=null;

        HuaBanApplication.getInstance().getRefwatcher().watch(this);
//        EventBus.getDefault().unregister(this);
    }
}
