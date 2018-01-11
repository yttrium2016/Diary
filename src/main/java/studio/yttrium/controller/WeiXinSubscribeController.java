package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/11
 * Time: 15:40
 */
@Controller
@RequestMapping("wx")
public class WeiXinSubscribeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "wx";
    }

}
