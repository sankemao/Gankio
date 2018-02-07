package sankemao.baselib.ui.keyboardadjust;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Description: 监听布局的高度变化
 * Create Time: 2017/11/29.14:55
 * Author:jin
 * Email:210980059@qq.com
 */
public class KeyboardListenLayout extends RelativeLayout{

    private onSizeChangedListener mChangedListener;

    public KeyboardListenLayout(Context context) {
        super(context);
    }

    public KeyboardListenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardListenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (null != mChangedListener && 0 != oldw && 0 != oldh) {
            boolean showKeyboard = h < oldh;
            mChangedListener.onChanged(showKeyboard, h, oldh);
        }
    }

    public void setOnSizeChangedListener(onSizeChangedListener listener) {
        mChangedListener = listener;
    }

    /**
     * 监听键盘弹出
     */
    public interface onSizeChangedListener {
        /**
         *
         * @param showKeyboard  键盘是否弹出
         * @param h             弹出之后的高度
         * @param oldh
         */
        void onChanged(boolean showKeyboard, int h, int oldh);
    }
}
