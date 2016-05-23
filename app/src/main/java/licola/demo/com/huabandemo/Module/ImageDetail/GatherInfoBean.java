package licola.demo.com.huabandemo.Module.ImageDetail;

/**
 * Created by LiCola on  2016/05/06  15:44
 * 对某个图片采集的网络调用 得到是否曾经被采集的信息
 */
public class GatherInfoBean {

    /**
     * warning : 100
     * exist_pin : {"pin_id":707960382,"user_id":15246080,"board_id":17891564,"file_id":76634908,"file":{"farm":"farm1","bucket":"hbimg","key":"480ff4b6c1ee8f5a6facf64baf2138c6dcf3361c7cb2c-5cHQTC","type":"image/jpeg","width":638,"height":1136,"frames":1},"media_type":0,"source":null,"link":null,"raw_text":"","via":707907583,"via_user_id":12082335,"original":417098788,"created_at":1462517186,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null,"board":{"board_id":17891564,"user_id":15246080,"title":"主画板修改","description":"","category_id":"beauty","seq":3,"pin_count":559,"follow_count":107,"like_count":0,"created_at":1412310925,"updated_at":1462517187,"deleting":0,"is_private":0}}
     */

    private int warning;
    /**
     * pin_id : 707960382
     * user_id : 15246080
     * board_id : 17891564
     * file_id : 76634908
     * file : {"farm":"farm1","bucket":"hbimg","key":"480ff4b6c1ee8f5a6facf64baf2138c6dcf3361c7cb2c-5cHQTC","type":"image/jpeg","width":638,"height":1136,"frames":1}
     * media_type : 0
     * source : null
     * link : null
     * raw_text :
     * via : 707907583
     * via_user_id : 12082335
     * original : 417098788
     * created_at : 1462517186
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * orig_source : null
     * board : {"board_id":17891564,"user_id":15246080,"title":"主画板修改","description":"","category_id":"beauty","seq":3,"pin_count":559,"follow_count":107,"like_count":0,"created_at":1412310925,"updated_at":1462517187,"deleting":0,"is_private":0}
     */

    private ExistPinBean exist_pin;

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public ExistPinBean getExist_pin() {
        return exist_pin;
    }

    public void setExist_pin(ExistPinBean exist_pin) {
        this.exist_pin = exist_pin;
    }

    public static class ExistPinBean {
        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * farm : farm1
         * bucket : hbimg
         * key : 480ff4b6c1ee8f5a6facf64baf2138c6dcf3361c7cb2c-5cHQTC
         * type : image/jpeg
         * width : 638
         * height : 1136
         * frames : 1
         */

        private FileBean file;
        private int media_type;
        private Object source;
        private Object link;
        private String raw_text;
        private int via;
        private int via_user_id;
        private int original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private Object orig_source;
        /**
         * board_id : 17891564
         * user_id : 15246080
         * title : 主画板修改
         * description :
         * category_id : beauty
         * seq : 3
         * pin_count : 559
         * follow_count : 107
         * like_count : 0
         * created_at : 1412310925
         * updated_at : 1462517187
         * deleting : 0
         * is_private : 0
         */

        private BoardBean board;

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getFile_id() {
            return file_id;
        }

        public void setFile_id(int file_id) {
            this.file_id = file_id;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public Object getLink() {
            return link;
        }

        public void setLink(Object link) {
            this.link = link;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }

        public int getVia() {
            return via;
        }

        public void setVia(int via) {
            this.via = via;
        }

        public int getVia_user_id() {
            return via_user_id;
        }

        public void setVia_user_id(int via_user_id) {
            this.via_user_id = via_user_id;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
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

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public Object getOrig_source() {
            return orig_source;
        }

        public void setOrig_source(Object orig_source) {
            this.orig_source = orig_source;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }

        public static class FileBean {
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int width;
            private int height;
            private int frames;

            public String getFarm() {
                return farm;
            }

            public void setFarm(String farm) {
                this.farm = farm;
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getFrames() {
                return frames;
            }

            public void setFrames(int frames) {
                this.frames = frames;
            }
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
        }
    }
}
