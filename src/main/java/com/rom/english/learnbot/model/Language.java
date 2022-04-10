package com.rom.english.learnbot.model;

public enum Language {

    EN("English"), RU("Russian");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
