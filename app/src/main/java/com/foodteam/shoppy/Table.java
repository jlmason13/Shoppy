package com.foodteam.shoppy;

public class Table {
    //NOTE: This code is not yet being used. This is for optimizing the DBHandler methods
    //so that I can have one generic "add" method instead of "addToList", "addToDetails", etc.
    String product;
    int frequency;
    float avgPrice;
    float lowestPrice;
    float totalSpent;

    public Table(){}

    public void setProduct(String product){
        this.product = product;
    }

    public void setFrequency(int f){
        f = frequency;
    }

    public void setAvgPrice(float avg){
        avg = avgPrice;
    }

    public void setLowPrice(float low){
        low = lowestPrice;
    }

    public void setTotal(float tot){
        tot = totalSpent;
    }

    public String getProduct(){
        return product;
    }

    public int getFrequency(){
        return frequency;
    }

    public float getAvgPrice(){
        return avgPrice;
    }

    public float getLowestPrice(){
        return lowestPrice;
    }

    public float getTotalSpent(){
        return totalSpent;
    }
}
