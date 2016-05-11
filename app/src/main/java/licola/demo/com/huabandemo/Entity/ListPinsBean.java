package licola.demo.com.huabandemo.Entity;

import java.util.List;

/**
 * Created by LiCola on  2015/12/04  20:02
 * 所有的图片列表基类
 */
public class ListPinsBean {

    /**
     * pin_id : 562785369
     * user_id : 17344184
     * board_id : 24048815
     * file_id : 89211799
     * file : {"farm":"farm1","bucket":"hbimg","key":"a2945bdf440f492c5144d24eebe45f23b82f3ff84d81f-4vhC2L","type":"image/jpeg","width":900,"height":1350,"frames":1}
     * media_type : 0
     * source : zcool.com.cn
     * link : http://www.zcool.com.cn/work/ZMTQ0Mzc0MTI=/2.html
     * raw_text : 日料日志————花漫
     * text_meta : {}
     * via : 562658878
     * via_user_id : 605533
     * original : 562658878
     * created_at : 1450779767
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * orig_source : http://img.zcool.cn/community/01eb1a567524a36ac725ad906a11e4.jpg@900w_1l_2o
     * user : {"user_id":17344184,"username":"蓝枫芊柔","urlname":"y0ogn4uezm","created_at":1432372599,"avatar":"https"}
     * board : {"board_id":24048815,"user_id":17344184,"title":"爱好","description":"","category_id":"food_drink","seq":5,"pin_count":1128,"follow_count":76,"like_count":3,"created_at":1432544067,"updated_at":1450779767,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"401845965"}}}
     * via_user : {"user_id":605533,"username":"宁馨郁金香","urlname":"xxf837568038","created_at":1344088961,"avatar":"https"}
     */

    private List<PinsMainEntity> pins;

    public void setPins(List<PinsMainEntity> pins) {
        this.pins = pins;
    }

    public List<PinsMainEntity> getPins() {
        return pins;
    }




}
