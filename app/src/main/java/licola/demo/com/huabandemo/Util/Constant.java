package licola.demo.com.huabandemo.Util;

/**
 * Created by LiCola on  2015/12/19  18:15
 * 常量类 用于保存字段的key值
 */
public class Constant {
    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final String Authorization="Authorization";

    //token information
    public static final String TOKENACCESS = "TokenAccess";
    public static final String TOKENREFRESH = "TokenRefresh";
    public static final String TOKENTYPE = "TokenType";
    public static final String TOKENEXPIRESIN = "TokeExpiresIn";

    //user information
    public static final String ISLOGIN = "isLogin";
    public static final String LOGINTIME = "loginTime";
    public static final String USERACCOUNT = "userAccount";
    public static final String USERPASSWORD = "userPassword";


    public static final String USERNAME = "userName";
    public static final String USERID = "userID";

    public static final String USERHEADKEY = "userHeadKey";

    public static final String USEREMAIL = "userEmail";

    //board info
    public static final String BOARDTILTARRAY="boardTitleArray";
    public static final String BOARDIDARRAY="boardIdArray";

    //,
    public static final String SEPARATECOMMA=",";

    //search
    public static final String HISTORYTEXT = "historyText";

    //http limit number
    public static final int LIMIT = 20;

    //RxView点击防止抖动时间间隔
    public static final long throttDuration=300;

    //用户喜欢操作的操作字段
    public static final String OPERATELIKE = "like";
    public static final String OPERATEUNLIKE = "unlike";

    //用户对画板的关注操作字段
    public static final String OPERATEFOLLOW = "follow";
    public static final String OPERATEUNFOLLOW = "unfollow";

    //获得用户画板列表详情的操作符
    public static final String OPERATEBOARDEXTRA="recommend_tags";
    public static final boolean OPERATECHECK=true;

    //删除画板的操作符
    public static final String OPERATEDELETEBOARD="DELETE";
}
