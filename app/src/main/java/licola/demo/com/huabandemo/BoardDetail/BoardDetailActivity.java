package licola.demo.com.huabandemo.BoardDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import licola.demo.com.huabandemo.API.OnBoardDetailFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Logger;

public class BoardDetailActivity extends BaseActivity
        implements OnBoardDetailFragmentInteractionListener {
    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";

    //联网的授权字段 提供子Fragment使用
    public String mAuthorization = Base64.mClientInto;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.fab_board_detail)
    FloatingActionButton mFABBoard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_board_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public String mKey;
    public String mTitle;

    public static void launch(Activity activity, String key, String title) {
        Intent intent = new Intent(activity, BoardDetailActivity.class);
        intent.putExtra(TYPE_TITLE, title);
        intent.putExtra(TYPE_KEY, key);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, int flag) {
        Intent intent = new Intent(activity, BoardDetailActivity.class);
        intent.setFlags(flag);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mKey = getIntent().getStringExtra(TYPE_KEY);
        mTitle = getIntent().getStringExtra(TYPE_TITLE);
        mAuthorization = getAuthorization();
        setTitle(mTitle);

        mFABBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportFragmentManager().
                beginTransaction().replace(R.id.framelayout_board_detail, BoardDetailFragment.newInstance(mKey)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d("id=" + item.getItemId());
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                actionHome();
                break;
        }
        return true;
    }

    private void actionHome() {
        finish();
    }


    @Override
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        //绑定的fragment 需要跳转的点击事件
        ImageDetailActivity.launch(BoardDetailActivity.this);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        //绑定的fragment 需要跳转的点击事件
        ImageDetailActivity.launch(BoardDetailActivity.this);
    }

    @Override
    public void onClickUserField(String key, String title) {
        //// TODO: 2016/4/7 0007  绑定的fragment headView 的点击事件 跳转到用户界面
    }
}
