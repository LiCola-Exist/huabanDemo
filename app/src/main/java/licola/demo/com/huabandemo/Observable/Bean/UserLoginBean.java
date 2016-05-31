package licola.demo.com.huabandemo.Observable.Bean;

/**
 * Created by LiCola on  2016/05/31  15:34
 */

public class UserLoginBean {

    private String userAccount;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    private String userPassword;

    public UserLoginBean(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }


}
