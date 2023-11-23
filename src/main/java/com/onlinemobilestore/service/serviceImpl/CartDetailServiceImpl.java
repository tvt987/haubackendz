package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.CartDetailRepository;
import com.onlinemobilestore.service.CartDetailService;
import org.springframework.stereotype.Service;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    private CartDetailRepository cartDetailRepository;

    public CartDetailServiceImpl (CartDetailRepository cartDetailRepository){
        this.cartDetailRepository = cartDetailRepository;
    }
}
