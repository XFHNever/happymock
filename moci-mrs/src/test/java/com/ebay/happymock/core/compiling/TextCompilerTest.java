package com.ebay.happymock.core.compiling;

import com.ebay.happymock.core.compiling.v1.BodyCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-26
 */
@Test
public class TextCompilerTest {

    @Test public void testCompile(){
      String dsl="{\n" +
              "               \"xml\":\"<request><parameters><id>1</id></parameters></request>\"\n" +
              "        }";
        BodyCompiler bodyCompiler =new BodyCompiler();
        try {
            bodyCompiler.compile(dsl);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }

    @Test public void testCompile1(){
        String dsl="{\"json\":{\"foo\":\"bar\"}}";
        BodyCompiler bodyCompiler =new BodyCompiler();
        try {
            bodyCompiler.compile(dsl);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }

}
