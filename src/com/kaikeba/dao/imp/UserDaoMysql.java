package com.kaikeba.dao.imp;

import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseUserDao;
import com.kaikeba.util.JdbcUtil;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class UserDaoMysql implements BaseUserDao {

    /**
     * 用于查询数据库中的全部用户人数和日注册量
     */
    public static final String SQL_CONSOLE = "SELECT COUNT(*) totalUser,COUNT(TO_DAYS(REGISTER_TIME)=TO_DAYS(NOW()) OR NULL) dayUser FROM USER";
    /**
     * 用于查询数据库中的所有快递信息
     */
    public static final String SQL_FIND_ALL = "SELECT * FROM USER";
    /**
     * 用于分页查询数据库中的快递信息
     */
    public static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";
    /**
     * 通过ID查询快递信息
     */
    public static final String SQL_FIND_BY_ID = "SELECT * FROM USER WHERE ID=?";
    /**
     * 通过录入人手机号查询快递信息
     */
    public static final String SQL_FIND_BY_USER_PHONE = "SELECT * FROM USER WHERE USER_PHONE=?";
    /**
     * 录入快递
     */
    public static final String SQL_INSERT = "INSERT INTO USER VALUES (NULL,?,?,?,?,NOW(),NULL)";
    ;
    /**
     * 快递修改
     */
    public static final String SQL_UPDATE = "UPDATE USER SET USERNAME=?,ID_CARD_NUMBER=?,PASSWORD=?,USER_PHONE=? WHERE ID=?";
    /**
     * 快递的删除
     */
    public static final String SQL_DELETE = "DELETE FROM USER WHERE ID=?";

    /**
     * 控制台
     *
     * @return {@link List<Map<String, Integer>>}
     */
    @Override
    public List<Map<String, Integer>> console() {
        return JdbcUtil.queryForMap(SQL_CONSOLE, Integer.class);
    }

    /**
     * 找到所有
     *
     * @param limit      限制
     * @param offset     抵消
     * @param pageNumber 页码
     * @return {@link List<User>}
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        if (limit) {
            return JdbcUtil.queryForList(SQL_FIND_LIMIT, User.class, offset, pageNumber);
        } else {
            return JdbcUtil.queryForList(SQL_FIND_ALL, User.class);
        }
    }

    /**
     * 找到通过用户电话
     *
     * @param userPhone 用户电话
     * @return {@link User}
     */
    @Override
    public User findByUserPhone(String userPhone) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_USER_PHONE, User.class, userPhone);
    }

    /**
     * 插入
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean insert(User user) {
        return JdbcUtil.update(SQL_INSERT, user.getUsername(), user.getUserPhone(),
                user.getIdCardNumber(), user.getPassword()) > 0;
    }

    @Override
    public User findById(Integer id) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_ID, User.class, id);
    }

    /**
     * 删除
     *
     * @param id ID编号
     * @return boolean
     */
    @Override
    public boolean delete(int id) {
        return JdbcUtil.update(SQL_DELETE, id) > 0;
    }

    /**
     * 更新
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean update(User user) {
        return JdbcUtil.update(SQL_UPDATE, user.getUsername(),
                user.getIdCardNumber(), user.getPassword(), user.getUserPhone(), user.getId()) > 0;
    }
}
