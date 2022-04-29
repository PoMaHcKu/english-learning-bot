package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordRelationGroupRepo;
import com.rom.english.learnbot.model.WordRelationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordRelationGroupService {

    private final WordRelationGroupRepo wordRelationGroupRepo;

    public Page<WordRelationGroup> getPage(int pageNum) {
        PageRequest pr = PageRequest.of(pageNum, 50);
        return wordRelationGroupRepo.findAll(pr);
    }
}
