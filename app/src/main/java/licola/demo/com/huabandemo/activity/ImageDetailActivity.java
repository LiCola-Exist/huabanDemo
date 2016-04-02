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
import android.view.MenuItem;
import android.view.View;

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
import licola.demo.com.huabandemo.httpUtils.ImageLoadFresco;

public class ImageDetailActivity extends BaseActivity
        implements ImageDetailFragment.OnImageDetailFragmentInteractionListener {

    @BindDrawable(R.drawable.ic_cancel_black_24dp)
    Drawable drawable_cancel;
    @BindDrawable(R.drawable.ic_refresh_black_24dp)
    Drawable drawable_refresh;

    //小图的后缀
    @BindString(R.string.url_image_big)
    String mFormatImageUrlBig;
    //大图的后缀
    @BindString(R.string.url_image_general)
    String mFormatImageGeneral;

    @Bind(R.id.colltoolbar_layout)
    CollapsingToolbarLayout mCollapsingToolBar;
    @Bind(R.id.toolbar_image)
    Toolbar toolbar;
    @Bind(R.id.fab_main)
    FloatingActionButton fab;
    @Bind(R.id.img_image_big)
    SimpleDraweeView img_image_big;

    public PinsEntity mPinsBean;

    public String mImageUrl;//图片地址
    public String mPinsId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册
        Intent intent = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolBar.setExpandedTitleColor(Color.TRANSPARENT);//设置折叠后的文字颜色
        initFloatingAction();
        //设置图片空间的宽高比
        img_image_big.setAspectRatio(
                Utils.getAspectRatio(mPinsBean.getFile().getWidth(), mPinsBean.getFile().getHeight()));
        getSupportFragmentManager().
                beginTransaction().replace(R.id.framelayout_info_recycler, ImageDetailFragment.newInstance(mPinsId)).commit();
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

        String url = String.format(mFormatImageUrlBig, mImageUrl);
        String url_low = String.format(mFormatImageGeneral, mImageUrl);
        //加载大图
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_image_big, url)
//                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setUrlLow(url_low)
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
    public boolean onOptionsItemSelected(MenuItem item) {

        Logger.d("id=" + item.getItemId());
        Logger.d("android.R.id.home=" + android.R.id.home);
        return super.onOptionsItemSelected(item);
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
        mImageUrl = mPinsBean.getFile().getKey();
        mPinsId = String.valueOf(mPinsBean.getPin_id());

    }


    @Override
    public void onClickItemImage(PinsEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onClickItemText(PinsEntity bean, View view) {
        ImageDetailActivity.launch(this);
    }

    @Override
    public void onClickBoardField(String key, String title) {
        BoardDetailActivity.launch(this, key, title);
    }

    @Override
    public void onClickUserField(String key, String title) {
        // TODO: 2016/4/2 0002 图片详情的用户跳转
    }


}
