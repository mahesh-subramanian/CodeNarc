/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.source

/**
 * Contains SourceCode-related utility methods.
 * <p/>
 * This is an internal class and its API is subject to change.
 *
 * @author Chris Mair
 * @version $Revision: 7 $ - $Date: 2009-01-21 21:52:00 -0500 (Wed, 21 Jan 2009) $
 */
class SourceCodeUtil {

    /**
     * Return true if the specified criteria apply to thw SourceCode
     * @param sourceCode - the SourceCode
     * @param applyToFilesMatching - only apply to source code (file) pathnames matching this regular expression.
     *      May be null, in which case all SourceCode instances match.
     * @param doNotApplyToFilesMatching - only apply to source code (file) pathnames that do NOT match this
     *      regular expression. May be null, in which case all SourceCode instances match.
     * @return true only if the criteria match to the SourceCode
     */
    public static boolean shouldApplyTo(
        SourceCode sourceCode,
        String applyToFilesMatching,
        String doNotApplyToFilesMatching) {

        boolean apply = (applyToFilesMatching) ? sourceCode.path ==~ applyToFilesMatching : true

        if (apply && doNotApplyToFilesMatching) {
            apply = !(sourceCode.path ==~ doNotApplyToFilesMatching)
        }
        return apply
    }

    /**
     * Private constructor. All members are static.
     */
    private SourceClassUtil() { }
}