package com.github.spring.mvc.util.handler;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;


public class UriMatchingStaticMethodMatcherPointcut extends StaticMethodMatcherPointcut {

    private static final Log log = LogFactory.getLog(UriMatchingStaticMethodMatcherPointcut.class);

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        try {
            return targetClass.isAnnotationPresent(Includes.class) || targetClass.isAnnotationPresent(Excludes.class);
        } catch (SecurityException e) {
            log.debug("Illegal access to method=["+method.getName()+"] for class=["+targetClass+"]",e);
            return false;
        }
    }

}
