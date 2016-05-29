package licola.demo.com.huabandemo.Module.Follow;

import java.util.List;

import licola.demo.com.huabandemo.Entity.PinsMainEntity;

/**
 * Created by LiCola on  2016/04/03  16:07
 */
public class FollowPinsBean {

    private int followed_board;

    private List<PinsMainEntity> pins;

    public int getFollowed_board() {
        return followed_board;
    }

    public void setFollowed_board(int followed_board) {
        this.followed_board = followed_board;
    }


    public void setPins(List<PinsMainEntity> pins) {
        this.pins = pins;
    }

    public List<PinsMainEntity> getPins() {
        return pins;
    }
}
