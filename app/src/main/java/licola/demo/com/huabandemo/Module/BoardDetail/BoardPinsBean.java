package licola.demo.com.huabandemo.Module.BoardDetail;

import java.util.List;

/**
 * Created by LiCola on  2015/12/12  17:43
 */
public class BoardPinsBean {

    /**
     * pin_id : 551289779
     * user_id : 17008723
     * board_id : 27444799
     * file_id : 24671677
     * file : {"farm":"farm1","bucket":"hbimg","key":"14ff233f10fcf3b133403afb38a772f8fedb76e812b57-LItr9Q","type":"image/jpeg","width":440,"height":660,"frames":1,"theme":"262427"}
     * media_type : 0
     * source : null
     * link : null
     * raw_text :
     * text_meta : {}
     * via : 93657864
     * via_user_id : 1113163
     * original : 90831265
     * created_at : 1449764907
     * like_count : 0
     * comment_count : 0
     * repin_count : 1
     * is_private : 0
     * orig_source : null
     * hide_origin : false
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
         * key : 14ff233f10fcf3b133403afb38a772f8fedb76e812b57-LItr9Q
         * type : image/jpeg
         * width : 440
         * height : 660
         * frames : 1
         * theme : 262427
         */

        private FileEntity file;
        private int media_type;
        private Object source;
        private Object link;
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
        private boolean hide_origin;

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

        public void setSource(Object source) {
            this.source = source;
        }

        public void setLink(Object link) {
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

        public void setHide_origin(boolean hide_origin) {
            this.hide_origin = hide_origin;
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

        public Object getSource() {
            return source;
        }

        public Object getLink() {
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

        public boolean isHide_origin() {
            return hide_origin;
        }

        public static class FileEntity {
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int width;
            private int height;
            private int frames;
            private String theme;

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

            public void setTheme(String theme) {
                this.theme = theme;
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

            public String getTheme() {
                return theme;
            }
        }

        public static class TextMetaEntity {
        }
    }
}
