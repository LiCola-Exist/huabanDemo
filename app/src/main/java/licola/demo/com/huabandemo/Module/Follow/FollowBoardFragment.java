package licola.demo.com.huabandemo.Module.Follow;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import butterknife.BindString;
import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.FollowingAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.BoardPinsBean;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/04  21:39
 */
public class FollowBoardFragment extends BaseRecyclerHeadFragment<RecyclerBoardAdapter, List<BoardPinsBean>> {
    private static final String TAG = "FollowBoardFragment";

    @BindString(R.string.snack_message_not_notify)
    String mStringNotNotify;

    private int mIndex = 1;//联网的起始页 默认1

    private OnBoardFragmentInteractionListener<BoardPinsBean> mListener;
    private OnRefreshFragmentInteractionListener mRefreshListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static FollowBoardFragment newInstance() {
        return new FollowBoardFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState!=null){
//            mAuthorization=savedInstanceState.getString("key1");
//            Logger.d(mAuthorization);
//        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("key1",mAuthorization);
//        Logger.d();
//    }

    @Override
    protected Subscription getHttpFirst() {

        return RetrofitClient.createService(FollowingAPI.class)
                .httpsMyFollowingBoardRx(mAuthorization, mIndex, mLimit)
                .map(followingBoardListBean -> followingBoardListBean.getBoards())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilterFunc1())
                .subscribe(new Action1<List<BoardPinsBean>>() {
                    @Override
                    public void call(List<BoardPinsBean> boardPinsBeen) {
                        Logger.d();
                        mAdapter.addListNotify(boardPinsBeen);
                        mIndex++;
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
        return () -> {
            Logger.d();
            mRefreshListener.OnRefreshState(false);
        };
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
                mListener.onClickBoardItemImage(bean, view);
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

        if ((context instanceof OnBoardFragmentInteractionListener) && (context instanceof OnRefreshFragmentInteractionListener)) {
            mListener = (OnBoardFragmentInteractionListener<BoardPinsBean>) context;
            mRefreshListener = (OnRefreshFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof FollowActivity) {
            String a=((FollowActivity) context).mAuthorization;
            if (a!=null){
                mAuthorization=a;
            }
            Logger.d(mAuthorization);
        }
    }

}
