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

package functionly.ast.init.util;

import com.romanenco.cfrm.ParsingTreeNode;
import com.romanenco.cfrm.ast.AbstractSAttrVisitor;

import functionly.ast.init.Constant;

public class ValueCopier extends AbstractSAttrVisitor {

    private final int childIndex;

    public ValueCopier(int childIndex) {
        super();
        this.childIndex = childIndex;
    }

    @Override
    protected void visited(ParsingTreeNode node) {
        node.setAttribute(Constant.VALUE,
                node.getChild(childIndex).getAttribute(Constant.VALUE));
    }

}