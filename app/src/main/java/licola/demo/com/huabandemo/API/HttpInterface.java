package licola.demo.com.huabandemo.API;

/**
 * Created by LiCola on  2015/12/19  14:04
 */
public abstract class HttpInterface<T> {
    public abstract void onHttpSuccess(T result);//联网成功 数据返回成功

    public abstract void onHttpError(int code, String msg);//联网成功 数据返回失败

    public abstract void onHttpFailure(String error);//网络连接失败

    public void onHttpStart() {
    }

    public void onHttpFinish() {
    }
}
