package sankemao.gankio.app;

import android.os.Environment;

import java.io.File;

/**
 * Description:TODO
 * Create Time:2017/11/8.23:15
 * Author:jin
 * Email:210980059@qq.com
 */
public class Constant {
    public static final class Type {
        //所有
        public static final String ALL = "all";

        //家居
        public static final String HOME = "home";

        //美女
        public static final String BEAUTY = "beauty";

        //动漫
        public static final String ANIME = "anime";

        //旅行
        public static final String TRAVEL_PLACES = "travel_places";

        //宠物
        public static final String PETS = "pets";

    }

    public static final class Http {
        //每次加载条目数限制
        public static final int LIMIT = 20;
        public static final int DEFAULT_VALUE_MINUS_ONE = -1;

        //用于生成图片地址
        public static final String URL_GENERAL_FORMAT = "http://img.hb.aicdn.com/%s_fw320sf";
        //大图的后缀
        public static final String FORMAT_URL_IMAGE_BIG = "http://img.hb.aicdn.com/%s_fw658";
        //小图的后缀
        public static final String FORMAT_URL_IMAGE_SMALL = "http://img.hb.aicdn.com/%s_sq75sf";
    }

    public static final class Commom {
        public static final String APP_DIR =
                Environment.getExternalStorageDirectory().getPath() + File.separator + App.mContext.getPackageName();
    }

    public static final class Db {
        public static final String DB_NAME = "mac.db";
    }
}
