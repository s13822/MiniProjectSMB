package com.example.miniprojectsmb.DBContext;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product")
public class Product {
    public Product(String name, int price, int count, boolean bought) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.bought = bought;
    }



    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "IdProduct")
    private int IdProduct;
    @ColumnInfo(name = "name")
    @NonNull
    private String name;
    @ColumnInfo(name = "price")
    @NonNull
    private int price;
    @ColumnInfo(name = "count")
    @NonNull
    private int count;
    @ColumnInfo(name = "bought")
    private boolean bought;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public void setIdProduct(int idProduct) {
        IdProduct = idProduct;
    }

    public int getIdProduct() {
        return IdProduct;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public boolean isBought() {
        return bought;
    }
}
