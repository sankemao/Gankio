package sankemao.framlib.http.callbacks;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import sankemao.baselib.commonutils.ThreadUtil;
import sankemao.baselib.http.callback.EngineCallBack;

/**
 * Created by jin on 2017/7/22.
 *
 */
public abstract class DefaultCallBack<T> implements EngineCallBack {

    @Override
    public void onPreExecute(Context cxt, Map<String, Object> params) {
        //可以在这里添加一些共用参数.
        onPreExecute();
    }

    protected void onPreExecute(){

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        final T objResult = gson.fromJson(result, analysisClazzInfo(this));
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (objResult != null) {
                    onSuccess(objResult);
                }
            }
        });
    }

    /**
     * 解析一个类上面的class-泛型
     * 如果支持泛型，返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class，因为可能有多个，所以是数组。
     */
    public Type analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params[0];
    }

    /**
     * 返回可以直接操作的对象
     */
    public abstract void onSuccess(T result);
}
