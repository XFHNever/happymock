package com.ebay.happymock.core.processor;

import com.ebay.happymock.core.entity.MociRequest;
import com.ebay.happymock.core.entity.Request;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Process headers
 *
 * User: jicui
 * Date: 14-9-1
 */
public class HeadersProcessor implements RequestProcessor {
    private ImmutableMap<String,String> headersMap;

    @Override
    public boolean match(Request httpRequest) {
        Map<String,String> httpHeaders=httpRequest.getHeaders();
        if(httpHeaders==null){
            return false;
        }
        boolean match=true;
        Iterator<String> keyItor=headersMap.keySet().iterator();
        while(keyItor.hasNext()&&match){
            String key= keyItor.next().toLowerCase();//use lower case to match
            if(!httpHeaders.containsKey(key)){
                match=false;
            }else{
                String v1=httpHeaders.get(key);
                String v2=headersMap.get(key);
                if(!v1.trim().equalsIgnoreCase(v2.trim())){
                    match=false;
                }
            }
        }
        return match;
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if(mociRequest.getHeaders()!=null&&!mociRequest.getHeaders().isEmpty()){
            Map<String,String> headMap=new HashMap<String,String>();
            Iterator<String> itor= mociRequest.getHeaders().keySet().iterator();
            while(itor.hasNext()){
                String key=itor.next();
                String value=mociRequest.getHeaders().get(key);
                headMap.put(key.toLowerCase(),value);
            }
            headersMap= ImmutableMap.copyOf(headMap);
            return true;
        }
        return false;
    }
}
