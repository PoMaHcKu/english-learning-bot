package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordReferenceRepo;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class WordReferenceService {

    private final WordReferenceRepo wordReferenceRepo;

    @Transactional
    public Long createNewReference(WordReference wordReference) {
        Word sourceWord = wordReference.getSourceWord();
        Word targetWord = wordReference.getTargetWord();

        return wordReferenceRepo.findBySourceWord_WordAndTargetWord_Word(sourceWord.getWord(), targetWord.getWord())
                .orElseGet(() -> wordReferenceRepo.save(wordReference))
                .getId();
    }

    @Transactional(readOnly = true)
    public WordReference getRandomReference() {
        return wordReferenceRepo.getRandomWordReference("en");
    }

}
