package licola.demo.com.huabandemo.Module.Follow;

import java.util.List;

import licola.demo.com.huabandemo.Entity.BoardPinsBean;

/**
 * Created by LiCola on  2016/04/04  19:53
 */
public class FollowBoardListBean {

    /**
     * filter : board:following:all
     * page : 1
     * following_count : 102
     * user_info : {"follower_count":114,"board_count":2,"pin_count":742}
     */

    private String filter;
    private int page;
    private int following_count;
    /**
     * follower_count : 114
     * board_count : 2
     * pin_count : 742
     */

    private UserInfoBean user_info;

    private List<BoardPinsBean> boards;

    public void setBoards(List<BoardPinsBean> pins) {
        this.boards = pins;
    }

    public List<BoardPinsBean> getBoards() {
        return boards;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoBean {
        private int follower_count;
        private int board_count;
        private int pin_count;

        public int getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public int getBoard_count() {
            return board_count;
        }

        public void setBoard_count(int board_count) {
            this.board_count = board_count;
        }

        public int getPin_count() {
            return pin_count;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }
    }
}
