package licola.demo.com.huabandemo.Module.BoardDetail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.Fragment.OnBoardDetailFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.BoardDetailAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/03/29  18:12
 */
public class BoardDetailFragment extends BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter, List<PinsMainEntity>> {

    private int mMaxId;

    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.text_board_describe_null)
    String mStringNullDescribe;
    @BindString(R.string.text_attention_number)
    String mStringAttentionNumber;
    @BindString(R.string.text_gather_number)
    String mStringGatherNumber;


    LinearLayout mLLBoardUser;
    SimpleDraweeView mImageUser;
    TextView mTVUserName;
    TextView mTVBoardDescribe;
    TextView mTVBoardAttention;
    TextView mTVBoardGather;

    private OnBoardDetailFragmentInteractionListener mListener;

    private String mStringUserKey;
    private String mStringUserTitle;

    @Override
    protected String getTAG() {
        return this.toString();
    }


    //只需要一个Key作为关键字联网
    public static BoardDetailFragment newInstance(String key) {
        BoardDetailFragment fragment = new BoardDetailFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initListener() {
        super.initListener();

        mLLBoardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
                mListener.onClickUserField(mStringUserKey, mStringUserTitle);
            }
        });//画板栏事件
        mAdapter.setOnClickItemListener(new RecyclerPinsHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);//发送bean 而不用知晓接受得类
                mListener.onClickPinsItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);//发送bean 而不用知晓接受得类
                mListener.onClickPinsItemText(bean, view);
            }

            @Override
            public void onClickInfoGather(PinsMainEntity bean, View view) {
                //某个图片的收集点击 在fragment中直接处理 不用跳转
                Logger.d();
            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {
                //某个图片的喜欢点击 在fragment中直接处理 不用跳转
                Logger.d();
            }
        });
    }


    @Override
    protected Subscription getHttpOther() {
        return RetrofitClient.createService(BoardDetailAPI.class)
                .httpsBoardDetailRx(mAuthorization, mKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardDetailBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        //// TODO: 2016/3/29 0029 从父activity应该能取出缓存 设值

                    }

                    @Override
                    public void onNext(BoardDetailBean boardDetailBean) {
                        mLLBoardUser.setEnabled(true);
                        mStringUserKey = String.valueOf(boardDetailBean.getBoard().getUser().getUser_id());
                        mStringUserTitle = boardDetailBean.getBoard().getUser().getUsername();
                        setBoardInfo(boardDetailBean);
                    }
                });
    }

    /**
     * 联网返回结果填充数据
     *
     * @param bean 联网返回的bean
     */
    private void setBoardInfo(BoardDetailBean bean) {

        mListener.onHttpBoardAttentionState(String.valueOf(bean.getBoard().getUser_id()), bean.getBoard().isFollowing());

        String url_head = String.format(mFormatUrlSmall, bean.getBoard().getUser().getAvatar());
        setBoardUserInfo(url_head, bean.getBoard().getUser().getUsername());
        setBoardTextInfo(bean.getBoard().getDescription(), bean.getBoard().getPin_count(), bean.getBoard().getFollow_count());
    }

    private void setBoardTextInfo(String describe, int gather, int attention) {
        if (!TextUtils.isEmpty(describe)) {
            mTVBoardDescribe.setText(describe);
        } else {
            mTVBoardDescribe.setText(mStringNullDescribe);
        }
        mTVBoardGather.setText(String.format(mStringGatherNumber, gather));
        mTVBoardAttention.setText(String.format(mStringAttentionNumber, attention));
    }

    private void setBoardUserInfo(String url_head, String username) {
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), mImageUser, url_head)
                .setIsCircle(true)
                .build();

        mTVUserName.setText(username);
    }

    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(BoardDetailAPI.class)
                .httpsBoardPinsRx(mAuthorization, mKey, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ListPinsBean, List<PinsMainEntity>>() {
                    @Override
                    public List<PinsMainEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<PinsMainEntity>>() {
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
                    public void onNext(List<PinsMainEntity> pinsEntities) {
                        mMaxId = getMaxId(pinsEntities);
                        mAdapter.addListNotify(pinsEntities);
                    }
                });
    }

    private int getMaxId(List<PinsMainEntity> result) {
        return result.get(result.size() - 1).getPin_id();
    }


    @Override
    protected Subscription getHttpScroll() {
        return RetrofitClient.createService(BoardDetailAPI.class)
                .httpsBoardPinsMaxRx(mAuthorization, mKey, mMaxId, mLimit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ListPinsBean, List<PinsMainEntity>>() {
                    @Override
                    public List<PinsMainEntity> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
                    }
                })
                .filter(getFilterFunc1())
                .subscribe(new Subscriber<List<PinsMainEntity>>() {
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
                    public void onNext(List<PinsMainEntity> pinsEntities) {
                        Logger.d();
                        mMaxId = getMaxId(pinsEntities);
                        mAdapter.addListNotify(pinsEntities);
                    }
                });

    }

    @Override
    protected View getHeadView() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_board_detail_info, mRecyclerView, false);
        findHeadView(headView);

        return headView;
    }

    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerPinsHeadCardAdapter setAdapter() {
        return new RecyclerPinsHeadCardAdapter(mRecyclerView);
    }

    private void findHeadView(View headView) {
        mLLBoardUser = ButterKnife.findById(headView, R.id.linearlayout_board_user);
        mImageUser = ButterKnife.findById(headView, R.id.img_board_user);
        mTVUserName = ButterKnife.findById(headView, R.id.tv_board_user);
        mTVBoardDescribe = ButterKnife.findById(headView, R.id.tv_board_describe);
        mTVBoardAttention = ButterKnife.findById(headView, R.id.tv_board_attention);
        mTVBoardGather = ButterKnife.findById(headView, R.id.tv_board_gather);

        mLLBoardUser.setEnabled(false);

    }


    /**
     * 检查绑定的activity 并得到引用 否则抛出异常
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPinsFragmentInteractionListener) {
            mListener = (OnBoardDetailFragmentInteractionListener) context;//在绑定时候得到listener的真正引用
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof BaseActivity) {
            mAuthorization = ((BaseActivity) context).mAuthorization;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
