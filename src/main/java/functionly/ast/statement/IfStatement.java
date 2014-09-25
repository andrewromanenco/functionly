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

package functionly.ast.statement;

import functionly.ast.AbstractExpression;
import functionly.ast.AbstractStatement;
import functionly.ast.StatementsBlock;
import functionly.ast.Visitable;
import functionly.print.ASTVisitor;

public class IfStatement extends AbstractStatement implements Visitable {

    private final AbstractExpression condition;
    private final StatementsBlock thenBlock;
    private final StatementsBlock elseBlock;

    public IfStatement(AbstractExpression condition, StatementsBlock thenBlock,
            StatementsBlock elseBlock) {
        super();
        assert condition != null;
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public AbstractExpression getCondition() {
        return condition;
    }

    public StatementsBlock getThenBlock() {
        return thenBlock;
    }

    public StatementsBlock getElseBlock() {
        return elseBlock;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
