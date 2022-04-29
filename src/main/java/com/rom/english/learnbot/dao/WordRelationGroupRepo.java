package com.rom.english.learnbot.dao;

import com.rom.english.learnbot.model.WordRelationGroup;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WordRelationGroupRepo extends PagingAndSortingRepository<WordRelationGroup, Long> {
}
