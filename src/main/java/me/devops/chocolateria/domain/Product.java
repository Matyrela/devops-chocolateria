package me.devops.chocolateria.domain;

import lombok.Data;

@Data
public class Product {
  private Long id;
  private String name;
  private int priceCents;

  // getters/setters/constructores
}