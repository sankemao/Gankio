package sankemao.gankio.ui.custom.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import sankemao.gankio.R;

/**
 * Description:TODO
 * Create Time: 2018/1/11.16:08
 * Author:jin
 * Email:210980059@qq.com
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {
    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.tabs;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = -Math.abs(dependency.getTranslationY());
        child.setTranslationY(translationY);
        return true;
    }
}
