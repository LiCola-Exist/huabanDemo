package licola.demo.com.huabandemo.HttpUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by LiCola on  2016/05/23  19:41
 * 在这里我们需要通过Handler对象进行发送和处理消息。
 * 于是定义了两个抽象方法sendMessage和handleMessage。
 * 之后又定义了一个抽象方法onProgress来处理下载进度的显示，
 * 而这个onProgress则是我们需要在ui线程进行调用。
 * 最后创建了一个继承自Handler的ResponseHandler内部类。
 * 为了避免内存泄露我们使用static关键字。
 */

public abstract class ProgressHandler {
    protected abstract void sendMessage(ProgressBean progressBean);

    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler{

        private ProgressHandler mProgressHandler;
        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }
}
