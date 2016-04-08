package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by LiCola on  2016/04/08  14:19
 */
public final class  CompatUtil {
    public static Drawable getTintCompatDrawable(Context mContext, int mResDrawableId, int ResTintId){
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, mResDrawableId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, ResTintId));
        return drawable;
    }
}
