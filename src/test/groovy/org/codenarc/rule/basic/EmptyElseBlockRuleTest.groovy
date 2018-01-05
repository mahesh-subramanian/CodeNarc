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
package org.codenarc.rule.basic

import org.codenarc.rule.AbstractRuleTestCase
import org.junit.Test

/**
 * Tests for EmptyElseBlockRule
 *
 * @author Chris Mair
 */
class EmptyElseBlockRuleTest extends AbstractRuleTestCase<EmptyElseBlockRule> {

    @Test
    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'EmptyElseBlock'
    }

    @Test
    void testApplyTo_Violation() {
        final SOURCE = '''
            class MyClass {
                def myMethod() {
                    if (x==23) {
                        println 'ok'
                    } else {
                    }
                    if (alreadyInitialized()) {
                        println 'ok'
                    } else { }
                }
            }
        '''
        assertTwoViolations(SOURCE, 6, '} else {', 10, '} else { }')
    }

    @Test
    void testApplyTo_Violation_ElseBlockContainsComment() {
        final SOURCE = '''
            class MyClass {
                def update = {
                    if (isReady) {
                        println 'ok'
                    }
                    else {
                        // TODO Should do something here
                    }
                }
            }
        '''
        assertSingleViolation(SOURCE, 7, 'else {')
    }

    @Test
    void testApplyTo_NoViolations() {
        final SOURCE = '''class MyClass {
                def myMethod() {
                    if (isReady) {
                        println "ready"
                    } else {
                        println "not ready"
                    }
                }
            }'''
        assertNoViolations(SOURCE)
    }

    @Override
    protected EmptyElseBlockRule createRule() {
        new EmptyElseBlockRule()
    }

}
