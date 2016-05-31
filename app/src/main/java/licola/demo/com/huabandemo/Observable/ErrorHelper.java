package licola.demo.com.huabandemo.Observable;

import android.support.annotation.NonNull;

import licola.demo.com.huabandemo.Module.User.UserBoardSingleBean;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by LiCola on  2016/05/31  17:04
 */

public class ErrorHelper {

    private ErrorHelper() {

    }

    @NonNull
    public static  Observable<UserBoardSingleBean> getCheckNetError(final UserBoardSingleBean bean) {
        return Observable.create(new Observable.OnSubscribe<UserBoardSingleBean>() {
            @Override
            public void call(Subscriber<? super UserBoardSingleBean> subscriber) {

                String msg = bean.getMsg();
                if (msg != null) {
                    Logger.d("onError=" + msg);

                    subscriber.onError(new RuntimeException(bean.getMsg()));
                } else {
                    Logger.d("onNext");
                    subscriber.onNext(bean);
                }
            }
        });
    }

}
