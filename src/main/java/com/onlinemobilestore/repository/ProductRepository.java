package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findByTrademarkId(int id);
}
