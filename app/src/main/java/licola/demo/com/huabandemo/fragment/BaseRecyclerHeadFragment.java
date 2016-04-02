package licola.demo.com.huabandemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import butterknife.Bind;
import butterknife.BindString;
import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.activity.ImageDetailActivity;
import licola.demo.com.huabandemo.adapter.RecyclerHeadCardAdapter;
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import licola.demo.com.huabandemo.view.recyclerview.RecyclerViewUtils;

/**
 * Created by LiCola on  2016/03/26  16:55
 * 作为一个持有 RecyclerHeadCardAdapter对象 其中包含数据List<PinsEntity>
 * 在RecyclerView负责显示内容的基类
 * 因为app中有多个类型一致的一致的返回List结果 抽象出来作为模板类
 * 作用在于：固定通用方法确定整体结构 然后具体算法实现在子类中实现扩展 还确保了子类的拓展
 * 定义成抽象类：既要约束子类的行为，又为子类提供公共功能
 * 所以：模板方法的基类只提供通用功能和确定骨架，而不应该决定逻辑跳转等具体子类的功能
 */
public abstract class BaseRecyclerHeadFragment extends BaseFragment {
    protected static final String TYPE_KEY = "KEY";//搜索关键字的key值
    protected final float percentageScroll = 0.8f;//滑动距离的百分比

    protected String mKey;//用于联网查询的关键字

    protected static int mLimit = Constant.LIMIT;

    protected boolean isFistHttp = true;//是否第一次联网

    @BindString(R.string.urlImageRoot)
    String mUrlImageRoot;

    @Bind(R.id.recycler_list)
    RecyclerView mRecyclerView;

    //    private RecyclerCardAdapter mAdapter;
    protected RecyclerHeadCardAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mKey = args.getString(TYPE_KEY);//父类取出key
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initListener();
        getHttpFirst();
        getHttpOther();
    }

    //界面初始化的其他联网 可以不重写
    protected void getHttpOther() {

    }

    protected abstract void getHttpFirst();//界面初始化的联网 由子类重写

    protected abstract void getHttpScroll();//滑动产生的联网 由子类重写

    protected abstract View setFootView();

    protected abstract View setHeadView();


    protected void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //// TODO: 2016/3/17 0017 预留选项 应该在设置中 添加一条单条垂直滚动选项
//        LinearLayoutManager layoutManager=new LinearLayoutManager(HuaBanApplication.getInstance());
        mAdapter = new RecyclerHeadCardAdapter(mRecyclerView);
        HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(headAdapter);
        //绑定能添加头尾View的adapter后 检查View返回 添加
        if (setHeadView() != null) {
            RecyclerViewUtils.addHearView(mRecyclerView, setHeadView());
        }
        if (setFootView() != null) {
            RecyclerViewUtils.addFootView(mRecyclerView, setFootView());
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
                    if (mAdapter.getAdapterPosition() >= --size) {
                        getHttpScroll();
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
     * 初始化监听器的空方法 子类需要重写
     */
    protected void initListener() {
        //基类中不应该 确定具体的逻辑跳转 交由子类实现
//        mAdapter.setOnClickItemListener(new RecyclerHeadCardAdapter.OnAdapterListener() {
//            @Override
//            public void onClickImage(PinsEntity bean, View view) {
//                Logger.d();
//                ImageDetailActivity.launch(getActivity());
//                EventBus.getDefault().postSticky(bean);
//            }
//
//            @Override
//            public void onClickTitleInfo(PinsEntity bean, View view) {
//                Logger.d();
//                ImageDetailActivity.launch(getActivity());
//                EventBus.getDefault().postSticky(bean);
//            }
//
//            @Override
//            public void onClickInfoGather(PinsEntity bean, View view) {
//                Logger.d();
//            }
//
//            @Override
//            public void onClickInfoLike(PinsEntity bean, View view) {
//                Logger.d();
//            }
//
//        });
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
