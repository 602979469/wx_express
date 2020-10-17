package com.kaikeba.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description :
 * @author: Faker
 * @date : 2020-09-28
 */
public class JdbcUtil {

    /**
     * 自动提交
     */
    private static boolean commit = true;

    /**
     * 自动驼峰命名
     */
    private static boolean camelCase = true;

    /**
     * 设置提交
     *
     * @param commit 提交
     */
    public static void setCommit(boolean commit) {
        JdbcUtil.commit = commit;
    }


    /**
     * 设置自动驼峰命名
     *
     * @param camelCase 自动驼峰命名
     */
    public static void setCamelCase(boolean camelCase) {
        JdbcUtil.camelCase = camelCase;
    }

    /**
     * 获取连接
     *
     * @return {@link Connection}
     */
    private static Connection getConnection() {
        try {
            Connection connection = DruidUtil.getDataSource().getConnection();
            connection.setAutoCommit(commit);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("获取连接失败");
        }
    }

    /**
     * 更新
     * 更新方法只要返回影响的行数，返回值都是int
     *
     * @param sql     sql
     * @param objects 对象
     * @return int
     */
    public static int update(String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement statement = null;
        int i = -1;
        try {
            //填充SQL参数
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            int index = 1;
            for (Object object : objects) {
                statement.setObject(index++, object);
            }
            i = statement.executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return i;
        } finally {
            close(connection, statement);
        }
    }

    public static <T> List<Map<String, Object>> queryForMap(String sql, Object... objects) {
        return queryForMap(sql, Object.class, objects);
    }

