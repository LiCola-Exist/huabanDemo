package licola.demo.com.huabandemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import licola.demo.com.huabandemo.API.HttpInterface;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.bean.TokenBean;
import licola.demo.com.huabandemo.bean.UserMeBean;
import licola.demo.com.huabandemo.httpUtils.HttpRequest;
import licola.demo.com.huabandemo.httpUtils.RetrofitGson;
import licola.demo.com.huabandemo.httpUtils.RetrofitGsonRx;
import retrofit.Call;
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
    @Bind(R.id.atv_username)
    AutoCompleteTextView mAtvUsername;
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
        return "Login";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        setupActionBar();
        // Set up the login form.
        addUsernameAutoComplete();

        initListener();

    }

    private void initListener() {
        //软键盘 确定按钮 监听
        mEditPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
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
            arrayList.add("36140137"+i+"@qq.com");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, arrayList);

        mAtvUsername.setAdapter(adapter);
    }

    /**
     * Set up the {@link ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 输入检查 提示错误 正确开始联网登录
     */
    private void attemptLogin() {

        // Reset errors.
        mAtvUsername.setError(null);
        mEditPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = mAtvUsername.getText().toString();
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
            mAtvUsername.setError(getString(R.string.error_field_required));
            focusView = mAtvUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mAtvUsername.setError(getString(R.string.error_invalid_username));
            focusView = mAtvUsername;
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
                        saveUserInfo(userMeBean);
                    }
                });
    }

    private void startHttpLogin(String username, String password) {
        getToken(username, password);
    }

    private Observable<TokenBean> getUserToken(String username, String password) {
        return RetrofitGsonRx.service.httpsTokenRx(BASIC + Base64.getClientInfo(), PASSWORD, username, password);
    }

    private Observable<UserMeBean> getUserInfo(String bearer, String key) {
        return RetrofitGsonRx.service.httpUserRx(bearer + " " + key);
    }

    private void getToken(String username, String password) {

        Call<TokenBean> call = RetrofitGson.service.httpsToken(BASIC + Base64.getClientInfo(), PASSWORD, username, password);
        mCall = call;
        HttpRequest.Requeset(call, new HttpInterface<TokenBean>() {
            @Override
            public void onHttpStart() {
                showProgress(true);
            }

            @Override
            public void onHttpSuccess(TokenBean result) {
                //成功后下一步
                getUserMe(result.getToken_type(), result.getAccess_token());
            }

            @Override
            public void onHttpError(int code, String msg) {
                //网络返回数据错误 需要提示
                showProgress(false);
                Snackbar.make(mScrollViewLogin, R.string.error_incorrect_password, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onHttpFailure(String error) {
                //联网错误 需要提示
                showProgress(false);
                Snackbar.make(mScrollViewLogin, R.string.net_error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    private void getUserMe(String bearer, String key) {
        String Authorization = bearer + " " + key;
        Call<UserMeBean> call = RetrofitGson.service.httpUsers(Authorization);
        mCall = call;

        HttpRequest.Requeset(call, new HttpInterface<UserMeBean>() {

            @Override
            public void onHttpFinish() {
                //联网结束
                showProgress(false);
            }

            @Override
            public void onHttpSuccess(UserMeBean result) {
                //登录成功需要保存数据
                Snackbar.make(mScrollViewLogin, "登录成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveUserInfo(result);
            }

            @Override
            public void onHttpError(int code, String msg) {
                Snackbar.make(mScrollViewLogin, R.string.net_error_data, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onHttpFailure(String error) {
                Snackbar.make(mScrollViewLogin, R.string.net_error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void saveUserInfo(UserMeBean result) {
        SPUtils.put(mContext, Constant.ISLOGIN, Boolean.FALSE);
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

            mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
            mScrollViewLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
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
            mProgressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            mScrollViewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

