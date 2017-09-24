package com.ucap.controller;

import com.google.gson.Gson;
import com.ucap.beans.Result;
import com.ucap.util.elasticsearch.ESHttpClient;
import com.ucap.util.elasticsearch.ESQuerySearch;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Create with IntelliJ IDEA
 * User:曹立华
 * Date:2017/9/20
 * 页面 auto_eject.html相关搜索请求
 */
@RestController
@RequestMapping("/auto_eject")
public class Auto_ejectController {
    public static Logger logger = LoggerFactory.getLogger(Auto_ejectController.class);
    @Value("${es.url.test}")
    private String esUrlTest;
    @Value("${es.index.article}")
    private String esIndexArticle;
    @Value("${es.search}")
    private String esSearch;

    @RequestMapping("/searchClick")
    @ResponseBody
    public Result searchClick(HttpServletRequest request){
        String searchClickParam = request.getParameter("searchClickParam");
        String searchTime = request.getParameter("searchTime");
        System.out.println("-----------------");

        ESQuerySearch esQuerySearch = new ESQuerySearch();
        esQuerySearch.setMatchToBool("must","content",searchClickParam);
        String queryUrl = esUrlTest+esIndexArticle+esSearch;
        Gson gson = new Gson();
        String queryDsl = gson.toJson(esQuerySearch.getSearchMap());
        ESHttpClient esHttpClient  = new ESHttpClient();
        Result result  = new Result();
        try {
            result = esHttpClient.query(new HttpPost(),queryDsl,queryUrl);
        } catch (Exception e) {
            result.setStatus(false);
            result.setMsg(e.getMessage());
        }

        System.out.println(result);
        return result;
    }
}
