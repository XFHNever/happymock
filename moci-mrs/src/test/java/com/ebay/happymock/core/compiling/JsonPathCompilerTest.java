package com.ebay.happymock.core.compiling;

import com.ebay.happymock.core.compiling.v1.JsonPathCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-26
 */
@Test
public class JsonPathCompilerTest {

    @Test public void testCompile(){
        JsonPathCompiler jsonPathCompiler=new JsonPathCompiler();
        String dsl="{\n" +
                "              \".ticketSeat[?(@.type=='R')]\":\"1\",\n" +
                "              \"$.ticketSeat\":\"2\"\n" +
                "        }";
        try {
            jsonPathCompiler.compile(dsl);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.INVALID_JSONPATH.getCode());
        }
    }


}
