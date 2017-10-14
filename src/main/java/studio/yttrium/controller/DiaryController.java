package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.yttrium.annotation.LogInfo;
import studio.yttrium.annotation.PrivilegeInfo;
import studio.yttrium.pojo.DefaultResult;
import studio.yttrium.pojo.Diary;
import studio.yttrium.pojo.User;
import studio.yttrium.service.DiaryService;
import studio.yttrium.service.PrivilegeService;
import studio.yttrium.utils.LoggerUtils;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日记相关Controller
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/16
 * Time: 23:21
 */
@Controller
@RequestMapping("diary")
public class DiaryController {

    @Resource
    private DiaryService diaryService;

    @Resource
    private PrivilegeService privilegeService;

    /**
     * 添加或者编辑的页面转跳(添加用户和修改用户应该这么操作)
     * 需要login权限
     *
     * @param request
     * @param view
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "进入日记编写修改页面")
    @RequestMapping("addOrEdit")
    public String addOrEdit(HttpServletRequest request, Model view) {

        try {
            User user = (User) request.getSession().getAttribute("loginUser");
            String idStr = request.getParameter("id");
            if (StringUtils.isNotBlank(idStr)) {
                int id = Integer.parseInt(idStr);
                Diary diary = diaryService.getDiary(id, user.getId());
                if (diary != null) {
                    view.addAttribute("diary", diary);
                } else {
                    return "common/404";
                }
            }
        } catch (Exception e) {
            LoggerUtils.error(DiaryController.class, "addOrEdit 反正出错了", e);
        }

        return "mobile/add_edit_diary";
    }

    /**
     * 添加和编辑日记的接口
     *
     * @param request
     * @param diary
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "添加或者修改日记")
    @RequestMapping(value = "addOrEditDiary", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult addOrEditDiary(HttpServletRequest request, Diary diary) {
        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("失败");
        if (diary != null) {
            if (diary.getId() == null) {
                Date date = new Date();
                User user = (User) request.getSession().getAttribute("loginUser");
                if (user != null)
                    diary.setCreateBy(user.getId());
                diary.setCreateOn(date);
                diary.setModifyOn(date);
                System.out.println(diary.toString());

            } else {
                Date date = new Date();
                diary.setModifyOn(date);
            }

            int res = diaryService.saveOrUpdate(diary);

            if (res > 0) {
                result.setCode(1);
                result.setMessage("成功");
                result.setRedirect("/diary/addOrEdit.shtml?id=" + diary.getId());
            }
        }

        return result;
    }

    /**
     * 显示日记的页面转跳
     * 需要login权限
     *
     * @param request
     * @param view
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "查看日记")
    @RequestMapping("showDiary")
    public String showDiary(HttpServletRequest request, Model view) {

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            Diary diary = diaryService.getDiary(id, null);
            if (diary != null) {
                view.addAttribute("diary", diary);
            }
        } catch (Exception e) {
            LoggerUtils.error(DiaryController.class, "showDiary 反正出错了", e);
        }

        return "mobile/show";
    }

    /**
     * 删除日记的接口
     *
     * @param request
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "删除日记")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult delete(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setCode(0);
        result.setMessage("删除失败");

        try {
            String idStr = request.getParameter("id");
            User user = (User) request.getSession().getAttribute("loginUser");
            int id = Integer.parseInt(idStr);
            if (user != null) {
                int res = diaryService.updateDiaryByDelete(id, user.getId());
                if (res > 0) {
                    result.setCode(1);
                    result.setMessage("删除成功");
                }
            }
        } catch (Exception e) {
            LoggerUtils.error(this.getClass(), "删除出错", e);
        }

        return result;
    }

    /**
     * 显示日记列表的转跳
     * 需要login权限
     *
     * @param request
     * @param view
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "进入查看日记列表")
    @RequestMapping(value = "listDiary")
    public String listDiary(HttpServletRequest request, Model view) {


        String type = request.getParameter("type");
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        if (StringUtils.isNotBlank(type) && loginUser != null) {
            if ("my".equals(type.trim())) {
                view.addAttribute("type", "my");
                Diary diary = new Diary();
                diary.setId(loginUser.getId());
                List<Diary> diaryList = diaryService.listDiary(diary);
                if (diaryList != null) {
                    view.addAttribute("diaryList", diaryList);
                }
            } else if ("friend".equals(type.trim())) {
                view.addAttribute("type", "friend");
                List<Integer> privilegeList = new ArrayList<Integer>();
                Integer id = loginUser.getId();
                if (id != null)
                    privilegeList = privilegeService.findPrivilegeList(id);

                if (privilegeList != null && privilegeList.size() > 0) {
                    List<Diary> diaryList = diaryService.listDiaryByUserIds(privilegeList, "");
                    if (diaryList != null) {
                        view.addAttribute("diaryList", diaryList);
                    }
                }
            }
            return "mobile/list";
        } else {
            return "common/404";
        }

    }

    /**
     * 查找日记的接口(为了良好的用户体验 有点蠢)
     *
     * @param request
     * @return
     */
    @PrivilegeInfo(name = "login")
    @LogInfo(message = "获取日记列表")
    @RequestMapping(value = "getDiaryList", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult getDiaryList(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();
        result.setMessage("查找失败");
        result.setCode(0);

        String type = request.getParameter("type");
        String title = request.getParameter("title");
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        try {
            if (StringUtils.isNotBlank(type) && loginUser != null) {
                if ("my".equals(type.trim())) {

                    Diary diary = new Diary();
                    diary.setId(loginUser.getId());
                    if (StringUtils.isNotBlank(title)) {
                        diary.setTitle("%" + title.trim() + "%");
                    }
                    List<Diary> diaryList = diaryService.listDiary(diary);
                    if (diaryList != null) {
                        result.setData(diaryList);
                        result.setMessage("查找成功");
                        result.setCode(1);
                    }
                } else if ("friend".equals(type.trim())) {

                    List<Integer> privilegeList = privilegeService.findPrivilegeList(loginUser.getId());

                    if (privilegeList != null) {
                        List<Diary> diaryList = diaryService.listDiaryByUserIds(privilegeList, title.trim());
                        if (diaryList != null) {
                            result.setData(diaryList);
                            result.setMessage("查找成功");
                            result.setCode(1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtils.error(this.getClass(), "error:", e);
        }

        return result;
    }
}
