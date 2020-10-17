package com.kaikeba.controller;

import com.aliyuncs.utils.LogUtils;
import com.kaikeba.bean.BootStrapTableExpress;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.mvc.ResponseView;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.BootStrapUtil;
import com.kaikeba.util.LoginUtil;
import com.kaikeba.util.WebUtil;
import com.sun.imageio.plugins.wbmp.WBMPImageReader;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.WebConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {

    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        String status = request.getParameter("status");
        String loginUserPhone = LoginUtil.getLoginUserPhone();
        List<Express> byUserPhone = ExpressService.findByUserPhone(loginUserPhone);
        if (byUserPhone !=null && byUserPhone.size()>0) {
            Stream<Express> statusOExpress = byUserPhone.stream().filter(express -> {
                if (express.getStatus() == 0) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = o1.getInTime().getTime();
                long o2Time = o2.getInTime().getTime();
                return (int) (o1Time - o2Time);
            });
            Stream<Express> status1Express = byUserPhone.stream().filter(express -> {
                if (express.getStatus() == 1) {
                    return true;
                } else {
                    return false;
                }
            }).sorted((o1, o2) -> {
                long o1Time = o1.getInTime().getTime();
                long o2Time = o2.getInTime().getTime();
                return (int) (o1Time - o2Time);
            });
            Object[] s0 = statusOExpress.toArray();
            Object[] s1 = status1Express.toArray();
            List<BootStrapTableExpress> status0=new ArrayList<>();
            List<BootStrapTableExpress> status1=new ArrayList<>();
            for (Object o : s0) {
                status0.add(BootStrapUtil.castExpress((Express) o));
            }
            for (Object o : s1) {
                status1.add(BootStrapUtil.castExpress((Express)o));
            }
            Map data=new HashMap();
            data.put("status0",status0);
            data.put("status1",status1);

            return WebUtil.toJson(new Message(0, "查询成功", data));
        }
        return WebUtil.toJson(new Message(-1, "查询失败"));
    }

    /**
     * 快递录入
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request,HttpServletResponse response){
        Express express = WebUtil.toBean(request.getParameterMap(), new Express());
        express.setSysPhone(LoginUtil.getLoginUserPhone());
        if(ExpressService.insert(express)){
            return WebUtil.toJson(new Message(0,"录入成功"));
        }
        return WebUtil.toJson(new Message(1,"录入失败"));
    }

    @ResponseBody("/express/takeByCode.do")
    public String takeByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        if (ExpressService.updateStatus(code)){
            LoginUtil.removeNumber();
            return WebUtil.toJson(new Message(0,"取件成功"));
        }
        return WebUtil.toJson(new Message(-1,"取件失败"));
    }

    @ResponseBody("/express/findByCode.do")
    public String findByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        Express express=null;
        if ((express=ExpressService.findByCode(code))!=null){
            return WebUtil.toJson(new Message(0,"查询成功", BootStrapUtil.castExpress(express)));
        }
        return WebUtil.toJson(new Message(-1,"查询失败"));
    }

    @ResponseView("/express/setNumber.do")
    public String findByNumber(HttpServletRequest request,HttpServletResponse response){
        String number = request.getParameter("number");
        Express express=null;
        if ((express=ExpressService.findByNumber(number))!=null){
            LoginUtil.setNumber(number);
        }
        return "/pickExpress.html";
    }

    @ResponseBody("/express/getNumber.do")
    public String getNumber(HttpServletRequest request,HttpServletResponse response){
        String number=null;
        if ((number=LoginUtil.getNumber())!=null){
            LoginUtil.removeNumber();
            return WebUtil.toJson(new Message(0,"查询成功",number));
        }
        return WebUtil.toJson(new Message(-1,"查询失败"));
    }


    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String number = request.getParameter("number");
        Express express=null;
        if ((express=ExpressService.findByNumber(number))!=null){
            return WebUtil.toJson(new Message(0,"查询成功",BootStrapUtil.castExpress(express)));
        }
        return WebUtil.toJson(new Message(-1,"查询失败"));
    }


    @ResponseBody("/express/findBySysPhone.do")
    public String findBySysPhone(HttpServletRequest request,HttpServletResponse response){
        String sysPhone = request.getParameter("sysPhone");
        List<Express> expresses=null;
        if ((expresses=ExpressService.findBySysPhone(sysPhone))!=null){
            List<BootStrapTableExpress> expressList=new ArrayList<>();
            for (Express express : expresses) {
                expressList.add(BootStrapUtil.castExpress(express));
            }
            return WebUtil.toJson(new Message(0,"查询成功",expressList));
        }
        return WebUtil.toJson(new Message(-1,"查询失败"));
    }
}
