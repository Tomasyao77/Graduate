package com.whut.tomasyao.auth.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AuthPointCut {

    @Pointcut("@annotation(com.whut.tomasyao.auth.aspect.AuthAnnotation)")
    public void authAccess() {
    }

}
