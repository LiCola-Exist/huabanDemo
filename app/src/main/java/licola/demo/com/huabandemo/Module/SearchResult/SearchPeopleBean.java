package licola.demo.com.huabandemo.Module.SearchResult;

import java.util.List;

/**
 * Created by LiCola on  2015/12/05  15:43
 * 搜索用户的返回结果
 * 替换了avatar 的返回结果
 */
public class SearchPeopleBean {


    /**
     * text : 美人鱼
     * type : people
     */

    private QueryBean query;
    /**
     * query : {"text":"美人鱼","type":"people"}
     * pin_count : 10327
     * board_count : 418
     * people_count : 227
     * gift_count : 26
     * users : [{"user_id":78293,"username":"不爱美人鱼","urlname":"tamalove","created_at":1329136943,"avatar":"图片地址","follower_count":225,"pins":[{"pin_id":625429166,"user_id":78293,"board_id":14019096,"file_id":94804587,"file":{"farm":"farm1","bucket":"hbimg","key":"43fe900fb8b0eed365d9e790066f8e1be1461d54844f23-FsibUB","type":"image/jpeg","height":"10943","frames":"1","width":"1920"},"media_type":0,"source":"tao.bb","link":"http://tao.bb/kOOsn","raw_text":"家居百货用品水杯天猫首页活动页面 物生物春季新品发布会 物生物旗舰店","text_meta":{},"via":623964202,"via_user_id":803657,"original":623798181,"created_at":1456881507,"like_count":0,"comment_count":0,"repin_count":2,"is_private":0,"orig_source":null},{"pin_id":625428479,"user_id":78293,"board_id":13428088,"file_id":94593318,"file":{"farm":"farm1","bucket":"hbimg","key":"c583df5290b76fccbb5a50e0339936b7db44f66164c7fa-WOQ9Wl","type":"image/jpeg","height":"11914","frames":"1","width":"1920"},"media_type":0,"source":null,"link":null,"raw_text":"佰魅伊人2016春季页面","text_meta":{},"via":624415594,"via_user_id":803657,"original":621544310,"created_at":1456881483,"like_count":0,"comment_count":0,"repin_count":2,"is_private":0,"orig_source":null},{"pin_id":623438449,"user_id":78293,"board_id":13428088,"file_id":94705844,"file":{"farm":"farm1","bucket":"hbimg","key":"7621a6cde3cf2768717942f6dd970754410e627371a643-yA7vwZ","type":"image/jpeg","height":"9993","frames":"1","width":"1920"},"media_type":0,"source":null,"link":"","raw_text":"春光节美容护肤化妆品天猫首页活动页面 瓷肌旗舰店","text_meta":{},"via":622771567,"via_user_id":976267,"original":622739358,"created_at":1456732147,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]}]
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * user_id : 78293
     * username : 不爱美人鱼
     * urlname : tamalove
     * created_at : 1329136943
     * avatar : 图片地址
     * follower_count : 225
     * pins : [{"pin_id":625429166,"user_id":78293,"board_id":14019096,"file_id":94804587,"file":{"farm":"farm1","bucket":"hbimg","key":"43fe900fb8b0eed365d9e790066f8e1be1461d54844f23-FsibUB","type":"image/jpeg","height":"10943","frames":"1","width":"1920"},"media_type":0,"source":"tao.bb","link":"http://tao.bb/kOOsn","raw_text":"家居百货用品水杯天猫首页活动页面 物生物春季新品发布会 物生物旗舰店","text_meta":{},"via":623964202,"via_user_id":803657,"original":623798181,"created_at":1456881507,"like_count":0,"comment_count":0,"repin_count":2,"is_private":0,"orig_source":null},{"pin_id":625428479,"user_id":78293,"board_id":13428088,"file_id":94593318,"file":{"farm":"farm1","bucket":"hbimg","key":"c583df5290b76fccbb5a50e0339936b7db44f66164c7fa-WOQ9Wl","type":"image/jpeg","height":"11914","frames":"1","width":"1920"},"media_type":0,"source":null,"link":null,"raw_text":"佰魅伊人2016春季页面","text_meta":{},"via":624415594,"via_user_id":803657,"original":621544310,"created_at":1456881483,"like_count":0,"comment_count":0,"repin_count":2,"is_private":0,"orig_source":null},{"pin_id":623438449,"user_id":78293,"board_id":13428088,"file_id":94705844,"file":{"farm":"farm1","bucket":"hbimg","key":"7621a6cde3cf2768717942f6dd970754410e627371a643-yA7vwZ","type":"image/jpeg","height":"9993","frames":"1","width":"1920"},"media_type":0,"source":null,"link":"","raw_text":"春光节美容护肤化妆品天猫首页活动页面 瓷肌旗舰店","text_meta":{},"via":622771567,"via_user_id":976267,"original":622739358,"created_at":1456732147,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]
     */

    private List<UsersBean> users;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    public int getGift_count() {
        return gift_count;
    }

    public void setGift_count(int gift_count) {
        this.gift_count = gift_count;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class QueryBean {
        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class UsersBean {
        private int user_id;
        private String username;
        private String urlname;
        private int created_at;
        private String avatar;
        private int follower_count;
        /**
         * pin_id : 625429166
         * user_id : 78293
         * board_id : 14019096
         * file_id : 94804587
         * file : {"farm":"farm1","bucket":"hbimg","key":"43fe900fb8b0eed365d9e790066f8e1be1461d54844f23-FsibUB","type":"image/jpeg","height":"10943","frames":"1","width":"1920"}
         * media_type : 0
         * source : tao.bb
         * link : http://tao.bb/kOOsn
         * raw_text : 家居百货用品水杯天猫首页活动页面 物生物春季新品发布会 物生物旗舰店
         * text_meta : {}
         * via : 623964202
         * via_user_id : 803657
         * original : 623798181
         * created_at : 1456881507
         * like_count : 0
         * comment_count : 0
         * repin_count : 2
         * is_private : 0
         * orig_source : null
         */

        private List<PinsBean> pins;

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

        public int getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(int follower_count) {
            this.follower_count = follower_count;
        }

        public List<PinsBean> getPins() {
            return pins;
        }

        public void setPins(List<PinsBean> pins) {
            this.pins = pins;
        }

        public static class PinsBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            /**
             * farm : farm1
             * bucket : hbimg
             * key : 43fe900fb8b0eed365d9e790066f8e1be1461d54844f23-FsibUB
             * type : image/jpeg
             * height : 10943
             * frames : 1
             * width : 1920
             */

            private FileBean file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private TextMetaBean text_meta;
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

            public TextMetaBean getText_meta() {
                return text_meta;
            }

            public void setText_meta(TextMetaBean text_meta) {
                this.text_meta = text_meta;
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

            public static class TextMetaBean {
            }
        }
    }
}