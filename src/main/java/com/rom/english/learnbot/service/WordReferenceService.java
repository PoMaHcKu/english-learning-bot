package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dao.WordReferenceRepo;
import com.rom.english.learnbot.model.SpeechPart;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordReferenceService {

    private final WordReferenceRepo wordReferenceRepo;
    private final SpeechPartService speechPartService;
    private final WordService wordService;

    @Transactional
    public Long createNewReference(WordReference wordReference) {
        Word sourceWord = wordReference.getSourceWord();
        Word targetWord = wordReference.getTargetWord();

        List<SpeechPart> sourceSpeechPart = sourceWord.getSpeechPart().stream()
                .map(sp -> speechPartService.getByName(sp.getName()))
                .collect(Collectors.toList());
        sourceWord.setSpeechPart(sourceSpeechPart);

        List<SpeechPart> targetSpeechParts = targetWord.getSpeechPart().stream()
                .map(sp -> speechPartService.getByName(sp.getName()))
                .collect(Collectors.toList());
        targetWord.setSpeechPart(targetSpeechParts);

        final Word finalSourceWord = wordService.getOrCreate(sourceWord);
        List<SpeechPart> sourceWordNewParts = sourceSpeechPart.stream()
                .filter(sp -> !finalSourceWord.getSpeechPart().contains(sp))
                .collect(Collectors.toList());

        final Word finalTargetWord = wordService.getOrCreate(targetWord);
        List<SpeechPart> targetWordNewParts = targetSpeechParts.stream()
                .filter(sp -> !finalTargetWord.getSpeechPart().contains(sp))
                .collect(Collectors.toList());

        finalSourceWord.getSpeechPart().addAll(sourceWordNewParts);
        finalTargetWord.getSpeechPart().addAll(targetWordNewParts);

        return wordReferenceRepo.findBySourceWord_WordAndTargetWord_Word(finalSourceWord.getWord(), finalTargetWord.getWord())
                .orElseGet(() -> {
                    wordReference.setSourceWord(finalSourceWord);
                    wordReference.setTargetWord(finalTargetWord);
                    return wordReferenceRepo.save(wordReference);
                })
                .getId();
    }

    @Transactional(readOnly = true)
    public WordReference getRandomReference() {
        return wordReferenceRepo.getRandomWordReference("en");
    }

}
