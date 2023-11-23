package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.PreviewRepository;
import com.onlinemobilestore.service.PreviewService;
import org.springframework.stereotype.Service;

@Service
public class PreviewServiceImpl implements PreviewService {
    private PreviewRepository previewRepository;
    public PreviewServiceImpl(PreviewRepository previewRepository){
        this.previewRepository = previewRepository;
    }
}
