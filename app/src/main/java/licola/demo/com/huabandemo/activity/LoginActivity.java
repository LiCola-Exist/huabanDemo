package licola.demo.com.huabandemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.bean.TokenBean;
import licola.demo.com.huabandemo.bean.UserMeBean;
import licola.demo.com.huabandemo.httpUtils.RetrofitGsonRx;
import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    //登录的报文需要
    private static final String BASIC = "Basic ";
    private static final String PASSWORD = "password";

    private String mAccessToken;//后续所有的https联网都使用 需要暂时保存
    private String mRefreshToken;

    //需要的资源
    @BindString(R.string.snack_message_net_error)
    String snack_message_net_error;
    @BindString(R.string.snack_message_password_error)
    String snack_message_password_error;
    @BindString(R.string.snack_action_to_setting)
    String snack_action_to_setting;
    @BindString(R.string.snack_message_login_success)
    String snack_message_login_success;

    // UI references.
    @Bind(R.id.progress_login)
    ProgressBar mProgressLogin;
    @Bind(R.id.actv_username)
    AutoCompleteTextView mACTVUsername;
    @Bind(R.id.edit_password)
    EditText mEditPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.ll_login)
    LinearLayout mLinearLogin;
    @Bind(R.id.scroll_login_form)
    ScrollView mScrollViewLogin;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the login form.
        addUsernameAutoComplete();

        initListener();

    }

    private void initListener() {
        //软键盘 确定按钮 监听

//        mEditPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                Logger.d("textView = "+textView.getText().toString()+" id= "+id);
//                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
////                    attemptLogin();
//                    Logger.d();
//                    return true;
//                }
//                return false;
//            }
//        });
        RxTextView.editorActions(mEditPassword, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_DONE;
            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)//设置500毫秒的间隔 防止抖动 用户点击太快登录多次
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        //// TODO: 2016/3/20 0020 需要隐藏软键盘
                        attemptLogin();
                    }
                });


        RxView.clicks(mBtnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)//设置500毫秒的间隔 防止抖动 用户点击太快登录多次
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        attemptLogin();
                    }
                });
    }

    private void addUsernameAutoComplete() {
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arrayList.add("36140137" + i + "@qq.com");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_dropdown_item, arrayList);

        mACTVUsername.setAdapter(adapter);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 输入检查 提示错误 正确开始联网登录
     */
    private void attemptLogin() {

        // Reset errors.
        mACTVUsername.setError(null);
        mEditPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = mACTVUsername.getText().toString();
        String password = mEditPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mEditPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditPassword;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mACTVUsername.setError(getString(R.string.error_field_required));
            focusView = mACTVUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mACTVUsername.setError(getString(R.string.error_invalid_username));
            focusView = mACTVUsername;
            cancel = true;
        }

        //所有的检查完成 判断是否能开始联网 还是弹出提示
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            startHttpLogin(username, password);
            httpLogin(username, password);
        }
    }

    private void httpLogin(String username, String password) {
        getUserToken(username, password)
                //得到toke成功 用内部字段保存 在最后得到用户信息一起保存写入
                .map(new Func1<TokenBean, TokenBean>() {
                    @Override
                    public TokenBean call(TokenBean tokenBean) {
                        mAccessToken = tokenBean.getAccess_token();
                        mRefreshToken = tokenBean.getRefresh_token();
                        return tokenBean;
                    }
                })
                //得到Observable<> 将它转换成另一个Observable<>
                .flatMap(new Func1<TokenBean, Observable<UserMeBean>>() {
                    @Override
                    public Observable<UserMeBean> call(TokenBean tokenBean) {
                        return getUserInfo(tokenBean.getToken_type(), tokenBean.getAccess_token());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserMeBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        showProgress(true);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        showProgress(false);
                        if (e instanceof SocketTimeoutException) {
                            //SocketTimeoutException socket连接超时
                            NetUtils.showNetworkError(mContext, mScrollViewLogin, snack_message_net_error, snack_action_to_setting);
                        }
                        if (e instanceof UnknownHostException) {
                            //未知的网络主机 一般是没有联网
                            NetUtils.showNetworkError(mContext, mScrollViewLogin, snack_message_net_error, snack_action_to_setting);
                        }
                        if (e instanceof HttpException) {
                            Snackbar.make(mScrollViewLogin, snack_message_password_error, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onNext(UserMeBean userMeBean) {
                        Snackbar.make(mScrollViewLogin, snack_message_login_success, Snackbar.LENGTH_LONG)
                                .show();
                        saveUserInfo(userMeBean,mAccessToken,mRefreshToken);
                    }
                });
    }


    private Observable<TokenBean> getUserToken(String username, String password) {
        return RetrofitGsonRx.service.httpsTokenRx(BASIC + Base64.getClientInfo(), PASSWORD, username, password);
    }

    private Observable<UserMeBean> getUserInfo(String bearer, String key) {
        return RetrofitGsonRx.service.httpUserRx(bearer + " " + key);
    }


    private void saveUserInfo(UserMeBean result,String mAccessToken,String mRefreshToken) {
        SPUtils.clear(mContext);
        SPUtils.put(mContext,Constant.ACCESSTOKEN,mAccessToken);
        SPUtils.put(mContext,Constant.REFRESHTOKEN,mRefreshToken);
        SPUtils.put(mContext, Constant.ISLOGIN, Boolean.TRUE);
        SPUtils.put(mContext, Constant.USERNAME, result.getUsername());
        SPUtils.put(mContext, Constant.USERID, result.getUser_id());
        SPUtils.put(mContext, Constant.USERHEADKEY, result.getAvatar().getKey());
        SPUtils.put(mContext, Constant.USEREMAIL, result.getEmail());
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
//            mScrollViewLogin.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

            mProgressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mProgressLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
//            mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

