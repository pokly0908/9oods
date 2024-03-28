package com.kuku9.goods.domain.file.repository;

import com.kuku9.goods.domain.file.entity.File;

public interface FileRepository {

	File findById(Long fileId);

}
