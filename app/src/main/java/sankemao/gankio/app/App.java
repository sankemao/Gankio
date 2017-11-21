package sankemao.gankio.app;

import android.app.Application;
import android.os.Environment;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;

import okhttp3.logging.HttpLoggingInterceptor;
import sankemao.baselib.http.HttpUtils;
import sankemao.gankio.fix.FixDexManager;

/**
 * Description:application
 * Create Time:2017/9/30.10:36
 * Author:jin
 * Email:210980059@qq.com
 */
public class App extends Application {

    static String patchFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dex/";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Stetho.initializeWithDefaults(this);
        initHttp();

        fix();
    }

    private void fix() {
        //热修复
        FixDexManager fixDexManager = new FixDexManager(this);
        try {
            fixDexManager.fixDex(patchFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHttp() {
        HttpUtils.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        HttpUtils.setConnTimeOut(5);
        HttpUtils.supportHttps();
    }

}
