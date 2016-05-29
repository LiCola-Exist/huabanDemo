package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Display;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;

import licola.demo.com.huabandemo.R;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by LiCola on  2015/12/05  14:12
 */
public final class Utils {
    /**
     * 检查对象非空
     *
     * @param object
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 检查是否在主线程 关键检查 弹出异常
     */
    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
    }

    /**
     * 检查当前是否在主线程 返回true为主线程
     * @return
     */
    public static boolean checkUiThreadBoolean(){
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 检查输入是否为空
     *
     * @param values String[]
     * @return Returns true if the values of this string[] is empty ro where are empty
     */
    public static int checkStringIsEmpty(String... values) {
        int location = -1;
        if (values.length == 1) {
            return values[0].isEmpty() ? 0 : -1;
        }
        for (int i = 0, size = values.length; i < size; i++) {
            if (values[i].isEmpty()) {
                return i;
            }
        }
        return location;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 计算宽高比
     *
     * @param width
     * @param height
     * @return
     */
    public static float getAspectRatio(int width, int height) {
        float ratio = (float) width / (float) height;
        //宽高比<0.7 表示长图 需要截断处理
        if (ratio < 0.7) {
            return 0.7f;
        }
        //// TODO: 2016/5/11 0011 ratio>3会导致图片不能显示
        return ratio;
    }


    /**
     * 检查图片类型是否为 git
     *
     * @param type
     * @return
     */
    public static boolean checkIsGif(String type) {
        if (type == null||type.isEmpty()) {
            return false;
        }

        if (type.contains("gif") || type.contains("GIF")) {
            return true;
        }
        return false;
    }

    public static String getPinsType(String type){
        if (type == null||type.isEmpty()) {
            return ".jpeg";
        }

        if (type.contains("jpeg")){
            return ".jpeg";
        }else if (type.contains("png")){
            return ".png";
        }else if (type.contains("gif")){
            return ".gif";
        }

        return ".jpeg";
    }

    /**
     * 根据手机的分辨率从dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return getPoint(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getPoint(context).y;
    }

    @NonNull
    private static Point getPoint(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    /** Returns true if {@code annotations} contains an instance of {@code cls}. */
    public static boolean isAnnotationPresent(Annotation[] annotations,
                                       Class<? extends Annotation> cls) {
        for (Annotation annotation : annotations) {
            if (cls.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    public static ResponseBody buffer(final ResponseBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.source().readAll(buffer);
        return ResponseBody.create(body.contentType(), body.contentLength(), buffer);
    }



    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
