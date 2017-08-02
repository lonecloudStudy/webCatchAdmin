package cn.lonecloud.utils;

/**
 * Created by lonecloud on 17/6/13.
 * 数组的工具类
 */
public class ArraysUtils {
    /**
     * 判断是否存在该种元素
     * @param src
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> boolean  isContains(T src,T[] arr){
        if (arr==null||arr.length==0)
            return false;
        for (T t:arr) {
            if (t.equals(src)){
                return true;
            }
        }
        return false;
    }
}
