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
@Entity(name = "word_relation")
public class WordReference {

    @Id
    @GeneratedValue(generator = "word_relation_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_word")
    private Word sourceWord;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_word")
    private Word targetWord;
}
