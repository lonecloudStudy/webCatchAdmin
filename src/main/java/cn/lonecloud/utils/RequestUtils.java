package cn.lonecloud.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lonecloud on 17/5/13.
 */
public class RequestUtils {
    /**
     * 获取Request工具类
     * @return
     */
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取真实路径
     * @param path
     * @return
     */
    public static String getServletPath(String path){
        return getRequest().getServletContext().getRealPath(path);
    }

}
