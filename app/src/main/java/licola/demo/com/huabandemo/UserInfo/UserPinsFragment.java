package licola.demo.com.huabandemo.UserInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.API.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Bean.ListPinsBean;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/08  15:05
 */
public class UserPinsFragment extends
        BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter, List<PinsAndUserEntity>> {

    private static final String TAG = "UserPinsFragment";
    private int mMax;

    private OnPinsFragmentInteractionListener mListener;
    private OnRefreshFragmentInteractionListener mRefreshListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static UserPinsFragment newInstance(String key) {
        UserPinsFragment fragment = new UserPinsFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Subscription getHttpFirst() {
        return RetrofitService.createAvatarService()
                .httpsUserPinsRx(mAuthorization,mKey,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(listPinsBean -> listPinsBean.getPins())
                .filter(getFilterFunc1())
                .subscribe(new Action1<List<PinsAndUserEntity>>() {
                    @Override
                    public void call(List<PinsAndUserEntity> pinsAndUserEntities) {
                        Logger.d();
                        mAdapter.setListNotify(pinsAndUserEntities);
                        mMax = getMax(pinsAndUserEntities);
                    }
                },getErrorAction(),getCompleteAction());
    }

    private int getMax(List<PinsAndUserEntity> bean) {
        return bean.get(bean.size() - 1).getPin_id();
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
        return RetrofitService.createAvatarService()
                .httpsUserPinsMaxRx(mAuthorization,mKey,mMax,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(listPinsBean -> listPinsBean.getPins())
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<PinsAndUserEntity>>() {
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
                    public void onNext(List<PinsAndUserEntity> pinsAndUserEntities) {
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
            public void onClickImage(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemText(bean, view);
            }

            @Override
            public void onClickInfoGather(PinsAndUserEntity bean, View view) {
                Logger.d();
            }

            @Override
            public void onClickInfoLike(PinsAndUserEntity bean, View view) {
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

        if (context instanceof UserInfoActivity){
            mAuthorization=((UserInfoActivity) context).mAuthorization;
        }
    }

    @Override
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }


}
