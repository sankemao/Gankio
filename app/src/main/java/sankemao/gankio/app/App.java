package sankemao.gankio.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Description:application
 * Create Time:2017/9/30.10:36
 * Author:jin
 * Email:210980059@qq.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

}
