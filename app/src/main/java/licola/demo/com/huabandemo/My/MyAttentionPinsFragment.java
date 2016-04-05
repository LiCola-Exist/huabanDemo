package licola.demo.com.huabandemo.My;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.API.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.View.LoadingFooter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  14:46
 */
public class MyAttentionPinsFragment extends BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter,List<PinsAndUserEntity>>{
    private static final String TAG = "MyAttentionPinsFragment";
    //联网关键参数
    private int mMaxId;//下一次联网的pinsId开始
    private String mTokenType;
    private String mTokenAccess;


    private OnPinsFragmentInteractionListener mListener;


    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static MyAttentionPinsFragment newInstance() {
        MyAttentionPinsFragment fragment = new MyAttentionPinsFragment();
//        Bundle args = new Bundle();
//        args.putString(TYPE_KEY, type);
//        args.putString(TYPE_TITLE, title);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTokenType=((MyAttentionActivity)getActivity()).mTokenType;
        this.mTokenAccess=((MyAttentionActivity)getActivity()).mTokenAccess;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPinsFragmentInteractionListener){
            mListener= (OnPinsFragmentInteractionListener) context;
        }else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected void getHttpFirst() {
        getMyFollowingPins(mTokenType,mTokenAccess,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<FollowingPinsBean, List<PinsAndUserEntity>>() {
                    @Override
                    public List<PinsAndUserEntity> call(FollowingPinsBean followingPinsBean) {
                        return followingPinsBean.getPins();
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
                        mAdapter.setList(pinsAndUserEntities);
                        mMaxId=getMaxId(pinsAndUserEntities);
                    }
                });
    }

    private int getMaxId(List<PinsAndUserEntity> result) {
        return result.get(result.size() - 1).getPin_id();
    }

    @Override
    protected void getHttpScroll() {
        getMyFollowingPinsMax(mTokenType,mTokenAccess,mMaxId,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<FollowingPinsBean, List<PinsAndUserEntity>>() {
                    @Override
                    public List<PinsAndUserEntity> call(FollowingPinsBean followingPinsBean) {
                        return followingPinsBean.getPins();
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
                        mAdapter.setList(pinsAndUserEntities);
                        mMaxId=getMaxId(pinsAndUserEntities);
                    }
                });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerPinsHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickItemImage(bean,view);
            }

            @Override
            public void onClickTitleInfo(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickItemText(bean,view);
            }

            @Override
            public void onClickInfoGather(PinsAndUserEntity bean, View view) {
                Logger.d();
                //todo 收集时间 类内部处理不传递
            }

            @Override
            public void onClickInfoLike(PinsAndUserEntity bean, View view) {
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

    private Observable<FollowingPinsBean> getMyFollowingPins(String bearer, String key, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsRx(bearer + " " + key,limit);
    }

    private Observable<FollowingPinsBean> getMyFollowingPinsMax(String bearer, String key, int maxId, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsMaxRx(bearer + " " + key,maxId,limit);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mTokenType=null;
        this.mTokenAccess=null;
    }
}
