package licola.demo.com.huabandemo.bean;

import java.util.List;
import java.util.Objects;

/**
 * Created by LiCola on  2015/12/21  13:03
 */
public class SearchHitBean {

    /**
     * total : 3
     * result : ["妹子","妹子图","妹子图分享"]
     */

    private int total;
    private List<String> result;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public List<String> getResult() {
        return result;
    }

    
}
