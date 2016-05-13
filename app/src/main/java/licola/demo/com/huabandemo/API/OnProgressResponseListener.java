package licola.demo.com.huabandemo.API;

/**
 * Created by LiCola on  2016/05/12  15:36
 */
public interface OnProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
