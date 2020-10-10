package com.kaikeba.dao;


/**
 * 基地管理刀
 * 用于定义eadmin表格的操作规范
 *
 * @author Faker
 * @date 2020/10/01
 */
public interface BaseAdminDao {

    /**
     * 更新登录时间
     * 根据用户名，更新登陆时间和登录ip
     *
     * @param username 用户名
     * @param ip       知识产权
     */
    void updateLoginTime(String username, String ip);

    /**
     * 管理员根据账号密码登陆
     * @param username 账号
     * @param password 密码
     * @return 登陆的结果， true表示登陆成功
     */
    boolean login(String username,String password);
}
