package me.devops.chocolateria.store;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import me.devops.chocolateria.domain.Order;
import me.devops.chocolateria.domain.Product;

public class InMemoryStore {
  public static final List<Product> products = new ArrayList<>();
  public static final List<Order> orders = new ArrayList<>();
  public static final AtomicLong productIdGen = new AtomicLong(1);
  public static final AtomicLong orderIdGen = new AtomicLong(1);
}