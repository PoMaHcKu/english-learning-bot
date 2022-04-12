package com.rom.english.learnbot.service;

import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.Language;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final WordReferenceService wordReferenceService;
    private final WordService wordService;


    public Question getRandomQuestion() {
        WordReference randomReference = wordReferenceService.getRandomReference();

        Word wordCorrect = randomReference.getTargetWord();
        Word firstIncorrect = wordService.getRandomWord(Language.RU, wordCorrect.getId());
        Word secondIncorrect = wordService.getRandomWord(Language.RU, firstIncorrect.getId(), wordCorrect.getId());

        return Question.builder()
                .questionWord(randomReference.getSourceWord())
                .answerWords(Set.of(wordCorrect, firstIncorrect, secondIncorrect))
                .rightAnswerWord(wordCorrect)
                .build();
    }
}
