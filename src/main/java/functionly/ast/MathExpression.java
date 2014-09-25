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

package functionly.ast;

import functionly.print.ASTVisitor;

public class MathExpression extends AbstractExpression implements Visitable {

    public enum Operation {

        ADD("+"),
        SUBSTRACT("-"),
        MULTUPLY("*"),
        DIVIDE("/");

        private final String sign;

        Operation(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }

    }

    private final Operation operation;
    private final AbstractExpression left;
    private final AbstractExpression right;

    public MathExpression(Operation oper, AbstractExpression left, AbstractExpression right) {
        super();
        this.operation = oper;
        this.left = left;
        this.right = right;
    }

    public Operation getOperation() {
        return operation;
    }

    public AbstractExpression getLeft() {
        return left;
    }

    public AbstractExpression getRight() {
        return right;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
