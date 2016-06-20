package licola.demo.com.huabandemo.Module.SearchResult;

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

import java.util.HashSet;
import java.util.Set;


import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnPeopleFragmentInteraction;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Entity.BoardPinsBean;
import licola.demo.com.huabandemo.Module.User.UserActivity;
import licola.demo.com.huabandemo.Module.SearchResult.SearchPeopleListBean.UsersBean;
import licola.demo.com.huabandemo.Module.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.Module.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;


public class SearchResultActivity extends BaseActivity implements
        OnPinsFragmentInteractionListener, OnBoardFragmentInteractionListener<BoardPinsBean>
        , OnPeopleFragmentInteraction<UsersBean> {

    private static final String SEARCHKEY = "KEY";
    private String key;//搜索的关键字

    @BindString(R.string.title_activity_search_result)
    String mTitle;
    @BindString(R.string.title_fragment_gather)
    String mTitleImage;
    @BindString(R.string.title_fragment_board)
    String mTitleBoard;
    @BindString(R.string.title_fragment_user)
    String mTitleUser;
    @BindColor(R.color.white)
    int mColorTabIndicator;

    @BindView(R.id.viewpager_search)
    ViewPager mViewPager;
    @BindView(R.id.tablayou_search)
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity, String key) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        intent.putExtra(SEARCHKEY, key);
        activity.startActivity(intent);
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_result);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        key = getIntent().getStringExtra(SEARCHKEY);

        Logger.d(key);
        saveSearchHistory(key);
        initAdapter();
        setTitle(String.format(mTitle, key));

    }

    private void saveSearchHistory(String key) {
        //转到这个界面就表示 搜索成功 保存搜索记录
        HashSet<String> hashSet = (HashSet<String>) SPUtils.get(mContext, Constant.HISTORYTEXT, new HashSet<String>());
        //关键操作 需要在新的集合添加值 然后再提交修改
        Set<String> changeData = new HashSet<>(hashSet);
        changeData.add(key);

        boolean isSuccess = SPUtils.putCommit(mContext, Constant.HISTORYTEXT, changeData);
        Logger.d("isSuccess=" + isSuccess);
    }

    private void initAdapter() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(mColorTabIndicator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onClickBoardItemImage(BoardPinsBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }


    @Override
    public void onClickItemUser(UsersBean bean, View view) {
        Logger.d();
        UserActivity.launch(this, String.valueOf(bean.getUser_id()), bean.getUsername());
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
                return ResultPinsFragment.newInstance(key);
            } else if (position == 1) {
                return ResultBoardFragment.newInstance(key);
            } else {
                return ResultPeopleFragment.newInstance(key);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mTitleImage;
                case 1:
                    return mTitleBoard;
                case 2:
                    return mTitleUser;
            }
            return null;
        }
    }
}
