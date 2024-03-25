package com.sd3.market.dtos;

public class ProductDto {
    public long id;
    public String name;
    public String description;
    public double price;
    public int quantityInStock;

    public ProductDto() {
    }

    public ProductDto(long id, String name, String description, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public ProductDto(String name, String description, double price, int quantityInStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
}
