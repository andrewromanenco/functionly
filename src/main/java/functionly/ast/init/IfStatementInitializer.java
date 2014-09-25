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

import functionly.ast.AbstractExpression;
import functionly.ast.StatementsBlock;
import functionly.ast.init.util.ValueCopier;
import functionly.ast.statement.IfStatement;

public final class IfStatementInitializer {

    private IfStatementInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("STATEMENT -> IF"),
                new ValueCopier(0));

        builder.addSDTHandler(
                grammar.getProduction("IF -> if ( EXPR ) then STATEMENTS_BLOCK ELSE ;"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final AbstractExpression condition = (AbstractExpression)node
                        .getChild(2)
                        .getAttribute(Constant.VALUE);
                final StatementsBlock thenBlock = (StatementsBlock)node
                        .getChild(5)
                        .getAttribute(Constant.VALUE);
                final StatementsBlock elseBlock = (StatementsBlock)node
                        .getChild(6)
                        .getAttribute(Constant.VALUE);
                node.setAttribute(Constant.VALUE,
                        new IfStatement(condition,
                        thenBlock, elseBlock));
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("ELSE -> else STATEMENTS_BLOCK"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        node.getChild(1).getAttribute(Constant.VALUE));
            }
        });
    }

}
