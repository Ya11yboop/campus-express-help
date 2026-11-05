package com.campus.pickup.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 角色权限校验注解。标注在 Controller 类或方法上，指定允许访问的角色 key。
 *
 * @author campus-pickup
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresRole {

    /**
     * 允许访问的角色 key 数组
     */
    String[] value() default {};
}
