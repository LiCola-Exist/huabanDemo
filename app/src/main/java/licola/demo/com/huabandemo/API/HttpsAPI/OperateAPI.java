package licola.demo.com.huabandemo.API.HttpsAPI;

import licola.demo.com.huabandemo.Module.BoardDetail.FollowBoardOperateBean;
import licola.demo.com.huabandemo.Module.ImageDetail.GatherInfoBean;
import licola.demo.com.huabandemo.Module.ImageDetail.GatherResultBean;
import licola.demo.com.huabandemo.Module.ImageDetail.LikePinsOperateBean;
import licola.demo.com.huabandemo.Module.User.FollowUserOperateBean;
import licola.demo.com.huabandemo.Module.User.UserBoardSingleBean;
import licola.demo.com.huabandemo.Util.Constant;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  21:02
 */

public interface OperateAPI {
    //https://api.huaban.com/pins/687738004/like
    //https://api.huaban.com/pins/687738004/unlike POST方法 这两个统一成一个接口
    //对图片的进行like操作
    @POST("pins/{pinId}/{operate}")
    Observable<LikePinsOperateBean> httpsLikeOperate(@Header(
            Constant.Authorization) String authorization, @Path("pinId") String pinsId, @Path("operate") String operate);


    //https://api.huaban.com/boards/967118/follow
    //https://api.huaban.com/boards/967118/unfollow POST方法 统一成一个接口
    //对画板进行关注操作
    @POST("boards/{boardId}/{operate}")
    Observable<FollowBoardOperateBean> httpsFollowBoardOperate(
            @Header(Constant.Authorization) String authorization, @Path("boardId") String boardId, @Path("operate") String operate);

    //关注某个用户
    //https://api.huaban.com/users/17037199/follow  或者unfollow POST方法 统一成一个接口
    @POST("users/{userId}/{operate}")
    Observable<FollowUserOperateBean> httpsFollowUserOperate(@Header(Constant.Authorization) String authorization, @Path("userId") String userId, @Path("operate") String operate);


    //对某个图片进行采集前网络访问 判断是否被采集过
    //https://api.huaban.com/pins/707907583/repin/?check=true
    @GET("pins/{viaId}/repin/")
    Observable<GatherInfoBean> httpsGatherInfo(@Header(Constant.Authorization) String authorization, @Path("viaId") String viaId, @Query("check") boolean check);

    //采集某个图片 用body形式传输数据
    //https://api.huaban.com/pins/ body=board_id=17891564&text=描述内容&via=707423726
    @FormUrlEncoded
    @POST("pins/")
    Observable<GatherResultBean> httpsGatherPins(@Header(Constant.Authorization) String authorization, @Field("board_id") String boardId, @Field("text") String describe, @Field("via") String PinsIda);

    //新建画板
    //https://api.huaban.com/boards  body=category=类型&description=描述&title=标题
    @FormUrlEncoded
    @POST("boards/")
    Observable<UserBoardSingleBean> httpsAddBoard(
            @Header(Constant.Authorization) String authorization, @Field("title") String title, @Field("description") String description, @Field("category") String category
    );

    //修改某个画板的信息
    //https://api.huaban.com/boards/29646779 category=photography&description=%E6%B7%BB%E5%8A%A0%E6%8F%8F%E8%BF%B0&title=%E6%B7%BB%E5%8A%A0
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpsEditBoard(
            @Header(Constant.Authorization) String authorization, @Path("boardId") String boardId, @Field("title") String title, @Field("description") String description, @Field("category") String category);

    //删除某个画板
    //https://api.huaban.com/boards/29653031 POST BODY= _method=DELETE
    @FormUrlEncoded
    @POST("boards/{boardId}")
    Observable<UserBoardSingleBean> httpsDeleteBoard(@Header(Constant.Authorization) String authorization, @Path("boardId") String boardId, @Field("_method") String operate);



}
