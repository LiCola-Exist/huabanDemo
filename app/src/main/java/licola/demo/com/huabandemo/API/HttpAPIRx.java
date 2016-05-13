package licola.demo.com.huabandemo.API;


import java.util.List;

import licola.demo.com.huabandemo.Module.BoardDetail.AttentionOperateBean;
import licola.demo.com.huabandemo.Module.BoardDetail.BoardDetailBean;
import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.Module.ImageDetail.GatherInfoBean;
import licola.demo.com.huabandemo.Module.ImageDetail.GatherResultBean;
import licola.demo.com.huabandemo.Module.ImageDetail.LikeOperateBean;
import licola.demo.com.huabandemo.Module.ImageDetail.PinsDetailBean;
import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.Module.Attention.AttentionBoardListBean;
import licola.demo.com.huabandemo.Module.Attention.AttentionPinsBean;
import licola.demo.com.huabandemo.Module.Search.SearchHintBean;
import licola.demo.com.huabandemo.Module.SearchResult.SearchBoardListBean;
import licola.demo.com.huabandemo.Module.SearchResult.SearchImageBean;
import licola.demo.com.huabandemo.Module.SearchResult.SearchPeopleListBean;
import licola.demo.com.huabandemo.Module.User.UserBoardListBean;
import licola.demo.com.huabandemo.Module.User.UserBoardSingleBean;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by LiCola on  2016/03/17  19:24
 * Retrofit的RxJava接口 网络返回结果为Observable 可以直接观察
 */
public interface HttpAPIRx {

    //https//api.huaban.com/favorite/food_drink?limit=20
    // 模板类型
    @GET("favorite/{type}")
    Observable<ListPinsBean> httpsTypeLimitRx(@Header("Authorization") String authorization, @Path("type") String type, @Query("limit") int limit);

    //https//api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    //模板类型 的后续联网 max
    @GET("favorite/{type}")
    Observable<ListPinsBean> httpsTypeMaxLimitRx(@Header("Authorization") String authorization, @Path("type") String type, @Query("max") int max, @Query("limit") int limit);


    //https//api.huaban.com/search/hint?q=%E4%BA%BA
    //搜索关键字 提示
    @GET("search/hint")
    Observable<SearchHintBean> httpsSearHintBean(@Header("Authorization") String authorization, @Query("q") String key);

