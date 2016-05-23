package licola.demo.com.huabandemo.Module.BoardDetail;

/**
 * Created by LiCola on  2015/12/12  17:43
 * 画板的简单信息bean
 */
public class BoardInfoBean {

    /**
     * board_id : 27444785
     * user_id : 12369502
     * title : 色彩
     * description :
     * category_id : null
     * seq : 8
     * pin_count : 3
     * follow_count : 0
     * like_count : 0
     * created_at : 1449763269
     * updated_at : 1449807929
     * deleting : 0
     * is_private : 0
     * extra : null
     * user : {"user_id":12369502,"username":"光腳丫♡唱歌","urlname":"yuanping6689","created_at":1394094442,"avatar":{"id":39378598,"farm":"farm1","bucket":"hbimg","key":"59338bbb5e34ad46dfd8e70034b510dc10e12f76120b-aP0y16","type":"image/jpeg","width":100,"height":100,"frames":1},"pin_count":628,"follower_count":9,"board_count":8,"boards_like_count":2,"commodity_count":18,"like_count":6,"creations_count":0,"following_count":104,"profile":{},"status":{"newbietask":0,"lr":1449842301,"invites":0,"share":"0"}}
     */

    private BoardEntity board;

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public static class BoardEntity {
        private int board_id;
        private int user_id;
        private String title;
        private String description;
        private Object category_id;
        private int seq;
        private int pin_count;
        private int follow_count;
        private int like_count;
        private int created_at;
        private int updated_at;
        private int deleting;
        private int is_private;
        private Object extra;
        /**
         * user_id : 12369502
         * username : 光腳丫♡唱歌
         * urlname : yuanping6689
         * created_at : 1394094442
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
         * status : {"newbietask":0,"lr":1449842301,"invites":0,"share":"0"}
         */

        private UserEntity user;

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCategory_id(Object category_id) {
            this.category_id = category_id;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public void setDeleting(int deleting) {
            this.deleting = deleting;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public int getBoard_id() {
            return board_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Object getCategory_id() {
            return category_id;
        }

        public int getSeq() {
            return seq;
        }

        public int getPin_count() {
            return pin_count;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public int getCreated_at() {
            return created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public int getDeleting() {
            return deleting;
        }

        public int getIs_private() {
            return is_private;
        }

        public Object getExtra() {
            return extra;
        }

        public UserEntity getUser() {
            return user;
        }

        public static class UserEntity {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
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
             * newbietask : 0
             * lr : 1449842301
             * invites : 0
             * share : 0
             */

            private StatusEntity status;

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setUrlname(String urlname) {
                this.urlname = urlname;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
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

            public void setStatus(StatusEntity status) {
                this.status = status;
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

            public int getCreated_at() {
                return created_at;
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

            public StatusEntity getStatus() {
                return status;
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

            public static class StatusEntity {
                private int newbietask;
                private int lr;
                private int invites;
                private String share;

                public void setNewbietask(int newbietask) {
                    this.newbietask = newbietask;
                }

                public void setLr(int lr) {
                    this.lr = lr;
                }

                public void setInvites(int invites) {
                    this.invites = invites;
                }

                public void setShare(String share) {
                    this.share = share;
                }

                public int getNewbietask() {
                    return newbietask;
                }

                public int getLr() {
                    return lr;
                }

                public int getInvites() {
                    return invites;
                }

                public String getShare() {
                    return share;
                }
            }
        }
    }
}
