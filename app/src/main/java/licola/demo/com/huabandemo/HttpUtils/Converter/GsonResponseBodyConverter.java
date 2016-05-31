//package licola.demo.com.huabandemo.HttpUtils.Converter;
//
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//
//import java.io.IOException;
//
//import licola.demo.com.huabandemo.Util.Logger;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//
///**
// * Created by LiCola on  2016/05/31  17:36
// */
//
//public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
//    private final Gson gson;
//    private final TypeAdapter<T> adapter;
//
//    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
//        this.gson = gson;
//        this.adapter = adapter;
//    }
//
//    @Override
//    public T convert(ResponseBody value) throws IOException {
//
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            return adapter.read(jsonReader);
//        } catch (Exception e) {
//            Logger.d(e.toString());
//        } finally {
//            value.close();
//        }
//        return null;
//    }
//}