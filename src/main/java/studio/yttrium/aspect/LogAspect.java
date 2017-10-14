package studio.yttrium.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import studio.yttrium.annotation.LogInfo;
import studio.yttrium.pojo.Log;
import studio.yttrium.pojo.User;
import studio.yttrium.service.LogService;
import studio.yttrium.utils.DateUtils;
import studio.yttrium.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 日志 记录 配合 @LogInfo 注解
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/10/13
 * Time: 13:55
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    private LogService logService;

    /**
     * 环绕通知 这个就够了
     *
     * @param point
     * @param logInfo
     * @return
     * @throws Throwable
     */
    @Around(value = "execution(* studio.yttrium.controller..*.*(..)) && @annotation(logInfo) ")
    public Object around(ProceedingJoinPoint point, LogInfo logInfo) throws Throwable {

        Log log = new Log();

        Object result = null;
        try {
            //前
            result = point.proceed();
            //后

            //我放在后面去执行
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            //读取session中的用户
            User user = (User) session.getAttribute("loginUser");
            if (user != null) {
                log.setUserId(user.getId());
                log.setUserName(user.getUserName());
            }
            //日志记录信息
            log.setMessage(StringUtils.isNotBlank(logInfo.message()) ? logInfo.message() : "");
            //日志记录时间
            log.setDateTime(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            //获取请求ip
            log.setIp(StringUtils.isNotBlank(request.getRemoteAddr()) ? request.getRemoteAddr() : "");
            //日志执行方法名
            log.setMethodName(StringUtils.isNotBlank(point.getSignature().getName()) ? point.getSignature().getName() : "");
            logService.addLog(log);
        }catch (Exception e){
            //异常
        }
        return result;
    }
}
