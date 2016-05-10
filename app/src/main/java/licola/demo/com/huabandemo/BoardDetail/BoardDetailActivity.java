package licola.demo.com.huabandemo.BoardDetail;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import licola.demo.com.huabandemo.API.OnBoardDetailFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.ImageDetail.ImageDetailActivity;
import licola.demo.com.huabandemo.Observable.MyRxObservable;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.UserInfo.UserInfoActivity;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.Util.SPUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BoardDetailActivity extends BaseActivity
        implements OnBoardDetailFragmentInteractionListener {
    protected static final String TYPE_KEY = "TYPE_KEY";
    protected static final String TYPE_TITLE = "TYPE_TITLE";

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.fab_board_detail)
    FloatingActionButton mFabBoardAttention;

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
    //是否我的画板 音响FloatingActionButton 的显示
    private boolean isMe=false;
    //该画板是否被关注的标志位 默认false
    public boolean isAttention = false;

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

        setTitle(mTitle);
        Logger.d(mAuthorization);
        //先锁定
        mFabBoardAttention.setEnabled(false);

        mFabBoardAttention.setOnClickListener(view -> actionAttention());

        getSupportFragmentManager().
                beginTransaction().replace(R.id.framelayout_board_detail, BoardDetailFragment.newInstance(mKey)).commit();
    }

    private void actionAttention() {
        String operate = isAttention ? Constant.OPERATEUNATTENTION : Constant.OPERATEATTENTION;

        Animator animation = AnimatorInflater.loadAnimator(mContext, R.animator.scale_small_animation);
        MyRxObservable.add(animation, mFabBoardAttention)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(aVoid -> RetrofitService.createAvatarService()
                        .httpsAttentionOperate(mAuthorization, mKey, operate))
                .observeOn(AndroidSchedulers.mainThread())//最后统一回到UI线程中处理
                .subscribe(new Subscriber<AttentionOperateBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                        setFabDrawableAndStart(isAttention ? R.drawable.ic_done_white_24dp : R.drawable.ic_loyalty_white_24dp, mContext, mFabBoardAttention);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        setFabDrawableAndStart(R.drawable.ic_report_white_24dp, mContext, mFabBoardAttention);
                        NetUtils.checkHttpException(mContext, e, mToolBar);
                    }

                    @Override
                    public void onNext(AttentionOperateBean attentionOperateBean) {
                        isAttention = !isAttention;//取反
                    }
                });
    }

    private void setFabDrawableAndStart(int resId, Context mContext, FloatingActionButton mFabActionBtn) {
        mFabActionBtn.setImageResource(resId);
        Animator animation = AnimatorInflater.loadAnimator(mContext, R.animator.scale_magnify_animation);
        animation.setTarget(mFabActionBtn);
        animation.start();
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
        UserInfoActivity.launch(BoardDetailActivity.this, key, title);
    }


    //联网结果回调
    @Override
    public void onHttpBoardAttentionState(String userId,boolean isAttention) {
        //如果登录 才能取本地userId值
        if (isLogin){
            String localUserId = (String) SPUtils.get(mContext, Constant.USERID, "");
            isMe = userId.equals(localUserId);
        }
        if (!isMe){
            this.isAttention = isAttention;
            mFabBoardAttention.setVisibility(View.VISIBLE);
            mFabBoardAttention.setImageResource(
                    isAttention ? R.drawable.ic_done_white_24dp : R.drawable.ic_loyalty_white_24dp);
        }

    }
}
