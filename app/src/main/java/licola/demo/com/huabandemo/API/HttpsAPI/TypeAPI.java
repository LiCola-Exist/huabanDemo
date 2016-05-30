package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  20:57
 */

public interface TypeAPI {
    //https//api.huaban.com/favorite/food_drink?limit=20
    // 模板类型
    @GET("favorite/{type}")
    Observable<ListPinsBean> httpsTypeLimitRx(@Header(Constant.Authorization) String authorization, @Path("type") String type, @Query("limit") int limit);

    //https//api.huaban.com/favorite/food_drink?max=5445324325&limit=20
    //模板类型 的后续联网 max
    @GET("favorite/{type}")
    Observable<ListPinsBean> httpsTypeMaxLimitRx(@Header(Constant.Authorization) String authorization, @Path("type") String type, @Query("max") int max, @Query("limit") int limit);

}
