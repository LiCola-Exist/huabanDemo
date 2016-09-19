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
 * token的Api接口
 * 详细参照https://oauth.net/2/ OAuth2.0模式
 *
 */

public interface TokenAPI {
    //https 获取token接口 OAuth 2.0密码模式
    //Authorization 报头一个固定的值 内容 grant_type=password&password=密码&username=账号
    //传入用户名和密码
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsGetTokenRx(@Header(Constant.Authorization) String authorization, @Field("grant_type") String grant,
                                          @Field("username") String username, @Field("password") String password);

    //刷新token接口
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsRefreshTokenRx(@Header(Constant.Authorization) String authorization, @Field("grant_type") String grant,
                                       @Field("refresh_token") String username);
}
