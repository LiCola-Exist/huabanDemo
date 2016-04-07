package licola.demo.com.huabandemo.MyUser;

import java.util.List;

/**
 * Created by LiCola on  2016/04/07  17:37
 */
public class UserBoardListBean {

    /**
     * board_id : 17793839
     * user_id : 15246080
     * title : 待归类采集
     * description :
     * category_id : null
     * seq : 2
     * pin_count : 184
     * follow_count : 8
     * like_count : 0
     * created_at : 1411778585
     * updated_at : 1411778585
     * deleting : 0
     * is_private : 2
     * following : false
     */

    private List<UserBoardItemBean> boards;

    public List<UserBoardItemBean> getBoards() {
        return boards;
    }

    public void setBoards(List<UserBoardItemBean> boards) {
        this.boards = boards;
    }


}
