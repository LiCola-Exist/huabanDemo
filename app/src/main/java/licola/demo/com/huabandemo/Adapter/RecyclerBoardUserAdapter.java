package licola.demo.com.huabandemo.Adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import licola.demo.com.huabandemo.Base.BaseRecyclerAdapter;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.Module.User.UserBoardItemBean;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Logger;

import static android.view.View.OnClickListener;


/**
 * Created by LiCola on  2016/04/07  17:00
 * 负责展示 展示用户的画板 adapter 差别在于底部是否有操作功能
 * 在我的界面是 是编辑 在其他用户是关注
 * 需要判断是否 isMe 影响底部的显示
 */
public class RecyclerBoardUserAdapter extends BaseRecyclerAdapter<UserBoardItemBean> {

    private boolean isMe;//是否是我的标志位 true是我 否则不是

    private onAdapterListener mListener;

    private final String mAttentionFormat;//关注数量
    private final String mGatherFormat;//采集数量
    private final String mOperateEdit;//编辑
    private final String mOperateFollowing;//关注
    private final String mOperateFollowed;//已关注

    private final Drawable mDrawableBlock;
    private final Drawable mDrawableEdit;
    private final Drawable mDrawableFollowing;
    private final Drawable mDrawableFollowed;

    public interface onAdapterListener {
        void onClickImage(UserBoardItemBean bean, View view);

        void onClickOperate(UserBoardItemBean bean, View view);
    }

    public RecyclerBoardUserAdapter(RecyclerView mRecyclerView, boolean isMe) {
        super(mRecyclerView);
        this.isMe = isMe;
        Resources resources = mContext.getResources();
        this.mGatherFormat = resources.getString(R.string.text_gather_number);
        this.mAttentionFormat = resources.getString(R.string.text_attention_number);
        this.mOperateEdit = resources.getString(R.string.text_edit);
        this.mOperateFollowing = resources.getString(R.string.text_following);
        this.mOperateFollowed = resources.getString(R.string.text_followed);
        this.mDrawableBlock = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_block_black_24dp, R.color.tint_list_grey);
        this.mDrawableEdit = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_mode_edit_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowing = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_add_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowed = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_check_black_24dp, R.color.tint_list_grey);
    }


    public void setOnClickItemListener(onAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Logger.d(life);
        ViewHolderBoard holder = null;//ViewHolder的子类

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_board_user, parent, false);
        holder = new ViewHolderBoard(view);

        //子类可以自动转型为父类
        return holder;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        //当有被被回收viewHolder调用
        //Logger.d(life);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //Logger.d(life);
        final UserBoardItemBean bean = mList.get(position);

        //父类强制转换成子类 因为这个holder本来就是子类初始化的 所以可以强转
        ViewHolderBoard viewHolder = (ViewHolderBoard) holder;//强制类型转换 转成内部的ViewHolder

        onBindData(viewHolder, bean);
        onBindListener(viewHolder, bean);//初始化点击事件

    }


    private void onBindData(final ViewHolderBoard holder, UserBoardItemBean bean) {
        //检查图片信息
        Logger.d("deleting " + bean.getDeleting());
        boolean isOperate = false;
        //不为0的 标志位 才能操作
        if (bean.getDeleting() != 0) {
            isOperate = true;
        }
        boolean isFollowing = bean.isFollowing();
        Drawable drawable;
        String text;

        if (isOperate) {
            if (isMe) {
                text = mOperateEdit;
                drawable = mDrawableEdit;
            } else {
                if (isFollowing) {
                    text = mOperateFollowed;
                    drawable = mDrawableFollowed;
                } else {
                    text = mOperateFollowing;
                    drawable = mDrawableFollowing;
                }
            }
        } else {
            drawable = mDrawableBlock;
            text = "";
        }

        holder.tv_board_operate.setText(text);
        holder.linearLayout_gourp.setTag(isOperate);
        holder.tv_board_operate.setCompoundDrawablesWithIntrinsicBounds(
                drawable,
                null,
                null,
                null);

        if (checkInfoContext(bean)) {
            holder.tv_board_title.setText(bean.getTitle());
            holder.tv_board_gather.setText(String.format(mGatherFormat, bean.getPin_count()));
            holder.tv_board_attention.setText(String.format(mAttentionFormat, bean.getFollow_count()));
        } else {
            //// TODO: 2016/4/5 0005 空值状态的显示
        }

        String url_img = String.format(mUrlGeneralFormat, getFirstPinsFileKey(bean));

        float ratio = 1f;//固定宽高比 为1:1 矩形图
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比
        Drawable dProgressImage =
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_toys_black_48dp, R.color.tint_list_pink);

        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_card_image, url_img)
                .setProgressBarImage(dProgressImage)
                .build();
    }

    private String getFirstPinsFileKey(UserBoardItemBean bean) {
        int size = bean.getPins().size();
        if (size > 0) {
            return bean.getPins().get(0).getFile().getKey();
        } else {
            return "";
        }
    }

    /**
     * 检查文字项目
     *
     * @param bean
     * @return
     */
    private boolean checkInfoContext(UserBoardItemBean bean) {

        return true;
    }

    private void onBindListener(ViewHolderBoard holder, final UserBoardItemBean bean) {

        holder.frameLayout_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickImage(bean, v);
            }
        });

        holder.linearLayout_gourp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOperate = (boolean) v.getTag();
                if (isOperate) {
                    mListener.onClickOperate(bean, v);
                } else {
                    mListener.onClickImage(bean, v);
                }
            }
        });
    }

    public static class ViewHolderBoard extends RecyclerView.ViewHolder {
        //这个CardView采用两层操作
        public final View mView;

        public final LinearLayout linearLayout_gourp;
        public final FrameLayout frameLayout_image;//第一层 
        public final SimpleDraweeView img_card_image;

        public final TextView tv_board_title;
        public final TextView tv_board_gather;
        public final TextView tv_board_attention;

        public final TextView tv_board_operate;

        public ViewHolderBoard(View view) {
            super(view);
            mView = view;
            linearLayout_gourp = (LinearLayout) view.findViewById(R.id.linearlayout_group);
            frameLayout_image = (FrameLayout) view.findViewById(R.id.framelayout_image);
            img_card_image = (SimpleDraweeView) view.findViewById(R.id.img_card_image);//主图

            tv_board_title = (TextView) view.findViewById(R.id.tv_board_title);//描述的title
            tv_board_gather = (TextView) view.findViewById(R.id.tv_board_gather);//描述的title
            tv_board_attention = (TextView) view.findViewById(R.id.tv_board_attention);//描述的title
            tv_board_operate = (TextView) view.findViewById(R.id.tv_board_operate);//相关操作

        }

    }


}
