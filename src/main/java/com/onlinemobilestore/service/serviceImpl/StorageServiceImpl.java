package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.StorageRepository;
import com.onlinemobilestore.service.StorageService;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    private StorageRepository storageRepository;
    public StorageServiceImpl(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
    }
}
