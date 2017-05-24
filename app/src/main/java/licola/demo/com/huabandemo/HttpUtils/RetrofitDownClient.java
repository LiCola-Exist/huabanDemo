package licola.demo.com.huabandemo.HttpUtils;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static licola.demo.com.huabandemo.HttpUtils.OkHttpHelper.addProgressClient;


/**
 * Created by LiCola on  2016/04/16  0:08
 */
public class RetrofitDownClient {

    //所有的联网地址 统一成https
    public final static String mBaseUrl = "https://api.huaban.com/";


    //static 静态成员 一旦被初始化就就保存在同一个地址
    //所以一旦 被addProgressClient 修饰添加拦截器 之后 整个系统唯一的httpClient 就被添加了拦截器
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass, OnProgressResponseListener listener) {
        Retrofit retrofit = builder
                .client(addProgressClient(httpClient, listener).build())
                .build();
        return retrofit.create(serviceClass);
    }


}
