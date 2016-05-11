package licola.demo.com.huabandemo.API.Fragment;

/**
 * Created by LiCola on  2016/04/04  23:38
 */
public interface OnBoardDetailFragmentInteractionListener
        extends OnPinsFragmentInteractionListener {

    void onClickUserField(String key, String title);

    void onHttpBoardAttentionState(String userId,boolean isAttention);
}
