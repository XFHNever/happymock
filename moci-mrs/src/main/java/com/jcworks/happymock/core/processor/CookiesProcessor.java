package com.jcworks.happymock.core.processor;

import com.jcworks.happymock.core.entity.MociRequest;
import com.jcworks.happymock.core.entity.Request;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-12
 */
public class CookiesProcessor implements RequestProcessor {

    private ImmutableMap<String,String> cookies;

    @Override
    public boolean match(Request httpRequest) {
        Map<String,String> httpCookie=httpRequest.getCookies();
        if(httpCookie==null){
            return false;
        }
        boolean match=true;
        Iterator<String> keyItor=cookies.keySet().iterator();
        while(keyItor.hasNext()&&match){
           String key= keyItor.next().toLowerCase();
           if(!httpCookie.containsKey(key)){
               match=false;
           }else{
               String v1=httpCookie.get(key);
               String v2=cookies.get(key);
               if(!v1.trim().equalsIgnoreCase(v2.trim())){
                   match=false;
               }
           }
        }
        return match;
    }

    @Override
    public boolean bind(MociRequest mociRequest) {
        if(mociRequest.getCookies()!=null&&!mociRequest.getCookies().isEmpty()){
            Map<String,String> cookieMap=new HashMap<String,String>();
            Iterator<String> itor= mociRequest.getCookies().keySet().iterator();
            while(itor.hasNext()){
                String key=itor.next();
                String value=mociRequest.getCookies().get(key);
                cookieMap.put(key.toLowerCase(),value);
            }
            cookies=ImmutableMap.copyOf(cookieMap);
            return true;
        }
        return false;
    }
}
