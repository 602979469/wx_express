package com.kaikeba.filter;

import com.kaikeba.util.LoginUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 访问控件过滤器
 *
 * @author Faker
 * @date 2020/09/30
 */
@WebFilter({"/","*.html"})
public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //第一次填充session
        if (LoginUtil.getSession()==null){
            HttpSession session = ((HttpServletRequest) req).getSession();
            session.setMaxInactiveInterval(-1);
            LoginUtil.setSession(session);
        }
        String url = request.getRequestURI();
        if (LoginUtil.getUser()!=null || "/login.html".equals(url) || "/admin/login.do".equals(url) || "/admin/sendMessage.do".equals(url)){
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath()+"login.html");
        }
    }
}
