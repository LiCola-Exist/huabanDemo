package licola.demo.com.huabandemo.UserInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.API.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Bean.ListPinsBean;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/08  15:05
 */
public class UserLikeFragment extends
        BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter, List<PinsAndUserEntity>> {

    private static final String TAG = "UserPinsFragment";
    private int mMax;
    private String mTokenType;
    private String mTokenAccess;

    private OnPinsFragmentInteractionListener mListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTokenAccess = ((UserInfoActivity) getActivity()).mTokenAccess;
        mTokenType = ((UserInfoActivity) getActivity()).mTokenType;
    }

    @Override
    protected Subscription getHttpFirst() {
        return getPins(mTokenType, mTokenAccess, mKey, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ListPinsBean, List<PinsAndUserEntity>>() {
                    @Override
                    public List<PinsAndUserEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
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
                        mAdapter.addList(pinsAndUserEntities);
                        mMax = getMax(pinsAndUserEntities);
                    }
                });
    }

    private int getMax(List<PinsAndUserEntity> bean) {
        return bean.get(bean.size() - 1).getSeq();
    }


    @Override
    protected Subscription getHttpScroll() {
        return getPinsMax(mTokenType, mTokenAccess, mKey, mMax, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ListPinsBean, List<PinsAndUserEntity>>() {
                    @Override
                    public List<PinsAndUserEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
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
                        mAdapter.addList(pinsAndUserEntities);
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
        if (context instanceof OnPinsFragmentInteractionListener) {
            mListener = (OnPinsFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }

    private Observable<ListPinsBean> getPins(String bearer, String key, String userId, int limit) {
        return RetrofitHttpsPinsRx.service.httpsUserLikePinsRx(bearer + " " + key, userId, limit);
    }

    private Observable<ListPinsBean> getPinsMax(String bearer, String key, String userId, int max, int limit) {
        return RetrofitHttpsPinsRx.service.httpsUserLikePinsMaxRx(bearer + " " + key, userId, max, limit);
    }
}
