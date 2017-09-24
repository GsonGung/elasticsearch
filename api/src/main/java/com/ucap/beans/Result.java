package com.ucap.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caolh on 2017/6/28.
 */
public class Result implements Serializable    {
    public static void main(String[] args) {
        Result result = new Result();
        System.out.println(result);
    }
    private boolean status=false;
    private String  msg=null;
    private Map dataMap = new HashMap();
    //默认count是0
    private Integer count =0;
    //默认结果集是空
    private List list = new ArrayList();

    //通过构造方法完成组合
    public Result() {
        //默认是0，若改变需要重新dataMap.put("count",num),不能通过设置成员变量count的值
        dataMap.put("count",count);
        //默认list为空，增加直接通过成员变量list  add添加元素就可以
        dataMap.put("list",list);
    }

    /**
     * 只打印属性status,msg,dataMap
     * @return
     */
    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", dataMap=" + dataMap +
                '}';
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getDataMap() {
        return dataMap;
    }

    //禁止setMap
//    public void setDataMap(Map dataMap) {
//        this.dataMap = dataMap;
//    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List getList() {
        return list;
    }

    //禁止setList，防止出错
//    public void setList(List list) {
//        this.list = list;
//    }
}
