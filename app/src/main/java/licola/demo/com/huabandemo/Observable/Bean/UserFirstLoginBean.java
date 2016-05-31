package licola.demo.com.huabandemo.Observable.Bean;

import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;

/**
 * Created by LiCola on  2016/05/31  16:13
 */

public class UserFirstLoginBean extends UserLoginBean{
    private TokenBean mTokenBean;
    private UserMeAndOtherBean mUserBean;
    private BoardListInfoBean mBoardListInfoBean;


    public UserFirstLoginBean(String userAccount, String userPassword, TokenBean mTokenBean) {
        super(userAccount, userPassword);
        this.mTokenBean = mTokenBean;
    }

    public BoardListInfoBean getmBoardListInfoBean() {
        return mBoardListInfoBean;
    }

    public void setmBoardListInfoBean(BoardListInfoBean mBoardListInfoBean) {
        this.mBoardListInfoBean = mBoardListInfoBean;
    }

    public TokenBean getmTokenBean() {
        return mTokenBean;
    }

    public void setmTokenBean(TokenBean mTokenBean) {
        this.mTokenBean = mTokenBean;
    }

    public UserMeAndOtherBean getmUserBean() {
        return mUserBean;
    }

    public void setmUserBean(UserMeAndOtherBean mUserBean) {
        this.mUserBean = mUserBean;
    }
}
