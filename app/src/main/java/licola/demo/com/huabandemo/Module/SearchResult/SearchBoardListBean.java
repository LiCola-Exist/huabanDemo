package licola.demo.com.huabandemo.Module.SearchResult;

import java.util.List;

import licola.demo.com.huabandemo.Entity.BoardPinsBean;
import licola.demo.com.huabandemo.Entity.ErrorBaseBean;

/**
 * Created by LiCola on  2016/04/05  16:42
 */
public class SearchBoardListBean extends ErrorBaseBean {

    /**
     * text : material design
     * type : board
     */

    private QueryBean query;
    /**
     * query : {"text":"material design","type":"board"}
     * pin_count : 4032
     * board_count : 460
     * people_count : 0
     * gift_count : 0
     * facets : {"missing":260,"total":200,"other":0,"results":{"web_app_icon":159,"design":15,"architecture":12,"other":7,"home":3,"travel_places":1,"industrial_design":1,"illustration":1,"data_presentation":1}}
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * missing : 260
     * total : 200
     * other : 0
     * results : {"web_app_icon":159,"design":15,"architecture":12,"other":7,"home":3,"travel_places":1,"industrial_design":1,"illustration":1,"data_presentation":1}
     */

    private FacetsBean facets;

    private List<BoardPinsBean> boards;

    public void setBoards(List<BoardPinsBean> pins) {
        this.boards = pins;
    }

    public List<BoardPinsBean> getBoards() {
        return boards;
    }

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
        /**
         * web_app_icon : 159
         * design : 15
         * architecture : 12
         * other : 7
         * home : 3
         * travel_places : 1
         * industrial_design : 1
         * illustration : 1
         * data_presentation : 1
         */

        private ResultsBean results;

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

        public ResultsBean getResults() {
            return results;
        }

        public void setResults(ResultsBean results) {
            this.results = results;
        }

        public static class ResultsBean {
            private int web_app_icon;
            private int design;
            private int architecture;
            private int other;
            private int home;
            private int travel_places;
            private int industrial_design;
            private int illustration;
            private int data_presentation;

            public int getWeb_app_icon() {
                return web_app_icon;
            }

            public void setWeb_app_icon(int web_app_icon) {
                this.web_app_icon = web_app_icon;
            }

            public int getDesign() {
                return design;
            }

            public void setDesign(int design) {
                this.design = design;
            }

            public int getArchitecture() {
                return architecture;
            }

            public void setArchitecture(int architecture) {
                this.architecture = architecture;
            }

            public int getOther() {
                return other;
            }

            public void setOther(int other) {
                this.other = other;
            }

            public int getHome() {
                return home;
            }

            public void setHome(int home) {
                this.home = home;
            }

            public int getTravel_places() {
                return travel_places;
            }

            public void setTravel_places(int travel_places) {
                this.travel_places = travel_places;
            }

            public int getIndustrial_design() {
                return industrial_design;
            }

            public void setIndustrial_design(int industrial_design) {
                this.industrial_design = industrial_design;
            }

            public int getIllustration() {
                return illustration;
            }

            public void setIllustration(int illustration) {
                this.illustration = illustration;
            }

            public int getData_presentation() {
                return data_presentation;
            }

            public void setData_presentation(int data_presentation) {
                this.data_presentation = data_presentation;
            }
        }
    }
}
