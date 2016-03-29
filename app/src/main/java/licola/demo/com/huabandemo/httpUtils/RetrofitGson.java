package licola.demo.com.huabandemo.httpUtils;

import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.API.HttpAPICall;
import licola.demo.com.huabandemo.R;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by LiCola on  2015/12/05  12:30
 * 主要联网操作工具类
 */
public class RetrofitGson {
    /**
     * 初始化 设置baseUrl
     * 注意addConverterFactory的顺序
     */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HuaBanApplication.getInstance().getString(R.string.urlRoot))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static HttpAPICall service = retrofit.create(HttpAPICall.class);

}
