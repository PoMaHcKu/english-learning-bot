package com.rom.english.learnbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "words_speech_part",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "speech_part_id"))
    private List<SpeechPart> speechPart;

    @Enumerated(EnumType.STRING)
    private Language language;

}
