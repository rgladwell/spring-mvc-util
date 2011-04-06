spring-mvc-utils
================

Useful utility classes for Spring MVC

URI matching Handler Interceptors
---------------------------------

To ensure your Spring MVC handler interceptors only run against specific URLs, you can decorate your handler interceptors
with annotations to ensure they're only called when needed.

For example,:

    @Includes({ "/include", "/include/*", "*.html" })
    @Excludes({ "/exclude", "/exclude/*", "*.jsp" })
    public class UriMatchingHandlerInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // TODO Auto-generated method stub
            return super.preHandle(request, response, handler);
            
            doSomething();
            
        }

    } 

The @Includes annotation above ensures that the list of patterns of URIs must be included; all files are included when omitted.
Similarly, the @Excludes annotation ensures the list of patterns of URIs must be excluded; no files are excluded when omitted.

To enable this feature you must use the uri-matching-annotation-driven is used in your Spring configuration, like this:

	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xmlns:context="http://www.springframework.org/schema/context"
	       xmlns:mvc-util="https://github.com/rgladwell/spring-mvc-util"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans
	                http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
	                http://www.springframework.org/schema/context
	                http://www.springframework.org/schema/context/spring-context-2.5.xsd
	                https://github.com/rgladwell/spring-mvc-util
	                https://github.com/rgladwell/spring-mvc-util/raw/src/main/resources/com/github/spring-mvc-util-1.0.xsd">
	
		<mvc-util:uri-matching-annotation-driven />
	
	 	<!-- Override default handler mapping -->
	    <bean id="handlerMapping" class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping">
		   <property name="interceptors">
		   		<list>
		   		    <ref bean="exampleInterceptor" />
		     	</list>
		   </property>
	    </bean>
	
	    <bean id="exampleInterceptor" class="com.github.spring.mvc.util.handler.ExampleHandlerInterceptor" />
	 
	</beans>

Credits
-------
The spring-mvc-utils is an open source project licensed under the Apache License 2.0.