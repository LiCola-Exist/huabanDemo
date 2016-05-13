package licola.demo.com.huabandemo.HttpUtils;

import java.io.IOException;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by LiCola on  2016/05/12  15:49
 * 进度回调辅助类
 */
public class ProgressHelper {
    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final OnProgressResponseListener listener){

        //增加拦截器
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(),listener)).build();

            }
        });
        return client;
    }
}
