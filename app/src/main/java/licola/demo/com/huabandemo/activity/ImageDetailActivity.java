package licola.demo.com.huabandemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.bean.PinsEntity;
import licola.demo.com.huabandemo.fragment.ImageDetailFragment;
import licola.demo.com.huabandemo.fragment.ResultImageFragment;
import licola.demo.com.huabandemo.httpUtils.ImageLoadFresco;

public class ImageDetailActivity extends BaseActivity {

    @BindString(R.string.url_image)
    String url_image;
    @BindDrawable(R.drawable.ic_cancel_black_24dp)
    Drawable drawable_cancel;
    @BindDrawable(R.drawable.ic_refresh_black_24dp)
    Drawable drawable_refresh;


    @Bind(R.id.colltoolbar_layout)
    CollapsingToolbarLayout mCollapsingToolBar;
    @Bind(R.id.toolbar_image)
    Toolbar toolbar;
    @Bind(R.id.fab_main)
    FloatingActionButton fab;
    @Bind(R.id.img_image_big)
    SimpleDraweeView img_image_big;

    public String url_img;//图片地址

    public PinsEntity mPinsBean;//公开 填充的Fragment需要访问权限


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
//        ActivityCompat.startActivity();
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFloatingAction();
        mCollapsingToolBar.setExpandedTitleColor(Color.TRANSPARENT);//设置折叠后的文字颜色
        String key = String.valueOf(mPinsBean.getPin_id());
        getSupportFragmentManager().
                beginTransaction().replace(R.id.framelayout_info_recycler, ImageDetailFragment.newInstance(key)).commit();
    }

    private void initFloatingAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //加载大图
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_image_big, url_img)
//                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setRetryImage(drawable_refresh)
                .setFailureImage(drawable_cancel)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (animatable != null) {
                            animatable.start();
                        }
                    }
                })
                .build();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventReceiveBean(PinsEntity bean) {
        //接受EvenBus传过来的数据
        Logger.d(TAG + " receive bean");
        this.mPinsBean = bean;
        url_img = url_image + mPinsBean.getFile().getKey();

        img_image_big.setAspectRatio(Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight()));

    }


}
