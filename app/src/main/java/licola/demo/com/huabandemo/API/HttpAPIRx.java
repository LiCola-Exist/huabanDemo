package licola.demo.com.huabandemo.API;

import java.util.List;

import licola.demo.com.huabandemo.bean.BoardDetailBean;
import licola.demo.com.huabandemo.bean.ListPinsBean;
import licola.demo.com.huabandemo.bean.PinsDetailBean;
import licola.demo.com.huabandemo.bean.PinsEntity;
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
    Observable<ListPinsBean> httpTypeLimitRx(@Path("type") String type, @Query("limit") int limit);

    //http://api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    //模板类型 的后续联网 max
    @GET("favorite/{type}")
    Observable<ListPinsBean> httpTypeMaxLimitRx(@Path("type") String type, @Query("max") int max, @Query("limit") int limit);

    //http://api.huaban.com/search/hint?q=%E4%BA%BA
    //搜索关键字 提示
    @GET("search/hint")
    Observable<SearchHintBean> httpSearHintBean(@Query("q") String key);

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

    //http://api.huaban.com/pins/663478425
    //根据图片id获取详情
    @GET("pins/{pinsId}")
    Observable<PinsDetailBean> httpPinsDetail(@Path("pinsId") String pinsId);

    //http://api.huaban.com/boards/3514299
    //获取画板的详情
    @GET("boards/{boardId}")
    Observable<BoardDetailBean> httpBoardDetail(@Path("boardId") String boardId);

    //http://api.huaban.com/boards/19196160/pins?limit=40
    //获取画板的图片集合
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpBoardPins(@Path("boardId") String boardId, @Query("limit") int limit);

    //http://api.huaban.com/boards/19196160/pins?limit=40&max=508414882
    //获取画板的图片集合 根据上一个图片的id继续加载
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpBoardPinsMax(@Path("boardId") String boardId, @Query("max") int max, @Query("limit") int limit);

    //http://api.huaban.com/pins/654197326/recommend/?page=1&limit=40
    //获取某个图片的推荐图片列表
    @GET("pins/{pinsId}/recommend/")
    Observable<List<PinsEntity>> httpPinsRecommend(@Path("pinsId") String pinsId, @Query("page") int page, @Query("limit") int limit);

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
