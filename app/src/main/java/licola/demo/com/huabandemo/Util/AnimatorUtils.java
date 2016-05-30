package licola.demo.com.huabandemo.Util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by LiCola on  2016/05/28  15:31
 * 动画工具类 代码生成动画对象
 * 主要是为了 能够使用V4包的FastOutSlowIn 的兼容动画
 */

public class AnimatorUtils {

    //用不上 Fab有自己的隐藏显示方法
    public static AnimatorSet getScale(View target) {
        final AnimatorSet set = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1, 0, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1, 0, 1);

        set.setDuration(1000);
        set.play(animatorX).with(animatorY);
        set.setInterpolator(new FastOutSlowInInterpolator());

        return set;
    }

    public static ObjectAnimator getRotationAD(View target) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ROTATION, 0, 360);
        objectAnimator.setDuration(500);

        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return objectAnimator;
    }

    public static ObjectAnimator getRotationFS(View target) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ROTATION, 0, 360);
        objectAnimator.setDuration(600);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        return objectAnimator;
    }

    public static ValueAnimator getValue() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 1f);
        valueAnimator.setDuration(800);

        valueAnimator.setInterpolator(new FastOutLinearInInterpolator());
        return valueAnimator;
    }
}
