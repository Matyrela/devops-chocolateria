package me.devops.chocolateria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.devops.chocolateria.domain.Order;
import me.devops.chocolateria.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "API para gestionar pedidos de la chocolatería")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Listar todos los pedidos", description = "Retorna una lista de todos los pedidos realizados")
    @GetMapping
    public List<Order> list() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido para un producto específico")
    @PostMapping
    public ResponseEntity<Order> create(
            @Parameter(description = "ID del producto a ordenar") @RequestParam Long productId,
            @Parameter(description = "Cantidad de productos a ordenar") @RequestParam int quantity) {
        Order order = orderService.createOrder(productId, quantity);
        return ResponseEntity.created(URI.create("/api/orders/" + order.getId())).body(order);
    }

    @Operation(summary = "Obtener tasa de pedidos", description = "Obtiene la cantidad total de productos ordenados en los últimos 5 minutos")
    @GetMapping("/rate/{productId}")
    public long countLast5m(
            @Parameter(description = "ID del producto a consultar") @PathVariable Long productId) {
        return orderService.getOrderRateForProduct(productId, Duration.ofMinutes(5));
    }
}