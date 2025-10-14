package me.devops.chocolateria.service;

import io.micrometer.core.instrument.MeterRegistry;
import me.devops.chocolateria.domain.Order;
import me.devops.chocolateria.domain.Product;
import me.devops.chocolateria.store.InMemoryStore;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {
    private final MeterRegistry registry;

    public OrderService(MeterRegistry registry) {
        this.registry = registry;
    }

    public List<Order> getAllOrders() {
        return InMemoryStore.orders;
    }

    public Order createOrder(Long productId, int quantity) {
        Product product = InMemoryStore.products.stream()
                .filter(prod -> prod.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Order order = new Order();
        order.setId(InMemoryStore.orderIdGen.getAndIncrement());
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setCreatedAt(Instant.now());

        InMemoryStore.orders.add(order);

        registry.counter("orders_items_total",
                "productId", product.getId().toString(),
                "productName", product.getName())
                .increment(quantity);

        return order;
    }

    public long getOrderRateForProduct(Long productId, Duration duration) {
        Instant now = Instant.now();
        return InMemoryStore.orders.stream()
                .filter(o -> o.getProduct().getId().equals(productId) &&
                        o.getCreatedAt().isAfter(now.minus(duration)))
                .mapToLong(Order::getQuantity)
                .sum();
    }
}
