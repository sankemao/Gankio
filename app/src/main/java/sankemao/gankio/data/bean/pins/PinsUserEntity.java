package sankemao.gankio.data.bean.pins;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:47
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsUserEntity implements Serializable{

    /**
     * user_id : 844104
     * username : 懒人茶--
     * urlname : hnnevaesot
     * created_at : 1347951357
     * avatar : {"id":157561603,"farm":"farm1","bucket":"hbimg","key":"da2e127146284d2762079590b629ca3b66fecafc21fec-Rp1d90","type":"image/jpeg","width":"500","height":"500","frames":"1"}
     * extra : null
     */

    private int user_id;
    private String username;
    private String urlname;
    private int created_at;
    private AvatarEntity avatar;

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

    public AvatarEntity getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
    }

}
