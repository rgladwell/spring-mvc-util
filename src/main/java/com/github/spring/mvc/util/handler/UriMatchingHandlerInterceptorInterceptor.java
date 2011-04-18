/**
 * Copyright 2011 Ricardo Gladwell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spring.mvc.util.handler;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UriMatchingHandlerInterceptorInterceptor implements MethodInterceptor {

    private static final Log log = LogFactory.getLog(UriMatchingHandlerInterceptorInterceptor.class);

    private final Method preHandle;
    private final Method postHandle;
    private final Method afterCompletion;

    public UriMatchingHandlerInterceptorInterceptor() throws Exception {
        preHandle = HandlerInterceptor.class.getMethod("preHandle", HttpServletRequest.class, HttpServletResponse.class, Object.class);
        postHandle = HandlerInterceptor.class.getMethod("postHandle", HttpServletRequest.class, HttpServletResponse.class, Object.class, ModelAndView.class);
        afterCompletion = HandlerInterceptor.class.getMethod("afterCompletion", HttpServletRequest.class, HttpServletResponse.class, Object.class, Exception.class);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        if(MethodUtils.isOverloaded(method, preHandle) || MethodUtils.isOverloaded(method, postHandle) || MethodUtils.isOverloaded(method, afterCompletion)) {

            Includes includes = invocation.getThis().getClass().getAnnotation(Includes.class);
            Excludes excludes = invocation.getThis().getClass().getAnnotation(Excludes.class);

            if(HandlerInterceptor.class.isAssignableFrom(invocation.getThis().getClass())) {
                String uri = null;
                for(Object argument : invocation.getArguments()) {
                    if(argument instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) argument;
                        uri = request.getRequestURI();
                        break;
                    }
                }

                if(excludes != null) {
                    for (String excludePath : excludes.value()) {
                        if(HandlerUtils.servletUrlPatternMatch(excludePath, uri)) {
                            if(log.isTraceEnabled()) {
                                log.trace("Found matching exclude=[" + excludePath + "] for uri=[" + uri + "]: halt processing");
                            }

                            return true;
                        }
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

            }

        }

        return invocation.proceed();
    }

}
