package com.multi_threads_practice.dao;

import com.multi_threads_practice.bean.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiQueryDao {

    List<Order> getOrderList(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
