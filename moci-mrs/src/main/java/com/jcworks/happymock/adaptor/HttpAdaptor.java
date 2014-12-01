package com.jcworks.happymock.adaptor;


import com.jcworks.happymock.core.entity.Request;
import com.jcworks.happymock.core.entity.Response;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: jicui
 * Date: 14-8-12
 */
@Deprecated
public class HttpAdaptor {
    private static final Logger LOG = LoggerFactory.getLogger(HttpAdaptor.class);

    public static Request fromHttpRequest(HttpServletRequest httpServletRequest){
        Request request=new Request();
        /*populate http method*/
        request.setMethod(httpServletRequest.getMethod());
       /* populate URI*/
        String constructedUri=httpServletRequest.getRequestURI();
        request.setUri(constructedUri);
        /* parse domainName and resourceName */
        String[] uris = constructedUri.split("/");
        if (uris.length >= 4) {
            if (!uris[1].equals(""))
                request.setUsername(uris[1]);
        } else if (uris.length == 3 ){
            if (!uris[1].equals(""))
                request.setUsername(uris[1]);
        } else if (uris.length == 2){
            if (!uris[1].equals(""))
                request.setUsername(uris[1]);
        }

        //add query string to uri
        String queryString=httpServletRequest.getQueryString();
        if(!Strings.isNullOrEmpty(queryString)){
            constructedUri+="?"+queryString;
            Map<String,String> queryMap=new HashMap<String,String>();
            String[] queryToken=queryString.split("&");
            for(String query:queryToken){
                String[] nv=query.split("=");
                queryMap.put(nv[0],nv[1]);
            }
            request.setQueryParam(queryMap);
        }

        request.setUrl(constructedUri);
        //compose query map
       /* populate cookie*/
        Cookie[] cookies=httpServletRequest.getCookies();
        if(cookies!=null&&cookies.length>0){
            Map<String,String> cookieMap=new HashMap<String,String>();
            for(Cookie cookie:cookies){
                String ck=cookie.getName();
                String cv=cookie.getValue();
                cookieMap.put(ck.toLowerCase(),cv);
            }
            request.setCookies(cookieMap);
        }

        /* populate headers*/
        Enumeration enumeration =httpServletRequest.getHeaderNames();
        Map<String,String> headMap=new HashMap<String,String>();
        if(null!=enumeration){
            while(enumeration.hasMoreElements()){
                String hn=(String)enumeration.nextElement();
                String hv=httpServletRequest.getHeader(hn);
                headMap.put(hn.toLowerCase(),hv);
            }
            if(headMap!=null){
                request.setHeaders(headMap);
            }
        }

        /**
         * populate body content,if method is get the body is null
         */
        int contentLength=httpServletRequest.getContentLength();
        if(contentLength>0){
            try {
                String content= CharStreams.toString(httpServletRequest.getReader());
                request.setText(content);
            } catch (IOException e) {
                request.setText(null);
            }
        }
        return request;
    }

    public static void toHttpResponse(HttpServletResponse httpServletResponse,Response response) {
        //set return http code
        if(response.getCode()>0){
            httpServletResponse.setStatus(response.getCode());
        }
       /* //set the cookies
        if(null!=response.getCookies()&&!response.getCookies().isEmpty()){
            Iterator<String> itor=response.getCookies().keySet().iterator();
            while(itor.hasNext()){
                String cn=itor.next();
                String val=response.getCookies().get(cn);
                Cookie cookie=new Cookie(cn,val);
                httpServletResponse.addCookie(cookie);
            }
        }*/
        //set headers
        if(null!=response.getHeaders()&&!response.getHeaders().isEmpty()){
            Iterator<String> itor=response.getHeaders().keySet().iterator();
            while(itor.hasNext()){
                String hn=itor.next();
                String hv=response.getHeaders().get(hn);
                httpServletResponse.setHeader(hn,hv);
            }
        }
       //set body
        OutputStream ps= null;
        try {
            ps = httpServletResponse.getOutputStream();
            if(!Strings.isNullOrEmpty(response.getText())){
                //httpServletResponse.setHeader("Content-Type","text/html");
                ps.write(response.getText().getBytes("UTF-8"));
            }else if(!Strings.isNullOrEmpty(response.getJson())){
                //httpServletResponse.setHeader("Content-Type","application/json");
                ps.write(response.getJson().getBytes("UTF-8"));
            }else if(!Strings.isNullOrEmpty(response.getXml())){
                //httpServletResponse.setHeader("Content-Type","application/xml");
                ps.write(response.getXml().getBytes("UTF-8"));
            }
        } catch (IOException e) {
            LOG.error("writing to response fail",e);
        }
    }
}
