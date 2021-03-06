/*
 * Copyright 2014 Andrew Romanimport com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ast.ASTBuilder;
Apache License, Version 2.0 (the "License");
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

import com.romanenco.cfrm.Grammar;
import com.romanenco.cfrm.ast.ASTBuilder;

public final class AttributeHandlers {

    private AttributeHandlers() {
        // nothing
    }

    public static void initForBuilder(Grammar grammar, ASTBuilder builder) {
        AssignStatementInitializer.init(grammar, builder);
        CallInitializer.init(grammar, builder);
        ExpressionInitializer.init(grammar, builder);
        FunctionInitializer.init(grammar, builder);
        IfStatementInitializer.init(grammar, builder);
        MathExpressionInitializer.init(grammar, builder);
        ProgramInitializer.init(grammar, builder);
        ReturnStatementInitializer.init(grammar, builder);
        StatementsBlockInitializer.init(grammar, builder);
        WhileStatementInitializer.init(grammar, builder);
    }

}
