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

import java.util.List;

import functionly.print.ASTVisitor;

public class Program implements Visitable {

    private final List<Function> functions;
    private final Function mainFunction;

    public Program(List<Function> functions, Function mainFunction) {
        this.functions = functions;
        this.mainFunction = mainFunction;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public Function getMainFunction() {
        return mainFunction;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
