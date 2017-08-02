package cn.lonecloud.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lonecloud on 17/6/9.
 * @author lonecloud
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/index";
    }
}
