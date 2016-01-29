package licola.demo.com.huabandemo.httpUtils;

import licola.demo.com.huabandemo.API.HttpApi;
import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.converter.Converter;
import licola.demo.com.huabandemo.converter.PinsConverter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by LiCola on  2015/12/05  12:30
 * 主要联网操作工具类
 */
public class RetrofitPins {
    /**
     * 初始化 设置baseUrl
     * 注意addConverterFactory的顺序
     */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HuaBanApplication.getInstance().getString(R.string.url))
            .addConverterFactory(PinsConverter.create())
            .build();

    public static HttpApi service = retrofit.create(HttpApi.class);

}
