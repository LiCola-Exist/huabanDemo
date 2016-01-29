package licola.demo.com.huabandemo.httpUtils;

import licola.demo.com.huabandemo.HuaBanApplication;
import licola.demo.com.huabandemo.API.HttpApi;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.bean.TokenBean;
import licola.demo.com.huabandemo.bean.UserMeBean;
import licola.demo.com.huabandemo.converter.Converter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
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
            .baseUrl(HuaBanApplication.getInstance().getString(R.string.url))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static HttpApi service = retrofit.create(HttpApi.class);

}
