package com.kaikeba.dao;

import com.kaikeba.bean.Express;

import java.util.List;
import java.util.Map;

/**
 * 基地快递刀
 *
 * @author Faker
 * @date 2020/09/30
 */
public interface BaseExpressDao extends BaseCrud<Express> {

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    Express findByNumber(String number);


    /**
     * 用于查询所有快递
     *
     * @return 快递的集合
     */
    List<Express> findAll();

    /**
     * 懒人排行
     * @return
     */
    List<Map<String, Object>> lazyBoard(int status);
    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    Express findByCode(String code);

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findByUserPhone(String userPhone);

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean updateStatus(String code);

    /**
     * 更新录入人电话
     *
     * @param oldSysPhone 旧系统电话
     * @param newSysPhone 新系统手机
     * @return boolean
     */
    boolean updateSysPhone(String newSysPhone, String oldSysPhone);


    /**
     * 更新用户电话
     *
     * @param oldUserPhone 老用户电话
     * @param newUserPhone 新用户电话
     * @return boolean
     */
    boolean updateUserPhone(String newUserPhone, String oldUserPhone);

}