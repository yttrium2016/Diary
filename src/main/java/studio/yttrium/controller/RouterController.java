package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import studio.yttrium.annotation.PrivilegeInfo;

/**
 * 路由...比较蛋疼
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/6/19
 * Time: 11:24
 */

@Controller
public class RouterController {

    /**
     * 登录的路由
     * @return
     */
    @RequestMapping("login")
    public String login() {
        return "mobile/login";
    }


    /**
     * 注册的路由
     * @return
     */
    @RequestMapping("register")
    public String register() {
        return "mobile/register";
    }

    /**
     * 去主页的路由
     * @return
     */
    @PrivilegeInfo(name = "login")
    @RequestMapping("index")
    public String index() {
        return "mobile/index";
    }


    /**
     * 没有权限的页面 后来直接放弃了 直接404了
     * @return
     */
    @RequestMapping("noPermission")
    public ModelAndView noPermission() {
        ModelAndView noPermission = new ModelAndView("common/error");
        noPermission.addObject("title","没有权限");
        noPermission.addObject("message","不好意思,你没有访问的权限");
        return noPermission;
    }

}
