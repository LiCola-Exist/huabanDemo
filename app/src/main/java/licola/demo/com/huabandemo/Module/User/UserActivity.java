package licola.demo.com.huabandemo.Module.User;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import licola.demo.com.huabandemo.API.Dialog.OnAddDialogInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.HttpsAPI.OperateAPI;
import licola.demo.com.huabandemo.API.HttpsAPI.UserAPI;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Base.BaseSwipeViewPagerActivity;
import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Module.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.Module.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.FastBlurUtil;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.Widget.MyDialog.BoardAddDialogFragment;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 用户界面 我和其他的用户界面用公用
 * 区别 在toolbar不同功能
 * 在于每个Fragment中的的Adapter中的item项目操作不同
 */
public class UserActivity
        extends BaseSwipeViewPagerActivity<BaseRecyclerHeadFragment>
        implements OnBoardFragmentInteractionListener<UserBoardItemBean>,
        OnPinsFragmentInteractionListener, OnAddDialogInteractionListener {
    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TITLE = "TYPE_TITLE";

    @BindColor(R.color.white)
    int mColorTabIndicator;

    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.httpRoot)
    String mHttpRoot;
    @BindString(R.string.text_fans_attention)
    String mFansFollowingFormat;

    @BindView(R.id.toolbar_user)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.collapsingtoolbar_user)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.linearlayout_user_info)
    LinearLayout mLayoutUser;
    @BindView(R.id.img_image_user)
    SimpleDraweeView mImageUser;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_location_job)
    TextView mTvUserLocationJob;
    @BindView(R.id.tv_user_friend)
    TextView mTvUserFriend;

    @BindView(R.id.tablayout_user)
    TabLayout mTabLayout;

    @BindView(R.id.fab_operate)
    FloatingActionButton mFabOperate;

    public String mKey;
    public String mTitle;
    public boolean isMe;
    public boolean isFollow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    public static void launch(Activity activity, String key, String title) {
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(TYPE_TITLE, title);
        intent.putExtra(TYPE_KEY, key);
        activity.startActivity(intent);
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected String[] getTitleList() {
        return getResources().getStringArray(R.array.title_user_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);//设置打开时的文字颜色
        //// TODO: 2016/5/29 0029
        if (isMe) {
            addSubscription(getMyBoardListInfo());
        }

        initView();

    }

    private void initView() {
        mTvUserFriend.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                CompatUtils.getTintDrawable(mContext,R.drawable.ic_chevron_right_white_24dp,Color.WHITE),
                null);
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        RxView.clicks(mFabOperate)
                .throttleFirst(Constant.throttDuration, TimeUnit.MILLISECONDS)//防止抖动处理
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startOperate();
                    }
                });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Logger.d("verticalOffset=" + verticalOffset + " getTotalScrollRange=" + appBarLayout.getTotalScrollRange());
                final float size = -verticalOffset;
                final float total = appBarLayout.getTotalScrollRange();
                double per = 1.0f - (size / total);
