package licola.demo.com.huabandemo.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import licola.demo.com.huabandemo.Base.BaseRecyclerAdapter;
import licola.demo.com.huabandemo.HttpUtils.ImageLoadFresco;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.CompatUtils;

import static android.view.View.OnClickListener;
import static licola.demo.com.huabandemo.Module.SearchResult.SearchPeopleListBean.UsersBean;


/**
 * Created by LiCola on  2016/03/22  18:00
 * 负责展示  图片CardView 的adapter
 */
public class RecyclerPeopleAdapter extends BaseRecyclerAdapter<UsersBean> {

    private OnAdapterListener mListener;

    private final String mFansFormat;
    private final String mHttpRoot;


    public interface OnAdapterListener {
        void onClickUser(UsersBean bean, View view);
    }

    public RecyclerPeopleAdapter(RecyclerView mRecyclerView) {
        super(mRecyclerView);
        this.mFansFormat = mContext.getResources().getString(R.string.text_fans_number);
        this.mHttpRoot = mContext.getResources().getString(R.string.httpRoot);
    }


    public void setOnClickItemListener(OnAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Logger.d(life);
        ViewHolderGeneral holder = null;//ViewHolder的子类

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item_user, parent, false);
        holder = new ViewHolderGeneral(view);//使用子类初始化ViewHolder

        holder.ibtn_image_user_chevron_right.setImageDrawable(
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_chevron_right_black_36dp, R.color.tint_list_grey));

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
        final UsersBean bean = mList.get(position);

        //父类强制转换成子类 因为这个holder本来就是子类初始化的 所以可以强转
        ViewHolderGeneral viewHolder = (ViewHolderGeneral) holder;//强制类型转换 转成内部的ViewHolder

        onBindData(viewHolder, bean);
        onBindListener(viewHolder, bean);//初始化点击事件

    }


    private void onBindData(final ViewHolderGeneral holder, UsersBean bean) {
        //检查图片信息
        if (checkInfoContext(bean)) {
            holder.tv_image_user.setText(bean.getUsername());
            holder.tv_image_about.setText(String.format(mFansFormat, bean.getFollower_count()));
        } else {
            //// TODO: 2016/4/5 0005 空值显示处理
        }

        //用户信息
        String url = bean.getAvatar();
        //非空检查
        if (url != null) {
            //如果是花瓣本地服务器的图片 不包含只有图片的key 加载时需要添加http头
            //否则 可以直接使用
            if (!url.contains(mHttpRoot)) {
                url = String.format(mUrlSmallFormat, url);
            }
            new ImageLoadFresco.LoadImageFrescoBuilder(mContext, holder.img_image_user, url)
                    .setPlaceHolderImage(CompatUtils.getTintDrawable(mContext,R.drawable.ic_account_circle_gray_48dp, Color.GRAY))
                    .setIsCircle(true)
                    .build();
        }else {
            //// TODO: 2016/4/6 0006 对于同一个View，请不要多次调用setHierarchy，即使这个View是可回收的。创建 DraweeHierarchy 的较为耗时的一个过程，应该多次利用。

//            holder.img_image_user.setImageURI(null);
            holder.img_image_user.getHierarchy().setPlaceholderImage(R.drawable.ic_account_circle_gray_48dp);
        }


    }

    /**
     * 检查三项信息 任何一项不为空 都返回true
     *
     * @param bean
     * @return
     */
    private boolean checkInfoContext(UsersBean bean) {
        return true;
    }

    private void onBindListener(ViewHolderGeneral holder, final UsersBean bean) {
        holder.mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickUser(bean, v);
            }
        });

    }

    public static class ViewHolderGeneral extends RecyclerView.ViewHolder {
        //这个CardView采用两层操作
        public final View mView;


        public final SimpleDraweeView img_image_user;
        public final ImageButton ibtn_image_user_chevron_right;


        public final TextView tv_image_user;
        public final TextView tv_image_about;

        public ViewHolderGeneral(View view) {
            super(view);
            mView = view;
            img_image_user = (SimpleDraweeView) view.findViewById(R.id.img_image_user);//主图
            ibtn_image_user_chevron_right = (ImageButton) view.findViewById(R.id.ibtn_image_user_chevron_right);//播放按钮

            tv_image_user = (TextView) view.findViewById(R.id.tv_image_user);
            tv_image_about = (TextView) view.findViewById(R.id.tv_image_about);
        }

    }


}
