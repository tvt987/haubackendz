package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.CartRepository;
import com.onlinemobilestore.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

}
