package licola.demo.com.huabandemo.Module.Follow;

import java.util.List;

/**
 * Created by LiCola on  2015/12/12  17:41
 * 用户的关注者信息
 */
public class UserFollowBean {

    /**
     * user_id : 83768
     * username : cherish_li
     * urlname : cherish4150
     * created_at : 1329389979
     * avatar : {"id":640434,"farm":"farm1","bucket":"hbimg","key":"e8eb683d84e2426147961e1c37af81c1fdcf75873a8b-Ggh3kL","type":"image/jpeg","width":180,"height":180,"frames":1}
     * seq : 124622038
     * pins : [{"pin_id":544279891,"user_id":83768,"board_id":27337029,"file_id":84654119,"file":{"farm":"farm1","bucket":"hbimg","key":"ec69284e70d8cc025a5be6f69ac9f4c45f4cd5154a4fa3-OtYO2Y","type":"image/png","width":1903,"height":8238,"frames":1},"media_type":0,"source":"new.yohobuy.com","link":"http://new.yohobuy.com/lifestyle/","raw_text":"创意生活|YOHO!BUY有货 | 年轻人潮流购物中心，中国潮流购物风向标，官方授权正品保证","text_meta":{},"via":507277690,"via_user_id":6825821,"original":507277690,"created_at":1449130302,"like_count":0,"comment_count":0,"repin_count":1,"is_private":0,"orig_source":null},{"pin_id":544279672,"user_id":83768,"board_id":27337029,"file_id":85770679,"file":{"farm":"farm1","bucket":"hbimg","key":"c72c295763634d548cdd25dccba27cee7f81eaec481406-CSGc7V","type":"image/png","width":1903,"height":4479,"frames":1},"media_type":0,"source":"yohobuy.com","link":"http://www.yohobuy.com/?utm_source=bdcpc&utm_medium=cpc&utm_campaign=301","raw_text":"YOHO!有货 | 年轻人潮流购物中心，中国潮流购物风向标，官方授权正品保证","text_meta":{},"via":519936686,"via_user_id":6825821,"original":519936686,"created_at":1449130293,"like_count":0,"comment_count":0,"repin_count":1,"is_private":0,"orig_source":null},{"pin_id":544279284,"user_id":83768,"board_id":27337029,"file_id":86759711,"file":{"farm":"farm1","bucket":"hbimg","key":"c01f9c2e2de13b11068b3a2822bdb0cbb647f00e4c3b1-7xUdkT","type":"image/png","width":1248,"height":352,"frames":1},"media_type":0,"source":"sale.yohobuy.com","link":"http://sale.yohobuy.com/?specialsale_id=10&gender=1,3","raw_text":"潮流商品搜索 | YOHO!有货","text_meta":{},"via":541216631,"via_user_id":6825821,"original":532807104,"created_at":1449130277,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]
     * pin_count : 2740
     * board_count : 37
     * boards_like_count : 1
     * follower_count : 252
     * commodity_count : 14
     * following_count : 126
     * like_count : 1
     * creations_count : 0
     * profile : {"location":"北京 海淀区","about":"我不选择最好，让最好的选择我。","url":""}
     */

    private List<UsersEntity> users;

    public void setUsers(List<UsersEntity> users) {
        this.users = users;
    }

    public List<UsersEntity> getUsers() {
        return users;
    }

    public static class UsersEntity {
        private int user_id;
        private String username;
        private String urlname;
        private int created_at;
        /**
         * id : 640434
         * farm : farm1
         * bucket : hbimg
         * key : e8eb683d84e2426147961e1c37af81c1fdcf75873a8b-Ggh3kL
         * type : image/jpeg
         * width : 180
         * height : 180
         * frames : 1
         */

        private AvatarEntity avatar;
        private int seq;
        private int pin_count;
        private int board_count;
        private int boards_like_count;
        private int follower_count;
        private int commodity_count;
        private int following_count;
        private int like_count;
        private int creations_count;
        /**
         * location : 北京 海淀区
         * about : 我不选择最好，让最好的选择我。
         * url :
         */

