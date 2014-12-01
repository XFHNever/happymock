package com.jcworks.happymock.core.compiling;

import com.jcworks.happymock.core.compiling.v1.XPathCompiler;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-26
 */
@Test
public class XPathCompilerTest {

    @Test public void testCompile(){
        String dsl="{\n" +
                "              \"/request/parameters/name[@cid='test']\":\"1\",\n" +
                "              \"/request/parameters/id[text()='2000']\":\"1\"\n" +
                "        }";
        XPathCompiler xPathCompiler=new XPathCompiler();
        try {
            xPathCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.INVALID_XPATH.getCode());
        }
    }

    @Test public void testCompile1(){
        String dsl="{\n" +
                "              \"/request/parameters/name[@cid='test']\":\"1\",\n" +
                "              \"/request/parameters/id[text()='2000']\":\"a\"\n" +
                "        }";
        XPathCompiler xPathCompiler=new XPathCompiler();
        try {
            xPathCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.INVALID_EXPRESSION_VALUE.getCode());
        }
    }
}
