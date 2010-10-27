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

import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.MethodNode

import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.CastExpression
import org.codehaus.groovy.ast.expr.ClosureExpression

/**
 * This rule detects when null is returned from a method that might return an
 * array. Instead of null, return a zero length array. 
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class ReturnsNullInsteadOfEmptyArrayRule extends AbstractAstVisitorRule {
    String name = 'ReturnsNullInsteadOfEmptyArray'
    int priority = 2
    Class astVisitorClass = ReturnsNullInsteadOfEmptyArrayAstVisitor
}

class ReturnsNullInsteadOfEmptyArrayAstVisitor extends AbstractAstVisitor {

    def void visitMethod(MethodNode node) {
        if (methodReturnsArray(node)) {
            // does this method ever return null?
            node.code?.visit(new NullReturnTracker(parent: this))
        }
        super.visitMethod(node)
    }

    def void handleClosure(ClosureExpression expression) {
        if (closureReturnsArray(expression)) {
            // does this closure ever return null?
            expression.code?.visit(new NullReturnTracker(parent: this))
        }
        super.visitClosureExpression(expression)
    }

    private static boolean methodReturnsArray(MethodNode node) {
        if (node.returnType.isArray()) {
            return true
        }

        boolean returnsArray = false
        node.code?.visit(new ArrayReturnTracker(callbackFunction: {returnsArray = true}))
        return returnsArray
    }

    private static boolean closureReturnsArray(ClosureExpression node) {
        boolean returnsArray = false
        node.code?.visit(new ArrayReturnTracker(callbackFunction: {returnsArray = true}))
        return returnsArray
    }
}

class ArrayReturnTracker extends AbstractAstVisitor {
    def callbackFunction

    def void visitReturnStatement(ReturnStatement statement) {
        if (statement.expression instanceof CastExpression) {
            if (statement.expression.type.isArray()) {
                callbackFunction()
            }
        }
        super.visitReturnStatement(statement)
    }
}
