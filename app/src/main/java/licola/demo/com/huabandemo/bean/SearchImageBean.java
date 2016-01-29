package licola.demo.com.huabandemo.bean;

import java.util.List;

/**
 * Created by LiCola on  2015/12/05  15:25
 */
public class SearchImageBean {

    /**
     * text : 美食
     * type : pin
     */

    private QueryEntity query;
    /**
     * query : {"text":"美食","type":"pin"}
     * pin_count : 156877
     * board_count : 61448
     * people_count : 510
     * gift_count : 50
     * facets : {"missing":0,"total":156877,"other":0,"results":{"food_drink":58957,"travel_places":32928,"design":18016,"illustration":7865,"web_app_icon":6948,"other":6822,"home":4565,"photography":4267,"apparel":2170,"tips":1743,"diy_crafts":1612,"anime":1230,"beauty":1119,"wedding_events":1084,"funny":978,"desire":857,"architecture":825,"art":730,"industrial_design":727,"modeling_hair":654,"quotes":565,"film_music_books":532,"games":388,"people":323,"pets":267,"data_presentation":208,"fitness":140,"cars_motorcycles":138,"kids":88,"men":60,"education":40,"sports":19,"geek":10,"digital":2}}
     * pins : [{"pin_id":535263512,"user_id":311860,"board_id":1025547,"file_id":86962113,"file":{"farm":"farm1","bucket":"hbimg","key":"4545b302a60852592bed89614e3d19e86c5e65c530b01a-G3daBb","type":"image/png","width":1020,"height":5194,"frames":1},"media_type":0,"source":"huipinzhe.com","link":"http://huipinzhe.com","raw_text":"emart-12，家居，美食，女装，护肤，美妆，男装，鞋包，百货，服饰，Banner，广告","text_meta":{},"via":535213597,"via_user_id":610087,"original":535206526,"created_at":1448440915,"like_count":31,"comment_count":0,"repin_count":311,"is_private":0,"orig_source":null,"user":{"user_id":311860,"username":"无视雀念念","urlname":"c2yforever","created_at":1337563556,"avatar":{"id":2605311,"farm":"farm1","bucket":"hbimg","key":"003e86c74ed9bcc147f4ba7614e8fb728dce1f7911f06-5X5ZkK","type":"image/jpeg","width":400,"height":460,"frames":1}},"board":{"board_id":1025547,"user_id":311860,"title":"banner","description":"","category_id":"web_app_icon","seq":5,"pin_count":10330,"follow_count":14995,"like_count":128,"created_at":1338991608,"updated_at":1449222932,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":610087,"username":"n_ieckt","urlname":"j6gcpg6x84g","created_at":1344253705,"avatar":{"id":13852661,"farm":"farm1","bucket":"hbimg","key":"0d5fc643d5462c47bdc59ba1b143e132fd1f2cfb82bf-NDcFNV","type":"image/jpeg","width":580,"height":871,"frames":1}}},{"pin_id":527079964,"user_id":947407,"board_id":19318445,"file_id":75995703,"file":{"farm":"farm1","bucket":"hbimg","key":"8c148b07fd141d816350b0b597db1ebda0afae7de3ec-pu7PaA","type":"image/jpeg","width":736,"height":1106,"frames":1},"media_type":0,"source":null,"link":null,"raw_text":"","text_meta":{},"via":408713387,"via_user_id":17371418,"original":408713387,"created_at":1447755070,"like_count":20,"comment_count":0,"repin_count":122,"is_private":0,"orig_source":null,"user":{"user_id":947407,"username":"茵韵~~","urlname":"bbklezs0oq","created_at":1349696906,"avatar":{"id":76582688,"farm":"farm1","bucket":"hbimg","key":"752bc17f378d77d94eaa62d743b9613522ea298010393-pLrjjT","type":"image/jpeg","width":600,"height":900,"frames":1}},"board":{"board_id":19318445,"user_id":947407,"title":"唯有美食不可辜负","description":"","category_id":"food_drink","seq":27,"pin_count":2978,"follow_count":12302,"like_count":16,"created_at":1423535098,"updated_at":1449194777,"deleting":0,"is_private":0,"extra":null},"via_user":{"user_id":17371418,"username":"JJM北欧之家","urlname":"nodichome","created_at":1432913837,"avatar":{"id":81501350,"farm":"farm1","bucket":"hbimg","key":"91fb97fa71e398eb31a63b2bbddef4caf2d71261e642-ejISEV","type":"image/jpeg","width":1000,"height":1000,"frames":1}}}]
     * page : 1
     * category : null
     * ads : {"fixedAds":[],"normalAds":[]}
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * missing : 0
     * total : 156877
     * other : 0
     * results : {"food_drink":58957,"travel_places":32928,"design":18016,"illustration":7865,"web_app_icon":6948,"other":6822,"home":4565,"photography":4267,"apparel":2170,"tips":1743,"diy_crafts":1612,"anime":1230,"beauty":1119,"wedding_events":1084,"funny":978,"desire":857,"architecture":825,"art":730,"industrial_design":727,"modeling_hair":654,"quotes":565,"film_music_books":532,"games":388,"people":323,"pets":267,"data_presentation":208,"fitness":140,"cars_motorcycles":138,"kids":88,"men":60,"education":40,"sports":19,"geek":10,"digital":2}
     */

