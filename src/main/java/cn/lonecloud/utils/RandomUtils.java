package cn.lonecloud.utils;

import java.util.UUID;

/**
 * Created by lonecloud on 17/6/16.
 * @Author lonecloud
 */
public class RandomUtils {
    /**
     * 返回UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
