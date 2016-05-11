package licola.demo.com.huabandemo.Module.SearchResult;

import java.util.List;

/**
 * Created by LiCola on  2015/12/05  15:26
 * 搜索画板的返回结果
 * 其中去掉了 results表示画板中属于什么类型的结果
 * 替换了 avatar 统一成String
 */
public class SearchBoardBean {
    /**
     * text : 美人鱼
     * type : board
     */

    private QueryBean query;
    /**
     * query : {"text":"美人鱼","type":"board"}
     * pin_count : 10327
     * board_count : 418
     * people_count : 227
     * gift_count : 26
     * facets : {"missing":181,"total":237,"other":0}
     * boards : [{"board_id":28185956,"user_id":11163317,"title":"美人鱼","description":"","category_id":"illustration","seq":28185956,"pin_count":24,"follow_count":2922,"like_count":0,"created_at":1455907880,"updated_at":1457206262,"deleting":0,"is_private":0,"extra":null,"pins":[{"pin_id":614471257,"user_id":11163317,"board_id":28185956,"file_id":41354146,"file":{"farm":"farm1","bucket":"hbimg","key":"238c6bcaa579030ae6b612ef89451ae2237694dd3407d-ruEKxm","type":"image/jpeg","width":620,"height":413,"frames":1},"media_type":0,"source":"heiguang.com","link":"http://www.heiguang.com/digital/smcy/20140411/114900.html","raw_text":"美人鱼","text_meta":{},"via":603915231,"via_user_id":17690958,"original":155061587,"created_at":1455908028,"like_count":0,"comment_count":0,"repin_count":8,"is_private":0,"orig_source":null},{"pin_id":614471189,"user_id":11163317,"board_id":28185956,"file_id":42184513,"file":{"farm":"farm1","bucket":"hbimg","key":"b118dd567156d8ea22d0ec23c072a6fe0f3c2a3319455-mC4ODX","type":"image/jpeg","width":736,"height":1116,"frames":1},"media_type":0,"source":"pinterest.com","link":"http://www.pinterest.com/pin/278167714459452222/","raw_text":"Shelly Wan","text_meta":{},"via":603917696,"via_user_id":17690958,"original":159550545,"created_at":1455907994,"like_count":0,"comment_count":0,"repin_count":9,"is_private":0,"orig_source":null}],"user":{"user_id":11163317,"username":"mendafan","urlname":"menda_flq","created_at":1387222387,"avatar":"图片地址"}}]
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * missing : 181
     * total : 237
     * other : 0
     */

    private FacetsBean facets;
    /**
     * board_id : 28185956
     * user_id : 11163317
     * title : 美人鱼
     * description :
     * category_id : illustration
     * seq : 28185956
     * pin_count : 24
     * follow_count : 2922
     * like_count : 0
     * created_at : 1455907880
     * updated_at : 1457206262
     * deleting : 0
     * is_private : 0
     * extra : null
     * pins : [{"pin_id":614471257,"user_id":11163317,"board_id":28185956,"file_id":41354146,"file":{"farm":"farm1","bucket":"hbimg","key":"238c6bcaa579030ae6b612ef89451ae2237694dd3407d-ruEKxm","type":"image/jpeg","width":620,"height":413,"frames":1},"media_type":0,"source":"heiguang.com","link":"http://www.heiguang.com/digital/smcy/20140411/114900.html","raw_text":"美人鱼","text_meta":{},"via":603915231,"via_user_id":17690958,"original":155061587,"created_at":1455908028,"like_count":0,"comment_count":0,"repin_count":8,"is_private":0,"orig_source":null},{"pin_id":614471189,"user_id":11163317,"board_id":28185956,"file_id":42184513,"file":{"farm":"farm1","bucket":"hbimg","key":"b118dd567156d8ea22d0ec23c072a6fe0f3c2a3319455-mC4ODX","type":"image/jpeg","width":736,"height":1116,"frames":1},"media_type":0,"source":"pinterest.com","link":"http://www.pinterest.com/pin/278167714459452222/","raw_text":"Shelly Wan","text_meta":{},"via":603917696,"via_user_id":17690958,"original":159550545,"created_at":1455907994,"like_count":0,"comment_count":0,"repin_count":9,"is_private":0,"orig_source":null}]
     * user : {"user_id":11163317,"username":"mendafan","urlname":"menda_flq","created_at":1387222387,"avatar":"图片地址"}
     */

    private List<BoardsBean> boards;

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

    public FacetsBean getFacets() {
        return facets;
    }

    public void setFacets(FacetsBean facets) {
        this.facets = facets;
    }

    public List<BoardsBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardsBean> boards) {
        this.boards = boards;
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

    public static class FacetsBean {
        private int missing;
        private int total;
        private int other;

        public int getMissing() {
            return missing;
        }

        public void setMissing(int missing) {
            this.missing = missing;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getOther() {
            return other;
        }

        public void setOther(int other) {
            this.other = other;
        }
    }

    public static class BoardsBean {
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
         * user_id : 11163317
         * username : mendafan
         * urlname : menda_flq
         * created_at : 1387222387
         * avatar : 图片地址
         */

        private UserBean user;
        /**
         * pin_id : 614471257
         * user_id : 11163317
         * board_id : 28185956
         * file_id : 41354146
         * file : {"farm":"farm1","bucket":"hbimg","key":"238c6bcaa579030ae6b612ef89451ae2237694dd3407d-ruEKxm","type":"image/jpeg","width":620,"height":413,"frames":1}
         * media_type : 0
         * source : heiguang.com
         * link : http://www.heiguang.com/digital/smcy/20140411/114900.html
         * raw_text : 美人鱼
         * text_meta : {}
         * via : 603915231
         * via_user_id : 17690958
         * original : 155061587
         * created_at : 1455908028
         * like_count : 0
         * comment_count : 0
         * repin_count : 8
         * is_private : 0
         * orig_source : null
         */

        private List<PinsBean> pins;

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

        public List<PinsBean> getPins() {
            return pins;
        }

        public void setPins(List<PinsBean> pins) {
            this.pins = pins;
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

        public static class PinsBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            /**
             * farm : farm1
             * bucket : hbimg
             * key : 238c6bcaa579030ae6b612ef89451ae2237694dd3407d-ruEKxm
             * type : image/jpeg
             * width : 620
             * height : 413
             * frames : 1
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

            public static class TextMetaBean {
            }
        }
    }

}
