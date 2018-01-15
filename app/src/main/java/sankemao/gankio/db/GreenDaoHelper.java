package sankemao.gankio.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import sankemao.gankio.app.App;
import sankemao.gankio.app.Constant;
import sankemao.gankio.db.gen.DaoMaster;
import sankemao.gankio.db.gen.DaoSession;

public class GreenDaoHelper extends App {
    private GreenDaoHelper Instance;
    private static DaoMaster daoMaster;
    private static DaoSession sDaoSession;
    private static DaoMaster.OpenHelper sHelper;

    public GreenDaoHelper getInstance() {
        if (Instance == null) {
            Instance = this;
        }
        return Instance;
    }

    /**
    * 获取DaoMaster
    * 是GreenDao的入口也是greenDao顶级对象,对于一个指定的表单持有数据库对象（SQLite数据库）并且能够管理DAO类
     能够创建表和删除表
     其内部类OpenHelper 与DevOpenHelper是创建SQlite数据库的SQLiteOpenHelper的具体实现
    */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            try{
                sHelper = new DaoMaster.DevOpenHelper(context, Constant.Db.DB_NAME,null);
                daoMaster = new DaoMaster(sHelper.getWritableDatabase()); //获取未加密的数据库
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return daoMaster;
    }

    /**
    * 获取DaoSession对象
     * 对于一个指定的表单可以管理所有的Dao 对象。
     也能够对实体类执行 insert ,load，update,refresh.delete操作。
     DaoSession也能跟踪 identity scope：即session查询后的实体会存在缓存中，并给该实体生成一个flag来追踪该实体，下次再次查询时会直接从缓存中取出来而不是从数据库中取出来
    */
    public static DaoSession getDaoSession(Context context) {

        if (sDaoSession == null) {
            if (daoMaster == null) {
                getDaoMaster(context);
            }
            sDaoSession = daoMaster.newSession();
        }
        return sDaoSession;
    }

    public void setDebug(){
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper(){
        if(sHelper != null){
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession(){
        if(sDaoSession != null){
            sDaoSession.clear();
            sDaoSession = null;
        }
    }

    public static Database getDatabase(Context cxt) {
        return getDaoMaster(cxt).getDatabase();
    }
}  