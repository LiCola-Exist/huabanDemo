package licola.demo.com.huabandemo.Module.User;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.Dialog.OnEditDialogInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.OperateAPI;
import licola.demo.com.huabandemo.API.HttpsAPI.UserAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardUserAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.DialogUtils;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Widget.MyDialog.BoardEditDialogFragment;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/07  20:34
 */
public class UserBoardFragment extends
        BaseRecyclerHeadFragment<RecyclerBoardUserAdapter, List<UserBoardItemBean>>
        implements OnEditDialogInteractionListener {
    private static final String TAG = "UserBoardFragment";
    private int mMaxId;
    private boolean isMe;

    private OnBoardFragmentInteractionListener<UserBoardItemBean> mListener;
    private OnRefreshFragmentInteractionListener mRefreshListener;

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
    }

    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(UserAPI.class)
                .httpsUserBoardRx(mAuthorization, mKey, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(UserBoardListBean::getBoards)
                .filter(getFilterFunc1())
                .subscribe(new Action1<List<UserBoardItemBean>>() {
                    @Override
                    public void call(List<UserBoardItemBean> userBoardItemBeen) {
                        Logger.d();
                        mAdapter.setListNotify(userBoardItemBeen);
                        mMaxId = getMax(userBoardItemBeen);
                    }
                }, getErrorAction(), getCompleteAction());


    }

    private int getMax(List<UserBoardItemBean> been) {
        return been.get(been.size() - 1).getBoard_id();
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
                .httpsUserBoardMaxRx(mAuthorization, mKey, mMaxId, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(UserBoardListBean::getBoards)
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
                        mAdapter.addListNotify(userBoardItemBeen);
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
        if ((context instanceof OnBoardFragmentInteractionListener) && (context instanceof OnRefreshFragmentInteractionListener)) {
            mListener = (OnBoardFragmentInteractionListener<UserBoardItemBean>) context;
            mRefreshListener = (OnRefreshFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof UserActivity) {
            mAuthorization = ((UserActivity) context).mAuthorization;
            isMe = ((UserActivity) context).isMe;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardUserAdapter.onAdapterListener() {
            @Override
            public void onClickImage(UserBoardItemBean bean, View view) {
                mListener.onClickBoardItemImage(bean, view);
            }

            @Override
            public void onClickOperate(UserBoardItemBean bean, View view) {
//                mListener.onClickBoardItemOperate(bean, view);
                //点击图片操作项 本地Fragment处理 不传递事件
                handleOperate(bean, view);
            }
        });
    }

    private void handleOperate(UserBoardItemBean bean, View view) {
        Logger.d();
        if (isMe) {
            //如果是我的画板 弹出编辑对话框
            BoardEditDialogFragment fragment = BoardEditDialogFragment.create(String.valueOf(bean.getBoard_id()),
                    bean.getTitle(), bean.getDescription(), bean.getCategory_id());
            fragment.setListener(this);//注入已经实现接口的 自己
            fragment.show(getActivity().getSupportFragmentManager(), null);
        } else {
            //如果是其他用户的画板 直接操作
            Logger.d();
        }
    }


    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerBoardUserAdapter setAdapter() {
        return new RecyclerBoardUserAdapter(mRecyclerView, isMe);
    }


    @Override
    public void onDialogPositiveClick(String boardId, String name, String describe, String selectType) {
        Logger.d("name=" + name + " describe=" + describe + " selectPosition=" + selectType);

        RetrofitClient.createService(OperateAPI.class)
                .httpsEditBoard(mAuthorization, boardId, name, describe, selectType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRefreshListener.OnRefreshState(true);
                    }
                })
                .subscribe(getNextAction(), getErrorAction());
    }

    @NonNull
    private Action1<UserBoardSingleBean> getNextAction() {
        return new Action1<UserBoardSingleBean>() {
            @Override
            public void call(UserBoardSingleBean userBoardSingleBean) {
                getHttpFirst();
            }
        };
    }


    @Override
    public void onDialogNeutralClick(String boardId, String boardTitle) {
        Logger.d();

        DialogUtils.showDeleteDialog(getContext(), boardTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startDeleteBoard(boardId);
            }
        });
    }

    private void startDeleteBoard(String boardId) {

        RetrofitClient.createService(OperateAPI.class)
                .httpsDeleteBoard(mAuthorization, boardId, Constant.OPERATEDELETEBOARD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRefreshListener.OnRefreshState(true);
                    }
                })
                .subscribe(getNextAction(), getErrorAction());
    }
}
