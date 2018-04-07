package sankemao.baselib.ui.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jin on 2017/7/11.
 *
 */
public class StatusbarUtil {

    /**
     * 设置状态栏颜色
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
        int statusBarHeightId = resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 当设置全屏时，防止内容顶上去
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

    /**设置状态栏透明与字体颜色*/
    public static void setStatusBarTrans(final Activity acitivty, boolean lightStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            acitivty.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = acitivty.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        ILightStatusBar IMPL;
        if (MIUILightStatusBarImpl.isMe()) {
            IMPL = new MIUILightStatusBarImpl();
        } else if (MeizuLightStatusBarImpl.isMe()) {
            IMPL = new MeizuLightStatusBarImpl();
        } else {
            IMPL = new ILightStatusBar() {
                @Override
                public void setLightStatusBar(Window window, boolean lightStatusBar) {
                }
            };
        }
        IMPL.setLightStatusBar(acitivty.getWindow(), lightStatusBar);
    }

    /**小米状态栏设置类*/
    public static class MIUILightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            return "Xiaomi".equals(Build.MANUFACTURER);
        }

        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            Class<? extends Window> clazz = window.getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, lightStatusBar ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {

            }
        }
    }

    /**魅族状态栏设置类*/
    public static class MeizuLightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            String displayId = Build.DISPLAY;
            if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
                String[] displayIdArray = displayId.split(" ");
                for (String temp : displayIdArray) {
                    //版本号4以上，形如4.x.
                    if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            WindowManager.LayoutParams params = window.getAttributes();
            try {
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(params);
                if (lightStatusBar) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(params, value);
                window.setAttributes(params);
                darkFlag.setAccessible(false);
                meizuFlags.setAccessible(false);
            } catch (Exception e) {

            }
        }
    }

    interface ILightStatusBar {
        void setLightStatusBar(Window window, boolean lightStatusBar);
    }
}
