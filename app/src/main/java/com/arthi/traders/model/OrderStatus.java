package com.arthi.traders.model;

public class OrderStatus {
    String OrderId,Date_placed,Date_processing,Date_shipping,Date_delivered,Date_canceled;

    public OrderStatus() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getDate_placed() {
        return Date_placed;
    }

    public String getDate_processing() {
        return Date_processing;
    }

    public String getDate_shipping() {
        return Date_shipping;
    }

    public String getDate_delivered() {
        return Date_delivered;
    }

    public String getDate_canceled() {
        return Date_canceled;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setDate_placed(String date_placed) {
        Date_placed = date_placed;
    }

    public void setDate_processing(String date_processing) {
        Date_processing = date_processing;
    }

    public void setDate_shipping(String date_shipping) {
        Date_shipping = date_shipping;
    }

    public void setDate_delivered(String date_delivered) {
        Date_delivered = date_delivered;
    }

    public void setDate_canceled(String date_canceled) {
        Date_canceled = date_canceled;
    }
}
