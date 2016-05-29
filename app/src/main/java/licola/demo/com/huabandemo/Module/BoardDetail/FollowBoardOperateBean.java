package licola.demo.com.huabandemo.Module.BoardDetail;

/**
 * Created by LiCola on  2016/05/07  13:48
 */
public class FollowBoardOperateBean {

    /**
     * board_id : 967118
     * user_id : 15246080
     * board_owner_id : 134270
     * seq : 993083628
     * followed_at : 1462599833
     */

    private FollowBean follow;

    public FollowBean getFollow() {
        return follow;
    }

    public void setFollow(FollowBean follow) {
        this.follow = follow;
    }

    public static class FollowBean {
        private int board_id;
        private int user_id;
        private int board_owner_id;
        private int seq;
        private int followed_at;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBoard_owner_id() {
            return board_owner_id;
        }

        public void setBoard_owner_id(int board_owner_id) {
            this.board_owner_id = board_owner_id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getFollowed_at() {
            return followed_at;
        }

        public void setFollowed_at(int followed_at) {
            this.followed_at = followed_at;
        }
    }
}
