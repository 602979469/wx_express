package com.kaikeba.filter;

import com.kaikeba.util.WebUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问控件过滤器
 *
 * @author Faker
 * @date 2020/09/30
 */
//@WebFilter({"/","*.html"})
public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //第一次填充session
        if (WebUtil.getSession()==null){
            WebUtil.setSession(((HttpServletRequest) req).getSession());
        }
        String userName = WebUtil.getLoginUsername();
        String url = request.getRequestURI();
        if(userName != null || url.endsWith("login.html") || url.endsWith("/admin/login.do")) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath()+"/login.html");
        }
    }
}
