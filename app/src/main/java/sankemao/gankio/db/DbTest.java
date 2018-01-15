package sankemao.gankio.db;

/**
 * Description:TODO
 * Create Time: 2018/1/15.9:53
 * Author:jin
 * Email:210980059@qq.com
 */
public class DbTest {
    public static void main(String[] args) {
        String testBlank = "aaa   aaa aa      aaaa";
        String[] stringArray = testBlank.split("\\s+");
        System.out.println(stringArray.length);
    }
}
