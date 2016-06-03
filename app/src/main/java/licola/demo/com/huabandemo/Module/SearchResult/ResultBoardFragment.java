package licola.demo.com.huabandemo.Module.SearchResult;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.SearchAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerBoardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.BoardPinsBean;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Observable.ErrorHelper;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/05  17:05
 */
public class ResultBoardFragment extends BaseRecyclerHeadFragment<RecyclerBoardAdapter,List<BoardPinsBean>> {
    private static final String TAG = "ResultBoardFragment";
    
    private int mIndex = 1;//联网的起始页 默认1


    private OnBoardFragmentInteractionListener<BoardPinsBean> mListener;

    //只需要一个Key作为关键字联网
    public static ResultBoardFragment newInstance(String key) {
        ResultBoardFragment fragment = new ResultBoardFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }
    
    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(SearchAPI.class)
                .httpsBoardSearchRx(mAuthorization,mKey,mIndex,mLimit)
                .flatMap(new Func1<SearchBoardListBean, Observable<SearchBoardListBean>>() {
                    @Override
                    public Observable<SearchBoardListBean> call(SearchBoardListBean searchBoardListBean) {
                        return ErrorHelper.getCheckNetError(searchBoardListBean);
                    }
                })
                .map(new Func1<SearchBoardListBean, List<BoardPinsBean>>() {
                    @Override
                    public List<BoardPinsBean> call(SearchBoardListBean searchBoardListBean) {
                        return searchBoardListBean.getBoards();
                    }
                })
                .filter(getFilterFunc1())
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread())//订阅者的运行线程 在main线程中才能修改UI
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
                    public void onNext(List<BoardPinsBean> boardPinsBeen) {
                        Logger.d();
                        mAdapter.addListNotify(boardPinsBeen);
                        mIndex++;
                    }
                });
    }

    @Override
    protected Subscription getHttpScroll() {
        return getHttpFirst();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBoardFragmentInteractionListener) {
            mListener= (OnBoardFragmentInteractionListener<BoardPinsBean>) context;
        }else {
            throwRuntimeException(context);
        }

        if (context instanceof SearchResultActivity){
            mAuthorization=((SearchResultActivity) context).mAuthorization;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardAdapter.onAdapterListener() {
            @Override
            public void onClickImage(BoardPinsBean bean, View view) {
                mListener.onClickBoardItemImage(bean,view);
            }

            @Override
            public void onClickTextInfo(BoardPinsBean bean, View view) {
                mListener.onClickBoardItemImage(bean,view);
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

    
}
