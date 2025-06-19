package com.nisum.productapi.service;

import com.nisum.productapi.model.Product;
import com.nisum.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        product.setId(id);
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

