package com.multi_threads_practice.service.impl;

import com.multi_threads_practice.bean.Order;
import com.multi_threads_practice.dao.MultiQueryDao;
import com.multi_threads_practice.service.MultiQueryService;
import com.multi_threads_practice.task.MultiQueryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class MultiQueryServiceImpl implements MultiQueryService {

    @Autowired
    private MultiQueryDao multiQueryDao;

    @Override
    public List<Order> getOrderList(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            c1.setTime(startDate);
            c2.setTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取要查询的时间范围
        int timeInterval = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        // 规定只允许查询一个月的数据
        List<Order> list = new ArrayList<>();
        // 将时间范围分割成每天，为每天开启一个线程进行查询，最后将查询的结果汇总成一条结果集
        ExecutorService exec = Executors.newFixedThreadPool(timeInterval);

        List<Callable<List<Order>>> tasks = new ArrayList<>();// 用于存放执行任务
        for (int i = 0; i < timeInterval; i++) {
            // 将查询任务放入tasks
            tasks.add(new MultiQueryTask(multiQueryDao, divideDate(c1)));
        }

        List<Future<List<Order>>> futureList = new ArrayList<>();// 用于接收执行结果
        try {
            futureList = exec.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < timeInterval; i++) {
            if (futureList.get(i) != null) {
                try {
                    list.addAll(futureList.get(i).get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        // 将订单按商户进行group
        Map<String, List<Order>> res = new HashMap<>();
        for (Order element : list) {
            String key = element.getMarket();
            if (res.containsKey(key)) {
                res.get(key).add(element);
            } else {
                List<Order> value = new ArrayList<>();
                value.add(element);
                res.put(key, value);
            }
        }

        // 将商户的订单金额汇总
        List<Order> result = new ArrayList<>();
        for (String key : res.keySet()) {
            Order order = new Order();
            if (res.get(key).size() > 1) {
                Double transAmount = 0d;
                String market = "";
                for (Order o : res.get(key)) {
                    transAmount += o.getTransAmount();
                    market = o.getMarket();
                }
                order.setTransAmount(transAmount);
                order.setMarket(market);
                result.add(order);
            } else {
                result.addAll(res.get(key));
            }
        }
        return result;
    }

    // 传入startTime，在for循环中经过timeInterval次后至endTime
    private ConcurrentHashMap<String, String> divideDate(Calendar calendar) {
        ConcurrentHashMap<String, String> param = new ConcurrentHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Date startTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endTime = calendar.getTime();

        param.put("startTime", sdf.format(startTime));
        param.put("endTime", sdf.format(endTime));

        return param;
    }

}
