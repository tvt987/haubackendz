package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.CategoryRepository;
import com.onlinemobilestore.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
}
