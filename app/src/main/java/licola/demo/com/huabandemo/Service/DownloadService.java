package licola.demo.com.huabandemo.Service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.Utils;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by LiCola on  2016/05/13  22:02
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    private static final String KEYURL = "KEYURL";

    private String mUrl;
    private OnProgressResponseListener mlistener;


    public DownloadService() {
        super(TAG);
        Logger.d(TAG);
    }

    public static void launch(Activity activity, String url) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(KEYURL, url);
        activity.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Logger.d(intent.toString());
        Logger.d(Thread.currentThread().toString());
        mUrl = intent.getStringExtra(KEYURL);
        actionDownload(mUrl);
//        try {
//            Thread.sleep(2000);
//            Logger.d(mUrl);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG);
        mlistener = new OnProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                if (bytesRead == 0) {
                    Logger.d(Thread.currentThread().toString() + " bytesRead=" + bytesRead + " contentLength=" + contentLength + " done=" + done);
                }
                if (done) {
                    Logger.d(Thread.currentThread().toString() + " bytesRead=" + bytesRead + " contentLength=" + contentLength + " done=" + done);
                }
            }
        };
    }


    private void actionDownload(String mImageUrl) {
//        getSupportActionBar().invalidateOptionsMenu();
        RetrofitService.createDownloadService(mlistener)
                .httpDownImage(mImageUrl)
                .subscribeOn(Schedulers.io())//发布者的运行线程 联网操作属于IO操作
                .observeOn(Schedulers.io()) //订阅者的运行线程 在main线程中才能修改UI
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        Logger.d(responseBody.contentLength() + " " + responseBody.contentType().toString());
                        File file = getFile();
                        Logger.d(file.getPath());
                        String name = System.currentTimeMillis() + Utils.getPinsType(responseBody.contentType().toString());
                        writeResponseBodyToDisk(file, responseBody, name);
                    }
                });
    }

    @NonNull
    private File getFile() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "huabanDCIM");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }


    private boolean writeResponseBodyToDisk(File file, ResponseBody body, String name) {
        Logger.d(Thread.currentThread().toString());
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(file.getPath(), name);

//            Logger.d("isExternalStorageWritable=" + Utils.isExternalStorageWritable());
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    if (fileSizeDownloaded == 0) {
                        Logger.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    if (fileSizeDownloaded == fileSize) {
                        Logger.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                    }
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG);
        mlistener = null;
    }
}
