package licola.demo.com.huabandemo.User;

import android.app.Application;

import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.SPUtils;

/**
 * Created by LiCola on  2016/05/28  17:56
 */

public class UserSingleton {

    private String mAuthorization;

    private Boolean isLogin;

    private volatile static UserSingleton instance=new UserSingleton();

    private UserSingleton(){

    }

    public static UserSingleton getInstance(){
        return instance;
    }

    public String getAuthorization() {
        if (isLogin){
            if (mAuthorization==null){

            }
            return mAuthorization;
        }

        return mAuthorization;
    }

    public void setAuthorization(String mAuthorization) {

        this.mAuthorization = mAuthorization;
    }

    public boolean isLogin(Application mContext) {
        if (isLogin==null){
            isLogin = (boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        }
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
