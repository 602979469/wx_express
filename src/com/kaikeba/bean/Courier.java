package com.kaikeba.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * (Courier)实体类
 *
 * @author makejava
 * @since 2020-10-01 01:01:27
 */
public class Courier implements Serializable {
    private static final long serialVersionUID = -35495722972110934L;

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 的名字
     */
    private String name;

    /**
     * 快递员的手机
     */
    private String sysPhone;

    /**
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录时间
     */
    private Timestamp registerTime;

    /**
     * 登录时间
     */
    private Timestamp loginTime;

    public Courier() {
    }

    public Courier(String name, String sysPhone, String idCardNumber, String password,Timestamp registerTime) {
        this.name = name;
        this.sysPhone = sysPhone;
        this.idCardNumber = idCardNumber;
        this.password = password;
        this.registerTime=registerTime;
    }

    public Courier(Integer id, String name, String sysPhone, String idCardNumber, String password,Timestamp registerTime, Timestamp loginTime) {
        this.id = id;
        this.name = name;
        this.sysPhone = sysPhone;
        this.idCardNumber = idCardNumber;
        this.password = password;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sysPhone='" + sysPhone + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", loginTime=" + loginTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysPhone() {
        return sysPhone;
    }

    public void setSysPhone(String sysPhone) {
        this.sysPhone = sysPhone;
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

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

}