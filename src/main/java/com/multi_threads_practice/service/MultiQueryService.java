package com.multi_threads_practice.service;

import com.multi_threads_practice.bean.Order;

import java.util.List;

public interface MultiQueryService {

    List<Order> getOrderList(String startTime, String endTime);

}
