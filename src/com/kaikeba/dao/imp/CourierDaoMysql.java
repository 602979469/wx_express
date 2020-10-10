package com.kaikeba.dao.imp;

import com.kaikeba.bean.Courier;
import com.kaikeba.dao.BaseCourierDao;
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
public class CourierDaoMysql implements BaseCourierDao {

    /**
     * 用于查询数据库中的全部用户人数和日注册量
     */
    public static final String SQL_CONSOLE = "SELECT COUNT(*) totalCourier,COUNT(TO_DAYS(REGISTER_TIME)=TO_DAYS(NOW()) OR NULL) dayCourier FROM COURIER";
    /**
     * 用于查询数据库中的所有快递信息
     */
    public static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    /**
     * 用于分页查询数据库中的快递信息
     */
    public static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    /**
     * 通过录入人手机号查询快递信息
     */
    public static final String SQL_FIND_BY_SYS_PHONE = "SELECT * FROM COURIER WHERE SYS_PHONE=?";
    /**
     * 通过ID查询快递信息
     */
    public static final String SQL_FIND_BY_ID = "SELECT * FROM COURIER WHERE ID=?";
    /**
     * 录入快递
     */
    public static final String SQL_INSERT = "INSERT INTO COURIER VALUES (NULL,?,?,?,?,NOW(),NULL)";
    ;
    /**
     * 快递修改
     */
    public static final String SQL_UPDATE = "UPDATE COURIER SET NAME=?,PASSWORD=?,SYS_PHONE=?,ID_CARD_NUMBER=? WHERE ID=?";
    /**
     * 更新最近登录时间
     */
    public static final String SQL_UPDATE_LOGIN_TIME="UPDATE COURIER SET LOGIN_TIME=NOW() WHERE SYS_PHONE=?";
    /**
     * 快递的删除
     */
    public static final String SQL_DELETE = "DELETE FROM COURIER WHERE ID=?";

    /**
     * 通过快递员电话找到
     *
     * @param sysPhone 系统的手机
     * @return {@link Courier}
     */
    @Override
    public Courier findBySysPhone(String sysPhone) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_SYS_PHONE, Courier.class,sysPhone);
    }

    @Override
    public boolean updateLoginTime(String sysPhone) {
        return JdbcUtil.update(SQL_UPDATE_LOGIN_TIME,sysPhone)>0;
    }

    /**
     * 快递员新增
     *
     * @param courier 快递
     * @return boolean
     */
    @Override
    public boolean insert(Courier courier) {
        return JdbcUtil.update(SQL_INSERT,courier.getName(),courier.getSysPhone(),
                courier.getIdCardNumber(),courier.getPassword())>0;
    }

    @Override
    public Courier findById(Integer id) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_ID, Courier.class,id);
    }

    /**
     * 控制台
     *
     * @return {@link List<Map<String, Integer>>}
     */
    @Override
    public List<Map<String, Integer>> console() {
        return JdbcUtil.queryForMap(SQL_CONSOLE,Integer.class);
    }

    /**
     * 查询所有快递员信息
     *
     * @param limit      是否分页
     * @param offset     开始
     * @param pageNumber 每页数据量
     * @return {@link List< Courier >}
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        if (limit){
            return JdbcUtil.queryForList(SQL_FIND_LIMIT, Courier.class,offset,pageNumber);
        }
        else{
            return JdbcUtil.queryForList(SQL_FIND_ALL, Courier.class);
        }
    }

    /**
     * 更新快递员数据
     *
     * @param courier 快递
     * @return boolean
     */
    @Override
    public boolean update(Courier courier) {
        return JdbcUtil.update(SQL_UPDATE,courier.getName(),courier.getPassword(),
                courier.getSysPhone(),courier.getIdCardNumber(),courier.getId())>0;
    }

    /**
     * 根据id编号删除
     *
     * @param id ID编号
     * @return boolean
     */
    @Override
    public boolean delete(int id) {
        return JdbcUtil.update(SQL_DELETE,id)>0;
    }
}
