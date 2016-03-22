package licola.demo.com.huabandemo.API;

import licola.demo.com.huabandemo.bean.CardBigBean;
import licola.demo.com.huabandemo.bean.SearchBoardBean;
import licola.demo.com.huabandemo.bean.SearchHintBean;
import licola.demo.com.huabandemo.bean.SearchImageBean;
import licola.demo.com.huabandemo.bean.SearchPeopleBean;
import licola.demo.com.huabandemo.bean.TokenBean;
import licola.demo.com.huabandemo.bean.UserMeBean;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/03/17  19:24
 * Retrofit的RxJava接口 网络返回结果为Observable 可以直接观察
 */
public interface HttpAPIRx {

    //http://api.huaban.com/favorite/food_drink?limit=20
    // 模板类型
    @GET("favorite/{type}")
    Observable<CardBigBean> httpTypeLimitRx(@Path("type") String type, @Query("limit") int limit);

    //http://api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    //模板类型 的后续联网 max
    @GET("favorite/{type}")
    Observable<CardBigBean> httpTypeMaxLimitRx(@Path("type") String type, @Query("max") int max, @Query("limit") int limit);

    //http://api.huaban.com/search/hint?q=%E4%BA%BA
    //搜索关键字 提示
    @GET("search/hint")
    Observable<SearchHintBean> httpSearHintBean(@Query("q") String key);

    //弃用
//    //http://api.huaban.com/all/food_drink/keywords
//    @GET("all/{type}/keywords")
//    Observable<String> httpTypeKeyWordRx(@Path("type") String type);
//
//    //http://api.huaban.com/favorite/food_drink?q=%E6%96%99%E7%90%86&page=1&per_page=2
//    @GET("favorite/{type}")
//    Observable<CardBigBean> httpTypeSearchRx(@Path("type") String type, @Query("q") String key,
//                                     @Query("page") int page, @Query("per_page") int per_page);


    //http://api.huaban.com/search/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    //图片搜索 返回结果跟模板类型差不多
    @GET("search/")
    Observable<SearchImageBean> httpImageSearchRx(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //http://api.huaban.com/search/boards/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=1
    //画板搜索
    @GET("search/boards/")
    Observable<SearchBoardBean> httpBoardSearchRx(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //http://api.huaban.com/search/people/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    //用户搜索
    @GET("search/people/")
    Observable<SearchPeopleBean> httpPeopleSearchRx(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);


    //https 用户登录  的第一步
    //传入用户名和密码
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsTokenRx(@Header("Authorization") String authorization, @Field("grant_type") String grant,
                                       @Field("username") String username, @Field("password") String password);
    //登录第二步 用上一步结果联网
    @GET("users/me")
    Observable<UserMeBean> httpUserRx(@Header("Authorization") String authorization);
}
