package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.RepPreviewRepository;
import com.onlinemobilestore.service.RepPreviewService;

public class RepPreviewServiceImpl implements RepPreviewService {
    private RepPreviewRepository repPreviewRepository;
    public RepPreviewServiceImpl(RepPreviewRepository repPreviewRepository){
        this.repPreviewRepository = repPreviewRepository;
    }
}
