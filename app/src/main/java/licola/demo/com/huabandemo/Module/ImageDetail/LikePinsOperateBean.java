package licola.demo.com.huabandemo.Module.ImageDetail;

/**
 * Created by LiCola on  2016/04/19  17:25
 */
public class LikePinsOperateBean {

    /**
     * pin_id : 690448610
     * user_id : 15246080
     * seq : 52990507
     * liked_at : 1461060515
     */

    private LikeBean like;

    public LikeBean getLike() {
        return like;
    }

    public void setLike(LikeBean like) {
        this.like = like;
    }

    public static class LikeBean {
        private int pin_id;
        private int user_id;
        private int seq;
        private int liked_at;

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getLiked_at() {
            return liked_at;
        }

        public void setLiked_at(int liked_at) {
            this.liked_at = liked_at;
        }
    }
}
