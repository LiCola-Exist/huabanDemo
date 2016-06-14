package licola.demo.com.huabandemo.HttpUtils;

import java.io.IOException;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import licola.demo.com.huabandemo.Util.Logger;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by LiCola on  2016/05/23  22:09
 */

public class OkHttpHelper {

    private static ProgressBean progressBean = new ProgressBean();

    private static ProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addLogClient(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(logging);
        return builder;
    }

    public static OkHttpClient.Builder addProgressClient(OkHttpClient.Builder builder, OnProgressResponseListener listener) {

//        OnProgressResponseListener listener=new OnProgressResponseListener() {
//            @Override
//            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//                if (mProgressHandler==null){
//                    return;
//                }
//                progressBean.setBytesRead(bytesRead);
//                progressBean.setContentLength(contentLength);
//                progressBean.setDone(done);
//                mProgressHandler.sendMessage(progressBean);
//            }
//        };

        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Logger.d("start intercept");
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(), listener))
                        .build();
            }
        });

        return builder;

    }
}
