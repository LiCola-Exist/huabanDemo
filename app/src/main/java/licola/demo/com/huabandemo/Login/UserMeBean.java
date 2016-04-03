package licola.demo.com.huabandemo.Login;

/**
 * Created by LiCola on  2015/12/16  16:51
 */
public class UserMeBean {

    /**
     * user_id : 15246080
     * username : 李_可乐
     * urlname : qdmnp8ibc7
     * avatar : {"id":65650502,"farm":"farm1","bucket":"hbimg","key":"1c1c24c21325c8e45446467d717de277ea7e1842f7b2-04hX8c","type":"image/jpeg","width":640,"height":640,"frames":1}
     * email : 361401376@qq.com
     * created_at : 1410013135
     * like_count : 4
     * boards_like_count : 0
     * following_count : 100
     * commodity_count : 1
     * board_count : 1
     * follower_count : 113
     * creations_count : 0
     * pin_count : 726
     * profile : {"location":"金华"}
     * bindings : {}
     * user : {"user_id":15246080,"username":"李_可乐","urlname":"qdmnp8ibc7","avatar":{"id":65650502,"farm":"farm1","bucket":"hbimg","key":"1c1c24c21325c8e45446467d717de277ea7e1842f7b2-04hX8c","type":"image/jpeg","width":640,"height":640,"frames":1},"email":"361401376@qq.com","created_at":1410013135,"like_count":4,"boards_like_count":0,"following_count":100,"commodity_count":1,"board_count":1,"follower_count":113,"creations_count":0,"pin_count":726,"profile":{"location":"金华"},"bindings":{}}
     */

    private int user_id;
    private String username;
    private String urlname;
    /**
     * id : 65650502
     * farm : farm1
     * bucket : hbimg
     * key : 1c1c24c21325c8e45446467d717de277ea7e1842f7b2-04hX8c
     * type : image/jpeg
     * width : 640
     * height : 640
     * frames : 1
     */

    private AvatarEntity avatar;
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
    /**
     * location : 金华
     */

    private ProfileEntity profile;
    private BindingsEntity bindings;
    /**
     * user_id : 15246080
     * username : 李_可乐
     * urlname : qdmnp8ibc7
     * avatar : {"id":65650502,"farm":"farm1","bucket":"hbimg","key":"1c1c24c21325c8e45446467d717de277ea7e1842f7b2-04hX8c","type":"image/jpeg","width":640,"height":640,"frames":1}
     * email : 361401376@qq.com
     * created_at : 1410013135
     * like_count : 4
     * boards_like_count : 0
     * following_count : 100
     * commodity_count : 1
     * board_count : 1
     * follower_count : 113
     * creations_count : 0
     * pin_count : 726
     * profile : {"location":"金华"}
     * bindings : {}
     */

    private UserEntity user;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public void setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public void setBindings(BindingsEntity bindings) {
        this.bindings = bindings;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrlname() {
        return urlname;
    }

    public AvatarEntity getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public int getCreated_at() {
        return created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public int getPin_count() {
        return pin_count;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public BindingsEntity getBindings() {
        return bindings;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class AvatarEntity {
        private int id;
        private String farm;
        private String bucket;
        private String key;
        private String type;
        private int width;
        private int height;
        private int frames;

        public void setId(int id) {
            this.id = id;
        }

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

        public int getId() {
            return id;
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
    }

    public static class ProfileEntity {
    }

    public static class BindingsEntity {
    }

    public static class UserEntity {
        private int user_id;
        private String username;
        private String urlname;
        /**
         * id : 65650502
         * farm : farm1
         * bucket : hbimg
         * key : 1c1c24c21325c8e45446467d717de277ea7e1842f7b2-04hX8c
         * type : image/jpeg
         * width : 640
         * height : 640
         * frames : 1
         */

        private AvatarEntity avatar;
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
        /**
         * location : 金华
         */

        private ProfileEntity profile;
        private BindingsEntity bindings;

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setUrlname(String urlname) {
            this.urlname = urlname;
        }

        public void setAvatar(AvatarEntity avatar) {
            this.avatar = avatar;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setBoards_like_count(int boards_like_count) {
            this.boards_like_count = boards_like_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
        }

        public void setCommodity_count(int commodity_count) {
            this.commodity_count = commodity_count;
        }

        public void setBoard_count(int board_count) {
            this.board_count = board_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public void setCreations_count(int creations_count) {
            this.creations_count = creations_count;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public void setProfile(ProfileEntity profile) {
            this.profile = profile;
        }

        public void setBindings(BindingsEntity bindings) {
            this.bindings = bindings;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUsername() {
            return username;
        }

        public String getUrlname() {
            return urlname;
        }

        public AvatarEntity getAvatar() {
            return avatar;
        }

        public String getEmail() {
            return email;
        }

        public int getCreated_at() {
            return created_at;
        }

        public int getLike_count() {
            return like_count;
        }

        public int getBoards_like_count() {
            return boards_like_count;
        }

        public int getFollowing_count() {
            return following_count;
        }

        public int getCommodity_count() {
            return commodity_count;
        }

        public int getBoard_count() {
            return board_count;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public int getCreations_count() {
            return creations_count;
        }

        public int getPin_count() {
            return pin_count;
        }

        public ProfileEntity getProfile() {
            return profile;
        }

        public BindingsEntity getBindings() {
            return bindings;
        }

        public static class AvatarEntity {
            private int id;
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int width;
            private int height;
            private int frames;

            public void setId(int id) {
                this.id = id;
            }

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

            public int getId() {
                return id;
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
        }

        public static class ProfileEntity {
        }

        public static class BindingsEntity {
        }
    }
}
