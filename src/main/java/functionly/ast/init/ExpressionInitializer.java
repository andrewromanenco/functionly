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
import com.romanenco.cfrm.ast.AbstractSAttrVisitor;
import com.romanenco.cfrm.ast.SDTVisitor;

import functionly.ast.FloatNumber;
import functionly.ast.init.util.ValueCopier;

public final class ExpressionInitializer {

    private ExpressionInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("EXPR -> T EXPR'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                final ParsingTreeNode nodeT = node.getChild(0);
                builder.build(nodeT);
                final ParsingTreeNode nodeEXPRPrime = node.getChild(1);
                nodeEXPRPrime.setAttribute(Constant.INH,
                        nodeT.getAttribute(Constant.VALUE));
                builder.build(nodeEXPRPrime);
                node.setAttribute(Constant.VALUE,
                        nodeEXPRPrime.getAttribute(Constant.VALUE));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("EXPR' -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        node.getAttribute(Constant.INH));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("T -> F T'"),
                new SDTVisitor() {
            @Override
            public void visit(ParsingTreeNode node, ASTBuilder builder) {
                final ParsingTreeNode nodeF = node.getChild(0);
                builder.build(nodeF);
                final ParsingTreeNode nodeTPrime = node.getChild(1);
                nodeTPrime.setAttribute(Constant.INH,
                        nodeF.getAttribute(Constant.VALUE));
                builder.build(nodeTPrime);
                node.setAttribute(Constant.VALUE,
                        nodeTPrime.getAttribute(Constant.VALUE));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("T' -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        node.getAttribute(Constant.INH));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("F -> ( EXPR )"),
                new ValueCopier(1));

        builder.addSDTHandler(
                grammar.getProduction("F -> number"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final String number = node.getChild(0).getValue().getValue();
                node.setAttribute(Constant.VALUE, new FloatNumber(number));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("F -> VARIABLE_OR_FUNCTION_CALL"),
                new ValueCopier(0));
    }

}
