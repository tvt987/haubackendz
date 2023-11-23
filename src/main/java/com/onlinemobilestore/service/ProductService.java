package com.onlinemobilestore.service;

import com.onlinemobilestore.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    List<Product> findAll();
}
