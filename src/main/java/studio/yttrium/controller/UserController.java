package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.annotation.LogInfo;
import studio.yttrium.annotation.PrivilegeInfo;
import studio.yttrium.pojo.DefaultResult;
import studio.yttrium.pojo.User;
import studio.yttrium.service.UserService;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关Controller
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


    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("logout")
    @LogInfo(message = "退出登录")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUser");
        return "mobile/login";
    }

    /**
     * 登录的接口
     * @param request
     * @return
     */
    @LogInfo(message = "登录日记本")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult login(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("登录失败");

        String loginName = request.getParameter("loginName");
        String loginPassword = request.getParameter("loginPassword");

        if (StringUtils.isNotBlank(loginName, loginPassword)) {
            User user = userService.findUserByLoginName(loginName);
            String psw = StringUtils.getSHA256(StringUtils.getMD5(loginPassword));

            if (user != null && user.getLoginPassword() != null && user.getLoginPassword().equals(psw)) {
                request.getSession().setAttribute("loginUser", user);
                result.setCode(1);
                result.setMessage("登录成功");
                result.setRedirect("/index.shtml");
            }

        }
        return result;
    }


    /**
     * 注册的接口
     * @param request
     * @return
     */
    @LogInfo(message = "日记本注册")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult register(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("注册失败");

        String loginName = request.getParameter("loginName");
        String userName = request.getParameter("userName");
        String loginPassword = request.getParameter("loginPassword");
        String rePassword = request.getParameter("rePassword");

        if (StringUtils.isBlank(loginName, userName, loginPassword, rePassword)) {
            result.setMessage("所填项不能为空");
            return result;
        } else {

            User dbUser = userService.findUserByLoginName(loginName);
            if (dbUser != null) {
                result.setMessage("登录名已存在");
                return result;
            }

            if (!loginPassword.equals(rePassword)) {
                result.setMessage("两次密码不一致");
                return result;
            }

            User user = new User();
            String psw = StringUtils.getSHA256(StringUtils.getMD5(loginPassword));
            user.setLoginName(loginName);
            user.setLoginPassword(psw);
            user.setUserName(userName);
            int res = userService.addUser(user);
            if (res > 0) {
                result.setCode(1);
                result.setMessage("注册成功");
                result.setRedirect("/login.shtml");
            }

        }
        return result;
    }

    /**
     * 修改用户的接口(本来应该和注册写在一起的后来懒的改了)
     * @param request
     * @return
     */
    @LogInfo(message = "修改用户信息")
    @PrivilegeInfo(name = "login")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult edit(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("修改失败");

        String loginName = request.getParameter("loginName");
        String userName = request.getParameter("userName");
        String loginPassword = request.getParameter("loginPassword");
        String rePassword = request.getParameter("rePassword");

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (!StringUtils.isNotBlank(loginName, userName)) {
            if (!StringUtils.isNotBlank(loginPassword, rePassword)) {
                result.setMessage("全为空就别改了嘛");
                return result;
            }
        }

        if (StringUtils.isNotBlank(loginPassword, rePassword)) {
            if (!loginPassword.equals(rePassword)) {
                result.setMessage("两次密码不一致");
                return result;
            } else {
                String psw = StringUtils.getSHA256(StringUtils.getMD5(loginPassword));
                loginUser.setLoginPassword(psw);
            }
        }

        if (StringUtils.isNotBlank(loginName) && !loginUser.getLoginName().equals(loginName)) {
            User dbUser = userService.findUserByLoginName(loginName);
            if (dbUser != null) {
                result.setMessage("登录名字已经存在");
                return result;
            } else {
                loginUser.setLoginName(loginName);
            }
        }

        if (StringUtils.isNotBlank(userName)) {
            loginUser.setUserName(userName);
        }

        int res = userService.editUser(loginUser);
        if (res > 0) {
            result.setCode(1);
            result.setMessage("修改成功");
            result.setRedirect("/index.shtml");
        }

        return result;
    }

    /**
     * 获取用户列表的接口
     * @param request
     * @return
     */
    @LogInfo(message = "获取用户列表")
    @PrivilegeInfo(name = "login")
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
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("获取失败");
        }

        return result;
    }

    /**
     * 授权的页面转跳
     *  需要login的权限
     * @param request
     * @param view
     * @return
     */
    @LogInfo(message = "进入用户授权界面")
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
        if (userList != null) view.addAttribute("userList", userList);
        if (privilegeUserList != null) view.addAttribute("privilegeUserList", privilegeUserList);

        return "mobile/accredit";
    }

    /**
     * 编辑用户的转跳
     *  需要login的权限
     * @param request
     * @param view
     * @return
     */
    @LogInfo(message = "进入用户编辑界面")
    @RequestMapping("editUser")
    @PrivilegeInfo(name = "login")
    public String editUser(HttpServletRequest request, Model view) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            view.addAttribute("user", loginUser);
        }
        return "mobile/edit_user";
    }

}
