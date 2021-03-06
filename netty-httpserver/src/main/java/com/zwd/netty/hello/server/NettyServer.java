package com.zwd.netty.hello.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private static final int port = 8080; //设置服务端端口
    private static  EventLoopGroup work = new NioEventLoopGroup();   // 通过nio方式来接收连接和处理连接

    private static  ServerBootstrap b = new ServerBootstrap();

    /**
     * channel 通道
     * buffer 缓冲区
     * select 事件 轮询通道
     */



    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public static void main(String[] args) throws InterruptedException {
        try {
            b.group(work);   // 线程
            b.channel(NioServerSocketChannel.class); // 通道
            b.childHandler(new NettyServerFilter()); //设置处理器
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务端启动成功...");
            // 监听服务器关闭监听
            ChannelFuture end = f.channel().closeFuture().sync();

        } finally {
            work.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }
}