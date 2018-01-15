package sankemao.gankio.db;

import android.content.Context;
import android.content.res.AssetManager;

import com.blankj.utilcode.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description:TODO
 * Create Time: 2018/1/15.9:26
 * Author:jin
 * Email:210980059@qq.com
 */
public class Dat2Db {
    public static void readDat(final Context context, final String dbFileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetManager assetsManager = context.getAssets();
                String macBrandLine = null;
                try {
                    InputStream inputStream = assetsManager.open(dbFileName);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((macBrandLine = bufferedReader.readLine()) != null) {
                        String[] macBrandArray = macBrandLine.split("\\s+");
                        if (macBrandArray.length >= 3) {
                            MacBrand macBrand = new MacBrand(null, macBrandArray[0], macBrandArray[2]);
                            MacDbUtils.getInstance().insertMacBrand(macBrand);
                            LogUtils.d(macBrand.toString());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
