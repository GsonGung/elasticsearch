package com.ucap.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by caolh on 2017/6/28.
 * 查询的输入参数
 */
public class Param implements Serializable {
    public int getPage() {
        return page<=0?1: page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {

        return pageSize <=0 ? 10 : pageSize > 1000 ? 10 : pageSize ;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int page = 1;
    private int pageSize = 10;
    private Map condition;
    public Map getCondition() {
        return condition;
    }

    public void setCondition(Map condition) {
        this.condition = condition;
    }


}
