package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {

    CartDetail findByCartId(int id);
}
