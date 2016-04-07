package licola.demo.com.huabandemo.MyUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import licola.demo.com.huabandemo.API.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitAvatarRx;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.SearchResult.ResultBoardFragment;
import licola.demo.com.huabandemo.SearchResult.ResultImageFragment;
import licola.demo.com.huabandemo.SearchResult.ResultPeopleFragment;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyUserActivity extends BaseActivity implements OnBoardFragmentInteractionListener<UserBoardItemBean> {
    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String TYPE_TITLE = "TYPE_TITLE";

    @BindString(R.string.title_fragment_like)
    String mTitleLike;
    @BindString(R.string.title_fragment_board)
    String mTitleBoard;
    @BindString(R.string.title_fragment_gather)
    String mTitleGather;
    @BindColor(R.color.white)
    int mColorTabIndicator;
    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.httpRoot)
    String mHttpRoot;
    @BindString(R.string.text_fans_attention)
    String mFansFollowingFormat;

    @Bind(R.id.toolbar_user)
    Toolbar mToolbar;
    @Bind(R.id.collapsingtoolbar_user)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.img_image_user)
    SimpleDraweeView mImageUser;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.tv_user_location_job)
    TextView mTvUserLocationJob;
    @Bind(R.id.tv_user_friend)
    TextView mTvUserFriend;
    @Bind(R.id.viewpager_user)
    ViewPager mViewPager;
    @Bind(R.id.tablayout_user)
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static final int mCount = 3;
    public String mKey;
    public String mTitle;
    public boolean isMe;
    public String mTokenType;
    public String mTokenAccess;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_user;
    }

    public static void launch(Activity activity, String key, String title) {
        Intent intent = new Intent(activity, MyUserActivity.class);
        intent.putExtra(TYPE_TITLE, title);
        intent.putExtra(TYPE_KEY, key);
        activity.startActivity(intent);
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();

        mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);//设置折叠后的文字颜色
        initAdapter();
    }

    private void getData() {
        mTitle = getIntent().getStringExtra(TYPE_TITLE);
        mKey = getIntent().getStringExtra(TYPE_KEY);
        mTokenType = (String) SPUtils.get(mContext, Constant.TOKENTYPE, "");
        mTokenAccess = (String) SPUtils.get(mContext, Constant.TOKENACCESS, "");
        String userId= (String) SPUtils.get(mContext,Constant.USERID,"");
        isMe=mKey.equals(userId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addSubscription(getHttpsUserInfo());
    }

    //联网获取用户信息
    private Subscription getHttpsUserInfo() {
        return getUserInfo(mTokenType, mTokenAccess, mKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());

                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        setUserData(userInfoBean);
                    }
                });


    }

    private void setUserData(UserInfoBean bean) {
        String url = bean.getAvatar();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains(mHttpRoot)) {
                url = String.format(mFormatUrlSmall, url);
            }
            new ImageLoadFresco.LoadImageFrescoBuilder(getApplicationContext(), mImageUser, url)
                    .setIsCircle(true)
                    .build();
        }
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

    private Observable<UserInfoBean> getUserInfo(String bearer, String key, String userId) {
        return RetrofitAvatarRx.service.httpsUserInfoRx(bearer + " " + key, userId);
    }

    private void initAdapter() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(mColorTabIndicator);
    }

    @Override
    public void onClickItemImage(UserBoardItemBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }

    @Override
    public void onClickItemOperate(UserBoardItemBean bean, View view) {
        //// TODO: 2016/4/7 0007 回调过来的 操作事件 需要联网
        Logger.d();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return UserBoardFragment.newInstance(mKey);
            } else if (position == 1) {
                return ResultBoardFragment.newInstance("文艺");
            } else {
                return ResultPeopleFragment.newInstance("文艺");
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mTitleBoard;
                case 1:
                    return mTitleGather;
                case 2:
                    return mTitleLike;
            }
            return null;
        }
    }
}
