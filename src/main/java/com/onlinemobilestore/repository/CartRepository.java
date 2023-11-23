package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findBySessionId(String sessionId);

    Cart findByUserId(Integer idUser);
}
