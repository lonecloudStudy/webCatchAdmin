package cn.lonecloud.controller;

import cn.lonecloud.bean.CompanyInfo;
import cn.lonecloud.bean.UserData;
import cn.lonecloud.cfg.CompanyInfoContants;
import cn.lonecloud.service.CatchPageService;
import cn.lonecloud.tools.CatchPageTool;
import cn.lonecloud.utils.ExcelUtils;
import cn.lonecloud.utils.RandomUtils;
import cn.lonecloud.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by lonecloud on 17/6/14.
 * 抓取页面的控制层
 */
@Controller
public class CatchController {
    @Autowired
    CatchPageService catchPageService;

    @PostMapping("/url")
    public String getUrl(@RequestParam("url") String url, HttpServletRequest request, Model model) throws IOException, IllegalAccessException {
        UserData userData=new UserData();
        userData.setUrl(url);
        String uuid = RandomUtils.getUUID();
        userData.setDataid(uuid);
        catchPageService.catchData(userData);
        //设置数据
        model.addAttribute("dataId",uuid);
        return "/success";
    }
}
