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
