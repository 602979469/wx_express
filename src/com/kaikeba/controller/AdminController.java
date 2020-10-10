package com.kaikeba.controller;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.Message;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理控制器
 *
 * @author Faker
 * @date 2020/09/30
 */
public class AdminController {


    /**
     * 寻找登录用户名
     *
     * @param request  请求
     * @param response 响应
     * @return {@link String}
     */
    @ResponseBody("/admin/find.do")
    public String findLoginUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = WebUtil.getLoginUsername();
        Message message = new Message();
        if (username != null) {
            message.setResult(username);
            message.setStatus(0);
        } else {
            message.setResult(null);
            message.setStatus(-1);
        }
        return WebUtil.toJson(message);
    }

    @ResponseBody("/admin/loginSMS.do")
    public String loginSMS(HttpServletRequest request,HttpServletResponse response){
        String userPhone=request.getParameter("userPhone");
        String code=request.getParameter("code");
        Message message = new Message();
        if (WebUtil.getLoginSMS(userPhone,code)){
            message.setStatus(0);
            message.setResult("用户登录成功");
        }else{
            message.setStatus(-1);
            message.setResult("登录失败，验证码不正确！");
        }
        return WebUtil.toJson(message);
    }

    @ResponseBody("/admin/sendLoginSMS.do")
    public String sendLoginSMS(HttpServletRequest request,HttpServletResponse response){
        String userPhone=request.getParameter("userPhone");
        String code= WebUtil.getCode();
        WebUtil.setLoginSMS(userPhone,code);
        Message message = new Message();
        if (userPhone!=null){
            message.setStatus(0);
            message.setResult("短信已发送");
        }else{
            message.setStatus(1);
            message.setResult("短信发送失败，请检查手机号是否正确！");
        }
        return WebUtil.toJson(message);
    }

    @ResponseBody("/admin/login.do")
    public String courierLogin(HttpServletRequest request,HttpServletResponse response){
        //1.    接参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Message msg = null;
        Courier courier=null;
        if ((courier= CourierService.login(username,password))!=null){
            //{status:0,result:"登录成功"}
            msg = new Message(0,"登录成功");
            //登录时间 和 ip的更新
            CourierService.updateLoginTime(username);
            request.getSession().setAttribute("adminUserName",courier);
            WebUtil.setLoginUser(courier);
        }else {
            msg = new Message(-1, "登录失败");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/admin/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();
        message.setStatus(1);
        if (WebUtil.getLoginUser() != null) {
            WebUtil.removeUsername();
            message.setStatus(0);
        }
        return WebUtil.toJson(message);

    }


}
