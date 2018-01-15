package sankemao.gankio.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Description:TODO
 * Create Time: 2018/1/15.9:10
 * Author:jin
 * Email:210980059@qq.com
 */
@Entity
public class MacBrand {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "MAC")
    private String mac;
    @Property(nameInDb = "MACBRAND")
    private String macBrand;

    @Generated(hash = 1540364034)
    public MacBrand(Long _id, String mac, String macBrand) {
        this._id = _id;
        this.mac = mac;
        this.macBrand = macBrand;
    }

    @Generated(hash = 704325698)
    public MacBrand() {
    }

    @Override
    public String toString() {
        return "MacBrand{" +
                "_id=" + _id +
                ", mac='" + mac + '\'' +
                ", macBrand='" + macBrand + '\'' +
                '}';
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMacBrand() {
        return this.macBrand;
    }

    public void setMacBrand(String macBrand) {
        this.macBrand = macBrand;
    }
}
