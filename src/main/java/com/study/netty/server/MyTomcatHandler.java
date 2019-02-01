package com.study.netty.server;

import com.study.netty.http.MyRequest;
import com.study.netty.http.MyResponse;
import com.study.netty.http.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class MyTomcatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest r = (HttpRequest) msg;
            MyRequest request = new MyRequest(ctx,r);
            MyResponse response = new MyResponse(ctx,r);
            new MyServlet().doGet(request,response);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
