package com.sankemao.quick.http.utils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:TODO
 * Create Time: 2018/1/23.18:07
 * Author:jin
 * Email:210980059@qq.com
 */
public class Utils {
    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            // 不包含 ? 直接添加一个
            stringBuffer.append("?");
        }
        if (!url.endsWith("?")) {
            // 如果不是以 ? 结尾
            stringBuffer.append("&");
        }
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class-泛型
     * 如果支持泛型，返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class，因为可能有多个，所以是数组。
     */
    public static Type analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params[0];
    }

    public static void closeIo(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
