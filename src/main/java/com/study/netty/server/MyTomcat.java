package com.study.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyTomcat {

    public void start(int port) throws  Exception{
        //netty 主从线程模型
        //boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //Netty服务
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try{
            serverBootstrap.group(bossGroup,workerGroup)
                           .channel(NioServerSocketChannel.class)
                           .childHandler(new ChannelInitializer<SocketChannel>() {
                               @Override
                               protected void initChannel(SocketChannel client) throws Exception {
                                   //业务逻辑链路,编码器
                                   client.pipeline().addLast(new HttpResponseEncoder());
                                   //解码器
                                   client.pipeline().addLast(new HttpRequestDecoder());
                                   //业务逻辑处理
                                   client.pipeline().addLast(new MyTomcatHandler());
                               }
                           })
                           .option(ChannelOption.SO_BACKLOG,128)//主线程的配置
                           .childOption(ChannelOption.SO_KEEPALIVE,true);//子线程的配置
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("我的Tomcat 在8080端口启动了.....");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new MyTomcat().start(8080);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
