package licola.demo.com.huabandemo.Base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

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
        refWatcher = LeakCanary.install(this);//初始化 内存检测工具
        Fresco.initialize(this);//初始化Fresco图片加载框架

        CrashReport.initCrashReport(getApplicationContext(), "900037004", false);


        //chrome 调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }


    /**
     * 获得内存监视器 监视任何对象
     * 使用 refWatcher.watch(object);
     *
     * @return 全局的refWatcher
     */
    public static RefWatcher getRefwatcher(Context context) {
        HuaBanApplication huaBanApplication = (HuaBanApplication) context.getApplicationContext();
        return huaBanApplication.refWatcher;
    }

}
