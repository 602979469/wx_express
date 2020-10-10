package com.kaikeba.test;

import com.kaikeba.dao.BaseAdminDao;
import com.kaikeba.dao.imp.AdminDaoMysql;
import com.kaikeba.service.CourierService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AdminDaoMysql Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>9�� 30, 2020</pre>
 */
public class AdminDaoMysqlTest {

    BaseAdminDao dao=new AdminDaoMysql();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: updateLoginTime(String username, Date date, String ip)
     */
    @Test
    public void testUpdateLoginTime() throws Exception {
        dao.updateLoginTime("root","192.168.1.5");
    }

    /**
     * Method: login(String username, String password)
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println(CourierService.login("15858298809", "123456"));
    }
} 
