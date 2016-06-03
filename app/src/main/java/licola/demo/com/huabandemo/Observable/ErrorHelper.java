package licola.demo.com.huabandemo.Observable;

import android.support.annotation.NonNull;

import licola.demo.com.huabandemo.Entity.ErrorBaseBean;
import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by LiCola on  2016/05/31  17:04
 */

public class ErrorHelper {

    private ErrorHelper() {

    }

    @NonNull
    public static <T extends ErrorBaseBean> Observable<T> getCheckNetError(final T bean) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (bean != null){
                    String msg = bean.getMsg();
                    if (msg != null) {
                        Logger.d("onError=" + msg);

                        subscriber.onError(new RuntimeException(bean.getMsg()));
                    } else {
                        Logger.d("onNext");
                        subscriber.onNext(bean);
                    }
                }else {
                    subscriber.onError(new RuntimeException());
                }

            }
        });
    }

}
