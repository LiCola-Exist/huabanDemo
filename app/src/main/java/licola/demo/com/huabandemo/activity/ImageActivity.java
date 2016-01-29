package licola.demo.com.huabandemo.activity;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.httpUtils.FrescoBuilder;

public class ImageActivity extends BaseActivity {
    private String url_img;
    private String url;
    private Context mContext=this;

    private SimpleDraweeView img_image_top;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected String getTAG() {
        return "Image_old";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 允许使用transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(new Explode());
        }else {
            Logger.d("< LOLLIPOP");
        }

        url_img=getResources().getString(R.string.url_image);
        img_image_top= (SimpleDraweeView) findViewById(R.id.img_image_top);
        url=url_img+getIntent().getStringExtra("key");
        Logger.d(url);

        Button btn_image_title= (Button) findViewById(R.id.btn_image_title);
        btn_image_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }
            }
        });

        startLoadImage();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void startLoadImage() {
//        FrescoBuilder.setImageDrawableMC2V(mContext, img_image_top, url, new FrescoBuilder.onAnimatableListener() {
//            @Override
//            public void onComplete(boolean isPlay, Animatable animatable) {
//                Logger.d("image is onComplete");
//            }
//        });
    }
}
