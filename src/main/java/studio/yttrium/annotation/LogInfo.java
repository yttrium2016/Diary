package studio.yttrium.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/10/13
 * Time: 14:18
 */
@Documented
@Target(ElementType.METHOD)  //注解作用的位置。。方法
@Inherited
@Retention(RetentionPolicy.RUNTIME) //注解的生命周期
public @interface LogInfo {
    /**
     * 日志信息
     * @return
     */
    String message() default "";
}
