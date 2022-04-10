package com.rom.english.learnbot.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "speech_part")
public class SpeechPart {

    @Id
    @GeneratedValue(generator = "speech_part_id_seq")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Enumerated(EnumType.STRING)
    private SpeechPartNames name;

}
