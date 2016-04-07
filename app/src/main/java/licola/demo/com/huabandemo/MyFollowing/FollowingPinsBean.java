package licola.demo.com.huabandemo.MyFollowing;

import java.util.List;

import licola.demo.com.huabandemo.Bean.PinsAndUserEntity;

/**
 * Created by LiCola on  2016/04/03  16:07
 */
public class FollowingPinsBean {

    private int followed_board;

    private List<PinsAndUserEntity> pins;

    public int getFollowed_board() {
        return followed_board;
    }

    public void setFollowed_board(int followed_board) {
        this.followed_board = followed_board;
    }


    public void setPins(List<PinsAndUserEntity> pins) {
        this.pins = pins;
    }

    public List<PinsAndUserEntity> getPins() {
        return pins;
    }
}
