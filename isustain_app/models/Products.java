package com.example.isustain_app.models;

public class Products {

    Integer productid;
    String productName;
    String productQty;
    String productPrice;
    Integer imageUrl;

    public Products(Integer productid, String productName, String productQty, String productPrice, Integer imageUrl) {
        this.productid = productid;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
