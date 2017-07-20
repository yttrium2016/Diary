package studio.yttrium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PrivilegeInfo(name = "login")
    @RequestMapping("addOrEdit")
    public String addOrEdit(HttpServletRequest request, Model view) {

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            Diary diary = diaryService.getDiary(id);
            if (diary != null) {
                view.addAttribute("diary", diary);
            }
        } catch (Exception e) {
            LoggerUtils.error(DiaryController.class, "addOrEdit 反正出错了", e);
        }

        return "mobile/add_edit_diary";
    }

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

    @PrivilegeInfo(name = "login")
    @RequestMapping("showDiary")
    public String showDiary(HttpServletRequest request, Model view) {

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            Diary diary = diaryService.getDiary(id);
            if (diary != null) {
                view.addAttribute("diary", diary);
            }
        } catch (Exception e) {
            LoggerUtils.error(DiaryController.class, "showDiary 反正出错了", e);
        }

        return "mobile/show";
    }

    @RequestMapping(value = "getDiary", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult getDiary(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            Diary diary = diaryService.getDiary(id);
            if (diary != null) {
                result.setData(diary);
                result.setCode(1);
                result.setMessage("操作成功");
            }
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("操作失败");
        }

        return result;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public DefaultResult delete(HttpServletRequest request) {

        DefaultResult result = new DefaultResult();

        try {
            String idStr = request.getParameter("id");
            int id = Integer.parseInt(idStr);
            int res = diaryService.updateDiaryByDelete(id);
            if (res > 0) {
                result.setCode(1);
                result.setMessage("删除成功");
            }
        } catch (Exception e) {
            result.setCode(0);
            result.setMessage("删除失败");
        }

        return result;
    }

    @PrivilegeInfo(name = "login")
    @RequestMapping(value = "listDiary")
    public String listDiary(HttpServletRequest request, Model view) {


        String type = request.getParameter("type");
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        if (StringUtils.isNotBlank(type) && loginUser != null) {
            if ("my".equals(type.trim())) {
                view.addAttribute("type","my");
                Diary diary = new Diary();
                diary.setId(loginUser.getId());
                List<Diary> diaryList = diaryService.listDiary(diary);
                if (diaryList != null) {
                    view.addAttribute("diaryList", diaryList);
                }
            } else if ("friend".equals(type.trim())){
                view.addAttribute("type","friend");
                List<Integer> privilegeList = new ArrayList<Integer>();
                Integer id = loginUser.getId();
                if (id != null)
                privilegeList =  privilegeService.findPrivilegeList(id);

                if (privilegeList != null) {
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

    @RequestMapping(value = "getDiaryList",method = RequestMethod.POST)
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
                    if (StringUtils.isNotBlank(title)){
                        diary.setTitle("%"+title.trim()+"%");
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
