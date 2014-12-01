package com.jcworks.happymock.middleware.http.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * User: jicui
 * Date: 14-10-16
 */
public class HappyMockServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //HappyMockEngine.happyMockEngine().withPersistAdaptor();
        ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
        ch.pipeline().addLast("happy-mock-decoder", new HappyMockRequestDecoder());
        ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
        ch.pipeline().addLast("happy-mock-encoder", new HappyMockResponseEncoder());
        ch.pipeline().addLast("mrs-ServerHandler", new MRSServerHandler());
    }
}
