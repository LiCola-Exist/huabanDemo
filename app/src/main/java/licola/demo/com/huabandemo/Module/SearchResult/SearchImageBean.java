package licola.demo.com.huabandemo.Module.SearchResult;

import java.util.List;

import licola.demo.com.huabandemo.Entity.ErrorBaseBean;
import licola.demo.com.huabandemo.Entity.FacetsEntity;
import licola.demo.com.huabandemo.Entity.PinsMainEntity;

/**
 * Created by LiCola on  2015/12/05  15:25
 * 搜索图片的返回结果
 * 其中去掉了 comments 这个数组对象 其中包含有个图片的评论信息
 * 已经把user相关的 avatar 对象改为 String
 */
public class SearchImageBean extends ErrorBaseBean{


    /**
     * text : 狗狗
     * type : pin
     */

    private QueryBean query;
    /**
     * query : {"text":"狗狗","type":"pin"}
     * pin_count : 23924
     * board_count : 2312
     * people_count : 474
     * gift_count : 95
     * facets : {"missing":0,"total":23923,"other":0,"results":{"pets":6450,"funny":2284,"travel_places":1809,"other":1764,"photography":1760,"design":1652,"illustration":1529,"apparel":956,"web_app_icon":923,"home":779,"diy_crafts":584,"desire":567,"beauty":469,"industrial_design":419,"kids":298,"people":238,"anime":232,"film_music_books":218,"tips":191,"quotes":173,"food_drink":128,"wedding_events":123,"art":96,"modeling_hair":74,"games":55,"men":50,"architecture":30,"data_presentation":23,"education":20,"fitness":13,"geek":5,"sports":4,"digital":4,"cars_motorcycles":3}}
     * pins : [{"pin_id":613719457,"user_id":18165573,"board_id":28175104,"file_id":80120821,"file":{"farm":"farm1","bucket":"hbimg","key":"393e0b24e9e047e7d922b4277743d42a11bc563e8653-NqeAzU","type":"image/jpeg","width":440,"height":440,"frames":1},"media_type":0,"source":"weibo.com","link":"http://weibo.com/2968590850/DixnevliG","raw_text":"对可爱又听话的狗狗毫无抵抗力","text_meta":{},"via":7,"via_user_id":0,"original":null,"created_at":1455853492,"like_count":0,"comment_count":0,"repin_count":30,"is_private":0,"orig_source":null,"user":{"user_id":18165573,"username":"安頔Andy","urlname":"nmualcswwp","created_at":1449122560,"avatar":{"id":94431253,"farm":"farm1","bucket":"hbimg","key":"b036e8fb347fdfdd8428b1c1b93961aded1c563d77b9-nRf8ou","type":"image/jpeg","height":"440","frames":"1","width":"440"}},"board":{"board_id":28175104,"user_id":18165573,"title":"萌宠 萌宠 萌宠","description":"","category_id":"kids","seq":32,"pin_count":59,"follow_count":35,"like_count":0,"created_at":1455848727,"updated_at":1458096951,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"631791256"}}}}]
     * page : 1
     * category : null
     * ads : {"fixedAds":[{"id":15,"link":"http://event.huaban.com/activity/4/slug/home/?md=homefeed","image":{"bucket":"hbimg-other","key":"92ebf6aacbcf7649e39458132c45b0f06dbf46e022659","width":236,"height":420},"type":2,"placement":"PC:fixed:home,PC:fixed:search","category":"CATEGORY_all","startAt":1458000000,"endAt":1459036800,"position":0}],"normalAds":[]}
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * missing : 0
     * total : 23923
     * other : 0
     * results : {"pets":6450,"funny":2284,"travel_places":1809,"other":1764,"photography":1760,"design":1652,"illustration":1529,"apparel":956,"web_app_icon":923,"home":779,"diy_crafts":584,"desire":567,"beauty":469,"industrial_design":419,"kids":298,"people":238,"anime":232,"film_music_books":218,"tips":191,"quotes":173,"food_drink":128,"wedding_events":123,"art":96,"modeling_hair":74,"games":55,"men":50,"architecture":30,"data_presentation":23,"education":20,"fitness":13,"geek":5,"sports":4,"digital":4,"cars_motorcycles":3}
     */

