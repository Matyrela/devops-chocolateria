package me.devops.chocolateria.controller;

import io.micrometer.core.instrument.MeterRegistry;
import me.devops.chocolateria.domain.Order;
import me.devops.chocolateria.domain.Product;
import me.devops.chocolateria.store.InMemoryStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final MeterRegistry registry;
  public OrderController(MeterRegistry registry) { this.registry = registry; }

  @GetMapping
  public List<Order> list() { return InMemoryStore.orders; }

  @PostMapping
  public ResponseEntity<Order> create(@RequestParam Long productId, @RequestParam int quantity) {
    Product p = InMemoryStore.products.stream().filter(prod -> prod.getId().equals(productId)).findFirst().orElseThrow();
    Order o = new Order();
    o.setId(InMemoryStore.orderIdGen.getAndIncrement());
    o.setProduct(p);
    o.setQuantity(quantity);
    o.setCreatedAt(Instant.now());
    InMemoryStore.orders.add(o);
    registry.counter("orders_items_total", "productId", p.getId().toString(), "productName", p.getName()).increment(quantity);
    return ResponseEntity.created(URI.create("/api/orders/" + o.getId())).body(o);
  }

  @GetMapping("/rate/{productId}")
  public long countLast5m(@PathVariable Long productId) {
    Instant now = Instant.now();
    return InMemoryStore.orders.stream()
      .filter(o -> o.getProduct().getId().equals(productId) && o.getCreatedAt().isAfter(now.minus(Duration.ofMinutes(5))))
      .mapToLong(Order::getQuantity)
      .sum();
  }
}