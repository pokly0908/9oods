package com.kuku9.goods.domain.file.repository;

import com.kuku9.goods.domain.file.entity.*;

public interface FileRepository {

    File findById(Long fileId);

    void save(File file);

    void delete(File file);

}
