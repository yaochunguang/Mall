package com.company.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页结果封装对象
 * @author: chunguang.yao
 * @date: 2019-07-04 0:08
 */
public class PageResult implements Serializable {

    // 总记录数
    private long total;

    // 结果，格式是json，如：[{"firstChar":"L","id":1,"name":"联想"},{"firstChar":"H","id":2,"name":"华为"}]
    private List rows;

    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
