package licola.demo.com.huabandemo.Observable;

import android.animation.Animator;
import android.view.animation.Animation;

import licola.demo.com.huabandemo.Util.Utils;
import rx.Observable;

/**
 * Created by LiCola on  2016/04/10  15:49
 */
public class MyRxObservable {
    public static Observable<Void> add(Animator animator,Object target){
        Utils.checkNotNull(animator,"Animation is null");
        Utils.checkNotNull(target,"target is null");
        animator.setTarget(target);
        return Observable.create(new AnimatorOnSubscribe(animator));
    }
}
