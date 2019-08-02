package com.multi_threads_practice.task;

import com.multi_threads_practice.bean.Order;
import com.multi_threads_practice.dao.MultiQueryDao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class MultiQueryTask implements Callable<List<Order>> {

    private MultiQueryDao multiQueryDao;

    private final ConcurrentHashMap<String, String> param;

    public MultiQueryTask(MultiQueryDao multiQueryDao, ConcurrentHashMap<String, String> param) {
        this.multiQueryDao = multiQueryDao;
        this.param = param;
    }

    @Override
    public List<Order> call() throws Exception {
        return multiQueryDao.getOrderList(param.get("startTime"), param.get("endTime"));
    }
}
