package licola.demo.com.huabandemo.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LiCola on  2016/04/15  21:10
 */
public class SPBuild {
    private final SharedPreferences.Editor editor;

    public SPBuild(Context context) {
        this.editor=context.getSharedPreferences(SPUtils.FILE_NAME,SPUtils.MODE).edit();
    }

    public SPBuild addData(String key,Object object){
        SPUtils.putAdd(editor,key,object);
        return this;
    }

    public void build(){
        this.editor.apply();
    }
}
