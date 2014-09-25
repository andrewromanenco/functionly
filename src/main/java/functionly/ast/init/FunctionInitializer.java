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

import java.util.ArrayList;
import java.util.List;

import com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.ASTBuilder;
import com.romanenco.cfrm.ast.AbstractSAttrVisitor;

import functionly.ast.Function;
import functionly.ast.StatementsBlock;
import functionly.ast.init.util.ValueCopier;

public final class FunctionInitializer {

    private FunctionInitializer() {
        // nothing
    }

    public static void init(Grammar grammar, ASTBuilder builder) {
        builder.addSDTHandler(
                grammar.getProduction("PUBLIC_FUNCTION -> public FUNCTION"),
                new ValueCopier(1)
                );

        builder.addSDTHandler(
                grammar.getProduction("FUNCTION -> function name ( PARAMS ) STATEMENTS_BLOCK"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final String name = node.getChild(1).getValue().getValue();
                final StatementsBlock block = (StatementsBlock)node.getChild(5)
                        .getAttribute(Constant.VALUE);
                @SuppressWarnings("unchecked")
                final List<String> params = (List<String>)node
                        .getChild(3)
                        .getAttribute(Constant.VALUE);
                final Function function = new Function(name, params, block);
                node.setAttribute(Constant.VALUE, function);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTIONS -> FUNCTION FUNCTIONS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                final Function funct = (Function)node.getChild(0)
                        .getAttribute(Constant.VALUE);
                @SuppressWarnings("unchecked")
                final List<Function> list =
                    (List<Function>)node
                    .getChild(1)
                    .getAttribute(Constant.VALUE);
                list.add(0, funct);
                node.setAttribute(Constant.VALUE, list);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("FUNCTIONS -> epsilon"),
                new AbstractSAttrVisitor() {
                    @Override
                    protected void visited(ParsingTreeNode node) {
                        node.setAttribute(Constant.VALUE,
                                new ArrayList<Function>());
                    }
                });

        builder.addSDTHandler(
                grammar.getProduction("PARAMS -> name MORE_PARAMS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                addFunctionParam(node, 1, 0);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("PARAMS -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        new ArrayList<String>());
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("MORE_PARAMS -> , name MORE_PARAMS"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                addFunctionParam(node, 2, 1);
            }
        });

        builder.addSDTHandler(
                grammar.getProduction("MORE_PARAMS -> epsilon"),
                new AbstractSAttrVisitor() {
            @Override
            protected void visited(ParsingTreeNode node) {
                node.setAttribute(Constant.VALUE,
                        new ArrayList<String>());
            }
        });
    }

    private static void addFunctionParam(ParsingTreeNode node, int listIndex,
            int nameIndex) {
        final String name = node.getChild(nameIndex).getValue().getValue();
        @SuppressWarnings("unchecked")
        final List<String> list =
            (List<String>)node
            .getChild(listIndex)
            .getAttribute(Constant.VALUE);
        list.add(0, name);
        node.setAttribute(Constant.VALUE, list);
    }

}
