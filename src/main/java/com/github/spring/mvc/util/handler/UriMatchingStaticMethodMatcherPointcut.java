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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;


public class UriMatchingStaticMethodMatcherPointcut extends StaticMethodMatcherPointcut {

    private static final Log log = LogFactory.getLog(UriMatchingStaticMethodMatcherPointcut.class);

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        try {
            return targetClass.isAnnotationPresent(Includes.class) || targetClass.isAnnotationPresent(Excludes.class);
        } catch (SecurityException e) {
            log.debug("Illegal access to method=["+method.getName()+"] for class=["+targetClass+"]",e);
            return false;
        }
    }

}
