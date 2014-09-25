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

import java.util.ArrayList;
import java.util.List;

import com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.ast.AbstractSAttrVisitor;
import com.romanenco.cfrm.ast.SDTVisitor;

import functionly.ast.AbstractExpression;
import functionly.ast.FunctionCall;
import functionly.ast.Variable;

public final class CallInitializer {

    private CallInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {

        builder.addSDTHandler(
                grammar.getProduction("VARIABLE_OR_FUNCTION_CALL -> name FUNCTION_CALL"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                final ParsingTreeNode nodeName = node.getChild(0);
                final String name = nodeName.getValue().getValue();
                final ParsingTreeNode nodeFCall = node.getChild(1);
                nodeFCall.setAttribute(Constant.INH, name);
                builder.build(nodeFCall);
                node.setAttribute(Constant.VALUE,
                        nodeFCall.getAttribute(Constant.VALUE));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTION_CALL -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final String name = (String)node.getAttribute(Constant.INH);
                node.setAttribute(Constant.VALUE, new Variable(name));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTION_CALL -> ( FUNCTION_CALL_PARAMS )"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final String name = (String)node.getAttribute(Constant.INH);
                @SuppressWarnings("unchecked")
                final List<AbstractExpression> params =
                        (List<AbstractExpression>)node
                        .getChild(1)
                        .getAttribute(Constant.VALUE);
                node.setAttribute(Constant.VALUE,
                        new FunctionCall(name, params));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTION_CALL_PARAMS -> EXPR MORE_FUNCTION_CALL_PARAMS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                addExprToParams(node, 1, 0);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTION_CALL_PARAMS -> epsilon"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                node.setAttribute(Constant.VALUE,
                        new ArrayList<AbstractExpression>());
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("MORE_FUNCTION_CALL_PARAMS -> , EXPR MORE_FUNCTION_CALL_PARAMS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                addExprToParams(node, 2, 1);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("MORE_FUNCTION_CALL_PARAMS -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        new ArrayList<AbstractExpression>());
            }
        });
    }

    private static void addExprToParams(ParsingTreeNode node, int listIndex,
            int exprIndex) {
        final AbstractExpression expr = (AbstractExpression)node
                .getChild(exprIndex)
                .getAttribute(Constant.VALUE);
        @SuppressWarnings("unchecked")
        final List<AbstractExpression> list = (List<AbstractExpression>)node
                .getChild(listIndex)
                .getAttribute(Constant.VALUE);
        list.add(0, expr);
        node.setAttribute(Constant.VALUE, list);
    }

}
