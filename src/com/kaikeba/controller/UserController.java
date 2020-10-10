package com.kaikeba.controller;

import com.kaikeba.bean.*;
import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.service.ExpressService;
import com.kaikeba.service.UserService;
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
public class UserController {

    @ResponseBody("/user/console.do")
    public String console(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Integer>> data = UserService.console();
        Message msg = new Message();
        msg.setStatus(data.size() == 0 ? -1 : 0);
        msg.setData(data);
        return WebUtil.toJson(msg);
    }


    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        List<User> list = UserService.findAll(true, offset, pageNumber);
        List<BootStrapTableUser> list2 = new ArrayList<>();
        for (User user : list) {
            List<Express> byUserPhone = ExpressService.findByUserPhone(user.getUserPhone());
            int expressNumber=byUserPhone==null?0:byUserPhone.size();
            BootStrapTableUser user2 = new BootStrapTableUser(user.getId(), user.getUsername(),
                    user.getUserPhone(),user.getIdCardNumber(), user.getPassword(),expressNumber,
                    WebUtil.format(user.getRegisterTime()), WebUtil.format(user.getLoginTime()));
            list2.add(user2);
        }
        List<Map<String, Integer>> console = UserService.console();
        Integer total = console.get(0).get("totalUser");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        return WebUtil.toJson(data);
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        User user = WebUtil.toBean(request.getParameterMap(), new User());
        boolean flag = UserService.insert(user);
        Message msg = new Message();
        if (flag) {
            //录入成功
            msg.setStatus(0);
            msg.setResult("用户添加成功!");
        } else {
            //录入失败
            msg.setStatus(-1);
            msg.setResult("用户添加失败!");
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        User user = UserService.findByUserPhone(userPhone);
        Message msg = new Message();
        if (user == null) {
            msg.setStatus(-1);
            msg.setResult("用户不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(user);
        }
        return WebUtil.toJson(msg);
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        User user = WebUtil.toBean(request.getParameterMap(), new User());
        boolean flag = UserService.update(user);
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

    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        int id = WebUtil.parseInt(request.getParameter("id"), -1);
        boolean flag = UserService.delete(id);
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
