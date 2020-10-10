package com.kaikeba.controller;

import com.kaikeba.bean.BootStrapTableExpress;
import com.kaikeba.bean.Express;
import com.kaikeba.bean.Message;
import com.kaikeba.bean.ResultData;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.ExpressService;
import com.kaikeba.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 快递控制器
 *
 * @author Faker
 * @date 2020/09/30
 */
public class ExpressController {

    @ResponseBody("/express/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = ExpressService.console();
        Message msg = new Message();
        msg.setStatus(data.size() == 0?-1:0);
        msg.setData(data);
        return WebUtil.toJson(msg);
    }


    @ResponseBody("/express/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response){
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        List<Express> list = ExpressService.findAll(true, offset, pageNumber);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            String inTime = WebUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库": WebUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        List<Map<String, Integer>> console = ExpressService.console();
        Integer total = console.get(0).get("totalExpress");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return WebUtil.toJson(data);
    }

    @ResponseBody("/express/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response){
        Express e= WebUtil.toBean(request.getParameterMap(),new Express());
        e.setSysPhone( WebUtil.getSysPhone());
        boolean flag = ExpressService.insert(e);
        Message msg = new Message();
        if(flag){
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递录入成功!");
        }else{
            //录入失败
            msg.setStatus(-1);
            msg.setResult("快递录入失败!");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/express/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String number = request.getParameter("number");
        Express e = ExpressService.findByNumber(number);
        Message msg = new Message();
        if(e == null){
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e);
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/express/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        Express express = WebUtil.toBean(request.getParameterMap(), new Express());
        boolean flag = ExpressService.update(express);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/express/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = ExpressService.delete(id);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        return WebUtil.toJson(msg);
    }
}
