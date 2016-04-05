package licola.demo.com.huabandemo.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.My.FollowingBoardItemBean;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Utils;
import licola.demo.com.huabandemo.View.recyclerview.RecyclerViewUtils;

import static android.view.View.OnClickListener;


/**
 * Created by LiCola on  2016/04/05  15:00
 * 负责展示 画板信息的 adapter
 */
public class RecyclerBoardAdapter extends RecyclerView.Adapter {
    private final String life = "BoardLife";
    private RecyclerView mRecyclerView;
    private Context mContext;

    private List<FollowingBoardItemBean> mList = new ArrayList<>(20);
    private onAdapterListener mListener;
    private final String mUrlFormat;//地址
    private final String mAttentionFormat;//关注数量
    private final String mGatherFormat;//采集数量
    private final String mUsernameFormat;//采集数量

    public int mAdapterPosition = 0;

    public List<FollowingBoardItemBean> getmList() {
        return mList;
    }

    public void setList(List<FollowingBoardItemBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addList(List<FollowingBoardItemBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public int getAdapterPosition() {
        return mAdapterPosition;
    }

    public void setAdapterPosition(int mAdapterPosition) {
        this.mAdapterPosition = mAdapterPosition;
    }


    public interface onAdapterListener {
        void onClickImage(FollowingBoardItemBean bean, View view);

        void onClickTextInfo(FollowingBoardItemBean bean, View view);

    }

    public RecyclerBoardAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mContext = recyclerView.getContext();
        this.mUrlFormat = mContext.getResources().getString(R.string.url_image_general);
        this.mGatherFormat = mContext.getResources().getString(R.string.text_gather_number);
        this.mAttentionFormat = mContext.getResources().getString(R.string.text_attention_number);
        this.mUsernameFormat = mContext.getResources().getString(R.string.text_by_username);
    }

    public void setOnClickItemListener(onAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Logger.d(life);
        ViewHolderBoard holder = null;//ViewHolder的子类

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_board, parent, false);
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
        final FollowingBoardItemBean bean = mList.get(position);

        //父类强制转换成子类 因为这个holder本来就是子类初始化的 所以可以强转
        ViewHolderBoard viewHolder = (ViewHolderBoard) holder;//强制类型转换 转成内部的ViewHolder

        onBindData(viewHolder, bean);
        onBindListener(viewHolder, bean);//初始化点击事件

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mAdapterPosition = RecyclerViewUtils.getAdapterPosition(mRecyclerView, holder);
    }

    private void onBindData(final ViewHolderBoard holder, FollowingBoardItemBean bean) {
        //检查图片信息
        if (checkInfoContext(bean)) {
            holder.tv_board_title.setText(bean.getTitle());
            holder.tv_board_gather.setText(String.format(mGatherFormat,bean.getPin_count()));
            holder.tv_board_attention.setText(String.format(mAttentionFormat,bean.getFollow_count()));
            holder.tv_board_username.setText(String.format(mUsernameFormat,bean.getUser().getUsername()));
        } else {
            //// TODO: 2016/4/5 0005 空值状态的显示
        }

        String url_img=String.format(mUrlFormat,getFirstPinsFileKey(bean));

        float ratio = 1f;//固定宽高比 为1:1 矩形图
        holder.img_card_image.setAspectRatio(ratio);//设置宽高比
        Drawable dProgressImage =
                Utils.getTintCompatDrawable(mContext, R.drawable.ic_toys_black_48dp, R.color.tint_list_pink);

        new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_card_image, url_img)
                .setProgressBarImage(dProgressImage)
                .build();
    }

    private String getFirstPinsFileKey(FollowingBoardItemBean bean) {
        int size=bean.getPins().size();
        if (size>0){
            return bean.getPins().get(0).getFile().getKey();
        }else {
            return "";
        }
    }

    /**
     * 检查文字项目
     * @param bean
     * @return
     */
    private boolean checkInfoContext(FollowingBoardItemBean bean) {

        return true;
    }

    private void onBindListener(ViewHolderBoard holder, final FollowingBoardItemBean bean) {

        holder.frameLayout_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickImage(bean, v);
            }
        });

       holder.tv_board_username.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               mListener.onClickTextInfo(bean,v);
           }
       });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolderBoard extends RecyclerView.ViewHolder {
        //这个CardView采用两层操作
        public final View mView;

        public final FrameLayout frameLayout_image;//第一层 
        public final SimpleDraweeView img_card_image;

        public final TextView tv_board_title;
        public final TextView tv_board_gather;
        public final TextView tv_board_attention;

        public final TextView tv_board_username;

        public ViewHolderBoard(View view) {
            super(view);
            mView = view;
            frameLayout_image = (FrameLayout) view.findViewById(R.id.framelayout_image);
            img_card_image = (SimpleDraweeView) view.findViewById(R.id.img_card_image);//主图

            tv_board_title = (TextView) view.findViewById(R.id.tv_board_title);//描述的title
            tv_board_gather = (TextView) view.findViewById(R.id.tv_board_gather);//描述的title
            tv_board_attention = (TextView) view.findViewById(R.id.tv_board_attention);//描述的title
            tv_board_username = (TextView) view.findViewById(R.id.tv_board_username);//描述的title

        }

    }


}
