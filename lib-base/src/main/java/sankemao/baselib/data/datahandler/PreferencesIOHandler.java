package sankemao.baselib.data.datahandler;

import android.support.annotation.Nullable;
import android.util.Base64;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jin on 2017/9/25.
 */
public class PreferencesIOHandler implements IOHandler {

    @Override
    public void save(String key, String value) {
        SPUtils.getInstance().put(key, value);
    }

    @Override
    public void save(String key, boolean value) {
        SPUtils.getInstance().put(key, value);
    }

    @Override
    public void save(String key, int value) {
        SPUtils.getInstance().put(key, value);
    }

    @Override
    public void save(String key, Object value) {
        if (!(value instanceof Serializable)) {
            throw new IllegalStateException(value.getClass().getSimpleName() + "must implements Serializable");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            String productBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            save(key, productBase64);
            LogUtils.e("save object success");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("save object error");
        }
    }

    @Override
    public void save(String key, float value) {
        SPUtils.getInstance().put(key, value);
    }

    @Override
    public void save(String key, long value) {
        SPUtils.getInstance().put(key, value);
    }

    @Override
    public String getString(String key, String def) {
        return SPUtils.getInstance().getString(key, def);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return SPUtils.getInstance().getBoolean(key, def);
    }

    @Override
    public int getInt(String key, int def) {
        return SPUtils.getInstance().getInt(key, def);
    }

    @Override
    public Object getObject(String key, @Nullable Object def) {
        String wordBase64 = getString(key, "");

        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);

        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            Object obj = bis.readObject();
            LogUtils.e("get object success");
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtils.e("get object error");
        return null;
    }

    @Override
    public float getFloat(String key, float def) {
        return SPUtils.getInstance().getFloat(key, def);
    }

    @Override
    public long getLong(String key, long def) {
        return SPUtils.getInstance().getLong(key, def);
    }

}
