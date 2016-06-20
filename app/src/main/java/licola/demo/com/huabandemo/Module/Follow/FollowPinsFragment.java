package licola.demo.com.huabandemo.Module.Follow;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import butterknife.BindString;

import org.greenrobot.eventbus.EventBus;

import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.FollowingAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  14:46
 */
public class FollowPinsFragment
        extends BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter,
        List<PinsMainEntity>> {
    //联网关键参数
    private int mMaxId;//下一次联网的pinsId开始

    private OnPinsFragmentInteractionListener mListener;
    private OnRefreshFragmentInteractionListener mRefreshListener;

    @BindString(R.string.snack_message_not_notify)
    String mStringNotNotify;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static FollowPinsFragment newInstance() {

        return new FollowPinsFragment();
    }

    public FollowPinsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(context.toString());
        if ((context instanceof OnRefreshFragmentInteractionListener)
                && (context instanceof OnPinsFragmentInteractionListener)) {
            mListener = (OnPinsFragmentInteractionListener) context;
            mRefreshListener = (OnRefreshFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof FollowActivity) {
            String a = ((FollowActivity) context).mAuthorization;
            if (a != null) {
                mAuthorization = a;
            }
            Logger.d(mAuthorization);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mRefreshListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mAuthorization = savedInstanceState.getString("key1");
            Logger.d(mAuthorization);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key1", mAuthorization);
        Logger.d();
    }


    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(FollowingAPI.class)
                .httpsMyFollowingPinsRx(mAuthorization, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(FollowPinsBean::getPins)
                .filter(getFilterFunc1())
                .subscribe(new Action1<List<PinsMainEntity>>() {
                    @Override
                    public void call(List<PinsMainEntity> pinsAndUserEntities) {
                        if (checkNotify(pinsAndUserEntities)) {
                            Logger.d();
                            mAdapter.setListNotify(pinsAndUserEntities);
                            mMaxId = getMaxId(pinsAndUserEntities);
                        } else {
                            Logger.d("not notify");
                            NetUtils.showSnackBar(mRootView, mStringNotNotify);
                        }

                    }
                }, getErrorAction(), getCompleteAction());
    }

    private Action1<Throwable> getErrorAction() {
        return throwable -> {
            Logger.d(throwable.toString());
            checkException(throwable);
            mRefreshListener.OnRefreshState(false);
        };
    }

    private Action0 getCompleteAction() {
//        return () -> {
//            Logger.d();
////            mRefreshListener.OnRefreshState(false);
//            ((OnRefreshFragmentInteractionListener) getActivity()).OnRefreshState(false);
//        };
        return new Action0() {
            @Override
            public void call() {
                Logger.d();
//            mRefreshListener.OnRefreshState(false);
            ((OnRefreshFragmentInteractionListener) getActivity()).OnRefreshState(false);
            }
        };
    }


    /**
     * 判断集合是否和当前adapter中的集合对象一致 这里只简单实现 应该重写hash方法 用equals做判断
     *
     * @param result
     * @return
     */
    //// TODO: 2016/5/20 0020 方法需要重写 重写类的hash 和equals
    private boolean checkNotify(List<PinsMainEntity> result) {
        if (!mAdapter.getList().isEmpty()) {
            if (mAdapter.getList().get(0).getFile().getKey().equals(result.get(0).getFile().getKey())) {
                return false;
            }
        }
        return true;
    }

    private int getMaxId(List<PinsMainEntity> result) {
        return result.get(result.size() - 1).getPin_id();
    }

    @Override
    protected Subscription getHttpScroll() {
        return RetrofitClient.createService(FollowingAPI.class)
                .httpsMyFollowingPinsMaxRx(mAuthorization, mMaxId, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<FollowPinsBean, List<PinsMainEntity>>() {
                    @Override
                    public List<PinsMainEntity> call(FollowPinsBean followingPinsBean) {
                        return followingPinsBean.getPins();
                    }
                })
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<PinsMainEntity>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        checkException(e);
                    }

                    @Override
                    public void onNext(List<PinsMainEntity> pinsAndUserEntities) {
                        mAdapter.addListNotify(pinsAndUserEntities);
                        mMaxId = getMaxId(pinsAndUserEntities);
                    }
                });
    }

    @Override
    protected void initListener() {
        mAdapter.setOnClickItemListener(new RecyclerPinsHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemText(bean, view);
            }

            @Override
            public void onClickInfoGather(PinsMainEntity bean, View view) {
                Logger.d();
                //todo 收集时间 类内部处理不传递
            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {
                Logger.d();
            }
        });
    }


    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }


}
