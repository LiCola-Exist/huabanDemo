package licola.demo.com.huabandemo.My;

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

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

public class MyAttentionActivity extends BaseActivity {
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
                return MyAttentionPinsFragment.newInstance();
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
