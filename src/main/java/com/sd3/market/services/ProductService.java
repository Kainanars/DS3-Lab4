package com.sd3.market.services;

import com.sd3.market.dtos.ProductDto;
import com.sd3.market.entities.Product;
import com.sd3.market.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(p -> new ProductDto(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getQuantityInStock()))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantityInStock()
            );
        }
        return null;
    }


    public ProductDto addProduct(ProductDto productDTO) {
        Product product = new Product(productDTO.name, productDTO.price, productDTO.description, productDTO.quantityInStock);

        product = productRepository.save(product);

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }

    public ProductDto updateProduct(long id, ProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(updatedProductDto.name);
            existingProduct.setDescription(updatedProductDto.description);
            existingProduct.setPrice(updatedProductDto.price);
            existingProduct.setQuantityInStock(updatedProductDto.quantityInStock);

            existingProduct = productRepository.save(existingProduct);

            return new ProductDto(
                    existingProduct.getId(),
                    existingProduct.getName(),
                    existingProduct.getDescription(),
                    existingProduct.getPrice(),
                    existingProduct.getQuantityInStock()
            );
        }
        return null;
    }


    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
