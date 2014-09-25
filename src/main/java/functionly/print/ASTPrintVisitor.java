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

package functionly.print;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

import functionly.FunctionlyError;
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

public class ASTPrintVisitor implements ASTVisitor, Closeable {

    private static final String TAB = "    ";

    private final BufferedWriter out;
    private final StringBuilder indent = new StringBuilder();

    public ASTPrintVisitor() throws IOException {
        super();
        out = new BufferedWriter(new FileWriter("ast.out"));
    }

    @Override
    public void visit(Program program) {
        write("@public\n");
        final Function main = program.getMainFunction();
        main.accept(this);
        for (final Function funct: program.getFunctions()) {
            if (!funct.equals(main)) {
                funct.accept(this);
            }
        }
    }

    @Override
    public void visit(Function function) {
        write("function ");
        write(function.getName());
        write("(");
        boolean first = true;
        for (final String param: function.getParameters()) {
            if (first) {
                write(param);
                first = false;
            } else {
                write(", ");
                write(param);
            }
        }
        write(")\n");
        write("begin\n");
        this.visit(function.getBody());
        write("end;\n\n");
    }

    @Override
    public void visit(StatementsBlock block) {
        increaseIndent();
        for (final AbstractStatement statement: block.getStatements()) {
            statement.accept(this);
        }
        decreaseIndent();
    }

    @Override
    public void visit(AbstractStatement statement) {
        throw new UnsupportedOperationException("Should never be called");
    }

    @Override
    public void visit(AssignStatement statement) {
        makeIndent();
        final Variable var = statement.getLeft();
        var.accept(this);
        write(" = ");
        final AbstractExpression expr = statement.getRight();
        expr.accept(this);
        write(";\n");
    }

    @Override
    public void visit(IfStatement statement) {
        makeIndent();
        write("if (");
        final AbstractExpression expr = statement.getCondition();
        expr.accept(this);
        write(") then begin\n");
        statement.getThenBlock().accept(this);
        if (statement.getElseBlock() != null) {
            makeIndent();
            write("end; else begin\n");
            statement.getElseBlock().accept(this);
        }
        makeIndent();
        write("end;\n");
    }

    @Override
    public void visit(ReturnStatement statement) {
        makeIndent();
        write("return ");
        statement.getExpression().accept(this);
        write(";\n");
    }

    @Override
    public void visit(WhileStatement statement) {
        makeIndent();
        write("while (");
        final AbstractExpression expr = statement.getCondition();
        expr.accept(this);
        write(") begin\n");
        statement.getBlock().accept(this);
        makeIndent();
        write("end;\n");
    }

    @Override
    public void visit(FloatNumber number) {
        write(number.getValue());
    }

    @Override
    public void visit(AbstractExpression expr) {
        throw new UnsupportedOperationException("Should never be called");
    }

    @Override
    public void visit(MathExpression expr) {
        final AbstractExpression left = expr.getLeft();
        left.accept(this);
        write(expr.getOperation().getSign());
        final AbstractExpression right = expr.getRight();
        right.accept(this);
    }

    @Override
    public void visit(Variable var) {
        write(var.getName());
    }

    @Override
    public void visit(FunctionCall functionCall) {
        write(functionCall.getName());
        write("(");
        boolean first = true;
        for (final AbstractExpression expr: functionCall.getParameters()) {
            if (first) {
                first = false;
            } else {
                write(", ");
            }
            expr.accept(this);
        }
        write(")");
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            // nothing
        }
    }

    private void write(String data) {
        try {
            out.write(data);
        } catch (IOException e) {
            throw new FunctionlyError("Error writing to the file", e);
        }
    }

    private void increaseIndent() {
        indent.append(TAB);
    }

    private void decreaseIndent() {
        indent.delete(indent.length() - TAB.length(), indent.length());
    }

    private void makeIndent() {
        write(indent.toString());
    }

}
