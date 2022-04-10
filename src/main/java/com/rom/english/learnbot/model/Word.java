package com.rom.english.learnbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "words")
public class Word {

    @Id
    @GeneratedValue(generator = "words_id_seq")
    private Long id;

    private String word;

    @Enumerated(EnumType.STRING)
    private SpeechPart speechPart;

    @Enumerated(EnumType.STRING)
    private Language language;

}
