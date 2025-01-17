package com.zbra.go.persistence;

import com.zbra.go.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, String> {

    ImageFile findByMediaId(String mediaId);
}
