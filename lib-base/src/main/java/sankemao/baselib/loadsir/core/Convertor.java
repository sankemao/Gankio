package sankemao.baselib.loadsir.core;


import sankemao.baselib.loadsir.callback.Callback;

/**
 * Description:TODO
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface Convertor<T> {
   /**
    * 将传入的.class文件转为class对象
    */
   Class<?extends Callback> map(T t);
}
