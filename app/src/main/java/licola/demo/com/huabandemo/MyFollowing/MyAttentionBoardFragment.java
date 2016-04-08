package licola.demo.com.huabandemo.MyFollowing;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Bean.BoardPinsBean;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  21:39
 */
public class MyAttentionBoardFragment extends BaseRecyclerHeadFragment<RecyclerBoardAdapter, List<BoardPinsBean>> {
    private static final String TAG = "MyAttentionBoardFragment";

    private int mIndex = 1;//联网的起始页 默认1
    private String mTokenType;
    private String mTokenAccess;

    private OnBoardFragmentInteractionListener<BoardPinsBean> mListener;

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
    protected Subscription getHttpFirst() {

        return getMyFollowingBoard(mTokenType, mTokenAccess, mIndex, mLimit)
                .map(new Func1<FollowingBoardListBean, List<BoardPinsBean>>() {
                    @Override
                    public List<BoardPinsBean> call(FollowingBoardListBean followingBoardListBean) {
                        return followingBoardListBean.getBoards();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<BoardPinsBean>>() {
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
                    public void onNext(List<BoardPinsBean> followingBoardItemBeen) {
                        Logger.d();
                        mAdapter.addList(followingBoardItemBeen);
                        mIndex++;
                    }
                });
    }

    @Override
    protected Subscription getHttpScroll() {
        return getHttpFirst();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardAdapter.onAdapterListener() {
            @Override
            public void onClickImage(BoardPinsBean bean, View view) {
                Logger.d();
                mListener.onClickBoardItemImage(bean, view);
            }

            @Override
            public void onClickTextInfo(BoardPinsBean bean, View view) {
                Logger.d();
                mListener.onClickBoardItemOperate(bean, view);
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
    protected RecyclerBoardAdapter setAdapter() {
        return new RecyclerBoardAdapter(mRecyclerView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBoardFragmentInteractionListener) {
            mListener = (OnBoardFragmentInteractionListener<BoardPinsBean>) context;
        } else {
            throwRuntimeException(context);
        }
    }

    public Observable<FollowingBoardListBean> getMyFollowingBoard(String bearer, String key, int index, int limit) {
        return RetrofitHttpsPinsRx.service.httpsMyFollowingBoardRx(bearer + " " + key, index, limit);
    }
}
