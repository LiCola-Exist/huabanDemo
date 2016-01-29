package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by LiCola on  2015/12/05  14:12
 */
public final class Utils {
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 检查输入是否为空
     * @param values String[]
     * @return Returns true if the values of this string[] is empty ro where are empty
     */
    public static int checkStringIsEmpty(String ...values){
        int location=-1;
        if(values.length==1){
            return values[0].isEmpty()?0:-1;
        }
        for (int i = 0,size=values.length; i < size; i++) {
            if (values[i].isEmpty()){
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


    public static float getAspectRatio(int width, int height) {
        float ratio=(float) width / (float) height;
        if (ratio<0.5){
            return 0.5f;
        }
        return ratio;
    }

    public static boolean checkIsGif(String type) {
        if (type.isEmpty()) return false;

        if (type.contains("gif") || type.contains("GIF")) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机的分辨率从dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static int px2dp(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    public static int getScreenWidth(Context context){
        return getPoint(context).x;
    }

    public static int getScreenHeight(Context context){
        return getPoint(context).y;
    }

    @NonNull
    private static Point getPoint(Context context) {
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        return point;
    }
}
