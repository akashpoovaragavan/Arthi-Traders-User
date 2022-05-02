package com.arthi.traders.model;

public class Product {


   private String Dol_id,Dol_category,Dol_sub_category,Dol_product_name,Dol_product_description,Dol_product_code,Dol_product_pack,
           Dol_product_weight,Dol_product_image,Dol_product_quantity,Dol_product_price,Dol_created_at,Dol_updated_at;


   public Product(){

    }
    public Product(String dol_id, String dol_category, String dol_sub_category, String dol_product_name, String dol_product_description, String dol_product_code, String dol_product_pack,
                        String dol_product_weight,String dol_product_image,String dol_product_quantity,String dol_product_price,String dol_created_at,String dol_updated_at) {
        this.Dol_id = dol_id;
        this.Dol_category = dol_category;
        this.Dol_sub_category = dol_sub_category;
        this.Dol_product_name = dol_product_name;
        this.Dol_product_description = dol_product_description;
        this.Dol_product_code = dol_product_code;
        this.Dol_product_pack = dol_product_pack;
        this.Dol_product_weight = dol_product_weight;
        this.Dol_product_image= dol_product_image;
        this.Dol_product_quantity = dol_product_quantity;
        this.Dol_product_price= dol_product_price;
        this.Dol_created_at = dol_created_at;
        this.Dol_updated_at = dol_updated_at;

    }

    public String getDol_id() {
        return Dol_id;
    }

    public String getDol_category() {
        return Dol_category;
    }

    public String getDol_sub_category() {
        return Dol_sub_category;
    }

    public String getDol_product_name() {
        return Dol_product_name;
    }

    public String getDol_product_description() {
        return Dol_product_description;
    }

    public String getDol_product_code() {
        return Dol_product_code;
    }

    public String getDol_product_pack() {
        return Dol_product_pack;
    }

    public String getDol_product_weight() {
        return Dol_product_weight;
    }

    public String getDol_product_image() {
        return Dol_product_image;
    }

    public String getDol_product_quantity() {
        return Dol_product_quantity;
    }

    public String getDol_product_price() {
        return Dol_product_price;
    }

    public String getDol_created_at() {
        return Dol_created_at;
    }

    public String getDol_updated_at() {
        return Dol_updated_at;
    }

    public void setDol_id(String dol_id) {
        Dol_id = dol_id;
    }

    public void setDol_category(String dol_category) {
        Dol_category = dol_category;
    }

    public void setDol_sub_category(String dol_sub_category) {
        Dol_sub_category = dol_sub_category;
    }

    public void setDol_product_name(String dol_product_name) {
        Dol_product_name = dol_product_name;
    }

    public void setDol_product_description(String dol_product_description) {
        Dol_product_description = dol_product_description;
    }

    public void setDol_product_code(String dol_product_code) {
        Dol_product_code = dol_product_code;
    }

    public void setDol_product_pack(String dol_product_pack) {
        Dol_product_pack = dol_product_pack;
    }

    public void setDol_product_weight(String dol_product_weight) {
        Dol_product_weight = dol_product_weight;
    }

    public void setDol_product_image(String dol_product_image) {
        Dol_product_image = dol_product_image;
    }

    public void setDol_product_quantity(String dol_product_quantity) {
        Dol_product_quantity = dol_product_quantity;
    }

    public void setDol_product_price(String dol_product_price) {
        Dol_product_price = dol_product_price;
    }

    public void setDol_created_at(String dol_created_at) {
        Dol_created_at = dol_created_at;
    }

    public void setDol_updated_at(String dol_updated_at) {
        Dol_updated_at = dol_updated_at;
    }
}
