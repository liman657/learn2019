package com.learn.spring.source.introduction.controller;

import com.learn.spring.source.introduction.service.DemoService;
import com.learn.spring.source.introduction.service.impl.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * autor:liman
 * createtime:2020/9/2
 * comment:
 */
@Slf4j
@WebServlet(urlPatterns = "/demo1")
public class DemoServlet01 extends HttpServlet {

    DemoService demoService = new DemoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("request in ");
        resp.getWriter().println(demoService.findAll().toString());
    }
}
