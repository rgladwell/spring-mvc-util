package com.github.spring.mvc.util.handler;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class UriMatchingHandlerInterceptorInterceptor implements MethodInterceptor {

    private static final Log log = LogFactory.getLog(UriMatchingHandlerInterceptorInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Includes includes = invocation.getThis().getClass().getAnnotation(Includes.class);
        Excludes excludes = invocation.getThis().getClass().getAnnotation(Excludes.class);

        if(HandlerInterceptor.class.isAssignableFrom(invocation.getThis().getClass())) {
            String uri = null;
            for(Object argument : invocation.getArguments()) {
                if(argument instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) argument;
                    uri = request.getRequestURI();
                }
            }

            if(includes != null) {
                for (String includePath : includes.value()) {
                    if(HandlerUtils.servletUrlPatternMatch(includePath, uri)) {
                        if(log.isTraceEnabled()) {
                            log.trace("Found matching include=[" + includePath + "] for uri=[" + uri + "]: continue processing");
                        }

                        return invocation.proceed();
                    }
                }
            }

            if(excludes != null) {
                for (String excludePath : excludes.value()) {
                    if(HandlerUtils.servletUrlPatternMatch(excludePath, uri)) {
                        if(log.isTraceEnabled()) {
                            log.trace("Found matching exclude=[" + excludePath + "] for uri=[" + uri + "]: halt processing");
                        }

                        return false;
                    }
                }
            }

        }

        return invocation.proceed();
    }

}
