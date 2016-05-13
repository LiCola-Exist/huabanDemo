package licola.demo.com.huabandemo.HttpUtils.Converter;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.Utils;
import okhttp3.ResponseBody;

/**
 * Created by LiCola on  2016/04/17  18:23
 */
public class AvatarResponseBodyConverter<T> implements retrofit2.Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    private String mSKey = "\"key\":\"([^\"]*)\"";
    private Pattern mPkey = Pattern.compile(mSKey);

    public AvatarResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
        // Logger.d();
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        Reader reader = value.charStream();//取出字节流
        String result;
        try {
            BufferedReader in = new BufferedReader(reader);//读取
            StringBuffer buffer = new StringBuffer();//构造buffer对象用于拼接
            String line;
            while ((line = in.readLine()) != null) {//读行
                if (Thread.interrupted()) {
                    break;
                }
                buffer.append(line);//写入buffer
            }
            result = buffer.toString();
        } catch (InterruptedIOException e) {
            Logger.d(e.toString());
            result = "{}";
        } finally {
            Utils.closeQuietly(reader);//记得关闭流
        }
        // Logger.d();
        return gson.fromJson(regexChange(result), type);//返回解析后的对象
    }

    /**
     * 对输入的字符串进行处理
     *
     * @param input 传入的需要处理的字符串
     * @return
     */
    private String regexChange(String input) {
        String result = input;
        //匹配规则是当avatar是{}包装的对象

        if (!TextUtils.isEmpty(result)&&(!"{}".equals(result))) {
            Pattern mPAvatar = Pattern.compile("\"avatar\":\\{([^\\}]*)\\}");
            Matcher mMAvatar = mPAvatar.matcher(result);
            while (mMAvatar.find()) {//如果找到 开始替换
                result = result.replaceFirst("\"avatar\":\\{([^\\}]*)\\}", getKey(mMAvatar.group()));
            }
            // Logger.d();
        }
        return result;
    }

    /**
     * 取出关键值返回
     * 取出key值 统一拼接成 avatar:"key" 作为String返回
     *
     * @param group
     * @return
     */
    private String getKey(String group) {
        Matcher matcher = mPkey.matcher(group);
        StringBuffer buffer = new StringBuffer();
//            Logger.d(TAG);
//            buffer.append("\"avatar\":\"http://img.hb.aicdn.com/");
        buffer.append("\"avatar\":\"");//替换成不带http头的avatar
        while (matcher.find()) {
            buffer.append(matcher.group(1));
        }
        buffer.append("\"");//添加 " 做最后一个字符 完成拼接
        return buffer.toString();
    }
}
