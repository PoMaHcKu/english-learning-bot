package com.rom.english.learnbot.bot;

public enum BotMessageEnum {
    EXCEPTION_ILLEGAL_MESSAGE("Invalid message"),
    EXCEPTION_WHAT_THE_FUCK("What are you doing???");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
