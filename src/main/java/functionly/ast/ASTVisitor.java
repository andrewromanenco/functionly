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

import functionly.ast.statement.AssignStatement;
import functionly.ast.statement.IfStatement;
import functionly.ast.statement.ReturnStatement;
import functionly.ast.statement.WhileStatement;

public interface ASTVisitor {

    void visit(Program program);
    void visit(Function function);
    void visit(StatementsBlock block);
    void visit(AbstractStatement statement);
    void visit(AssignStatement statement);
    void visit(IfStatement statement);
    void visit(ReturnStatement statement);
    void visit(WhileStatement statement);
    void visit(AbstractExpression expr);
    void visit(FloatNumber number);
    void visit(MathExpression expr);
    void visit(Variable var);
    void visit(FunctionCall functionCall);

}