    //https//api.huaban.com/search/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    //图片搜索 返回结果跟模板类型差不多
    @GET("search/")
    Observable<SearchImageBean> httpsImageSearchRx(@Header("Authorization") String authorization, @Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //https//api.huaban.com/search/boards/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=1
    //画板搜索
    @GET("search/boards/")
    Observable<SearchBoardListBean> httpsBoardSearchRx(@Header("Authorization") String authorization, @Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //https//api.huaban.com/search/people/?q=%E7%BE%8E%E9%A3%9F&page=1&per_page=2
    //用户搜索
    @GET("search/people/")
    Observable<SearchPeopleListBean> httpsPeopleSearchRx(@Header("Authorization") String authorization, @Query("q") String key, @Query("page") int page, @Query("per_page") int per_page);

    //https://api.huaban.com/pins/663478425
    //根据图片id获取详情
    @GET("pins/{pinsId}")
    Observable<PinsDetailBean> httpsPinsDetailRx(@Header("Authorization") String authorization, @Path("pinsId") String pinsId);

    //https//api.huaban.com/boards/3514299
    //获取画板的详情
    @GET("boards/{boardId}")
    Observable<BoardDetailBean> httpsBoardDetailRx(@Header("Authorization") String authorization, @Path("boardId") String boardId);

    //https//api.huaban.com/boards/19196160/pins?limit=40
    //获取画板的图片集合
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpsBoardPinsRx(@Header("Authorization") String authorization, @Path("boardId") String boardId, @Query("limit") int limit);

    //https//api.huaban.com/boards/19196160/pins?limit=40&max=508414882
    //获取画板的图片集合 根据上一个图片的id继续加载
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpsBoardPinsMaxRx(@Header("Authorization") String authorization, @Path("boardId") String boardId, @Query("max") int max, @Query("limit") int limit);

    //https//api.huaban.com/pins/654197326/recommend/?page=1&limit=40
    //获取某个图片的推荐图片列表
    @GET("pins/{pinsId}/recommend/")
    Observable<List<PinsMainEntity>> httpPinsRecommendRx(@Header("Authorization") String authorization, @Path("pinsId") String pinsId, @Query("page") int page, @Query("limit") int limit);

    //https 用户登录  的第一步
    // Authorization 报头一个固定的值 内容 grant_type=password&password=密码&username=账号
    //传入用户名和密码
    @FormUrlEncoded
    @POST("https://huaban.com/oauth/access_token/")
    Observable<TokenBean> httpsTokenRx(@Header("Authorization") String authorization, @Field("grant_type") String grant,
                                       @Field("username") String username, @Field("password") String password);

    //登录第二步 用上一步结果联网
    @GET("users/me")
    Observable<UserMeAndOtherBean> httpsUserRx(@Header("Authorization") String authorization);

    //https://api.huaban.com/following?limit=40
    //我的关注图片  需要 报头 bearer getAccess_token
    @GET("following")
    Observable<AttentionPinsBean> httpsMyFollowingPinsRx(@Header("Authorization") String authorization, @Query("limit") int limit);

    //https://api.huaban.com/following?limit=40&max=670619456
    //我的关注图片的 后续滑动联网
    @GET("following")
    Observable<AttentionPinsBean> httpsMyFollowingPinsMaxRx(@Header("Authorization") String authorization, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/boards/following?page=1&per_page=20
    //我的关注画板
    @GET("boards/following")
    Observable<AttentionBoardListBean> httpsMyFollowingBoardRx(@Header("Authorization") String authorization, @Query("page") int page, @Query("per_page") int per_page);

    //https://api.huaban.com/users/15246080
    //我的个人信息
    @GET("users/{userId}")
    Observable<UserMeAndOtherBean> httpsUserInfoRx(@Header("Authorization") String authorization, @Path("userId") String pinsId);

    //https://api.huaban.com/users/15246080/boards?limit=20
    //用户画板信息
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpsUserBoardRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/16211815/boards?limit=20&max=18375118
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpsUserBoardMaxRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/pins?limit=40
    //用户的采集
    @GET("users/{userId}/pins")
    Observable<ListPinsBean> httpsUserPinsRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/pins?limit=40&max=19532400
    //后续滑动联网
    @GET("users/{userId}/pins")
    Observable<ListPinsBean> httpsUserPinsMaxRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/likes?limit=40
    //用户的喜欢
    @GET("users/{userId}/likes")
    Observable<ListPinsBean> httpsUserLikePinsRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/743988/likes?limit=40&max=4338219
    //用户喜欢的后续联网
    @GET("users/{userId}/likes")
    Observable<ListPinsBean> httpsUserLikePinsMaxRx(@Header("Authorization") String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);


    @GET("http://img.hb.aicdn.com/{pinId}")
    Observable<ResponseBody> httpDownImage(@Path("pinId") String pinId);

    //https://api.huaban.com/pins/687738004/like
    //https://api.huaban.com/pins/687738004/unlike POST方法 这两个统一成一个接口
    //对图片的进行like操作
    @POST("pins/{pinId}/{operate}")
    Observable<LikeOperateBean> httpsLikeOperate(@Header("Authorization") String authorization, @Path("pinId") String pinsId, @Path("operate") String operate);

    //https://api.huaban.com/boards/967118/follow
    //https://api.huaban.com/boards/967118/unfollow POST方法 统一成一个接口
    //对画板进行关注操作
    @POST("boards/{boardId}/{operate}")
    Observable<AttentionOperateBean> httpsAttentionOperate(@Header("Authorization") String authorization, @Path("boardId") String boardId, @Path("operate") String operate);

    //获取我的画板集合信息
    //https://api.huaban.com/last_boards/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Header("Authorization") String authorization, @Query("extra") String extra);

    //对某个图片进行采集前网络访问 判断是否被采集过
    //https://api.huaban.com/pins/707907583/repin/?check=true
    @GET("pins/{viaId}/repin/")
    Observable<GatherInfoBean> httpsGatherInfo(@Header("Authorization") String authorization, @Path("viaId") String viaId, @Query("check") boolean check);

    //采集某个图片 用body形式传输数据
    //board_id=17891564&text=描述内容&via=707423726
    //https://api.huaban.com/pins/
    @FormUrlEncoded
    @POST("pins/")
    Observable<GatherResultBean> httpsGatherPins(@Header("Authorization") String authorization, @Field("board_id") String boardId, @Field("text") String describe, @Field("via") String PinsIda);

    //修改某个画板的信息
    //https://api.huaban.com/boards/29646779 category=photography&description=%E6%B7%BB%E5%8A%A0%E6%8F%8F%E8%BF%B0&title=%E6%B7%BB%E5%8A%A0
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpsEditBoard(@Header("Authorization") String authorization, @Path("boardId") String boardId, @Field("title") String title, @Field("description") String description, @Field("category") String category);

    //https://api.huaban.com/boards/29653031 POST BODY= _method=DELETE
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpsDeleteBoard(@Header("Authorization") String authorization, @Path("boardId") String boardId, @Field("_method") String operate);
}
