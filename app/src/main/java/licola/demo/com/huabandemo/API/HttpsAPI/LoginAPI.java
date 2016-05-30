package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  21:00
 */

public interface LoginAPI {
    //https 用户登录  的第一步
    // Authorization 报头一个固定的值 内容 grant_type=password&password=密码&username=账号
    //传入用户名和密码
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsTokenRx(@Header(Constant.Authorization) String authorization, @Field("grant_type") String grant,
                                       @Field("username") String username, @Field("password") String password);

    //登录第二步 用上一步结果联网
    @GET("users/me")
    Observable<UserMeAndOtherBean> httpsUserRx(@Header(Constant.Authorization) String authorization);
}
