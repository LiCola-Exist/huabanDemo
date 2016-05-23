package licola.demo.com.huabandemo.HttpUtils;

import android.os.Looper;
import android.os.Message;

/**
 * Created by LiCola on  2016/05/23  19:52
 */

public abstract class DownloadProgressHandler extends ProgressHandler {
    private static final int DOWNLOAD_PROGRESS = 1;
    protected ResponseHandler mHandler=new ResponseHandler(this, Looper.getMainLooper());

    @Override
    protected void sendMessage(ProgressBean progressBean) {
        mHandler.obtainMessage(DOWNLOAD_PROGRESS,progressBean).sendToTarget();
    }

    @Override
    protected void handleMessage(Message message){
        switch (message.what){
            case DOWNLOAD_PROGRESS:
                ProgressBean progressBean = (ProgressBean)message.obj;
                onProgress(progressBean.getBytesRead(),progressBean.getContentLength(),progressBean.isDone());
        }
    }



}
