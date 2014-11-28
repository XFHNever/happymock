package com.ebay.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;

/**
 * User: jicui
 * Date: 14-8-26
 */
public class BodyJsonCompiler implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        try{
            //request dsl should be a json object
            JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
    }

    @Override
    public String getKeyword() {
        return "json";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
