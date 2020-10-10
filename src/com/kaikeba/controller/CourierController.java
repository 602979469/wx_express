package com.kaikeba.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.CourierService;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class CourierController {

    @ResponseBody("/courier/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Integer>> data = CourierService.console();
        Message msg = new Message();
        msg.setStatus(data.size() == 0 ? -1 : 0);
        msg.setData(data);
        return WebUtil.toJson(msg);
    }


    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        //1.    获取查询数据的起始索引值
        int offset = WebUtil.parseInt(request.getParameter("offset"), 1);
        //2.    获取当前页要查询的数据量
        int pageNumber = WebUtil.parseInt(request.getParameter("pageNumber"),ResultData.DEFAULT_PAGE_SIZE);
        //3.    进行查询
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        //封装数据
        for (Courier courier : list) {
            //封装派送量
            List<Express> bySysPhone = ExpressService.findBySysPhone(courier.getSysPhone());
            Integer sendNumber=bySysPhone==null?0:bySysPhone.size();
            BootStrapTableCourier courier1 = new BootStrapTableCourier(courier.getId(), courier.getName(),
                    courier.getSysPhone(), courier.getIdCardNumber(), courier.getPassword(), sendNumber,
                    WebUtil.format(courier.getRegisterTime()), WebUtil.format(courier.getLoginTime()));
            list2.add(courier1);
        }
        //获取所有对象
        Integer total = CourierService.console().get(0).get("totalCourier");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return WebUtil.toJson(data);
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        Courier courier = WebUtil.toBean(request.getParameterMap(), new Courier());
        boolean flag = CourierService.insert(courier);
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);
            msg.setResult("添加快递员成功!");
        } else {
            //录入失败
            msg.setStatus(-1);
            msg.setResult("添加快递员失败!");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        //封装参数为对象
        String sysPhone = request.getParameter("sysPhone");
        Courier courier = CourierService.findBySysPhone(sysPhone);
        Message msg = new Message();
        if (courier == null) {
            msg.setStatus(-1);
            msg.setResult("快递员信息不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(courier);
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        //封装参数为对象
        Courier courier = WebUtil.toBean(request.getParameterMap(), new Courier());
        boolean flag = CourierService.update(courier);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("修改成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        //获取参数
        int id = WebUtil.parseInt(request.getParameter("id"), -1);
        boolean flag = CourierService.delete(id);
        Message msg = new Message();
        if (flag) {
            msg.setStatus(0);
            msg.setResult("删除成功");
        } else {
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        return WebUtil.toJson(msg);
    }
}
