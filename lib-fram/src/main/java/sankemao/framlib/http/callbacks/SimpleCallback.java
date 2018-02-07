package sankemao.framlib.http.callbacks;

import android.content.Context;

import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.callback.EngineCallback;
import com.sankemao.quick.http.converter.Converter;
import com.sankemao.quick.http.exception.ServerDataTransformException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import sankemao.framlib.http.response.BaseResponse;

/**
 * Created by jin on 2017/7/23.
 * 项目通用的callback
 * 参考: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
 */
public abstract class SimpleCallback<T> implements EngineCallback {
    @Override
    public void onPreExecute(Context cxt, Map<String, Object> params) {

    }

    @Override
    public void onSuccess(final String resultStr) {
        Converter.Factory factory = GoHttp.getDefaultConfig().getFactory();
        if (factory == null) {
            throw new IllegalArgumentException("数据解析转换错误，请在初始化HttpConfig中配置解析工厂");
        }
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //获取泛型T的具体类型, 包括T自己的泛型 eg: baseResponse<userInfo>
        final Type type = params[0];

        if (type == String.class) {//T为String, 不解析, 直接返回String.
            onParseSuccess((T) resultStr);
            return;
        }

        Type rawType = null;
        //如果T是参数化泛型，即T可能为BaseResponse<UserInfo>
        if ((type instanceof ParameterizedType)) {
            rawType = ((ParameterizedType) type).getRawType();
        }

        try {
            Converter<String, T> converter = factory.responseConverter((Class<T>) type);
            T parseResult = converter.convert(resultStr);

            if (rawType == BaseResponse.class) {
                BaseResponse baseResponse = (BaseResponse) parseResult;
                final boolean isOk = baseResponse.isOk();
                if (isOk) {
                    onParseSuccess(((T) baseResponse));
                } else {
                    //TODO:此自定义异常
                    onError(new IllegalStateException());
                }
            } else {
                //如果T不是BaseResponse类型或者不是参数化泛型
                onParseSuccess(parseResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            serverTransformError(new ServerDataTransformException(e));
        }
    }

    /**
     * 回调到主线程中.
     *
     * @param result 可以直接操作的对象
     */
    public abstract void onParseSuccess(T result);

    /**
     * 数据解析出错，一般是服务器返回数据与预期不同
     */
    protected void serverTransformError(ServerDataTransformException e) {

    }

}
