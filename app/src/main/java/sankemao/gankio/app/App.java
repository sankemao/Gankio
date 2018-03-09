package sankemao.gankio.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.blankj.utilcode.util.Utils;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.GoHttpConfig;
import sankemao.framlib.loadsir.EmptyCallback;
import sankemao.framlib.loadsir.ErrorCallback;
import sankemao.framlib.loadsir.LoadingCallback;
import sankemao.baselib.loadsir.core.LoadSir;

import okhttp3.logging.HttpLoggingInterceptor;
import sankemao.gankio.fix.FixDexManager;

/**
 * Description:application
 * Create Time:2017/9/30.10:36
 * Author:jin
 * Email:210980059@qq.com
 */
public class App extends Application {
    public static Context mContext;

    static String patchFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dex/";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Utils.init(this);
//        Stetho.initializeWithDefaults(this);
        initHttp();
        fix();
        //长图加载
        BigImageViewer.initialize(GlideImageLoader.with(this));
//        Dat2Db.readDat(this, "ncoui.dat");

        initLoadSir();
    }

    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new ErrorCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
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
        GoHttpConfig config = new GoHttpConfig.Builder()
                .setTimeout(5)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .supportHttps()
                .build();
        GoHttp.config(config);
    }

}