    /**
     * 将查询结果封装为Map，如果有多条数据则返回一个元素为Map的List集合
     *
     * @param sql       sql
     * @param className 类名
     * @param objects   对象
     * @return {@link T}
     */
    public static <T> List<Map<String, T>> queryForMap(String sql, Class<T> className, Object... objects) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String methodName = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            int index = 1;
            //填充SQL参数
            for (Object object : objects) {
                statement.setObject(index++, object);
            }
            resultSet = statement.executeQuery();
            List<Map<String, T>> list = new ArrayList<>();
            //填充查询结果为Java对象
            while (resultSet.next()) {
                Map<String, T> map = handlerMapData(resultSet, className);
                list.add(map);
            }
            return list.isEmpty()?null:list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
    }

    /**
     * 查询为对象
     * 传入一个className，返回与给定类对象类型相同的对象
     * 目前只能处理包装数据类型，POJO类
     *
     * @param sql     sql
     * @param objects 对象
     * @return {@link T}
     */
    public static <T> T queryForObject(String sql, Class<T> className, Object... objects) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            int index = 1;
            //填充SQL参数
            for (Object object : objects) {
                statement.setObject(index++, object);
            }
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return handlerData(resultSet, className);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(connection, statement, resultSet);
        }
        //填充查询结果为Java对象

    }

    /**
     * 查询为列表
     * 传入一个className，返回与给定类对象类型相同的对象列表
     *
     * @param sql     sql
     * @param objects 对象
     * @return {@link List<T>}
     */
    public static <T> List<T> queryForList(String sql, Class<T> className, Object... objects) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String methodName = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            int index = 1;
            //填充SQL参数
            for (Object object : objects) {
                statement.setObject(index++, object);
            }
        }catch (SQLException e) {
            System.out.println("SQL参数异常!");
            close(connection, statement);
            e.printStackTrace();
            return null;
        }
        try {
            resultSet = statement.executeQuery();
            List<T> list = new ArrayList<>();
            //填充查询结果为Java对象
            while (resultSet.next()) {
                list.add(handlerData(resultSet, className));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, statement, resultSet);
        }
        return null;
    }


    /**
     * 处理程序数据
     * 这个方法是将结果集封装为对象，可能是封装一个pojo对象，也可能是封装一个Integer普通对象
     * 比如select count(*) from table,也是查询语句，也走ResultSet方法。
     *
     * @param resultSet 结果集
     * @return {@link T}
     */
    private static <T> T handlerData(ResultSet resultSet, Class<T> className) throws SQLException{
        T object = null;
        try {
            //如果是包装数据类型 java.lang下的
            if (className.getName().startsWith("java.lang")) {
                return defaultHandlerData(resultSet, className);
            }
            object = className.newInstance();
            fillInData(object, className, resultSet, false);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static <T> Map<String, T> handlerMapData(ResultSet resultSet, Class<T> className)throws SQLException {
        Map<String, T> object = null;
        try {
            object = new HashMap<>(16);
            fillInData(object, className, resultSet, true);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 填充数据
     *
     * @param object    对象
     * @param className 类名
     * @param resultSet 结果集
     * @param toMap     映射
     * @throws SQLException              sqlexception异常
     * @throws NoSuchMethodException     没有这样的方法异常
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException    非法访问异常
     */
    private static <T> void fillInData(Object object, Class<T> className, ResultSet resultSet, boolean toMap) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取一条查询记录的字段数
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i < columnCount + 1; i++) {
            //取出每一个字段的名字
            String columnName = metaData.getColumnName(i);
            StringBuffer sb = new StringBuffer();
            Class type = matchObject(metaData.getColumnTypeName(i));
            //格式化
            String baseMethodName=columnName;
            if (camelCase){
                for (int j = 0; j < columnName.length(); j++) {
                    if (columnName.charAt(j) == '_') {
                        j++;
                        sb.append(columnName.substring(j, j + 1).toUpperCase());
                    } else {
                        sb.append(columnName.substring(j, j + 1));
                    }
                }
                baseMethodName=sb.toString();
            }
            //调用对象的set方法来填充对象属性
            String[] strings = type.getTypeName().split("\\.");
            String typeName = strings[strings.length - 1];
            //通过字段名判断使用哪个get方法来查询数据
            String handlerMethodName = "get" + ("Integer".equals(typeName) ? "Int" : typeName.substring(0, 1).toUpperCase() + typeName.substring(1, typeName.length()));
            Method handlerMethod = resultSet.getClass().getDeclaredMethod(handlerMethodName, int.class);
            Object arg = handlerMethod.invoke(resultSet, i);
            //调用get方法
            if (toMap) {
                Method method = object.getClass().getDeclaredMethod("put", Object.class, Object.class);
                method.invoke(object, baseMethodName, arg);
            } else {
                String methodName = "set" + baseMethodName.substring(0, 1).toUpperCase() + baseMethodName.substring(1, baseMethodName.length());
                Method method = className.getDeclaredMethod(methodName, type);
                method.invoke(object, arg);
            }
        }
    }

    /**
     * 默认数据的处理程序
     *
     * @param resultSet 结果集
     * @param className 类名
     * @return {@link T}* @throws NoSuchMethodException 没有这样的方法异常
     * @throws SQLException              sqlexception异常
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException    非法访问异常
     * @throws InstantiationException    实例化异常
     */
    private static <T> T defaultHandlerData(ResultSet resultSet, Class<T> className) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Constructor<T> constructor = null;
        Method handlerMethod = null;
        String handlerMethodName = null;
        //匹配该字段的类型，将java.lang分割掉，留下来最后的类型
        Class type = matchObject(metaData.getColumnTypeName(1));
        String[] strings = type.getTypeName().split("\\.");
        String typeName = strings[strings.length - 1];
        //如果是Integer，resultSet的get方法是getInt
        //其他的方法都是类型前加get，一一对应
        if ("Integer".equals(typeName)) {
            constructor = className.getConstructor(int.class);
            handlerMethodName = "getInt";
        } else {
            constructor = className.getConstructor(type);
            handlerMethodName = "get" + typeName.substring(0, 1).toUpperCase() + typeName.substring(1, typeName.length());
        }
        //调用resultSet的get方法得到数据
        handlerMethod = resultSet.getClass().getDeclaredMethod(handlerMethodName, int.class);
        Object arg = handlerMethod.invoke(resultSet, 1);
        //调用一参构造方法
        return constructor.newInstance(arg);
    }


    /**
     * 将数据库的字段的数据类型匹配为我们java的数据类型
     *
     * @param columnTypeName 列类型的名字
     * @return {@link Class<?>}
     */
    private static Class<?> matchObject(String columnTypeName) {
        switch (columnTypeName) {
            case "INT":
            case "BIGINT":
                return Integer.class;
            case "TIMESTAMP":
                return Timestamp.class;
            case "VARCHAR":
            case "CHAR":
                return String.class;
            case "FLOAT":
                return Float.class;
            case "DOUBLE":
            case "DECIMAL":
                return Double.class;
            case "DATE":
            case "DATETIME":
                return Date.class;
            default:
                System.out.println(columnTypeName + "类型没有找到对应的匹配类型");
                return Object.class;
        }
    }


    /**
     * 关闭
     *
     * @param connection 连接
     * @param statement  声明
     */
    private static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    /**
     * 关闭
     *
     * @param connection 连接
     */
    private static void close(Connection connection) {
        close(connection, null, null);
    }

    /**
     * 关闭
     *
     * @param connection 连接
     * @param statement  声明
     * @param resultSet  结果集
     */
    private static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (!commit) {
                connection.rollback();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
