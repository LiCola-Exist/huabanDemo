package licola.demo.com.huabandemo.Util;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.File;

import licola.demo.com.huabandemo.R;

/**
 * Created by LiCola on  2016/05/16  18:46
 * 通知栏工具类 囿于Android各个版本对通知栏的规定变化
 * 默认使用应用图标 作为icon显示 每个方法提供默认使用 以后再根据实际情况修改
 */
public class NotificationUtils {
    //默认的图标
    private static final int resId=R.mipmap.ic_launcher;

    //默认使用应用图标的通知栏
    public static Notification showNotification(
            Application application, String title, String text) {
        return showNotification(application, resId, title, text);
    }

    public static Notification showNotification(
            Application application, int resIdIcon, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resIdIcon)//图标
                .setContentTitle(title)//标题
                .setContentText(text)//正文描述
                .setTicker(text);//滚动文字
        return mBuilder.build();
    }

    //默认方法
    public static Notification showProcessNotification(
            Application application, String title, int process) {
        return showProcessNotification(application, resId, title, process);
    }


    public static Notification showProcessNotification(
            Application application, int resIdIcon, String title, int process) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resIdIcon)//图标
                .setContentTitle(title)//标题
                .setProgress(100, process, false)
                ;
        return mBuilder.build();
    }


    public static Notification showIntentNotification(
            Application application,Intent intent, String title, String text) {
        return showIntentNotification(application,resId,intent,title,text);
    }

    public static Notification showIntentNotification(
            Application application, int resId, Intent intent, String title, String text) {

        PendingIntent pendingIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(application)
                .setSmallIcon(resId)//图标
                .setContentTitle(title)
                .setContentText(text)
                .setTicker(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)//点击之后 自动消失
                ;
        return mBuilder.build();
    }

}
