package licola.demo.com.huabandemo.Module.Type;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.TypeAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseFragment;
import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Module.Main.MainActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Widget.LoadingFooter;
import licola.demo.com.huabandemo.Widget.MyRecyclerview.HeaderAndFooterRecyclerViewAdapter;
import licola.demo.com.huabandemo.Widget.MyRecyclerview.RecyclerViewUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2015/11/28  18:00
 * 展示各个模块的Fragment 在Main和Module Activity负责展示UI
 */
public class TypeFragment extends BaseFragment {
    private final float percentageScroll = 0.8f;//滑动距离的百分比
    private int mMaxId = 0;

    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";
    protected String type;
    protected String title;
    private static int limit = Constant.LIMIT;



    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.progressBar_recycler)
    ProgressBar mProgressBar;

    //    private MainRecyclerViewAdapter mAdapter;
//    private RecyclerPinsCardAdapter mAdapter;
    private RecyclerPinsHeadCardAdapter mAdapter;

    private OnPinsFragmentInteractionListener mListener;

    public static TypeFragment newInstance(String type, String title) {
        TypeFragment fragment = new TypeFragment();
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefresh.setColorSchemeResources(R.color.pink_300, R.color.pink_500, R.color.pink_700, R.color.pink_900);
        initRecyclerView();
        initListener();
        getHttpFirstAndRefresh();//默认的联网，区分于滑动的联网加载


    }


    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //// TODO: 2016/3/17 0017 预留选项 应该在设置中 添加一条单条垂直滚动选项
//        LinearLayoutManager layoutManager=new LinearLayoutManager(HuaBanApplication.getInstance());

//        mAdapter = new MainRecyclerViewAdapter(HuaBanApplication.getInstance());

        mAdapter = new RecyclerPinsHeadCardAdapter(mRecyclerView);//正常adapter的初始化
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
                        getHttpMaxId(mMaxId);
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
     */
    private void getHttpMaxId(int max) {
        Subscription s = RetrofitClient.createService(TypeAPI.class)
                .httpsTypeMaxLimitRx(mAuthorization, type, max, limit)
                .map(new Func1<ListPinsBean, List<PinsMainEntity>>() {
                    @Override
                    public List<PinsMainEntity> call(ListPinsBean listPinsBean) {
                        //取出list对象
                        return listPinsBean.getPins();
                    }
                })
                .filter(new Func1<List<PinsMainEntity>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsMainEntity> pinsEntities) {
                        //检查非空
                        return pinsEntities.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsMainEntity>>() {
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
                    public void onNext(List<PinsMainEntity> pinsEntities) {
                        Logger.d();
                        mMaxId = getMaxId(pinsEntities);
                        mAdapter.addListNotify(pinsEntities);
                    }
                });
        addSubscription(s);


    }

    /**
     * 联网得到内容 每次都会清空之前内容
     */
    private void getHttpFirstAndRefresh() {

        Subscription s = RetrofitClient.createService(TypeAPI.class)
                .httpsTypeLimitRx(mAuthorization, type, limit)
                .filter(new Func1<ListPinsBean, Boolean>() {
                    @Override
                    public Boolean call(ListPinsBean Bean) {
                        //过滤掉数组为0的next
                        return Bean.getPins().size() != 0;
                    }
                })
                .map(new Func1<ListPinsBean, List<PinsMainEntity>>() {
                    @Override
                    public List<PinsMainEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread())//订阅者的运行线程 在main线程中才能修改UI
                .subscribe(new Subscriber<List<PinsMainEntity>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
//                        mSwipeRefresh.setRefreshing(true);
                        setRecyclerProgressVisibility(false);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        mSwipeRefresh.setRefreshing(false);
                        setRecyclerProgressVisibility(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        mSwipeRefresh.setRefreshing(false);
                        setRecyclerProgressVisibility(true);
                        checkException(e);//检查错误 弹出提示
                    }

                    @Override
                    public void onNext(List<PinsMainEntity> result) {
                        Logger.d();
                        //保存maxId值 后续加载需要

                        mMaxId = getMaxId(result);
                        mAdapter.setListNotify(result);
                    }
                });

        addSubscription(s);

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
    private int getMaxId(List<PinsMainEntity> result) {
        return result.get(result.size() - 1).getPin_id();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPinsFragmentInteractionListener) {
            mListener = (OnPinsFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof MainActivity) {
            mAuthorization=((MainActivity)context).mAuthorization;
        } else if (context instanceof TypeActivity) {
            mAuthorization=((TypeActivity)context).mAuthorization;
        }
    }

    private void initListener() {
        //swipeRefresh 控件的滑动监听
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHttpFirstAndRefresh();
            }
        });


        mAdapter.setOnClickItemListener(new RecyclerPinsHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsMainEntity bean, View view) {
                Logger.d();
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsMainEntity bean, View view) {
                Logger.d();
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemText(bean, view);
            }

            @Override
            public void onClickInfoGather(PinsMainEntity bean, View view) {
                Logger.d();
            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {
                Logger.d(bean.toString());
//                int count=bean.getLike_count()+1;
//                bean.setLike_count(count);
//                mAdapter.notifyDataSetChanged();
                startLike();
            }

        });


    }

    private void startLike() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;

//        EventBus.getDefault().unregister(this);
    }
}
