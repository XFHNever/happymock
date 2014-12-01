package com.jcworks.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcworks.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.jcworks.happymock.core.compiling.v1.exception.CompileException;

import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-22
 */
public class RequestCompiler extends CompilerUtility implements Compiler {

    @Override
    public void compile(String dsl) throws CompileException {
        int subNode=0;
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
            jsonObject=JSON.parseObject(dsl);
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
                subNode++;
            }
        }
        if(subNode==0){
            throw new CompileException(CompileErrorCode.EMPTY_DSL_BODY,dsl);
        }
    }

    @Override
    public String getKeyword() {
       return "request";
    }

    @Override
    public Compiler locateChild(String keyword) {
        if(keyword.equals("uri")){
            return new UriCompiler();
        }
        if(keyword.equals("cookies")){
            return new CookieCompiler();
        }
        if(keyword.equals("body")){
            return new BodyCompiler();
        }
        if(keyword.equals("xpath")){
            return new XPathCompiler();
        }
        if(keyword.equals("jsonpath")){
            return new JsonPathCompiler();
        }
        if(keyword.equals("headers")) {
            return new HeadersCompiler();
        }
        if(keyword.equals("method")){
            return new HttpMethodCompiler();
        }
        return null;
    }
}
