package licola.demo.com.huabandemo.bean;

import java.util.List;

/**
 * Created by LiCola on  2015/12/04  20:02
 */
public class CardBigBean {

    /**
     * pin_id : 562785369
     * user_id : 17344184
     * board_id : 24048815
     * file_id : 89211799
     * file : {"farm":"farm1","bucket":"hbimg","key":"a2945bdf440f492c5144d24eebe45f23b82f3ff84d81f-4vhC2L","type":"image/jpeg","width":900,"height":1350,"frames":1}
     * media_type : 0
     * source : zcool.com.cn
     * link : http://www.zcool.com.cn/work/ZMTQ0Mzc0MTI=/2.html
     * raw_text : 日料日志————花漫
     * text_meta : {}
     * via : 562658878
     * via_user_id : 605533
     * original : 562658878
     * created_at : 1450779767
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * orig_source : http://img.zcool.cn/community/01eb1a567524a36ac725ad906a11e4.jpg@900w_1l_2o
     * user : {"user_id":17344184,"username":"蓝枫芊柔","urlname":"y0ogn4uezm","created_at":1432372599,"avatar":"https"}
     * board : {"board_id":24048815,"user_id":17344184,"title":"爱好","description":"","category_id":"food_drink","seq":5,"pin_count":1128,"follow_count":76,"like_count":3,"created_at":1432544067,"updated_at":1450779767,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"401845965"}}}
     * via_user : {"user_id":605533,"username":"宁馨郁金香","urlname":"xxf837568038","created_at":1344088961,"avatar":"https"}
     */

    private List<PinsEntity> pins;

    public void setPins(List<PinsEntity> pins) {
        this.pins = pins;
    }

    public List<PinsEntity> getPins() {
        return pins;
    }

    public static class PinsEntity {
        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * farm : farm1
         * bucket : hbimg
         * key : a2945bdf440f492c5144d24eebe45f23b82f3ff84d81f-4vhC2L
         * type : image/jpeg
         * width : 900
         * height : 1350
         * frames : 1
         */

        private FileEntity file;
        private int media_type;
        private String source;
        private String link;
        private String raw_text;
        private int via;
        private int via_user_id;
        private int original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private String orig_source;
        /**
         * user_id : 17344184
         * username : 蓝枫芊柔
         * urlname : y0ogn4uezm
         * created_at : 1432372599
         * avatar : https
         */

        private UserEntity user;
        /**
         * board_id : 24048815
         * user_id : 17344184
         * title : 爱好
         * description :
         * category_id : food_drink
         * seq : 5
         * pin_count : 1128
         * follow_count : 76
         * like_count : 3
         * created_at : 1432544067
         * updated_at : 1450779767
         * deleting : 0
         * is_private : 0
         * extra : {"cover":{"pin_id":"401845965"}}
         */

        private BoardEntity board;
        /**
         * user_id : 605533
         * username : 宁馨郁金香
         * urlname : xxf837568038
         * created_at : 1344088961
         * avatar : https
         */

        private ViaUserEntity via_user;

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

        public void setOrig_source(String orig_source) {
            this.orig_source = orig_source;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setBoard(BoardEntity board) {
            this.board = board;
        }

        public void setVia_user(ViaUserEntity via_user) {
            this.via_user = via_user;
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

        public String getOrig_source() {
            return orig_source;
        }

        public UserEntity getUser() {
            return user;
        }

        public BoardEntity getBoard() {
            return board;
        }

        public ViaUserEntity getVia_user() {
            return via_user;
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

        public static class UserEntity {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private String avatar;

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

            public void setAvatar(String avatar) {
                this.avatar = avatar;
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

            public String getAvatar() {
                return avatar;
            }
        }

        public static class BoardEntity {
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
            /**
             * cover : {"pin_id":"401845965"}
             */

            private ExtraEntity extra;

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

            public void setCategory_id(String category_id) {
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

            public void setExtra(ExtraEntity extra) {
                this.extra = extra;
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

            public String getCategory_id() {
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

            public ExtraEntity getExtra() {
                return extra;
            }

            public static class ExtraEntity {
                /**
                 * pin_id : 401845965
                 */

                private CoverEntity cover;

                public void setCover(CoverEntity cover) {
                    this.cover = cover;
                }

                public CoverEntity getCover() {
                    return cover;
                }

                public static class CoverEntity {
                    private String pin_id;

                    public void setPin_id(String pin_id) {
                        this.pin_id = pin_id;
                    }

                    public String getPin_id() {
                        return pin_id;
                    }
                }
            }
        }

        public static class ViaUserEntity {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private String avatar;



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

            public void setAvatar(String avatar) {
                this.avatar = avatar;
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

            public String getAvatar() {
                return avatar;
            }
        }
    }


}
