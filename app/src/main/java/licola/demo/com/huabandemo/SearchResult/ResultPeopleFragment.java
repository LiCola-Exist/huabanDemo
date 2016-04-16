package licola.demo.com.huabandemo.SearchResult;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import licola.demo.com.huabandemo.API.OnPeopleFragmentInteraction;
import licola.demo.com.huabandemo.Adapter.RecyclerPeopleAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.SearchResult.SearchPeopleListBean.UsersBean;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/04/05  22:09
 */
public class ResultPeopleFragment extends
        BaseRecyclerHeadFragment<RecyclerPeopleAdapter, List<UsersBean>> {

    private static final String TAG = "ResultPeopleFragment";
    private int mIndex = 1;//联网的起始页 默认1

    private OnPeopleFragmentInteraction<UsersBean> mListener;

    @Override
    protected String getTAG() {
        return this.toString();
    }


    //只需要一个Key作为关键字联网
    public static ResultPeopleFragment newInstance(String key) {
        ResultPeopleFragment fragment = new ResultPeopleFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Subscription getHttpFirst() {
        return RetrofitService.createAvatarService()
                .httpPeopleSearchRx(mAuthorization,mKey,mIndex,mLimit)
                .map(new Func1<SearchPeopleListBean, List<UsersBean>>() {
                    @Override
                    public List<UsersBean> call(SearchPeopleListBean searchPeopleListBean) {
                        return searchPeopleListBean.getUsers();
                    }
                })
                .filter(getFilterFunc1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UsersBean>>() {
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
                    public void onNext(List<UsersBean> usersBeen) {
                        mAdapter.addList(usersBeen);
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
        if (context instanceof OnPeopleFragmentInteraction) {
            mListener = (OnPeopleFragmentInteraction<UsersBean>) context;
        } else {
            throwRuntimeException(context);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnClickItemListener(new RecyclerPeopleAdapter.OnAdapterListener() {
            @Override
            public void onClickUser(UsersBean bean, View view) {
                mListener.onClickItemUser(bean, view);
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
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerPeopleAdapter setAdapter() {
        return new RecyclerPeopleAdapter(mRecyclerView);
    }


}
