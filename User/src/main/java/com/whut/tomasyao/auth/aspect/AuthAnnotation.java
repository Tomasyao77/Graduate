package com.whut.tomasyao.auth.aspect;

import edu.whut.pocket.auth.model.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthAnnotation {

    UserType value() default UserType.普通用户;

    AuthCaseEnum cases() default AuthCaseEnum.CASE_USER_ID;

    int[] moduleId() default {};
}
