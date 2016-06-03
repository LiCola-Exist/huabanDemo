package licola.demo.com.huabandemo.Util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import licola.demo.com.huabandemo.R;

/**
 * 跟网络相关的工具类
 *
 * @author zhy
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 弹出snackbar 提示错误 并添加点击事件 跳转到设置
     *
     * @param context
     * @param view
     * @param message
     * @param action
     */
    public static void showNetworkErrorSnackBar(final Context context, View view, String message, String action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .show();

    }

    public static Snackbar showSnackBar(View rootView, String message) {
        Snackbar snackbar=Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    public static void checkHttpException(Context mContext, Throwable mThrowable, View mRootView) {
        String snack_action_to_setting = mContext.getString(R.string.snack_action_to_setting);
        if ((mThrowable instanceof UnknownHostException)) {
            String snack_message_net_error = mContext.getString(R.string.snack_message_net_error);
            NetUtils.showNetworkErrorSnackBar(mContext, mRootView, snack_message_net_error, snack_action_to_setting);
        } else if (mThrowable instanceof JsonSyntaxException) {
            String snack_message_data_error = mContext.getString(R.string.snack_message_data_error);
            NetUtils.showNetworkErrorSnackBar(mContext, mRootView, snack_message_data_error, snack_action_to_setting);
        } else if (mThrowable instanceof SocketTimeoutException) {
            String snack_message_time_out = mContext.getString(R.string.snack_message_timeout_error);
            NetUtils.showNetworkErrorSnackBar(mContext, mRootView, snack_message_time_out, snack_action_to_setting);
        } else if (mThrowable instanceof ConnectException) {
            String snack_message_net_error = mContext.getString(R.string.snack_message_net_error);
            NetUtils.showNetworkErrorSnackBar(mContext, mRootView, snack_message_net_error, snack_action_to_setting);
        } else {
            String snack_message_unknown_error = mContext.getString(R.string.snack_message_unknown_error);
            NetUtils.showSnackBar(mRootView,snack_message_unknown_error);
        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
