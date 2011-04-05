package com.github.spring.mvc.util.handler;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Includes({ "/include" })
@Excludes({ "/exclude" })
public class TestUriMatchingHandlerInterceptor extends HandlerInterceptorAdapter {

}
