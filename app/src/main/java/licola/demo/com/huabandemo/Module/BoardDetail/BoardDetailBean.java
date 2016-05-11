package licola.demo.com.huabandemo.Module.BoardDetail;

import java.util.List;

/**
 * Created by LiCola on  2016/03/29  16:50
 * 画板的详情介绍 已经将avatar 改为String
 */
public class BoardDetailBean {

    /**
     * board_id : 3514299
     * user_id : 6285104
     * title : 可爱的点心们
     * description : 小sai采集必须精品~~\(≧▽≦)/~
     * category_id : food_drink
     * seq : 22
     * pin_count : 2018
     * follow_count : 1651
     * like_count : 10
     * created_at : 1361415078
     * updated_at : 1459240602
     * deleting : 0
     * is_private : 0
     * extra : null
     * user : {"user_id":6285104,"username":"sai叔（saimme）","urlname":"sai370419748","created_at":1355974630,"avatar":{"id":8607108,"farm":"farm1","bucket":"hbimg","key":"d556c1e555a56783df97f77fa9b2b4a0a9906040359ec-AzkrbO","type":"image/jpeg","width":600,"height":906,"frames":1},"pin_count":27864,"boards_like_count":381,"follower_count":19875,"board_count":73,"commodity_count":42,"following_count":1052,"creations_count":0,"like_count":4881,"explore_following_count":5,"profile":{"location":"上海","sex":"0","birthday":"","job":"2货 无脑 射基师","url":"","about":"sai叔一个爱设计的吃货女汉子 ，欢迎关注我的公众号sai叔的日常 每周推出美食和设计相关的内容ˊ_&gt;ˋ","show_verification":"weibo"},"status":{"newbietask":0,"lr":1459241229,"invites":0,"share":"0","default_board":3329094,"notificationsRead":[23],"past_shiji_guide":1,"past_design_welcome":1,"hide_douban":1,"douban_unsearchable":1}}
     * following : false
     */

