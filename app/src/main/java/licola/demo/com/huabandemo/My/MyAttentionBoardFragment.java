package licola.demo.com.huabandemo.My;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.View.LoadingFooter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  21:39
 */
public class MyAttentionBoardFragment extends BaseRecyclerHeadFragment<RecyclerBoardAdapter> {
    private static final String TAG = "MyAttentionBoardFragment";

    private int mIndex = 1;//联网的起始页 默认1
    private String mTokenType;
    private String mTokenAccess;

    //能显示三种状态的 footView
    LoadingFooter mFooterView;

    private OnBoardFragmentInteractionListener<FollowingBoardItemBean> mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static MyAttentionBoardFragment newInstance() {
        MyAttentionBoardFragment fragment = new MyAttentionBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTokenType = ((MyAttentionActivity) getActivity()).mTokenType;
        this.mTokenAccess = ((MyAttentionActivity) getActivity()).mTokenAccess;
    }

    @Override
    protected void getHttpFirst() {
        getMyFollowingBoard(mTokenType, mTokenAccess, mIndex, mLimit)
                .map(new Func1<FollowingBoardListBean, List<FollowingBoardItemBean>>() {
                    @Override
                    public List<FollowingBoardItemBean> call(FollowingBoardListBean followingBoardListBean) {
                        return followingBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FollowingBoardItemBean>>() {
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
                    public void onNext(List<FollowingBoardItemBean> followingBoardItemBeen) {
                        mAdapter.addList(followingBoardItemBeen);
                        mIndex++;
                    }
                });
    }

    @Override
    protected void getHttpScroll() {
        getHttpFirst();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardAdapter.onAdapterListener() {
            @Override
            public void onClickImage(FollowingBoardItemBean bean, View view) {
                Logger.d();
                mListener.onClickItemImage(bean,view);
            }

            @Override
            public void onClickTextInfo(FollowingBoardItemBean bean, View view) {
                Logger.d();
                mListener.onClickItemText(bean,view);
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
    protected RecyclerBoardAdapter setAdapter() {
        return new RecyclerBoardAdapter(mRecyclerView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBoardFragmentInteractionListener){
            mListener= (OnBoardFragmentInteractionListener<FollowingBoardItemBean>) context;
        }else {
            throwRuntimeException(context);
        }
    }

    public Observable<FollowingBoardListBean> getMyFollowingBoard(String bearer, String key, int index, int limit) {
        return RetrofitHttpsPinsRx.service.httpsMyFollowingBoardRx(bearer + " " + key, index, limit);
    }
}
