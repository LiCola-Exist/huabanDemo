package licola.demo.com.huabandemo.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


import butterknife.BindView;
import licola.demo.com.huabandemo.API.OnFragmentRefreshListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.R;


/**
 * Created by LiCola on  2016/04/23  17:34
 * 集成了 SwipeRefreshLayout ViewPager 两个控件的Activity
 * 作为抽象父类 提供控制功能
 */
public abstract class BaseSwipeViewPagerActivity<T extends BaseFragment>
        extends BaseActivity
        implements OnRefreshFragmentInteractionListener {

    @BindView(R.id.container)
    protected ViewPager mViewPager;
    @BindView(R.id.swipe_refresh_widget)
    protected SwipeRefreshLayout mSwipeRefresh;

    protected ArrayList<T> mFragmentList;

    protected String[] mTitleList;

    protected abstract String[] getTitleList();

    //刷新的接口 子Fragment实现
    protected OnFragmentRefreshListener mListenerRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleList = getTitleList();
        mFragmentList = initFragmentList();
        //默认取第一个为强转为接口
        mListenerRefresh = (OnFragmentRefreshListener) mFragmentList.get(0);
        initViewPagerTab();

    }


    //初始创建出 fragment集合
    protected abstract ArrayList<T> initFragmentList();


    protected void initViewPagerTab() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        setupTabLayoutWithViewPager(mViewPager);
    }

    //抽象方法 绑定viewpager和tablayout
    protected abstract void setupTabLayoutWithViewPager(ViewPager mViewPager);

    protected void setSwipeRefresh(){
        mSwipeRefresh.setRefreshing(true);
        mListenerRefresh.getHttpRefresh();
    }

    protected void initResAndListener() {

        mSwipeRefresh.setColorSchemeResources(ints);
        mSwipeRefresh.setOnRefreshListener(() -> mListenerRefresh.getHttpRefresh());


        //上下滑动mSwipeRefresh 和左右滑动mViewPager 滑动冲突解决
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeRefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Logger.d("onPageScrolled= "+position+" "+positionOffset+" "+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                Logger.d("position=" + position);
                mListenerRefresh = (OnFragmentRefreshListener) mFragmentList.get(position);
                ViewPagerPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Logger.d("state=" + state);
            }
        });
    }

    @Override
    public void OnRefreshState(boolean isRefreshing) {
        mSwipeRefresh.setRefreshing(isRefreshing);
    }

    protected abstract void ViewPagerPageSelected(int position);

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
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mTitleList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList[position];
        }


    }
}
