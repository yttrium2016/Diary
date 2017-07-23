package studio.yttrium.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import studio.yttrium.annotation.PrivilegeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器 配合 注解 实现登录的权限控制 其实纯属多余
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/16
 * Time: 21:28
 */
public class PrivilegeInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从传入的handler中检查是否有AuthCheck的声明
        HandlerMethod method = (HandlerMethod)handler;
        PrivilegeInfo info = method.getMethodAnnotation(PrivilegeInfo.class);

        //找到了，取出定义的权限属性，结合身份信息进行检查
        if(info != null) {
            String name = info.name();

            //根据type与write，结合session/cookie等身份信息进行检查
            //如果权限检查不通过，可以输出特定信息、进行跳转等操作
            //并且一定要return false，表示被拦截的方法不用继续执行了
            if ("login".equals(name)){
                Object loginUser = request.getSession().getAttribute("loginUser");
                if (loginUser == null){
                    response.sendRedirect("/login.shtml?error=1");
                    //喜欢重定向 哈哈
                    //request.getRequestDispatcher("/login.shtml").forward(request, response);
                    return false;
                }
            }

        }

        //检查通过，返回true，方法会继续执行
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
