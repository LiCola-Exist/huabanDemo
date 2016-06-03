package licola.demo.com.huabandemo.Module.SearchResult;

import java.util.List;

import licola.demo.com.huabandemo.Entity.ErrorBaseBean;

/**
 * Created by LiCola on  2016/04/05  20:02
 */
public class SearchPeopleListBean extends ErrorBaseBean{

    /**
     * text : 特立独行的猫
     * type : people
     */

    private QueryBean query;
    /**
     * query : {"text":"特立独行的猫","type":"people"}
     * pin_count : 138
     * board_count : 2
     * people_count : 34
     * gift_count : 0
     * users : [{"user_id":16211815,"username":"特立独行的猫2014","urlname":"teliduxingdemao","created_at":1415252603,"avatar":"图片地址","follower_count":2787,"following":0},{"user_id":188174,"username":"一只特立独行的猫","urlname":"vivi0507","created_at":1333682701,"avatar":"图片地址","follower_count":178,"following":null}]
     */

    private int pin_count;
    private int board_count;
    private int people_count;
    private int gift_count;
    /**
     * user_id : 16211815
     * username : 特立独行的猫2014
     * urlname : teliduxingdemao
     * created_at : 1415252603
     * avatar : 图片地址
     * follower_count : 2787
     * following : 0
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
        private int following;

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

        public int getFollowing() {
            return following;
        }

        public void setFollowing(int following) {
            this.following = following;
        }
    }
}
