package licola.demo.com.huabandemo.API.HttpsAPI;

import java.util.List;

import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.Module.ImageDetail.PinsDetailBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  20:58
 */

public interface ImageDetailAPI {
    //https://api.huaban.com/pins/663478425
    //根据图片id获取详情
    @GET("pins/{pinsId}")
    Observable<PinsDetailBean> httpsPinsDetailRx(@Header(Constant.Authorization) String authorization, @Path("pinsId") String pinsId);

    //https//api.huaban.com/pins/654197326/recommend/?page=1&limit=40
    //获取某个图片的推荐图片列表
    @GET("pins/{pinsId}/recommend/")
    Observable<List<PinsMainEntity>> httpPinsRecommendRx(@Header(Constant.Authorization) String authorization, @Path("pinsId") String pinsId, @Query("page") int page, @Query("limit") int limit);

}
