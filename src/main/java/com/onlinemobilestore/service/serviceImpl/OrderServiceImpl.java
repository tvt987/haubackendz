package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.OrderRepository;
import com.onlinemobilestore.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
}
