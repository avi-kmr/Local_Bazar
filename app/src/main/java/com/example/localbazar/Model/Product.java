package com.example.localbazar.Model;

public class Product {

    private String pname;
    private String price;
    private String quantity;
    private String desc;
    private String Image;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    private String PID;

    public Product(String pname, String price, String quantity, String desc, String image, String date, String time, String productType,String PID) {
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.desc = desc;
        this.Image = image;
        this.Date = date;
        this.Time = time;
        this.productType = productType;
        this.PID = PID;
    }

    private String Date;
    private String Time;
    private String productType;

    Product()
    {

    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }



}
