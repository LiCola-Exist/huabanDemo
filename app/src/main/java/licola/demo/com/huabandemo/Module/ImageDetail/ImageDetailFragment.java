package licola.demo.com.huabandemo.Module.ImageDetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

import org.greenrobot.eventbus.EventBus;

import licola.demo.com.huabandemo.API.Fragment.OnImageDetailFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.ImageDetailAPI;
import licola.demo.com.huabandemo.Adapter.RecyclerPinsHeadCardAdapter;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.TimeUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/03/26  19:05
 */
public class ImageDetailFragment extends
        BaseRecyclerHeadFragment<RecyclerPinsHeadCardAdapter, List<PinsMainEntity>> {
    private static final String TAG = "ImageDetailFragment";
    protected int mIndex = 1;//默认值为1

    @BindString(R.string.text_like_number)
    String mStringLikeNumber;
    @BindString(R.string.text_gather_number)
    String mStringGatherNumber;
    @BindString(R.string.text_image_describe_null)
    String mStringNullDescribe;

    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.httpRoot)
    String mHttpRoot;

    TextView tv_image_text;//图片的文字描述
    TextView tv_image_link;//链接
    TextView tv_image_gather;//被采集数量
    TextView tv_image_like;//被喜欢数量

    RelativeLayout mRLImageUser;//用户信息的父视图
    SimpleDraweeView img_image_user;//用户头像
    TextView tv_image_user;//用户名
    TextView tv_image_time;//采集时间
    ImageButton ibtn_image_user_chevron_right;

    RelativeLayout mRLImageBoard;//画板信息的父视图
    SimpleDraweeView img_image_board_1;//归属的画板图片
    SimpleDraweeView img_image_board_2;
    SimpleDraweeView img_image_board_3;
    SimpleDraweeView img_image_board_4;
    TextView tv_image_board;//归属的画板名称
    ImageButton ibtn_image_board_chevron_right;

    private PinsMainEntity mPinsBean;

    private String mBoardId;
    private String mUserId;

    private String mBoardName;
    private String mUserName;

    private OnImageDetailFragmentInteractionListener mListener;


    @Override
    protected String getTAG() {
        return this.toString();
    }

    //只需要一个Key作为关键字联网
    public static ImageDetailFragment newInstance(String key) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setViewDrawable();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnImageDetailFragmentInteractionListener) {
            mListener = (OnImageDetailFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }

        if (context instanceof ImageDetailActivity) {
            mAuthorization = ((ImageDetailActivity) context).mAuthorization;
            mPinsBean = ((ImageDetailActivity) context).mPinsBean;
        }
    }


    //点击事件 Fragment 不做任何的跳转处理 分发给Activity处理
    @Override
    protected void initListener() {
        super.initListener();

        mAdapter.setOnClickItemListener(new RecyclerPinsHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsMainEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickPinsItemText(bean, view);
            }

            @Override
            public void onClickInfoGather(PinsMainEntity bean, View view) {
                Logger.d();
            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {
                Logger.d();

            }
        });

        tv_image_link.setOnClickListener(v -> {
            String link = (String) v.getTag();
            Logger.d("link=" + link);
            mListener.onClickImageLink(link);
        });

        tv_image_gather.setOnClickListener(v -> Logger.d());
