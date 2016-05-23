package licola.demo.com.huabandemo.Entity;

import java.util.List;

/**
 * Created by LiCola on  2016/05/06  18:06
 */
public class BoardItemInfoBean {

    /**
     * board_id : 17891564
     * user_id : 15246080
     * title : 主画板修改
     * is_private : 0
     * category_id : beauty
     * recommend_tags : ["荐女郎推广","诱惑","气质","街拍","比基尼","日韩"]
     * pin_count : 558
     * follow_count : 107
     * description :
     */

    private int board_id;
    private int user_id;
    private String title;
    private int is_private;
    private String category_id;
    private int pin_count;
    private int follow_count;
    private String description;
    private List<String> recommend_tags;

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

    public int getIs_private() {
        return is_private;
    }

    public void setIs_private(int is_private) {
        this.is_private = is_private;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRecommend_tags() {
        return recommend_tags;
    }

    public void setRecommend_tags(List<String> recommend_tags) {
        this.recommend_tags = recommend_tags;
    }
}
