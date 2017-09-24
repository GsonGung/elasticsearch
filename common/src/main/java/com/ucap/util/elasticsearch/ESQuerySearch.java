package com.ucap.util.elasticsearch;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹立华 on 2017/8/7.
 *
 */
public class ESQuerySearch {
    /**
     * 最外层
     */
    Map searchMap = new HashMap();
    //第一层
    //分页
    private Integer from = 0;
    private Integer size = 20;
    //query
    private Map queryMap = new HashMap();
    //排序
    private List sortList = new ArrayList();
    //聚合
    private Map aggMap = new HashMap();
    //高亮
    private Map highlightMap = new HashMap();

    //第二层
    private Map boolMap = new HashMap();
    //第三层
    private List mustList = new ArrayList();
    private List shouldList = new ArrayList();

    /**
     * 初始化封装，默认分页from：0，size：20
     */
    public ESQuerySearch() {
        //一级封装
        searchMap.put("from", from);
        searchMap.put("size", size);
        searchMap.put("query", queryMap);
        searchMap.put("sort", sortList);
        searchMap.put("aggs", aggMap);
        searchMap.put("highlight",highlightMap);
        //二级封装
        queryMap.put("bool", boolMap);
        //三级封装
        boolMap.put("must", mustList);
        boolMap.put("should", shouldList);
    }

    /**
     * 初始化封装，带分页
     */
    public ESQuerySearch(int from, int size) {
        //一级封装
        searchMap.put("from", from);
        searchMap.put("size", size);
        searchMap.put("query", queryMap);
        searchMap.put("sort", sortList);
        searchMap.put("aggs", aggMap);
        searchMap.put("highlight",highlightMap);
        //二级封装
        queryMap.put("bool", boolMap);

        //三级封装
        boolMap.put("must", mustList);
        boolMap.put("should", shouldList);
    }

    public Map getSearchMap() {
        return searchMap;
    }

    /**
     * 将排序规则sort设置到一级查询search里
     * @param field 排序字段
     * @param order 排序规则，asc/desc
     */
    public void setSortToSearch(String field, String order) {
        Map fieldMap = new HashMap();
        Map orderMap = new HashMap();
        fieldMap.put(field, orderMap);
        orderMap.put("order", order);
        sortList.add(fieldMap);
    }

    /**
     * 设置需要高亮显示的字段
     * @param field
     */
    public void setHighlightToSearch(String field){
        Map fieldsMap = new HashMap();
        fieldsMap.put(field,new HashMap());
        highlightMap.put("fields",fieldsMap);
    }
    /**
     * 将全文检索match设置到逻辑值bool里
     * @param bool       逻辑类型 ，must/should
     * @param matchField 全文检索字段名
     * @param value      字段值
     */
    public void setMatchToBool(String bool, String matchField, String value) {
        Map matchMap = new HashMap();
        Map mvMap = new HashMap();
        matchMap.put("match", mvMap);
        mvMap.put(matchField, value);
        if (bool.equals("must")) {
            mustList.add(matchMap);
        } else if (bool.equals("should")) {
            shouldList.add(matchMap);
        }
    }

    /**
     * 将模糊查询wildcard设置到逻辑值bool里
     * @param bool          逻辑值must/should
     * @param wildcardField 模糊查询字段名
     * @param value         模糊查询字段值
     */
    public void setWildcardToBool(String bool, String wildcardField, String value) {
        Map wildcardMap = new HashMap();
        Map wvMap = new HashMap();
        wildcardMap.put("wildcard", wvMap);
        wvMap.put(wildcardField, "*"+value+"*");
        if(bool.equals("must")){
            mustList.add(wildcardMap);
        }else if(bool.equals("should")){
            shouldList.add(wildcardMap);
        }
    }


    /**
     * 一定是must，条件过滤
     * 将range 范围过滤设置到逻辑值bool里
     * @param rangeField
     * @param gte
     * @param lte
     */
    public void setRangeToMust(String rangeField, Long gte, Long lte) {
        Map rangeMap = new HashMap();
        Map filedMap = new HashMap();
        Map glMap = new HashMap();
        rangeMap.put("range", filedMap);
        filedMap.put(rangeField, glMap);
        if(gte!=null){
            glMap.put("gte", gte);
        }
        if(lte!=null){
            glMap.put("lte", lte);
        }
        mustList.add(rangeMap);
    }


    public static void main(String[] args) throws Exception {
        String a = "xxxx";
        System.out.println(a);
        ESQuerySearch esQuerySearchUtil = new ESQuerySearch(1, 2);
        esQuerySearchUtil.setSortToSearch("writetime", "desc");
        esQuerySearchUtil.setMatchToBool("must", "pcodeid", "440000");
        esQuerySearchUtil.setRangeToMust("writetime", 1502095838235l, 1502095838235l);
        esQuerySearchUtil.setWildcardToBool("should","netserverport_wacode","x");
        esQuerySearchUtil.setHighlightToSearch("haapy");
        Gson gson =new Gson();
        String str = gson.toJson(esQuerySearchUtil.getSearchMap());
        System.out.println(str);
    }

}
