package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordRelationGroupRepo;
import com.rom.english.learnbot.model.WordReference;
import com.rom.english.learnbot.model.WordRelationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordRelationGroupService {

    private final WordRelationGroupRepo wordRelationGroupRepo;

    public Page<WordRelationGroup> getPage(int pageNum) {
        PageRequest pr = PageRequest.of(pageNum, 50);
        return wordRelationGroupRepo.findAll(pr);
    }

    public WordRelationGroup getOrCreate(String title) {
        WordRelationGroup group = wordRelationGroupRepo.findByTitle(title);
        if (group == null) {
            group = WordRelationGroup.builder()
                    .title(title)
                    .build();
            return wordRelationGroupRepo.save(group);
        }
        return group;
    }

    public void addWordToGroup(WordRelationGroup group, WordReference wordReference) {
        Set<Long> wordRefs = wordRelationGroupRepo.findById(group.getId())
                .map(WordRelationGroup::getWords)
                .map(s -> s.stream().map(WordReference::getId).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        if (!wordRefs.contains(wordReference.getId())) {
            group.getWords().add(wordReference);
            wordRelationGroupRepo.save(group);
        }
    }
}
