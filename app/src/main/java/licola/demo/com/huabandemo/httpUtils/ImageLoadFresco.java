package licola.demo.com.huabandemo.httpUtils;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by LiCola on  2016/01/16  15:26
 * 用Fresco加载图片的类
 * 针对这个Demo已经配置很多默认的值
 * 用构造器模式便于设置更多形式
 * <p>
 * 使用示例：
 * new ImageLoadFresco.LoadImageFrescoBuilder(mContext,img_image_user,url_head)
 * .setIsCircle(true)
 * .build();
 */
public class ImageLoadFresco {
    private static final String TAG = "ImageLoadFresco";

    //必要参数
    private SimpleDraweeView mSimpleDraweeView;
    private Context mContext;


    /**
     * 私有化的构造函数 得到builder的参数 构造对象
     *
     * @param frescoBuilder 构造器
     */
    private ImageLoadFresco(LoadImageFrescoBuilder frescoBuilder) {
        this.mContext = frescoBuilder.mContext;
        this.mSimpleDraweeView = frescoBuilder.mSimpleDraweeView;

        //初始化M层 用于配置各种显示资源
        GenericDraweeHierarchyBuilder builderM = new GenericDraweeHierarchyBuilder(mContext.getResources());

        //设置图片的缩放形式
        builderM.setActualImageScaleType(frescoBuilder.mActualImageScaleType);
        if (frescoBuilder.mActualImageScaleType == ScalingUtils.ScaleType.FOCUS_CROP) {
            builderM.setActualImageFocusPoint(new PointF(0f, 0f));
        }

        //初始化C层 用于控制图片的加载
        PipelineDraweeControllerBuilder builderC = Fresco.newDraweeControllerBuilder();

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(frescoBuilder.mUrl))
                .setResizeOptions(frescoBuilder.mResizeOptions)
                .build();
        builderC.setImageRequest(request);


        if (frescoBuilder.mPlaceHolderImage != null) {
            builderM.setPlaceholderImage(frescoBuilder.mPlaceHolderImage, ScalingUtils.ScaleType.CENTER);
        }

        if (frescoBuilder.mProgressBarImage != null) {
            Drawable progressBarDrawable = new AutoRotateDrawable(frescoBuilder.mProgressBarImage, 2000);
            builderM.setProgressBarImage(progressBarDrawable);
            //// TODO: 2016/3/18 0018 直接设置无效 是自定义Drawable setColor知识为了类里面的取值
//            MyProgressBarDrawable progressBarDrawable=new MyProgressBarDrawable();
//            builderM.setProgressBarImage(progressBarDrawable);
        }

        //设置重试图 同时就是设置支持加载视频时重试
        if (frescoBuilder.mRetryImage != null) {
            builderC.setTapToRetryEnabled(true);
            builderM.setRetryImage(frescoBuilder.mRetryImage);
        }

        if (frescoBuilder.mFailureImage != null) {
            builderM.setFailureImage(frescoBuilder.mFailureImage);
        }

        if (frescoBuilder.mBackgroundImage != null) {
            builderM.setBackground(frescoBuilder.mBackgroundImage);
        }

        if (frescoBuilder.mIsCircle) {
            builderM.setRoundingParams(RoundingParams.asCircle());
        }

        //如果圆角取默认值10 或者是已经修改过的mRadius值
        if (frescoBuilder.mIsRadius) {
            builderM.setRoundingParams(RoundingParams.fromCornersRadius(frescoBuilder.mRadius));
        }


        if (frescoBuilder.mControllerListener != null) {
            builderC.setControllerListener(frescoBuilder.mControllerListener);
        }

