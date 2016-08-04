package licola.demo.com.huabandemo.HttpUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/01/16  15:26
 * 用Fresco加载图片的类
 * 针对这个Demo已经配置很多默认的值
 * 用构造器模式便于设置更多形式
 * <p/>
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

        //初始化M层 用于初始化图片中包含的数据
        GenericDraweeHierarchyBuilder builderM=new GenericDraweeHierarchyBuilder(mContext.getResources());

        //请求参数 主要配置url 和C层相关
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(frescoBuilder.mUrl))
                .setResizeOptions(frescoBuilder.mResizeOptions)
                .build();
        //初始化C层 用于控制图片的加载 是主要的实现控制类
        PipelineDraweeControllerBuilder builderC = Fresco.newDraweeControllerBuilder()
//                .setOldController(mSimpleDraweeView.getController())
                ;

        if (frescoBuilder.mUrlLow != null) {
            builderC.setLowResImageRequest(ImageRequest.fromUri(frescoBuilder.mUrlLow));
        }

        builderC.setImageRequest(request);

        setViewPerformance(frescoBuilder, builderM, builderC);

        if (frescoBuilder.mControllerListener != null) {
            builderC.setControllerListener(frescoBuilder.mControllerListener);
        }

        DraweeController draweeController=builderC.build();

        if (frescoBuilder.mBitmapDataSubscriber!=null){
            ImagePipeline imagePipeline = Fresco.getImagePipeline();

            DataSource<CloseableReference<CloseableImage>> dataSource =
                    imagePipeline.fetchDecodedImage(request, mSimpleDraweeView.getContext());
            dataSource.subscribe(frescoBuilder.mBitmapDataSubscriber,CallerThreadExecutor.getInstance());
        }

        mSimpleDraweeView.setHierarchy(builderM.build());
        mSimpleDraweeView.setController(draweeController);
    }

    /**
     * 配置DraweeView的各种表现效果
     * 如 失败图 重试图 圆角或圆形
     * @param frescoBuilder
     * @param builderM
     * @param builderC
     */
    private void setViewPerformance(LoadImageFrescoBuilder frescoBuilder, GenericDraweeHierarchyBuilder builderM, PipelineDraweeControllerBuilder builderC) {

        //设置图片的缩放形式
        builderM.setActualImageScaleType(frescoBuilder.mActualImageScaleType);
        if (frescoBuilder.mActualImageScaleType == ScalingUtils.ScaleType.FOCUS_CROP) {
            builderM.setActualImageFocusPoint(new PointF(0f, 0f));
        }
        ;
        if (frescoBuilder.mPlaceHolderImage != null) {
//            builderM.setPlaceholderImage(ContextCompat.getDrawable(mContext, R.drawable.ic_account_circle_gray_48dp), ScalingUtils.ScaleType.CENTER);
            builderM.setPlaceholderImage(frescoBuilder.mPlaceHolderImage, ScalingUtils.ScaleType.CENTER);
        }

        if (frescoBuilder.mProgressBarImage != null) {
            Drawable progressBarDrawable = new AutoRotateDrawable(frescoBuilder.mProgressBarImage, 2000);
            builderM.setProgressBarImage(progressBarDrawable);
            //// TODO: 2016/3/18 0018 直接设置无效 是自定义Drawable setColor知识为了类里面的取值
//            MyProgressBarDrawable progressBarDrawable=new MyProgressBarDrawable();
//            builderM.setProgressBarImage(progressBarDrawable);
        }

        //设置重试图 同时需要C层支持点击控制
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

            if (frescoBuilder.mIsBorder) {
                //默认白色包边
                builderM.setRoundingParams(RoundingParams.asCircle().setBorder(0xFFFFFFFF, 2));
            }else {
                builderM.setRoundingParams(RoundingParams.asCircle());
            }
//            builderM.setRoundingParams(RoundingParams.asCircle());
        }

        //如果圆角取默认值10 或者是已经修改过的mRadius值
        if (frescoBuilder.mIsRadius) {
            builderM.setRoundingParams(RoundingParams.fromCornersRadius(frescoBuilder.mRadius));
        }

    }

    //构造器 作为类级内部类
    public static class LoadImageFrescoBuilder {
        //必要参数
        private Context mContext;
        private SimpleDraweeView mSimpleDraweeView;
        private String mUrl;

        //非必要参数
        private String mUrlLow;//低分率图地址

        private Drawable mPlaceHolderImage;//占位图
        private Drawable mProgressBarImage;//loading图
        private Drawable mRetryImage;//重试图
        private Drawable mFailureImage;//失败图
        private Drawable mBackgroundImage;//背景图

        private ScalingUtils.ScaleType mActualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        private boolean mIsCircle = false;//是否圆形图片
        private boolean mIsRadius = false;//是否圆角
        private boolean mIsBorder = false;//是否有包边
        private float mRadius = 10;//圆角度数 默认10
        private ResizeOptions mResizeOptions = new ResizeOptions(3000, 3000);//图片的大小限制

        private ControllerListener mControllerListener;//图片加载的回调

        private BaseBitmapDataSubscriber mBitmapDataSubscriber;
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
            Logger.d("图片开始加载 viewId="+this.mSimpleDraweeView.getId()+" url"+this.mUrl);
//            if (TextUtils.isEmpty(mUrl)) {
//                throw new IllegalArgumentException("URL不能为空");
//            }

            //不能同时设定 圆形圆角
            if (mIsCircle && mIsRadius) {
                throw new IllegalArgumentException("图片不能同时设置圆角和圆形");
            }

            return new ImageLoadFresco(this);
        }

        public LoadImageFrescoBuilder setBitmapDataSubscriber(BaseBitmapDataSubscriber mBitmapDataSubscriber){
            this.mBitmapDataSubscriber = mBitmapDataSubscriber;
            return this;
        }

        public LoadImageFrescoBuilder setUrlLow(String urlLow) {
            this.mUrlLow = urlLow;
            return this;
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
            Drawable color = ContextCompat.getDrawable(mContext, colorId);
            this.mBackgroundImage = color;
            return this;
        }

        public LoadImageFrescoBuilder setIsCircle(boolean mIsCircle) {
            this.mIsCircle = mIsCircle;
            return this;
        }

        public LoadImageFrescoBuilder setIsCircle(boolean mIsCircle, boolean mIsBorder) {
            this.mIsBorder = mIsBorder;
            this.mIsCircle = mIsCircle;
            return this;
        }

        public LoadImageFrescoBuilder setIsRadius(boolean mIsRadius) {
            this.mIsRadius = mIsRadius;
            return this;
        }

        public LoadImageFrescoBuilder setIsRadius(boolean mIsRadius, float mRadius) {
            this.mRadius = mRadius;
            return setIsRadius(mIsRadius);
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
