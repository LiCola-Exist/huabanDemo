package licola.demo.com.huabandemo.API;

import licola.demo.com.huabandemo.Bean.ListPinsBean;
import licola.demo.com.huabandemo.SearchResult.SearchBoardBean;
import licola.demo.com.huabandemo.SearchResult.SearchImageBean;
import licola.demo.com.huabandemo.SearchResult.SearchPeopleBean;
import licola.demo.com.huabandemo.Login.TokenBean;
import licola.demo.com.huabandemo.Login.UserMeBean;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by LiCola on  2015/12/04  18:24
 * 传统的网络数据回调接口 使用时需要回调得到网络结果
 */
public interface HttpAPICall {

    //http://api.huaban.com/favorite/food_drink?limit=20
    @GET("favorite/{type}")
    Call<ListPinsBean> httpTypeLimit(@Path("type") String type, @Query("limit") int limit);

    //http://api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    @GET("favorite/{type}")
    Call<ListPinsBean> httpTypeMaxLimit(@Path("type") String type, @Query("max") int max, @Query("limit") int limit);

    //http://api.huaban.com/all/food_drink/keywords
    @GET("all/{type}/keywords")
    Call<String> httpTypeKeyWord(@Path("type") String type);

    //http://api.huaban.com/favorite/food_drink?q=%E6%96%99%E7%90%86&page=1&per_page=2
    @GET("favorite/{type}")
    Call<ListPinsBean> httpTypeSearch(@Path("type") String type, @Query("q") String key,
                                      @Query("page") int page, @Query("per_page") int per_page);

    //http://api.huaban.com/search/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    @GET("search/")
    Call<SearchImageBean> httpImageSearch(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //http://api.huaban.com/search/boards/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=1
    @GET("search/boards/")
    Call<SearchBoardBean> httpBoardSearch(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //http://api.huaban.com/search/people/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    @GET("search/people/")
    Call<SearchPeopleBean> httpPeopleSearch(@Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Call<TokenBean> httpsToken(@Header("Authorization") String authorization, @Field("grant_type") String grant,
                               @Field("username") String username, @Field("password")String password);

    @GET("users/me")
    Call<UserMeBean> httpUsers(@Header("Authorization") String authorization);
}
