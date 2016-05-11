package licola.demo.com.huabandemo.Module.Main;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;

import static android.view.ViewGroup.OnClickListener;
import static android.view.ViewGroup.VISIBLE;


/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private final String life = "Life";
    private Context mContext;
    private List<PinsMainEntity> mList = new ArrayList<>(20);
    private onAdapterListener mListener;
    private final String url_root;
    public int mAdapterPosition = 0;

    public List<PinsMainEntity> getmList() {
        return mList;
    }

    public void setmList(List<PinsMainEntity> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setNotifyData(List<PinsMainEntity> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public int getmAdapterPosition() {
        return mAdapterPosition;
    }

    public void setmAdapterPosition(int mAdapterPosition) {
        this.mAdapterPosition = mAdapterPosition;
    }


    public interface onAdapterListener {
        void onClickImage(PinsMainEntity bean, View view);

        void onClickBoard(PinsMainEntity bean, View view);

        void onClickInfo(PinsMainEntity bean, View view);
    }

    public MainRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.url_root = mContext.getResources().getString(R.string.urlImageRoot);
    }


    public void setOnClickItemListener(onAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Logger.d(life);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        //当有被被回收viewHolder调用
        // Logger.d(life);
        holder.img_card_image.setTag(null);
        holder.img_card_head.setTag(null);
        holder.ibtn_card_gif.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Logger.d(life);
//        Logger.d(holder.toString());
        final PinsMainEntity bean = mList.get(position);

        //注释的是 动态修改image高度
//        LayoutParams lp = holder.img_card_image.getLayoutParams();
//        lp.height = height[mAdapterPosition];
//        holder.img_card_image.setLayoutParams(lp);

        onBindData(holder, bean);
        onBindListener(holder, bean);//初始化点击事件

    }

    @Override
    public void onViewAttachedToWindow(final ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // Logger.d(life);
        mAdapterPosition = holder.getAdapterPosition();
    }

    private void onBindData(final ViewHolder holder, PinsMainEntity bean) {
        String title = bean.getRaw_text();
        if (!TextUtils.isEmpty(title)) {
            holder.tv_card_title.setVisibility(VISIBLE);
            holder.tv_card_title.setText(title);
        } else {
            holder.tv_card_title.setVisibility(View.GONE);
        }

        holder.tv_card_gather.setText(bean.getBoard().getTitle());
        holder.tv_card_username.setText(bean.getUser().getUsername());

        String url_img = url_root + bean.getFile().getKey();
        String url_head = bean.getUser().getAvatar();
//        String mImageUrl = "http://img.hb.aicdn.com/1d16a79ac7cffbec844eb48e7e714c9f8c0afffc7f997-ZZCJsm";

        float ratio = Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight());
        //长图 "width":440,"height":5040,
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比


//        FrescoBuilder.setHeadDrawableMC2V(mContext, holder.img_card_head, url_head,true);
        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_card_head, url_head)
                .setIsCircle(true)
                .build();


//        FrescoBuilder.setImageDrawableMC2V(mContext, holder.img_card_image, mImageUrl, new FrescoBuilder.onAnimatableListener() {
//            @Override
//            public void onComplete(boolean isPlay, Animatable animatable) {
//                if (isPlay) {
//                    holder.ibtn_card_gif.setVisibility(VISIBLE);
//                    setPlayListener(holder, animatable);
//                    setPlayDrawable(holder, false);
//                }
//            }
//        });
        Drawable dProgressImage = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, R.drawable.ic_toys_black_24dp).mutate());
        DrawableCompat.setTintList(dProgressImage, ContextCompat.getColorStateList(mContext, R.color.tint_list_pink));

        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_card_image, url_img)
                .setProgressBarImage(dProgressImage)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (animatable != null) {
                            holder.ibtn_card_gif.setVisibility(VISIBLE);
                            setPlayListener(holder, animatable);
                            setPlayDrawable(holder, false);
                        }
                    }
                })
                .build();
    }

    private void onBindListener(final ViewHolder holder, final PinsMainEntity bean) {

        holder.rl_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickImage(bean, holder.rl_image);
            }
        });

        holder.ll_board.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickBoard(bean, holder.ll_board);
            }
        });

        holder.ll_info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickInfo(bean, holder.ll_info);
            }
        });
    }

    /**
     * 设置ibtn_card_gif的显示
     * 传true就显示暂停 传false显示播放
     *
     * @param holder
     * @param isRunning 默认false为不播放
     */
    private void setPlayDrawable(ViewHolder holder, boolean isRunning) {
        if (!isRunning) {
            Drawable drawable = holder.ibtn_card_gif.getResources().getDrawable(android.R.drawable.ic_media_play);
            holder.ibtn_card_gif.setImageDrawable(drawable);
        } else {
            Drawable drawable = holder.ibtn_card_gif.getResources().getDrawable(android.R.drawable.ic_media_pause);
            holder.ibtn_card_gif.setImageDrawable(drawable);
        }
    }

    private void setPlayListener(final ViewHolder holder, final Animatable animatable) {
        holder.ibtn_card_gif.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!animatable.isRunning()) {
                    animatable.start();
                    setPlayDrawable(holder, true);
                } else {
                    animatable.stop();
                    setPlayDrawable(holder, false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //这个CardView采用三层操作
        public final View mView;

        public final FrameLayout rl_image;//第一层
        public final SimpleDraweeView img_card_image;
        public final ImageButton ibtn_card_gif;

        public final LinearLayout ll_board;//第二层
        public final TextView tv_card_title;

        public final TextView tv_card_gather;
        public final LinearLayout ll_info;//第三层
        public final TextView tv_card_username;
        public final SimpleDraweeView img_card_head;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            rl_image = (FrameLayout) view.findViewById(R.id.framelayout_image);
            ll_info = (LinearLayout) view.findViewById(R.id.linearlayout_info);
            ll_board = (LinearLayout) view.findViewById(R.id.ll_board);
            img_card_image = (SimpleDraweeView) view.findViewById(R.id.img_card_image);
            img_card_head = (SimpleDraweeView) view.findViewById(R.id.img_card_head);
            ibtn_card_gif = (ImageButton) view.findViewById(R.id.ibtn_card_gif);
            tv_card_title = (TextView) view.findViewById(R.id.tv_card_title);
            tv_card_username = (TextView) view.findViewById(R.id.tv_card_username);
            tv_card_gather = (TextView) view.findViewById(R.id.tv_card_gather);
        }

    }
}
