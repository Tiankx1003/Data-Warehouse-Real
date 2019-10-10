package com.tian.gmallpublisher.controller;

import com.alibaba.fastjson.JSON;
import com.tian.gmallpublisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tian
 * @version 1.0.0
 * @date 2019/10/9 10:45
 */
@RestController
public class PublisherController {
    @Autowired
    public PublisherService service;

    @GetMapping("realtime-total")
    public String getDau(@RequestParam("date") String date) { //使用注解解析参数
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "dau");
        map1.put("name", "新增日活");
        map1.put("value", service.getDau(date));
        resultList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "new_mid");
        map2.put("name", "新增设备");
        map2.put("value", 233);
        resultList.add(map2);

        return JSON.toJSONString(resultList);
    }

    @GetMapping("realtime-hour")
    public String getHourDau(@RequestParam("id") String id, @RequestParam("date") String date) {
        if ("dau".equals(id)) {
            Map<String, Long> today = service.getHourDau(date);
            Map<String, Long> yesterday = service.getHourDau(date2Yesterday(date));
            HashMap<String, Map<String, Long>> resultMap = new HashMap<>();
            resultMap.put("today", today);
            resultMap.put("yesterday", yesterday);
            return JSON.toJSONString(resultMap);
        } else {
            return "";
        }
    }

    @GetMapping("realtime-total")
    public String getRealtimeTotal(@RequestParam("date") String date) {
        List<Map> totalList = new ArrayList<>();

        Map<String,Object> dauMap = new HashMap<>();
        dauMap.put("id", "dau");
        dauMap.put("name", "新增日活");
        dauMap.put("value", service.getDauTotal(date));
        totalList.add(dauMap);

        Map<String,Object> midMap = new HashMap<>();
        midMap.put("id", "new_mid");
        midMap.put("name", "新增设备");
        midMap.put("value", "1000");
        totalList.add(midMap);

        Map<String,Object> orderAmountMap = new HashMap<>();
        orderAmountMap.put("id", "order_amount");
        orderAmountMap.put("name", "新增交易额");
        orderAmountMap.put("value", service.getOrderAmountTotal(date));
        totalList.add(orderAmountMap);

        return JSON.toJSONString(totalList);
    }

    @GetMapping("realtime-hour")
    public String getRealtimeHour(@RequestParam("id") String id, @RequestParam("date") String date) {
        if ("dau".equals(id)) {
            Map todayDauHourMap = service.getDauHour(date);
            Map yesterdayDauHourMap = service.getDauHour(date2Yesterday(date));

            Map<String, Map> hourMap = new HashMap<>();
            hourMap.put("today", todayDauHourMap);
            hourMap.put("yesterday", yesterdayDauHourMap);
            return JSON.toJSONString(hourMap);
        } else if ("order_amount".equals(id)) {
            Map todayOrderAmountHourMap = service.getOrderAmountHour(date);
            Map yesterdayOrderAmountHourMap = service.getOrderAmountHour(date2Yesterday(date));

            Map<String, Map> hourMap = new HashMap<>();
            hourMap.put("today", todayOrderAmountHourMap);
            hourMap.put("yesterday", yesterdayOrderAmountHourMap);
            return JSON.toJSONString(hourMap);
        }
        return null;
    }


    /**
     * 根据传入的日期转换成昨天
     *
     * @param date
     * @return
     */
    private String date2Yesterday(String date) {
        return LocalDate.parse(date).minusDays(1).toString();
    }

}
