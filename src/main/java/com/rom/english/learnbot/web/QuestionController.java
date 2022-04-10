package com.rom.english.learnbot.web;

import com.rom.english.learnbot.dto.Question;
import com.rom.english.learnbot.model.Language;
import com.rom.english.learnbot.model.Word;
import com.rom.english.learnbot.model.WordReference;
import com.rom.english.learnbot.service.WordReferenceService;
import com.rom.english.learnbot.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final WordReferenceService wordReferenceService;
    private final WordService wordService;

    @GetMapping("get-random")
    public ResponseEntity<Question> getRandomQuestion() {
        WordReference randomReference = wordReferenceService.getRandomReference();

        Word wordCorrect = randomReference.getTargetWord();
        Word firstIncorrect = wordService.getRandomWord(Language.RU, wordCorrect.getId());
        Word secondIncorrect = wordService.getRandomWord(Language.RU, firstIncorrect.getId(), wordCorrect.getId());

        Question question = Question.builder()
                .questionWord(randomReference.getSourceWord())
                .answerWords(Set.of(wordCorrect, firstIncorrect, secondIncorrect))
                .rightAnswerId(wordCorrect.getId())
                .build();

        return ResponseEntity.ok(question);
    }

}
