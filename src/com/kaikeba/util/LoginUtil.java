package com.kaikeba.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.kaikeba.bean.Courier;
import com.kaikeba.bean.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class LoginUtil {

    /**
     * session
     */
    private static HttpSession session;

    /**
     * 登录用户的用户名
     */
    private static String loginUserPhone;

    /**
     * 是用户登录还是管理员登录，如果是用户登录则为true，否则为false
     */
    private static boolean flag = true;

    /**
     * 设置登录用户类型
     *
     * @param user
     */
    public static void setUser(Object user) {
        if (user == null) {
            session.removeAttribute("loginUser");
        } else if (user instanceof Courier) {
            flag = false;
        } else if (user instanceof User) {
            flag = true;
        } else {
            throw new ClassCastException("类型错误");
        }
        session.setAttribute("loginUser", user);
    }

    public static Object getUser() {
        if (flag) {
            return (User) session.getAttribute("loginUser");
        }
        return (Courier) session.getAttribute("loginUser");
    }

    /**
     * 设置短信的登录的用户名和验证码
     *
     * @param userPhone
     * @param code
     */
    public static void setLoginSMS(String userPhone, String code) {
        session.setAttribute(userPhone, code);
    }

    /**
     * 验证登录的用户名和验证码是否正确
     *
     * @param userPhone
     * @param code
     * @return
     */
    public static boolean checkLoginSMS(String userPhone, String code) {
        String SMSCode = null;
        if ((SMSCode = (String) session.getAttribute(userPhone)) != null) {
            return SMSCode.equals(code);
        }
        return false;
    }


    public static HttpSession getSession() {
        return session;
    }

    public static void setSession(HttpSession session) {
        LoginUtil.session = session;
    }

    public static String getLoginUserPhone() {
        return loginUserPhone;
    }

    public static void setLoginUserPhone(String loginUserPhone) {
        LoginUtil.loginUserPhone = loginUserPhone;
    }

    /**
     * 发送登录短信
     * <p>
     * [快递e站]您的验证码：${code},您正在进行身份验证，打死不告诉别人。
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    public static boolean sendLoginMessage(String phoneNumber, String code) {
        return sendTemplate(phoneNumber, code, "SMS_204275322");
    }

    /**
     * 发送取件码
     * <p>
     * [快递e站]您的取件码：${code}，打死不告诉别人。
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    public static boolean sendTakeCodeMessage(String phoneNumber, String code) {
        return sendTemplate(phoneNumber, code, "SMS_204111140");
    }

    /**
     * 发送更换手机的验证码
     *
     * 验证码为：${code}，您正在尝试变更重要信息，请妥善保管账户信息。
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    public static boolean sendUpdateUserPhone(String phoneNumber,String code){
        return sendTemplate(phoneNumber,code,"SMS_204440374");
    }

    /**
     * 短信模板，阿里云短信
     *
     * @param phoneNumber 电话号码
     * @param code        代码
     * @return boolean
     */
    private static boolean sendTemplate(String phoneNumber, String code, String template) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G1VYLEusvr8FeoAyT4Y", "2OyykFIyDjLDoM7hKhaGGbjyyhqgvs");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "快递e站");
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            String json = response.getData();
            Gson g = new Gson();
            HashMap result = g.fromJson(json, HashMap.class);
            if ("OK".equals(result.get("Message"))) {
                return true;
            } else {
                System.out.println("短信发送失败，原因：" + result.get("Message"));
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getQrCode() {
        return (String) session.getAttribute("qrCode");
    }

    public static void setQrCode(String qrCode) {
        session.setAttribute("qrCode", qrCode);
    }

    public static String getNumber() {
        return (String) session.getAttribute("number");
    }

    public static void setNumber(String number) {
        session.setAttribute("number", number);
    }

    public static void removeNumber() {
        session.removeAttribute("number");
    }
}
