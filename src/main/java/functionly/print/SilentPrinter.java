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

import java.util.List;

import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.Token;

import functionly.Printer;
import functionly.ast.Program;

public class SilentPrinter implements Printer {

    @Override
    public void print(List<Token> tokens) {
        // do nothing
    }

    @Override
    public void print(ParsingTreeNode parsingTree) {
        // do nothing
    }

    @Override
    public void print(Program program) {
        // do nothing
    }

}
