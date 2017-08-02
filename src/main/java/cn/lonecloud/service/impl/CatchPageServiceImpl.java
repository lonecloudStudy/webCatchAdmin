package cn.lonecloud.service.impl;

import cn.lonecloud.bean.UserData;
import cn.lonecloud.dao.UserDataMapper;
import cn.lonecloud.service.CatchPageService;
import cn.lonecloud.tools.CatchAllTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by lonecloud on 17/6/14.
 */
@Service
@Transactional
public class CatchPageServiceImpl implements CatchPageService {

    @Autowired
    UserDataMapper userDataMapper;


    @Override
    public void catchData(UserData userData) {
        userData.setCreatetime(new Date());
        CatchAllTool tools = new CatchAllTool(userData.getUrl(), 1);
        //创建线程进行工作
        new Thread(tools).start();
        userData.setPrecent(tools.getPresent());
        userData.setPath(tools.getPath());
        //插入
        userDataMapper.insert(userData);
    }
}
