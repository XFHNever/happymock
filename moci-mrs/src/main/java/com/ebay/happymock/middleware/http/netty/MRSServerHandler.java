package com.ebay.happymock.middleware.http.netty;

import com.ebay.happymock.core.compiling.v1.RootCompiler;
import com.ebay.happymock.core.compiling.v1.exception.CompileException;
import com.ebay.happymock.core.entity.MociSetting;
import com.ebay.happymock.core.entity.Request;
import com.ebay.happymock.core.entity.Response;
import com.ebay.happymock.search.MociFinder;
import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import static com.ebay.happymock.middleware.http.netty.MrsRunnerBuilder.mrsRunner;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jicui on 10/12/14.
 */
public class MRSServerHandler extends SimpleChannelInboundHandler<Request> {
    private static final Logger LOG = LoggerFactory.getLogger(MRSServerHandler.class);
    private static final String COMPILE_CONTEXT = "/compile";

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Request request) throws Exception {
        //mock request
        Response response = null;
        if (!request.getUrl().startsWith(COMPILE_CONTEXT)) {
            LOG.info("Start mock request on endpoint={} for user={}",request.getUrl(),request.getUsername());
            Stopwatch stopwatch=Stopwatch.createStarted();
            response=mrsRunner().getHappyMockEngine().mock(request);
            stopwatch.stop();
            LOG.info("End mock request time elapsed={}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
        //compile request
        if (request.getUrl().startsWith(COMPILE_CONTEXT)) {
            LOG.debug("Start compile request");
            response =mrsRunner().getHappyMockEngine().compile(request.getText());
            LOG.debug("End compile request");
        }
        //close if the connection=close
        ChannelFuture future = ctx.writeAndFlush(response);
        if (!request.isKeepAlive()) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Error: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
