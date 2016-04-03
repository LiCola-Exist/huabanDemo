package licola.demo.com.huabandemo.HttpUtils;

import licola.demo.com.huabandemo.API.HttpAPIRx;
import licola.demo.com.huabandemo.Base.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by LiCola on  2016/03/17  19:30
 * 主要联网操作工具类
 */
public class RetrofitGsonRx {
    /**
     * 初始化 设置baseUrl
     * 注意addConverterFactory的顺序
     */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HuaBanApplication.getInstance().getString(R.string.urlRoot))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static HttpAPIRx service = retrofit.create(HttpAPIRx.class);

}
