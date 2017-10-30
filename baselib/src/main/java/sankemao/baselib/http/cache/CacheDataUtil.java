package sankemao.baselib.http.cache;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import sankemao.baselib.http.db.DaoSupportFactory;
import sankemao.baselib.http.db.IDaoSupport;

/**
 * @author jin
 */
public class CacheDataUtil {

    /**
     * 获取数据
     */
    public static String getCacheResultJson(String finalUrl) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        // 需要缓存，从数据库拿缓存，问题又来了，OkHttpEngine  BaseLibrary
        // 数据库缓存在 FrameLibrary
        List<CacheData> cacheDatas = dataDaoSupport.querySupport()
                // finalUrl http:w 报错  finalUrl -> MD5处理
                .selection("mUrlKey = ?").selectionArgs(EncryptUtils.encryptMD5ToString(finalUrl)).query();

        if (cacheDatas.size() != 0) {
            // 代表有数据
            CacheData cacheData = cacheDatas.get(0);
            String resultJson = cacheData.getResultJson();

            return resultJson;
        }
        return null;
    }

    /**
     * 缓存数据
     */
    public static long cacheData(String finalUrl, String resultJson) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().
                getDao(CacheData.class);
        dataDaoSupport.delete("mUrlKey=?", EncryptUtils.encryptMD5ToString(finalUrl));
        long number = dataDaoSupport.insert(new CacheData(EncryptUtils.encryptMD5ToString(finalUrl), resultJson));
        LogUtils.e("number --> " + number);
        return number;
    }
}
