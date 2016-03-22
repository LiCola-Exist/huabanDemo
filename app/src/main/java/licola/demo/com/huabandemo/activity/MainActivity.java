package licola.demo.com.huabandemo.activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.fragment.ModuleFragment;
import licola.demo.com.huabandemo.httpUtils.ImageLoadFresco;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    //布局中控件 自动生成
    @Bind(R.id.navigation_view)
    NavigationView mNavigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fab_main)
    FloatingActionButton mFab_main;

    @BindString(R.string.snack_message_main_gather)
    String snack_message_main_gather;


    //NavigationView中的控件 手动填充
    private SimpleDraweeView img_nav_head;//头像
    private TextView tv_nav_username;//用户名
    private TextView tv_nav_email;//用户邮箱

    private FragmentManager fragmentManager;

    private final int mDrawableList[] = {R.drawable.ic_loyalty_white_36dp, R.drawable.ic_cloud_upload_white_36dp,
            R.drawable.ic_message_white_36dp, R.drawable.ic_group_white_36dp};
    private String[] types;
    private String[] titles;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        types = getResources().getStringArray(R.array.type_array);
        titles = getResources().getStringArray(R.array.title_array);

        initFloatingActionButton();

        intiDrawer(toolbar);//初始化DrawerLayout
        initHeadView();//为Drawer添加头部
        intiMenuView();//为Drawer添加menu菜单项目

        selectFragment(0);//默认选中0

    }

    private void initFloatingActionButton() {
        mFab_main.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.launch(MainActivity.this);
            }
        });
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
        Boolean isLogin = (Boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        Logger.d("isLogin=" + isLogin);
        if (isLogin) {
            String key = (String) SPUtils.get(mContext, Constant.USERHEADKEY, "");
            if (!TextUtils.isEmpty(key)) {
                key = getString(R.string.url_image) + key;
                new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_nav_head, key)
                        .setIsCircle(true)
                        .build();
            } else {
                Logger.d("user head key is empty");
            }

            String username = (String) SPUtils.get(mContext, Constant.USERNAME, "");
            if (!TextUtils.isEmpty(username)) {
                tv_nav_username.setText(username);
            }

            String email = (String) SPUtils.get(mContext, Constant.USEREMAIL, "");
            if (!TextUtils.isEmpty(email)) {
                tv_nav_email.setText(email);
            }
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
        Button btn = null;
        for (int i = 0, size = group.getChildCount(); i < size; i++) {
            btn = (Button) group.getChildAt(i);
            btn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    Utils.getTintCompatDrawable(mContext, mDrawableList[i], R.color.tint_list_pink),
                    null,
                    null);
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
            menu.add(Menu.NONE, order++, Menu.NONE, title).setIcon(mDrawableList[0]).setCheckable(true);
        }
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
        transaction.replace(R.id.id_content, ModuleFragment.newInstance(type, title));
        transaction.commit();
        setTitle(title);
    }


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

        selectFragment(item.getItemId());

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_nav_head:
                LoginActivity.launch(MainActivity.this);
                break;
            case R.id.tv_nav_username:

                break;

            default:
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
