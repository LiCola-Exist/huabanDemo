package licola.demo.com.huabandemo.My;


import android.os.Bundle;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.View.LoadingFooter;
import licola.demo.com.huabandemo.bean.PinsAndUserEntity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  14:46
 */
public class MyAttentionPinsFragment extends BaseRecyclerHeadFragment{
    private static final String TAG = "MyAttentionPinsFragment";
    //联网关键参数
    private int mMaxId;//下一次联网的pinsid开始
    private String mTokenType;
    private String mTokenAccess;

    //能显示三种状态的 footView
    LoadingFooter mFooterView;

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
    protected View setFootView() {
        if (mFooterView==null){
            mFooterView=new LoadingFooter(getContext());
        }
        mFooterView.setState(LoadingFooter.State.Loading);
        return null;
    }

    @Override
    protected View setHeadView() {
        return null;
    }

    private Observable<FollowingPinsBean> getMyFollowingPins(String bearer, String key, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsRx(bearer + " " + key,limit);
    }

    private Observable<FollowingPinsBean> getMyFollowingPinsMax(String bearer, String key, int maxId, int limit){
        return RetrofitHttpsPinsRx.service.httpsMyFollowingPinsMaxRx(bearer + " " + key,maxId,limit);
    }


}
