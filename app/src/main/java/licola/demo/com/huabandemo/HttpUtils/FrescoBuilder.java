//package licola.demo.com.huabandemo.httpUtils;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.drawable.Animatable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.util.Log;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
//import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
//import com.facebook.drawee.controller.BaseControllerListener;
//import com.facebook.drawee.controller.ControllerListener;
//import com.facebook.drawee.drawable.ScalingUtils;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
//import com.facebook.drawee.generic.RoundingParams;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.common.ResizeOptions;
//import com.facebook.imagepipeline.image.ImageInfo;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;
//
//import licola.demo.com.huabandemo.R;
//import licola.demo.com.huabandemo.Util.Logger;
//import licola.demo.com.huabandemo.Util.Utils;
//
///**
// * Created by LiCola on  2015/12/08  17:45
// */
//public class FrescoBuilder {
//
//    /**
//     * @param context
//     * @param mSimpleDraweeView
//     * @param url
//     * @param isCircle
//     * @deprecated Use ImageLoadFresco
//     */
//    @Deprecated
//    public static void setHeadDrawableMC2V(Context context, final SimpleDraweeView mSimpleDraweeView, String url, boolean isCircle) {
//        Resources resources = context.getResources();
//        Drawable placeHolderImage = resources.getDrawable(R.color.grey_100);
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
//
//        GenericDraweeHierarchy hierarchy = builder
//                .setFadeDuration(300)
//                .setBackground(placeHolderImage)
//                .setRoundingParams(getRoundCircleParams(isCircle))
//                .build();
//
//        mSimpleDraweeView.setHierarchy(hierarchy);
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri(url)
//                .setTapToRetryEnabled(false)
//                .setOldController(mSimpleDraweeView.getController())
//                .build();
//        mSimpleDraweeView.setController(controller);
//    }
//
//    /**
//     * 根据布尔值设置 图片为圆角或者圆圈
//     *
//     * @param isCircle true就是圆圈 否则就是固定值得圆角
//     * @return RoundingParams对象
//     */
//    private static RoundingParams getRoundCircleParams(boolean isCircle) {
//        RoundingParams roundingParams = null;
//        if (isCircle) {
//            roundingParams = RoundingParams.asCircle();
//        } else {
//            roundingParams = RoundingParams.fromCornersRadius(10);
//        }
//        return roundingParams;
//    }
//
//
//    /**
//     * Use ImageLoadFresco
//     *
//     * @param context
//     * @param mSimpleDraweeView
//     * @param url
//     * @param listener
//     */
//    @Deprecated
//    public static void setImageDrawableMC2V(Context context, final SimpleDraweeView mSimpleDraweeView, String url, final onAnimatableListener listener) {
//        /**
//         * 给V视图mSimpleDraweeView 配置
//         * M数据模型hierarchy C控制器DraweeController
//         */
//        Resources resources = context.getResources();
//        Drawable placeHolderImage = resources.getDrawable(R.drawable.normal_image_large);
//        Drawable retryImage = resources.getDrawable(R.drawable.ic_refresh_white_48dp);
//        Drawable failureImage = resources.getDrawable(R.drawable.ic_cancel_white_48dp);
//
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(context.getResources());
//        GenericDraweeHierarchy hierarchy = builder
//                .setFadeDuration(300)
//                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)//Fresco默认值
//                .setPlaceholderImage(placeHolderImage, ScalingUtils.ScaleType.CENTER)//占位图
//                .setRetryImage(retryImage)//加载失败提示图片
//                .setFailureImage(failureImage)//出错提示图片
//                .build();
//
//        mSimpleDraweeView.setHierarchy(hierarchy);
//
//
//        /**
//         * 如果你希望图片下载完之后自动播放，同时，当View从屏幕移除时，停止播放，只需要在image request 中简单设置
//         */
//        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                super.onFinalImageSet(id, imageInfo, animatable);
//                if (listener != null) {
//                    if (animatable != null) {
//                        listener.onComplete(true, animatable);
//                    } else {
//                        listener.onComplete(false, null);
//                    }
//                }
//            }
//
//            @Override
//            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
//                Logger.d("onIntermediateImageSet");
//            }
//        };
//
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setResizeOptions(new ResizeOptions(3000, 3000))
//                .build();
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setControllerListener(controllerListener)
//                .setTapToRetryEnabled(true)//用户可以点击重试图片重试图片加载
//                .build();
//        mSimpleDraweeView.setController(controller);
//
//    }
//
//    public interface onAnimatableListener {
//        void onComplete(boolean isPlay, Animatable animatable);
//    }
//}
