package sankemao.gankio.ui.custom.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.blankj.utilcode.util.LogUtils;

/**
 * Description:TODO
 * Create Time: 2018/1/12.9:39
 * Author:jin
 * Email:210980059@qq.com
 */
public class MyViewGroup extends ScrollView {

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        LogUtils.e("aaa", "MyViewGroup1");
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtils.e("aaa", "MyViewGroup2");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.e("aaa", "onAttachedToWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.e("aaa", "onDraw");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.e("aaa", "onFinishInflate");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.e("aaa", "onSizeChanged");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.e("aaa", "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.e("aaa", "onLayout");
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LogUtils.e("aaa", "generateDefaultLayoutParams");
        return super.generateDefaultLayoutParams();
    }
}
