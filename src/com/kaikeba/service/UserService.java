package com.kaikeba.service;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.dao.imp.UserDaoMysql;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class UserService {

    private static final BaseUserDao dao = new UserDaoMysql();

    /**
     * 控制台
     *
     * @return {@link List<Map<String, Integer>>}
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
     * @return {@link List<User>}
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }


    /**
     * 找到通过用户电话
     *
     * @param userPhone 用户电话
     * @return {@link User}
     */
    public static User findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }


    /**
     * 插入
     *
     * @param user 用户
     * @return boolean
     */
    public static boolean insert(User user) {
        return dao.insert(user);
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
     * @param newUser 新用户
     * @return boolean
     */
    public static boolean update(User newUser) {

        User oldUser = dao.findById(newUser.getId());
        if (!oldUser.getUserPhone().equals(newUser.getUserPhone())) {
            ExpressService.updateUserPhone(newUser.getUserPhone(),oldUser.getUserPhone());
        }
        return dao.update(newUser);
    }

    /**
     * 更新登录时间
     * @param userPhone
     * @return
     */
    public static boolean updateLoginTime(String userPhone){
        return dao.updateLoginTime(userPhone);
    }
}
