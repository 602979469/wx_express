package com.kaikeba.service;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.dao.imp.AdminDaoMysql;

/**
 * 管理员服务
 *
 * @author Faker
 * @date 2020/09/30
 */
public class AdminService {

    private final static BaseAdminDao dao = new AdminDaoMysql();

    /**
     * 更新登录时间和ip
     * 更新登陆时间与ip
     *
     * @param username 用户名
     * @param ip       ip
     */
    public static void updateLoginTimeAndIp(String username, String ip){
        dao.updateLoginTime(username,ip);
    }

    /**
     * 登录
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return true表示成功，false表示登陆失败
     */
    public static boolean login(String username,String password){
        return dao.login(username,password);
    }
}
