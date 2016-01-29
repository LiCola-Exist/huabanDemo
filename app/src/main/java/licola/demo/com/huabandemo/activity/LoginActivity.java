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
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import licola.demo.com.huabandemo.API.HttpInterface;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.bean.TokenBean;
import licola.demo.com.huabandemo.bean.UserMeBean;
import licola.demo.com.huabandemo.httpUtils.HttpRequest;
import licola.demo.com.huabandemo.httpUtils.RetrofitGson;
import retrofit.Call;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    //登录的报文需要
    private static final String BASIC = "Basic ";
    private static final String PASSWORD = "password";

    // UI references.
    @Bind(R.id.progress_login)
    ProgressBar progress_login;
    @Bind(R.id.atv_username)
    AutoCompleteTextView atv_username;
    @Bind(R.id.edit_password)
    EditText edit_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.ll_login)
    LinearLayout ll_login;
    @Bind(R.id.scroll_login_form)
    ScrollView scroll_login_form;


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
        edit_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void addUsernameAutoComplete() {
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("361401376@qq.com");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, arrayList);

        atv_username.setAdapter(adapter);
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
     */
    private void attemptLogin() {

        // Reset errors.
        atv_username.setError(null);
        edit_password.setError(null);

        // Store values at the time of the login attempt.
        String username = atv_username.getText().toString();
        String password = edit_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            edit_password.setError(getString(R.string.error_invalid_password));
            focusView = edit_password;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            atv_username.setError(getString(R.string.error_field_required));
            focusView = atv_username;
            cancel = true;
        } else if (!isEmailValid(username)) {
            atv_username.setError(getString(R.string.error_invalid_username));
            focusView = atv_username;
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
            startHttpLogin(username, password);
        }
    }

    private void startHttpLogin(String username, String password) {
        getToken(username, password);
    }

    private void getToken(String username, String password) {
        Call<TokenBean> call = RetrofitGson.service.httpsLogin(BASIC + Base64.getClientInfo(), PASSWORD, username, password);
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
                Snackbar.make(scroll_login_form, R.string.error_incorrect_password, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onHttpFailure(String error) {
                //联网错误 需要提示
                showProgress(false);
                Snackbar.make(scroll_login_form, R.string.net_error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void getUserMe(String bearer, String key) {
        String Authorization = bearer + " " + key;
        Call<UserMeBean> call = RetrofitGson.service.httpGetMe(Authorization);
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
                Snackbar.make(scroll_login_form, "登录成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveUserInfo(result);
            }

            @Override
            public void onHttpError(int code, String msg) {
                Snackbar.make(scroll_login_form, R.string.net_error_data, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onHttpFailure(String error) {
                Snackbar.make(scroll_login_form, R.string.net_error, Snackbar.LENGTH_LONG)
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

            scroll_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            scroll_login_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scroll_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress_login.setVisibility(show ? View.VISIBLE : View.GONE);
            progress_login.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress_login.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress_login.setVisibility(show ? View.VISIBLE : View.GONE);
            scroll_login_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

