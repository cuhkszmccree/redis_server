package com.example.redis_service.Controller;

import com.example.redis_service.Service.RtDataService;
import com.example.redis_service.domain.RtData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RtDataController {
    @Autowired
    private RtDataService RtDataService;
    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "/rtdata_searchbyid", method = RequestMethod.POST)    //永久数据查找
    @HystrixCommand(fallbackMethod = "GetByIdFallback")
    public String SearchRtDataById(@RequestBody String key){
        try {
            RtData result = RtDataService.get_RtDataByid(key);
            System.out.println(result);
            return "Search Complete";
        }
        catch (Exception e){
            System.out.println(e);
            return "Search Failed";
        }
    }

    @RequestMapping(value = "/rtdatainsert", method = RequestMethod.POST)     //永久数据插入
    @HystrixCommand(fallbackMethod = "InsertFallback")
    public String InsertRtData(@RequestBody RtData rtData){
        RLock lock = redissonClient.getLock("insert");
        try {
            lock.lock();
            int key = rtData.getKey();
            int data = rtData.getReal_Time_Data();
            RtDataService.insertRtData(key, data);
            return "Insert Complete";
        }
        catch (Exception e){
            System.out.println(e);
            return "Insert Failed";
        }
        finally {
            lock.unlock();
        }
    }

    @RequestMapping(value = "/rtdataupdate", method = RequestMethod.POST)      //永久数据更新
    @HystrixCommand(fallbackMethod = "UpdateFallback")
    public String UpdateRtData(@RequestBody int key, @RequestBody int data){
        RLock lock = redissonClient.getLock("update");
        try {
            lock.lock();
            RtDataService.updateRtData(key,data);
            return "Update Complete";
        }
        catch (Exception e){
            System.out.println(e);
            return "Update Failed";
        }
        finally {
            lock.unlock();
        }
    }

    public String GetByIdFallback(@RequestBody String key){
        return "Search service timeout";
    }

    public String UpdateFallback(@RequestBody String key, @RequestBody int data){ return "Update service timeout"; }

    public String InsertFallback(@RequestBody String key, @RequestBody int data){ return "Insert service timeout"; }
}
