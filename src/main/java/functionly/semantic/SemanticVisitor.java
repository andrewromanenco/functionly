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

package functionly.semantic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import functionly.ast.ASTVisitor;
import functionly.ast.AbstractExpression;
import functionly.ast.AbstractStatement;
import functionly.ast.FloatNumber;
import functionly.ast.Function;
import functionly.ast.FunctionCall;
import functionly.ast.MathExpression;
import functionly.ast.Program;
import functionly.ast.StatementsBlock;
import functionly.ast.Variable;
import functionly.ast.statement.AssignStatement;
import functionly.ast.statement.IfStatement;
import functionly.ast.statement.ReturnStatement;
import functionly.ast.statement.WhileStatement;

public class SemanticVisitor implements ASTVisitor {

    private final static String SCOPE_MARKER = "_scope_marker_";

    private final Set<Function> globalScope = new HashSet<>();
    private List<String> scope;  // variables

    @Override
    public void visit(Program program) {
        for (final Function funct: program.getFunctions()) {
            if (globalScope.contains(funct)) {
                throw new SemanticError("Duplicate function name: "
                        + funct.getName());
            }
            globalScope.add(funct);
        }
        for (final Function funct: program.getFunctions()) {
            funct.accept(this);
        }
    }

    @Override
    public void visit(Function function) {
        scope = new ArrayList<>();
        enterScope();
        for (final String param: function.getParameters()) {
            addVariableToScope(param);
        }
        function.getBody().accept(this);
        exitScope();
    }

    @Override
    public void visit(StatementsBlock block) {
        enterScope();
        for (final AbstractStatement statement: block.getStatements()) {
            statement.accept(this);
        }
        exitScope();
    }

    @Override
    public void visit(AbstractStatement statement) {
        // nothing
    }

    @Override
    public void visit(AssignStatement statement) {
        statement.getRight().accept(this);
        addVariableToScope(statement.getLeft().getName());
    }

    @Override
    public void visit(IfStatement statement) {
        statement.getCondition().accept(this);
        statement.getThenBlock().accept(this);
        if (statement.getElseBlock() != null) {
            statement.getElseBlock().accept(this);
        }
    }

    @Override
    public void visit(ReturnStatement statement) {
        statement.getExpression().accept(this);
    }

    @Override
    public void visit(WhileStatement statement) {
        statement.getCondition().accept(this);
        statement.getBlock().accept(this);
    }

    @Override
    public void visit(AbstractExpression expr) {
        // nothing
    }

    @Override
    public void visit(FloatNumber number) {
        // nothing, this is always valid
    }

    @Override
    public void visit(MathExpression expr) {
        expr.getLeft().accept(this);
        expr.getRight().accept(this);
    }

    @Override
    public void visit(Variable var) {
        // right side variable, must be in the scope
        if (!scope.contains(var.getName())) {
            throw new SemanticError("Unknown variable: " + var.getName());
        }
    }

    @Override
    public void visit(FunctionCall functionCall) {
        for (final Function funct: globalScope) {
            if (funct.getName().equals(functionCall.getName())
                    &&(funct.getParameters().size() == functionCall.getParameters().size())) {
                for (final AbstractExpression expr: functionCall.getParameters()) {
                    expr.accept(this);
                }
                return;
            }
        }
        throw new SemanticError("Unkown function to call: "
                + functionCall.getName());
    }

    private void enterScope() {
        scope.add(SCOPE_MARKER);
    }

    private void addVariableToScope(String param) {
        if (!scope.contains(param)) {
            scope.add(param);
        }
    }

    private void exitScope() {
        for (int i = scope.size() - 1; i >=0; i --) {
            final String removed = scope.remove(i);
            if (removed.equals(SCOPE_MARKER)) {
                break;
            }
        }
    }

}
