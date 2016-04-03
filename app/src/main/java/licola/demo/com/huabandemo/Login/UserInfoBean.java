package licola.demo.com.huabandemo.Login;

/**
 * Created by LiCola on  2015/12/12  17:40
 * 用户的简单信息
 */
public class UserInfoBean {

    /**
     * user_id : 12369502
     * username : 光腳丫♡唱歌
     * urlname : yuanping6689
     * avatar : {"id":39378598,"farm":"farm1","bucket":"hbimg","key":"59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16","type":"image/jpeg","width":100,"height":100,"frames":1}
     * pin_count : 628
     * follower_count : 9
     * board_count : 8
     * boards_like_count : 2
     * commodity_count : 18
     * like_count : 6
     * creations_count : 0
     * following_count : 104
     * profile : {}
     * bindings : {"qzone":{"bind_id":"qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576","user_id":12369502,"service_name":"qzone","auth_type":"oauth2","user_info":{"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"},"created_at":1394094415}}
     * user : {"user_id":12369502,"username":"光腳丫♡唱歌","urlname":"yuanping6689","avatar":{"id":39378598,"farm":"farm1","bucket":"hbimg","key":"59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16","type":"image/jpeg","width":100,"height":100,"frames":1},"pin_count":628,"follower_count":9,"board_count":8,"boards_like_count":2,"commodity_count":18,"like_count":6,"creations_count":0,"following_count":104,"profile":{},"bindings":{"qzone":{"bind_id":"qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576","user_id":12369502,"service_name":"qzone","auth_type":"oauth2","user_info":{"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"},"created_at":1394094415}}}
     */

    private int user_id;
    private String username;
    private String urlname;
    /**
     * id : 39378598
     * farm : farm1
     * bucket : hbimg
     * key : 59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16
     * type : image/jpeg
     * width : 100
     * height : 100
     * frames : 1
     */

    private AvatarEntity avatar;
    private int pin_count;
    private int follower_count;
    private int board_count;
    private int boards_like_count;
    private int commodity_count;
    private int like_count;
    private int creations_count;
    private int following_count;
    private ProfileEntity profile;
    /**
     * qzone : {"bind_id":"qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576","user_id":12369502,"service_name":"qzone","auth_type":"oauth2","user_info":{"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"},"created_at":1394094415}
     */

    private BindingsEntity bindings;
    /**
     * user_id : 12369502
     * username : 光腳丫♡唱歌
     * urlname : yuanping6689
     * avatar : {"id":39378598,"farm":"farm1","bucket":"hbimg","key":"59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16","type":"image/jpeg","width":100,"height":100,"frames":1}
     * pin_count : 628
     * follower_count : 9
     * board_count : 8
     * boards_like_count : 2
     * commodity_count : 18
     * like_count : 6
     * creations_count : 0
     * following_count : 104
     * profile : {}
     * bindings : {"qzone":{"bind_id":"qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576","user_id":12369502,"service_name":"qzone","auth_type":"oauth2","user_info":{"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"},"created_at":1394094415}}
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

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
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

    public int getPin_count() {
        return pin_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public int getFollowing_count() {
        return following_count;
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
        /**
         * bind_id : qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576
         * user_id : 12369502
         * service_name : qzone
         * auth_type : oauth2
         * user_info : {"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"}
         * created_at : 1394094415
         */

        private QzoneEntity qzone;

        public void setQzone(QzoneEntity qzone) {
            this.qzone = qzone;
        }

        public QzoneEntity getQzone() {
            return qzone;
        }

        public static class QzoneEntity {
            private String bind_id;
            private int user_id;
            private String service_name;
            private String auth_type;
            /**
             * id : D5EFC2FC23CFE6ABE03065F5D2C0C576
             * username : 光腳丫♡唱歌
             * avatar : http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100
             */

            private UserInfoEntity user_info;
            private int created_at;

