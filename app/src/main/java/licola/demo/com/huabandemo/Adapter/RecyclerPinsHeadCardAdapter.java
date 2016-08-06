package licola.demo.com.huabandemo.Adapter;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import licola.demo.com.huabandemo.Base.BaseRecyclerAdapter;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;
import licola.demo.com.huabandemo.Util.Utils;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.ViewGroup.VISIBLE;


/**
 * Created by LiCola on  2016/03/22  18:00
 * 负责展示  图片CardView 的adapter
 */
public class RecyclerPinsHeadCardAdapter extends BaseRecyclerAdapter<PinsMainEntity>  {

    private boolean mIsShowUser = false;//是否显示用户头像和名字的标志位

    private OnAdapterListener mListener;

    public interface OnAdapterListener {
        void onClickImage(PinsMainEntity bean, View view);

        void onClickTitleInfo(PinsMainEntity bean, View view);

        void onClickInfoGather(PinsMainEntity bean, View view);

        void onClickInfoLike(PinsMainEntity bean, View view);

    }



    public RecyclerPinsHeadCardAdapter(RecyclerView mRecyclerView) {
        super(mRecyclerView);
    }



    //多一个标志位的 构造函数
    public RecyclerPinsHeadCardAdapter(RecyclerView recyclerView, boolean isShowUser) {
        this(recyclerView);
        this.mIsShowUser = isShowUser;
    }


    public void setOnClickItemListener(OnAdapterListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Logger.d(life);
        ViewHolderGeneral holder = null;//ViewHolder的子类

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_image, parent, false);
        holder = new ViewHolderGeneral(view);//使用子类初始化ViewHolder

        holder.tv_card_like.setCompoundDrawablesWithIntrinsicBounds(
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_favorite_black_18dp, R.color.tint_list_grey),
                null,
                null,
                null);
        holder.tv_card_gather.setCompoundDrawablesWithIntrinsicBounds(
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_camera_black_18dp, R.color.tint_list_grey),
                null,
                null,
                null);


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
//        Logger.d(life);
        final PinsMainEntity bean = mList.get(position);

        //注释的是 动态修改image高度
//        LayoutParams lp = holder.img_card_image.getLayoutParams();
//        lp.height = height[mAdapterPosition];
//        holder.img_card_image.setLayoutParams(lp);

        //父类强制转换成子类 因为这个holder本来就是子类初始化的 所以可以强转
        ViewHolderGeneral viewHolder = (ViewHolderGeneral) holder;//强制类型转换 转成内部的ViewHolder

        onBindData(viewHolder, bean);
        onBindListener(viewHolder, bean);//初始化点击事件

    }


    private void onBindData(final ViewHolderGeneral holder, PinsMainEntity bean) {
        //检查图片信息
        if (checkInfoContext(bean)) {
            holder.ll_title_info.setVisibility(VISIBLE);

            String title = bean.getRaw_text();//图片的文字描述
            int like = bean.getLike_count();//被喜欢数量
            int gather = bean.getRepin_count();//被转采的数量
            if (!TextUtils.isEmpty(title)) {
                holder.tv_card_title.setVisibility(VISIBLE);
                holder.tv_card_title.setText(title);
            } else {
                holder.tv_card_title.setVisibility(GONE);
            }
            holder.tv_card_like.setText(" " + like);
            holder.tv_card_gather.setText(" " + gather);
        } else {
            holder.ll_title_info.setVisibility(GONE);
        }


//        String url_img = mUrlFormat + bean.getFile().getKey()+"_fw320sf";
        String url_img = String.format(mUrlGeneralFormat, bean.getFile().getKey());

//        String mImageUrl = "http://img.hb.aicdn.com/1d16a79ac7cffbec844eb48e7e714c9f8c0afffc7f997-ZZCJsm";

        if (Utils.checkIsGif(bean.getFile().getType())) {
            holder.ibtn_card_gif.setVisibility(VISIBLE);
        } else {
            holder.ibtn_card_gif.setVisibility(INVISIBLE);
        }

        float ratio = Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight());
        //长图 "width":440,"height":5040,
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比
        Drawable dProgressImage =
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_toys_black_48dp, R.color.tint_list_pink);

        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_card_image, url_img)
                .setProgressBarImage(dProgressImage)
                //加载gif图 自动播放
//                .setControllerListener(new BaseControllerListener<ImageInfo>() {
//                    @Override
//                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                        super.onFinalImageSet(id, imageInfo, animatable);
//                        if (animatable != null) {
//                            holder.ibtn_card_gif.setVisibility(VISIBLE);
//                            setPlayListener(holder, animatable);
//                            setPlayDrawable(holder, false);
//                        } else {
//                            holder.ibtn_card_gif.setVisibility(INVISIBLE);
//                        }
//                    }
//                })
                .build();
    }

    /**
     * 检查三项信息 任何一项不为空 都返回true
     *
     * @param bean
     * @return
     */
    private boolean checkInfoContext(PinsMainEntity bean) {

        String title = bean.getRaw_text();//图片的文字描述
        int like = bean.getLike_count();//被喜欢数量
        int gather = bean.getRepin_count();//被转采的数量

        if (!TextUtils.isEmpty(title)) {
            return true;
        } else if (like > 0 || gather > 0) {
            return true;
        }

        return false;
    }

    private void onBindListener(ViewHolderGeneral holder, final PinsMainEntity bean) {

        holder.rl_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickImage(bean, v);
            }
        });

        holder.ll_title_info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickTitleInfo(bean, v);
            }
        });

        holder.tv_card_gather.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickInfoGather(bean, v);
            }
        });

        holder.tv_card_like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickInfoLike(bean, v);
//                    RxBus.getDefault().post(bean);
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
    private void setPlayDrawable(ViewHolderGeneral holder, boolean isRunning) {
        if (!isRunning) {
            Drawable drawable = holder.ibtn_card_gif.getResources().getDrawable(android.R.drawable.ic_media_play);
            holder.ibtn_card_gif.setImageDrawable(drawable);
        } else {
            Drawable drawable = holder.ibtn_card_gif.getResources().getDrawable(android.R.drawable.ic_media_pause);
            holder.ibtn_card_gif.setImageDrawable(drawable);
        }
    }

    private void setPlayListener(final ViewHolderGeneral holder, final Animatable animatable) {
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


    public static class ViewHolderGeneral extends RecyclerView.ViewHolder {
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


}
