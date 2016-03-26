package licola.demo.com.huabandemo.activity;

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

import java.util.HashSet;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.bean.SearchBoardBean;
import licola.demo.com.huabandemo.bean.SearchImageBean;
import licola.demo.com.huabandemo.bean.SearchPeopleBean;
import licola.demo.com.huabandemo.fragment.ResultImageFragment;
import licola.demo.com.huabandemo.httpUtils.RetrofitPinsRx;
import rx.Observable;


public class SearchResultActivity extends BaseActivity {

    private static final String SEARCHKEY = "KEY";
    private String key;//搜索的关键字

    @BindString(R.string.title_activity_search_result)
    String mTitle;
    @BindString(R.string.title_fragment_image)
    String mTitleImage;
    @BindString(R.string.title_fragment_board)
    String mTitleBoard;
    @BindString(R.string.title_fragment_user)
    String mTitleUser;
    @BindColor(R.color.white)
    int mColorTabIndicator;

    @Bind(R.id.viewpager_search)
    ViewPager mViewPager;
    @Bind(R.id.tablayou_search)
    TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }


    public static void launch(Activity activity, String key) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        intent.putExtra(SEARCHKEY, key);
        activity.startActivity(intent);
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

    private Observable<SearchImageBean> getSearchImage(String key, int page, int limit) {
        return RetrofitPinsRx.service.httpImageSearchRx(key, page, limit);
    }

    private Observable<SearchBoardBean> getSearchBoard(String key, int page, int limit) {
        return RetrofitPinsRx.service.httpBoardSearchRx(key, page, limit);
    }

    private Observable<SearchPeopleBean> getSearchPeople(String key, int page, int limit) {
        return RetrofitPinsRx.service.httpPeopleSearchRx(key, page, limit);
    }

    private void saveSearchHistory(String key) {
        //转到这个界面就表示 搜索成功 保存搜索记录
        HashSet<String> hashSet = (HashSet<String>) SPUtils.get(mContext, Constant.HISTORYTEXT, new HashSet<String>());
        for (String s :
                hashSet) {
            Logger.d(s);
        }
        hashSet.add(key);

        SPUtils.put(mContext, Constant.HISTORYTEXT, hashSet);
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
                return ResultImageFragment.newInstance(key);
            }
            return ResultImageFragment.newInstance(key);
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
