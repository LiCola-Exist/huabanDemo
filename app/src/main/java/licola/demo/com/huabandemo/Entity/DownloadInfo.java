package licola.demo.com.huabandemo.Entity;

/**
 * Created by LiCola on  2016/05/17  16:14
 * 下载信息的包装类
 */
public class DownloadInfo {
    //文件名
    public String fileName;

    //图片的下载地址
    public String mUrl;

    //当前下载的 任务id
    public int mWorkId;

    //当前下载图片的类型
    public String mMediaType;

    //当前下载图片的进度
    public int mProcess;

    //当前图片的下载状态 分为
    public String mState;

    public DownloadInfo(String fileName, String mMediaType, int mWorkId, String mUrl, String mState) {
        this.fileName = fileName;
        this.mMediaType = mMediaType;
        this.mWorkId = mWorkId;
        this.mUrl = mUrl;
        this.mState = mState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DownloadInfo{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", mUrl='").append(mUrl).append('\'');
        sb.append(", mWorkId=").append(mWorkId);
        sb.append(", mMediaType='").append(mMediaType).append('\'');
        sb.append(", mProcess=").append(mProcess);
        sb.append(", mState='").append(mState).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
