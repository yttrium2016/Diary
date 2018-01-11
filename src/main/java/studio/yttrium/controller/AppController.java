package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.pojo.AppResult;
import studio.yttrium.pojo.Diary;
import studio.yttrium.pojo.User;
import studio.yttrium.service.DiaryService;
import studio.yttrium.service.PrivilegeService;
import studio.yttrium.service.UserService;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 安卓端接口
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2018/1/1
 * Time: 22:51
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseApiController {

    @Resource
    private UserService userService;

    @Resource
    private DiaryService diaryService;

    @Resource
    private PrivilegeService privilegeService;

    /**
     * 登录的接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public AppResult login(HttpServletRequest request) {

        String loginName = request.getParameter("loginName");
        String loginPassword = request.getParameter("loginPassword");

        if (StringUtils.isNotBlank(loginName, loginPassword)) {
            User user = userService.findUserByLoginName(loginName);
            String psw = StringUtils.getSHA256(StringUtils.getMD5(loginPassword));

            if (user != null && user.getLoginPassword() != null && user.getLoginPassword().equals(psw)) {
                user.setLoginPassword("");
                return AppResult.success("登录成功").data(user);
            }
        }
        return AppResult.error("登录失败");
    }

    /**
     * 添加或者修改日记
     *
     * @param request HttpServletRequest
     * @param diary   日记
     * @return AppResult
     */
    @RequestMapping(value = "/diary/addOrEditDiary")
    @ResponseBody
    public AppResult addOrEditDiary(HttpServletRequest request, Diary diary) {

        if (!verifier(request)) {
            return AppResult.error("没有授权");
        }
        if (diary != null) {
            if (diary.getId() == null) {
                Date date = new Date();
                diary.setCreateBy(request.getIntHeader("userId"));
                diary.setCreateOn(date);
                diary.setModifyOn(date);
            } else {
                Date date = new Date();
                diary.setModifyOn(date);
            }

            int res = diaryService.saveOrUpdate(diary);

            if (res > 0) {
                return AppResult.success("操作成功");
            }
        }
        return AppResult.error("操作失败");
    }

    /**
     * 删除日记
     *
     * @param request HttpServletRequest
     * @return AppResult
     */
    @RequestMapping(value = "/diary/delete")
    @ResponseBody
    public AppResult delete(HttpServletRequest request) {
        try {
            if (!verifier(request)) {
                return AppResult.error("没有授权");
            }
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            int uid = Integer.parseInt(request.getHeader("userId"));
            int res = diaryService.updateDiaryByDelete(id, uid);
            if (res > 0) {
                return AppResult.success("删除成功");
            }
        } catch (Exception e) {
            return AppResult.error("删除出错" + e.getMessage());
        }
        return AppResult.error("删除失败");
    }


    /**
     * 查找列表
     *
     * @param request HttpServletRequest
     * @return AppResult
     */
    @RequestMapping(value = "/diary/query")
    @ResponseBody
    public AppResult getDiaryList(HttpServletRequest request) {
        try {
            if (!verifier(request)) {
                return AppResult.error("没有授权");
            }
            //0查自己 1查朋友
            String type = request.getParameter("type");
            String name = request.getParameter("name");
            String idStr = request.getHeader("userId");
            if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(idStr)) {
                int id = Integer.parseInt(idStr);
                List<Diary> diaryList = null;
                switch (type) {
                    case "0":
                        Diary diary = new Diary();
                        diary.setId(id);
                        if (StringUtils.isNotBlank(name)) {
                            diary.setTitle("%" + name.trim() + "%");
                        }
                        diaryList = diaryService.listDiary(diary);
                        if (diaryList != null) {
                            return AppResult.success("查找成功").data(diaryList);
                        }
                        break;
                    case "1":
                        List<Integer> privilegeList = privilegeService.findPrivilegeList(id);
                        if (privilegeList != null) {
                            diaryList = diaryService.listDiaryByUserIds(privilegeList, name);
                            if (diaryList != null) {
                                return AppResult.success("查找成功").data(diaryList);
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            return AppResult.error("查找出错" + e.getMessage());
        }
        return AppResult.error("查找失败");
    }

}
