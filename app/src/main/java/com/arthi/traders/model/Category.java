package com.arthi.traders.model;

public class Category {


    private String Categoryid,Categoryimage,Categoryname,Subcategoryid,Subcategoryname,Subcategoryimage;

    public Category() {

    }



    public String getCategoryid() {
        return Categoryid;
    }

    public String getCategoryimage() {
        return Categoryimage;
    }

    public String getCategoryname() {
        return Categoryname;
    }

    public String getSubcategoryid() {
        return Subcategoryid;
    }

    public String getSubcategoryname() {
        return Subcategoryname;
    }

    public String getSubcategoryimage() {
        return Subcategoryimage;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public void setCategoryimage(String categoryimage) {
        Categoryimage = categoryimage;
    }

    public void setCategoryname(String categoryname) {
        Categoryname = categoryname;
    }

    public void setSubcategoryid(String subcategoryid) {
        Subcategoryid = subcategoryid;
    }

    public void setSubcategoryname(String subcategoryname) {
        Subcategoryname = subcategoryname;
    }

    public void setSubcategoryimage(String subcategoryimage) {
        Subcategoryimage = subcategoryimage;
    }
}
