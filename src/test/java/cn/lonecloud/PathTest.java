package cn.lonecloud;

import cn.lonecloud.utils.FilePathUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * Created by lonecloud on 17/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:springMvc.xml"})
@WebAppConfiguration
public class PathTest {

    @Resource
    FilePathUtils filePathUtils;

    /**
     * 测试项目路径是否可以读取到
     */
    @Test
    public void getPath(){
        String path = filePathUtils.getPath();
        System.out.println(path);
    }

}
