package com.github.spring.mvc.util.handler.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class AnnotationDrivenNamespaceHandler extends NamespaceHandlerSupport {

    /**
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {
        this.registerBeanDefinitionParser("uri-matching-annotation-driven", new UriMatchingAnnotationDrivenBeanDefinitionParser());
    }

}