        mSimpleDraweeView.setHierarchy(builderM.build());
        mSimpleDraweeView.setController(builderC.build());
    }

    //构造器 作为类级内部类
    public static class LoadImageFrescoBuilder {
        //必要参数
        private Context mContext;
        private SimpleDraweeView mSimpleDraweeView;
        private String mUrl;

        //非必要参数
        private Drawable mPlaceHolderImage;//占位图
        private Drawable mProgressBarImage;//loading图
        private Drawable mRetryImage;//重试图
        private Drawable mFailureImage;//失败图
        private Drawable mBackgroundImage;//背景图

        private ScalingUtils.ScaleType mActualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        private boolean mIsCircle = false;//是否圆形图片
        private boolean mIsRadius = false;//是否圆角
        private float mRadius = 10;//圆角度数 默认10
        private ResizeOptions mResizeOptions = new ResizeOptions(3000, 3000);//图片的大小限制

        private ControllerListener mControllerListener;//图片加载的回调

        /**
         * 构造器的构造方法 传入必要参数
         *
         * @param mContext
         * @param mSimpleDraweeView
         * @param mUrl
         */
        public LoadImageFrescoBuilder(Context mContext, SimpleDraweeView mSimpleDraweeView, String mUrl) {
            this.mContext = mContext;
            this.mSimpleDraweeView = mSimpleDraweeView;
            this.mUrl = mUrl;
        }

        /**
         * 构造器的build方法 构造真正的对象 并返回
         * 构造之前需要检查
         *
         * @return
         */
        public ImageLoadFresco build() {

//            if (TextUtils.isEmpty(mUrl)) {
//                throw new IllegalArgumentException("URL不能为空");
//            }

            //不能同时设定 圆形圆角
            if (mIsCircle && mIsRadius) {
                throw new IllegalArgumentException("图片不能同时设置圆角和圆形");
            }

            return new ImageLoadFresco(this);
        }

        public LoadImageFrescoBuilder setActualImageScaleType(ScalingUtils.ScaleType mActualImageScaleType) {
            this.mActualImageScaleType = mActualImageScaleType;
            return this;
        }

        public LoadImageFrescoBuilder setPlaceHolderImage(Drawable mPlaceHolderImage) {
            this.mPlaceHolderImage = mPlaceHolderImage;
            return this;
        }

        public LoadImageFrescoBuilder setProgressBarImage(Drawable mProgressBarImage) {
            this.mProgressBarImage = mProgressBarImage;
            return this;
        }

        public LoadImageFrescoBuilder setRetryImage(Drawable mRetryImage) {
            this.mRetryImage = mRetryImage;
            return this;
        }

        public LoadImageFrescoBuilder setFailureImage(Drawable mFailureImage) {
            this.mFailureImage = mFailureImage;
            return this;
        }

        public LoadImageFrescoBuilder setBackgroundImage(Drawable mBackgroundImage) {
            this.mBackgroundImage = mBackgroundImage;
            return this;
        }

        public LoadImageFrescoBuilder setBackgroupImageColor(int colorId) {
            Drawable color = mContext.getResources().getDrawable(colorId);
            this.mBackgroundImage = color;
            return this;
        }

        public LoadImageFrescoBuilder setIsCircle(boolean mIsCircle) {
            this.mIsCircle = mIsCircle;
            return this;
        }

        public LoadImageFrescoBuilder setIsRadius(boolean mIsRadius) {
            this.mIsRadius = mIsRadius;
            return this;
        }

        public LoadImageFrescoBuilder setIsRadius(boolean mIsRadius, float mRadius) {
            this.mRadius = mRadius;
            this.mIsRadius = mIsRadius;
            return this;
        }

        public LoadImageFrescoBuilder setRadius(float mRadius) {
            this.mRadius = mRadius;
            return this;
        }

        public LoadImageFrescoBuilder setResizeOptions(ResizeOptions mResizeOptions) {
            this.mResizeOptions = mResizeOptions;
            return this;
        }

        public LoadImageFrescoBuilder setControllerListener(ControllerListener mControllerListener) {
            this.mControllerListener = mControllerListener;
            return this;
        }

    }
}
