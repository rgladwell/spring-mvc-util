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

public final class MethodUtils {

    private MethodUtils() { }

    /**
     * Check two methods reflectively to see if one has overriden the other
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
    public static boolean isOverriden(Method higher, Method lower) {
        return namesAreEqual(higher, lower) && returnTypesAreEqualOrCovariant(higher, lower);
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

}
