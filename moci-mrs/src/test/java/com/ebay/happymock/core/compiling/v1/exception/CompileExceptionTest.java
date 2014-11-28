package com.ebay.happymock.core.compiling.v1.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: jicui
 * Date: 14-8-21
 */
@Test
public class CompileExceptionTest {
    @Test public void testException(){
        Exception excep=new CompileException(CompileErrorCode.UNRECOGNIZED_KEYWORD,"req");
        Assert.assertEquals(excep.getMessage(),"the keyword is unrecognized,keyword=req");
    }

    @Test public void testException1(){

        Exception excep=new CompileException(CompileErrorCode.UNRECOGNIZED_KEYWORD,new NullPointerException("test"),"req");
        Assert.assertEquals(excep.getMessage(),"the keyword is unrecognized,keyword=req");
    }
}
