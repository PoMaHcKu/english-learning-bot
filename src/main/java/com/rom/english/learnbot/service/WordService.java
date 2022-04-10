package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordRepo;
import com.rom.english.learnbot.model.Language;
import com.rom.english.learnbot.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepo wordRepo;

    @Transactional
    public Long getOrCreate(Word word) {
        String value = word.getWord();
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Word can't be empty");
        }
        return wordRepo.findByWord(value)
                .orElseGet(() -> wordRepo.save(word))
                .getId();
    }

    @Transactional(readOnly = true)
    public Optional<Word> getById(Long id) {
        return wordRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Word> getByValue(String value) {
        return wordRepo.findByWord(value);
    }

    @Transactional(readOnly = true)
    public Word getRandomWord(Language language, Long... notIn) {
        if (notIn == null || notIn.length == 0) {
            notIn = new Long[]{0L};
        }
        return wordRepo.getRandomWord(language.name(), notIn);
    }
}
