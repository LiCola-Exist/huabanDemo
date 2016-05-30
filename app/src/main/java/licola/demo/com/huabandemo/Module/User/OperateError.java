package licola.demo.com.huabandemo.Module.User;

/**
 * Created by LiCola on  2016/05/30  18:50
 */

public class OperateError {

    /**
     * err : 403
     * msg : 相同标题的画板已经存在
     * board_id : 17891564
     */

    private int err;
    private String msg;
    private int board_id;

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

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }
}
