package com.jcworks.happymock.middleware.http.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jicui on 10/12/14.
 */
public final class MrsRunner {
    private static final Logger LOG = LoggerFactory.getLogger(MrsRunner.class);
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerBootstrap serverBootstrap;

    public void run(int port) throws Exception {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new HappyMockServerChannelInitializer());
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(port)).sync();
            LOG.info("happy mock server starts up on http://localhost:"+ port);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void shutdown(){
        if(boss!=null) boss.shutdownGracefully();
        if(worker!=null) worker.shutdownGracefully();
    }

}
