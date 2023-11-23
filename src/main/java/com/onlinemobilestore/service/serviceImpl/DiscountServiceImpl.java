package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.DiscountRepository;
import com.onlinemobilestore.service.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {
    private DiscountRepository discountRepository;
    public DiscountServiceImpl(DiscountRepository discountRepository){
        this.discountRepository = discountRepository;
    }
}
