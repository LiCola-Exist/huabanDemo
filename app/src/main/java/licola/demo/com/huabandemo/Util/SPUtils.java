package licola.demo.com.huabandemo.Util;

import java.util.Map;
import java.util.Set;

import android.content.ContentProvider;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

public class SPUtils {
    public SPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存在手机里面的文件名 和读写模式
     */
    public static final String FILE_NAME = "share_data";
    public static final int MODE = Context.MODE_PRIVATE;


    /**
     * 异步提交方法
     * @param context
     * @param key
     * @param object
     */
    public static void putApply(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);

        SharedPreferences.Editor editor = sp.edit();
        judgePutDataType(key, object, editor);
        editor.apply();
    }

    /**
     * 同步提交方法
     * @param context
     * @param key
     * @param object
     * @return
     */
    public static boolean putCommit(Context context, String key, Object object){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);
        SharedPreferences.Editor editor = sp.edit();
        judgePutDataType(key, object, editor);
        return editor.commit();
    }

    /**
     * 根据不同类型 使用不同的写入方法
     * @param key
     * @param object
     * @param editor
     */
    private static void judgePutDataType(String key, Object object, SharedPreferences.Editor editor) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    public static void putAdd(SharedPreferences.Editor editor, String key, Object object) {
        judgePutDataType(key, object, editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                MODE);
        return sp.getAll();
    }

}