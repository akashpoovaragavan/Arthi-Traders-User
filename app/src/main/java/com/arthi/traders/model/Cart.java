package com.arthi.traders.model;

public class Cart {
    private String cart_product_id,cart_product_name,cart_product_quantity,cart_purchased_qnt,cart_product_total,cart_product_description;
    private String cart_product_image;

    public Cart(String cart_product_id, String cart_product_name, String cart_product_quantity, String cart_purchased_qnt, String cart_product_total, String cart_product_image,String cart_product_description) {
        this.cart_product_id = cart_product_id;
        this.cart_product_name = cart_product_name;
        this.cart_product_quantity = cart_product_quantity;
        this.cart_purchased_qnt = cart_purchased_qnt;
        this.cart_product_total = cart_product_total;
        this.cart_product_image = cart_product_image;
        this.cart_product_description=cart_product_description;
    }

    public String getCart_product_id() {
        return cart_product_id;
    }

    public String getCart_product_name() {
        return cart_product_name;
    }

    public String getCart_product_quantity() {
        return cart_product_quantity;
    }

    public String getCart_purchased_qnt() {
        return cart_purchased_qnt;
    }

    public String getCart_product_total() {
        return cart_product_total;
    }

    public String getCart_product_description() {
        return cart_product_description;
    }

    public void setCart_product_id(String cart_product_id) {
        this.cart_product_id = cart_product_id;
    }

    public void setCart_product_name(String cart_product_name) {
        this.cart_product_name = cart_product_name;
    }

    public void setCart_product_quantity(String cart_product_quantity) {
        this.cart_product_quantity = cart_product_quantity;
    }

    public void setCart_purchased_qnt(String cart_purchased_qnt) {
        this.cart_purchased_qnt = cart_purchased_qnt;
    }

    public void setCart_product_total(String cart_product_total) {
        this.cart_product_total = cart_product_total;
    }

    public String getCart_product_image() {
        return cart_product_image;
    }

    public void setCart_product_image(String cart_product_image) {
        this.cart_product_image = cart_product_image;
    }

    public void setCart_product_description(String cart_product_description) {
        this.cart_product_description = cart_product_description;
    }
}
