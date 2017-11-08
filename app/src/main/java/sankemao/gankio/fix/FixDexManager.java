package sankemao.gankio.fix;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Description:TODO
 * Create Time:2017/11/7.23:15
 * Author:jin
 * Email:210980059@qq.com
 */
public class FixDexManager {
    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        //获取应用可以访问的dex目录
        mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     * @param fixDexPath  补丁所在文件夹(尽量是SD卡的路径, 否则会打开文件失败)
     */
    public void fixDex(String fixDexPath) throws Exception {


        //2. 获取下载好的补丁的dexElement
        //2.1 将补丁移动到系统能够访问的dex目录下, 最终转为ClassLoader.
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath + "不存在");
        }
        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            return;
        }
        copyFile(srcFile, destFile);

        //2.2 ClassLoader读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);

        fixDexFiles(fixDexFiles);
    }

    /**
     * 把dexElements 注入到classLoader中.
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception{
        //1. 获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //2. 获取pathList中的dexElements
        Field dexElementsField = Field.class.getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 从ClassLoader中获取dexElement
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {
        //1. 获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //2. 获取pathList中的dexElements
        Field dexElementsField = Field.class.getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        return dexElementsField.get(pathList);
    }

    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 合并两个dexElements数组
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        //这句值得学习.
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 加载所有的补丁包
     */
    public void loadFixDex() throws Exception{
        File[] dexFiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();

        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);

    }

    private void fixDexFiles(List<File> fixDexFiles) throws Exception{
        //1. 先获取已经运行的dexElement.
        ClassLoader applicationClassLoader = mContext.getClassLoader();
        Object applicationDexElements = getDexElementsByClassLoader(applicationClassLoader);

        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        //修复
        for (File fixDexFile : fixDexFiles) {
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    //dex路径
                    fixDexFile.getAbsolutePath(),
                    //解压路径
                    optimizedDirectory,
                    //so文件
                    null,
                    //父classLoader
                    applicationClassLoader
            );
            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);

            //3. 把补丁的dexElement插到已经运行的dexElement的最前面.
            //fixDexElements和dexElements两个数组合并.
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);
        }

        //把合并的数组, 注入到原来的类中 applicationClassLoader.
        injectDexElements(applicationClassLoader, applicationDexElements);
    }
}
