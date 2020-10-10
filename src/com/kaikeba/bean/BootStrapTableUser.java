package com.kaikeba.bean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class BootStrapTableUser {

    /**
     * ID编号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * ID编号卡数量
     */
    private String idCardNumber;
    /**
     * 密码
     */
    private String password;

    /**
     * 快递数量
     */
    private Integer expressNumber;
    /**
     * 寄存器时间
     */
    private String registerTime;
    /**
     * 登录时间
     */
    private String loginTime;

    public BootStrapTableUser() {
    }
    public BootStrapTableUser(Integer id, String username, String userPhone, String idCardNumber, String password, Integer expressNumber, String registerTime, String loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idCardNumber = idCardNumber;
        this.password = password;
        this.expressNumber = expressNumber;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
    }

    public Integer getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(Integer expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
