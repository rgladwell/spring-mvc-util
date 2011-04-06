package com.github.spring.mvc.util.handler;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Excludes({ "/*" })
public class WildcardUriMatchingHandlerInterceptor extends HandlerInterceptorAdapter {

}
