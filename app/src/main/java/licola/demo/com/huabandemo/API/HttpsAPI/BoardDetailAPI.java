package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Entity.ListPinsBean;
import licola.demo.com.huabandemo.Module.BoardDetail.BoardDetailBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  20:59
 */

public interface BoardDetailAPI {
    //https//api.huaban.com/boards/3514299
    //获取画板的详情
    @GET("boards/{boardId}")
    Observable<BoardDetailBean> httpsBoardDetailRx(@Header(Constant.Authorization) String authorization, @Path("boardId") String boardId);

    //https//api.huaban.com/boards/19196160/pins?limit=40
    //获取画板的图片集合
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpsBoardPinsRx(@Header(Constant.Authorization) String authorization, @Path("boardId") String boardId, @Query("limit") int limit);

    //https//api.huaban.com/boards/19196160/pins?limit=40&max=508414882
    //获取画板的图片集合 根据上一个图片的id继续加载
    @GET("boards/{boardId}/pins")
    Observable<ListPinsBean> httpsBoardPinsMaxRx(@Header(Constant.Authorization) String authorization, @Path("boardId") String boardId, @Query("max") int max, @Query("limit") int limit);

}
