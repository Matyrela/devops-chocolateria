package me.devops.chocolateria.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Order {
  private Long id;
  private Product product;
  private int quantity;
  private Instant createdAt = Instant.now();
  // getters/setters/constructores
}