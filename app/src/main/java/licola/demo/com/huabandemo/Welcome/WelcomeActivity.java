package licola.demo.com.huabandemo.Welcome;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import java.util.Vector;

import butterknife.Bind;
import butterknife.BindString;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.Login.LoginActivity;
import licola.demo.com.huabandemo.Login.TokenBean;
import licola.demo.com.huabandemo.Main.MainActivity;
import licola.demo.com.huabandemo.Observable.MyRxObservable;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.TimeUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LiYi on 2016/04/11  14:26
 * 欢迎页 进行登录判断 和联网重获token
 */

public class WelcomeActivity extends BaseActivity {
    //登录的报文需要
    private static final String PASSWORD = "password";
    private static final int mTimeDifference = TimeUtils.MINUTES;
//    private static final int mTimeDifference = 0;

    @BindString(R.string.text_auto_login_fail)
    String mMessageFail;
    @Bind(R.id.img_welcome)
    ImageView mImageView;

    private Boolean isLogin = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = (Boolean) SPUtils.get(getApplicationContext(), Constant.ISLOGIN, isLogin);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Animator animation = AnimatorInflater.loadAnimator(mContext, R.animator.welcome_animator);
//        Logger.d(mImageView.getDrawable().getClass() + " ");
        //observeOn() 指定的是它之后的操作所在的线程
        //subscribeOn() 作用于Observable对象
        //onCompleted() 和 onError() 二者是互斥的 调用一个就不会再调用另一个
        MyRxObservable.add(animation, mImageView)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())//指定订阅的Observable对象的call方法运行在ui线程中
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        Logger.d("isLogin=" + isLogin);
                        return isLogin;
//                        return true;
//                        return false;
                    }
                })
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        Long lastTime = (Long) SPUtils.get(getApplicationContext(), Constant.LOGINTIME, 0L);
                        long dTime = System.currentTimeMillis() - lastTime;
                        Logger.d("dTime=" + dTime + " default" + mTimeDifference);
                        return dTime > mTimeDifference;
                    }
                })
                .flatMap(new Func1<Void, Observable<TokenBean>>() {
                    @Override
                    public Observable<TokenBean> call(Void aVoid) {
                        Logger.d("flatMap");
                        String userAccount = (String) SPUtils.get(getApplicationContext(), Constant.USERACCOUNT, "");
                        String userPassword = (String) SPUtils.get(getApplicationContext(), Constant.USERPASSWORD, "");
                        return getUserToken(userAccount, userPassword);
                    }
                })
//                .retryWhen(new RetryWithConnectivityIncremental(WelcomeActivity.this, 4, 15, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())//最后统一回到UI线程中处理
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                        MainActivity.launch(WelcomeActivity.this);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        LoginActivity.launch(WelcomeActivity.this, mMessageFail);
                        finish();
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        Logger.d("https success");
                        saveToken(tokenBean);
                    }
                });

    }

    private void saveToken(TokenBean tokenBean) {
        SPUtils.put(getApplicationContext(), Constant.LOGINTIME, System.currentTimeMillis());
        SPUtils.put(getApplicationContext(), Constant.TOKENACCESS, tokenBean.getAccess_token());
        SPUtils.put(getApplicationContext(), Constant.TOKENTYPE, tokenBean.getToken_type());
    }

    private Observable<TokenBean> getUserToken(String username, String password) {
        return RetrofitService.createAvatarService().httpsTokenRx(Base64.mClientInto, PASSWORD, username, password);
    }

}
