package com.kaikeba.dao;

import com.kaikeba.bean.Express;

import java.util.List;
import java.util.Map;

/**
 * 基本的增删改查
 *
 * Created with IntelliJ IDEA.
 *
 * @author Faker
 * @Description :
 * @author: Faker
 * @date 2020/10/01
 */
public interface BaseCrud<T> {

    /**
     * 控制台
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    List<Map<String,Integer>> console();

    /**
     * 找到所有
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<T> findAll(boolean limit, int offset, int pageNumber);

        /**
         * 更新
         *
         * @param t t
         * @return 修改的结果，true表示成功，false表示失败
         */
    boolean update(T t);

    /**
     * 根据id删除
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    boolean delete(int id);

    /**
     * 插入数据
     *
     * @param t t
     * @return boolean
     */
    boolean insert(T t);

    /**
     * 找到通过ID编号
     *
     * @param id ID编号
     * @return {@link T}
     */
    T findById(Integer id);
}
