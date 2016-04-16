package licola.demo.com.huabandemo.HttpUtils.Converter;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.Utils;

/**
 * Created by LiCola on  2015/12/05  13:48
 * 自定义Converter 转换器 用于解析list返回类型时
 * 一般涉及到user用户信息的
 * avatar对象有时是String类型表示来自本地服务器之外的数据 有时是对象表示自身服务器的数据
 * 这里中正则表达式 统一为
 */
public class AvatarConverter extends retrofit.Converter.Factory {
    private static final String TAG = "AvatarConverter";

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static AvatarConverter create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static AvatarConverter create(Gson gson) {
        return new AvatarConverter(gson);
    }

    private final Gson gson;

    private AvatarConverter(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
        Logger.d();
    }

    @Override
    public retrofit.Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new StringResponseBodyConverter<>(gson, type);
    }

    @Override
    public retrofit.Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return super.toRequestBody(type, annotations);
    }


    final class StringResponseBodyConverter<T> implements retrofit.Converter<ResponseBody, T> {
        private final Gson gson;
        private final Type type;

        private String mSKey = "\"key\":\"([^\"]*)\"";
        private Pattern mPkey = Pattern.compile(mSKey);

        public StringResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
            Logger.d();
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
                    buffer.append(line);//写入buffer
                }
                result = buffer.toString();
            } finally {
                Utils.closeQuietly(reader);//记得关闭流
            }
            Logger.d();
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
            Pattern mPAvatar = Pattern.compile("\"avatar\":\\{([^\\}]*)\\}");
            Matcher mMAvatar = mPAvatar.matcher(result);
            while (mMAvatar.find()) {//如果找到 开始替换
                result = result.replaceFirst("\"avatar\":\\{([^\\}]*)\\}", getKey(mMAvatar.group()));
            }
            Logger.d();
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
}
