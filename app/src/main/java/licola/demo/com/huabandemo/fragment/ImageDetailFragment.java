package licola.demo.com.huabandemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.TimeUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.activity.ImageDetailActivity;
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.httpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.httpUtils.RetrofitPinsRx;
import licola.demo.com.huabandemo.view.LoadingFooter;
import licola.demo.com.huabandemo.view.recyclerview.RecyclerViewUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/03/26  19:05
 */
public class ImageDetailFragment extends BaseRecyclerHeadFragment {
    private static final String TAG = "ImageDetailFragment";
    protected int mIndex = 1;//默认值为1

    @BindString(R.string.text_image_like_number)
    String mStringLikeNumber;
    @BindString(R.string.text_image_gather_number)
    String mStringGatherNumber;

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
    SimpleDraweeView img_image_board;//归属的画板图片
    TextView tv_image_board;//归属的画板名称
    ImageButton ibtn_image_board_chevron_right;


    private PinsEntity mPinsBean;
    private String url_head;
    private String url_board;

    @Override
    public String getTAG() {
        return this.getClass().getSimpleName();
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
        url_head = mPinsBean.getUser().getAvatar();
        url_board = mPinsBean.getUser().getAvatar();
        RecyclerViewUtils.addFootView(mRecyclerView, new Button(getContext()));

        initTintDrawable(tv_image_gather, R.drawable.ic_explore_black_24dp);
        initTintDrawable(tv_image_like, R.drawable.ic_favorite_black_24dp);
        initTintDrawable(ibtn_image_user_chevron_right, R.drawable.ic_chevron_right_black_24dp);
        initTintDrawable(ibtn_image_board_chevron_right, R.drawable.ic_chevron_right_black_24dp);
        initTintDrawable(tv_image_link, R.drawable.ic_insert_link_black_24dp);
    }

    @Override
    protected void initListener() {
        super.initListener();

        tv_image_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = (String) v.getTag();
                Logger.d("link" + link);
                //打开选择浏览器 再浏览界面

                Uri uri = Uri.parse(link);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });

        mRLImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
            }
        });

        mRLImageBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setImageTextInfo(mPinsBean);

        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_user, url_head)
                .setIsCircle(true)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(getContext(), img_image_board, url_board)
                .setIsRadius(true)
                .build();
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
    protected void getHttpFirst() {
        getRecommend(mKey, mIndex, mLimit)
                .filter(new Func1<List<PinsEntity>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsEntity> pinsEntities) {
                        return pinsEntities.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(AndroidSchedulers.mainThread()) //订阅者的运行线程 在main线程中才能修改UI
                .subscribe(new Subscriber<List<PinsEntity>>() {
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
                    public void onNext(List<PinsEntity> pinsEntities) {
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


    private void findHeadView(View headView) {
        tv_image_text = ButterKnife.findById(headView, R.id.tv_image_text);
        tv_image_link = ButterKnife.findById(headView, R.id.tv_image_link);
        tv_image_gather = ButterKnife.findById(headView, R.id.tv_image_gather);
        tv_image_like = ButterKnife.findById(headView, R.id.tv_image_like);
        tv_image_user = ButterKnife.findById(headView, R.id.tv_image_user);
        tv_image_time = ButterKnife.findById(headView, R.id.tv_image_time);
        tv_image_board = ButterKnife.findById(headView, R.id.tv_image_board);

        img_image_user = ButterKnife.findById(headView, R.id.img_image_user);
        img_image_board = ButterKnife.findById(headView, R.id.img_image_board);

        ibtn_image_board_chevron_right = ButterKnife.findById(headView, R.id.ibtn_image_board_chevron_right);
        ibtn_image_user_chevron_right = ButterKnife.findById(headView, R.id.ibtn_image_user_chevron_right);

        mRLImageUser = ButterKnife.findById(headView, R.id.relativelayout_image_user);
        mRLImageBoard = ButterKnife.findById(headView, R.id.relativelayout_image_board);
    }


    private void setImageTextInfo(PinsEntity bean) {
        //描述
        String raw = bean.getRaw_text();
        if (!TextUtils.isEmpty(raw)) {
            tv_image_text.setText(raw);
        } else {
            tv_image_text.setText("图片暂无描述");
        }

        //链接
        String link = bean.getLink();
        String source = bean.getSource();
        if ((!TextUtils.isEmpty(link)) && (!TextUtils.isEmpty(source))) {
            tv_image_link.setText(source);
            tv_image_link.setTag(link);
        } else {
            tv_image_link.setVisibility(View.GONE);
        }

        tv_image_gather.setText(String.format(mStringGatherNumber, bean.getRepin_count()));
        tv_image_like.setText(String.format(mStringLikeNumber, bean.getLike_count()));

        //采集时间
        String dTime = TimeUtils.getTimeDifference(bean.getCreated_at(), System.currentTimeMillis());
        tv_image_time.setText(dTime);

        //用户名
        String user = bean.getUser().getUsername();
        tv_image_user.setText(user);

        //画板名称
        String board_title = bean.getBoard().getTitle();
        if (!TextUtils.isEmpty(board_title)) {
            tv_image_board.setText(board_title);
        } else {
            tv_image_board.setText("暂无画板信息");
        }


    }

    private Observable<List<PinsEntity>> getRecommend(String pinsId, int page, int limit) {
        return RetrofitPinsRx.service.httpPinsRecommend(pinsId, page, limit);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HuaBanApplication.getInstance().getRefwatcher().watch(this);
    }
}
