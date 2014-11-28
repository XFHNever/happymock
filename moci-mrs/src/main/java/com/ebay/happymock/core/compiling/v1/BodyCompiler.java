package com.ebay.happymock.core.compiling.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebay.happymock.core.compiling.v1.exception.CompileErrorCode;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;

import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-26
 */
public class BodyCompiler extends CompilerUtility implements Compiler {

    @Override
    public void compile(String dsl) throws CompileException {
        int xmlNode=0;
        int jsonNode=0;
        int textNode=0;
        JSONObject jsonObject=null;
        try{
            //request dsl should be a json object
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
            if(compiler.getKeyword().equals("xml")){
                xmlNode+=1;
            }
            if(compiler.getKeyword().equals("json")){
                jsonNode+=1;
            }
            if(compiler.getKeyword().equals("text")){
                textNode+=1;
            }
        }
        //check if all required keyword has been compiled
        if(xmlNode+jsonNode+textNode>1){
            throw new CompileException(CompileErrorCode.INVALID_BODY_ELEMENT);
        }
    }

    @Override
    public String getKeyword() {
        return "body";
    }

    @Override
    public Compiler locateChild(String keyword) {
        if(keyword.equals("json")){
            return new BodyJsonCompiler();
        }
        if(keyword.equals("xml")){
            return new BodyXMLCompiler();
        }
        if(keyword.equals("text")){
            return new BodyTextCompiler();
        }
        return null;
    }
}