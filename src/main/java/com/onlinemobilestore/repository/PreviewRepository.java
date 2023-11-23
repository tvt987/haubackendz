package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Preview;
import com.onlinemobilestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreviewRepository extends JpaRepository<Preview, Integer> {
    List<Preview> findByProductId(Integer id);
}
