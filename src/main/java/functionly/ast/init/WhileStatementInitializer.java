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
import functionly.ast.statement.WhileStatement;

public final class WhileStatementInitializer {

    private WhileStatementInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("STATEMENT -> WHILE"),
                new ValueCopier(0));

        builder.addSDTHandler(
                grammar.getProduction("WHILE -> while ( EXPR ) STATEMENTS_BLOCK ;"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final AbstractExpression condition = (AbstractExpression)node
                        .getChild(2)
                        .getAttribute(Constant.VALUE);
                final StatementsBlock block = (StatementsBlock)node
                        .getChild(4)
                        .getAttribute(Constant.VALUE);
                node.setAttribute(Constant.VALUE,
                        new WhileStatement(condition,
                        block));
            }
        });
    }

}
