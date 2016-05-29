package licola.demo.com.huabandemo.Module.Login;

/**
 * Created by LiCola on  2015/12/16  16:51
 */
public class UserMeAndOtherBean {

    /**
     * user_id : 15246080
     * username : 李可乐MD
     * urlname : qdmnp8ibc7
     * avatar : 图片地址
     * email : 361401376@qq.com
     * created_at : 1410013135
     * like_count : 12
     * boards_like_count : 0
     * following_count : 103
     * commodity_count : 1
     * board_count : 2
     * follower_count : 114
     * creations_count : 0
     * pin_count : 743
     * profile : {"location":"金华","sex":"1","birthday":"1992-6-10","job":"工程师","url":"","about":"Android开发工程师"}
     */

    private int user_id;
    private String username;
    private String urlname;
    private String avatar;
    private String email;
    private int created_at;
    private int like_count;
    private int boards_like_count;
    private int following_count;
    private int commodity_count;
    private int board_count;
    private int follower_count;
    private int creations_count;
    private int pin_count;

    //该用户是否已经关注 关注为1 否则没有对应的网络字段 int默认值为0
    private int following;

    /**
     * location : 金华
     * sex : 1
     * birthday : 1992-6-10
     * job : 工程师
     * url :
     * about : Android开发工程师
     */

    private ProfileBean profile;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public static class ProfileBean {
        private String location;
        private String sex;
        private String birthday;
        private String job;
        private String url;
        private String about;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }
}
