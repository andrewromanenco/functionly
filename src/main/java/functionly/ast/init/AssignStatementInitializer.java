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
import functionly.ast.Variable;
import functionly.ast.init.util.ValueCopier;
import functionly.ast.statement.AssignStatement;

public final class AssignStatementInitializer {

    private AssignStatementInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("STATEMENT -> ASSIGN"),
                new ValueCopier(0));

        builder.addSDTHandler(
                grammar.getProduction("ASSIGN -> name = EXPR ;"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final String name = node.getChild(0).getValue().getValue();
                final AbstractExpression expression = (AbstractExpression)node
                        .getChild(2)
                        .getAttribute(Constant.VALUE);
                node.setAttribute(Constant.VALUE,
                        new AssignStatement(new Variable(name),
                        expression));
            }
        });
    }

}
