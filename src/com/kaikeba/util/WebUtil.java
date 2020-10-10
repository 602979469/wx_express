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
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-09-29
 */
public class WebUtil {

    private static final Gson gson = new Gson();

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static HttpSession session;


    public static void setSession(HttpSession session) {
        WebUtil.session = session;
    }

    public static HttpSession getSession() {
        return session;
    }


    public static void setLoginSMS(String userPhone,String code){
        session.setAttribute(userPhone,code);
    }

    public static boolean getLoginSMS(String userPhone,String code){
        String attribute = (String) session.getAttribute(userPhone);
        return Objects.equals(attribute,code);
    }

    /**
     * 解析int
     *
     * @param param      参数
     * @param defaultInt 默认int
     * @return int
     */
    public static int parseInt(String param, Integer defaultInt) {
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return defaultInt;
        }
    }

    /**
     * 解析双
     *
     * @param param         参数
     * @param defaultDouble 默认double
     * @return double
     */
    public static double parseDouble(String param, Double defaultDouble) {
        try {
            return Double.parseDouble(param);
        } catch (Exception e) {
            return defaultDouble;
        }
    }

    /**
     * 转换为JavaBean对象
     *
     * @param parameterMap 参数映射
     * @param bean         豆
     * @return {@link T}
     */
    public static <T> T toBean(Map<String, String[]> parameterMap, T bean) {
        try {
            BeanUtils.populate(bean, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private final static Random r = new Random();


    /**
     * 获取6位数的验证码
     *
     * @return int
     */
    public static String getCode() {
        return r.nextInt(900000) + 100000 + "";
    }

    ;

    /**
     * 转换为json对象
     *
     * @param obj obj
     * @return {@link String}
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 日期格式转换格式
     *
     * @param date 日期
     * @return {@link String}
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return format.format(date);
    }

    public static boolean sendLoginMessage(String phoneNumber, String code){
        return sendTemplate(phoneNumber,code,"SMS_204275322");
    }

    public static boolean sendTakeCodeMessage(String phoneNumber, String code){
        return sendTemplate(phoneNumber,code,"SMS_204111140");
    }


    /**
     * 发送
     * [快递e站]您的验证码：${code},您正在进行身份验证，打死不告诉别人。
     *
     * @param phoneNumber 电话号码
     * @param code        代码
     * @return boolean
     */
    private static boolean sendTemplate(String phoneNumber, String code,String template) {
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
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
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

    /**
     * 阿里巴巴短信模板对象
     *
     * @author Faker
     * @date 2020/10/02
     */
    public static class AlibabaMessage {
        String area="陆家嘴18号快递e栈";
        String code;
        String company;
        String sysPhone;

        public AlibabaMessage(String code, String company, String sysPhone) {
            this.code = code;
            this.company = company;
            this.sysPhone = sysPhone;
        }
    }

    public static Courier getLoginUser(){
        return (Courier) session.getAttribute("adminUserName");
    }


    public static String getLoginUsername() {
        if (session.getAttribute("adminUserName") == null) {
            return null;
        }
        return ((Courier) (session.getAttribute("adminUserName"))).getName();
    }

    public static String getSysPhone() {
        if (session.getAttribute("adminUserName") == null) {
            return null;
        }
        return ((Courier) session.getAttribute("adminUserName")).getSysPhone();
    }

    public static void removeUsername() {
        setLoginUser(null);
    }

    public static void setLoginUser(Courier courier) {
        if (courier == null) {
            session.removeAttribute("adminUserName");
        } else {
            session.setAttribute("adminUserName", courier);
        }
    }
}
