package sankemao.gankio.model.bean.pins;

import java.io.Serializable;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:36
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsFileEntity implements Serializable{

    /**
     * id : 138839969
     * farm : farm1
     * bucket : hbimg
     * key : 39c28003e2af8033e5625ceec7e77396ab3ab3291b2fba-JCPGgB
     * type : image/jpeg
     * width : 990
     * height : 7172
     * frames : 1
     * colors : [{"color":16250871,"ratio":0.56},{"color":3355443,"ratio":0.11}]
     * theme : f7f7f7
     */

    private int id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int width;
    private int height;
    private String frames;
    private String theme;
    private List<ColorsBean> colors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFrames() {
        return frames;
    }

    public void setFrames(String frames) {
        this.frames = frames;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<ColorsBean> getColors() {
        return colors;
    }

    public void setColors(List<ColorsBean> colors) {
        this.colors = colors;
    }

    public static class ColorsBean implements Serializable{
        /**
         * color : 16250871
         * ratio : 0.56
         */

        private int color;
        private double ratio;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }
    }
}
