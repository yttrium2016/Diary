package studio.yttrium.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2017/7/16
 * Time: 20:28
 */
@Documented
@Target(ElementType.METHOD)  //注解作用的位置。。方法
@Inherited
@Retention(RetentionPolicy.RUNTIME) //注解的生命周期
public @interface PrivilegeInfo {
    /**
     * 权限名字
     * @return
     */
    String name() default "";
}
