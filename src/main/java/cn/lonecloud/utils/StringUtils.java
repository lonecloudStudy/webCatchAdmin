package cn.lonecloud.utils;

import java.util.UUID;

/**
 * Created by lonecloud on 17/4/30.
 */
public class StringUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    public static String getPath(){
       return  StringUtils.class.getClassLoader().getResource("").getPath();
    }
    public static String getWebPath(){
       return StringUtils.class.getClassLoader().getResource("").getPath().replace("/classes","");
    }
}
