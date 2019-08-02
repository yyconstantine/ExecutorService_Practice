package com.multi_threads_practice.bean;

public class Order {

    private String id;// 订单号
    private String market;// 订单所属商家
    private String status;// 订单状态
    private Double transAmount;// 订单交易金额
    private String orderDate;// 订单交易时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", market='" + market + '\'' +
                ", status='" + status + '\'' +
                ", transAmount='" + transAmount + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }

    public Order() {
    }
}
