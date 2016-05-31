package licola.demo.com.huabandemo.HttpUtils;

import com.google.gson.Gson;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import licola.demo.com.huabandemo.HttpUtils.Converter.AvatarConverter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static licola.demo.com.huabandemo.HttpUtils.OkHttpHelper.addLogClient;
import static licola.demo.com.huabandemo.HttpUtils.OkHttpHelper.addProgressClient;


/**
 * Created by LiCola on  2016/04/16  0:08
 */
public class RetrofitClient {

    //所有的联网地址 统一成https
    public final static String mBaseUrl = "https://api.huaban.com/";

    public static Gson gson = new Gson();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(AvatarConverter.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .client(addLogClient(httpClient).build())
                .build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, OnProgressResponseListener listener) {
        Retrofit retrofit = builder
                .client(addProgressClient(httpClient, listener).build())
                .build();
        return retrofit.create(serviceClass);
    }


}
