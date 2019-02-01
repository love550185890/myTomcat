package com.study.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.List;
import java.util.Map;

public class MyRequest {

    private ChannelHandlerContext ctx;
    private HttpRequest request;
    public MyRequest(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public  String getUri() {
        return request.getUri();
    }

    public String getMethod() {
        return request.getMethod().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(getUri());
        return decoder.parameters();
    }

    public String getParameter(String name){
        Map<String, List<String>> map = getParameters();
        List<String> list = map.get(name);
        if(null == list) {
            return  null;
        }else {
            return list.get(0);
        }
    }
}