    private FacetsEntity facets;
    private int page;
    private Object category;
    private AdsBean ads;
    /**
     * pin_id : 613719457
     * user_id : 18165573
     * board_id : 28175104
     * file_id : 80120821
     * file : {"farm":"farm1","bucket":"hbimg","key":"393e0b24e9e047e7d922b4277743d42a11bc563e8653-NqeAzU","type":"image/jpeg","width":440,"height":440,"frames":1}
     * media_type : 0
     * source : weibo.com
     * link : http://weibo.com/2968590850/DixnevliG
     * raw_text : 对可爱又听话的狗狗毫无抵抗力
     * text_meta : {}
     * via : 7
     * via_user_id : 0
     * original : null
     * created_at : 1455853492
     * like_count : 0
     * comment_count : 0
     * repin_count : 30
     * is_private : 0
     * orig_source : null
     * user : {"user_id":18165573,"username":"安頔Andy","urlname":"nmualcswwp","created_at":1449122560,"avatar":{"id":94431253,"farm":"farm1","bucket":"hbimg","key":"b036e8fb347fdfdd8428b1c1b93961aded1c563d77b9-nRf8ou","type":"image/jpeg","height":"440","frames":"1","width":"440"}}
     * board : {"board_id":28175104,"user_id":18165573,"title":"萌宠 萌宠 萌宠","description":"","category_id":"kids","seq":32,"pin_count":59,"follow_count":35,"like_count":0,"created_at":1455848727,"updated_at":1458096951,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"631791256"}}}
     */

    private List<PinsMainEntity> pins;

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

    public FacetsEntity getFacets() {
        return facets;
    }

    public void setFacets(FacetsEntity facets) {
        this.facets = facets;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public AdsBean getAds() {
        return ads;
    }

    public void setAds(AdsBean ads) {
        this.ads = ads;
    }

    public List<PinsMainEntity> getPins() {
        return pins;
    }

    public void setPins(List<PinsMainEntity> pins) {
        this.pins = pins;
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
    

    public static class AdsBean {
        /**
         * id : 15
         * link : http://event.huaban.com/activity/4/slug/home/?md=homefeed
         * image : {"bucket":"hbimg-other","key":"92ebf6aacbcf7649e39458132c45b0f06dbf46e022659","width":236,"height":420}
         * type : 2
         * placement : PC:fixed:home,PC:fixed:search
         * category : CATEGORY_all
         * startAt : 1458000000
         * endAt : 1459036800
         * position : 0
         */

        private List<FixedAdsBean> fixedAds;


        public List<FixedAdsBean> getFixedAds() {
            return fixedAds;
        }

        public void setFixedAds(List<FixedAdsBean> fixedAds) {
            this.fixedAds = fixedAds;
        }

        public static class FixedAdsBean {
            private int id;
            private String link;
            /**
             * bucket : hbimg-other
             * key : 92ebf6aacbcf7649e39458132c45b0f06dbf46e022659
             * width : 236
             * height : 420
             */

            private ImageBean image;
            private int type;
            private String placement;
            private String category;
            private int startAt;
            private int endAt;
            private int position;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPlacement() {
                return placement;
            }

            public void setPlacement(String placement) {
                this.placement = placement;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public int getStartAt() {
                return startAt;
            }

            public void setStartAt(int startAt) {
                this.startAt = startAt;
            }

            public int getEndAt() {
                return endAt;
            }

            public void setEndAt(int endAt) {
                this.endAt = endAt;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public static class ImageBean {
                private String bucket;
                private String key;
                private int width;
                private int height;

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
            }
        }
    }


}