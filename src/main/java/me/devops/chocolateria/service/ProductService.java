package me.devops.chocolateria.service;

import me.devops.chocolateria.domain.Product;
import me.devops.chocolateria.store.InMemoryStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllProducts() {
        return InMemoryStore.products;
    }

    public Product createProduct(Product product) {
        product.setId(InMemoryStore.productIdGen.getAndIncrement());
        InMemoryStore.products.add(product);
        return product;
    }
}
