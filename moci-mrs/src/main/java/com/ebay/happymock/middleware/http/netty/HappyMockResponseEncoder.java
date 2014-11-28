package com.ebay.happymock.middleware.http.netty;

import com.ebay.happymock.core.entity.Response;
import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jicui on 10/12/14.
 */
public class HappyMockResponseEncoder extends MessageToMessageEncoder<Response> {
    private static final Logger LOG = LoggerFactory.getLogger(HappyMockResponseEncoder.class);
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    @Override
    protected void encode(ChannelHandlerContext ctx, Response response, List<Object> objects) throws Exception {
        //set body
        LOG.trace("encode response start...");
        ByteBuf encodeBuf=null;
        DefaultFullHttpResponse defaultFullHttpResponse=null;
        if(!Strings.isNullOrEmpty(response.getContent())){
            encodeBuf = Unpooled.copiedBuffer(response.getContent(), UTF_8);
            defaultFullHttpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,encodeBuf);
        }else{
            defaultFullHttpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        }
        //set keep alive and add content-length
        if(response.isKeepAlive()){
            if(null!=encodeBuf){
                defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH,encodeBuf.readableBytes());
            }else{
                defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH,0);
            }
        }
        //set return http code
        if(response.getCode()>0){
            defaultFullHttpResponse.setStatus(HttpResponseStatus.valueOf(response.getCode()));
        }
        //set headers
        if(null!=response.getHeaders()&&!response.getHeaders().isEmpty()){
            Iterator<String> itor=response.getHeaders().keySet().iterator();
            while(itor.hasNext()){
                String hn=itor.next();
                String hv=response.getHeaders().get(hn);
                defaultFullHttpResponse.headers().add(hn, hv);
            }
        }
        LOG.trace("encode response end");
        objects.add(defaultFullHttpResponse);
    }
}
