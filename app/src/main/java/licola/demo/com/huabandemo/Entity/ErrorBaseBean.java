package licola.demo.com.huabandemo.Entity;

/**
 * Created by LiCola on  2016/05/30  18:50
 */

public class ErrorBaseBean {

    /**
     * err : 403
     * msg : 相同标题的画板已经存在
     * board_id : 17891564
     */

    private int err;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
