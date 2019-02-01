package com.study.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.UnsupportedEncodingException;
import static io.netty.handler.codec.rtsp.RtspHeaders.Names.*;

public class MyResponse {
    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public MyResponse(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public void write(String out) throws UnsupportedEncodingException {
        try {
            if(null == out) return;
            FullHttpResponse response = new DefaultFullHttpResponse(
                                            HttpVersion.HTTP_1_1,
                                            HttpResponseStatus.OK,
                                            Unpooled.wrappedBuffer(out.getBytes("utf-8"))
                                        );
            response.headers().set(CONTENT_TYPE,"text/json");
            response.headers().set(CONTENT_LENGTH,response.content().readableBytes());
            response.headers().set(EXPIRES,0);
            if(HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, RtspHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ctx.flush();
        }
    }
}
