package com.rom.english.learnbot.bot;

public enum BotCommands {
    CHECK_ANSWER("CA");

    private final String title;

    BotCommands(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
