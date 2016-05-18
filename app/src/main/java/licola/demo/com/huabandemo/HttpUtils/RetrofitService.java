package licola.demo.com.huabandemo.HttpUtils;

import com.google.gson.Gson;

import java.io.IOException;

import licola.demo.com.huabandemo.API.HttpAPIRx;
import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import licola.demo.com.huabandemo.HttpUtils.Converter.AvatarConverter;

import licola.demo.com.huabandemo.Util.Logger;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by LiCola on  2016/04/16  0:08
 */
public class RetrofitService {

    //所有的联网地址 统一成https
    public final static String mBaseUrl = "https://api.huaban.com/";

    public static Gson gson = new Gson();


    private static OkHttpClient getClient() {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpBuilder.addInterceptor(logging);
        return httpBuilder.build();

    }

    private static OkHttpClient setProgressClient(OnProgressResponseListener listener) {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpBuilder.addInterceptor(logging);

        httpBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Logger.d("start intercept");
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(),listener)).build();
            }
        });

        return httpBuilder.build();

    }

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static HttpAPIRx createAvatarService() {
        Retrofit retrofit = builder
                .addConverterFactory(AvatarConverter.create(gson))
                .client(getClient())
                .build();
        return retrofit.create(HttpAPIRx.class);
    }

    public static HttpAPIRx createDownloadService(OnProgressResponseListener listener) {
        OkHttpClient client= setProgressClient(listener);
        Retrofit retrofit = builder
                .client(client)
                .build();

        return retrofit.create(HttpAPIRx.class);
    }

}
