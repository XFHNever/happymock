package com.ebay.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.ebay.happymock.core.compiling.v1.RequestCompiler;
import com.ebay.happymock.core.compiling.v1.ResponseCompiler;

import java.lang.*;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

/**
 * Check if 'request' and response's existence
 * and pass in sub compile to deep compile
 *
 * User: jicui
 * Date: 14-8-22
 */
public class RootCompiler extends CompilerUtility implements Compiler {
    public RootCompiler() {}

    @Override
    public void compile(String dsl) throws CompileException {
        int requestCompiled=0;
        int responseCompiled=0;
        checkNotNull(dsl);
        JSONObject jsonObject=null;
        try{
            //dsl should be a jsonobject
            jsonObject= JSON.parseObject(dsl);
        }catch(Exception e){
            throw new CompileException(CompileErrorCode.UNPARSEABLE_FORMAT, e.getMessage());
        }
        Map<String,String> dslMap=super.getChildDSL(jsonObject);
        Iterator<String> iterator=dslMap.keySet().iterator();
        while(iterator.hasNext()){
            String keyword=iterator.next();
            String innerDSL=dslMap.get(keyword);
            Compiler compiler=locateChild(keyword);
            if(compiler==null){
                throw new CompileException(CompileErrorCode.UNRECOGNIZED_KEYWORD, keyword);
            }else{
                compiler.compile(innerDSL);
            }
            if(compiler.getKeyword().equals("request")){
                requestCompiled+=1;
            }
            if(compiler.getKeyword().equals("response")){
                responseCompiled+=1;
            }
        }
        //check if all required keyword has been compiled
        if(requestCompiled!=1){
            throw new CompileException(CompileErrorCode.MISSING_COMPILED_KEYWORD, "request","root","1");
        }
        if(responseCompiled!=1){
            throw new CompileException(CompileErrorCode.MISSING_COMPILED_KEYWORD, "response","root","1");
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String getKeyword() {
        return "root";
    }

    @Override
    public Compiler locateChild(String keyword) {
        if(keyword.equals("request")){
            return new RequestCompiler();
        }
        if(keyword.equals("response")){
            return new ResponseCompiler();
        }
        return null;
    }
}
