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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.romanenco.cfrm.Lexer;
import com.romanenco.cfrm.Parser;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.Token;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.lexer.RegLexer;
import com.romanenco.cfrm.llparser.LLParser;

import functionly.ast.Program;
import functionly.ast.init.AttributeHandlers;
import functionly.grammar.FunctionlyGrammar;
import functionly.print.FilePrinter;
import functionly.print.SilentPrinter;

public class Functionly {

    private final Printer printer;
    private final Lexer lexer;
    private final Parser parser;
    private final ASTBuilder builder;

    public Functionly(boolean silentRun) {
        final FunctionlyGrammar grammar = new FunctionlyGrammar();
        lexer = new RegLexer(grammar);
        parser = new LLParser(grammar);
        builder = new ASTBuilder();
        AttributeHandlers.initForBuilder(grammar, builder);
        if (silentRun) {
            printer = new SilentPrinter();
        } else {
            printer = new FilePrinter();
        }
    }

    public void process(String input) {
        final List<Token> tokens = lexer.tokenize(input);
        printer.print(tokens);
        final ParsingTreeNode parsingTree = parser.parse(tokens);
        printer.print(parsingTree);
        builder.build(parsingTree);
        final Program program = (Program)parsingTree.getAttribute("value");
        printer.print(program);
    }

    public static void main(String[] args) throws IOException {
        if ((args.length == 0)||(args.length > 2)) {
            printHelp();
            return;
        }
        final String fileName = args[0];
        final boolean silentRun = !((args.length == 1)||("/nooutput".equals(args[1])));

        if (sourceExists(fileName)) {
            final String input = getSourceFromFile(fileName);
            new Functionly(silentRun).process(input);
        } else {
            printNoFileError();
        }
    }

    private static boolean sourceExists(String fileName) {
        final File file = new File(fileName);
        return file.isFile();
    }

    private static void printHelp() {
        final StringBuilder helpMessage = new StringBuilder()
          .append("PROGRAM <path to .fly source> [/nooutput]\n")
          .append("/nooutput - if provided, no intermediary representations will be saved\n");
        System.out.println(helpMessage);  // NOPMD NOSONAR
    }

    private static void printNoFileError() {
        System.out.println("No source file");  // NOPMD NOSONAR
    }

    private static String getSourceFromFile(String fileName) throws IOException {
        final byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        return new String(encoded, Charset.defaultCharset());
    }
}
