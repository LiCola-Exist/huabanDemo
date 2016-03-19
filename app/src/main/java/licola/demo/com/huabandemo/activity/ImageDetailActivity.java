package licola.demo.com.huabandemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.TimeUtils;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.bean.CardBigBean;
import licola.demo.com.huabandemo.httpUtils.ImageLoadFresco;

public class ImageDetailActivity extends BaseActivity {

    @BindString(R.string.url_image)
    String url_image;
    @BindDrawable(R.drawable.ic_cancel_white_48dp)
    Drawable drawable_cancel;
    @BindDrawable(R.drawable.ic_refresh_white_48dp)
    Drawable drawable_refresh;
    @BindDrawable(R.color.pink_500)
    Drawable drawable_pink;

    @Bind(R.id.toolbar_image)
    Toolbar toolbar;
    @Bind(R.id.fab_main)
    FloatingActionButton fab;

    @Bind(R.id.img_image_big)
    SimpleDraweeView img_image_big;
    @Bind(R.id.tv_image_text)
    TextView tv_image_text;
    @Bind(R.id.tv_image_link)
    TextView tv_image_link;
    @Bind(R.id.btn_image_gather)
    Button btn_image_gather;
    @Bind(R.id.btn_image_favorite)
    Button btn_image_favorite;

    @Bind(R.id.img_image_user)
    SimpleDraweeView img_image_user;
    @Bind(R.id.tv_image_user)
    TextView tv_image_user;
    @Bind(R.id.tv_image_time)
    TextView tv_image_time;
    @Bind(R.id.ibtn_image_user_chevron_right)
    ImageButton ibtn_image_user_chevron_right;

    @Bind(R.id.img_image_board)
    SimpleDraweeView img_image_board;
    @Bind(R.id.tv_image_board)
    TextView tv_image_board;
    @Bind(R.id.ibtn_image_board_chevron_right)
    ImageButton ibtn_image_board_chevron_right;



    private String url_img;//图片地址
    private String url_head;//用户头像图片地址
    private String url_board;//画板图片地址


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
        EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initTintDrawable(btn_image_gather, R.drawable.ic_favorite_border_white_24dp);
        initTintDrawable(btn_image_favorite, R.drawable.ic_favorite_border_white_24dp);
        initTintDrawable(ibtn_image_user_chevron_right, R.drawable.ic_chevron_right_white_24dp);
        initTintDrawable(ibtn_image_board_chevron_right, R.drawable.ic_chevron_right_white_24dp);
        initTintDrawable(tv_image_link, R.drawable.ic_link_white_24dp);

//        int[] attrs = new int[]{R.attr.selectableItemBackground};
//        TypedArray typedArray = this.obtainStyledAttributes(attrs);
//        int backgroundResource = typedArray.getResourceId(0, 0);
//        btn_image_gather.setBackgroundResource(backgroundResource);
    }

    private void initTintDrawable(View view, int resId) {
        Drawable drawable = null;
        if (view instanceof Button) {
            drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, resId).mutate());
            DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, R.color.tint_list_pink));
            ((Button) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            return;
        }

        if (view instanceof ImageButton) {
            drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, resId).mutate());
            DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, R.color.tint_list_grey));
            ((ImageButton) view).setImageDrawable(drawable);
            return;
        }
        if (view instanceof TextView) {
            drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, resId).mutate());
            DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, R.color.tint_list_grey));
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_image_big, url_img)
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setRetryImage(drawable_refresh)
                .setFailureImage(drawable_cancel)
                .build();

        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_image_user, url_head)
                .setIsCircle(true)
                .build();
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, img_image_board, url_head)
                .setIsRadius(true)
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
    public void onEventReceiveBean(CardBigBean.PinsEntity bean) {
        Logger.d(TAG + " receive bean");
        url_img = url_image + bean.getFile().getKey();
        url_head = bean.getUser().getAvatar();

        img_image_big.setAspectRatio(Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight()));

        setImageTextInfo(bean);//设置图片的文本信息

    }

    private void setImageTextInfo(CardBigBean.PinsEntity bean) {
        String raw = bean.getRaw_text();
        if (!TextUtils.isEmpty(raw)) {
            setTitle(raw);
            tv_image_text.setText(raw);
        } else {
            setTitle("");
            tv_image_text.setText("图片暂无描述");
        }

        String link = bean.getLink();
        String source = bean.getSource();
        if ((!TextUtils.isEmpty(link)) && (!TextUtils.isEmpty(source))) {
            tv_image_link.setText(source);
            tv_image_link.setTag(link);
        } else {
            tv_image_link.setVisibility(View.GONE);
        }


        String dTime = TimeUtils.getTimeDifference(bean.getCreated_at(), System.currentTimeMillis());
        tv_image_time.setText(dTime);

        String user = bean.getUser().getUsername();
        tv_image_user.setText(user);

        String board_title = bean.getBoard().getTitle();
        if (!TextUtils.isEmpty(board_title)) {
            tv_image_board.setText(board_title);
        } else {
            tv_image_board.setText("暂无画板信息");
        }
    }

    @OnClick(R.id.rl_image_user)
    public void onClickItemImageUser() {
        Logger.d();
    }

    @OnClick(R.id.rl_image_board)
    public void onClickItemImageBoard() {
        Logger.d();
    }

    @OnClick(R.id.tv_image_link)
    public void onClickLink(View view) {
        String url = (String) view.getTag();
        Logger.d(url);

        //直接打开浏览器
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse(url);
//        intent.setData(content_url);
//        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
//        startActivity(intent);

        //打开选择浏览器 再浏览界面
        final Uri uri = Uri.parse(url);
        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }
}