    private BoardBean board;

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public static class BoardBean {
        private int board_id;
        private int user_id;
        private String title;
        private String description;
        private String category_id;
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
         * user_id : 6285104
         * username : sai叔（saimme）
         * urlname : sai370419748
         * created_at : 1355974630
         * avatar : {"id":8607108,"farm":"farm1","bucket":"hbimg","key":"d556c1e555a56783df97f77fa9b2b4a0a9906040359ec-AzkrbO","type":"image/jpeg","width":600,"height":906,"frames":1}
         * pin_count : 27864
         * boards_like_count : 381
         * follower_count : 19875
         * board_count : 73
         * commodity_count : 42
         * following_count : 1052
         * creations_count : 0
         * like_count : 4881
         * explore_following_count : 5
         * profile : {"location":"上海","sex":"0","birthday":"","job":"2货 无脑 射基师","url":"","about":"sai叔一个爱设计的吃货女汉子 ，欢迎关注我的公众号sai叔的日常 每周推出美食和设计相关的内容ˊ_&gt;ˋ","show_verification":"weibo"}
         * status : {"newbietask":0,"lr":1459241229,"invites":0,"share":"0","default_board":3329094,"notificationsRead":[23],"past_shiji_guide":1,"past_design_welcome":1,"hide_douban":1,"douban_unsearchable":1}
         */

        private UserBean user;
        private boolean following;

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getPin_count() {
            return pin_count;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public int getDeleting() {
            return deleting;
        }

        public void setDeleting(int deleting) {
            this.deleting = deleting;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isFollowing() {
            return following;
        }

        public void setFollowing(boolean following) {
            this.following = following;
        }

        public static class UserBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            /**
             * id : 8607108
             * farm : farm1
             * bucket : hbimg
             * key : d556c1e555a56783df97f77fa9b2b4a0a9906040359ec-AzkrbO
             * type : image/jpeg
             * width : 600
             * height : 906
             * frames : 1
             */

            private String avatar;
            private int pin_count;
            private int boards_like_count;
            private int follower_count;
            private int board_count;
            private int commodity_count;
            private int following_count;
            private int creations_count;
            private int like_count;
            private int explore_following_count;
            /**
             * location : 上海
             * sex : 0
             * birthday :
             * job : 2货 无脑 射基师
             * url :
             * about : sai叔一个爱设计的吃货女汉子 ，欢迎关注我的公众号sai叔的日常 每周推出美食和设计相关的内容ˊ_&gt;ˋ
             * show_verification : weibo
             */

            private ProfileBean profile;
            /**
             * newbietask : 0
             * lr : 1459241229
             * invites : 0
             * share : 0
             * default_board : 3329094
             * notificationsRead : [23]
             * past_shiji_guide : 1
             * past_design_welcome : 1
             * hide_douban : 1
             * douban_unsearchable : 1
             */

            private StatusBean status;

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

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getPin_count() {
                return pin_count;
            }

            public void setPin_count(int pin_count) {
                this.pin_count = pin_count;
            }

            public int getBoards_like_count() {
                return boards_like_count;
            }

            public void setBoards_like_count(int boards_like_count) {
                this.boards_like_count = boards_like_count;
            }

            public int getFollower_count() {
                return follower_count;
            }

            public void setFollower_count(int follower_count) {
                this.follower_count = follower_count;
            }

            public int getBoard_count() {
                return board_count;
            }

            public void setBoard_count(int board_count) {
                this.board_count = board_count;
            }

            public int getCommodity_count() {
                return commodity_count;
            }

            public void setCommodity_count(int commodity_count) {
                this.commodity_count = commodity_count;
            }

            public int getFollowing_count() {
                return following_count;
            }

            public void setFollowing_count(int following_count) {
                this.following_count = following_count;
            }

            public int getCreations_count() {
                return creations_count;
            }

            public void setCreations_count(int creations_count) {
                this.creations_count = creations_count;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getExplore_following_count() {
                return explore_following_count;
            }

            public void setExplore_following_count(int explore_following_count) {
                this.explore_following_count = explore_following_count;
            }

            public ProfileBean getProfile() {
                return profile;
            }

            public void setProfile(ProfileBean profile) {
                this.profile = profile;
            }

            public StatusBean getStatus() {
                return status;
            }

            public void setStatus(StatusBean status) {
                this.status = status;
            }

            public static class ProfileBean {
                private String location;
                private String sex;
                private String birthday;
                private String job;
                private String url;
                private String about;
                private String show_verification;

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

                public String getShow_verification() {
                    return show_verification;
                }

                public void setShow_verification(String show_verification) {
                    this.show_verification = show_verification;
                }
            }

            public static class StatusBean {
                private int newbietask;
                private int lr;
                private int invites;
                private String share;
                private int default_board;
                private int past_shiji_guide;
                private int past_design_welcome;
                private int hide_douban;
                private int douban_unsearchable;
                private List<Integer> notificationsRead;

                public int getNewbietask() {
                    return newbietask;
                }

                public void setNewbietask(int newbietask) {
                    this.newbietask = newbietask;
                }

                public int getLr() {
                    return lr;
                }

                public void setLr(int lr) {
                    this.lr = lr;
                }

                public int getInvites() {
                    return invites;
                }

                public void setInvites(int invites) {
                    this.invites = invites;
                }

                public String getShare() {
                    return share;
                }

                public void setShare(String share) {
                    this.share = share;
                }

                public int getDefault_board() {
                    return default_board;
                }

                public void setDefault_board(int default_board) {
                    this.default_board = default_board;
                }

                public int getPast_shiji_guide() {
                    return past_shiji_guide;
                }

                public void setPast_shiji_guide(int past_shiji_guide) {
                    this.past_shiji_guide = past_shiji_guide;
                }

                public int getPast_design_welcome() {
                    return past_design_welcome;
                }

                public void setPast_design_welcome(int past_design_welcome) {
                    this.past_design_welcome = past_design_welcome;
                }

                public int getHide_douban() {
                    return hide_douban;
                }

                public void setHide_douban(int hide_douban) {
                    this.hide_douban = hide_douban;
                }

                public int getDouban_unsearchable() {
                    return douban_unsearchable;
                }

                public void setDouban_unsearchable(int douban_unsearchable) {
                    this.douban_unsearchable = douban_unsearchable;
                }

                public List<Integer> getNotificationsRead() {
                    return notificationsRead;
                }

                public void setNotificationsRead(List<Integer> notificationsRead) {
                    this.notificationsRead = notificationsRead;
                }
            }
        }
    }
}
