package com.arthi.traders.model;

public class Notification {
    private String Message,Title,Amount,Date,Orderid;

    public Notification() {

    }


    public String getMessage() {
        return Message;
    }

    public String getTitle() {
        return Title;
    }

    public String getAmount() {
        return Amount;
    }

    public String getDate() {
        return Date;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }
}
