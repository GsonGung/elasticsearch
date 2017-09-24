package com.ucap.util.elasticsearch;

import com.google.gson.Gson;
import com.ucap.beans.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ES查询结果集处理
 * User:caolh
 * Date:2017/8/2
 */
public class ESResultDeal {
    /**
     * 根据主键Id查询，返回结果集处理
     *
     * @param json
     * @return
     */
    public Result getResultDeal(String statusLine, String json) {
        Gson gson = new Gson();
        Result result = new Result();
        Map resMap =null;

        Map dataMap = result.getDataMap();
        List list = result.getList();
        //设置成功或失败的原因
        result.setMsg(statusLine);
        //es自己的问题：es正常启动，但是查询返回的不是标准json(可能性比较小，但是不能不考虑)
        try{
            resMap = gson.fromJson(json, Map.class);
        }catch (Exception e){
            result.setMsg(statusLine+";"+e.getMessage());
        }


        if (resMap.get("_source") != null) {
            /**
             * {
             "_index": "test_index",
             "_type": "test_mapping",
             "_id": "1",
             "_version": 1,
             "found": true,
             "_source": {
             "name": "caolihua",
             "age": 18,
             "money": 100,
             "date": "Aug 28, 2017 11:59:10 AM"
             }
             }
             */
            result.setStatus(true);
            list.add(resMap.get("_source"));
            dataMap.put("count", 1);
            return result;
        } else {
            /**
             * {
             "_index": "test_index",
             "_type": "test_mapping",
             "_id": "10",
             "found": false
             }
             */
            return result;
        }

    }

    /**
     * 提交post请求，返回结果集处理
     *
     * @param json
     * @return
     */
    public Result postResultDeal(String statusLine,String json) {
        Gson gson = new Gson();
        Result result = new Result();
        Map resMap =null;
        //设置成功或失败的原因
        result.setMsg(statusLine);
        //es自己的问题：es正常启动，但是查询返回的不是标准json(可能性比较小，但是不能不考虑)
        try{
            resMap = gson.fromJson(json, Map.class);
        }catch (Exception e){
            result.setMsg(statusLine+";"+e.getMessage());
        }

        Map hits1Map = (Map)resMap.get("hits");
        if(hits1Map.get("total").equals(0.0)){
            /**
             * {
             "took": 22,
             "timed_out": false,
             "_shards": {
             "total": 5,
             "successful": 5,
             "failed": 0
             },
             "hits": {
             "total": 0,
             "max_score": null,
             "hits": []
             }
             }
             */
            //查询成功，但结果集为空
            result.setStatus(true);
            return result;
        }else if((Double)hits1Map.get("total")>=0.0){
            /**
             * {
             "took": 223,
             "timed_out": false,
             "_shards": {
             "total": 5,
             "successful": 5,
             "failed": 0
             },
             "hits": {
             "total": 4,
             "max_score": 1.0,
             "hits": [{
             "_index": "test_index",
             "_type": "test_mapping",
             "_id": "2",
             "_score": 1.0,
             "_source": {
             "name": "caolihua",
             "age": 18,
             "money": 100.0,
             "date": "Aug 28, 2017 12:02:02 PM"
             }
             },
             {
             "_index": "test_index",
             "_type": "test_mapping",
             "_id": "3",
             "_score": 1.0,
             "_source": {
             "name": "caolihua",
             "age": 18,
             "money": 100.0,
             "date": "Aug 28, 2017 12:02:45 PM"
             }
             }]
             }
             }
             */

            //查询成功，结果集不为空
            List hits2List =(ArrayList) hits1Map.get("hits");
            int count = hits2List.size();
            result.getDataMap().put("count",count);
            //循环遍历hits2里面的map，过滤得到source再次填充到结果集里的dataMap
            for(int i=0;i<count;i++){
                Map singleSourceMap =(Map) hits2List.get(i);
                result.getList().add(singleSourceMap.get("_source"));
            }
            result.setStatus(true);
            return result;
        }else {
                //小于0.0 情况，es出问题了
                result.setMsg("ES--error");
                return result;
        }

    }

    /**
     * 执行Http请求查询ES，各种异常处理，禁止服务被调用处返回500异常
     * @param excepString
     * @return
     */
    public Result exceptionDeal(String excepString){
        Result result = new Result();
        result.setStatus(false);
        result.setMsg(excepString);
        return result;
    }
}
