package com.ucap.util.elasticsearch;


import com.google.gson.Gson;
import com.ucap.beans.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Create with IntelliJ IDEA
 * User:caolh
 * Date:2017/8/17
 */
public class ESHttpClient {
    private static String baseUrl = "http://localhost:9200/";
    private static String urlGetById = baseUrl+"test_index/test_mapping/1";
    private static String urlPostNone=baseUrl+"_search?q=name:1";
    private static String urlPostMany=baseUrl+"_search";
    private static Gson gson = new Gson();
    private static ESHttpClient httpclientUtil = new ESHttpClient();
    public static void main(String[] args) throws Exception {
        //测试根据主键查
        //Result resultGet = httpclientUtil.query(new HttpGet(),"",urlGetById);
        //System.out.println("resultGet>>"+resultGet);

        //测试查询多个结果
        Result resultPostNone = httpclientUtil.query(new HttpPost(),"",urlPostNone);
        System.out.println("resultPostNone>>"+resultPostNone);
        Result resultPostMany = httpclientUtil.query(new HttpPost(),"",urlPostMany);
        System.out.println("resultPostMany>>"+resultPostMany);





    }


    /**
     *
     * @param request
     * @param json
     * @param url
     * @return
     * @throws Exception
     */
    public Result query(HttpRequest request, String json, String url) throws Exception {
        //判断是否是httpPut请求
        if (request.getClass().equals(HttpPut.class)) {
            StringEntity entity = new StringEntity(json, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(entity);
            return  executeHttp(httpPut);
        }
        //判断是否是HttpPost请求
        if (request.getClass().equals(HttpPost.class)) {
            StringEntity entity = new StringEntity(json, "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            return executeHttp(httpPost);
        }
        //判断是否是HttpGet请求
        if (request.getClass().equals(HttpGet.class)) {
            HttpGet httpGet = new HttpGet(url);
            return executeHttp(httpGet);
        }
        //不是以上三种http请求类型
        Result result = new Result();
        result.setStatus(false);
        result.setMsg("请输入正确的请求类型");
        return result;
    }

    /**
     * 执行put、post、get请求
     *
     * @param httpUriRequest
     * @return
     */
    protected Result executeHttp(HttpUriRequest httpUriRequest) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //结果集处理：不同请求类型，处理方式不同
        ESResultDeal esResultDeal = new ESResultDeal();

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
            StatusLine statusLine = httpResponse.getStatusLine();
            int code = statusLine.getStatusCode();
            HttpEntity resEntity = httpResponse.getEntity();

            String queryESJson= EntityUtils.toString(resEntity);
            //将状态传入result的结果里
            if(httpUriRequest.getClass().equals(HttpGet.class)){
                return esResultDeal.getResultDeal(statusLine.toString(),queryESJson);
            }
            if(httpUriRequest.getClass().equals(HttpPost.class)){
                return esResultDeal.postResultDeal(statusLine.toString(),queryESJson);
            }
            if(httpUriRequest.getClass().equals(HttpPut.class)){
                //
            }
            //System.out.println(code);
            // 200 or 201 ???
//            if (code == 200||code==201) {
//                return resMap;
//                //根据主键id查询ES没查到；如果查到，需要过滤_source结果集
//            } else if(code==404&&(httpUriRequest.getClass().equals(HttpGet.class))){
//                return statusLine+">>json:"+EntityUtils.toString(resEntity);
//            } else {
//                //返回400等
//                throw new RuntimeException(statusLine+">>json:"+EntityUtils.toString(resEntity));
//            }
        } catch (Exception e) {
            //日志打印，后续补上
            //logger.info();
            //1.es断开连接
            return esResultDeal.exceptionDeal(e.getMessage());
            //2......
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

}
