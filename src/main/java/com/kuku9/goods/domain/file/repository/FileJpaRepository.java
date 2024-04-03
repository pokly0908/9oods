package com.kuku9.goods.domain.file.repository;

import com.kuku9.goods.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<File, Long> {

}
