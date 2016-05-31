//package licola.demo.com.huabandemo.HttpUtils.Converter;
//
///**
// * Created by LiCola on  2016/05/31  17:34
// */
//
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//import retrofit2.Retrofit;
//
//
////**
////        * A {@linkplain Converter.Factory converter} which uses Gson for JSON.
////        * <p>
////* Because Gson is so flexible in the types it supports, this converter assumes that it can handle
////        * all types. If you are mixing JSON serialization with something else (such as protocol buffers),
////        * you must {@linkplain Retrofit.Builder#addConverterFactory(Converter.Factory) add this instance}
////        * last to allow the other converters a chance to see their types.
////        */
//public final class MyGsonConverter extends Converter.Factory {
//    /**
//     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
//     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
//     */
//    public static MyGsonConverter create() {
//        return create(new Gson());
//    }
//
//    /**
//     * Create an instance using {@code gson} for conversion. Encoding to JSON and
//     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
//     */
//    public static MyGsonConverter create(Gson gson) {
//        return new MyGsonConverter(gson);
//    }
//
//    private final Gson gson;
//
//    private MyGsonConverter(Gson gson) {
//        if (gson == null) throw new NullPointerException("gson == null");
//        this.gson = gson;
//    }
//
//    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
//                                                            Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new GsonResponseBodyConverter<>(gson, adapter);
////        return super.responseBodyConverter(type,annotations,retrofit);
//    }
//
//    @Override
//    public Converter<?, RequestBody> requestBodyConverter(Type type,
//                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//        return new GsonRequestBodyConverter<>(gson, adapter);
//    }
//}
