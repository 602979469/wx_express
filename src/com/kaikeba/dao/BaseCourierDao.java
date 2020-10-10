package com.kaikeba.dao;

import com.kaikeba.bean.Courier;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public interface BaseCourierDao extends BaseCrud<Courier>{

    /**
     * 通过系统电话找到
     * 根据手机号码，查询他所有的快递员的信息
     *
     * @param sysPhone 系统的手机
     * @return 查询的快递信息列表
     */
    Courier findBySysPhone(String sysPhone);
    /**
     * 更新登录时间
     *
     * @param sysPhone 系统的手机
     * @return boolean
     */
    boolean updateLoginTime(String sysPhone);
}
