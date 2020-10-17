package com.kaikeba.test;

import com.kaikeba.bean.Express;
import com.kaikeba.bean.User;
import com.kaikeba.dao.BaseExpressDao;
import com.kaikeba.dao.imp.ExpressDaoMysql;
import com.kaikeba.service.UserService;
import com.kaikeba.util.JdbcUtil;
import com.kaikeba.util.WebUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ExpressDaoMysql Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>9�� 30, 2020</pre>
 */
public class ExpressDaoMysqlTest {

    BaseExpressDao dao = new ExpressDaoMysql();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: console()
     */
    @Test
    public void testConsole() throws Exception {
        List<Map<String, Integer>> console = dao.console();
        for (Map<String, Integer> stringIntegerMap : console) {
            System.out.println(stringIntegerMap);

        }
    }

    /**
     * Method: findAll(boolean limit, int offset, int pageNumber)
     */
    @Test
    public void testFindAll() throws Exception {
        //System.out.println(dao.findAll(true, 0, 10));
        List<Express> all = dao.findAll(false, 0, 10);
        for (Express express : all) {
            System.out.println(express);
        }

    }

    /**
     * Method: findByNumber(String number)
     */
    @Test
    public void testFindByNumber() throws Exception {
        System.out.println(dao.findByNumber("123"));
    }

    /**
     * Method: findByCode(String code)
     */
    @Test
    public void testFindByCode() throws Exception {
        System.out.println(dao.findByCode("123456"));
    }

    /**
     * Method: findByUserPhone(String userPhone)
     */
    @Test
    public void testFindByUserPhone() throws Exception {
        List<Express> byUserPhone = dao.findByUserPhone("13843838438");
        for (Express express : byUserPhone) {
            System.out.println(express);
        }
    }

    /**
     * Method: findBySysPhone(String sysPhone)
     */
    @Test
    public void testFindBySysPhone() throws Exception {
        List<Express> bySysPhone = dao.findBySysPhone("15858298809");
        for (Express express : bySysPhone) {
            System.out.println(express);
        }
    }

    /**
     * Method: insert(Express e)
     */
    @Test
    public void testInsert() throws Exception {
        JdbcUtil.setCommit(false);
        Express express = new Express();
        express.setNumber("1234");
        System.out.println(dao.insert(express));
    }

    /**
     * Method: update(int id, Express newExpress)
     */
    @Test
    public void testUpdate() throws Exception {
        Express express = dao.findByNumber("123");
        express.setCompany(express.getCompany() + 1);
        System.out.println(express.getId());
        dao.update(express);
        System.out.println(dao.findByNumber("123"));
    }

    /**
     * Method: updateStatus(String code)
     */
    @Test
    public void testUpdateStatus() throws Exception {
        System.out.println(dao.updateStatus("136796"));
    }

    /**
     * Method: delete(int id)
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println(dao.delete(1));
    }

    @Test
    public void testUpdateSysPhone() throws Exception {
        System.out.println(dao.updateSysPhone("1", "123"));
    }

    @Test
    public void testUpdateUserPhone() throws Exception {
        System.out.println(dao.updateUserPhone("1", "123"));
    }

    /**
     * NUMBER,
     * CODE,
     *
     * @throws SQLException sqlexception异常
     */

    @Test
    public void insertExpress() throws SQLException {
        String[] username = {"铠", "橘右京", "项羽", "白起", "赵云", "李白", "韩信", "刘备", "鲁班七号",
                "墨子", "孙膑", "周瑜", "廉颇", "程咬金", "典韦", "后羿", "扁鹊", "李元芳",
                "张飞", "刘禅", "兰陵王", "达摩", "曹操", "钟馗", "高渐离", "宫本武藏", "吕布", "嬴政",
                "姜子牙", "老夫子", "狄仁杰",
                "夏侯惇", "关羽", "哪吒", "太乙真人", "干将莫邪", "牛魔", "百里守约",
                "百里玄策", "苏烈", "黄忠", "诸葛亮", "东皇太一", "杨戬", "后羿", "孙悟空", "张良", "刘邦", "达摩"
                , "露娜", "妲己", "甄姬", "虞姬", "大乔", "小乔", "王昭君", "貂蝉", "芈月", "阿珂", "花木兰",
                "武则天", "不知火舞", "孙尚香", "蔡文姬", "安琪拉", "钟无艳", "女蜗", "雅典娜"};
        Random random = new Random();
        for (int j = 0; j < 2000; j++) {
            int i =random.nextInt(username.length);
            Express express = new Express();
            express.setUsername(username[i]);
            express.setCompany("京东速运");
            express.setSysPhone("15858298809");
            if (i < 10) {
                express.setUserPhone("1380491588" + i);
            } else {
                express.setUserPhone("138049158" + i);
            }
            express.setCode(WebUtil.getCode());
            express.setNumber(WebUtil.getCode() + i);
            dao.insert(express);
        }
    }


    /**
     * username
     * userPhone
     * idCard
     * password
     */
    @Test
    public void insertUser() {
        String[] username = {"铠", "橘右京", "项羽", "白起", "赵云", "李白", "韩信", "刘备", "鲁班七号",
                "墨子", "孙膑", "周瑜", "廉颇", "程咬金", "典韦", "后羿", "扁鹊", "李元芳",
                "张飞", "刘禅", "兰陵王", "达摩", "曹操", "钟馗", "高渐离", "宫本武藏", "吕布", "嬴政",
                "姜子牙", "老夫子", "狄仁杰",
                "夏侯惇", "关羽", "哪吒", "太乙真人", "干将莫邪", "牛魔", "百里守约",
                "百里玄策", "苏烈", "黄忠", "诸葛亮", "东皇太一", "杨戬", "后羿", "孙悟空", "张良", "刘邦", "达摩"
                , "露娜", "妲己", "甄姬", "虞姬", "大乔", "小乔", "王昭君", "貂蝉", "芈月", "阿珂", "花木兰",
                "武则天", "不知火舞", "孙尚香", "蔡文姬", "安琪拉", "钟无艳", "女蜗", "雅典娜"};
        User user = new User();
        user.setPassword("123456");
        for (int i = 0; i < username.length; i++) {
            user.setUsername(username[i]);
            if (i < 10) {
                user.setIdCardNumber("53010219900307179" + i);
            } else {
                user.setIdCardNumber("5301021990030717" + i);
            }
            String sql = "select user_phone from express where username=?";
            user.setUserPhone(JdbcUtil.queryForObject(sql, String.class, username[i]));
            // user.setUserPhone();
            UserService.insert(user);
        }

    }

} 
