package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.Module.User.UserBoardListBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  21:06
 */

public interface UserAPI {
    //https://api.huaban.com/users/15246080
    //获取个人信息
    @GET("users/{userId}")
    Observable<UserMeAndOtherBean> httpsUserInfoRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId);

    //获取我的画板集合信息 不需要显示需要保存
    //https://api.huaban.com/last_boards/?extra=recommend_tags
    @GET("last_boards/")
    Observable<BoardListInfoBean> httpsBoardListInfo(@Header(Constant.Authorization) String authorization, @Query("extra") String extra);

    //https://api.huaban.com/users/15246080/boards?limit=20
    //用户画板信息
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpsUserBoardRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/16211815/boards?limit=20&max=18375118
    @GET("users/{userId}/boards")
    Observable<UserBoardListBean> httpsUserBoardMaxRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/pins?limit=40
    //用户的采集
    @GET("users/{userId}/pins")
    Observable<ListPinsBean> httpsUserPinsRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/pins?limit=40&max=19532400
    //后续滑动联网
    @GET("users/{userId}/pins")
    Observable<ListPinsBean> httpsUserPinsMaxRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/users/188174/likes?limit=40
    //用户的喜欢
    @GET("users/{userId}/likes")
    Observable<ListPinsBean> httpsUserLikePinsRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("limit") int limit);

    //https://api.huaban.com/users/743988/likes?limit=40&max=4338219
    //用户喜欢的后续联网
    @GET("users/{userId}/likes")
    Observable<ListPinsBean> httpsUserLikePinsMaxRx(@Header(Constant.Authorization) String authorization, @Path("userId") String pinsId, @Query("max") int max, @Query("limit") int limit);

}