//
        tv_image_like.setOnClickListener(v -> Logger.d());

        mRLImageUser.setOnClickListener(v -> {
            mListener.onClickUserField(mUserId, mUserName);
        });

        mRLImageBoard.setOnClickListener(v -> {
            mListener.onClickBoardField(mBoardId, mBoardName);
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setViewDrawable() {
        initTintDrawable(tv_image_gather, R.drawable.ic_camera_black_24dp);
        initTintDrawable(tv_image_like, R.drawable.ic_favorite_black_24dp);
        initTintDrawable(ibtn_image_user_chevron_right, R.drawable.ic_chevron_right_black_36dp);
        initTintDrawable(ibtn_image_board_chevron_right, R.drawable.ic_chevron_right_black_36dp);
        initTintDrawable(tv_image_link, R.drawable.ic_insert_link_black_24dp);
    }

    private void initTintDrawable(View view, int resId) {

        if (view instanceof ImageButton) {
            ((ImageButton) view).setImageDrawable
                    (CompatUtils.getTintListDrawable(getContext(), resId, R.color.tint_list_grey));
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(
                    CompatUtils.getTintListDrawable(getContext(), resId, R.color.tint_list_grey),
                    null,
                    null,
                    null);

        }
    }

    @Override
    protected Subscription getHttpOther() {
        return RetrofitClient.createService(ImageDetailAPI.class)
                .httpsPinsDetailRx(mAuthorization, mKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinsDetailBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        //网络错误 使用本地缓存
                        setImageInfo(mPinsBean);
                    }

                    @Override
                    public void onNext(PinsDetailBean pinsDetailBean) {
                        Logger.d();
                        //联网成功使用 正确的网络数据
                        setImageInfo(pinsDetailBean);
                    }
                });

    }

    @Override
    protected Subscription getHttpFirst() {
        return RetrofitClient.createService(ImageDetailAPI.class)
                .httpPinsRecommendRx(mAuthorization, mKey, mIndex, mLimit)
                .filter(pinsEntities -> pinsEntities.size() > 0)
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread()) //订阅者的运行线程 在main线程中才能修改UI
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
                        mAdapter.addListNotify(pinsEntities);
                        mIndex++;//联网成功 +1
                    }
                });
    }

    @Override
    protected Subscription getHttpScroll() {
        return getHttpFirst();
    }


    @Override
    protected View getHeadView() {

        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_image_detail_info_head, mRecyclerView, false);
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
        tv_image_text = ButterKnife.findById(headView, R.id.tv_image_text);
        tv_image_link = ButterKnife.findById(headView, R.id.tv_image_link);
        tv_image_gather = ButterKnife.findById(headView, R.id.tv_image_gather);
        tv_image_like = ButterKnife.findById(headView, R.id.tv_image_like);
        tv_image_user = ButterKnife.findById(headView, R.id.tv_image_user);
        tv_image_time = ButterKnife.findById(headView, R.id.tv_image_about);
        tv_image_board = ButterKnife.findById(headView, R.id.tv_image_board);

        img_image_user = ButterKnife.findById(headView, R.id.img_image_user);
        img_image_board_1 = ButterKnife.findById(headView, R.id.img_image_board_1);
        img_image_board_2 = ButterKnife.findById(headView, R.id.img_image_board_2);
        img_image_board_3 = ButterKnife.findById(headView, R.id.img_image_board_3);
        img_image_board_4 = ButterKnife.findById(headView, R.id.img_image_board_4);

        ibtn_image_board_chevron_right = ButterKnife.findById(headView, R.id.ibtn_image_board_chevron_right);
        ibtn_image_user_chevron_right = ButterKnife.findById(headView, R.id.ibtn_image_user_chevron_right);

        mRLImageUser = ButterKnife.findById(headView, R.id.relativelayout_image_user);
        mRLImageBoard = ButterKnife.findById(headView, R.id.relativelayout_image_board);
    }

    //图像文字信息 填充
    private void setImageTextInfo(String raw, String link, String source, int gather, int like) {
        if (!TextUtils.isEmpty(raw)) {
            tv_image_text.setText(raw);
        } else {
            tv_image_text.setText(mStringNullDescribe);
        }

        if ((!TextUtils.isEmpty(link)) && (!TextUtils.isEmpty(source))) {
            tv_image_link.setText(source);
            tv_image_link.setTag(link);
        } else {
            tv_image_link.setVisibility(View.GONE);
        }

        tv_image_gather.setText(String.format(mStringGatherNumber, gather));
        setTvImageLikeNumber(like);
    }

    private void setTvImageLikeNumber(int like) {
        tv_image_like.setText(String.format(mStringLikeNumber, like));
    }

    //图像的用户信息 填充
    private void setImageUserInfo(String url_head, String username, int created_time) {
        //因为图片来源不定 需要做处理
        if (url_head != null) {
            if (!url_head.contains(mHttpRoot)) {
                url_head = String.format(mFormatUrlSmall, url_head);
            }
            //用户名头像加载
            new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_user, url_head)
                    .setPlaceHolderImage(CompatUtils.getTintDrawable(getContext(), R.drawable.ic_account_circle_gray_48dp, Color.GRAY))
                    .setIsCircle(true)
                    .build();
        }

        //用户名
        tv_image_user.setText(username);
        //创建时间
        tv_image_time.setText(TimeUtils.getTimeDifference(created_time, System.currentTimeMillis()));
    }

    private void setImageBoardInfo(String url1, String url2, String url3, String url4, String board_name) {
        //画板名称
        if (!TextUtils.isEmpty(board_name)) {
            tv_image_board.setText(board_name);
        } else {
            tv_image_board.setText("暂无画板信息");
        }

        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_1, url1)
                .setIsRadius(true, 5)
                .build();
