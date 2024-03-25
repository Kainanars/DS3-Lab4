package com.sd3.market.entities;

import jakarta.persistence.*;

@Entity
@Table(name="TB_PRODUCTS")
public class Product extends AbstractEntity {
    @Column(name = "NM_PRODUCT")
    private String name;
    @Column(name = "VL_PRICE")
    private Double price;

    @Column(name = "DS_DESCRIPTION")
    private String description;

    @Column(name = "NR_STOCK")
    private int quantityInStock;

    public Product(String name, Double price, String description, int quantityInStock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityInStock = quantityInStock;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public void setName(String namePassed) {
        name = namePassed;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double pricePassed) {
        price = pricePassed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionPassed) {
        description = descriptionPassed;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
