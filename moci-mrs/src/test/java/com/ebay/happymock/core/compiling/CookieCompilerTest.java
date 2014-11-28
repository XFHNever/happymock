package com.ebay.happymock.core.compiling;

import com.ebay.happymock.core.compiling.v1.CookieCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-22
 */
@Test
public class CookieCompilerTest {
    @Test
    void testCompile(){
        String dls="{\"Authorization\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\",\"key\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\"}";
        try {
            new CookieCompiler().compile(dls);
        } catch (CompileException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Test
    void testCompile1(){
        String dls="{\"Authorization\":\"gWwh4zP4l90Cj4wQCslKHpB67_8a\",\"key\":\"\"}";
        try {
            new CookieCompiler().compile(dls);
        } catch (CompileException e) {
            Assert.assertEquals(e.getErrorCode().getCode(), CompileErrorCode.INVALID_COOKIE_NAME_VALUE_PARE.getCode());
        }
    }
}