        private ProfileEntity profile;
        /**
         * pin_id : 544279891
         * user_id : 83768
         * board_id : 27337029
         * file_id : 84654119
         * file : {"farm":"farm1","bucket":"hbimg","key":"ec69284e70d8cc025a5be6f69ac9f4c45f4cd5154a4fa3-OtYO2Y","type":"image/png","width":1903,"height":8238,"frames":1}
         * media_type : 0
         * source : new.yohobuy.com
         * link : http://new.yohobuy.com/lifestyle/
         * raw_text : 创意生活|YOHO!BUY有货 | 年轻人潮流购物中心，中国潮流购物风向标，官方授权正品保证
         * text_meta : {}
         * via : 507277690
         * via_user_id : 6825821
         * original : 507277690
         * created_at : 1449130302
         * like_count : 0
         * comment_count : 0
         * repin_count : 1
         * is_private : 0
         * orig_source : null
         */

        private List<PinsEntity> pins;

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

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public void setBoard_count(int board_count) {
            this.board_count = board_count;
        }

        public void setBoards_like_count(int boards_like_count) {
            this.boards_like_count = boards_like_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public void setCommodity_count(int commodity_count) {
            this.commodity_count = commodity_count;
        }

        public void setFollowing_count(int following_count) {
            this.following_count = following_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setCreations_count(int creations_count) {
            this.creations_count = creations_count;
        }

        public void setProfile(ProfileEntity profile) {
            this.profile = profile;
        }

        public void setPins(List<PinsEntity> pins) {
            this.pins = pins;
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

        public int getSeq() {
            return seq;
        }

        public int getPin_count() {
            return pin_count;
        }

        public int getBoard_count() {
            return board_count;
        }

        public int getBoards_like_count() {
            return boards_like_count;
        }

        public int getFollower_count() {
            return follower_count;
        }

        public int getCommodity_count() {
            return commodity_count;
        }

        public int getFollowing_count() {
            return following_count;
        }

        public int getLike_count() {
            return like_count;
        }

        public int getCreations_count() {
            return creations_count;
        }

        public ProfileEntity getProfile() {
            return profile;
        }

        public List<PinsEntity> getPins() {
            return pins;
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
            private String location;
            private String about;
            private String url;

            public void setLocation(String location) {
                this.location = location;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLocation() {
                return location;
            }

            public String getAbout() {
                return about;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class PinsEntity {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            /**
             * farm : farm1
             * bucket : hbimg
             * key : ec69284e70d8cc025a5be6f69ac9f4c45f4cd5154a4fa3-OtYO2Y
             * type : image/png
             * width : 1903
             * height : 8238
             * frames : 1
             */

            private FileEntity file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private TextMetaEntity text_meta;
            private int via;
            private int via_user_id;
            private int original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private Object orig_source;

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public void setFile_id(int file_id) {
                this.file_id = file_id;
            }

            public void setFile(FileEntity file) {
                this.file = file;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public void setText_meta(TextMetaEntity text_meta) {
                this.text_meta = text_meta;
            }

            public void setVia(int via) {
                this.via = via;
            }

            public void setVia_user_id(int via_user_id) {
                this.via_user_id = via_user_id;
            }

            public void setOriginal(int original) {
                this.original = original;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public void setOrig_source(Object orig_source) {
                this.orig_source = orig_source;
            }

            public int getPin_id() {
                return pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public int getBoard_id() {
                return board_id;
            }

            public int getFile_id() {
                return file_id;
            }

            public FileEntity getFile() {
                return file;
            }

            public int getMedia_type() {
                return media_type;
            }

            public String getSource() {
                return source;
            }

            public String getLink() {
                return link;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public TextMetaEntity getText_meta() {
                return text_meta;
            }

            public int getVia() {
                return via;
            }

            public int getVia_user_id() {
                return via_user_id;
            }

            public int getOriginal() {
                return original;
            }

            public int getCreated_at() {
                return created_at;
            }

            public int getLike_count() {
                return like_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public int getIs_private() {
                return is_private;
            }

            public Object getOrig_source() {
                return orig_source;
            }

            public static class FileEntity {
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
            }

            public static class TextMetaEntity {
            }
        }
    }
}
