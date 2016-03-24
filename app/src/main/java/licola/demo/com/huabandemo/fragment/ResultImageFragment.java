package licola.demo.com.huabandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonSyntaxException;

import java.net.UnknownHostException;
import java.util.List;

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
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.bean.SearchImageBean;
import licola.demo.com.huabandemo.httpUtils.RetrofitPinsRx;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2015/11/28  18:00
 * 展示各个模块的Fragment 在Main和Module Activity负责展示UI
 */
public class ResultImageFragment extends BaseFragment {
    private static final String TYPE_KEY = "KEY";//搜索关键字的key值
    private final float percentageScroll = 0.8f;//滑动距离的百分比

    private String mKey;//用于联网查询的关键字
    private int mIndex = 1;//默认值为1
    private static int limit = Constant.LIMIT;

    private boolean isFistHttp=true;//是否第一次联网

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
    @Bind(R.id.progressBar_recycler)
    ProgressBar mProgressBar;

    //    private MainRecyclerViewAdapter mAdapter;
    private RecyclerCardAdapter mAdapter;

    //只需要一个Key作为关键字联网
    public static ResultImageFragment newInstance(String key) {
        ResultImageFragment fragment = new ResultImageFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_progressbar;
    }

    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mKey = args.getString(TYPE_KEY);

//        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initListener();
        getHttpSearchImage(mKey, mIndex, limit);//默认的联网
    }


    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //// TODO: 2016/3/17 0017 预留选项 应该在设置中 添加一条单条垂直滚动选项
//        LinearLayoutManager layoutManager=new LinearLayoutManager(HuaBanApplication.getInstance());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerCardAdapter(mRecyclerView);
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
                        getHttpSearchImage(mKey, mIndex, limit);//滑动联网
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
     * @param key
     * @param index
     * @param limit
     */
    private void getHttpSearchImage(String key, int index, int limit) {
        Logger.d();

        getSearchImageObservable(key, index, limit)
//                .filter(new Func1<CardBigBean, Boolean>() {
//                    @Override
//                    public Boolean call(CardBigBean cardBigBean) {
//                        //过滤掉数组为0的next
//                        return cardBigBean.getPins().size() != 0;
//                    }
//                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread())//订阅者的运行线程 在main线程中才能修改UI
                .map(new Func1<SearchImageBean, List<PinsEntity>>() {
                    @Override
                    public List<PinsEntity> call(SearchImageBean searchImageBean) {
                        return searchImageBean.getPins();//取出list对象
                    }
                })
                .subscribe(new Subscriber<List<PinsEntity>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        if (isFistHttp){
                            setRecyclerProgressVisibility(false);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        if (isFistHttp){
                            setRecyclerProgressVisibility(true);
                            isFistHttp=false;//第一次联网完成 修改状态值
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        if (isFistHttp){
                            setRecyclerProgressVisibility(true);
                            isFistHttp=false;//第一次联网异常 修改状态值
                        }
                        //// TODO: 2016/3/22 0022 需要显示异常
                        checkException(e);//检查错误 弹出提示
                    }

                    @Override
                    public void onNext(List<PinsEntity> pinsEntities) {
                        Logger.d(pinsEntities.size() + "");
                        mAdapter.addList(pinsEntities);
                        mIndex++;//联网成功 +1
                    }
                });
    }

    private Observable<SearchImageBean> getSearchImageObservable(String key, int index, int limit) {
        return RetrofitPinsRx.service.httpImageSearchRx(key, index, limit);
    }

    /**
     * true 显示recycler 隐藏progress
     *
     * @param isShowRecycler
     */
    private void setRecyclerProgressVisibility(boolean isShowRecycler) {
        if (mRecyclerView!=null) {
            mRecyclerView.setVisibility(isShowRecycler?View.VISIBLE:View.GONE);
        }
        if (mProgressBar!=null) {
            mProgressBar.setVisibility(isShowRecycler ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 判断异常类型 根据类型弹出提示
     *
     * @param e
     */
    private void checkException(Throwable e) {
        if ((e instanceof UnknownHostException)) {
            NetUtils.showNetworkError(getActivity(), mRecyclerView, snack_message_net_error, snack_action_to_setting);
        }
        if (e instanceof JsonSyntaxException) {
            NetUtils.showNetworkError(getActivity(), mRecyclerView, snack_message_data_error, snack_action_to_setting);
        } else {
            Snackbar.make(mRecyclerView, snack_message_unknown_error, Snackbar.LENGTH_LONG);
        }
    }


    private void initListener() {

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
