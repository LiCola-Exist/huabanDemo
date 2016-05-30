package licola.demo.com.huabandemo.API.HttpsAPI;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LiCola on  2016/05/23  21:01
 */

public interface DownUpAPI {
    @GET("http://img.hb.aicdn.com/{pinId}")
    Observable<ResponseBody> httpDownImage(@Path("pinId") String pinId);
}
