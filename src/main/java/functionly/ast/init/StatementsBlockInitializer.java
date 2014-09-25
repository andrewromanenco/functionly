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

import functionly.ast.AbstractStatement;
import functionly.ast.StatementsBlock;

public final class StatementsBlockInitializer {

    private StatementsBlockInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("STATEMENTS_BLOCK -> begin STATEMENTS end"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                @SuppressWarnings("unchecked")
                final List<AbstractStatement> statements = (List<AbstractStatement>)node
                    .getChild(1)
                    .getAttribute(Constant.VALUE);
                final StatementsBlock block = new StatementsBlock(statements);
                node.setAttribute(Constant.VALUE, block);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("STATEMENTS -> STATEMENT STATEMENTS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                @SuppressWarnings("unchecked")
                final List<AbstractStatement> statements =
                    (List<AbstractStatement>)node
                    .getChild(1)
                    .getAttribute(Constant.VALUE);
                final AbstractStatement statement = (AbstractStatement)node
                    .getChild(0)
                    .getAttribute(Constant.VALUE);
                statements.add(0, statement);
                node.setAttribute(Constant.VALUE, statements);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("STATEMENTS -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        new ArrayList<AbstractStatement>());
            }
        });
    }

}
