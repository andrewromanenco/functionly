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
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Document;

import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.Token;
import com.romanenco.cfrm.util.ParsingTreeToXML;
import com.romanenco.cfrm.util.XmlFileWriter;

import functionly.FunctionlyError;
import functionly.Printer;
import functionly.ast.Program;

public class FilePrinter implements Printer {

    private static final String PARSING_TREE = "parsing-tree.out.xml";
    private static final String TOKENS = "tokens.out";

    @Override
    public void print(List<Token> tokens) {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(TOKENS))) {
            for (final Token token: tokens) {
                out.write(token2String(token));
                out.write('\n');
            }
        } catch (IOException e) {
            throw new FunctionlyError("Error saving tokens list", e);
        }
    }

    private String token2String(Token token) {
        return String.format("%s: %s [%d,%d]",
                token.getValue(),
                token.getTerminal(),
                token.getLine(),
                token.getColumn());
    }

    @Override
    public void print(ParsingTreeNode parsingTree) {
        final Document document = ParsingTreeToXML.build(parsingTree);
        XmlFileWriter.save(document, PARSING_TREE);
    }

    @Override
    public void print(Program program) {
        try (ASTPrintVisitor visitor = new ASTPrintVisitor()) {
            program.accept(visitor);
        } catch (IOException e) {
            throw new FunctionlyError("Error saving abstract syntax tree", e);
        }
    }


}
