package com.longfeizeng;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//servlet 可以再e-mail,web服务器段上运行，service方法所有服务器都可以运行,Httpservlet被容器调用
public class HelloworldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("doGet");
        response.getWriter().write("<a href='http://www.baidu.com'>go</a>");
    }
}

