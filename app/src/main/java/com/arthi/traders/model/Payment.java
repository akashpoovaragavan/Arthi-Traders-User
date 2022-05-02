package com.arthi.traders.model;

public class Payment {

    private String Userid,Amount,Paymentdate,Trandsactionmode,Paymentimage,Status,Trandsactionid;

    public Payment() {

    }

    public String getUserid() {
        return Userid;
    }

    public String getAmount() {
        return Amount;
    }

    public String getPaymentdate() {
        return Paymentdate;
    }

    public String getTrandsactionmode() {
        return Trandsactionmode;
    }

    public String getPaymentimage() {
        return Paymentimage;
    }

    public String getStatus() {
        return Status;
    }

    public String getTrandsactionid() {
        return Trandsactionid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setPaymentdate(String paymentdate) {
        Paymentdate = paymentdate;
    }

    public void setTrandsactionmode(String trandsactionmode) {
        Trandsactionmode = trandsactionmode;
    }

    public void setPaymentimage(String paymentimage) {
        Paymentimage = paymentimage;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setTrandsactionid(String trandsactionid) {
        Trandsactionid = trandsactionid;
    }
}
