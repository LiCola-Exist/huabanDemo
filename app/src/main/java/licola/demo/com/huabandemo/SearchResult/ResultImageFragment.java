package licola.demo.com.huabandemo.SearchResult;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.API.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitPinsRx;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.View.LoadingFooter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/05  16:48
 */
public class ResultImageFragment extends BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter> {
    private static final String TAG = "ResultImageFragment";

    private int mIndex = 1;//联网的起始页 默认1

    //能显示三种状态的 footView
    LoadingFooter mFooterView;

    private OnPinsFragmentInteractionListener mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }

    //只需要一个Key作为关键字联网
    public static ResultImageFragment newInstance(String key) {
        ResultImageFragment fragment = new ResultImageFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getHttpFirst() {
        getSearchImage(mKey,mIndex,mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<SearchImageBean, List<PinsAndUserEntity>>() {
                    @Override
                    public List<PinsAndUserEntity> call(SearchImageBean searchImageBean) {
                        return searchImageBean.getPins();//取出list对象
                    }
                })
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
                        Logger.d();
                        mAdapter.addList(pinsAndUserEntities);
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
        if (context instanceof OnPinsFragmentInteractionListener){
            mListener= (OnPinsFragmentInteractionListener) context;
        }else {
            throwRuntimeException(context);
        }
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
            }

            @Override
            public void onClickInfoLike(PinsAndUserEntity bean, View view) {
                Logger.d();
            }
        });
    }

    @Override
    protected View setFootView() {
        if (mFooterView == null) {
            mFooterView = new LoadingFooter(getContext());
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
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }

    private Observable<SearchImageBean> getSearchImage(String key, int index, int limit) {
        return RetrofitPinsRx.service.httpImageSearchRx(key, index, limit);
    }
}
