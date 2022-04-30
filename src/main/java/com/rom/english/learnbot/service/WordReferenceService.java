package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordReferenceRepo;
import com.rom.english.learnbot.model.SpeechPart;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordReferenceService {

    private final WordReferenceRepo wordReferenceRepo;
    private final SpeechPartService speechPartService;
    private final WordService wordService;

    @Transactional
    public WordReference createNewReference(WordReference wordReference) {
        Word sourceWord = wordReference.getSourceWord();
        Word targetWord = wordReference.getTargetWord();

        List<SpeechPart> sourceSpeechPart = Optional.ofNullable(sourceWord.getSpeechParts())
                .orElse(Collections.emptyList()).stream()
                .map(sp -> speechPartService.getByName(sp.getName()))
                .collect(Collectors.toList());
        sourceWord.setSpeechParts(sourceSpeechPart);

        List<SpeechPart> targetSpeechParts = Optional.ofNullable(targetWord.getSpeechParts())
                .orElse(Collections.emptyList()).stream()
                .map(sp -> speechPartService.getByName(sp.getName()))
                .collect(Collectors.toList());
        targetWord.setSpeechParts(targetSpeechParts);

        final Word finalSourceWord = wordService.getOrCreate(sourceWord);
        List<SpeechPart> sourceWordNewParts = sourceSpeechPart.stream()
                .filter(sp -> !finalSourceWord.getSpeechParts().contains(sp))
                .collect(Collectors.toList());

        final Word finalTargetWord = wordService.getOrCreate(targetWord);
        List<SpeechPart> targetWordNewParts = targetSpeechParts.stream()
                .filter(sp -> !finalTargetWord.getSpeechParts().contains(sp))
                .collect(Collectors.toList());

        finalSourceWord.getSpeechParts().addAll(sourceWordNewParts);
        finalTargetWord.getSpeechParts().addAll(targetWordNewParts);

        return wordReferenceRepo.findBySourceWord_WordAndTargetWord_Word(finalSourceWord.getWord(), finalTargetWord.getWord())
                .orElseGet(() -> {
                    wordReference.setSourceWord(finalSourceWord);
                    wordReference.setTargetWord(finalTargetWord);
                    return wordReferenceRepo.save(wordReference);
                });
    }

    @Transactional(readOnly = true)
    public WordReference getRandomReference() {
        return wordReferenceRepo.getRandomWordReference("en");
    }

    public WordReference getRandomReferenceFromGroup(Long groupId) {
        return wordReferenceRepo.getRandomWordReference(groupId);
    }
}
