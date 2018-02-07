package sankemao.framlib.http.callbacks;

import android.content.Context;

import java.util.Map;

/**
 * Description: 统一处理网络请求时的loadingDialog
 * Create Time: 2018/2/7.13:11
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class DialogCallback<T> extends SimpleCallback<T> {
    @Override
    public void onPreExecute(Context cxt, Map<String, Object> params) {
        //显示dialog
    }

    @Override
    public void onError(Exception e) {
        //取消dialog
    }

    @Override
    public void onSuccess(String resultStr) {
        super.onSuccess(resultStr);
        //取消dialog
    }
}
