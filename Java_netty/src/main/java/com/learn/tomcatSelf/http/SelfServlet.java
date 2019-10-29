package com.learn.tomcatSelf.http;

/**
 * autor:liman
 * createtime:2019/10/28
 * comment:
 */
public abstract class SelfServlet {

    public void service(SelfRequest request,SelfResponse response){
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet(SelfRequest request,SelfResponse response);

    public abstract void doPost(SelfRequest request,SelfResponse response);

}
