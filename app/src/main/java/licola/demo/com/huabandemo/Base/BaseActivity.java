package licola.demo.com.huabandemo.Base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.User.UserSingleton;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.Util.SPUtils;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LiYi on 2015/11/4 0004 14:59.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getTAG();
    
    protected abstract int getLayoutId();

    protected abstract String getTAG();

    protected Context mContext;


    //关键的是否登录 由父类提供
    public boolean isLogin=false;
    //关键的https联网字段 由父类提供
    public String mAuthorization ;

    protected static final int[] ints = new int[]{R.color.pink_300, R.color.pink_500, R.color.pink_700, R.color.pink_900};

    @Override
    public String toString() {
        return getClass().getSimpleName() + " @" + Integer.toHexString(hashCode());
    }

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1122/3712.html
         * 在BaseActivity.java里：我们通过判断当前sdk_int大于4.4(kitkat),则通过代码的形式设置status bar为透明
         * (这里其实可以通过values-v19 的sytle.xml里设置windowTranslucentStatus属性为true来进行设置，但是在某些手机会不起效，所以采用代码的形式进行设置)。
         * 还需要注意的是我们这里的AppCompatAcitivity是android.support.v7.app.AppCompatActivity支持包中的AppCompatAcitivity,也是为了在低版本的android系统中兼容toolbar。
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isTranslucentStatusBar()) {
                Window window = getWindow();
                // Translucent status bar
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        getNecessaryData();
        initResAndListener();
        if (isLogin()){
            Logger.d(TAG);
        }

    }

    //空方法 规定子类 初始化监听器 和定义显示资源 的步骤
    protected void initResAndListener() {

    }

    protected void getNecessaryData() {
        UserSingleton.getInstance().isLogin(getApplication());
        isLogin = (boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        mAuthorization=getAuthorizations(isLogin);
    }

    //是否statusBar 状态栏为透明 的方法 默认为真
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    protected boolean isLogin(){
        return true;
    }

    protected String getAuthorizations(boolean isLogin) {

        String temp = " ";
        if (isLogin) {
            return SPUtils.get(mContext, Constant.TOKENTYPE, temp)
                    + temp
                    + SPUtils.get(mContext, Constant.TOKENACCESS, temp);
        }
        return Base64.mClientInto;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isLogin()){
            Logger.d(TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogin()){
            Logger.d(TAG);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isLogin()){
            Logger.d(TAG);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLogin()){
            Logger.d(TAG);
        }
        if (this.mCompositeSubscription != null) {

            this.mCompositeSubscription.unsubscribe();
        }
        //不知道有没有用
//        if (isFinishing()) {
//            fixInputMethodManagerLeak(this);
//        }
    }

//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//        Logger.d("finalize"+TAG);
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isLogin()){
            Logger.d(TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isLogin()){
            Logger.d(TAG);
        }
    }

    protected void checkException(Throwable e,View mRootView) {
        NetUtils.checkHttpException(mContext, e, mRootView);
    }
}
