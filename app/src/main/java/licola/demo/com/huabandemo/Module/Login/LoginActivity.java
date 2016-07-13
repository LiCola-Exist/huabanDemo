package licola.demo.com.huabandemo.Module.Login;

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
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;

import licola.demo.com.huabandemo.API.HttpsAPI.LoginAPI;
import licola.demo.com.huabandemo.API.HttpsAPI.UserAPI;
import licola.demo.com.huabandemo.Entity.BoardItemInfoBean;
import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Module.User.UserActivity;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.IntentUtils;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NetUtils;
import licola.demo.com.huabandemo.Util.SPBuild;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.Util.SPUtils;
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

    private static final String PASSWORD = "password";
    private static final String TYPE_KEY = "type_key";

    private TokenBean mTokenBean;
    private UserMeAndOtherBean mUserBean;

    //需要的资源
    @BindString(R.string.snack_message_login_success)
    String snack_message_login_success;

    // UI references.
    @BindView(R.id.progress_login)
    ProgressBar mProgressLogin;
    @BindView(R.id.actv_username)
    AutoCompleteTextView mACTVUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.ll_login)
    LinearLayout mLinearLogin;
    @BindView(R.id.scroll_login_form)
    ScrollView mScrollViewLogin;

    //联网的授权字段 提供子Fragment使用
    public String mAuthorization = Base64.mClientInto;

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

    public static void launch(Activity activity, String message) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(TYPE_KEY, message);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String message = getIntent().getStringExtra(TYPE_KEY);
        if (!TextUtils.isEmpty(message)) {
            NetUtils.showSnackBar(mScrollViewLogin, message);
        }
        // Set up the login form.
        addUsernameAutoComplete();


    }

    @Override
    protected  void initResAndListener() {
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
        RxView.clicks(mBtnRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)//设置500毫秒的间隔 防止抖动 用户点击太快登录多次
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startNetRegister();
                    }
                });
    }

    private void startNetRegister() {
        //跳转网络 注册
        Intent intent = IntentUtils.startUriLink(getResources().getString(R.string.urlRegister));
        if (IntentUtils.checkResolveIntent(this, intent)) {
            startActivity(intent);
        } else {
            Logger.d("checkResolveIntent = null");
        }
    }

    private void addUsernameAutoComplete() {
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arrayList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            arrayList.add("36140137" + i + "@qq.com");
//        }
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

    private void httpLogin(final String username, final String password) {
        RetrofitClient.createService(LoginAPI.class)
                .httpsTokenRx(mAuthorization, PASSWORD, username, password)
                //得到toke成功 用内部字段保存 在最后得到用户信息一起保存写入
                //得到Observable<> 将它转换成另一个Observable<>
                .flatMap((Func1<TokenBean, Observable<UserMeAndOtherBean>>) tokenBean -> {
                    mTokenBean = tokenBean;
                    mAuthorization = tokenBean.getToken_type() + " " + tokenBean.getAccess_token();
                    return RetrofitClient.createService(LoginAPI.class)
                            .httpsUserRx(mAuthorization);
                })
                .flatMap((Func1<UserMeAndOtherBean, Observable<BoardListInfoBean>>) userMeAndOtherBean -> {
                    mUserBean = userMeAndOtherBean;
                    return RetrofitClient.createService(UserAPI.class)
                            .httpsBoardListInfo(mAuthorization, Constant.OPERATEBOARDEXTRA);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardListInfoBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        showProgress(true);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        showProgress(false);
                        NetUtils.checkHttpException(mContext, e, mScrollViewLogin);
                    }

                    @Override
                    public void onNext(BoardListInfoBean boardListInfoBean) {
                        Logger.d();
                        showProgress(false);
                        NetUtils.showSnackBar(mScrollViewLogin, snack_message_login_success).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                UserActivity.launch(LoginActivity.this, String.valueOf(mUserBean.getUser_id()), mUserBean.getUsername());
                                finish();
                            }
                        });
                        saveUserInfo(mUserBean, mTokenBean, username, password, boardListInfoBean.getBoards());
                        Logger.d();
                    }
                });

    }


    private void saveUserInfo(UserMeAndOtherBean result,
                              TokenBean mTokenBean,
                              String mUserAccount, String mUserPassword, List<BoardItemInfoBean> mBoardList) {

        //保存先清空内容
        SPUtils.clear(getApplicationContext());

        //构造两个StringBuilder对象 拼接用逗号分隔 写入 SharedPreferences
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();

        for (int i = 0, size = mBoardList.size(); i < size; i++) {
            boardTitle.append(mBoardList.get(i).getTitle());
            boardId.append(mBoardList.get(i).getBoard_id());

            if (i != size - 1) {
                boardTitle.append(Constant.SEPARATECOMMA);
                boardId.append(Constant.SEPARATECOMMA);
            }
        }

        new SPBuild(getApplicationContext())
                .addData(Constant.ISLOGIN, Boolean.TRUE)//登陆志位
                .addData(Constant.LOGINTIME, System.currentTimeMillis())//登陆时间
                .addData(Constant.USERACCOUNT, mUserAccount)//账号
                .addData(Constant.USERPASSWORD, mUserPassword)//密码
                .addData(Constant.TOKENACCESS, mTokenBean.getAccess_token())
                .addData(Constant.TOKENREFRESH, mTokenBean.getRefresh_token())
                .addData(Constant.TOKENTYPE, mTokenBean.getToken_type())
                .addData(Constant.TOKENEXPIRESIN, mTokenBean.getExpires_in())
                .addData(Constant.USERNAME, result.getUsername())
                .addData(Constant.USERID, String.valueOf(result.getUser_id()))
                .addData(Constant.USERHEADKEY, result.getAvatar())
                .addData(Constant.USEREMAIL, result.getEmail())
                .addData(Constant.BOARDTILTARRAY, boardTitle.toString())
                .addData(Constant.BOARDIDARRAY, boardId.toString())
                .build();
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

