package sankemao.baselib.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Description: 界面展示帮助类
 * Create Time: 2018/4/18.9:35
 * Author:jin
 * Email:210980059@qq.com
 */
public class DisplayUtil {
    /**
     * 设置全屏
     *
     * @param context
     */
    public static void setFullScreen(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(params);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    /**
     * 取消全屏
     *
     * @param context
     */
    public static void cancelFullScreen(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(params);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 判断是否全屏
     *
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    public static boolean isElevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }
}
