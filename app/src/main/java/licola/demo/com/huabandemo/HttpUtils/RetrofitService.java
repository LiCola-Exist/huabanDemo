package licola.demo.com.huabandemo.HttpUtils;

import com.google.gson.Gson;

import licola.demo.com.huabandemo.API.HttpAPIRx;
import licola.demo.com.huabandemo.HttpUtils.Converter.AvatarConverter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by LiCola on  2016/04/16  0:08
 */
public class RetrofitService {

    //所有的联网地址 统一成https
    public final static String mBaseUrl = "https://api.huaban.com/";


    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    public static Gson gson = new Gson();

    public static HttpAPIRx createGonsService() {
        Retrofit retrofit = builder
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(HttpAPIRx.class);
    }

    public static HttpAPIRx createAvatarService() {
        Retrofit retrofit = builder
                .addConverterFactory(AvatarConverter.create(gson))
                .build();
        return retrofit.create(HttpAPIRx.class);

    }

}
