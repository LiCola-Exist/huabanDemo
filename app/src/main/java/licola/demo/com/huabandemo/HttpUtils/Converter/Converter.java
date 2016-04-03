package licola.demo.com.huabandemo.HttpUtils.Converter;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import licola.demo.com.huabandemo.Util.Utils;

/**
 * Created by LiCola on  2015/12/05  13:48
 * 自定义Converter 转换器 用于解析String返回类型
 */
public class Converter extends retrofit.Converter.Factory {
    private static final String TAG = "Converter";

    public static Converter converter() {
        return new Converter();
    }

    @Override
    public retrofit.Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new StringResponseBodyConverter<>();
    }

    @Override
    public retrofit.Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return super.toRequestBody(type, annotations);
    }


    final class StringResponseBodyConverter<T> implements retrofit.Converter<ResponseBody, T>{


        public StringResponseBodyConverter() {

        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Reader reader = value.charStream();

            try {
                BufferedReader in=new BufferedReader(reader);
                StringBuffer buffer=new StringBuffer();
                String line="";
                while ((line=in.readLine())!=null){
                    buffer.append(line);
                }
                return (T) buffer.toString();
            } finally {
                Utils.closeQuietly(reader);
            }
        }
    }
}
