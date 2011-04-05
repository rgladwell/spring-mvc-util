package com.github.spring.mvc.util.handler;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UriMatchingHandlerInterceptorInterceptorTest {

    private UriMatchingHandlerInterceptorInterceptor interceptor;

    @Mock
    private MethodInvocation invocation;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() throws Throwable {
        interceptor = new UriMatchingHandlerInterceptorInterceptor();
        when(invocation.getThis()).thenReturn(new TestUriMatchingHandlerInterceptor());
        when(invocation.getArguments()).thenReturn(new Object[] { request });
        stub(invocation.proceed()).toReturn(Boolean.TRUE);
    }

    @Test
    public void testWithoutExcludeOrInclude() throws Throwable {
        when(request.getRequestURI()).thenReturn("/uri");

        interceptor.invoke(invocation);

        verify(invocation).proceed();
    }

    @Test
    public void testMatchingExclude() throws Throwable {
        when(request.getRequestURI()).thenReturn("/exclude");

        interceptor.invoke(invocation);

        verify(invocation, never()).proceed();
    }

    @Test
    public void testMatchingInclude() throws Throwable {
        when(request.getRequestURI()).thenReturn("/include");

        interceptor.invoke(invocation);

        verify(invocation).proceed();
    }

}
