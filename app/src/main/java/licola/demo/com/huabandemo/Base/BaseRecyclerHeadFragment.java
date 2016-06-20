package licola.demo.com.huabandemo.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import licola.demo.com.huabandemo.API.OnFragmentRefreshListener;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Widget.LoadingFooter;
import licola.demo.com.huabandemo.Widget.MyRecyclerview.HeaderAndFooterRecyclerViewAdapter;
import licola.demo.com.huabandemo.Widget.MyRecyclerview.RecyclerViewUtils;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by LiCola on  2016/03/26  16:55
 * BaseRecyclerHeadFragment 抽象出来作为模板类
 * 采用泛型 约束条件是RecyclerView.Adapter
 * 作用在于：固定通用方法确定整体结构 然后具体算法实现在子类中实现扩展 还确保了子类的拓展
 * 定义成抽象类：既要约束子类的行为，又为子类提供公共功能
 * 所以：模板方法的基类只提供通用功能和确定骨架，而不应该决定逻辑跳转等具体子类的功能
 */
public abstract class BaseRecyclerHeadFragment
        <T extends RecyclerView.Adapter, K extends List> extends BaseFragment
        implements OnFragmentRefreshListener {
    protected static final String TYPE_KEY = "KEY";//搜索关键字的key值
    protected final float percentageScroll = 0.8f;//滑动距离的百分比

    protected String mKey;//用于联网查询的关键字

    protected static int mLimit = Constant.LIMIT;

    protected boolean isFistHttp = true;//是否第一次联网

    //是否还监听滑动的联网 标志位 默认为true 表示需要监听
    protected boolean isScorllLisener = true;


    @BindView(R.id.recycler_list)
    protected RecyclerView mRecyclerView;

    //    protected RecyclerPinsHeadCardAdapter mAdapter;
    protected T mAdapter;

    //能显示三种状态的 footView
    protected LoadingFooter mFooterView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData(getArguments());

    }

    /**
     * 取bundle值的方法 可以被子类重写
     *
     * @param args
     */
    protected void getBundleData(Bundle args) {
        if (args != null) {
            mKey = args.getString(TYPE_KEY);//父类取出key
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initListener();
        addSubscription(getHttpOther());
        addSubscription(getHttpFirst());
    }

    //界面初始化的其他联网 可以不重写
    protected Subscription getHttpOther() {
        return null;
    }

    //界面初始化的联网 由子类重写 提供参数判断是否初次联网
    protected abstract Subscription getHttpFirst();

    protected abstract Subscription getHttpScroll();//滑动产生的联网 由子类重写

    @Override
    public void getHttpRefresh() {
        addSubscription(getHttpFirst());
    }

    /**
     * 提供给子类过滤 网络返回list 并休息ui状态的方法
     *
     * @return
     */
    protected Func1<K, Boolean> getFilterFunc1() {
        return new Func1<K, Boolean>() {
            @Override
            public Boolean call(K k) {
//                getFootView();

                if (k == null || k.size() == 0) {
                    if (mFooterView!=null){
                        mFooterView.setState(LoadingFooter.State.TheEnd);
                        Logger.d("mFooterView ! = null");

                    }else {
                        Logger.d("mFooterView= null");
                    }


                    isScorllLisener = false;
                    return false;
                }

                if (k.size() < mLimit) {
                    mFooterView.setState(LoadingFooter.State.TheEnd);
                    isScorllLisener = false;
                    return true;
                }
                return true;
            }
        };
    }

    protected View getFootView() {
        if (mFooterView == null) {
            mFooterView = new LoadingFooter(getActivity());
            mFooterView.setState(LoadingFooter.State.Loading);
        }
        return mFooterView;
    }

    protected abstract View getHeadView();

    //返回当前RecyclerView显示的 holder 位置
    protected abstract int getAdapterPosition();

    protected abstract T setAdapter(); //这里初始化 adapter

    /**
     * 初始化监听器的空方法 子类需要重写
     */
    protected void initListener() {

    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initRecyclerView() {

        //// TODO: 2016/3/17 0017 预留选项 应该在设置中 添加一条单条垂直滚动选项
//        LinearLayoutManager layoutManager=new LinearLayoutManager(HuaBanApplication.getInstance());
//        mAdapter = new RecyclerPinsHeadCardAdapter(mRecyclerView);
        mAdapter = setAdapter();
        HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(headAdapter);
        mRecyclerView.setLayoutManager(getLayoutManager());
        //绑定能添加头尾View的adapter后 检查View返回 添加
        if (getHeadView() != null) {
            RecyclerViewUtils.addHearView(mRecyclerView, getHeadView());
        }
        if (getFootView() != null) {
            RecyclerViewUtils.addFootView(mRecyclerView, getFootView());
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    //滑动停止
//                    Logger.d("滑动停止 position=" + mAdapter.getAdapterPosition());
                    int size = (int) (mAdapter.getItemCount() * percentageScroll);
                    if (getAdapterPosition() >= --size && isScorllLisener) {
                        addSubscription(getHttpScroll());
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


    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
