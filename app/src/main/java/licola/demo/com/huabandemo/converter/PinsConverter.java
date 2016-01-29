package licola.demo.com.huabandemo.converter;

import android.widget.Button;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
 * avatar对象有时为String类型 有时是对象
 */
public class PinsConverter extends retrofit.Converter.Factory {
    private static final String TAG="PinsConverter";
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static PinsConverter create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static PinsConverter create(Gson gson) {
        return new PinsConverter(gson);
    }

    private final Gson gson;

    private PinsConverter(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
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

        private String mSKey="\"key\":\"([^\"]*)\"";
        private Pattern mPkey=Pattern.compile(mSKey);

        public StringResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Reader reader = value.charStream();
            String result;
            try {
                BufferedReader in = new BufferedReader(reader);
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                result = buffer.toString();
            } finally {
                Utils.closeQuietly(reader);
            }
            return gson.fromJson(regexChange(result), type);
        }

        private String regexChange(String input) {
            String result=input;
            Pattern mPAvatar=Pattern.compile("\"avatar\":\\{([^\\}]*)\\}");
            Matcher mMAvatar=mPAvatar.matcher(result);
            while (mMAvatar.find()){
                result=result.replaceFirst("\"avatar\":\\{([^\\}]*)\\}",getKey(mMAvatar.group()));
            }
            return result;
        }

        private String getKey(String group) {
            Matcher matcher=mPkey.matcher(group);
            StringBuffer buffer=new StringBuffer();
//            Logger.d(TAG);
            buffer.append("\"avatar\":\"http://img.hb.aicdn.com/");
            while (matcher.find()){
                buffer.append(matcher.group(1));
            }
            buffer.append("\"");
            return buffer.toString();
        }
    }
}
