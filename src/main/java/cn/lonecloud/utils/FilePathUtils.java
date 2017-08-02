package cn.lonecloud.utils;

import java.io.File;

/**
 * Created by lonecloud on 17/6/14.
 */
public class FilePathUtils {

    private static String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public static String getTmpPath(){
        if (path!=null&&path.lastIndexOf("/")==path.length()){
            return path;
        }else {
            return path+ File.separator;
        }

    }
}
