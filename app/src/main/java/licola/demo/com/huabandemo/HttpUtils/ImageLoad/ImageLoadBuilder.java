package licola.demo.com.huabandemo.HttpUtils.ImageLoad;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;

import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by 李可乐 on 2017/2/17 0017.
 */

public class ImageLoadBuilder {
    //必要参数
    Context mContext;
    SimpleDraweeView mSimpleDraweeView;
    String mUrl;

    //非必要参数
    String mUrlLow;//低分率图地址

    Drawable mPlaceHolderImage;//占位图
    Drawable mProgressBarImage;//loading图
    Drawable mRetryImage;//重试图
    Drawable mFailureImage;//失败图
    Drawable mBackgroundImage;//背景图

    ScalingUtils.ScaleType mActualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
    boolean mIsCircle = false;//是否圆形图片
    boolean mIsRadius = false;//是否圆角
    boolean mIsBorder = false;//是否有包边
    float mRadius = 10;//圆角度数 默认10
    ResizeOptions mResizeOptions = new ResizeOptions(3000, 3000);//图片的大小限制

    ControllerListener mControllerListener;//图片加载的回调

    BaseBitmapDataSubscriber mBitmapDataSubscriber;

    /**
     * 构造器的构造方法 传入必要参数
     *
     * @param mContext
     * @param mSimpleDraweeView
     * @param mUrl
     */
    public ImageLoadBuilder(Context mContext, SimpleDraweeView mSimpleDraweeView, String mUrl) {
        this.mContext = mContext;
        this.mSimpleDraweeView = mSimpleDraweeView;
        this.mUrl = mUrl;
    }

    public static ImageLoadBuilder Start(Context mContext, SimpleDraweeView mSimpleDraweeView, String mUrl) {
        return new ImageLoadBuilder(mContext, mSimpleDraweeView, mUrl);
    }

    /**
     * 构造器的build方法 构造真正的对象 并返回
     * 构造之前需要检查
     *
     * @return
     */
    public ImageLoadFresco build() {
        Logger.d("图片开始加载 viewId=" + this.mSimpleDraweeView.getId() + " url" + this.mUrl);
//            if (TextUtils.isEmpty(mUrl)) {
//                throw new IllegalArgumentException("URL不能为空");
//            }

        //不能同时设定 圆形圆角
        if (mIsCircle && mIsRadius) {
            throw new IllegalArgumentException("图片不能同时设置圆角和圆形");
        }

        return new ImageLoadFresco(this);
    }

    public ImageLoadBuilder setBitmapDataSubscriber(BaseBitmapDataSubscriber mBitmapDataSubscriber) {
        this.mBitmapDataSubscriber = mBitmapDataSubscriber;
        return this;
    }

    public ImageLoadBuilder setUrlLow(String urlLow) {
        this.mUrlLow = urlLow;
        return this;
    }

    public ImageLoadBuilder setActualImageScaleType(ScalingUtils.ScaleType mActualImageScaleType) {
        this.mActualImageScaleType = mActualImageScaleType;
        return this;
    }

    public ImageLoadBuilder setPlaceHolderImage(Drawable mPlaceHolderImage) {
        this.mPlaceHolderImage = mPlaceHolderImage;
        return this;
    }

    public ImageLoadBuilder setProgressBarImage(Drawable mProgressBarImage) {
        this.mProgressBarImage = mProgressBarImage;
        return this;
    }

    public ImageLoadBuilder setRetryImage(Drawable mRetryImage) {
        this.mRetryImage = mRetryImage;
        return this;
    }

    public ImageLoadBuilder setFailureImage(Drawable mFailureImage) {
        this.mFailureImage = mFailureImage;
        return this;
    }

    public ImageLoadBuilder setBackgroundImage(Drawable mBackgroundImage) {
        this.mBackgroundImage = mBackgroundImage;
        return this;
    }

    public ImageLoadBuilder setBackgroupImageColor(int colorId) {
        Drawable color = ContextCompat.getDrawable(mContext, colorId);
        this.mBackgroundImage = color;
        return this;
    }

    public ImageLoadBuilder setIsCircle(boolean mIsCircle) {
        this.mIsCircle = mIsCircle;
        return this;
    }

    public ImageLoadBuilder setIsCircle(boolean mIsCircle, boolean mIsBorder) {
        this.mIsBorder = mIsBorder;
        this.mIsCircle = mIsCircle;
        return this;
    }

    public ImageLoadBuilder setIsRadius(boolean mIsRadius) {
        this.mIsRadius = mIsRadius;
        return this;
    }

    public ImageLoadBuilder setIsRadius(boolean mIsRadius, float mRadius) {
        this.mRadius = mRadius;
        return setIsRadius(mIsRadius);
    }

    public ImageLoadBuilder setRadius(float mRadius) {
        this.mRadius = mRadius;
        return this;
    }

    public ImageLoadBuilder setResizeOptions(ResizeOptions mResizeOptions) {
        this.mResizeOptions = mResizeOptions;
        return this;
    }

    public ImageLoadBuilder setControllerListener(ControllerListener mControllerListener) {
        this.mControllerListener = mControllerListener;
        return this;
    }
}
