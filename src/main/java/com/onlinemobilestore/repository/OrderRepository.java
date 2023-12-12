package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.Order;
import com.onlinemobilestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
