package com.github.spring.mvc.util.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public abstract class URIMatchingHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Log log = LogFactory.getLog(URIMatchingHandlerInterceptorAdapter.class);

    private String[] includes;
    private String[] excludes;

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getServletPath();
        return hasIncludesMatches(uri) || ! hasExcludesMatches(uri);
    }

    private boolean hasIncludesMatches(String uri) {
        for (String includePath : getAllIncludes()) {
            if(HandlerUtils.servletUrlPatternMatch(includePath, uri)) {
                if(log.isTraceEnabled()) {
                    log.trace("Found matching include=[" + includePath + "] for uri=[" + uri + "]: halt processing");
                }
                return true;
            }
        }
        return false;
    }

    private boolean hasExcludesMatches(String uri) {
        for (String excludePath : getAllExcludes()) {
            if(HandlerUtils.servletUrlPatternMatch(excludePath, uri)) {
                if(log.isTraceEnabled()) {
                    log.trace("Found matching exclude=[" + excludePath + "] for uri=[" + uri + "]: continue processing");
                }
                return true;
            }
        }
        return false;
    }

    private List<String> getAllIncludes() {
        List<String> allIncludes = new ArrayList<String>();
        if(includes != null){
            for (String include : includes) {
                allIncludes.add(include);
            }
        }

        Includes includesAnnotation = getClass().getAnnotation(Includes.class);
        if(includesAnnotation != null){
            for (String include : includesAnnotation.value()) {
                allIncludes.add(include);
            }
        }

        return allIncludes;
    }

    private List<String> getAllExcludes() {
        List<String> allIncludes = new ArrayList<String>();
        if(excludes != null){
            for (String include : excludes) {
                allIncludes.add(include);
            }
        }

        Excludes excludesAnnotation = getClass().getAnnotation(Excludes.class);
        if(excludesAnnotation != null){
            for (String include : excludesAnnotation.value()) {
                allIncludes.add(include);
            }
        }

        return allIncludes;
    }


}
