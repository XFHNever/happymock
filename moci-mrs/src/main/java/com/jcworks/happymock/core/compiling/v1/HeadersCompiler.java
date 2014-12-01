package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;
import com.google.common.base.Strings;

import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-29
 */
public class HeadersCompiler extends CompilerUtility implements Compiler {
    @Override
    public void compile(String dsl) throws CompileException {
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        Map<String,String> headMap=super.getChildDSL(jsonObject);
        Iterator<String> iterator=headMap.keySet().iterator();
        while(iterator.hasNext()){
            String headName=iterator.next();
            String headValue=headMap.get(headName);
            if(Strings.isNullOrEmpty(headName)||Strings.isNullOrEmpty(headValue)){
                throw new CompileException(CompileErrorCode.INVALID_HEADER_NAME_VALUE_PARE,headName,headValue);
            }
        }
    }

    @Override
    public String getKeyword() {
        return "headers";
    }

    @Override
    public Compiler locateChild(String keyword) {
        return null;
    }
}
