//package licola.demo.com.huabandemo.HttpUtils;
//
//import licola.demo.com.huabandemo.API.HttpInterface;
//import licola.demo.com.huabandemo.Util.Logger;
//import retrofit.Call;
//import retrofit.Callback;
//import retrofit.Response;
//import retrofit.Retrofit;
//
///**
// * Created by LiCola on  2015/12/19  14:27
// */
//public class HttpRequest {
//
//    public static <T> void Requeset(Call call, final HttpInterface<T> hif) {
//
//        hif.onHttpStart();
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Response response, Retrofit retrofit) {
//                if (response.raw().code() == 200 && response.body() != null) {
//                    hif.onHttpSuccess((T) response.body());
//                    Logger.d("Net success onResponse "+ response.raw().toString());
//                } else {
//                    hif.onHttpError(response.code(), response.message());
//                    Logger.d("Net error onResponse raw=" + response.raw().toString());
//                }
//                hif.onHttpFinish();
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                hif.onHttpFailure(t.toString());
//                Logger.d("Net error onFailure" + t.toString());
//                hif.onHttpFinish();
//            }
//        });
//    }
//
//}
