/*
 * Copyright 2008 the original author or authors.
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
package org.codenarc.rule.imports

import org.codenarc.rule.Rule
import org.codenarc.rule.imports.UnusedImportRule

/**
 * Tests for UnusedImportRule
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class UnusedImportRuleTest extends AbstractImportRuleTest {

    void testRuleProperties() {
        assert rule.priority == 3
        assert rule.id == 'UnusedImport'
    }

    void testApplyTo_OneViolation() {
        final SOURCE = '''
            import java.io.InputStream
            import java.io.OutputStream
            class ABC {
                InputStream input;
            }
        '''
        assertImportViolations(SOURCE, ['import java.io.OutputStream'])
    }

    void testApplyTo_TwoViolations() {
        final SOURCE = '''
            import java.io.InputStream
            import java.util.Map
            import java.io.OutputStream
            class ABC {
                Map map;
            }
        '''
        assertImportViolations(SOURCE, ['import java.io.InputStream', 'import java.io.OutputStream'])
    }

    void testApplyTo_NoViolations() {
        final SOURCE = '''
            import java.io.InputStream
            import java.io.OutputStream
            class ABC {
                def run() {
                    String fff
                InputStream input;
                OutputStream output
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_NoImports() {
        final SOURCE = ' class ABC { }'
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new UnusedImportRule()
    }

}