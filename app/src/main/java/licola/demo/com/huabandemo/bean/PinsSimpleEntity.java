package licola.demo.com.huabandemo.bean;

/**
 * Created by LiCola on  2016/04/03  16:16
 * 简单的Pins图片bean  包含一般的图片file user board 这三类数据
 */
public class PinsSimpleEntity {

    /**
     * pin_id : 670268161
     * user_id : 10908998
     * board_id : 18466537
     * file_id : 98249119
     * file : {"farm":"farm1","bucket":"hbimg","key":"4cc2cfece6152c19de1483c5384c2e39f2c098ed107708-bNKrXu","type":"image/jpeg","height":"2448","frames":"1","width":"2448"}
     * media_type : 0
     * source : m.weibo.cn
     * link : http://m.weibo.cn/page/tpl?containerid=103003index2388303292_-_photo_all_l&itemid=&title=%E5%9B%BE%E7%89%87%E5%A2%99
     * raw_text : 图片墙
     * text_meta : {}
     * via : 10
     * via_user_id : 0
     * original : null
     * created_at : 1459662241
     * like_count : 0
     * comment_count : 0
     * repin_count : 1
     * is_private : 0
     * orig_source : http://ww4.sinaimg.cn/large/8e5a9dbcjw1f25f879x8wj21w01w01kx.jpg
     * board : {"board_id":18466537,"user_id":10908998,"title":"清纯","description":"","category_id":"beauty","seq":8,"pin_count":1117,"follow_count":521,"like_count":4,"created_at":1415805168,"updated_at":1459662241,"deleting":0,"is_private":0,"extra":null}
     * user : {"user_id":10908998,"username":"莫堇","urlname":"zs641522411","created_at":1385858451,"avatar":"图片地址"}
     */

    private int pin_id;
    private int user_id;
    private int board_id;
    private int file_id;
    /**
     * farm : farm1
     * bucket : hbimg
     * key : 4cc2cfece6152c19de1483c5384c2e39f2c098ed107708-bNKrXu
     * type : image/jpeg
     * height : 2448
     * frames : 1
     * width : 2448
     */

    private FileBean file;
    private int media_type;
    private String source;
    private String link;
    private String raw_text;
    private int via;
    private int via_user_id;
    private Object original;
    private int created_at;
    private int like_count;
    private int comment_count;
    private int repin_count;
    private int is_private;
    private String orig_source;
    /**
     * board_id : 18466537
     * user_id : 10908998
     * title : 清纯
     * description :
     * category_id : beauty
     * seq : 8
     * pin_count : 1117
     * follow_count : 521
     * like_count : 4
     * created_at : 1415805168
     * updated_at : 1459662241
     * deleting : 0
     * is_private : 0
     * extra : null
     */

    private BoardBean board;
    /**
     * user_id : 10908998
     * username : 莫堇
     * urlname : zs641522411
     * created_at : 1385858451
     * avatar : 图片地址
     */

    private UserBean user;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
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

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
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

    public String getOrig_source() {
        return orig_source;
    }

    public void setOrig_source(String orig_source) {
        this.orig_source = orig_source;
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class FileBean {
        private String farm;
        private String bucket;
        private String key;
        private String type;
        private String height;
        private String frames;
        private String width;

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

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getFrames() {
            return frames;
        }

        public void setFrames(String frames) {
            this.frames = frames;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
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
        private Object extra;

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
    }

    public static class UserBean {
        private int user_id;
        private String username;
        private String urlname;
        private int created_at;
        private String avatar;

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
    }
}
