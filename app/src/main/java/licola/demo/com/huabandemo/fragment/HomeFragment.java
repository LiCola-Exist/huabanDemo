package licola.demo.com.huabandemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.API.HttpInterface;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.activity.ImageDetailActivity;
import licola.demo.com.huabandemo.activity.ScrollingActivity;
import licola.demo.com.huabandemo.adapter.MainRecyclerViewAdapter;
import licola.demo.com.huabandemo.bean.CardBigBean;
import licola.demo.com.huabandemo.bean.CardBigBean.PinsEntity;
import licola.demo.com.huabandemo.bean.SearchHitBean;
import licola.demo.com.huabandemo.bean.SearchImageBean;
import licola.demo.com.huabandemo.bean.SearchPeopleBean;
import licola.demo.com.huabandemo.httpUtils.HttpRequest;
import licola.demo.com.huabandemo.httpUtils.RetrofitGson;
import licola.demo.com.huabandemo.httpUtils.RetrofitPins;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by LiCola on  2015/11/28  18:00
 */
public class HomeFragment extends BaseFragment {
    private final float percentageScroll = 0.8f;
    private int maxId = 0;

    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";
    protected String type;
    protected String title;
    private static int limit = Constant.LIMIT;

    @Bind(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;


    private MainRecyclerViewAdapter mAdapter;
    private Handler mHandler = new Handler();

    private final Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
//            mAdapter.getmList().clear();
//            httpTypeLimit(type, max, 20);
            startHttps();
            mSwipeRefresh.setRefreshing(false);
        }
    };

    private void startHttps() {
        httpTypeFirst(type, limit);
    }


    public static HomeFragment newInstance(String type, String title) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, type);
        args.putString(TYPE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
        httpTypeFirst(type, limit);
//        TestJson();
    }

    private void TestJson() {
        String test = "{\"total\":10,\"result\":[\"美人\",\"美人鱼\",\"美人鱼插画\",\"美人图\",\"美人心计\",\"美人制造\",\"美人如玉\",\"美人符\",\"美人蕉\",\"美人胚\"]}";
        Gson gson = new Gson();
        SearchHitBean bean = gson.fromJson(test, SearchHitBean.class);
        bean.toString();
        Logger.d(bean.toString());

    }


    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainRecyclerViewAdapter(HuaBanApplication.getInstance());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    //滑动停止
//                    Logger.d("滑动停止 position=" + mAdapter.getmAdapterPosition());
                    int size = (int) (mAdapter.getItemCount() * percentageScroll);
                    if (mAdapter.getmAdapterPosition() >= --size) {
                        httpTypeLimit(type, maxId, 20);
                    }
                } else if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    //用户正在滑动
//                    Logger.d("用户正在滑动 position=" + mAdapter.getmAdapterPosition());
                } else {
                    //惯性滑动
//                    Logger.d("惯性滑动 position=" + mAdapter.getmAdapterPosition());
                }
            }
        });
    }

    private void httpTypeLimit(String type, int max, int limit) {
        Logger.d("HttpTypeLimit Start 开始联网");
        Call<CardBigBean> cardBigBeanCall = RetrofitPins.service.httpTypeMaxLimit(type, max, limit);
        HttpRequest.Requeset(cardBigBeanCall, new HttpInterface<CardBigBean>() {
            @Override
            public void onHttpSuccess(CardBigBean result) {
                maxId = result.getPins().get(result.getPins().size() - 1).getPin_id();
                mAdapter.setNotifyData(result.getPins());
            }

            @Override
            public void onHttpError(int code, String msg) {
                Logger.d("code=" + code + " msg=" + msg);
            }

            @Override
            public void onHttpFailure(String error) {
                Logger.d(error);
            }
        });

    }


    private void httpTypeFirst(String type, int limit) {
        Logger.d("httpTypeFirst Start ");

        Call<CardBigBean> cardBigBeanCall = RetrofitPins.service.httpTypeLimit(type, limit);
        HttpRequest.Requeset(cardBigBeanCall, new HttpInterface<CardBigBean>() {
            @Override
            public void onHttpStart() {
                super.onHttpStart();
                Logger.d(" fragment first get data");
            }

            @Override
            public void onHttpSuccess(CardBigBean result) {
                if (result.getPins().size() != 0) {
                    maxId = result.getPins().get(result.getPins().size() - 1).getPin_id();
                    mAdapter.setmList(result.getPins());
                } else {
                    Logger.d("pins size=" + result.getPins().size());
                }
            }

            @Override
            public void onHttpError(int code, String msg) {
                Logger.d("code=" + code + " msg=" + msg);
            }

            @Override
            public void onHttpFailure(String error) {
                Logger.d(error);
            }
        });

    }


    private void httpTypeSearch(String type, String key, int page, int per_page) {
        // httpTypeSearch("food_drink","料理",1,20);
        RetrofitGson.service.httpTypeSearch(type, key, page, per_page).enqueue(new Callback<CardBigBean>() {
            @Override
            public void onResponse(Response<CardBigBean> response, Retrofit retrofit) {
                mAdapter.setNotifyData(response.body().getPins());

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


        mAdapter.setOnClickItemListener(new MainRecyclerViewAdapter.onAdapterListener() {
            @Override
            public void onClickImage(PinsEntity bean, View view) {
//                Logger.d(bean.toString());
//                Intent intent = new Intent(getActivity(), ImageActivity.class);
//                intent.putExtra("key", bean.getFile().getKey());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "card_image").toBundle());
//                }

                ImageDetailActivity.launch(getActivity());
                EventBus.getDefault().postSticky(bean);
            }

            @Override
            public void onClickBoard(PinsEntity bean, View view) {
                Logger.d(bean.toString());
                Intent intent = new Intent(getActivity(), ScrollingActivity.class);
                getActivity().startActivity(intent);
            }

            @Override
            public void onClickInfo(PinsEntity bean, View view) {
                Logger.d(bean.toString());
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
