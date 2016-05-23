package licola.demo.com.huabandemo.API.Fragment;

import android.view.View;

import licola.demo.com.huabandemo.Entity.PinsMainEntity;

/**
 * Created by LiCola on  2016/04/04  23:31
 * 所有有pins对象列表的 共用接口
 */
public interface OnPinsFragmentInteractionListener {
    void onClickPinsItemImage(PinsMainEntity bean, View view);

    void onClickPinsItemText(PinsMainEntity bean, View view);
}
