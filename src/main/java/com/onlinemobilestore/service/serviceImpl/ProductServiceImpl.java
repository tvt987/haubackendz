package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.repository.ProductRepository;
import com.onlinemobilestore.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
