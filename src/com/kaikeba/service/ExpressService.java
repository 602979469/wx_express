package com.kaikeba.service;

import com.kaikeba.bean.Express;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.dao.imp.ExpressDaoMysql;
import com.kaikeba.util.WebUtil;

import java.util.List;
import java.util.Map;

/**
 * 快递服务
 *
 * @author Faker
 * @date 2020/09/30
 */
public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param express 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public static boolean insert(Express express) {
        String code = null;
        //如果已经有了该取件码了，重新生成一次
        if (dao.findByCode((code = WebUtil.getCode())) != null) {
            return insert(express);
        }
        express.setCode(code);
        express.setSysPhone(WebUtil.getSysPhone());
        boolean flag = dao.insert(express);
        //录入成功,发送短信
        if (flag) {
            WebUtil.sendTakeCodeMessage(express.getUserPhone(),express.getCode());
        }
        return flag;
    }

    /**
     * 更新
     * 快递的修改
     *
     * @param newExpress 新的快递对象（number，company,username,userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     *
     */
    public static boolean update(Express newExpress) {
        //获取修改之前的信息
        Express oldExpress = dao.findById(newExpress.getId());
        //修改了手机号
        if (!oldExpress.getUserPhone().equals(newExpress.getUserPhone())){
            //TODO 原手机号发送一个验证码失效的通知，新手机号重新生成一个验证码
            ExpressService.updateUserPhone(newExpress.getUserPhone(), oldExpress.getUserPhone());
            return dao.update(newExpress);
        }
        //执行取件操作
        if (oldExpress.getStatus()==0 && newExpress.getStatus()==1){
            dao.updateStatus(oldExpress.getCode());
           return dao.update(newExpress);
        }
        //重新发送短信
        else if (oldExpress.getStatus()==1 && newExpress.getStatus()==0){
            dao.delete(oldExpress.getId());
            return dao.insert(newExpress);
        }
        //没有修改取件信息
        else {
            return dao.update(newExpress);
        }
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 更新电话系统
     *
     * @param newSysPhone 新系统手机
     * @param oldSysPhone 旧系统电话
     * @return boolean
     */
    public static boolean updateSysPhone(String newSysPhone,String oldSysPhone){
        return dao.updateSysPhone(newSysPhone,oldSysPhone );
    }


    /**
     * 更新用户电话
     *
     * @param newUserPhone 新用户电话
     * @param oldUserPhone 老用户电话
     * @return boolean
     */
    public static boolean updateUserPhone(String newUserPhone,String oldUserPhone){
        return dao.updateUserPhone(newUserPhone, oldUserPhone);
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
