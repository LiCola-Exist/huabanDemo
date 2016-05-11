package licola.demo.com.huabandemo.API.Dialog;

/**
 * Created by LiCola on  2016/05/06  19:03
 * 采集对话框的交互接口
 * 由dialog传递 宿主实现
 */
public interface OnGatherDialogInteractionListener {
     void onDialogPositiveClick(String describe,int selectPosition);
}
