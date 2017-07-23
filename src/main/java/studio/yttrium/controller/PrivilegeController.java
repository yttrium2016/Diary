package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.pojo.DefaultResult;
import studio.yttrium.pojo.User;
import studio.yttrium.service.PrivilegeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权利相关Controller
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/22
 * Time: 12:09
 */
@Controller
@RequestMapping("privilege")
public class PrivilegeController {

    @Resource
    private PrivilegeService privilegeService;

    /**
     * 删除权限的接口
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult delete(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        try {
            String idStr = request.getParameter("id");
            int userId = Integer.parseInt(idStr);
            if (loginUser != null) {
                int res = privilegeService.deletePrivilege(loginUser.getId(), userId);
                if (res > 0) {
                    result.setCode(1);
                    result.setMessage("删除成功");
                }
            }
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("删除失败");
        }

        return result;
    }

    /**
     * 添加权限的接口
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult add(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        try {
            String idStr = request.getParameter("id");
            int userId = Integer.parseInt(idStr);
            if (loginUser != null) {
                int count = privilegeService.findPrivilegeCount(loginUser.getId(), userId);
                if (count == 0) {
                    int res = privilegeService.addPrivilege(loginUser.getId(), userId);
                    if (res > 0) {
                        result.setCode(1);
                        result.setMessage("添加成功");
                    }
                }else {
                    result.setCode(0);
                    result.setMessage("已经存在权限");
                }
            }
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("添加失败");
        }

        return result;
    }
}
