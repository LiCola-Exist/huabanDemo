package licola.demo.com.huabandemo.Module.Follow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import licola.demo.com.huabandemo.API.Fragment.OnBoardFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Base.BaseSwipeViewPagerActivity;
import licola.demo.com.huabandemo.Entity.BoardPinsBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.Module.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.Module.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/04/23  18:09
 */
public class FollowActivity extends BaseSwipeViewPagerActivity<BaseRecyclerHeadFragment>
        implements OnPinsFragmentInteractionListener,
        OnBoardFragmentInteractionListener<BoardPinsBean> {

    @BindView(R.id.tablatyou_attention)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar_attention)
    Toolbar mToolbar;

    @BindColor(R.color.white)
    int mColorTaBindViewicator;


    @Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, FollowActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, int flag) {
        Intent intent = new Intent(activity, FollowActivity.class);
        intent.setFlags(flag);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow;
    }

    @Override
    protected String[] getTitleList() {
        return getResources().getStringArray(R.array.title_attention);
    }

    @Override
    protected ArrayList<BaseRecyclerHeadFragment> initFragmentList() {
        ArrayList<BaseRecyclerHeadFragment> mFragmentList=new ArrayList<>(2);
        mFragmentList.add(FollowPinsFragment.newInstance());
        mFragmentList.add(FollowBoardFragment.newInstance());
        return mFragmentList;
    }

    @Override
    protected void setupTabLayoutWithViewPager(ViewPager mViewPager) {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(mColorTaBindViewicator);
    }



    @Override
    protected String getTAG() {
        return this.toString();
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
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        //我的关注 画板的点击跳转
        ImageDetailActivity.launch(this, ImageDetailActivity.ACTION_ATTENTION);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        //我的关注 画板的点击跳转
        ImageDetailActivity.launch(this, ImageDetailActivity.ACTION_ATTENTION);
    }

    @Override
    public void onClickBoardItemImage(BoardPinsBean bean, View view) {
        String boardId = String.valueOf(bean.getBoard_id());
        BoardDetailActivity.launch(this, boardId, bean.getTitle());
    }


    @Override
    protected void ViewPagerPageSelected(int position) {
        Logger.d("position="+position);
    }
}
