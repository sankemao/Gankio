package sankemao.baselib.http.progress;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import sankemao.baselib.http.callback.FileCallBack;

/**
 * Created by jin on 2017/9/1.
 */
public class DownInterceptor implements Interceptor {

    private final FileCallBack mFileCallBack;

    public DownInterceptor(FileCallBack fileCallBack) {
        this.mFileCallBack = fileCallBack;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截
        Response originalResponse = chain.proceed(chain.request());
        //包装响应体并返回
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), mFileCallBack))
                .build();
    }
}
