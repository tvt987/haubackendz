package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Product;
import com.onlinemobilestore.entity.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrademarkRepository extends JpaRepository<Trademark,Integer> {
    List<Product> findProductsById(int trademarkId);

    @Query("select c.id, c.name from Trademark c")
    List<Object[]> getAllIdAndName();
}
