package com.study.netty.http;

import java.io.UnsupportedEncodingException;

public class MyServlet extends MyAbstractServlet {
    @Override
    public void doGet(MyRequest request, MyResponse response) {
        try {
            response.write(request.getParameter("name"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        doGet(request, response);
    }
}
