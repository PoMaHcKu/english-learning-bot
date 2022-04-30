package com.rom.english.learnbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "words_group")
public class WordRelationGroup {

    @Id
    @GeneratedValue(generator = "words_group_id_seq")
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "words_group_words",
            joinColumns = @JoinColumn(name = "words_group_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    private Set<WordReference> words;
}
