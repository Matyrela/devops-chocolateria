package me.devops.chocolateria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.devops.chocolateria.domain.Product;
import me.devops.chocolateria.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "API para gestionar productos de la chocolatería")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos disponibles")
  @GetMapping
  public List<Product> list() {
    return productService.getAllProducts();
  }

  @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema")
  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product product) {
    Product createdProduct = productService.createProduct(product);
    return ResponseEntity.created(URI.create("/api/products/" + createdProduct.getId())).body(createdProduct);
  }
}