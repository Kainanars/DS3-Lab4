package com.sd3.market.controllers;

import com.sd3.market.dtos.ProductDto;
import com.sd3.market.entities.Product;
import com.sd3.market.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Obtém a lista de produtos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos",
                            content = @Content(schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(value = """
[
  {
    "id": 8,
    "name": "Smartphone X12",
    "description": "Última geração com 5G e 128GB",
    "price": 899.99,
    "quantityInStock": 20
  },
  {
    "id": 11,
    "name": "Laptop Gamer Pro",
    "description": "16GB RAM, SSD 512GB, GTX 1660 Ti",
    "price": 1200.5,
    "quantityInStock": 8
  }
]
                                            """))),
                    @ApiResponse(responseCode = "204", description = "Nenhum produto encontrada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(products);
            }
        }catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso aos dados", e);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de persistência", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foi gerada uma exceção", e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém um produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna o produto encontrado",
                            content = @Content(schema = @Schema(implementation = Product.class),
                                    examples = @ExampleObject(value = """
{
  "id": 11,
  "name": "Laptop Gamer Pro",
  "description": "16GB RAM, SSD 512GB, GTX 1660 Ti",
  "price": 1200.5,
  "quantityInStock": 8
}
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        ProductDto product = productService.getProductById(id);
        try {
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso aos dados", e);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de persistência", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foi gerada uma exceção", e);
        }
    }


    @PostMapping
    @Operation(summary = "Cria um novo produto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(value = """
{
  "name": "Fone de Ouvido Bluetooth",
  "description": "Cancelamento de ruído ativo, até 35h de bateria",
  "price": 150.0,
  "quantityInStock": 50
}
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                            content = @Content(schema = @Schema(implementation = Product.class),
                                    examples = @ExampleObject(value = """
{
  "id": 13,
  "name": "Fone de Ouvido Bluetooth",
  "description": "Cancelamento de ruído ativo, até 35h de bateria",
  "price": 150.0,
  "quantityInStock": 50
}
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto product = productService.addProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(product).getBody();
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso aos dados", e);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de persistência", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foi gerada uma exceção", e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(value = """
{
  "name": "Smartphone X12",
  "description": "Última geração com 5G e 128GB",
  "price": 899.99,
  "quantityInStock": 25
}
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(value = """
{
  "id": 11,
  "name": "Smartphone X12",
  "description": "Última geração com 5G e 128GB",
  "price": 899.99,
  "quantityInStock": 25
}
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto updatedProductDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, updatedProductDto);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso aos dados", e);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de persistência", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foi gerada uma exceção", e);
        }

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma produto existente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de acesso aos dados", e);
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de persistência", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Foi gerada uma exceção", e);
        }
    }
}

