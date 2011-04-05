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

public class HandlerUtils {

    // Matching code copied from org.apache.catalina.core.ApplicationFilterFactory.matchFiltersURL
    public static boolean servletUrlPatternMatch(String pattern, String uri) {
        // Case 1 - Exact Match
        if (pattern.equals(uri))
            return (true);

        // Case 2 - Path Match ("/.../*")
        if (pattern.equals("/*"))
            return (true);
        if (pattern.endsWith("/*")) {
            if (pattern.regionMatches(0, uri, 0, pattern.length() - 2)) {
                if (uri.length() == (pattern.length() - 2)) {
                    return (true);
                } else if ('/' == uri.charAt(pattern.length() - 2)) {
                    return (true);
                }
            }
            return (false);
        }

        // Case 3 - Extension Match
        if (pattern.startsWith("*.")) {
            int slash = uri.lastIndexOf('/');
            int period = uri.lastIndexOf('.');
            if ((slash >= 0) && (period > slash)
                    && (period != uri.length() - 1)
                    && ((uri.length() - period)
                            == (pattern.length() - 1))) {
                return (pattern.regionMatches(2, uri, period + 1, pattern.length() - 2));
            }
        }

        // Case 4 - "Default" Match
        return (false); // NOTE - Not relevant for selecting filters
    }

}
