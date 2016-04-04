package licola.demo.com.huabandemo.ImageDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import de.greenrobot.event.EventBus;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Base.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.TimeUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.Adapter.RecyclerHeadCardAdapter;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitPinsRx;
import licola.demo.com.huabandemo.View.LoadingFooter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/03/26  19:05
 */
public class ImageDetailFragment extends BaseRecyclerHeadFragment<RecyclerHeadCardAdapter> {
    private static final String TAG = "ImageDetailFragment";
    protected int mIndex = 1;//默认值为1

    @BindString(R.string.text_image_like_number)
    String mStringLikeNumber;
    @BindString(R.string.text_image_gather_number)
    String mStringGatherNumber;
    @BindString(R.string.text_image_describe_null)
    String mStringNullDescribe;

    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.image_suffix_small)
    String mUrlSmallSuffix;
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

    private PinsAndUserEntity mPinsBean;

    private String mBoardId;
    private String mUserId;

    private String mBoardName;
    private String mUserName;

    private OnImageDetailFragmentInteractionListener mListener;

    public interface OnImageDetailFragmentInteractionListener {
        void onClickItemImage(PinsAndUserEntity bean, View view);

        void onClickItemText(PinsAndUserEntity bean, View view);

        void onClickBoardField(String key, String title);

        void onClickUserField(String key,String title);
    }

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
        this.mPinsBean = ((ImageDetailActivity) getActivity()).mPinsBean;//取出Activity的全局变量

        setViewDrawable();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnImageDetailFragmentInteractionListener) {
            mListener = (OnImageDetailFragmentInteractionListener) context;
        } else {
            throwRuntimeException(context);
        }
    }



    @Override
    protected void initListener() {
        super.initListener();

        mAdapter.setOnClickItemListener(new RecyclerHeadCardAdapter.OnAdapterListener() {
            @Override
            public void onClickImage(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickItemImage(bean, view);
            }

            @Override
            public void onClickTitleInfo(PinsAndUserEntity bean, View view) {
                EventBus.getDefault().postSticky(bean);
                mListener.onClickItemText(bean, view);
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

        /**
         * 警告：用户可能没有任何应用处理您发送到 startActivity() 的隐式 Intent。
         * 如果出现这种情况，则调用将会失败，且应用会崩溃。
         * 要验证 Activity 是否会接收 Intent，请对 Intent 对象调用 resolveActivity()。
         * 如果结果为非空，则至少有一个应用能够处理该 Intent，且可以安全调用 startActivity()。
         * 如果结果为空，则不应使用该 Intent。如有可能，您应禁用发出该 Intent 的功能。
         */
        tv_image_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = (String) v.getTag();
                Logger.d("link" + link);
                //打开选择浏览器 再浏览界面
                Uri uri = Uri.parse(link);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                if (it.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivity(it);
                }
            }
        });

        tv_image_gather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
            }
        });

        tv_image_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
            }
        });

        mRLImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
                mListener.onClickUserField(mUserId,mUserName);
            }
        });

        mRLImageBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
//                BoardDetailActivity.launch(getActivity(), mBoardId, mBoardName);
                mListener.onClickBoardField(mBoardId,mBoardName);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setViewDrawable() {
        initTintDrawable(tv_image_gather, R.drawable.ic_explore_black_24dp);
        initTintDrawable(tv_image_like, R.drawable.ic_favorite_black_24dp);
        initTintDrawable(ibtn_image_user_chevron_right, R.drawable.ic_chevron_right_black_24dp);
        initTintDrawable(ibtn_image_board_chevron_right, R.drawable.ic_chevron_right_black_24dp);
        initTintDrawable(tv_image_link, R.drawable.ic_insert_link_black_24dp);
    }

    private void initTintDrawable(View view, int resId) {

        if (view instanceof ImageButton) {
            ((ImageButton) view).setImageDrawable
                    (Utils.getTintCompatDrawable(getContext(), resId, R.color.tint_list_grey));
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(
                    Utils.getTintCompatDrawable(getContext(), resId, R.color.tint_list_grey),
                    null,
                    null,
                    null);

        }
    }

    @Override
    protected void getHttpOther() {
        getPinsDetail(mKey)
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
    protected void getHttpFirst() {

        getRecommend(mKey, mIndex, mLimit)
                .filter(new Func1<List<PinsAndUserEntity>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsAndUserEntity> pinsEntities) {
                        return pinsEntities.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread()) //订阅者的运行线程 在main线程中才能修改UI
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
                    public void onNext(List<PinsAndUserEntity> pinsEntities) {
                        mAdapter.addList(pinsEntities);
                        mIndex++;//联网成功 +1
                    }
                });
    }

    @Override
    protected void getHttpScroll() {
        getHttpFirst();
    }

    @Override
    protected View setFootView() {
        LoadingFooter loadingFooter = new LoadingFooter(getContext());
        loadingFooter.setState(LoadingFooter.State.Loading);
        return loadingFooter;
    }

    @Override
    protected View setHeadView() {

        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_image_detail_info, mRecyclerView, false);
        findHeadView(headView);

        return headView;
    }

    @Override
    protected int getAdapterPosition() {
        return mAdapter.getAdapterPosition();
    }

    @Override
    protected RecyclerHeadCardAdapter setAdapter() {
        return new RecyclerHeadCardAdapter(mRecyclerView);
    }


    private void findHeadView(View headView) {
        tv_image_text = ButterKnife.findById(headView, R.id.tv_image_text);
        tv_image_link = ButterKnife.findById(headView, R.id.tv_image_link);
        tv_image_gather = ButterKnife.findById(headView, R.id.tv_image_gather);
        tv_image_like = ButterKnife.findById(headView, R.id.tv_image_like);
        tv_image_user = ButterKnife.findById(headView, R.id.tv_image_user);
        tv_image_time = ButterKnife.findById(headView, R.id.tv_image_time);
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
        tv_image_like.setText(String.format(mStringLikeNumber, like));
    }

    //图像的用户信息 填充
    private void setImageUserInfo(String url_head, String username, int created_time) {
        //用户名头像加载
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_user, url_head)
                .setIsCircle(true)
                .build();
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
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_2, url2)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_3, url3)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board_4, url4)
                .build();

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
        //如果是花瓣本地服务器的图片 不包含只有图片的key 加载时需要添加http头
        //否则 可以直接使用
        if (!url.contains(mHttpRoot)) {
            url = String.format(mFormatUrlSmall, url);
        }
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
    private void setImageInfo(PinsAndUserEntity bean) {

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
        //如果是花瓣本地服务器的图片 不包含只有图片的key 加载时需要添加http头
        //否则 可以直接使用
        if (!url.contains(mHttpRoot)) {
            url = String.format(mFormatUrlSmall, url);
        }
        setImageUserInfo(url,
                bean.getUser().getUsername(),
                bean.getCreated_at()
        );

        //画板信息
        String url_file = String.format(mFormatUrlSmall, bean.getFile().getKey());
        setImageBoardInfo(url_file, url_file, url_file, url_file, bean.getBoard().getTitle());

    }

    private Observable<PinsDetailBean> getPinsDetail(String pinsId) {
        return RetrofitPinsRx.service.httpPinsDetailRx(pinsId);
    }

    private Observable<List<PinsAndUserEntity>> getRecommend(String pinsId, int page, int limit) {
        return RetrofitPinsRx.service.httpPinsRecommendRx(pinsId, page, limit);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HuaBanApplication.getInstance().getRefwatcher().watch(this);
    }
}
