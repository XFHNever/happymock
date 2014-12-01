package com.jcworks.happymock.middleware.http.netty;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jcworks.happymock.core.entity.Request.RequestBuilder;
import static com.jcworks.happymock.core.entity.Request.RequestBuilder.newRequest;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;

/**
 * Created by jicui on 10/12/14.
 */
public class HappyMockRequestDecoder extends
        MessageToMessageDecoder<FullHttpRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(HappyMockRequestDecoder.class);
    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("[^&?/]*=[^&]*");
    private static final Pattern URI_PATTERN = Pattern.compile("/[0-9a-zA-Z/]+");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, List<Object> objects) throws Exception {
        LOG.trace("decode request start...");
        RequestBuilder requestBuilder = newRequest();
        requestBuilder.withMethod(fullHttpRequest.getMethod().name());
        composeURI(requestBuilder, fullHttpRequest.getUri());
        //set cookies
        String cookieString = fullHttpRequest.headers().get(HttpHeaders.Names.COOKIE);
        Map<String, String> cookieMap = new HashMap<String, String>();
        if (null != cookieString) {
            Set<Cookie> cookies = CookieDecoder.decode(cookieString);
            if (!cookies.isEmpty()) {
                for (Cookie cookie : cookies) {
                    String ck = cookie.getName();
                    String cv = cookie.getValue();
                    cookieMap.put(ck.toLowerCase(), cv);
                }
                requestBuilder.withCookies(ImmutableMap.builder().putAll(cookieMap).build());
            }
        }
        //set keep alive flag
        if (isKeepAlive(fullHttpRequest)) {
            requestBuilder.withKeepAlive();
        }
        //set headers
        Iterator<Map.Entry<String, String>> itor = fullHttpRequest.headers().iterator();
        Map<String, String> headMap = new HashMap<String, String>();
        if (null != itor) {
            while (itor.hasNext()) {
                Map.Entry<String, String> entry = itor.next();
                String hn = entry.getKey();
                String hv = entry.getValue();
                headMap.put(hn.toLowerCase(), hv);
            }
            requestBuilder.withHeaders(ImmutableMap.builder().putAll(headMap).build());
        }
        //set content body
        int contentLength = fullHttpRequest.content().readableBytes();
        if (contentLength > 0) {
            String content = fullHttpRequest.content().toString(UTF_8);
            requestBuilder.withBody(content);
        }
        LOG.trace("decode request end.");
        objects.add(requestBuilder.build());
    }

    private void composeURI(RequestBuilder requestBuilder, String uri) {
        String[] uris = uri.split("/");
        boolean hasCtx = false;
        if (uris.length >= 1 && !Strings.isNullOrEmpty(uris[1]) && uris[1].startsWith("~")) {
            requestBuilder.withUserCtx(uris[1].substring(1));
            hasCtx = true;
        }

        Matcher matcher = QUERY_PARAM_PATTERN.matcher(uri);
        Map<String, String> queryMap = new HashMap<String, String>();
        while (matcher.find()) {
            String gp = matcher.group();
            String[] nv = gp.split("=");
            queryMap.put(nv[0], nv[1]);
        }
        requestBuilder.withQueryParam(ImmutableMap.builder().putAll(queryMap).build());

        if (hasCtx) {
            int index = uri.indexOf("/", 1);
            uri = uri.substring(index);
        }
        requestBuilder.withURL(uri);

        Matcher urlmatcher = URI_PATTERN.matcher(uri);
        urlmatcher.find();
        requestBuilder.withURI(urlmatcher.group());
    }
}