    private FacetsEntity facets;
    private int page;
    private Object category;
    private AdsEntity ads;
    /**
     * pin_id : 535263512
     * user_id : 311860
     * board_id : 1025547
     * file_id : 86962113
     * file : {"farm":"farm1","bucket":"hbimg","key":"4545b302a60852592bed89614e3d19e86c5e65c530b01a-G3daBb","type":"image/png","width":1020,"height":5194,"frames":1}
     * media_type : 0
     * source : huipinzhe.com
     * link : http://huipinzhe.com
     * raw_text : emart-12，家居，美食，女装，护肤，美妆，男装，鞋包，百货，服饰，Banner，广告
     * text_meta : {}
     * via : 535213597
     * via_user_id : 610087
     * original : 535206526
     * created_at : 1448440915
     * like_count : 31
     * comment_count : 0
     * repin_count : 311
     * is_private : 0
     * orig_source : null
     * user : {"user_id":311860,"username":"无视雀念念","urlname":"c2yforever","created_at":1337563556,"avatar":{"id":2605311,"farm":"farm1","bucket":"hbimg","key":"003e86c74ed9bcc147f4ba7614e8fb728dce1f7911f06-5X5ZkK","type":"image/jpeg","width":400,"height":460,"frames":1}}
     * board : {"board_id":1025547,"user_id":311860,"title":"banner","description":"","category_id":"web_app_icon","seq":5,"pin_count":10330,"follow_count":14995,"like_count":128,"created_at":1338991608,"updated_at":1449222932,"deleting":0,"is_private":0,"extra":null}
     * via_user : {"user_id":610087,"username":"n_ieckt","urlname":"j6gcpg6x84g","created_at":1344253705,"avatar":{"id":13852661,"farm":"farm1","bucket":"hbimg","key":"0d5fc643d5462c47bdc59ba1b143e132fd1f2cfb82bf-NDcFNV","type":"image/jpeg","width":580,"height":871,"frames":1}}
     */

    private List<PinsEntity> pins;

