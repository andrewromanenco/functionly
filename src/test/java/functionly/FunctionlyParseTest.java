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

package functionly;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.romanenco.cfrm.Lexer;
import com.romanenco.cfrm.Parser;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.lexer.RegLexer;
import com.romanenco.cfrm.llparser.LLParser;

import functionly.ast.Program;
import functionly.ast.init.AttributeHandlers;
import functionly.grammar.FunctionlyGrammar;
import functionly.semantic.SemanticVisitor;

public class FunctionlyParseTest {

    private static final Lexer LEXER;
    private static final Parser PARSER;
    private static final ASTBuilder AST_BUILDER;

    static {
        final FunctionlyGrammar grammar = new FunctionlyGrammar();
        LEXER = new RegLexer(grammar);
        PARSER = new LLParser(grammar);
        AST_BUILDER = new ASTBuilder();
        AttributeHandlers.initForBuilder(grammar, AST_BUILDER);
    }

    private static ParsingTreeNode parse(String input) {
        return PARSER.parse(LEXER.tokenize(input));
    }

    @Test
    public void test1() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("sample1.fly"));
        Assert.assertNotNull(parsingTree);
        AST_BUILDER.build(parsingTree);
        final Program program = assertAndGetProgramAttribute(parsingTree);
        final SemanticVisitor semanticChecker = new SemanticVisitor();
        program.accept(semanticChecker);
    }

    @Test
    public void test2() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("sample2.fly"));
        Assert.assertNotNull(parsingTree);
        AST_BUILDER.build(parsingTree);
        final Program program = assertAndGetProgramAttribute(parsingTree);
        final SemanticVisitor semanticChecker = new SemanticVisitor();
        program.accept(semanticChecker);
    }

    @Test
    public void test3() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("sample3.fly"));
        Assert.assertNotNull(parsingTree);
        AST_BUILDER.build(parsingTree);
        final Program program = assertAndGetProgramAttribute(parsingTree);
        final SemanticVisitor semanticChecker = new SemanticVisitor();
        program.accept(semanticChecker);
    }

    @Test
    public void test4() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("sample4.fly"));
        Assert.assertNotNull(parsingTree);
        AST_BUILDER.build(parsingTree);
        final Program program = assertAndGetProgramAttribute(parsingTree);
        final SemanticVisitor semanticChecker = new SemanticVisitor();
        program.accept(semanticChecker);
    }

    private static Program assertAndGetProgramAttribute(ParsingTreeNode parsingTree) {
        final Object value = parsingTree.getAttribute("value");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Program);
        return (Program)value;
    }

}
