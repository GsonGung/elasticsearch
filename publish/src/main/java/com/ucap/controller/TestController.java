package com.ucap.controller;

import com.google.gson.Gson;
import com.ucap.beans.Param;
import com.ucap.beans.Result;
import com.ucap.util.elasticsearch.ESHttpClient;
import com.ucap.util.elasticsearch.ESQuerySearch;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Create with IntelliJ IDEA
 * User:曹立华
 * Date:2017/9/19
 */
@RestController
public class TestController {
    public static Logger logger = LoggerFactory.getLogger(TestController.class);
        @RequestMapping("/test")
        @ResponseBody
        public Result testController(HttpServletRequest request) throws Exception{

            String paramJson = request.getParameter("param");
            Param param=null;
            Result result = new Result();
            Gson gson = new Gson();
//        try {
//            param = gson.fromJson(paramJson,Param.class);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            result.setMsg(e.getMessage());
//            return result;
//        }
        ESQuerySearch esQuerySearchUtil = new ESQuerySearch();
        esQuerySearchUtil.setMatchToBool("must","make",paramJson);

        String queryUrl= "http://112.124.112.59:9801/cars/transactions/_search";
        String queryDsl = gson.toJson(esQuerySearchUtil.getSearchMap());
        ESHttpClient esHttpClient = new ESHttpClient();
         result =  esHttpClient.query(new HttpPost(),queryDsl,queryUrl);

        System.out.println("hhhhh");
        System.out.println(param);
        return result;
    }
}
