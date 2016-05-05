package licola.demo.com.huabandemo.Observable;

import android.content.Context;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.functions.Func1;

/**
 * RetryWithConnectivityIncremental
 * <p>
 * Created by hanks on 15-11-29.
 */
public class RetryWithConnectivityIncremental implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxTimeout;
    private final TimeUnit timeUnit;
    private final Observable<Boolean> isConnected;
    private final int startTimeOut;
    private int timeout;

    public RetryWithConnectivityIncremental(Context context, int startTimeOut, int maxTimeout, TimeUnit timeUnit) {
        this.startTimeOut = startTimeOut;
        this.maxTimeout = maxTimeout;
        this.timeUnit = timeUnit;
        this.timeout = startTimeOut;
        isConnected = getConnectedObservable(context);
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap((Throwable throwable) -> {
            Logger.d(throwable.toString());
            if (throwable instanceof retrofit2.adapter.rxjava.HttpException ) {
                return isConnected;
            } else {
                return Observable.error(throwable);
            }
        }).compose(attachIncementalTimeout());
    }

    private Observable.Transformer<Boolean, Boolean> attachIncementalTimeout() {
        return observable -> observable.timeout(timeout, timeUnit)
                .doOnError(throwable -> {
                    Logger.d("attachIncementalTimeout doOnError"+ throwable.toString());
                    if (throwable instanceof TimeoutException) {
                        timeout = timeout > maxTimeout ? maxTimeout : timeout + startTimeOut;
                    }
                });
    }

    private Observable<Boolean> getConnectedObservable(Context context) {
        return BroadcastObservable.fromConnectivityManager(context)
                .distinctUntilChanged()
                .filter(isConnected -> isConnected);
    }
}
