package com.kaikeba.mvc;

import com.kaikeba.util.LoginUtil;
import com.kaikeba.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * dispatcher com.kaikeba.servlet
 *
 * @author Faker
 * @date 2020/09/30
 */
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        String path = config.getInitParameter("contentConfigLocation");
        InputStream is = DispatcherServlet.class.getClassLoader().getResourceAsStream(path);
        HandlerMapping.load(is);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.    获取用户请求的uri   /xx.do
        String uri = req.getRequestURI();
        HandlerMapping.MVCMapping mapping = HandlerMapping.get(uri);
        if (LoginUtil.getSession()==null){
            //session永不过期
            req.getSession().setMaxInactiveInterval(-1);
            LoginUtil.setSession(req.getSession());
        }
        if( mapping == null){
            resp.sendError(404,"MVC：映射地址不存在："+uri);
            return;
        }
        Object obj = mapping.getObj();
        Method method = mapping.getMethod();
        Object result = null;
        try {
            result = method.invoke(obj, req, resp);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        switch (mapping.getType()){
            case TEXT:
                resp.getWriter().write((String)result);
                break;
            case VIEW:
                resp.sendRedirect((String)result);
                break;
            default:
                break;
        }
    }

}
