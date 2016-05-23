package licola.demo.com.huabandemo.Entity;

import java.io.File;

/**
 * Created by LiCola on  2016/05/17  16:14
 * 下载信息的包装类
 */
public class DownloadInfo {
    public static final String StateStart="加入下载队列";
    public static final String StateLoading="正在下载";
    public static final String StateComplete="下载完成";

    //文件名
    public String fileName;

    //图片的下载地址
    public String mUrl;

    //当前下载的 任务id
    public int mWorkId;

    //下载后形成的文件
    public File mFile;

    //当前下载图片的类型
    public String mMediaType;

    //当前下载图片的进度
    public int mProcess;

    //当前图片的下载状态 分为
    public String mState;


//    public enum  State {
//        Start/**加入下载队列*/
//        , Loading/**加载到最底了*/
//        , Complete/**完成*/
//        , NetWorkError/**网络异常*/
//    }

    public DownloadInfo(String FileName, String MediaType, int WorkId, String Url,String State) {
        this.fileName = FileName;
        this.mMediaType = MediaType;
        this.mWorkId = WorkId;
        this.mUrl = Url;
        this.mState=State;
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
