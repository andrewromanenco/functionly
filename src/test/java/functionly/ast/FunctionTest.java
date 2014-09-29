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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FunctionTest {

    private static final String SOME_NAME = "some";

    @Test
    public void test1() {
        final Function funct1 = new Function(SOME_NAME, params(), null);
        final Function funct2 = new Function(SOME_NAME, params(), null);
        Assert.assertTrue(funct1.equals(funct2));
        Assert.assertTrue(funct1.hashCode() == funct2.hashCode());
    }

    @Test
    public void test2() {
        final Function funct1 = new Function(SOME_NAME, params(), null);
        final Function funct2 = new Function("other", params(), null);
        Assert.assertFalse(funct1.equals(funct2));
    }

    @Test
    public void test3() {
        final Function funct1 = new Function(SOME_NAME, params(), null);
        final Function funct2 = new Function(SOME_NAME, params("a"), null);
        Assert.assertFalse(funct1.equals(funct2));
    }

    @Test
    public void test4() {
        final Function funct1 = new Function(SOME_NAME, params("a", "b"), null);
        final Function funct2 = new Function(SOME_NAME, params("a", "b"), null);
        Assert.assertTrue(funct1.equals(funct2));
        Assert.assertTrue(funct1.hashCode() == funct2.hashCode());
    }

    @Test
    public void test5() {
        final Function funct1 = new Function(SOME_NAME, params("a", "b"), null);
        final Function funct2 = new Function(SOME_NAME, params("x", "y"), null);
        Assert.assertTrue(funct1.equals(funct2));
        Assert.assertTrue(funct1.hashCode() == funct2.hashCode());
    }

    private List<String> params(String... names) {
        final List<String> result = new ArrayList<>();
        for (final String param: names) {
            result.add(param);
        }
        return result;
    }
}
