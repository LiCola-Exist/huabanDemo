package licola.demo.com.huabandemo.Module.User;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import org.greenrobot.eventbus.EventBus;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.UserAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/08  15:05
 */
public class UserLikeFragment extends
        BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter, List<PinsMainEntity>> {

    private static final String TAG = "UserPinsFragment";
    private int mMax;

    private OnPinsFragmentInteractionListener mListener;
    private OnRefreshFragmentInteractionListener mRefreshListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static UserLikeFragment newInstance(String key) {
        UserLikeFragment fragment = new UserLikeFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(UserAPI.class)
                .httpsUserLikePinsRx(mAuthorization,mKey,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ListPinsBean::getPins)
                .filter(getFilterFunc1())
                .subscribe(new Action1<List<PinsMainEntity>>() {
                    @Override
                    public void call(List<PinsMainEntity> pinsAndUserEntities) {
                        Logger.d();
                        mAdapter.setListNotify(pinsAndUserEntities);
                        mMax=getMax(pinsAndUserEntities);
                    }
                },getErrorAction(),getCompleteAction());
    }

    private int getMax(List<PinsMainEntity> bean) {
        return bean.get(bean.size() - 1).getSeq();
    }

    private Action1<Throwable> getErrorAction() {
        return throwable -> {
            Logger.d(throwable.toString());
            checkException(throwable);
            mRefreshListener.OnRefreshState(false);
        };
    }

    private Action0 getCompleteAction() {
        return () -> {
            Logger.d();
            mRefreshListener.OnRefreshState(false);
        };
    }

    @Override
    protected Subscription getHttpScroll() {
        return RetrofitClient.createService(UserAPI.class)
                .httpsUserLikePinsMaxRx(mAuthorization,mKey, mMax, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ListPinsBean::getPins)
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
                        Logger.d();
                        mAdapter.addListNotify(pinsAndUserEntities);
                        mMax = getMax(pinsAndUserEntities);
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
    protected void initListener() {
        super.initListener();
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
            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {
                Logger.d();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ((context instanceof OnPinsFragmentInteractionListener)&&(context instanceof OnRefreshFragmentInteractionListener)) {
            mListener = (OnPinsFragmentInteractionListener) context;
            mRefreshListener= (OnRefreshFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof UserActivity){
            mAuthorization=((UserActivity) context).mAuthorization;
        }
    }

    @Override
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }

}
