package licola.demo.com.huabandemo.Module.Main;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.bugly.crashreport.CrashReport;


import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.ButterKnife;
import licola.demo.com.huabandemo.API.Fragment.OnPinsFragmentInteractionListener;
import licola.demo.com.huabandemo.API.Fragment.OnRefreshFragmentInteractionListener;
import licola.demo.com.huabandemo.API.OnFragmentRefreshListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Base.BaseRecyclerHeadFragment;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.Module.Follow.FollowActivity;
import licola.demo.com.huabandemo.Module.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.Module.Login.LoginActivity;
import licola.demo.com.huabandemo.Module.Search.SearchAndTypeActivity;
import licola.demo.com.huabandemo.Module.Setting.SettingsActivity;
import licola.demo.com.huabandemo.Module.Type.TypeNewFragment;
import licola.demo.com.huabandemo.Module.User.UserActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import rx.functions.Action1;

import static licola.demo.com.huabandemo.Util.SPUtils.FILE_NAME;
import static licola.demo.com.huabandemo.Util.SPUtils.MODE;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnClickListener,
        OnPinsFragmentInteractionListener,
        OnRefreshFragmentInteractionListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    //布局中控件 自动生成
    @BindView(R.id.navigation_view)
    NavigationView mNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fab_operate)
    FloatingActionButton mFabOperate;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;

    //NavigationView中的控件 手动填充
    private SimpleDraweeView img_nav_head;//头像
    private TextView tv_nav_username;//用户名
    private TextView tv_nav_email;//用户邮箱

    private FragmentManager fragmentManager;
    private BaseRecyclerHeadFragment fragment;

    private final int mDrawableList[] = {R.drawable.ic_loyalty_black_36dp, R.drawable.ic_camera_black_36dp,
            R.drawable.ic_message_black_36dp, R.drawable.ic_people_black_36dp};
    private String[] types;
    private String[] titles;

    private Boolean isLogin;
    private String mUserName = Constant.EMPTY_STRING;
    private String mUserId = Constant.EMPTY_STRING;

//    private FirebaseAnalytics mFirebaseAnalytics;

    //刷新的接口 子Fragment实现
    private OnFragmentRefreshListener mListenerRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, int flag) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(flag);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        getData();

        getSharedPreferences(FILE_NAME, MODE).registerOnSharedPreferenceChangeListener(this);
        intiDrawer(toolbar);//初始化DrawerLayout
        initHeadView();//为Drawer添加头部
        intiMenuView();//为Drawer添加menu菜单项目

        selectFragment(0);//默认选中0
        //测试bugly异常反馈
