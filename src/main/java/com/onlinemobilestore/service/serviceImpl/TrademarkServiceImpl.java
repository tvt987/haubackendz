package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.TrademarkRepository;
import com.onlinemobilestore.service.TrademarkService;
import org.springframework.stereotype.Service;

@Service
public class TrademarkServiceImpl implements TrademarkService {
    private TrademarkRepository trademarkRepository;

    public TrademarkServiceImpl(TrademarkRepository trademarkRepository) {
        this.trademarkRepository = trademarkRepository;
    }
}
