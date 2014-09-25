/*
 * Copyright 2014 Andrew Romanenco
 * www.romanenco.com
 * andrew@romanenco.com
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

package functionly.ast.init;

import com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.ast.SDTVisitor;

import functionly.ast.AbstractExpression;
import functionly.ast.MathExpression;

public final class MathExpressionInitializer {

    private MathExpressionInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("EXPR' -> + T EXPR'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                handleMathOperation(node, builder, MathExpression.Operation.ADD);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("EXPR' -> - T EXPR'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                handleMathOperation(node, builder, MathExpression.Operation.SUBSTRACT);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("T' -> * F T'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                handleMathOperation(node, builder, MathExpression.Operation.MULTUPLY);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("T' -> / F T'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                handleMathOperation(node, builder, MathExpression.Operation.DIVIDE);
            }
        });
    }

    /**
     * Handles productions: X -> oper Y Z
     *
     * @param node
     * @param builder
     * @param oper
     */
    private static void handleMathOperation(ParsingTreeNode node,
            ASTBuilder builder,
            MathExpression.Operation oper) {
        final AbstractExpression left = (AbstractExpression)node
                .getAttribute(Constant.INH);
        assert left != null;
        final ParsingTreeNode nodeF = node.getChild(1);
        builder.build(nodeF);
        final AbstractExpression right = (AbstractExpression)nodeF
                .getAttribute(Constant.VALUE);
        assert right != null;
        final MathExpression mathOper = new MathExpression(oper, left, right);
        final ParsingTreeNode nodeTPrime = node.getChild(2);
        nodeTPrime.setAttribute(Constant.INH, mathOper);
        builder.build(nodeTPrime);
        node.setAttribute(Constant.VALUE,
                nodeTPrime.getAttribute(Constant.VALUE));
    }

}
