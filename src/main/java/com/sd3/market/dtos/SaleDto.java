package com.sd3.market.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.Date;

public class SaleDto {
    public long id;
    public long idProduct;
    public int quantityProduct;
    public double saleValue;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateSale;


    public SaleDto() {
    }

    public SaleDto(long id, long idProduct, int quantityProduct, double saleValue, LocalDateTime dateSale) {
        this.id = id;
        this.idProduct = idProduct;
        this.quantityProduct = quantityProduct;
        this.saleValue = saleValue;
        this.dateSale = dateSale;
    }

    public SaleDto(long idProduct, int quantityProduct) {
        this.idProduct = idProduct;
        this.quantityProduct = quantityProduct;
    }
}
