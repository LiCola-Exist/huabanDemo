package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Module.Follow.FollowBoardListBean;
import licola.demo.com.huabandemo.Module.Follow.FollowPinsBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  21:05
 */

public interface FollowingAPI {

    //https://api.huaban.com/following?limit=40
    //我的关注图片  需要 报头 bearer getAccess_token
    @GET("following")
    Observable<FollowPinsBean> httpsMyFollowingPinsRx(@Header(Constant.Authorization) String authorization, @Query("limit") int limit);

    //https://api.huaban.com/following?limit=40&max=670619456
    //我的关注图片的 后续滑动联网
    @GET("following")
    Observable<FollowPinsBean> httpsMyFollowingPinsMaxRx(@Header(Constant.Authorization) String authorization, @Query("max") int max, @Query("limit") int limit);

    //https://api.huaban.com/boards/following?page=1&per_page=20
    //我的关注画板
    @GET("boards/following")
    Observable<FollowBoardListBean> httpsMyFollowingBoardRx(@Header(Constant.Authorization) String authorization, @Query("page") int page, @Query("per_page") int per_page);

}
