package com.kaikeba.service;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
import com.kaikeba.dao.imp.CourierDaoMysql;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class CourierService {

    private static final BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 控制台
     *
     * @return {@link List<Map<String,Integer>>}
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 找到所有
     *
     * @param limit      限制
     * @param offset     抵消
     * @param pageNumber 页码
     * @return {@link List< Courier >}
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }


    /**
     * 找到通过用户电话
     *
     * @param userPhone 用户电话
     * @return {@link Courier}
     */
    public static Courier findBySysPhone(String userPhone) {
        return dao.findBySysPhone(userPhone);
    }


    /**
     * 插入
     *
     * @param courier 快递
     * @return boolean
     */
    public static boolean insert(Courier courier) {
        return dao.insert(courier);
    }


    /**
     * 删除
     *
     * @param id ID编号
     * @return boolean
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }


    /**
     * 更新
     *
     * @param newCourier 新的快递员信息
     * @return boolean
     * <p>
     */
    public static boolean update(Courier newCourier) {
        Courier oldCourier = dao.findById(newCourier.getId());
        if (!newCourier.getSysPhone().equals(oldCourier.getSysPhone())) {
            ExpressService.updateSysPhone(newCourier.getSysPhone(), oldCourier.getSysPhone());
        }
        return dao.update(newCourier);
    }

    /**
     * 登录
     *
     * @param sysPhone 系统的手机
     * @param password 密码
     * @return {@link Courier}
     */
    public static Courier login(String sysPhone, String password) {
        Courier courier = dao.findBySysPhone(sysPhone);
        if (courier.getPassword().equals(password)){
            return courier;
        }
        return null;
    }

    /**
     * 更新登录时间
     *
     * @param sysPhone 系统的手机
     * @return boolean
     */
    public static boolean updateLoginTime(String sysPhone){
        return dao.updateLoginTime(sysPhone);
    }
}
