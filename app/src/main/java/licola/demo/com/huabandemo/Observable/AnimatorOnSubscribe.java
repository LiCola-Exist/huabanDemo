package licola.demo.com.huabandemo.Observable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import licola.demo.com.huabandemo.Util.Logger;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by LiCola on  2016/04/10  15:39
 */
public class AnimatorOnSubscribe implements Observable.OnSubscribe<Void> {
    final Animator animator;

    //构造器传入Animator
    public AnimatorOnSubscribe(Animator animator) {
        this.animator = animator;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        checkUiThread();
        AnimatorListenerAdapter adapter=new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                subscriber.onNext(null);
                Logger.d("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                subscriber.onCompleted();
                Logger.d("onAnimationEnd");
            }
        };

        animator.addListener(adapter);
        animator.start();//先绑定监听器再开始
//        subscriber.add(new MainThreadSubscription() {
//            @Override protected void onUnsubscribe() {
//               animator.removeAllListeners();
//            }
//        });
    }
}
