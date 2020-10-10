package com.kaikeba.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-10-01 01:01:28
 */
public class User implements Serializable {

    private static final long serialVersionUID = -81372407451374454L;

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
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    private Timestamp registerTime;

    /**
     * 登录时间
     */
    private Timestamp loginTime;

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

    public User() {
    }

    public User(Integer id, String username, String userPhone, String idCardNumber, String password, Timestamp registerTime, Timestamp loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.idCardNumber = idCardNumber;
        this.password = password;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", loginTime=" + loginTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(userPhone, user.userPhone) &&
                Objects.equals(idCardNumber, user.idCardNumber) &&
                Objects.equals(password, user.password) &&
                Objects.equals(registerTime, user.registerTime) &&
                Objects.equals(loginTime, user.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, idCardNumber, password, registerTime, loginTime);
    }
}