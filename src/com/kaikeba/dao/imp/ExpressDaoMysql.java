package com.kaikeba.dao.imp;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.util.JdbcUtil;

import java.util.List;
import java.util.Map;

/**
 * 快递刀mysql
 *
 * @author Faker
 * @date 2020/09/30
 */
public class ExpressDaoMysql implements BaseExpressDao {

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     */
    public static final String SQL_CONSOLE = "SELECT COUNT(ID) totalExpress,COUNT(TO_DAYS(IN_TIME)=TO_DAYS(NOW()) OR NULL) dayExpress,COUNT(STATUS=0 OR NULL) needTakeExpress,COUNT(TO_DAYS(IN_TIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) insertExpress FROM EXPRESS";

    /**
     * 懒人排行，按照总的快递数量排行
     */
    public static final String SQL_LAZY_BOARD_TOTAL="SELECT username,COUNT(*) counts FROM EXPRESS GROUP BY USER_PHONE ORDER BY COUNTS DESC";

    /**
     * 懒人排行，按照今年的快递数量进行排行
     */
    public static final String SQL_LAZY_BOARD_YEAR="SELECT username,COUNT(*) counts FROM EXPRESS WHERE YEAR(IN_TIME)=YEAR(NOW()) GROUP BY USER_PHONE ORDER BY COUNTS DESC";

    /**
     * 懒人排行，按照本月的快递数量进行排行
     */
    public static final String SQL_LAZY_BOARD_MONTH="SELECT username,COUNT(*) counts FROM EXPRESS WHERE MONTH(IN_TIME)=MONTH(NOW()) GROUP BY USER_PHONE ORDER BY COUNTS DESC";

    /**
     * 用于查询数据库中的所有快递信息
     */
    public static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    /**
     * 用于分页查询数据库中的快递信息
     */
    public static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    /**
     * 通过取件码查询快递信息
     */
    public static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE=?";
    /**
     * 通过ID查询快递信息
     */
    public static final String SQL_FIND_BY_ID = "SELECT * FROM EXPRESS WHERE ID=?";
    /**
     * 通过快递单号查询快递信息
     */
    public static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER=?";
    /**
     * 通过录入人手机号查询快递信息
     */
    public static final String SQL_FIND_BY_SYS_PHONE = "SELECT * FROM EXPRESS WHERE SYS_PHONE=?";
    /**
     * 通过用户手机号查询用户所有快递
     */
    public static final String SQL_FIND_BY_USER_PHONE = "SELECT * FROM EXPRESS WHERE USER_PHONE=?";
    /**
     * 录入快递
     */
    public static final String SQL_INSERT = "INSERT INTO EXPRESS (NUMBER,USERNAME,USER_PHONE,COMPANY,CODE,IN_TIME,STATUS,SYS_PHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    /**
     * 快递修改
     */
    public static final String SQL_UPDATE = "UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    /**
     * 快递的状态码改变（取件）
     */
    public static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1,IN_TIME=IN_TIME,OUT_TIME=NOW(),CODE=NULL WHERE CODE=?";

    /**
     * 更新录入人电话
     */
    public static final String SQL_UPDATE_SYS_PHONE = "UPDATE EXPRESS SET USER_PHONE=? WHERE USER_PHONE=?";

    /**
     * 更新更新用户电话
     */
    public static final String SQL_UPDATE_USER_PHONE = "UPDATE EXPRESS SET SYS_PHONE=? WHERE SYS_PHONE=?";


    /**
     * 快递的删除
     */
    public static final String SQL_DELETE = "DELETE FROM EXPRESS WHERE ID=?";


    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        return JdbcUtil.queryForMap(SQL_CONSOLE, Integer.class);
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        if (limit) {
            return JdbcUtil.queryForList(SQL_FIND_LIMIT, Express.class, offset, pageNumber);
        } else {
            return JdbcUtil.queryForList(SQL_FIND_ALL, Express.class);
        }
    }

    public List<Express> findAll(){
        return findAll(false,0,0);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    @Override
    public Express findByNumber(String number) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_NUMBER, Express.class, number);
    }

    @Override
    public List<Map<String, Object>> lazyBoard(int staus) {
        if (staus==2){
            return  JdbcUtil.queryForMap(SQL_LAZY_BOARD_TOTAL);
        }
        else if (staus==1){
            return  JdbcUtil.queryForMap(SQL_LAZY_BOARD_YEAR);
        }
        else if (staus==0){
            return  JdbcUtil.queryForMap(SQL_LAZY_BOARD_MONTH);
        }
        else{
            return null;
        }

    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_CODE, Express.class, code);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        return JdbcUtil.queryForList(SQL_FIND_BY_USER_PHONE, Express.class, userPhone);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        return JdbcUtil.queryForList(SQL_FIND_BY_SYS_PHONE, Express.class, sysPhone);
    }

    /**
     * 插入
     * 快递的录入
     * INSERT INTO EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(Express e) {
        return JdbcUtil.update(SQL_INSERT,
                e.getNumber(), e.getUsername(),
                e.getUserPhone(), e.getCompany(),
                e.getCode(), e.getSysPhone()) > 0;
    }

    @Override
    public Express findById(Integer id) {
        return JdbcUtil.queryForObject(SQL_FIND_BY_ID, Express.class, id);
    }


    /**
     * 更新
     * 快递的修改
     * UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?
     *
     * @param e e
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean update(Express e) {
        return JdbcUtil.update(SQL_UPDATE, e.getNumber(),
                e.getUsername(), e.getCompany(),
                e.getStatus(), e.getId()) > 0;
    }


    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递单号
     * @return 修改的结果，true表示成功，false表示失败
     */
    @Override
    public boolean updateStatus(String code) {
        return JdbcUtil.update(SQL_UPDATE_STATUS, code) > 0;
    }

    /**
     * 更新电话系统
     * 更新录入人电话
     *
     * @param oldSysPhone 旧的录入人电话
     * @param newSysPhone 新的录入人手机
     * @return boolean
     */
    @Override
    public boolean updateSysPhone(String newSysPhone, String oldSysPhone) {
        return JdbcUtil.update(SQL_UPDATE_SYS_PHONE,newSysPhone,oldSysPhone)>0;
    }

    /**
     * 更新用户电话
     *
     * @param oldUserPhone 老用户电话
     * @param newUserPhone 新用户电话
     * @return boolean
     */
    @Override
    public boolean updateUserPhone(String newUserPhone, String oldUserPhone) {
        return JdbcUtil.update(SQL_UPDATE_USER_PHONE,newUserPhone,oldUserPhone)>0;
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    @Override
    public boolean delete(int id) {
        return JdbcUtil.update(SQL_DELETE, id) > 0;
    }
}