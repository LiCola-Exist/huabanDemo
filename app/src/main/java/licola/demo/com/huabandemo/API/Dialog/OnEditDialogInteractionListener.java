package licola.demo.com.huabandemo.API.Dialog;

/**
 * Created by LiCola on  2016/05/10  15:32
 */
public interface OnEditDialogInteractionListener {
    void onDialogPositiveClick(String boardId,String name,String describe,String selectType);

    void onDialogNeutralClick(String boardId,String boardTitle);
}
