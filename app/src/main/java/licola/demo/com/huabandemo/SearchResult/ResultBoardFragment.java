package licola.demo.com.huabandemo.SearchResult;

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
import licola.demo.com.huabandemo.View.LoadingFooter;
import rx.Observable;
import rx.Subscriber;
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
    protected void getHttpFirst() {
        getBoard(mKey,mIndex,mLimit)
                .map(new Func1<SearchBoardListBean, List<BoardPinsBean>>() {
                    @Override
                    public List<BoardPinsBean> call(SearchBoardListBean searchBoardListBean) {
                        return searchBoardListBean.getBoards();
                    }
                })
                .filter(getFilterFunc1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        mAdapter.addList(boardPinsBeen);
                        mIndex++;
                    }
                });
    }

    @Override
    protected void getHttpScroll() {
        getHttpFirst();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBoardFragmentInteractionListener) {
            mListener= (OnBoardFragmentInteractionListener<BoardPinsBean>) context;
        }else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerBoardAdapter.onAdapterListener() {
            @Override
            public void onClickImage(BoardPinsBean bean, View view) {
                mListener.onClickItemImage(bean,view);
            }

            @Override
            public void onClickTextInfo(BoardPinsBean bean, View view) {
                mListener.onClickItemText(bean,view);
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

    public Observable<SearchBoardListBean> getBoard(String key,int index,int limit){
        return RetrofitHttpsPinsRx.service.httpBoardSearchRx(key,index,limit);
    }
    
}
