package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import licola.demo.com.huabandemo.Base.HuaBanApplication;
import licola.demo.com.huabandemo.Entity.BoardItemInfoBean;
import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.Observable.Bean.UserFirstLoginBean;

/**
 * Created by LiCola on  2016/04/15  21:10
 */
public class SPBuild {
    private final SharedPreferences.Editor editor;

    public SPBuild(Context context) {
        this.editor = context.getSharedPreferences(SPUtils.FILE_NAME, SPUtils.MODE).edit();
    }

    public SPBuild addData(String key, Object object) {
        SPUtils.putAdd(editor, key, object);
        return this;
    }

    public void build() {
        this.editor.apply();
    }


    public static void saveUserInfo(UserFirstLoginBean loginBean) {
        SPUtils.clear(HuaBanApplication.getInstance());

        //构造两个StringBuilder对象 拼接用逗号分隔 写入 SharedPreferences
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();

        List<BoardItemInfoBean> list = loginBean.getmBoardListInfoBean().getBoards();
        TokenBean tokenBean = loginBean.getmTokenBean();
        UserMeAndOtherBean userBean = loginBean.getmUserBean();

        //循环构造 以逗号， 为分隔符的 画板名称和 id 字符串
        for (int i = 0, size = list.size(); i < size; i++) {
            boardTitle.append(list.get(i).getTitle());
            boardId.append(list.get(i).getBoard_id());

            if (i != size - 1) {
                boardTitle.append(Constant.SEPARATECOMMA);
                boardId.append(Constant.SEPARATECOMMA);
            }
        }

        //保存数据
        new SPBuild(HuaBanApplication.getInstance())
                .addData(Constant.ISLOGIN, Boolean.TRUE)//登陆志位
                .addData(Constant.LOGINTIME, System.currentTimeMillis())//登陆时间
                .addData(Constant.USERACCOUNT, loginBean.getUserAccount())//账号
                .addData(Constant.USERPASSWORD, loginBean.getUserPassword())//密码
                //token 信息
                .addData(Constant.TOKENACCESS, tokenBean.getAccess_token())
                .addData(Constant.TOKENREFRESH, tokenBean.getRefresh_token())
                .addData(Constant.TOKENTYPE, tokenBean.getToken_type())
                .addData(Constant.TOKENEXPIRESIN, tokenBean.getExpires_in())
                //用户个人信息
                .addData(Constant.USERNAME, userBean.getUsername())
                .addData(Constant.USERID, String.valueOf(userBean.getUser_id()))
                .addData(Constant.USERHEADKEY, userBean.getAvatar())
                .addData(Constant.USEREMAIL, userBean.getEmail())
                //用户画板信息 已经拼接好的
                .addData(Constant.BOARDTILTARRAY, boardTitle.toString())
                .addData(Constant.BOARDIDARRAY, boardId.toString())
                .build();

//


    }

}
