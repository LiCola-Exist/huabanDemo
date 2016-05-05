package licola.demo.com.huabandemo.Entity;

import java.util.List;

/**
 * Created by LiCola on  2016/04/04  19:47
 */
public class BoardPinsBean {

    /**
     * board_id : 3210286
     * user_id : 470656
     * title : Special、
     * description :
     * category_id : beauty
     * seq : 5
     * pin_count : 460
     * follow_count : 1387
     * like_count : 18
     * created_at : 1354788674
     * updated_at : 1459764657
     * deleting : 0
     * is_private : 0
     * extra : {"cover":{"pin_id":"396708240"}}
     * cover : {"pin_id":396708240,"user_id":470656,"board_id":3210286,"file_id":69076727,"file":{"farm":"farm1","bucket":"hbimg","key":"cf06b9cab8166dff391a222536c2b43e674e7dfea9204-iTtsBl","type":"image/jpeg","width":1416,"height":1416,"frames":1,"theme":"9A969D"},"media_type":0,"source":"photo.weibo.com","link":"http://photo.weibo.com/1750156860/wbphotos/large/photo_id/3750772890161141/album_id/3561561172683350","raw_text":" ","text_meta":{},"via":394762585,"via_user_id":17020317,"original":333187864,"created_at":1433695869,"like_count":19,"comment_count":0,"repin_count":43,"is_private":0,"orig_source":null}
     * user : {"user_id":470656,"username":"有时黑有时白、","urlname":"mre7v3oyfx","created_at":1341270673,"avatar":"图片地址"}
     * following : true
     */

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
     * cover : {"pin_id":"396708240"}
     */

    private ExtraBean extra;
    /**
     * pin_id : 396708240
     * user_id : 470656
     * board_id : 3210286
     * file_id : 69076727
     * file : {"farm":"farm1","bucket":"hbimg","key":"cf06b9cab8166dff391a222536c2b43e674e7dfea9204-iTtsBl","type":"image/jpeg","width":1416,"height":1416,"frames":1,"theme":"9A969D"}
     * media_type : 0
     * source : photo.weibo.com
     * link : http://photo.weibo.com/1750156860/wbphotos/large/photo_id/3750772890161141/album_id/3561561172683350
     * raw_text :
     * text_meta : {}
     * via : 394762585
     * via_user_id : 17020317
     * original : 333187864
     * created_at : 1433695869
     * like_count : 19
     * comment_count : 0
     * repin_count : 43
     * is_private : 0
     * orig_source : null
     */

    private CoverBean cover;
    /**
     * user_id : 470656
     * username : 有时黑有时白、
     * urlname : mre7v3oyfx
     * created_at : 1341270673
     * avatar : 图片地址
     */

    private UserBean user;
    private boolean following;

    private List<PinsSimpleBean> pins;

    public void setPins(List<PinsSimpleBean> pins) {
        this.pins = pins;
    }

    public List<PinsSimpleBean> getPins() {
        return pins;
    }

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

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
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

    public static class ExtraBean {
        /**
         * pin_id : 396708240
         */

        private CoverBean cover;

        public CoverBean getCover() {
            return cover;
        }

        public void setCover(CoverBean cover) {
            this.cover = cover;
        }

        public static class CoverBean {
            private String pin_id;

            public String getPin_id() {
                return pin_id;
            }

            public void setPin_id(String pin_id) {
                this.pin_id = pin_id;
            }
        }
    }

    public static class CoverBean {
        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * farm : farm1
         * bucket : hbimg
         * key : cf06b9cab8166dff391a222536c2b43e674e7dfea9204-iTtsBl
         * type : image/jpeg
         * width : 1416
         * height : 1416
         * frames : 1
         * theme : 9A969D
         */

        private FileBean file;
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
        private Object orig_source;

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

        public static class FileBean {
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int width;
            private int height;
            private int frames;
            private String theme;

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

            public String getTheme() {
                return theme;
            }

            public void setTheme(String theme) {
                this.theme = theme;
            }
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
