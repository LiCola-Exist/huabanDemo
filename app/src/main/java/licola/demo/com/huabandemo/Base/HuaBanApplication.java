package licola.demo.com.huabandemo.Base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by LiCola on  2015/12/02  13:25
 */
public class HuaBanApplication extends Application {
    private static final String TAG = "HuaBanApplication";

    private static HuaBanApplication instance;

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(HuaBanApplication.getInstance());//初始化Fresco图片加载框架
        refWatcher= LeakCanary.install(this);//初始化 内存检测工具

    }

    public static HuaBanApplication getInstance() {
        if (null == instance) {
            instance = new HuaBanApplication();
        }
        return instance;
    }

    /**
     * 获得内存监视器 监视任何对象
     *  使用 refWatcher.watch(object);
     * @return 全局的refWatcher
     */
   public RefWatcher getRefwatcher(){
       return refWatcher;
   }

}
