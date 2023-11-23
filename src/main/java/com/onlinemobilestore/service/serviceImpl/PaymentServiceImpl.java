package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.PaymentRepository;
import com.onlinemobilestore.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;
    public PaymentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
}
