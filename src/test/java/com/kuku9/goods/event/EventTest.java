package com.kuku9.goods.event;

import com.kuku9.goods.domain.event.service.EventService;
import com.kuku9.goods.domain.file.entity.File;
import com.kuku9.goods.domain.file.repository.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EventTest {

	@Autowired
	FileRepository fileRepository;

	@Autowired
	EventService eventService;

	@DisplayName("file 테스트")
	@Test
	void filesave() {
		File file = File.builder().url("http://").build();
		fileRepository.save(file);
	}
}
