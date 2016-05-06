package licola.demo.com.huabandemo.ImageDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import licola.demo.com.huabandemo.API.OnGatherDialogInteractionListener;
import licola.demo.com.huabandemo.API.OnImageDetailFragmentInteractionListener;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.BoardDetail.BoardDetailActivity;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.Main.MainActivity;
import licola.demo.com.huabandemo.Module.ModuleActivity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.UserInfo.UserInfoActivity;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.Utils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageDetailActivity extends BaseActivity
        implements OnImageDetailFragmentInteractionListener, OnGatherDialogInteractionListener {

    //定义调用ImageDetailActivity的类 来自什么类型 在结束作为判断条件

    private static final String KEYPARCELABLE = "Parcelable";

    private int mActionFrom;
    public static final String ACTION_KEY = "key";//key值
    public static final int ACTION_DEFAULT = -1;//默认值
    public static final int ACTION_THIS = 0;//来自自己的跳转
    public static final int ACTION_MAIN = 1;//来自主界面的跳转
    public static final int ACTION_MODULE = 2;//来自模块界面的跳转
    public static final int ACTION_BOARD = 3;//来自画板界面的跳转
    public static final int ACTION_ATTENTION = 4;//来自我的关注界面的跳转
    public static final int ACTION_SEARCH = 5;//来自搜索界面的跳转

    @BindDrawable(R.drawable.ic_cancel_black_24dp)
    Drawable mDrawableCancel;
    @BindDrawable(R.drawable.ic_refresh_black_24dp)
    Drawable mDrawableRefresh;


    //小图的后缀
    @BindString(R.string.url_image_big)
    String mFormatImageUrlBig;
    //大图的后缀
    @BindString(R.string.url_image_general)
    String mFormatImageGeneral;

    @Bind(R.id.appbar_image_detail)
    AppBarLayout mAppBar;
    @Bind(R.id.colltoolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar_image)
    Toolbar toolbar;
    @Bind(R.id.fab_image_detail)
    FloatingActionButton mFabActionBtn;
    @Bind(R.id.img_image_big)
    SimpleDraweeView img_image_big;

    public PinsMainEntity mPinsBean;

    public String mImageUrl;//图片地址
    public String mPinsId;

    //联网的授权字段 提供子Fragment使用
    public String mAuthorization = Base64.mClientInto;

    private boolean isLike = false;//该图片是否被喜欢操作 默认false 没有被操作过

    private String[] mBoardIdArray;

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

    public static void launch(Activity activity, int action) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        intent.putExtra(ACTION_KEY, action);
        activity.startActivity(intent);
    }

    @Override
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);//注册
        mActionFrom = getIntent().getIntExtra(ACTION_KEY, ACTION_DEFAULT);
        mAuthorization = getAuthorization();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initListener();
        mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);//设置折叠后的文字颜色

        if (savedInstanceState != null) {
            if ((savedInstanceState.getParcelable(KEYPARCELABLE) != null) && (mPinsBean == null)) {
                Logger.d();
                mPinsBean = savedInstanceState.getParcelable(KEYPARCELABLE);
            }
        }
        mImageUrl = mPinsBean.getFile().getKey();
        mPinsId = String.valueOf(mPinsBean.getPin_id());
        isLike = mPinsBean.isLiked();

        //设置图片空间的宽高比
        int width = mPinsBean.getFile().getWidth();
        int height = mPinsBean.getFile().getHeight();
        if (width > height) {
            img_image_big.setAspectRatio(Utils.getAspectRatio(width, height));
        } else {
            img_image_big.setAspectRatio(1);
        }


        getSupportFragmentManager().
                beginTransaction().replace(R.id.framelayout_info_recycler, ImageDetailFragment.newInstance(mPinsId)).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d(TAG);
        outState.putParcelable(KEYPARCELABLE, mPinsBean);
    }

    private void initListener() {
//        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
////                Logger.d("verticalOffset=" + verticalOffset + "appBarLayout.getTotalScrollRange()" + appBarLayout.getTotalScrollRange());
////                if (menuItem!=null) {
////                    if (verticalOffset == -appBarLayout.getTotalScrollRange()) {
////                        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
////                    } else {
////                        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
////                    }
////                }
//            }
//        });

        mFabActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                final Drawable src = mFabActionBtn.getDrawable();
//                if (src instanceof Animatable) {
//                    ((Animatable) src).start();
//                }
                showGatherDialog();
            }
        });


    }

    private void showGatherDialog() {

        String boardTitleArray = (String) SPUtils.get(mContext, Constant.BOARDTILTARRAY, "");
        String mBoardId = (String) SPUtils.get(mContext, Constant.BOARDIDARRAY, "");
        Logger.d("title is " + boardTitleArray);

        String[] array = boardTitleArray != null ? boardTitleArray.split(Constant.SEPARATECOMMA) : new String[0];
        mBoardIdArray = mBoardId != null ? mBoardId.split(Constant.SEPARATECOMMA) : new String[0];
        GatherDialogFragment fragment = GatherDialogFragment.create(mAuthorization, mPinsId, mPinsBean.getRaw_text(), array);
        fragment.show(getSupportFragmentManager(), null);
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
                .setRetryImage(mDrawableRefresh)
                .setFailureImage(mDrawableCancel)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        Logger.d();

                        if (animatable != null) {
                            animatable.start();
                        }
                    }
                })
                .build();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //创建menu视图
        super.onCreateOptionsMenu(menu);
        Logger.d();
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //onCreateOptionsMenu的后续
        Logger.d();
        //menu文件中默认 选择没有选中的drawable
        setIconDynamic(menu.findItem(R.id.action_like), isLike);


        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d("id=" + item.getItemId());
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                actionHome(mActionFrom);//根据action值 选择向上键的 操作结果
                break;
            case R.id.action_like:
                actionLike(item);

                break;
            case R.id.action_download:
                actionDownload(item);
                break;
        }

        // boolean Return false to allow normal menu processing to
        // proceed, true to consume it here.
        // false：允许继续事件传递  true：就自己消耗事件 不再传递
        return true;
    }

    private void actionLike(MenuItem item) {
        Logger.d();
        //根据当前值 取操作符
        String operate = isLike ? Constant.OPERATEUNLIKE : Constant.OPERATELIKE;
        RetrofitService.createAvatarService()
                .httpsLikeOperate(mAuthorization, mPinsId, operate)
                .subscribeOn(Schedulers.io())
                .delay(600, TimeUnit.MILLISECONDS)//延迟 使得能够完成动画
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LikeOperateBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Logger.d();
                        item.setEnabled(false);//不可点击
                        AnimatedVectorDrawableCompat drawable = (AnimatedVectorDrawableCompat) item.getIcon();
                        if (drawable != null) {
                            drawable.start();
                        }


                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                        item.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                        item.setEnabled(true);
                    }

                    @Override
                    public void onNext(LikeOperateBean likeOperateBean) {
                        Logger.d();
                        //网络操作成功 标志位取反 然后重设图标
                        isLike = !isLike;
                        setIconDynamic(item, isLike);
                    }
                });

    }

    /**
     * 设置动态的icon图标 反向设置
     * 如果为true 显示undo图片
     * 为false 显示do图标
     * 所以传入当前状态值就可以 内部已经做判断
     *
     * @param item
     * @param isLike
     */
    private void setIconDynamic(MenuItem item, boolean isLike) {
        AnimatedVectorDrawableCompat drawableCompat;
        drawableCompat = AnimatedVectorDrawableCompat.create(mContext,
                isLike ? R.drawable.drawable_animation_favorite_undo : R.drawable.drawable_animation_favorite_do);
        item.setIcon(drawableCompat);
    }

    private void actionDownload(MenuItem item) {
//        getSupportActionBar().invalidateOptionsMenu();
//        RetrofitService.createAvatarService()
//                .httpDownImage(mImageUrl)
//                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
//                .observeOn(AndroidSchedulers.mainThread()) //订阅者的运行线程 在main线程中才能修改UI
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.d();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        Logger.d(responseBody.contentLength()+" ");
//                    }
//                });

    }

    private void actionHome(int mActionFrom) {
        switch (mActionFrom) {
            case ACTION_MAIN:
                //在maxifest已经定义 默认处理
                MainActivity.launch(this);
                break;
            case ACTION_MODULE:
                ModuleActivity.launch(this, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case ACTION_BOARD:
//                BoardDetailActivity.launch(this, );
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEventReceiveBean(PinsMainEntity bean) {
        //接受EvenBus传过来的数据
        Logger.d(TAG + " receive bean");
        this.mPinsBean = bean;

    }


    @Override
    public void onClickPinsItemImage(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this, mActionFrom);
    }

    @Override
    public void onClickPinsItemText(PinsMainEntity bean, View view) {
        ImageDetailActivity.launch(this, mActionFrom);
    }

    @Override
    public void onClickBoardField(String key, String title) {
        BoardDetailActivity.launch(this, key, title);
    }

    @Override
    public void onClickUserField(String key, String title) {
        // TODO: 2016/4/2 0002 图片详情的用户跳转
        UserInfoActivity.launch(this, key, title);
    }

    @Override
    public void onDialogPositiveClick(String describe, int selectPosition) {
        Logger.d("describe=" + describe + " selectPosition=" + selectPosition);

        RetrofitService.createAvatarService()
                .httpsGatherPins(mAuthorization, mBoardIdArray[selectPosition], describe, mPinsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatherResultBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(GatherResultBean gatherResultBean) {
                        Logger.d();
                    }
                });
    }
}
