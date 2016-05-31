package licola.demo.com.huabandemo.Observable;

import android.content.Context;

import licola.demo.com.huabandemo.Base.HuaBanApplication;
import licola.demo.com.huabandemo.Entity.BoardListInfoBean;
import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.Module.Login.UserMeAndOtherBean;
import licola.demo.com.huabandemo.Observable.Bean.UserLoginBean;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPBuild;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.TimeUtils;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by LiCola on  2016/05/31  13:14
 * 有关SharedPreference读写操作的 生成数据流 帮助类
 * 集中有关数据的操作
 */

public class SPHelper {


    private static final int mTimeDifference = TimeUtils.HOUR;
//    private static final int mTimeDifference = 0;

    //防止实例
    private SPHelper() {

    }

    public static Observable<Boolean> getLoginState() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Logger.d("getLoginState");
                subscriber.onNext((Boolean) SPUtils.get(HuaBanApplication.getInstance(), Constant.ISLOGIN, false));
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Boolean> getLoginDtime() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Long lastTime = (Long) SPUtils.get(HuaBanApplication.getInstance(), Constant.LOGINTIME, 0L);
                long dTime = System.currentTimeMillis() - lastTime;
                Logger.d("getLoginDtime " + "dTime=" + dTime + " default" + mTimeDifference);
//                boolean isDtime=dTime>mTimeDifference;
                subscriber.onNext(dTime > mTimeDifference);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<UserLoginBean> getUserLoginInfo() {
        return Observable.create(new Observable.OnSubscribe<UserLoginBean>() {
            @Override
            public void call(Subscriber<? super UserLoginBean> subscriber) {
                Logger.d("getUserLoginInfo");
                String userAccount = (String) SPUtils.get(HuaBanApplication.getInstance(), Constant.USERACCOUNT, "");
                String userPassword = (String) SPUtils.get(HuaBanApplication.getInstance(), Constant.USERPASSWORD, "");

                subscriber.onNext(new UserLoginBean(userAccount, userPassword));
                subscriber.onCompleted();
            }
        });
    }


    public static Func1<TokenBean, Void> funcSaveUserLogin() {
        return new Func1<TokenBean, Void>() {
            @Override
            public Void call(TokenBean tokenBean) {
                Logger.d("funcSaveUserLogin");
                new SPBuild(HuaBanApplication.getInstance())
                        .addData(Constant.LOGINTIME, System.currentTimeMillis())
                        .addData(Constant.TOKENACCESS, tokenBean.getAccess_token())
                        .addData(Constant.TOKENTYPE, tokenBean.getToken_type())
                        .build();

                return null;
            }
        };
    }


}
