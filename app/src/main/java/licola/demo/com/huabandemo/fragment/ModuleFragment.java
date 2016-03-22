package licola.demo.com.huabandemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonSyntaxException;

import java.net.UnknownHostException;

import butterknife.Bind;
import butterknife.BindString;
import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.activity.ImageDetailActivity;
import licola.demo.com.huabandemo.adapter.RecyclerCardAdapter;
import licola.demo.com.huabandemo.bean.CardBigBean;
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.bean.SearchImageBean;
import licola.demo.com.huabandemo.bean.SearchPeopleBean;
import licola.demo.com.huabandemo.httpUtils.RetrofitGson;
import licola.demo.com.huabandemo.httpUtils.RetrofitPinsRx;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
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
    private int maxId = 0;

    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";
    protected String type;
    protected String title;
    private static int limit = Constant.LIMIT;

    @BindString(R.string.snack_message_net_error)
    String snack_message_net_error;
    @BindString(R.string.snack_action_to_setting)
    String snack_action_to_setting;
    @BindString(R.string.snack_message_unknown_error)
    String snack_message_unknown_error;
    @BindString(R.string.snack_message_data_error)
    String snack_message_data_error;

    @Bind(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.progressBar_recycler)
    ProgressBar mProgressBar;

//    private MainRecyclerViewAdapter mAdapter;
    private RecyclerCardAdapter mAdapter;
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
    public String getTAG() {
        return this.getClass().getSimpleName();
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
        mRecyclerView.setLayoutManager(layoutManager);
//        mAdapter = new MainRecyclerViewAdapter(HuaBanApplication.getInstance());
        mAdapter = new RecyclerCardAdapter(HuaBanApplication.getInstance());
        mRecyclerView.setAdapter(mAdapter);
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
                        getHttpMaxId(type, maxId, limit);
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
    private void getHttpMaxId(String type, int max, int limit) {

        Observable<CardBigBean> observable = RetrofitPinsRx.service.httpTypeMaxLimitRx(type, max, limit);
        observable
                .filter(new Func1<CardBigBean, Boolean>() {
                    @Override
                    public Boolean call(CardBigBean cardBigBean) {
                        return cardBigBean.getPins().size() != 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CardBigBean>() {
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
                    public void onNext(CardBigBean result) {
                        Logger.d();
                        maxId = getMaxId(result);
                        mAdapter.addList(result.getPins());
                    }
                });
    }

    /**
     * 联网得到内容 每次都会清空之前内容
     *
     * @param type
     * @param limit
     */
    private void getHttpFirstAndRefresh(String type, int limit) {
        Logger.d("getHttpFirstAndRefresh Start ");

        Observable<CardBigBean> cardBigBeanObservable = RetrofitPinsRx.service.httpTypeLimitRx(type, limit);
        cardBigBeanObservable
                .filter(new Func1<CardBigBean, Boolean>() {
                    @Override
                    public Boolean call(CardBigBean cardBigBean) {
                        //过滤掉数组为0的next
                        return cardBigBean.getPins().size() != 0;
                    }
                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread())//订阅者的运行线程 在main线程中才能修改UI
                .subscribe(new Subscriber<CardBigBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        mProgressBar.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        mProgressBar.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        checkException(e);//检查错误 弹出提示
                    }

                    @Override
                    public void onNext(CardBigBean result) {
                        Logger.d(result.toString());
                        //保存maxId值 后续加载需要
                        maxId = getMaxId(result);
                        mAdapter.setList(result.getPins());
                    }
                });

//        Call<CardBigBean> cardBigBeanCall = RetrofitPins.service.httpTypeLimit(type, limit);
//        HttpRequest.Requeset(cardBigBeanCall, new HttpInterface<CardBigBean>() {
//            @Override
//            public void onHttpStart() {
//                super.onHttpStart();
//                Logger.d(" fragment first get data");
//            }
//
//            @Override
//            public void onHttpSuccess(CardBigBean result) {
//                if (result.getPins().size() != 0) {
//                    //保存maxid值 后续加载需要
//                    maxId = result.getPins().get(result.getPins().size() - 1).getPin_id();
//                    mAdapter.setList(result.getPins());
//                } else {
//                    Logger.d("pins size=" + result.getPins().size());
//                }
//            }
//
//            @Override
//            public void onHttpError(int code, String msg) {
//                Logger.d("code=" + code + " msg=" + msg);
//            }
//
//            @Override
//            public void onHttpFailure(String error) {
//                Logger.d(error);
//            }
//        });

    }

    private void checkException(Throwable e) {
        if ((e instanceof UnknownHostException)) {
            NetUtils.showNetworkError(getActivity(), mRecyclerView, snack_message_net_error, snack_action_to_setting);
        }
        if (e instanceof JsonSyntaxException) {
            NetUtils.showNetworkError(getActivity(),mRecyclerView,snack_message_data_error,snack_action_to_setting);
        } else {
            Snackbar.make(mRecyclerView, snack_message_unknown_error, Snackbar.LENGTH_LONG);
        }
    }


    /**
     * 从返回联网结果中保存max值 用于下次联网的关键
     *
     * @param result
     * @return
     */
    private int getMaxId(CardBigBean result) {
        return result.getPins().get(result.getPins().size() - 1).getPin_id();
    }


    private void httpTypeSearch(String type, String key, int page, int per_page) {
        // httpTypeSearch("food_drink","料理",1,20);
        RetrofitGson.service.httpTypeSearch(type, key, page, per_page).enqueue(new Callback<CardBigBean>() {
            @Override
            public void onResponse(Response<CardBigBean> response, Retrofit retrofit) {
                mAdapter.addList(response.body().getPins());

            }

            @Override
            public void onFailure(Throwable t) {
                Logger.d(t.toString());
            }
        });
    }

    private void httpSearchImage(String key, int page, int per_page) {
        RetrofitGson.service.httpImageSearch(key, page, per_page).enqueue(new Callback<SearchImageBean>() {
            @Override
            public void onResponse(Response<SearchImageBean> response, Retrofit retrofit) {
                Logger.d(response.body().getQuery().getText());
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.d(t.toString());
            }
        });
    }

    private void httpSearchPeople(String key, int page, int per_page) {
        RetrofitGson.service.httpPeopleSearch(key, page, per_page).enqueue(new Callback<SearchPeopleBean>() {
            @Override
            public void onResponse(Response<SearchPeopleBean> response, Retrofit retrofit) {
                Logger.d(response.body().getUsers().size() + " ");
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.d(t.toString());
            }
        });
    }


    private void initListener() {

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.removeCallbacks(mRefresh);
                mHandler.postDelayed(mRefresh, 800);
            }
        });


//        mAdapter.setOnClickItemListener(new MainRecyclerViewAdapter.onAdapterListener() {
//            @Override
//            public void onClickImage(PinsEntity bean, View view) {
////                Logger.d(bean.toString());
////                Intent intent = new Intent(getActivity(), ImageActivity.class);
////                intent.putExtra("key", bean.getFile().getKey());
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "card_image").toBundle());
////                }
//
//                ImageDetailActivity.launch(getActivity());
//                EventBus.getDefault().postSticky(bean);
//            }
//
//            @Override
//            public void onClickBoard(PinsEntity bean, View view) {
//                Logger.d(bean.toString());
//                Intent intent = new Intent(getActivity(), ScrollingActivity.class);
//                getActivity().startActivity(intent);
//            }
//
//            @Override
//            public void onClickTitleInfo(PinsEntity bean, View view) {
//                Logger.d(bean.toString());
//            }
//        });

        mAdapter.setOnClickItemListener(new RecyclerCardAdapter.onAdapterListener() {
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

        HuaBanApplication.getInstance().getRefwatcher().watch(this);
//        EventBus.getDefault().unregister(this);
    }
}
