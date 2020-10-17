package com.kaikeba.controller;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.UserService;
import com.kaikeba.util.LoginUtil;
import com.kaikeba.util.WebUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.ref.ReferenceQueue;

/**
 * 管理控制器
 *
 * @author Faker
 * @date 2020/09/30
 */
public class LoginController {

    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String code = request.getParameter("code");
        if (LoginUtil.checkLoginSMS(userPhone, code)) {
            Object user = null;
            LoginUtil.setLoginUserPhone(userPhone);
            if ((user = CourierService.findBySysPhone(userPhone)) != null) {
                LoginUtil.setUser(user);
                CourierService.updateLoginTime(userPhone);
                return WebUtil.toJson(new Message(2, "快递员登录成功"));
            } else if ((user = UserService.findByUserPhone(userPhone)) != null) {
                LoginUtil.setUser(user);
                UserService.updateLoginTime(userPhone);
                return WebUtil.toJson(new Message(1, "用户登录登录成功"));
            } else {
                User user1 = new User();
                user1.setUserPhone(userPhone);
                LoginUtil.setUser(user1);
                return WebUtil.toJson(new Message(0, "新用户登录成功"));
            }
        }
        return WebUtil.toJson(new Message(-1,"验证码错误!"));
    }

    @ResponseBody("/admin/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        LoginUtil.setLoginUserPhone(null);
        LoginUtil.setUser(null);
        return WebUtil.toJson(new Message(0,"登出成功"));
    }

    @ResponseBody("/admin/sendMessage.do")
    public String sendMessage(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        String code=WebUtil.getCode();
        //LoginUtil.sendLoginMessage(userPhone,code);
        LoginUtil.setLoginSMS(userPhone, "123456");
        return WebUtil.toJson(new Message(0,"短信发送成功"));
    }

    /**
     * 个人信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/admin/userInfo.do")
    public String userInfo(HttpServletRequest request,HttpServletResponse response){
        Object user = LoginUtil.getUser();
        Message message = new Message();
        if (user instanceof User){
            if (((User) user).getUsername()==null){
                message.setStatus(0);
                message.setResult("未认证");
                message.setData(user);
            }else{
                message.setStatus(1);
                message.setResult("用户");
                message.setData((User)user);
            }
        }
        else{
            message.setStatus(2);
            message.setResult("管理员");
            message.setData((Courier)user);
        }
        return WebUtil.toJson(message);
    }

    @ResponseBody("/admin/sendUpdateUserPhone.do")
    public String updateUserPhone(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String code=WebUtil.getCode();
        LoginUtil.sendUpdateUserPhone(userPhone,code);
        return WebUtil.toJson(new Message(0,"短信发送成功"));
    }
}
