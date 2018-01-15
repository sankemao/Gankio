package sankemao.gankio.db;

import android.content.Context;

import java.util.List;

import sankemao.gankio.app.App;
import sankemao.gankio.db.gen.DaoSession;
import sankemao.gankio.db.gen.MacBrandDao;

/**
 * Description:TODO
 * Create Time: 2017/12/25.15:08
 * Author:jin
 * Email:210980059@qq.com
 */
public class MacDbUtils {

    private DaoSession mDaoSession;
    private MacBrandDao mMacBrandDao;
    //单例
    private static MacDbUtils mLocationDbUtils = new MacDbUtils(App.mContext);

    private MacDbUtils(Context cxt) {
        init(cxt.getApplicationContext());
    }

    public static MacDbUtils getInstance() {
        return mLocationDbUtils;
    }

    /**
     * 初始化
     */
    private void init(Context cxt) {
        mDaoSession = GreenDaoHelper.getDaoSession(cxt);
        //能够持久访问和查询实体类.
        //比起DaoSession有更多的持久化方法 count, loadAll, insertInt等等
        mMacBrandDao = mDaoSession.getMacBrandDao();
    }


    /**
     * 添加一条记录
     */
    public void insertMacBrand(MacBrand macBrand) {
        mMacBrandDao.insert(macBrand);
    }

    /**
     * 删除一条记录
     */
    public void deleteMacBrand(MacBrand macBrand) {
        mMacBrandDao.delete(macBrand);
    }

    /**
     * 修改一条记录
     */
    public void updateMacBrand(MacBrand macBrand) {
        mMacBrandDao.update(macBrand);
    }

    /**
     * 查询所有记录
     */
    public List<MacBrand> queryAllMacBrands() {
        return mMacBrandDao.loadAll();
    }

    /**
     * 分页查询
     * 按id倒序
     */
    public List<MacBrand> queryMacBrands(int offset, int limit){
        List<MacBrand> Locations = mMacBrandDao.queryBuilder()
                .orderDesc(MacBrandDao.Properties._id)
                .offset(offset)
                .limit(limit)
                .list();
        return Locations;
    }

    /**
     * 清空数据库
     * 先删除表, 再创建
     */
    public void wipeData() {
        MacBrandDao.dropTable(GreenDaoHelper.getDatabase(App.mContext), true);
        MacBrandDao.createTable(GreenDaoHelper.getDatabase(App.mContext), true);
    }
}
