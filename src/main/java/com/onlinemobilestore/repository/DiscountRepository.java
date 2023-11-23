package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Query("SELECT d FROM Discount d WHERE d.product.id = :productId")
    List<Discount> findDiscountIdByProductId(@Param("productId") Integer productId);
}
