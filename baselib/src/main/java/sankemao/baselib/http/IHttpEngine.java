package sankemao.baselib.http;

import android.content.Context;

import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Response;
import sankemao.baselib.http.callback.EngineCallBack;
import sankemao.baselib.http.callback.FileCallBack;

/**
 * Created by jin on 2017/7/16.
 * 引擎的规范
 */
public interface IHttpEngine {

    //添加拦截器
    void addInterceptor(Interceptor interceptor);

    //忽略https
    void supportHttps();

    //get请求
    void get(boolean cache, Context context, String url, Map<String, Object> params, Map<String, String> headers, EngineCallBack callBack);

    //post请求
    void post(boolean cache, Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //同步请求.
    Response get(Context context, String url, Map<String, Object> params);

    //取消请求.
    void cancelRequest(Object tag);

    //下载文件
    void downFile(Context context, String url, FileCallBack callBack);

    //上传文件

    //https 添加证书.

    //设置超时
    void setConnTimeOut(int timeOut);

}
