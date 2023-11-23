package com.onlinemobilestore.service.serviceImpl;

import com.onlinemobilestore.repository.ImageRepository;
import com.onlinemobilestore.service.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }
}
