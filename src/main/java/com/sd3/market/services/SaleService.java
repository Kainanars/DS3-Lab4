package com.sd3.market.services;

import com.sd3.market.dtos.ProductDto;
import com.sd3.market.dtos.SaleDto;
import com.sd3.market.entities.Product;
import com.sd3.market.entities.Sale;
import com.sd3.market.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductService productService;


    public List<SaleDto> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(s -> new SaleDto(
                        s.getId(),
                        s.getIdProduct(),
                        s.getQuantityProduct(),
                        s.getSaleValue(),
                        s.getDateSale()))
                .collect(Collectors.toList());
    }

    public SaleDto getSaleById(long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale != null) {
            System.out.println("Data:" + sale.getDateSale());
            return new SaleDto(
                    sale.getId(),
                    sale.getIdProduct(),
                    sale.getQuantityProduct(),
                    sale.getSaleValue(),
                    sale.getDateSale());
        }
        return null;
    }


    public SaleDto createSale(SaleDto saleDto) {
        Sale sale = new Sale(saleDto.idProduct, saleDto.quantityProduct);
        if (sale.getQuantityProduct() > 0){
            ProductDto product;
            product = productService.getProductById(sale.getIdProduct());

            if (product != null && product.quantityInStock >= sale.getQuantityProduct()) {
                setValuesOfSale(sale, product);

                // Salva a venda com o valor de venda ajustado pelo desconto
                sale = saleRepository.save(sale);
                productService.updateProduct(product.id,
                        new ProductDto(
                                product.id,
                                product.name,
                                product.description,
                                product.price,
                                product.quantityInStock - sale.getQuantityProduct()));

                return new SaleDto(
                        sale.getId(),
                        sale.getIdProduct(),
                        sale.getQuantityProduct(),
                        sale.getSaleValue(),
                        sale.getDateSale());
            }
        }
        return null;
    }

    public SaleDto updateSale(long id, SaleDto saleDto) {
        Sale existingSale = saleRepository.findById(id).orElse(null);
        if (existingSale != null) {
            existingSale.setIdProduct(saleDto.idProduct);
            if (saleDto.quantityProduct != existingSale.getQuantityProduct()){
                int diff = saleDto.quantityProduct - existingSale.getQuantityProduct();
                ProductDto product;
                product = productService.getProductById(existingSale.getIdProduct());

                if (product != null && product.quantityInStock >= existingSale.getQuantityProduct()) {
                    existingSale.setQuantityProduct(saleDto.quantityProduct);
                    setValuesOfSale(existingSale, product);


                    productService.updateProduct(product.id,
                            new ProductDto(
                                    product.id,
                                    product.name,
                                    product.description,
                                    product.price,
                                    product.quantityInStock - diff));
                }
            }
            existingSale.setDateSale(LocalDateTime.now());
            existingSale = saleRepository.save(existingSale);

            return new SaleDto(
                    existingSale.getId(),
                    existingSale.getIdProduct(),
                    existingSale.getQuantityProduct(),
                    existingSale.getSaleValue(),
                    existingSale.getDateSale());
        }
        return null;
    }

    private static void setValuesOfSale(Sale sale, ProductDto product) {
        sale.setSaleValue(product.price * sale.getQuantityProduct());

        double discountRate = 0.0;
        if (sale.getQuantityProduct() > 20) {
            discountRate = 0.10; // 10% de desconto para mais de 20 unidades
        } else if (sale.getQuantityProduct() > 10) {
            discountRate = 0.05; // 5% de desconto para mais de 10 unidades
        }

        double originalSaleValue = sale.getSaleValue();
        double discountAmount = originalSaleValue * discountRate;
        double finalSaleValue = originalSaleValue - discountAmount;

        sale.setSaleValue(finalSaleValue);
    }

    public boolean deleteSale(Long id) {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale == null) {
            return false;
        }
        saleRepository.deleteById(id);
        return true;
    }
}
