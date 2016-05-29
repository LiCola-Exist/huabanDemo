package licola.demo.com.huabandemo.API.Fragment;

import android.view.View;

/**
 * Created by LiCola on  2016/04/04  23:31
 * 所有有pins对象列表的 共用接口
 */
public interface OnBoardFragmentInteractionListener<T> {
    void onClickBoardItemImage(T bean, View view);

//    void onClickBoardItemOperate(T bean, View view);
}
