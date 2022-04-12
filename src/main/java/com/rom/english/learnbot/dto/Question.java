package com.rom.english.learnbot.dto;

import com.rom.english.learnbot.model.Word;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Word questionWord;
    private Set<Word> answerWords;
    private Word rightAnswerWord;

}