//        CrashReport.testJavaCrash();
    }

    //取出各种需要用的全局变量
    private void getData() {
        types = getResources().getStringArray(R.array.type_array);
        titles = getResources().getStringArray(R.array.title_array);
        isLogin = (Boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        if (isLogin) {
            //如果登录才有取以下值的意义
            getDataByLogin();
        }

    }

    private void getDataByLogin() {
        mUserName = (String) SPUtils.get(mContext, Constant.USERNAME, mUserName);
        mUserId = (String) SPUtils.get(mContext, Constant.USERID, mUserId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d(intent.toString());
    }

    @Override
    protected  void initResAndListener() {
        mFabOperate.setImageResource(R.drawable.ic_search_black_24dp);
        RxView.clicks(mFabOperate)
                .throttleFirst(Constant.throttDuration, TimeUnit.MILLISECONDS)//防止抖动处理
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
//                        Logger.d();
                        SearchAndTypeActivity.launch(MainActivity.this);
                    }
                });

        mSwipeRefresh.setColorSchemeResources(ints);
        mSwipeRefresh.setOnRefreshListener(() -> mListenerRefresh.getHttpRefresh());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNavUserInfo();
    }

    /**
     * 根据登录状态 显示头像和用户名
     */
    private void setNavUserInfo() {
        Drawable drawable= CompatUtils.getTintDrawable(mContext,R.drawable.ic_account_circle_gray_48dp, Color.GRAY);
        Logger.d("isLogin=" + isLogin);
        if (isLogin) {
            String key = (String) SPUtils.get(mContext, Constant.USERHEADKEY, "");
            if (!TextUtils.isEmpty(key)) {
                key = getString(R.string.urlImageRoot) + key;
                new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_nav_head, key)
                        .setPlaceHolderImage(drawable)
                        .setIsCircle(true, true)
                        .build();
            } else {
                Logger.d("user head key is empty");
            }


            if (!TextUtils.isEmpty(mUserName)) {
                tv_nav_username.setText(mUserName);
            }

            String email = (String) SPUtils.get(mContext, Constant.USEREMAIL, "");
            if (!TextUtils.isEmpty(email)) {
                tv_nav_email.setText(email);
            }
        }else {

            new ImageLoadFresco.LoadImageFrescoBuilder(mContext,img_nav_head,"")
                    .setPlaceHolderImage(drawable)
                    .setIsCircle(true, true)
                    .build();
        }
    }

    private void initHeadView() {
        /**
         * 代码手动填充 view作为头部布局
         * 得到view之后 就可以对headView进行操作
         */
        View headView = mNavigation.inflateHeaderView(R.layout.nav_header_main);
        LinearLayout group = ButterKnife.findById(headView, R.id.ll_nav_operation);
        tv_nav_username = ButterKnife.findById(headView, R.id.tv_nav_username);
        tv_nav_email = ButterKnife.findById(headView, R.id.tv_nav_email);
        img_nav_head = ButterKnife.findById(headView, R.id.img_nav_head);

        addButtonDrawable(group);

        tv_nav_username.setOnClickListener(this);
        img_nav_head.setOnClickListener(this);

    }

    /**
     * 取出父视图中的button 动态添加的Drawable资源
     * 使用了V4兼容包的Tint方法
     *
     * @param group
     */
    private void addButtonDrawable(LinearLayout group) {
//        Button btn = null;
        for (int i = 0, size = group.getChildCount(); i < size; i++) {
            Button btn = (Button) group.getChildAt(i);
            btn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    CompatUtils.getTintListDrawable(mContext, mDrawableList[i], R.color.tint_list_pink),
                    null,
                    null);
            btn.setOnClickListener(this);
        }
    }


    /**
     * 手动填充Menu 方便以后对menu的调整
     */
    private void intiMenuView() {

        Menu menu = mNavigation.getMenu();
        String titleList[] = getResources().getStringArray(R.array.title_array);
        int order = 0;
        for (String title : titleList) {
//            menu.add(Menu.NONE, order++, Menu.NONE, title).setIcon(R.drawable.ic_menu_share).setCheckable(true);
            menu.add(R.id.menu_group_type, order++, Menu.NONE, title).setIcon(mDrawableList[0]).setCheckable(true);
        }
//        menu.addSubMenu("text");
        menu.getItem(0).setChecked(true);//默认选中第一项

    }

    private void intiDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation.setNavigationItemSelectedListener(this);
    }


    private void selectFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String type = types[position];
        String title = titles[position];
        fragment = TypeNewFragment.newInstance(type, title);
        if (fragment != null) {
            mListenerRefresh = fragment;
        }
        transaction.replace(R.id.container_with_refresh, fragment);
        transaction.commit();
        setTitle(title);
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.remove(fragment);
//        ft.commitAllowingStateLoss();
//        super.onSaveInstanceState(outState);
//    }



    @Override
    public void onBackPressed() {
        //监听返回键
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //如果DrawerLayout 拦截点击 关闭Drawer
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences(FILE_NAME, MODE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getGroupId() == R.id.menu_group_type) {
            selectFragment(item.getItemId());
        } else {
            if (item.getItemId() == R.id.nav_set) {
                SettingsActivity.launch(this);
            } else if (item.getItemId() == R.id.nav_exit) {
                exitOperate();//退出操作
            }
            //// TODO: 2016/3/24 0024 处理 设置 关于 界面
            Logger.d(item.getTitle().toString());
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exitOperate() {
        SPUtils.clear(mContext);
        finish();
    }


    /**
     * 主界面 drawer 所有控件的点击事件 回调处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_nav_head:
                if (isLogin) {
                    UserActivity.launch(MainActivity.this, mUserId, mUserName);
                } else {
                    LoginActivity.launch(MainActivity.this);
                }
                break;
            case R.id.tv_nav_username:

                break;

            case R.id.btn_nav_attention:
                FollowActivity.launch(MainActivity.this);
                break;

            default:
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        Logger.d();
        ImageDetailActivity.launch(this, ImageDetailActivity.ACTION_MAIN);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        Logger.d();
        ImageDetailActivity.launch(this);
    }

    @Override
    public void OnRefreshState(boolean isRefreshing) {
        mSwipeRefresh.setRefreshing(isRefreshing);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Logger.d(key);
        if (Constant.ISLOGIN.equals(key)) {
            isLogin = sharedPreferences.getBoolean(Constant.ISLOGIN, false);
        }
    }


}
