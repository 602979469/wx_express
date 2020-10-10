package com.kaikeba.bean;


/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-10-01
 */
public class BootStrapTableCourier {

    private Integer id;
    private String name;
    private String sysPhone;
    private String idCardNumber;
    private String password;
    private Integer sendNumber;
    private String registerTime;
    private String loginTime;

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(Integer id, String name, String sysPhone, String idCardNumber, String password, Integer sendNumber, String registerTime, String loginTime) {
        this.id = id;
        this.name = name;
        this.sysPhone = sysPhone;
        this.idCardNumber = idCardNumber;
        this.password = password;
        this.sendNumber = sendNumber;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
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

    public Integer getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(Integer sendNumber) {
        this.sendNumber = sendNumber;
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
