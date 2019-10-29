package com.learn.tomcatSelf.servlet;

import com.learn.tomcatSelf.http.SelfRequest;
import com.learn.tomcatSelf.http.SelfResponse;
import com.learn.tomcatSelf.http.SelfServlet;

import java.io.IOException;

/**
 * autor:liman
 * createtime:2019/10/28
 * comment:
 */
public class FirstServlet extends SelfServlet {
    @Override
    public void doGet(SelfRequest request, SelfResponse response) {
        doPost(request,response);
    }

    @Override
    public void doPost(SelfRequest request, SelfResponse response) {
        try {
            response.write("this is first servlet");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
