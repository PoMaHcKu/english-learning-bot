package com.rom.english.parsers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private String word;
    private List<SpeechPart> speechParts;
    private Language language;
}
