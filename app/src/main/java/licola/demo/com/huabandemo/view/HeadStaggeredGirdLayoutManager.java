package licola.demo.com.huabandemo.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import licola.demo.com.huabandemo.Util.Logger;

/**
 * Created by LiCola on  2016/03/23  21:11
 */
public class HeadStaggeredGirdLayoutManager extends StaggeredGridLayoutManager {
    private static final String TAG = "HeadStaggeredGirdLayoutManager";

    public HeadStaggeredGirdLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
//        if (view.getAdapter()!=null){
//            Logger.d(" "+view.getAdapter().getItemCount());
////            RecyclerView.ViewHolder holder=view.findViewHolderForAdapterPosition()
////            Logger.d(holder.toString());
//        }else {
//            Logger.d();
//        }
//        ViewGroup.LayoutParams layoutParams= view.findViewHolderForAdapterPosition(0).itemView.getLayoutParams();
//        if (layoutParams!=null &&layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
//            StaggeredGridLayoutManager.LayoutParams params= (StaggeredGridLayoutManager.LayoutParams) layoutParams;
//            params.setFullSpan(view.findViewHolderForLayoutPosition(0).getLayoutPosition()==0);
//        }
//        Logger.d(layoutParams.toString());
    }

}
