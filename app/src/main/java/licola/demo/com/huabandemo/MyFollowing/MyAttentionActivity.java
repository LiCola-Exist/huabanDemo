package licola.demo.com.huabandemo.MyFollowing;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import licola.demo.com.huabandemo.API.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Bean.BoardPinsBean;
import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;
import licola.demo.com.huabandemo.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;

public class MyAttentionActivity extends BaseActivity
        implements OnPinsFragmentInteractionListener, OnBoardFragmentInteractionListener<BoardPinsBean> {

    static final int mTabCount = 2;

    @BindString(R.string.title_fragment_attention_pins)
    String mTitleAttentionPins;
    @BindString(R.string.title_fragment_attention_board)
    String mTitleAttentionBoard;
    @Bind(R.id.tablatyou_attention)
    TabLayout mTabLayout;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_attention)
    Toolbar mToolbar;

    @BindColor(R.color.white)
    int mColorTabIndicator;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    //包含的两个fragment共享联网关键字段
    public String mTokenType;
    public String mTokenAccess;

    public static void launch(Activity activity, int flag) {
        Intent intent = new Intent(activity, MyAttentionActivity.class);
        intent.setFlags(flag);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MyAttentionActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_attention;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViewPagerTab();


        mTokenType = (String) SPUtils.get(mContext, Constant.TOKENTYPE, "");
        mTokenAccess = (String) SPUtils.get(mContext, Constant.TOKENACCESS, "");
    }

    private void initViewPagerTab() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(mColorTabIndicator);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d(intent.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_following, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickPinsItemImage(PinsAndUserEntity bean, View view) {
        //我的关注 画板的点击跳转
        ImageDetailActivity.launch(this, ImageDetailActivity.ACTION_ATTENTION);
    }

    @Override
    public void onClickPinsItemText(PinsAndUserEntity bean, View view) {
        //我的关注 画板的点击跳转
        ImageDetailActivity.launch(this, ImageDetailActivity.ACTION_ATTENTION);
    }

    @Override
    public void onClickBoardItemImage(BoardPinsBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }

    @Override
    public void onClickBoardItemOperate(BoardPinsBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return MyAttentionPinsFragment.newInstance();
            } else {
                return MyAttentionBoardFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return mTabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return mTitleAttentionPins;
            } else {
                return mTitleAttentionBoard;
            }
        }
    }
}
