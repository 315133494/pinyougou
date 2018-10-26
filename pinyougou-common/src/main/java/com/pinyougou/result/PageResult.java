package com.pinyougou.result;

import java.util.List;

/**
 * 返回结果类
 */
public class PageResult {
    public List<?> rows;
    public Long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
