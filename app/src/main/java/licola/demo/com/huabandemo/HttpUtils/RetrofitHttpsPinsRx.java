package licola.demo.com.huabandemo.HttpUtils;

import licola.demo.com.huabandemo.API.HttpAPIRx;
import licola.demo.com.huabandemo.Base.HuaBanApplication;
import licola.demo.com.huabandemo.HttpUtils.Converter.AvatarConverter;
import licola.demo.com.huabandemo.R;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by LiCola on  2016/03/17  19:30
 * 主要联网操作工具类
 */
public class RetrofitHttpsPinsRx {
    /**
     * 初始化 设置baseUrl
     * 注意addConverterFactory的顺序
     */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HuaBanApplication.getInstance().getString(R.string.urlHttpsRoot))
            .addConverterFactory(AvatarConverter.create())//定制的转换器 正则表达式替换网络返回的不符合json格式的数据
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//配置网络返回结果 为Observable
            .build();

    public static HttpAPIRx service = retrofit.create(HttpAPIRx.class);

}
