package com.sd3.market.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="TB_SALES")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_gen")
    @SequenceGenerator(name = "entity_seq_gen", sequenceName = "entity_seq", allocationSize = 1)
    @Column(name = "IX_ID")
    private long id;
    @Column(name = "IX_PRODUCT_ID")
    private long idProduct;

    @Column(name = "NR_QUANTITY")
    private int quantityProduct;

    @Column(name = "VL_SALE")
    private double saleValue;

    @Column(name = "DT_SALE")
    private LocalDateTime dateSale;

    public Sale(long idProduct, int quantityProduct) {
        LocalDateTime now = LocalDateTime.now();
        this.idProduct = idProduct;
        this.quantityProduct = quantityProduct;
        this.dateSale = now;
    }

    public Sale() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantityProduct() {
        return quantityProduct;
    }

    public void setQuantityProduct(int quantityProduct) {
        this.quantityProduct = quantityProduct;
    }

    public double getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }

    public LocalDateTime getDateSale() {
        return dateSale;
    }

    public void setDateSale(LocalDateTime dateSale) {
        this.dateSale = dateSale;
    }
}
