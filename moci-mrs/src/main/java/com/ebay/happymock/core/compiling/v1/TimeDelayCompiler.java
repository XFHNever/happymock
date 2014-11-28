package com.ebay.happymock.core.compiling.v1;

import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;

/**
 * User: jicui
 * Date: 14-10-22
 */
public class TimeDelayCompiler implements Compiler{
    @Override
    public void compile(String dsl) throws CompileException {
        Integer timeDelay =null;
        try {
            timeDelay=Integer.valueOf(dsl);
        } catch (Exception e) {
            throw new CompileException(CompileErrorCode.INVALID_TIME_DELAY_CODE, dsl);
        }

        if(timeDelay<=0||timeDelay>5*60*1000){
            throw new CompileException(CompileErrorCode.INVALID_TIME_DELAY_CODE, dsl);
        }
    }

    @Override
    public String getKeyword() {
        return "timeDelay";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
