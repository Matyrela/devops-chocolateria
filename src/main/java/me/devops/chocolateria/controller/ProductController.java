package me.devops.chocolateria.controller;

import me.devops.chocolateria.domain.Product;
import me.devops.chocolateria.store.InMemoryStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  @GetMapping
  public List<Product> list() { return InMemoryStore.products; }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product p) {
    p.setId(InMemoryStore.productIdGen.getAndIncrement());
    InMemoryStore.products.add(p);
    return ResponseEntity.created(URI.create("/api/products/" + p.getId())).body(p);
  }
}