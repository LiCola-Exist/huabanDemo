package licola.demo.com.huabandemo.My;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.Adapter.RecyclerHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.View.LoadingFooter;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  14:46
 */
public class MyAttentionPinsFragment extends BaseRecyclerHeadFragment<RecyclerHeadCardAdapter>{
    private static final String TAG = "MyAttentionPinsFragment";
    //联网关键参数
    private int mMaxId;//下一次联网的pinsId开始
    private String mTokenType;
    private String mTokenAccess;

    //能显示三种状态的 footView
    LoadingFooter mFooterView;

    private OnMyAttentionPinsFragmentInteractionListener mListener;

    public interface OnMyAttentionPinsFragmentInteractionListener{
        void onClickItemImage(PinsAndUserEntity bean, View view);

        void onClickItemText(PinsAndUserEntity bean, View view);
    }

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
        mTokenType= (String) SPUtils.get(getContext(), Constant.TOKENTYPE,"");
        mTokenAccess= (String) SPUtils.get(getContext(),Constant.TOKENACCESS,"");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyAttentionPinsFragmentInteractionListener){
            mListener= (OnMyAttentionPinsFragmentInteractionListener) context;
        }else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected void getHttpFirst() {
        getMyFollowingPins(mTokenType,mTokenAccess,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowingPinsBean>() {
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
                    public void onNext(FollowingPinsBean followingPinsBean) {
                        mAdapter.setList(followingPinsBean.getPins());
                        mMaxId=getMaxId(followingPinsBean.getPins());
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
                .subscribe(new Subscriber<FollowingPinsBean>() {
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
                    public void onNext(FollowingPinsBean followingPinsBean) {
                        mAdapter.addList(followingPinsBean.getPins());
                        mMaxId=getMaxId(followingPinsBean.getPins());
                    }
                });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerHeadCardAdapter.OnAdapterListener() {
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
    protected View setFootView() {
        if (mFooterView==null){
            mFooterView=new LoadingFooter(getContext());
        }
        mFooterView.setState(LoadingFooter.State.Loading);
        return mFooterView;
    }

    @Override
    protected View setHeadView() {
        return null;
    }

    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerHeadCardAdapter setAdapter() {
        return new RecyclerHeadCardAdapter(mRecyclerView);
    }

    private Observable<FollowingPinsBean> getMyFollowingPins(String bearer, String key, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsRx(bearer + " " + key,limit);
    }

    private Observable<FollowingPinsBean> getMyFollowingPinsMax(String bearer, String key, int maxId, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsMaxRx(bearer + " " + key,maxId,limit);
    }


}
