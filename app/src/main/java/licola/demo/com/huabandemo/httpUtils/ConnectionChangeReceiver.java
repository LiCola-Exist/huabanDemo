package licola.demo.com.huabandemo.HttpUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/03/28  22:17
 * 监听网络变化的广播接收器
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "ConnectionChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Logger.d("not network");
            //改变背景或者 处理网络的全局变量
        } else {
            //改变背景或者 处理网络的全局变量
            Logger.d("other net");
        }
    }
}
