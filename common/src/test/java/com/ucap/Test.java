package com.ucap;

import com.google.gson.Gson;
import com.ucap.util.elasticsearch.ESQuerySearch;

/**
 * Create with IntelliJ IDEA
 * User:曹立华
 * Date:2017/9/19
 */
public class Test {
    public static void main(String[] args) {
        ESQuerySearch esQuerySearchUtil = new ESQuerySearch();
        esQuerySearchUtil.setMatchToBool("must","make","honda");
        Gson gson = new Gson();
        String queryDsl = gson.toJson(esQuerySearchUtil.getSearchMap());
        System.out.println(queryDsl);
    }
}
