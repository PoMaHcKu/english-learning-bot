package com.rom.english.learnbot.model;

public enum SpeechPartNames {
    NOUN(0, "Noun"),
    VERB_PRESENT(1, "Verb (present tense)"),
    VERB_PAST(2, "Verb (past tense)"),
    VERB_PARTICIPLE(3, "Verb (past participle tense)"),
    VERB_ING(4, "Verb (gerund)"),
    VERB_INF(5, "Verb (infinitive)"),
    ADVERB(6, "Adverb"),
    ADJECTIVE(7, "Adjective"),
    NUMERIC(8, "Numeric"),
    PRONOUN(9, "Pronoun"),
    PRETEXT(10, "Pretext");

    private final int index;
    private final String title;

    SpeechPartNames(int index, String title) {
        this.index = index;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }
}