            public void setBind_id(String bind_id) {
                this.bind_id = bind_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public void setAuth_type(String auth_type) {
                this.auth_type = auth_type;
            }

            public void setUser_info(UserInfoEntity user_info) {
                this.user_info = user_info;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public String getBind_id() {
                return bind_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public String getService_name() {
                return service_name;
            }

            public String getAuth_type() {
                return auth_type;
            }

            public UserInfoEntity getUser_info() {
                return user_info;
            }

            public int getCreated_at() {
                return created_at;
            }

            public static class UserInfoEntity {
                private String id;
                private String username;
                private String avatar;

                public void setId(String id) {
                    this.id = id;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getId() {
                    return id;
                }

                public String getUsername() {
                    return username;
                }

                public String getAvatar() {
                    return avatar;
                }
            }
        }
    }

    public static class UserEntity {
        private int user_id;
        private String username;
        private String urlname;
        /**
         * id : 39378598
         * farm : farm1
         * bucket : hbimg
         * key : 59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16
         * type : image/jpeg
         * width : 100
         * height : 100
         * frames : 1
         */

        private AvatarEntity avatar;
        private int pin_count;
        private int follower_count;
        private int board_count;
        private int boards_like_count;
        private int commodity_count;
        private int like_count;
        private int creations_count;
        private int following_count;
        private ProfileEntity profile;
        /**
         * qzone : {"bind_id":"qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576","user_id":12369502,"service_name":"qzone","auth_type":"oauth2","user_info":{"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"},"created_at":1394094415}
         */

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

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public void setBoard_count(int board_count) {
            this.board_count = board_count;
        }

        public void setBoards_like_count(int boards_like_count) {
            this.boards_like_count = boards_like_count;
        }

        public void setCommodity_count(int commodity_count) {
            this.commodity_count = commodity_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setCreations_count(int creations_count) {
            this.creations_count = creations_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
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

        public int getPin_count() {
            return pin_count;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public int getBoard_count() {
            return board_count;
        }

        public int getBoards_like_count() {
            return boards_like_count;
        }

        public int getCommodity_count() {
            return commodity_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public int getCreations_count() {
            return creations_count;
        }

        public int getFollowing_count() {
            return following_count;
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
            /**
             * bind_id : qzone-D5EFC2FC23CFE6ABE03065F5D2C0C576
             * user_id : 12369502
             * service_name : qzone
             * auth_type : oauth2
             * user_info : {"id":"D5EFC2FC23CFE6ABE03065F5D2C0C576","username":"光腳丫♡唱歌","avatar":"http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100"}
             * created_at : 1394094415
             */

            private QzoneEntity qzone;

            public void setQzone(QzoneEntity qzone) {
                this.qzone = qzone;
            }

            public QzoneEntity getQzone() {
                return qzone;
            }

            public static class QzoneEntity {
                private String bind_id;
                private int user_id;
                private String service_name;
                private String auth_type;
                /**
                 * id : D5EFC2FC23CFE6ABE03065F5D2C0C576
                 * username : 光腳丫♡唱歌
                 * avatar : http://q.qlogo.cn/qqapp/100240394/D5EFC2FC23CFE6ABE03065F5D2C0C576/100
                 */

                private UserInfoEntity user_info;
                private int created_at;

                public void setBind_id(String bind_id) {
                    this.bind_id = bind_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public void setService_name(String service_name) {
                    this.service_name = service_name;
                }

                public void setAuth_type(String auth_type) {
                    this.auth_type = auth_type;
                }

                public void setUser_info(UserInfoEntity user_info) {
                    this.user_info = user_info;
                }

                public void setCreated_at(int created_at) {
                    this.created_at = created_at;
                }

                public String getBind_id() {
                    return bind_id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public String getService_name() {
                    return service_name;
                }

                public String getAuth_type() {
                    return auth_type;
                }

                public UserInfoEntity getUser_info() {
                    return user_info;
                }

                public int getCreated_at() {
                    return created_at;
                }

                public static class UserInfoEntity {
                    private String id;
                    private String username;
                    private String avatar;

                    public void setId(String id) {
                        this.id = id;
                    }

                    public void setUsername(String username) {
                        this.username = username;
                    }

                    public void setAvatar(String avatar) {
                        this.avatar = avatar;
                    }

                    public String getId() {
                        return id;
                    }

                    public String getUsername() {
                        return username;
                    }

                    public String getAvatar() {
                        return avatar;
                    }
                }
            }
        }
    }
}
