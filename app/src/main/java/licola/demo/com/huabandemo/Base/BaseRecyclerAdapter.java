package licola.demo.com.huabandemo.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Widget.MyRecyclerview.RecyclerViewUtils;

/**
 * Created by LiCola on  2016/04/07  19:30
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter   {
    protected final String life = "AdapterLife";
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    protected List<T> mList =new ArrayList<>(20);
    protected int mAdapterPosition = 0;
    protected final String mUrlSmallFormat;//小图地址
    protected final String mUrlGeneralFormat;//普通地址
    protected final String mUrlBigFormat;//大图地址
    
    public List<T> getList() {
        return mList;
    }

    public void setListNotify(List<T> mList) {
        this.mList.clear();
        this.mList=mList;
        notifyDataSetChanged();
    }

    public void addListNotify(List<T> mList){
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public int getAdapterPosition() {
        return mAdapterPosition;
    }

    public void setAdapterPosition(int mAdapterPosition) {
        this.mAdapterPosition = mAdapterPosition;
    }

    public BaseRecyclerAdapter(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mContext=mRecyclerView.getContext();
        this.mUrlSmallFormat = mContext.getResources().getString(R.string.url_image_small);
        this.mUrlGeneralFormat = mContext.getResources().getString(R.string.url_image_general);
        this.mUrlBigFormat = mContext.getResources().getString(R.string.url_image_big);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mAdapterPosition = RecyclerViewUtils.getAdapterPosition(mRecyclerView, holder);

    }

//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//        Logger.d("finalize"+life);
//    }
}
