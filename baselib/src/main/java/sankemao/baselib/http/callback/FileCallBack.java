package sankemao.baselib.http.callback;


import java.io.File;

import okhttp3.Response;
import sankemao.baselib.commonutils.ThreadUtil;
import sankemao.baselib.http.progress.FileConvert;

/**
 * Created by jin on 2017/8/31.
 *
 */
public abstract class FileCallBack {

    private FileConvert mFileConvert;

    public FileCallBack(String destFileName) {
        this(null, destFileName);
    }

    public FileCallBack(String destFileDir, String destFileName) {
        mFileConvert = new FileConvert(destFileDir, destFileName);
    }

    public void downError() {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onError();
            }
        });
    }

    public void downSuccess(final File file) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(file);
            }
        });
    }

    public abstract void onSuccess(File file);

    public abstract void onError();

    public abstract void onProgress(long currentBytes, long contentLength, boolean done);

    public File convertResponse(Response response) throws Throwable {
        File file = mFileConvert.convertResponse(response);
        response.close();
        return file;
    }
}
