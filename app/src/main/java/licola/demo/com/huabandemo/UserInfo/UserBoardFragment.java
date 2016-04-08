package licola.demo.com.huabandemo.UserInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardUserAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitHttpsPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/07  20:34
 */
public class UserBoardFragment extends BaseRecyclerHeadFragment<RecyclerBoardUserAdapter, List<UserBoardItemBean>> {
    private static final String TAG = "UserBoardFragment";
    private int mMaxId;
    private boolean isMe;
    private String mTokenType;
    private String mTokenAccess;

    private OnBoardFragmentInteractionListener<UserBoardItemBean> mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static UserBoardFragment newInstance(String key) {
        UserBoardFragment fragment = new UserBoardFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isMe = ((UserInfoActivity) getActivity()).isMe;
        mTokenAccess = ((UserInfoActivity) getActivity()).mTokenAccess;
        mTokenType = ((UserInfoActivity) getActivity()).mTokenType;
    }

    @Override
    protected Subscription getHttpFirst() {
        return getBoard(mTokenType, mTokenAccess, mKey, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d();
                        checkException(e);
                    }

                    @Override
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        Logger.d();
                        mAdapter.addList(userBoardItemBeen);
                        mMaxId = getMax(userBoardItemBeen);
                    }
                });


    }

    private int getMax(List<UserBoardItemBean> been) {
        return been.get(been.size() - 1).getBoard_id();
    }

    @Override
    protected Subscription getHttpScroll() {
        return getBoardMax(mTokenType, mTokenAccess, mKey, mMaxId, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
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
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        Logger.d();
                        mAdapter.addList(userBoardItemBeen);
                        mMaxId = getMax(userBoardItemBeen);
                    }
                });
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBoardFragmentInteractionListener){
            mListener= (OnBoardFragmentInteractionListener<UserBoardItemBean>) context;
        }else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardUserAdapter.onAdapterListener() {
            @Override
            public void onClickImage(UserBoardItemBean bean, View view) {
                mListener.onClickBoardItemImage(bean,view);
            }

            @Override
            public void onClickOperate(UserBoardItemBean bean, View view) {
                mListener.onClickBoardItemOperate(bean,view);
            }
        });
    }

    public Observable<UserBoardListBean> getBoard(String bearer, String key, String userId, int limit) {
        return RetrofitHttpsPinsRx.service.httpsUserBoardRx(bearer + " " + key, userId, limit);
    }

    public Observable<UserBoardListBean> getBoardMax(String bearer, String key, String userId, int max, int limit) {
        return RetrofitHttpsPinsRx.service.httpsUserBoardMaxRx(bearer + " " + key, userId, max, limit);
    }

    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerBoardUserAdapter setAdapter() {
        return new RecyclerBoardUserAdapter(mRecyclerView, isMe);
    }


}
