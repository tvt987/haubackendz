package com.onlinemobilestore.repository;

import com.onlinemobilestore.entity.RepPreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepPreviewRepository extends JpaRepository<RepPreview, Integer> {
}
