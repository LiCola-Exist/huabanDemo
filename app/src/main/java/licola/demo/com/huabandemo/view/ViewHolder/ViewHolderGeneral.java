package licola.demo.com.huabandemo.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import licola.demo.com.huabandemo.R;

/**
 * Created by LiCola on  2016/03/28  19:28
 * 从adapter 抽离出来的 ViewHolder 方便继承
 */
public class ViewHolderGeneral extends RecyclerView.ViewHolder {
    //这个CardView采用两层操作
    public final View mView;

    public final FrameLayout rl_image;//第一层 包含图片和播放按钮
    public final SimpleDraweeView img_card_image;
    public final ImageButton ibtn_card_gif;

    public final LinearLayout ll_title_info;//第二层 包含描述 图片信息
    public final TextView tv_card_title;//第二层 描述title

    public final LinearLayout ll_info;//第二层的子类 包含图片被采集和喜爱的信息
    public final TextView tv_card_gather;
    public final TextView tv_card_like;

    public ViewHolderGeneral(View view) {
        super(view);
        mView = view;
        rl_image = (FrameLayout) view.findViewById(R.id.framelayout_image);
        img_card_image = (SimpleDraweeView) view.findViewById(R.id.img_card_image);//主图
        ibtn_card_gif = (ImageButton) view.findViewById(R.id.ibtn_card_gif);//播放按钮

        ll_title_info = (LinearLayout) view.findViewById(R.id.linearlayout_title_info);//图片所有文字信息
        tv_card_title = (TextView) view.findViewById(R.id.tv_card_title);//描述的title

        ll_info = (LinearLayout) view.findViewById(R.id.linearlayout_info);//文字子类
        tv_card_gather = (TextView) view.findViewById(R.id.tv_card_gather);
        tv_card_like = (TextView) view.findViewById(R.id.tv_card_like);
    }

}
