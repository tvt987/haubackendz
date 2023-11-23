package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.ColorRepository;
import com.onlinemobilestore.service.ColorService;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
    private ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository){
        this.colorRepository = colorRepository;
    }
}
