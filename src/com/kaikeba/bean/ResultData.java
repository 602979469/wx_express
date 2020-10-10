package com.kaikeba.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果数据
 *
 * @author Faker
 * @date 2020/09/30
 */
public class ResultData<T> {

    /**
     * 默认的单页数量
     */
    public static final int DEFAULT_PAGE_SIZE=1<<2;

    /**
     * 每次查询的数据集合
     */
    private List<T> rows = new ArrayList<>();

    /**
     * 总数量
     */
    private int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
