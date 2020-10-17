package com.kaikeba.controller;

import com.kaikeba.bean.Courier;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.User;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.mvc.ResponseView;
import com.kaikeba.service.ExpressService;
import com.kaikeba.service.UserService;
import com.kaikeba.util.LoginUtil;
import com.kaikeba.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserController {

    /**
     * 懒人排行
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/user/lazyBoard.do")
    public String lazyBoard(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> total = ExpressService.lazyBoard(2);
        List<Map<String, Object>> year = ExpressService.lazyBoard(1);
        List<Map<String, Object>> month = ExpressService.lazyBoard(0);
        Map<String,Object> maps=new HashMap<>();
        maps.put("total",total);
        maps.put("year",year);
        maps.put("month",month);
        if(maps!=null && maps.size()>0){
            return WebUtil.toJson(new Message(0,"查询成功",maps));
        }
        return WebUtil.toJson(new Message(-1,"查询失败"));
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request,HttpServletResponse response){
        User user = WebUtil.toBean(request.getParameterMap(), new User());
        if (UserService.insert(user)){
            LoginUtil.setUser(user);
            UserService.updateLoginTime(user.getUserPhone());
            return WebUtil.toJson(new Message(0,"认证成功"));
        }
        return WebUtil.toJson(new Message(-1,"认证失败"));
    }

    @ResponseView("/user/createQrCode.do")
    public String createUserQrCode(HttpServletRequest request,HttpServletResponse response){
        LoginUtil.setQrCode("userPhone_"+LoginUtil.getLoginUserPhone());
        return "/personQrCode.html";
    }

    @ResponseView("/express/createQrCode.do")
    public String createExpressQrCode(HttpServletRequest request,HttpServletResponse response){
        LoginUtil.setQrCode("code_"+request.getParameter("code"));
        return "/personQrCode.html";
    }

    @ResponseBody("/qrCode/qrCode.do")
    public String qrCode(HttpServletRequest request,HttpServletResponse response){
        String qrCode = LoginUtil.getQrCode();
        if (qrCode!=null){
            return WebUtil.toJson(new Message(0,"获取二维码成功",qrCode));
        }
        return WebUtil.toJson(new Message(-1,"获取二维码失败"));
    }

}
