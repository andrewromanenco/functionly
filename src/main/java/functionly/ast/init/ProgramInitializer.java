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

import java.util.List;

import com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.ast.AbstractSAttrVisitor;

import functionly.ast.Function;
import functionly.ast.Program;

public final class ProgramInitializer {

    private ProgramInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("PROGRAM -> PUBLIC_FUNCTION FUNCTIONS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final Function mainFunction = (Function)node
                        .getChild(0)
                        .getAttribute(Constant.VALUE);
                @SuppressWarnings("unchecked")
                final List<Function> functions = (List<Function>)node
                        .getChild(1)
                        .getAttribute(Constant.VALUE);
                functions.add(mainFunction);
                final Program program = new Program(functions, mainFunction);
                node.setAttribute(Constant.VALUE, program);
            }
        });
    }

}
