package licola.demo.com.huabandemo.Service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import licola.demo.com.huabandemo.API.OnProgressResponseListener;
import licola.demo.com.huabandemo.Entity.DownloadInfo;
import licola.demo.com.huabandemo.HttpUtils.RetrofitService;
import licola.demo.com.huabandemo.Util.FileUtils;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.NotificationUtils;
import licola.demo.com.huabandemo.Util.Utils;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by LiCola on  2016/05/13  22:02
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";
    private static final String KEYURL = "KeyUrl";
    private static final String KEYTYPE = "KeyType";

    //下载操作不频繁 可以当做类变量 使用时候再创建
    private NotificationManager mNontificationManager;

    private OnProgressResponseListener mListener;

    //handler 主要实现计时器 每1000毫秒更新一次 下载进度
    private Handler mHandler;

    private long mDelayedTime = 1000;

    private Map<Integer, DownloadInfo> mMap = new HashMap<>();

    private int mWorkId;

    private DownloadInfo mDownloadInfo;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Logger.d(Thread.currentThread().toString() + " mProcess=" + mDownloadInfo.mProcess);
            Notification notification =
                    NotificationUtils.showProcessNotification(getApplication(), mDownloadInfo.fileName, mDownloadInfo.mProcess);
            mNontificationManager.notify(mWorkId, notification);
            mHandler.postDelayed(this, mDelayedTime);
        }
    };

    public DownloadService() {
        super(TAG);
        Logger.d(TAG);
    }

    public static void launch(Activity activity, String url, String type) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(KEYURL, url);
        intent.putExtra(KEYTYPE, type);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG);
        String url = intent.getStringExtra(KEYURL);
        String type = intent.getStringExtra(KEYTYPE);
        Logger.d(url + " " + Thread.currentThread().toString());
        int mWorkId = url.hashCode();

        if (!mMap.containsKey(mWorkId)) {
            //任务队列中不存在 构造一个DownloadInfo实例放入
            Logger.d("map is not containsKey");
            //根据时间和图片类型 拼接文件名
            String filename = String.valueOf(System.currentTimeMillis()) + Utils.getPinsType(type);
            DownloadInfo mDownloadInfo = new DownloadInfo(filename, type, mWorkId, url, "加入下载队列等待下载");
            mMap.put(mWorkId, mDownloadInfo);
            Notification notification = NotificationUtils.showNotification(getApplication(), mDownloadInfo.fileName, mDownloadInfo.mState);
            mNontificationManager.notify(mWorkId, notification);
        } else {
            Logger.d("this url is exist ");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //处理队列中的消息 顺序调用 处理完一个再处理下一个
        String url = intent.getStringExtra(KEYURL);
        Logger.d(url + " " + Thread.currentThread().toString());
        mWorkId = url.hashCode();

        if (mMap.containsKey(mWorkId)) {
            mDownloadInfo = mMap.get(mWorkId);
            actionDownload(url, mDownloadInfo);
        }


    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG);
        //获取通知管理器
        mNontificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mListener = new OnProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
//                Logger.d(Thread.currentThread().toString() + " bytesRead=" + bytesRead + " contentLength=" + contentLength + " done=" + done);
                mDownloadInfo.mProcess = (int) (bytesRead * 100 / contentLength);
            }
        };
        Looper looper = Looper.myLooper();
        mHandler = new Handler(looper);
    }

    /**
     * 阻塞当前线程的下载方法
     *
     * @param mImageUrl
     */
    private void actionDownload(String mImageUrl, DownloadInfo mDownloadInfo) {

        RetrofitService.createDownloadService(mListener)
                .httpDownImage(mImageUrl)
                //IntentService 有内部变量HandlerThread 运行在子线程中 所以不用切换线程
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        Logger.d(responseBody.contentLength() + " " + responseBody.contentType().toString());
                        mDownloadInfo.mMediaType = responseBody.contentType().toString();
                        File file = FileUtils.getDirsFile();//构造目录文件
                        Logger.d(file.getPath());

                        return FileUtils.writeResponseBodyToDisk(file, responseBody, mDownloadInfo.fileName);
                    }
                })
                .filter(file -> file != null)
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mDownloadInfo.mState = "开始下载";
//                        Notification notification =
//                                NotificationUtils.showNotification(getApplication(), mDownloadInfo.fileName, mDownloadInfo.mState);
//                        mNontificationManager.notify(workId, notification);
                        mHandler.postDelayed(mRunnable, mDelayedTime);
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(File file) {
                        Logger.d(file.getAbsolutePath());
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        mHandler.removeCallbacks(mRunnable);
                        mDownloadInfo.mState = "下载完成";
                        Logger.d(mDownloadInfo.toString());
                        Notification notification =
                                NotificationUtils.showIntentNotification(
                                        getApplication(), file, mDownloadInfo.mMediaType, mDownloadInfo.fileName, mDownloadInfo.mState
                                );
                        mNontificationManager.notify(mWorkId, notification);

                    }
                });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Logger.d(TAG);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG);
        mListener = null;
        mNontificationManager = null;
    }

}
