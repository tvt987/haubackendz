package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.UserPaymentRepository;
import com.onlinemobilestore.service.UserPaymentService;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
    private UserPaymentRepository userPaymentRepository;
    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository){
        this.userPaymentRepository = userPaymentRepository;
    }
}
