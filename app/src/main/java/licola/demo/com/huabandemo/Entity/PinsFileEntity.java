package licola.demo.com.huabandemo.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiCola on  2016/05/05  21:27
 */

public class PinsFileEntity implements Parcelable {
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int width;
    private int height;
    private int frames;

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public String getFarm() {
        return farm;
    }

    public String getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrames() {
        return frames;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.farm);
        dest.writeString(this.bucket);
        dest.writeString(this.key);
        dest.writeString(this.type);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.frames);
    }

    public PinsFileEntity() {
    }

    protected PinsFileEntity(Parcel in) {
        this.farm = in.readString();
        this.bucket = in.readString();
        this.key = in.readString();
        this.type = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.frames = in.readInt();
    }

    public static final Parcelable.Creator<PinsFileEntity> CREATOR = new Parcelable.Creator<PinsFileEntity>() {
        @Override
        public PinsFileEntity createFromParcel(Parcel source) {
            return new PinsFileEntity(source);
        }

        @Override
        public PinsFileEntity[] newArray(int size) {
            return new PinsFileEntity[size];
        }
    };
}
