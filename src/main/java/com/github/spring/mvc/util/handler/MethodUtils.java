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
import java.util.Arrays;

public final class MethodUtils {

    private MethodUtils() { }

    /**
     * Check two methods reflectively to see if one is overloading the other
     * Note that the parameter ordering is important if one method is higher in
     * the class hiearchy then the other. If this is the case, make sure the
     * method higher up in the chain is the first parameter. Otherwise, the
     * ordering does not matter.
     * 
     * @param higher
     *            method
     * @param lower
     *            method
     * @return
     */
    public static boolean isOverloaded(Method higher, Method lower) {
        return namesAreEqual(higher, lower) && returnTypesAreEqualOrCovariant(higher, lower)
        && isNotInterfaceImplementation(higher, lower) && isNotOverridden(higher, lower);
    }

    private static boolean isNotOverridden(Method higher, Method lower) {
        if (isOverridden(higher, lower)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param higher
     * @param lower
     * @return true if lower overrides higher
     */
    private static boolean isOverridden(Method higher, Method lower) {
        return declaringClassIsAssignableFrom(higher, lower) && declaringClassIsNotAnInterface(higher)  && parametersAreEqual(higher, lower);
    }

    /**
     * @param first
     * @param second
     * @return true if the first method's declaring class is assignable from the
     *         second
     */
    private static boolean declaringClassIsAssignableFrom(Method first, Method second) {
        return first.getDeclaringClass().isAssignableFrom(second.getDeclaringClass());

    }

    /**
     * We have to make sure we don't mistake standard interface implementation
     * (where first method is on an interface and the params are equal) for
     * overloading.
     * 
     * @param higher
     * @param lower
     * @return
     */
    private static boolean isNotInterfaceImplementation(Method higher, Method lower) {
        return !(declaringClassIsAnInterface(higher) && parametersAreEqual(higher, lower));
    }

    /**
     * check deep equality on parameters of two methods
     * 
     * @param first
     * @param second
     * @return
     */
    private static boolean parametersAreEqual(Method first, Method second) {
        return Arrays.deepEquals(first.getParameterTypes(), second.getParameterTypes());
    }

    /**
     * @param higher
     * @param lower
     * @return true if return types are equal or covariants
     */
    private static boolean returnTypesAreEqualOrCovariant(Method higher, Method lower) {
        return (declaringClassIsAssignableFrom(higher, lower) || higher.getReturnType().equals(lower.getReturnType()));
    }

    /**
     * @param first
     * @param second
     * @return true if the names of the two methods are equal
     */
    private static boolean namesAreEqual(Method first, Method second) {
        return first.getName().equals(second.getName());
    }

    private static boolean declaringClassIsAnInterface(Method method) {
        return method.getDeclaringClass().isInterface();
    }

    private static boolean declaringClassIsNotAnInterface(Method method) {
        return !declaringClassIsAnInterface(method);
    }

}
