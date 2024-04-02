package com.kuku9.goods.domain.file.repository;

import com.kuku9.goods.domain.file.entity.*;
import lombok.*;
import org.springframework.stereotype.*;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final FileJpaRepository fileJpaRepository;

    public File findById(Long fileId) {
        return fileJpaRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("해당 이미지는 존재하지 않습니다."));
    }

    public void save(File file) {
        fileJpaRepository.save(file);
    }

    public void delete(File file) {
        fileJpaRepository.delete(file);
    }
}
