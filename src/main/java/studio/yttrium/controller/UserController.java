package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.annotation.PrivilegeInfo;
import studio.yttrium.pojo.DefaultResult;
import studio.yttrium.pojo.User;
import studio.yttrium.service.UserService;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/14
 * Time: 11:17
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {

        request.getSession().removeAttribute("loginUser");

        return "mobile/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult login(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("登录失败");

        String loginName = request.getParameter("loginName");
        String loginPassword = request.getParameter("loginPassword");

        System.out.println("接收到了" + loginName + "," + loginPassword);
        if (StringUtils.isNotBlank(loginName)) {
            User user = userService.findUserByLoginName(loginName);
            String psw = StringUtils.getSHA256(StringUtils.getMD5(loginPassword));

            if (user!=null && user.getLoginPassword()!=null && user.getLoginPassword().equals(psw)) {
                request.getSession().setAttribute("loginUser", user);
                result.setCode(1);
                result.setMessage("登录成功");
                result.setRedirect("/index.shtml");
            }

        }
        return result;
    }

    @RequestMapping(value = "getUserList", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult getUserList(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();


        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<User> userList = null;
        try {
            if (loginUser != null)
                userList = userService.getUserList(null);

            if (userList != null) {
                result.setCode(1);
                result.setMessage("获取成功");
                result.setData(userList);
            }
        }catch (Exception e){
            result.setCode(0);
            result.setMessage("获取失败");
        }

        return result;
    }

    @RequestMapping("accredit")
    @PrivilegeInfo(name = "login")
    public String accredit(HttpServletRequest request, Model view) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<User> userList = null;
        List<User> privilegeUserList = null;
        if (loginUser != null) {
            userList = userService.getUserList(loginUser.getId());
            privilegeUserList = userService.findUserLeftPrivilege(loginUser.getId());
        }
        if (userList != null) view.addAttribute("userList",userList);
        if (privilegeUserList != null) view.addAttribute("privilegeUserList",privilegeUserList);

        return "mobile/accredit";
    }

}
