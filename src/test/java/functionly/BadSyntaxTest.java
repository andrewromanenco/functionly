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
import com.romanenco.cfrm.lexer.LexerError;
import com.romanenco.cfrm.lexer.RegLexer;
import com.romanenco.cfrm.llparser.LLParser;
import com.romanenco.cfrm.llparser.ParsingError;

import functionly.grammar.FunctionlyGrammar;

public class BadSyntaxTest {

    private static final Lexer LEXER;
    private static final Parser PARSER;

    static {
        final FunctionlyGrammar grammar = new FunctionlyGrammar();
        LEXER = new RegLexer(grammar);
        PARSER = new LLParser(grammar);
    }

    private static ParsingTreeNode parse(String input) {
        return PARSER.parse(LEXER.tokenize(input));
    }

    @Test( expected = ParsingError.class )
    public void test1() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("bad/syntax/bad1.fly"));
        Assert.assertNotNull(parsingTree);
    }

    @Test( expected = ParsingError.class )
    public void test2() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("bad/syntax/bad2.fly"));
        Assert.assertNotNull(parsingTree);
    }

    @Test( expected = ParsingError.class )
    public void test3() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("bad/syntax/bad3.fly"));
        Assert.assertNotNull(parsingTree);
    }

    @Test( expected = LexerError.class )
    public void test4() throws IOException {
        final ParsingTreeNode parsingTree = parse(
                Util.getSourceFromFile("bad/syntax/bad4.fly"));
        Assert.assertNotNull(parsingTree);
    }

}