//                Logger.d("per" + per);
                mLayoutUser.setAlpha((float) per);

            }
        });
    }

    private void startOperate() {
        if (isMe) {
            Logger.d("is me add broad");
            showAddBoardDialog();
        } else {
            Logger.d("httpFollowUser");
            httpFollowUser();

        }
    }

    private void showAddBoardDialog() {
        BoardAddDialogFragment fragment = BoardAddDialogFragment.create();
        fragment.setListener(this);//注入已经实现接口的 自己
        fragment.show(getSupportFragmentManager(), null);
    }

    private void httpFollowUser() {
        String operate = isFollow ? Constant.OPERATEUNFOLLOW : Constant.OPERATEFOLLOW;

        RetrofitClient.createService(OperateAPI.class)
                .httpsFollowUserOperate(mAuthorization, mKey, operate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowUserOperateBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                        int res = isFollow ? R.drawable.ic_done_black_24dp : R.drawable.ic_loyalty_black_24dp;
                        setFabDrawableAnimator(res, mFabOperate);
                        // TODO: 2016/5/29 0029 如果当前正在显示画板Fragment 需要刷新操作

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        checkException(e, mAppBar);
                        setFabDrawableAnimator(R.drawable.ic_report_black_24dp, mFabOperate);
                    }

                    @Override
                    public void onNext(FollowUserOperateBean followUserOperateBean) {
                        Logger.d();
                        isFollow = !isFollow;
                    }
                });
    }

    /**
     * 配置fab的drawable 和动画显示
     *
     * @param resId
     * @param mFabActionBtn
     */
    private void setFabDrawableAnimator(int resId, FloatingActionButton mFabActionBtn) {

        mFabActionBtn.hide(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onHidden(FloatingActionButton fab) {
                super.onHidden(fab);
                Logger.d("onHidden");
                fab.setImageResource(resId);
                fab.show();
                mSwipeRefresh.setRefreshing(true);
                mListenerRefresh.getHttpRefresh();
//                NetUtils.showSnackBar(mFabOperate,"关注用户成功");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(isMe ? R.menu.menu_user_me : R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_follow:
                httpFollowUser();
                break;
            case R.id.action_user_setting:
                //// TODO: 2016/5/29 0029 用户设置相关跳转
                Logger.d("action_user_setting");
                break;
        }

        // boolean Return false to allow normal menu processing to
        // proceed, true to consume it here.
        // false：允许继续事件传递  true：就自己消耗事件 不再传递
        return true;
    }

    @Override
    protected ArrayList<BaseRecyclerHeadFragment> initFragmentList() {
        ArrayList<BaseRecyclerHeadFragment> fragments = new ArrayList<>(3);
        fragments.add(UserBoardFragment.newInstance(mKey));
        fragments.add(UserPinsFragment.newInstance(mKey));
        fragments.add(UserLikeFragment.newInstance(mKey));
        return fragments;
    }

    @Override
    protected void setupTabLayoutWithViewPager(ViewPager mViewPager) {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(mColorTabIndicator);
    }


    @Override
    protected void getNecessaryData() {
        super.getNecessaryData();
        mTitle = getIntent().getStringExtra(TYPE_TITLE);
        mKey = getIntent().getStringExtra(TYPE_KEY);

        String userId = (String) SPUtils.get(mContext, Constant.USERID, "");
        Logger.d("is me " + mKey.equals(userId));
        isMe = mKey.equals(userId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addSubscription(getHttpsUserInfo());
    }


    //获取我的画板字段 没有UI效果
    private Subscription getMyBoardListInfo() {
        return RetrofitClient.createService(UserAPI.class)
                .httpsBoardListInfo(mAuthorization, Constant.OPERATEBOARDEXTRA)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardListInfoBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(BoardListInfoBean boardListInfoBean) {
                        Logger.d(boardListInfoBean.getBoards().size() + " ");
                    }
                });
    }

    //联网获取用户信息
    private Subscription getHttpsUserInfo() {
        return RetrofitClient.createService(UserAPI.class)
                .httpsUserInfoRx(mAuthorization, mKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserMeAndOtherBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(UserMeAndOtherBean userInfoBean) {
                        setUserHeadAndBackGround(userInfoBean);//设置用户头像和 模糊背景
                        setUserTextInfo(userInfoBean);//设置用户文字信息
                        setUserFollow(userInfoBean);
                    }
                });


    }

    @Override
    protected void ViewPagerPageSelected(int position) {
//        Logger.d("position=" + position + " view=" + mFabOperate.getVisibility() + " Y=" + mFabOperate.getTranslationY());

        if (position != 0) {
            //如果不在第一版面 重置第一页影响的Y值 然后隐藏
            mFabOperate.setTranslationY(0);
            if (mFabOperate.getVisibility() != View.GONE) {
                mFabOperate.hide();
            }

        } else {
            //如果回带第一版面 显示
            if (mFabOperate.getVisibility() != View.VISIBLE) {
                mFabOperate.show();
            }
        }
    }

    /**
     * 设置网络返回数据 主要判断是否已经关注过
     *
     * @param bean
     */
    private void setUserFollow(UserMeAndOtherBean bean) {
        isFollow = bean.getFollowing() == 1;
        //网络返回成功后 如果是我显示添加 否则根据关注状态 显示
        mFabOperate.setImageResource(
                isMe ? R.drawable.ic_add_black_24dp :
                        (isFollow ? R.drawable.ic_done_black_24dp : R.drawable.ic_loyalty_black_24dp));
        mFabOperate.show();

    }

    private void setUserTextInfo(UserMeAndOtherBean bean) {
        String name = bean.getUsername();
        if (!TextUtils.isEmpty(name)) {
            mTvUserName.setText(name);
        } else {
            mTvUserName.setText("用户名为空");
        }

        String location = bean.getProfile().getLocation();
        String job = bean.getProfile().getJob();
        StringBuffer buffer = new StringBuffer();
        if (!TextUtils.isEmpty(location)) {
            buffer.append(location);
            buffer.append(" ");
        }
        if (!TextUtils.isEmpty(job)) {
            buffer.append(job);
        }
        if (!TextUtils.isEmpty(buffer)) {
            mTvUserLocationJob.setText(buffer);
        }

        mTvUserFriend.setText(String.format(mFansFollowingFormat, bean.getFollower_count(), bean.getFollowing_count()));
    }

    private void setUserHeadAndBackGround(UserMeAndOtherBean bean) {
        String url = bean.getAvatar();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains(mHttpRoot)) {
                url = String.format(mFormatUrlSmall, url);
            }
            new ImageLoadFresco.LoadImageFrescoBuilder(getApplicationContext(), mImageUser, url)
                    .setPlaceHolderImage(CompatUtils.getTintDrawable(mContext,R.drawable.ic_account_circle_white_48dp,Color.WHITE))
                    .setIsCircle(true, true)
                    .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                        @Override
                        protected void onNewResultImpl(Bitmap bitmap) {
                            //得到缓存中的Bitmap对象 这里可以进行操作
                            //构造Drawable对象 模糊化设置给View控件
                            Logger.d("onNewResultImpl = " + Thread.currentThread().toString());
                            if (bitmap == null) {
                                Logger.d("bitmap is null");
                            } else {
                                Logger.d("bitmap is not null");
                                Drawable backDrawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(bitmap, 25, false));
                                if (Utils.checkUiThreadBoolean()) {
                                    mAppBar.setBackground(backDrawable);
                                } else {
                                    mAppBar.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAppBar.setBackground(backDrawable);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                            Logger.d("onFailureImpl");
                        }
                    })
                    .build();
        }
    }


    @Override
    public void onClickBoardItemImage(UserBoardItemBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }


    @Override
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onDialogPositiveClick(String name, String describe, String selectType) {
        Logger.d(name + " " + describe + " " + selectType);
    }
}
