package sankemao.baselib.ui.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jin on 2017/7/11.
 * https://github.com/QMUI/QMUI_Android/blob/da32f6be0462c908de5f842d4347da81c9144727/qmui/src/main/java/com/qmuiteam/qmui/util/QMUIStatusBarHelper.java#L119
 */
public class StatusbarUtil {

    /**
     * @deprecated 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 首先把他弄成全屏，在状态栏的部分加一个布局
            // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 电量 时间 网络状态 都还在
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);

            //  android:fitsSystemWindows="true" 每个布局都要写
            //  DecorView是一个 FrameLayout 布局 , 会加载一个系统的布局（LinearLayout)
            // 在系统布局中会有一个 id 为 android.R.id.content 这布局是（RelativeLayout）
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            //获取activity中setContentView布局的根布局.
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
    }

    /**
     * 调用前提，需在已设置了全屏模式下进行
     * 如果没有效果，配合AutoLinearLayout注解使用
     */
    public static void setFakeStatusView(Activity activity, ViewGroup contentView, @ColorInt int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //如果小于4.4，貌似不支持全屏？所以就不添加了。
            return;
        }
        View fakeStatusView = new View(contentView.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        fakeStatusView.setLayoutParams(params);
        fakeStatusView.setBackgroundColor(color);
        if (contentView instanceof LinearLayout) {
            contentView.addView(fakeStatusView, 0);
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 当设置全屏时，防止内容顶上去
     *
     * @param activity
     */
    public static void setDecorViewPadding(Activity activity) {
        //获取activity中setContentView布局的根布局.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
    }

    /**
     * 设置titleBar距顶部的高度，防止顶到上去
     */
    public static void setTitlePadding(Activity activity, View view) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int addHeight = getStatusBarHeight(activity);
        if (lp != null && lp.height > 0) {
            lp.height += addHeight;//增高
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + addHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 设置状态栏背景透明状态栏字体颜色
     */
    public static void setStatusBarTrans(final Activity activity, boolean lightStatusBar) {
        //实现状态栏背景透明，支持4.4以上版本的 MIUI 和 Flyme，以及 5.0 以上版本的其他 Android。

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && supportTransclentStatusBar6()) {
                // android 6以后可以改状态栏字体颜色，因此可以自行设置为透明
                // ZUK Z1是个另类，自家应用可以实现字体颜色变色，但没开放接口
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                // android 5不能修改状态栏字体颜色，因此直接用FLAG_TRANSLUCENT_STATUS，nexus表现为半透明
                // 魅族和小米的表现如何？
                // update: 部分手机运用FLAG_TRANSLUCENT_STATUS时背景不是半透明而是没有背景了。。。。。
                // window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                // 采取setStatusBarColor的方式，部分机型不支持，那就纯黑了，保证状态栏图标可见
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(0x40000000);
            }
        }
        //设置状态栏字体颜色
        ILightStatusBar IMPL = null;
        if (MIUILightStatusBarImpl.isMe()) {
            IMPL = new MIUILightStatusBarImpl();
        } else if (MeizuLightStatusBarImpl.isMe()) {
            IMPL = new MeizuLightStatusBarImpl();
        } else if (Android6LightStatusBarImpl.isMe()) {
            IMPL = new Android6LightStatusBarImpl();
        }
        if (IMPL != null) {
            IMPL.setLightStatusBar(activity.getWindow(), lightStatusBar);
        }
    }

    /**
     * 小米状态栏设字体颜色置类
     */
    public static class MIUILightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            return DeviceUtil.isMIUI();
        }

        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            if (!MIUISetStatusBarLightMode(window, lightStatusBar)) {
                //如果设置失败，即miui v9版本，换成原生android状态栏设置
                Android6SetStatusBarLightMode(window, lightStatusBar);
            }
        }
    }

    /**
     * 魅族状态栏字体颜色设置类
     */
    public static class MeizuLightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            return DeviceUtil.isFlyme();
        }

        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            // flyme 在 6.2.0.0A 支持了 Android 官方的实现方案，旧的方案失效
            Android6SetStatusBarLightMode(window, lightStatusBar);
            FlyMeSetStatusBarLightMode(window, lightStatusBar);
        }
    }

    /**
     * android6.0以上原生设置状态栏字体颜色
     */
    public static class Android6LightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        }

        @Override
        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            Android6SetStatusBarLightMode(window, lightStatusBar);
        }
    }

    interface ILightStatusBar {
        void setLightStatusBar(Window window, boolean lightStatusBar);
    }

    /**
     * MIUI系统设置状态栏背景颜色
     */
    @SuppressWarnings("unchecked")
    public static boolean MIUISetStatusBarLightMode(Window window, boolean light) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (light) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * flyme设置状态栏颜色
     *
     * @param window
     * @param light
     * @return
     */
    public static boolean FlyMeSetStatusBarLightMode(Window window, boolean light) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (light) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * Android 6设置状态栏背景颜色
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     */
    @TargetApi(23)
    private static void Android6SetStatusBarLightMode(Window window, boolean light) {
        View decorView = window.getDecorView();
        int systemUi = light ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        systemUi = changeStatusBarModeRetainFlag(window, systemUi);
        decorView.setSystemUiVisibility(systemUi);
//        if(DeviceUtil.isMIUIV9()){
//            // MIUI 9 低于 6.0 版本依旧只能回退到以前的方案
//            // https://github.com/QMUI/QMUI_Android/issues/160
//            MIUISetStatusBarLightMode(window, light);
//        }
    }

    @TargetApi(23)
    private static int changeStatusBarModeRetainFlag(Window window, int out) {
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        return out;
    }

    public static int retainSystemUiFlag(Window window, int out, int type) {
        int now = window.getDecorView().getSystemUiVisibility();
        if ((now & type) == type) {
            out |= type;
        }
        return out;
    }

    /**
     * 是否支持沉浸式状态栏
     *
     * @return
     */
    private static boolean supportTranslucent() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                // Essential Phone 不支持沉浸式，否则系统又不从状态栏下方开始布局又给你下发 WindowInsets
                && !Build.BRAND.toLowerCase().contains("essential");
    }

    /**
     * 检测 Android 6.0 是否可以启用 window.setStatusBarColor(Color.TRANSPARENT)。
     */
    public static boolean supportTransclentStatusBar6() {
        return !(DeviceUtil.isZUKZ1() || DeviceUtil.isZTKC2016());
    }
}
