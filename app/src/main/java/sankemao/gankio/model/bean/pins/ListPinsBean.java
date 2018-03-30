package sankemao.gankio.model.bean.pins;


import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:23
 * Author:jin
 * Email:210980059@qq.com
 */
public class ListPinsBean {
    private List<PinsMainEntity> pins;

    public void setPins(List<PinsMainEntity> pins) {
        this.pins = pins;
    }

    public List<PinsMainEntity> getPins() {
        return pins;
    }

}