    public void setQuery(QueryEntity query) {
        this.query = query;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    public void setGift_count(int gift_count) {
        this.gift_count = gift_count;
    }

    public void setFacets(FacetsEntity facets) {
        this.facets = facets;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public void setAds(AdsEntity ads) {
        this.ads = ads;
    }

    public void setPins(List<PinsEntity> pins) {
        this.pins = pins;
    }

    public QueryEntity getQuery() {
        return query;
    }

    public int getPin_count() {
        return pin_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public int getPeople_count() {
        return people_count;
    }

    public int getGift_count() {
        return gift_count;
    }

    public FacetsEntity getFacets() {
        return facets;
    }

    public int getPage() {
        return page;
    }

    public Object getCategory() {
        return category;
    }

    public AdsEntity getAds() {
        return ads;
    }

    public List<PinsEntity> getPins() {
        return pins;
    }

    public static class QueryEntity {
        private String text;
        private String type;

        public void setText(String text) {
            this.text = text;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public String getType() {
            return type;
        }
    }

    public static class FacetsEntity {
        private int missing;
        private int total;
        private int other;
        /**
         * food_drink : 58957
         * travel_places : 32928
         * design : 18016
         * illustration : 7865
         * web_app_icon : 6948
         * other : 6822
         * home : 4565
         * photography : 4267
         * apparel : 2170
         * tips : 1743
         * diy_crafts : 1612
         * anime : 1230
         * beauty : 1119
         * wedding_events : 1084
         * funny : 978
         * desire : 857
         * architecture : 825
         * art : 730
         * industrial_design : 727
         * modeling_hair : 654
         * quotes : 565
         * film_music_books : 532
         * games : 388
         * people : 323
         * pets : 267
         * data_presentation : 208
         * fitness : 140
         * cars_motorcycles : 138
         * kids : 88
         * men : 60
         * education : 40
         * sports : 19
         * geek : 10
         * digital : 2
         */

        private ResultsEntity results;

        public void setMissing(int missing) {
            this.missing = missing;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setOther(int other) {
            this.other = other;
        }

        public void setResults(ResultsEntity results) {
            this.results = results;
        }

        public int getMissing() {
            return missing;
        }

        public int getTotal() {
            return total;
        }

        public int getOther() {
            return other;
        }

        public ResultsEntity getResults() {
            return results;
        }

        public static class ResultsEntity {
            private int food_drink;
            private int travel_places;
            private int design;
            private int illustration;
            private int web_app_icon;
            private int other;
            private int home;
            private int photography;
            private int apparel;
            private int tips;
            private int diy_crafts;
            private int anime;
            private int beauty;
            private int wedding_events;
            private int funny;
            private int desire;
            private int architecture;
            private int art;
            private int industrial_design;
            private int modeling_hair;
            private int quotes;
            private int film_music_books;
            private int games;
            private int people;
            private int pets;
            private int data_presentation;
            private int fitness;
            private int cars_motorcycles;
            private int kids;
            private int men;
            private int education;
            private int sports;
            private int geek;
            private int digital;

            public void setFood_drink(int food_drink) {
                this.food_drink = food_drink;
            }

            public void setTravel_places(int travel_places) {
                this.travel_places = travel_places;
            }

            public void setDesign(int design) {
                this.design = design;
            }

            public void setIllustration(int illustration) {
                this.illustration = illustration;
            }

            public void setWeb_app_icon(int web_app_icon) {
                this.web_app_icon = web_app_icon;
            }

            public void setOther(int other) {
                this.other = other;
            }

            public void setHome(int home) {
                this.home = home;
            }

            public void setPhotography(int photography) {
                this.photography = photography;
            }

            public void setApparel(int apparel) {
                this.apparel = apparel;
            }

            public void setTips(int tips) {
                this.tips = tips;
            }

            public void setDiy_crafts(int diy_crafts) {
                this.diy_crafts = diy_crafts;
            }

            public void setAnime(int anime) {
                this.anime = anime;
            }

            public void setBeauty(int beauty) {
                this.beauty = beauty;
            }

            public void setWedding_events(int wedding_events) {
                this.wedding_events = wedding_events;
            }

            public void setFunny(int funny) {
                this.funny = funny;
            }

            public void setDesire(int desire) {
                this.desire = desire;
            }

            public void setArchitecture(int architecture) {
                this.architecture = architecture;
            }

            public void setArt(int art) {
                this.art = art;
            }

            public void setIndustrial_design(int industrial_design) {
                this.industrial_design = industrial_design;
            }

            public void setModeling_hair(int modeling_hair) {
                this.modeling_hair = modeling_hair;
            }

            public void setQuotes(int quotes) {
                this.quotes = quotes;
            }

            public void setFilm_music_books(int film_music_books) {
                this.film_music_books = film_music_books;
            }

            public void setGames(int games) {
                this.games = games;
            }

            public void setPeople(int people) {
                this.people = people;
            }

            public void setPets(int pets) {
                this.pets = pets;
            }

            public void setData_presentation(int data_presentation) {
                this.data_presentation = data_presentation;
            }

            public void setFitness(int fitness) {
                this.fitness = fitness;
            }

            public void setCars_motorcycles(int cars_motorcycles) {
                this.cars_motorcycles = cars_motorcycles;
            }

            public void setKids(int kids) {
                this.kids = kids;
            }

            public void setMen(int men) {
                this.men = men;
            }

            public void setEducation(int education) {
                this.education = education;
            }

            public void setSports(int sports) {
                this.sports = sports;
            }

            public void setGeek(int geek) {
                this.geek = geek;
            }

            public void setDigital(int digital) {
                this.digital = digital;
            }

            public int getFood_drink() {
                return food_drink;
            }

            public int getTravel_places() {
                return travel_places;
            }

            public int getDesign() {
                return design;
            }

            public int getIllustration() {
                return illustration;
            }

            public int getWeb_app_icon() {
                return web_app_icon;
            }

            public int getOther() {
                return other;
            }

            public int getHome() {
                return home;
            }

            public int getPhotography() {
                return photography;
            }

            public int getApparel() {
                return apparel;
            }

            public int getTips() {
                return tips;
            }

            public int getDiy_crafts() {
                return diy_crafts;
            }

            public int getAnime() {
                return anime;
            }

            public int getBeauty() {
                return beauty;
            }

            public int getWedding_events() {
                return wedding_events;
            }

            public int getFunny() {
                return funny;
            }

            public int getDesire() {
                return desire;
            }

            public int getArchitecture() {
                return architecture;
            }

            public int getArt() {
                return art;
            }

            public int getIndustrial_design() {
                return industrial_design;
            }

            public int getModeling_hair() {
                return modeling_hair;
            }

            public int getQuotes() {
                return quotes;
            }

            public int getFilm_music_books() {
                return film_music_books;
            }

            public int getGames() {
                return games;
            }

            public int getPeople() {
                return people;
            }

            public int getPets() {
                return pets;
            }

            public int getData_presentation() {
                return data_presentation;
            }

            public int getFitness() {
                return fitness;
            }

            public int getCars_motorcycles() {
                return cars_motorcycles;
            }

            public int getKids() {
                return kids;
            }

            public int getMen() {
                return men;
            }

            public int getEducation() {
                return education;
            }

            public int getSports() {
                return sports;
            }

            public int getGeek() {
                return geek;
            }

            public int getDigital() {
                return digital;
            }
        }
    }

    public static class AdsEntity {
        private List<?> fixedAds;
        private List<?> normalAds;

        public void setFixedAds(List<?> fixedAds) {
            this.fixedAds = fixedAds;
        }

        public void setNormalAds(List<?> normalAds) {
            this.normalAds = normalAds;
        }

        public List<?> getFixedAds() {
            return fixedAds;
        }

        public List<?> getNormalAds() {
            return normalAds;
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
         * key : 4545b302a60852592bed89614e3d19e86c5e65c530b01a-G3daBb
         * type : image/png
         * width : 1020
         * height : 5194
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
        private Object orig_source;
        /**
         * user_id : 311860
         * username : 无视雀念念
         * urlname : c2yforever
         * created_at : 1337563556
         * avatar : {"id":2605311,"farm":"farm1","bucket":"hbimg","key":"003e86c74ed9bcc147f4ba7614e8fb728dce1f7911f06-5X5ZkK","type":"image/jpeg","width":400,"height":460,"frames":1}
         */

        private UserEntity user;
        /**
         * board_id : 1025547
         * user_id : 311860
         * title : banner
         * description :
         * category_id : web_app_icon
         * seq : 5
         * pin_count : 10330
         * follow_count : 14995
         * like_count : 128
         * created_at : 1338991608
         * updated_at : 1449222932
         * deleting : 0
         * is_private : 0
         * extra : null
         */

        private BoardEntity board;
        /**
         * user_id : 610087
         * username : n_ieckt
         * urlname : j6gcpg6x84g
         * created_at : 1344253705
         * avatar : {"id":13852661,"farm":"farm1","bucket":"hbimg","key":"0d5fc643d5462c47bdc59ba1b143e132fd1f2cfb82bf-NDcFNV","type":"image/jpeg","width":580,"height":871,"frames":1}
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

        public void setOrig_source(Object orig_source) {
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

        public Object getOrig_source() {
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
            /**
             * id : 2605311
             * farm : farm1
             * bucket : hbimg
             * key : 003e86c74ed9bcc147f4ba7614e8fb728dce1f7911f06-5X5ZkK
             * type : image/jpeg
             * width : 400
             * height : 460
             * frames : 1
             */

            private AvatarEntity avatar;

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
            private Object extra;

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

            public void setExtra(Object extra) {
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

            public Object getExtra() {
                return extra;
            }
        }

        public static class ViaUserEntity {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            /**
             * id : 13852661
             * farm : farm1
             * bucket : hbimg
             * key : 0d5fc643d5462c47bdc59ba1b143e132fd1f2cfb82bf-NDcFNV
             * type : image/jpeg
             * width : 580
             * height : 871
             * frames : 1
             */

            private AvatarEntity avatar;

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
        }
    }
}
