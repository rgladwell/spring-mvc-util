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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class MethodUtilsTest {

    private static final String METHOD = "method";
    private static final String ANOTHER_METHOD = "anotherMethod";
    private static final Class<?>[] NO_PARAMETERS = null;

    public interface Interface {

        public Object method();

    }

    public class ConcreteClass implements Interface {

        /** (non-Javadoc)
         * implements the method in the interface type with a covariant return type
         * @see com.robsanheim.sandbox.reflection.overloading.Interface#method()
         */
        @Override
        public String method() {
            return null;
        }

        /**
         * Overloads method in this concrete class, does not overload method from interface
         * @param one
         * @return
         */
        public String method(String one) {
            return null;
        }

        public Object anotherMethod() {
            return null;
        }

    }

    public class SubClass extends ConcreteClass {

        /**
         * Override method from concrete class
         */
        @Override
        public String method() {
            return null;
        }

        public Object method(String one, String two, String three) {
            return null;
        }

    }

    private Method returnsStringNoParameters;
    private Method returnsStringOneParameter;
    private Method returnsObjectNoParametersDifferentName;
    private Method returnsStringNoParametersOnSubClass;
    private Method returnsObjectThreeParametersOnSubClass;
    private Method returnsObjectNoParametersOnInterfaceType;

    @Before
    public void setUp() throws Exception {
        returnsStringNoParameters = ConcreteClass.class.getMethod(METHOD, NO_PARAMETERS);
        returnsStringOneParameter = ConcreteClass.class.getMethod(METHOD, String.class);
        returnsObjectNoParametersDifferentName = ConcreteClass.class.getMethod(ANOTHER_METHOD, NO_PARAMETERS);
        returnsStringNoParametersOnSubClass = SubClass.class.getMethod(METHOD, NO_PARAMETERS);
        returnsObjectThreeParametersOnSubClass = SubClass.class.getMethod(METHOD, String.class, String.class, String.class);
        returnsObjectNoParametersOnInterfaceType = Interface.class.getMethod(METHOD, NO_PARAMETERS);
    }

    @Test
    public void testTwoSimpleOverloadedMethodsOnSameClass() throws Exception {
        assertTrue(MethodUtils.isOverloaded(returnsStringNoParameters, returnsStringOneParameter));
        assertTrue(MethodUtils.isOverloaded(returnsStringOneParameter, returnsStringNoParameters));
    }

    @Test
    public void testMoreSpecificMethodOnConcreteClassOverloadsInterfaceMethods() throws Exception {
        assertTrue(MethodUtils.isOverloaded(returnsObjectNoParametersOnInterfaceType, returnsStringOneParameter));
    }

    @Test
    public void testInterfaceImplementationIsNotOverloading() throws Exception {
        assertFalse(MethodUtils.isOverloaded(returnsObjectNoParametersOnInterfaceType, returnsStringNoParameters));
    }

    @Test
    public void testIsNotOverloadingForDifferentNamesOnSameClass() throws Exception {
        assertFalse(MethodUtils.isOverloaded(returnsStringNoParameters, returnsObjectNoParametersDifferentName));
        assertFalse(MethodUtils.isOverloaded(returnsStringOneParameter, returnsObjectNoParametersDifferentName));
    }

    @Test
    public void testIsNotOverloadingForDifferentNamesInterfaceAgainstConcrete() throws Exception {
        assertFalse(MethodUtils.isOverloaded(returnsObjectNoParametersOnInterfaceType, returnsObjectNoParametersDifferentName));
    }

    @Test
    public void testOverridingIsNotOverloading() throws Exception {
        assertFalse(MethodUtils.isOverloaded(returnsStringNoParameters, returnsStringNoParametersOnSubClass));
    }

    @Test
    public void testMethodOnSubclassOverloadsInterfaceMethod() throws Exception {
        assertTrue(MethodUtils.isOverloaded(returnsObjectNoParametersOnInterfaceType, returnsObjectThreeParametersOnSubClass));
    }

    @Test
    public void testMethodOnSubclassOverloadsMethodOnSameClass() throws Exception {
        assertTrue(MethodUtils.isOverloaded(returnsStringNoParametersOnSubClass, returnsObjectThreeParametersOnSubClass));
        assertTrue(MethodUtils.isOverloaded(returnsObjectThreeParametersOnSubClass, returnsStringNoParametersOnSubClass));
    }

    @Test
    public void testMethodOnSubclassOverloadsMethodOnSuperClass() throws Exception {
        assertTrue(MethodUtils.isOverloaded(returnsStringOneParameter, returnsObjectThreeParametersOnSubClass));
    }

    @Test
    public void testMethodOnSubclassDoesNotOverloadMethodOfDifferentNameOnSuperClass() throws Exception {
        assertFalse(MethodUtils.isOverloaded(returnsObjectNoParametersDifferentName, returnsObjectThreeParametersOnSubClass));
    }

}
