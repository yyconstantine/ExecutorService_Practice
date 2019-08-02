package com.multi_threads_practice.controller;

import com.multi_threads_practice.bean.Order;
import com.multi_threads_practice.service.MultiQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MultiQueryController {

    @Autowired
    private MultiQueryService multiQueryService;

    @RequestMapping(value = "/query")
    public String query(@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        List<Order> list = multiQueryService.getOrderList(startTime, endTime);
        return null;
    }

}
