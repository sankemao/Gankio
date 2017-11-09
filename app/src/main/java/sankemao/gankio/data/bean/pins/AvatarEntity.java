package sankemao.gankio.data.bean.pins;

/**
 * Description:TODO
 * Create Time: 2017/11/9.10:13
 * Author:jin
 * Email:210980059@qq.com
 */
public class AvatarEntity {

    /**
     * id : 120295835
     * farm : farm1
     * bucket : hbimg
     * key : 162245a2811d6475c92d2c6e0aaf259c854dd8161084d-Wpd72x
     * type : image/jpeg
     * width : 348
     * height : 310
     * frames : 1
     */

    private int id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private String width;
    private String height;
    private String frames;

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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFrames() {
        return frames;
    }

    public void setFrames(String frames) {
        this.frames = frames;
    }
}
