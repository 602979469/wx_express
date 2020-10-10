package com.kaikeba.dao.imp;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.util.JdbcUtil;

/**
 * 管理刀mysql
 *
 * @author Faker
 * @date 2020/09/30
 */
public class AdminDaoMysql implements BaseAdminDao {
    /**
     * 更新登录时间
     */
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE ADMIN SET LOGIN_TIME=NOW(),LOGIN_IP=? WHERE USERNAME=?";
    /**
     * 登录
     */
    private static final String SQL_LOGIN = "SELECT ID FROM ADMIN WHERE USERNAME=? AND PASSWORD=?";

    /**
     * 更新登录时间
     * 根据用户名，更新登陆时间和登录ip
     *
     * @param username 用户名
     * @param ip       知识产权
     */
    @Override
    public void updateLoginTime(String username,String ip) {
        JdbcUtil.update(SQL_UPDATE_LOGIN_TIME,ip,username);
    }

    /**
     * 管理员根据账号密码登陆
     *
     * @param username 账号
     * @param password 密码
     * @return 登陆的结果， true表示登陆成功
     */
    @Override
    public boolean login(String username, String password) {
        return JdbcUtil.queryForMap(SQL_LOGIN,username,password)!=null;
    }
}