//        Logger.d("id1=" + img_image_board_1.getId());
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_2, url2)
                .setIsRadius(true, 5)
                .build();
//        Logger.d("id2=" + img_image_board_2.getId());
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_3, url3)
                .setIsRadius(true, 5)
                .build();
//        Logger.d("id3=" + img_image_board_3.getId());
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_4, url4)
                .setIsRadius(true, 5)
                .build();
//        Logger.d("id4=" + img_image_board_4.getId());

    }

    /**
     * 使用网络数据填充UI控件 赋值全局变量 填充UI控件
     *
     * @param pinsDetailBean 网络bean
     */
    private void setImageInfo(PinsDetailBean pinsDetailBean) {
        PinsDetailBean.PinBean bean = pinsDetailBean.getPin();

        mBoardId = String.valueOf(bean.getBoard_id());
        mUserId = String.valueOf(bean.getUser_id());
        mBoardName = bean.getBoard().getTitle();
        mUserName = bean.getUser().getUrlname();

        //描述
        setImageTextInfo(bean.getRaw_text(),
                bean.getLink(),
                bean.getSource(),
                bean.getRepin_count(),
                bean.getLike_count()
        );

        //用户信息
        String url = bean.getUser().getAvatar();

        setImageUserInfo(url,
                bean.getUser().getUsername(),
                bean.getCreated_at()
        );

        //画板信息
        String url1 = String.format(mFormatUrlSmall, bean.getBoard().getPins().get(0).getFile().getKey());
        String url2 = String.format(mFormatUrlSmall, bean.getBoard().getPins().get(1).getFile().getKey());
        String url3 = String.format(mFormatUrlSmall, bean.getBoard().getPins().get(2).getFile().getKey());
        String url4 = String.format(mFormatUrlSmall, bean.getBoard().getPins().get(3).getFile().getKey());
        setImageBoardInfo(url1, url2, url3, url4, bean.getBoard().getTitle());
    }

    /**
     * 网络错误使用本地缓存 赋值全局变量 填充UI控件
     *
     * @param bean 本地缓存的bean
     */
    private void setImageInfo(PinsMainEntity bean) {

        mBoardId = String.valueOf(bean.getBoard_id());
        mUserId = String.valueOf(bean.getUser_id());
        mBoardName = bean.getBoard().getTitle();
        mUserName = bean.getUser().getUrlname();

        //描述
        setImageTextInfo(bean.getRaw_text(),
                bean.getLink(),
                bean.getSource(),
                bean.getRepin_count(),
                bean.getLike_count()
        );

        //用户信息
        String url = bean.getUser().getAvatar();
        setImageUserInfo(url,
                bean.getUser().getUsername(),
                bean.getCreated_at()
        );

        //画板信息
        String url_file = String.format(mFormatUrlSmall, bean.getFile().getKey());
        setImageBoardInfo(url_file, url_file, url_file, url_file, bean.getBoard().getTitle());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
