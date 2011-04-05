package com.github.spring.mvc.util.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestURIMatchingHandlerInterceptor extends URIMatchingHandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return super.preHandle(request, response, o);
    }

}
