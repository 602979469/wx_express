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

    private final static Random r = new Random();

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
}
