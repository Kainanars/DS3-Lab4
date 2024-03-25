package com.sd3.market.controllers;

import com.sd3.market.dtos.ProductDto;
import com.sd3.market.dtos.SaleDto;
import com.sd3.market.services.SaleService;
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
@RequestMapping("/api/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping
    @Operation(summary = "Obtém a lista de vendas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de vendas",
                            content = @Content(schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(value = """
[
  {
    "id": 9,
    "idProduct": 8,
    "quantityProduct": 1,
    "saleValue": 880.0,
    "dateSale": "25-03-2024 19:01:00"
  },
  {
    "id": 10,
    "idProduct": 8,
    "quantityProduct": 2,
    "saleValue": 899.99,
    "dateSale": "25-03-2024 19:09:43"
  }
]
                                            """))),
                    @ApiResponse(responseCode = "204", description = "Nenhum venda encontrada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<List<SaleDto>> getAllSales() {
        try {
            List<SaleDto> sales = saleService.getAllSales();
            if (sales.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(sales);
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
    @Operation(summary = "Obtém uma venda pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna a venda encontrada",
                            content = @Content(schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(value = """
{
  "id": 12,
  "idProduct": 11,
  "quantityProduct": 2,
  "saleValue": 2401.0,
  "dateSale": "25-03-2024 19:15:27"
}
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<SaleDto> getSaleById(@PathVariable long id) {
        try {
            SaleDto sale = saleService.getSaleById(id);
            if (sale != null) {
                return ResponseEntity.ok(sale);
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
    @Operation(summary = "Cria uma nova venda",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaleDto.class),
                            examples = @ExampleObject(value = """
{
  "idProduct": 11,
  "quantityProduct": 3
}
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Venda criada com sucesso",
                            content = @Content(schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(value = """
{
  "id": 14,
  "idProduct": 11,
  "quantityProduct": 3,
  "saleValue": 2699.97,
  "dateSale": "25-03-2024 20:14:57"
}
                                            """))),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<SaleDto> createSale(@RequestBody SaleDto saleDto) {
        try {
            SaleDto sale = saleService.createSale(saleDto);
            if (sale != null) {
                return ResponseEntity.ok(sale);
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

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma venda existente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(value = """
{
  "id": 12,
  "idProduct": 11,
  "quantityProduct": 2
 }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(value = """
{
  "id": 12,
  "idProduct": 11,
  "quantityProduct": 2,
  "saleValue": 2401.0,
  "dateSale": "25-03-2024 20:21:17"
}
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<SaleDto> updateSale(@PathVariable long id, @RequestBody SaleDto saleDto) {
        try {
            SaleDto updateSale = saleService.updateSale(id, saleDto);
            if (updateSale != null) {
                return ResponseEntity.ok(updateSale);
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
    @Operation(summary = "Exclui uma venda existente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            })
    public ResponseEntity<Void> deleteSale(@PathVariable long id) {
        try {
            boolean deleted = saleService.deleteSale(id);
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

