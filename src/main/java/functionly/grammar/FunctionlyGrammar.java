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

package functionly.grammar;

import com.romanenco.cfrm.grammar.builder.GrammarJBuilder;
import com.romanenco.cfrm.grammar.impl.GrammarImpl;
import com.romanenco.cfrm.lexer.LexerRule;

public class FunctionlyGrammar extends GrammarImpl {

    private static final String EPSILON = "epsilon";

    public FunctionlyGrammar() {
        super();
        // SONAR_OFF

        final GrammarJBuilder builder = new GrammarJBuilder(this);

        builder.declareNonTerminals(
                "PROGRAM",
                "FUNCTIONS",
                "PUBLIC_FUNCTION",
                "FUNCTION",
                "PARAMS", "MORE_PARAMS",
                "STATEMENTS_BLOCK",
                "STATEMENTS",
                "STATEMENT", "RETURN", "ASSIGN", "IF", "ELSE", "WHILE",

                "EXPR", "EXPR'", "T", "T'", "F",
                "VARIABLE_OR_FUNCTION_CALL", "FUNCTION_CALL", "FUNCTION_CALL_PARAMS", "MORE_FUNCTION_CALL_PARAMS"
                );
        builder.declareStartSymbol("PROGRAM");
        builder.declareEpsilon(EPSILON);

        builder.addTerminal("function", new LexerRule("function"));
        builder.addTerminal("public", new LexerRule("public"));
        builder.addTerminal("return", new LexerRule("return"));
        builder.addTerminal("if", new LexerRule("if"));
        builder.addTerminal("then", new LexerRule("then"));
        builder.addTerminal("else", new LexerRule("else"));
        builder.addTerminal("begin", new LexerRule("begin"));
        builder.addTerminal("end", new LexerRule("end"));
        builder.addTerminal("while", new LexerRule("while"));

        builder.addTerminal("number", new LexerRule("\\-?\\d+(\\.\\d+)?"));
        builder.addTerminal("name", new LexerRule("\\w+"));

        builder.addTerminal("(", new LexerRule("\\("));
        builder.addTerminal(")", new LexerRule("\\)"));
        builder.addTerminal(",", new LexerRule(","));
        builder.addTerminal(";", new LexerRule(";"));
        builder.addTerminal("=", new LexerRule("="));
        builder.addTerminal("+", new LexerRule("\\+"));
        builder.addTerminal("-", new LexerRule("\\-"));
        builder.addTerminal("*", new LexerRule("\\*"));
        builder.addTerminal("/", new LexerRule("\\/"));
        builder.addTerminal("<", new LexerRule("<"));
        builder.addTerminal(">", new LexerRule(">"));
        builder.addTerminal("<=", new LexerRule("<"));
        builder.addTerminal(">=", new LexerRule(">"));
        builder.addTerminal("==", new LexerRule("=="));
        builder.addTerminal("ws", new LexerRule(" +", LexerRule.TYPE.IGNORE));
        builder.addTerminal("tab", new LexerRule("\\t", LexerRule.TYPE.IGNORE));
        builder.addTerminal("nl", new LexerRule("\\r?\\n", LexerRule.TYPE.IGNORE));
        builder.addTerminal("comment", new LexerRule("\\/\\*.+\\*\\/", LexerRule.TYPE.IGNORE));

        builder.addProductions("PROGRAM", "PUBLIC_FUNCTION FUNCTIONS");
        builder.addProductions("PUBLIC_FUNCTION", "public FUNCTION");
        builder.addProductions("FUNCTIONS", "FUNCTION FUNCTIONS", EPSILON);
        builder.addProductions("FUNCTION", "function name ( PARAMS ) STATEMENTS_BLOCK");
        builder.addProductions("PARAMS", "name MORE_PARAMS", EPSILON);
        builder.addProductions("MORE_PARAMS", ", name MORE_PARAMS", EPSILON);

        builder.addProductions("STATEMENTS_BLOCK", "begin STATEMENTS end");
        builder.addProductions("STATEMENTS", "STATEMENT STATEMENTS", EPSILON);
        builder.addProductions("STATEMENT", "RETURN", "ASSIGN", "IF", "WHILE");

        builder.addProductions("EXPR", "T EXPR'");
        builder.addProductions("EXPR'", "+ T EXPR'", "- T EXPR'", EPSILON);
        builder.addProductions("T", "F T'");
        builder.addProductions("T'", "* F T'", "/ F T'", EPSILON);
        builder.addProductions("F", "( EXPR )", "number", "VARIABLE_OR_FUNCTION_CALL");

        builder.addProductions("VARIABLE_OR_FUNCTION_CALL", "name FUNCTION_CALL");
        builder.addProductions("FUNCTION_CALL", "( FUNCTION_CALL_PARAMS )", EPSILON);
        builder.addProductions("FUNCTION_CALL_PARAMS", "EXPR MORE_FUNCTION_CALL_PARAMS", EPSILON);
        builder.addProductions("MORE_FUNCTION_CALL_PARAMS", ", EXPR MORE_FUNCTION_CALL_PARAMS", EPSILON);

        builder.addProductions("RETURN", "return EXPR ;");

        builder.addProductions("ASSIGN", "name = EXPR ;");

        builder.addProductions("IF", "if ( EXPR ) then STATEMENTS_BLOCK ELSE ;");
        builder.addProductions("ELSE", "else STATEMENTS_BLOCK", EPSILON);

        builder.addProductions("WHILE", "while ( EXPR ) STATEMENTS_BLOCK ;");

        builder.validateGrammar();

     // SONAR_ON
    }

}
