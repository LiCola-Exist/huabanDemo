package licola.demo.com.huabandemo.Module.Login;

/**
 * Created by LiCola on  2015/12/16  15:42
 */
public class TokenBean  {

    /**
     * access_token : edd1fc77-7d46-4e5c-92b9-3a84a290df0c
     * token_type : bearer
     * expires_in : 76632
     * refresh_token : db185b8b-c9ec-4553-a3fb-ff4646dadae1
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
