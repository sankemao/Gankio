package sankemao.baselib.ui.navigation;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

/**
 * @author: jin .
 * @time: 2017/10/18 10:17
 * @description: 通用的导航栏（传入viewGroup的id）
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder> {

    public DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    public static Builder begin(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return new Builder(context, layoutId, parent);
    }

    public static class Builder extends AbsNavigationBar.AbsBuilder<Builder> {
        public Builder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public AbsNavigationBar build() {
            return new DefaultNavigationBar(this);
        }
    }
}

