package com.whut.tomasyao.log.aspect;

import com.whut.tomasyao.base.common.MavenModule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zouy on 18-7-27.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    //操作
    String opType() default "";
    //操作人索引
    int opUser() default 1;//0一般是HttpServletRequest 1一般是userId
    //额外描述
    String description() default "";
    //maven_module
    MavenModule mavenModule() default MavenModule.User;
}
